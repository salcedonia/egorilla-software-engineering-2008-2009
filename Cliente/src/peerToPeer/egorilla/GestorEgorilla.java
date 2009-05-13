package peerToPeer.egorilla;

import datos.Archivo;
import datos.Fragmento;
import gestorDeConfiguracion.ControlConfiguracionClienteException;
import gestorDeConfiguracion.ObservadorControlConfiguracionCliente;
import gestorDeFicheros.GestorCompartidos;
import gestorDeFicheros.GestorDisco;
import gestorDeRed.GestorDeRed;
import gestorDeRed.TCP.GestorDeRedTCPimpl;
import java.util.ArrayList;
import java.util.Properties;
import mensajes.Mensaje;
import mensajes.p2p.Tengo;
import mensajes.serverclient.DatosCliente;
import mensajes.serverclient.ListaArchivos;
import mensajes.serverclient.RespuestaPeticionConsulta;
import org.apache.log4j.Logger;
import peerToPeer.GestorP2P;
import peerToPeer.ObservadorP2P;
import peerToPeer.descargas.AlmacenDescargas;
import peerToPeer.descargas.Descargador;
import gestorDeConfiguracion.ControlConfiguracionCliente;
import gestorDeConfiguracion.PropiedadCliente;
import java.net.InetAddress;
import mensajes.p2p.Altoo;
import mensajes.serverclient.PeticionConsulta;
import mensajes.serverclient.PeticionDescarga;
import peerToPeer.EstadoP2P;
import peerToPeer.subidas.AlmacenSubidas;


/**
 *
 * @author Luis Ayuso, José Miguel Guerrero
 * @author Modificado por Javier Sánchez
 * @author rediseño por Luis Ayuso (ver 7ª iteración
 *
 * Esta clase utiliza parametros de configuracion por tanto va a ser observadora de ControlConfiguracionCliente 
 * y sera notificada cuando cambie la configuracion dando un tratamiento adecuado al cambio (o no hacer nada).
 * //TODO: Dar tratamiento a los cambios en la configuracion del cliente (si asi se desea).
 */
public class GestorEgorilla implements ObservadorControlConfiguracionCliente,
                                                      GestorP2P{
    /** gestor de red para realizar las comunicaciones. */
    private GestorDeRed<Mensaje> _red;
    /** se encarga de enviar los mensajes */
    private GestorMensajes _colaMensajes;
    /** variable para ver si estamos o no conectados */
    //private boolean _conectado;
    private EstadoP2P  _estado;
    /** almacen de descargas donde se almacenan estas */
    private AlmacenDescargas _descargas;
    /** el descargador es un hilo que se encarga de gestionar las descargas*/
    private Descargador     _descargador;
    /** el servidor egorilla se encarga de recibir los mensajes y tratarlos*/
    private ServidorP2PEgorilla _server;
    /** lista de observadores */
    private ArrayList<ObservadorP2P> _observadores;
    /** ip del servidor al que conectamos */
    private String _IPservidor;
    /** puerto del servidor al que conectamos */
    private int _puertoServidor;
    /** el loger */
    private static final Logger _log = Logger.getLogger(GestorEgorilla.class);
    /** puerto por el que escucha este cliente */
    private int _puertoCliente;
    /** vigilante de conexion */
    private VigilanteConexion _vigilante;

    /** almacen de subidas donde se almacenan estas */
    private AlmacenSubidas _subidas;
    /** el gestor de disco para hacer consultas y transferencias */
    private GestorDisco _disco;
    
    /**
     * constructor de la clase
     * @param puerto puerto por el que se escucha
     * @param disco, el gestor de disco
     */
    public GestorEgorilla(int puerto, GestorDisco disco) {
        //inicialmente no estamos conectados
        //_conectado =false;
        _estado = EstadoP2P.DESCONECTADO;
        _disco = disco;

        _observadores = new ArrayList<ObservadorP2P>();
        _descargas = new AlmacenDescargas();
        _subidas = new AlmacenSubidas(this);
        _server = new ServidorP2PEgorilla(this);
        
        // prepara la red y el servidor
        _puertoCliente = puerto;
        _red = new GestorDeRedTCPimpl<Mensaje>(puerto);
        _red.registraReceptor(_server);
        _red.comienzaEscucha();

        _vigilante =null;
        _colaMensajes = null;
    }

//------------------------------------------------------------------------------
//--------------------- interfaz ObservadorControlConfiguracionCliente ---------
//------------------------------------------------------------------------------
    @Override
    public void cambioEnPropiedades(ControlConfiguracionCliente obj, Properties propiedades) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void descargaCompletada(Archivo archivo) {
               // esta descarga no se continuará
       _descargas.descargaCompletada(archivo);
       
        for (ObservadorP2P obs: _observadores) {
            obs.finDescarga(this, archivo);
        }
    }
    
//------------------------------------------------------------------------------
//--------------------- interfaz GestorP2P -------------------------------------
//------------------------------------------------------------------------------

    @Override
    public void desconectar(){
        // enviamos Altoo al servidor
        Altoo alto =  new Altoo();
        alto.setDestino(_IPservidor, _puertoServidor);
        this.addMensajeParaEnviar(alto);
        //this._conectado = false;
        _estado = EstadoP2P.DESCONECTADO;
        // enviamos Altoo a todos los suplicantes de subidas y borramos todas
        // estas.

        // eliminamos la conexion con el servidor
        _red.eliminaConexion(_IPservidor);
        
        // termina las descargas.
        _descargas.pararDescargas();
        for (ObservadorP2P observadorP2P : _observadores) {
            observadorP2P.cambioEstado(_estado, _IPservidor, -1);
        }
        // envia y termina las transmisiones.
        _colaMensajes.flushYSalir();
        _colaMensajes = null;
    }

    @Override
    public AlmacenDescargas getAlmacenDescargas() {
       return _descargas;
    }

    public AlmacenSubidas getAlmacenSubidas() {
       return _subidas;
    }

    @Override
    public void nuevaConsulta(String cadena) {
                // crea el paquete consulta,
        Mensaje msj = new PeticionConsulta(cadena);
        msj.setDestino(_IPservidor, _puertoServidor);
       
        // si hemos conectado antes...
        // lo envia al servidor.
        if (_estado != EstadoP2P.CONECTADO){
            // TODO: notificar error
        }
        else
            this.addMensajeParaEnviar(msj);
    }

    @Override
    public void nuevaDescarga(Archivo a) {
           //si no lo tengo en los completos inicio la descarga
        if(_disco.getEnsamblador().nuevoArchivoTemporal(a)){
            _descargas.nuevaDescarga(a);
            pedirPropietariosaServidor(a);
            enviaListaArchivos();
        }
    }

    @Override
    public boolean estaConectadoAServidor() {
        return EstadoP2P.CONECTADO.equals(_estado);
    }

    @Override
    public void pausarDescarga(Archivo a) {
            // esta descarga no se continuará
       _descargas.pausaDescarga(a);
       
        for (ObservadorP2P obs: _observadores) {
            obs.pausaDescarga(this, a);
        }

    }

    @Override
    public void eliminarDescarga(Archivo a) {
        if (_disco.getEnsamblador().eliminarArchivoTemporal(a.getHash())) {
            // esta descarga se elimina
            _descargas.eliminaDescarga(a);

            for (ObservadorP2P obs : _observadores) {
                obs.eliminarDescarga(this, a);
            }
        }
    }

    public void pedirPropietariosaServidor(Archivo a) {
         // realizamos una consulta al servidor para saber los propietarios.
        PeticionDescarga peticion = new PeticionDescarga(a._nombre,a._hash);
        peticion.setDestino(_IPservidor, _puertoServidor);
        this.addMensajeParaEnviar(peticion);
    }

    @Override
    public void reanudarDescarga(Archivo a) {
        ListaArchivos l = GestorCompartidos.getInstancia().getGestorDisco().getListaArchivosTemporales();
        for(int i=0;i<l.size();i++){
            nuevaDescarga(l.get(i));
        }
    }

    @Override
    public void conectarAServidor(String ip, int puerto) {
 
        // empezamos el envio de paquetes
        _colaMensajes = new GestorMensajes(_red);
        _colaMensajes.start();
        
        // mostramos la conexión que intentamos establecer
//        _estado = EstadoP2P.NEGOCIANDO;
        _log.info("IP : "+ ip+ " Puerto: "+puerto);
        for (ObservadorP2P observadorP2P : _observadores) {
                observadorP2P.cambioEstado(_estado, ip, puerto);
        }
        // realiza la conexion. Envia los datos al servidor
        DatosCliente misDatos = new DatosCliente();
        // los datos se leen directamente del fichero de configuración
        ControlConfiguracionCliente config;
        try {
            config = ControlConfiguracionCliente.obtenerInstancia();
        } catch (ControlConfiguracionClienteException ex) {
            _log.error("error al obtener datos de cliente: " + ex.getMessage());
            _estado = EstadoP2P.DESCONECTADO;
            for (ObservadorP2P observadorP2P : _observadores) {
                observadorP2P.cambioEstado(_estado, ip, puerto);
            }
            return;
        }
        
        String nmb = config.obtenerPropiedad(PropiedadCliente.NOMBRE_USUARIO.obtenerLiteral());

        misDatos.setNombreUsuario(nmb);
        misDatos.setPuertoEscucha(_puertoCliente);

        _IPservidor = ip;
        _puertoServidor = puerto;
        misDatos.setDestino(_IPservidor, _puertoServidor);

        try {
            misDatos.setIP(InetAddress.getLocalHost().getHostAddress());
        } catch (Exception ex) {
            _log.fatal("error al obtener direccíón de red, posiblemente no hay" +
                       " tarjeta de red_ " + ex.getMessage());
            for (ObservadorP2P observadorP2P : _observadores) {
                observadorP2P.cambioEstado(_estado, ip, puerto);
            }
            return;
        }
        
        // envia mis datos
        addMensajeParaEnviar(misDatos);
        // comezamos a vigilar la conexion
        _vigilante = new VigilanteConexion();
        for (ObservadorP2P observadorP2P : _observadores) {
            _vigilante.agregarObservador(observadorP2P);
        }
        _vigilante.start();
    }

    @Override
    public synchronized void agregarObservador(ObservadorP2P obs) {
        _observadores.add(obs);
    }

    @Override
    public synchronized void eliminaObservador(ObservadorP2P obs) {
        _observadores.remove(obs);
    }

    void DescargaFichero(Archivo a, DatosCliente[] lista) {
        _descargas.respuestaPeticionDescarga(a, lista);
    }

    void actualizaDescarga(Tengo reciv) {
        _descargas.actualizaDescarga(reciv);
    }

//------------------------------------------------------------------------------
//------------------- Metodos del paquete --------------------------------------
//------------------------------------------------------------------------------
    /**
     * encola un mensaje para ser enviado por la red
     * @param msj
     */
    public void addMensajeParaEnviar(Mensaje msj) {
       _colaMensajes.addMensajeParaEnviar(msj);
    }

    /**
     * notifica que la conexión ha sido completada, hemos terminado con esto
     * asi que paramos el vigilante y informamos a los observadores
     */
    void conexionCompletada(){
        _vigilante.conexionCompletada();
        //_conectado = true;
        _estado = EstadoP2P.CONECTADO;
        
        for (ObservadorP2P observadorP2P : _observadores) {
            observadorP2P.cambioEstado(_estado, _IPservidor, _puertoCliente);
        }
        enviaListaArchivos();
        reanudarDescargas();
    }

//    boolean conectado() {
//        return _conectado;
//    }

    /**
     * envia al SERVIDOR la lista de archivos compatidos por este cliente.
     * este metodo se invoca durante la conexión, pero puede ser invocado
     * por ejemplo cuando termine la descarga de un fichero o se comparta algo
     * nuevo para actualizar la BBDD del servidor.
     */
    void enviaListaArchivos() {
            // aqui enviamos la lista de archivos compartidos que se sube
            // esto incluye tanto compartidos como a medias.

            // es un vector, por lo que hay que añadir los elementos en el consturctor
            // en un addall o algo
            ListaArchivos l = GestorCompartidos.getInstancia().getArchivosCompartidos();
            l.setDestino(_IPservidor, _puertoServidor);
            this.addMensajeParaEnviar(l);
    }

    void fragmentoDescargado(Fragmento f, Byte[] parte) {
        if(_disco.getEnsamblador().guardarFragmentoEnArchivo(f, parte)){
            if(_descargas.fragmentoDescargado(f)){
            //TODO MIRAR QUE HACER
   
            }
        }
    }

    void perdidaDeConexion(String ip) {
       if (ip.equals(_IPservidor)){
           // hemos perido la conexion con el servidor
           //_conectado = false;
           _estado = EstadoP2P.DESCONECTADO;
           for (ObservadorP2P observadorP2P : _observadores) {
               observadorP2P.cambioEstado(_estado, ip, -1);
           }
       }
       else {
            // emos perdido la conexion con un cliente
       }
    }

    public void reanudarDescargas() {
     ListaArchivos l = GestorCompartidos.getInstancia().getGestorDisco().getListaArchivosTemporales();
        for(int i=0;i<l.size();i++){
            nuevaDescarga(l.get(i));
        }
    }

    void resultadoBusqueda(RespuestaPeticionConsulta respuestaPeticionConsulta) {
        String cad = respuestaPeticionConsulta.getConsulta();
        Archivo[] lista = respuestaPeticionConsulta.getLista();
        
        for (ObservadorP2P obs : _observadores) {
            obs.resultadosBusqueda(this,cad, lista);
        }

    }

//------------------------------------------------------------------------------
//------------------- Metodos del privados -------------------------------------
//------------------------------------------------------------------------------
    
}
