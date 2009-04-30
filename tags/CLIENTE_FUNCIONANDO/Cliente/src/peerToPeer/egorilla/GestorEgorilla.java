package peerToPeer.egorilla;

import datos.Archivo;
import datos.Fragmento;
import gestorDeConfiguracion.ControlConfiguracionCliente;
import gestorDeConfiguracion.ControlConfiguracionClienteException;
import gestorDeConfiguracion.ObservadorControlConfiguracionCliente;
import gestorDeConfiguracion.PropiedadCliente;
import gestorDeRed.GestorDeRed;
import gestorDeRed.NetError;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Properties;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import mensajes.Mensaje;
import mensajes.p2p.Altoo;
import mensajes.serverclient.DatosCliente;
import mensajes.serverclient.ListaArchivos;
import mensajes.serverclient.PeticionConsulta;
import mensajes.serverclient.PeticionDescarga;
import peerToPeer.GestorClientes;
import gestorDeFicheros.*;
import java.net.*;
import java.util.Enumeration;
import java.util.Iterator;
import mensajes.p2p.Tengo;
import mensajes.serverclient.RespuestaPeticionConsulta;
import peerToPeer.descargas.AlmacenDescargas;
import peerToPeer.descargas.Descargador;

/**
 *
 * @author Luis Ayuso, José Miguel Guerrero
 * @author Modificado por Javier Sánchez
 * 
 * Esta clase utiliza parametros de configuracion por tanto va a ser observadora de ControlConfiguracionCliente 
 * y sera notificada cuando cambie la configuracion dando un tratamiento adecuado al cambio (o no hacer nada).
 * //TODO: Dar tratamiento a los cambios en la configuracion del cliente (si asi se desea).
 * 
 */
public class GestorEgorilla extends Thread implements ObservadorControlConfiguracionCliente{
    
    private Queue<Mensaje> _colaSalida;
    private GestorDeRed<Mensaje> _gestorDeRed;
    private GestorDisco _gestorDisco;
    private GestorClientes _gestorClientes;
    private AlmacenDescargas _almacenDescargas;
    private Descargador _descargador;
    private boolean _conectado;
    private String  _serverIP;
    private int  _serverPort;
    private boolean _doP2P;  
    /**
    * Estructura de datos para almacenar los observadores sobre este objeto.
    */
    private ArrayList<ObservadorGestorEgorilla> _listaObservadores;

    /**
     * Constructor de la clase GestorEgorilla.
     * 
     * @param gestorDeRed Gestor de Red de la aplicación.
     */
    public GestorEgorilla(GestorDeRed<Mensaje> gestorDeRed, GestorDisco disco) {
        
        _colaSalida = new LinkedList<Mensaje>();
        _gestorDeRed = gestorDeRed; 
        _gestorClientes = new GestorClientes();
        _listaObservadores= new ArrayList<ObservadorGestorEgorilla>();
        _doP2P = false;

        //En donde se instancia gestorSubidas? No lo veo
        //_gestorSubidas = new GestorSubidas( this, _gestorDisco );
        
        _gestorDisco = disco;
        //inicializo la variable
        _conectado = false;

        _almacenDescargas=new AlmacenDescargas();
        
        _descargador=new Descargador(this, _almacenDescargas);
        _descargador.start();
    } 
    
    /**
     * realiza la concexion con el servidor egorilla. 
     * este metodo es sincrono, por lo que estaremos dentro de el hasta que 
     * se realice toda la comunicaci�n, ya sea correcta o erronea.
     * 
     * @return si la conexion ha sido satisfactoria
     */
    public void conectaServidor(String ipServidor, int puertoServidor) throws ControlConfiguracionClienteException{
        
        // realiza la conexion. Envia los datos al servidor
        DatosCliente misDatos = new DatosCliente();
        
        // los datos se leen directamente del fichero de configuración
        ControlConfiguracionCliente config = ControlConfiguracionCliente.obtenerInstancia();
        
        int puerto = Integer.parseInt(config.obtenerPropiedad(PropiedadCliente.PUERTO.obtenerLiteral()));
        String nmb = config.obtenerPropiedad(PropiedadCliente.NOMBRE_USUARIO.obtenerLiteral());
        
        
        misDatos.setNombreUsuario(nmb); 
        misDatos.setPuertoEscucha(puerto);
        
       _serverPort = puertoServidor;
       _serverIP = ipServidor;
        misDatos.setDestino(_serverIP, _serverPort);
        try {
            misDatos.setIP(InetAddress.getLocalHost().getHostAddress());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        // envia mis datos
        addMensajeParaEnviar(misDatos);
        comienzaP2P();       
    }

    /**
     * Devuelve la IP del servidor al que estamos conectados.
     * 
     * @return La IP del servidor al que se conecta.
     */
    public String getServerIP() {
     
        return _serverIP;
    }

    /**
     * Devuelve la IP del servidor al que estamos conectados.
     * 
     * @return La IP del servidor al que se conecta.
     */
    public int getServerPort() {
        
        return _serverPort;
    }
     
    /** 
     * notifica que esta conectado a partir de este momento
     */
    void conectado() {
        _conectado = true;
        notificarConexionCompletada(_serverIP, _serverPort);
    }
    
    /**
     *desconecta y termina con el p2p
     */
   public void desconectar(){
        // TODO: termina esto
        
        // envia alto a todos los peers con descargas pendientes a los que 
        // estemos subiendo fragmentos
        
        
       // envía altoo al servidor.
        Altoo alto =  new Altoo();
        alto.setDestino(_serverIP, _serverPort);
        this.addMensajeParaEnviar(alto);
        this._conectado = false;
        
        // finaliza el p2p.
        this._doP2P = false;

        //Notificar el evento a los observadores
        this.notificarDesconexionCompletada();
    }

    /**
     * Devuelve el estado de la conexion (el valor de la variable conectado)
     * @return estado de la conexion.
     */
    public boolean estaConectadoAServidor(){
        return this._conectado;
    }
    
    /**
     * pone el p2p en modo escucha, de forma que se atiende a otros
     * clientes
     *
     */
    private void comienzaP2P(){
        
        _gestorDeRed.registraReceptor(new ServidorP2PEgorilla(this));
        _gestorDeRed.comienzaEscucha();
        _doP2P = true;
        this.start();
    }
    
    /**
     * se realiza una consulta, el m�todo es asincrono, asi que no espera respuesta
     * la ejecucion continua. 
     * La respuesta llegara a la parte serividora, la cual lo comunicara a sus listeners
     * 
     * @param query la consulta a hacer.
     */
    public void nuevaConsulta (String query){
        // crea el paquete consulta,
        Mensaje msj = new PeticionConsulta(query);
        msj.setDestino(_serverIP, _serverPort);
        
        // si hemos conectado antes...
        // lo envia al servidor.
        if (!_conectado){
            // TODO: notificar error
        }
        else
            this.addMensajeParaEnviar(msj);
    }
    
    /**
     * notifica la llegada de una consulta a los observadores
     * @param respuesta
     */
    void resultadoBusqueda(RespuestaPeticionConsulta respuesta) {
        String cad = respuesta.getConsulta();
        Archivo[] lista = respuesta.getLista();
        
        for (ObservadorGestorEgorilla obs : _listaObservadores) {
            obs.resultadosBusqueda(this,cad, lista);
        }
    }
    
    /**
     * se da la orden de comenzar una descarga.
     * esto dispara la colsulta de peers al servidor y 
     * despues comenzar�n las negociaciones con estos
     * 
     * @param a archivo a descargar
     */
    public void nuevaDescarga(Archivo a) {
        _gestorDisco.getEnsamblador().nuevoArchivoTemporal(a);
        _almacenDescargas.nuevaDescarga(a);
        this.pedirPropietariosaServidor(a);
    }

    public void pedirPropietariosaServidor (Archivo a){
        // realizamos una consulta al servidor para saber los propietarios.
        PeticionDescarga peticion = new PeticionDescarga(a._nombre,a._hash);
        peticion.setDestino(_serverIP, _serverPort);
        this.addMensajeParaEnviar(peticion);
    }

    /**
     * tras la respuesta del servidor, se comienza la descarga de un fichero desde 
     * los clientes indicados
     * 
     * @param a fichero a descargar
     * @param lista lista de clientes que lo contienen
     */
    public void DescargaFichero(Archivo a, DatosCliente[] lista) {
        _almacenDescargas.respuestaPeticionDescarga(a, lista);
    }
  
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
        l.setDestino(_serverIP, _serverPort);
        
        // y se envia
        if (!_conectado){
            // TODO: notificar error
        }
        else
            this.addMensajeParaEnviar(l);
    }
    
    /**
     * se encola un mensaje para darle salida.
     * los mensajes se encolan primero para poder controlar el flujo.
     * TODO mensaje de salida DEBE pasar por aqui
     * 
     * @param msj
     */
    public synchronized void addMensajeParaEnviar(Mensaje msj){
        _colaSalida.add(msj);
        
        this.notify();
    }

    public void actualizaDescarga(Tengo msj){
        _almacenDescargas.actualizaDescarga(msj);
    }

    public void fragmentoDescargado(Fragmento f, Byte[] datos){
        //indicamos primero al gestor de disco que se ha descargado un fragmento
        if(!_gestorDisco.getEnsamblador().guardarFragmentoEnArchivo(f, datos)){
            //TODO LANZAR ERROR
            System.out.println("ERROR GUARDAR FRAGMENTO");
        }
        //informamos al almacen del fragmento descargado
        if(!_almacenDescargas.fragmentoDescargado(f)){
            //TODO LANZAR ERROR
            System.out.println("ERROR FRAGMENTO DECARGADO ALMACEN");
        }
    }

    public AlmacenDescargas getAlmacenDescargas(){
        return _almacenDescargas;
    }

    
    @Override
    public synchronized void run() {
        this.setName("Gestor eGorilla");
        this.setPriority(MAX_PRIORITY);

        try {
            while (_doP2P) {

                // por cada mensaje al que se le deba dar salida, se le da.
                if (!_colaSalida.isEmpty()) {
                    Mensaje msj = _colaSalida.poll();
                    _gestorDeRed.envia(msj, msj.ipDestino(), msj.puertoDestino());
                }

                if (_colaSalida.isEmpty())
                    this.wait();
                else
                    this.wait(100);

            }
        } catch (NetError ex) {   
            // TODO: aki  propaga error, comunica a injterfaz y esas cosas
        } catch (InterruptedException in){
        }
    }

    //
    //-------------------------------------------------
    //Implementacion de los metodos equivalentes a los de la interfaz "Observable"
    //-------------------------------------------------
    
    /**
     * añade un observador que sera notificado con los eventos del modulo
     * @param obs el observador
     */
    public void agregarObservador(ObservadorGestorEgorilla obs){
        int iIndice=_listaObservadores.indexOf(obs);
        //Aniado el observador solo si no esta en la estructura de observadores
        if ( iIndice == -1 )
            _listaObservadores.add(obs);
    }

    /**
     * elimina un observador 
     * @param obs el observador a eliminar
     */
    public void eliminarObservador(ObservadorGestorEgorilla obs){
        int indice=_listaObservadores.indexOf(obs);
        _listaObservadores.remove(indice);
    }

    /**
     * Recorre todos los Observadores registrados para notificarles el que se ha producido
     * un cambio en el objeto GestorEgorilla: Conexion completada
     */
    public void notificarConexionCompletada(String ip, int port) {
        Iterator<ObservadorGestorEgorilla> iterador = _listaObservadores.iterator();
        while (iterador.hasNext()){
            iterador.next().conexionCompletada(this, ip, port);
        }
    }

    /**
     * Recorre todos los Observadores registrados para notificarles el que se ha producido
     * un cambio en el objeto GestorEgorilla: Desconexion completada
     */
    public void notificarDesconexionCompletada() {
        Iterator<ObservadorGestorEgorilla> iterador = _listaObservadores.iterator();
        while (iterador.hasNext()){
            iterador.next().desconexionCompletada(this);
        }
    }

    //
    //-------------------------------------------------
    //Implementacion de la interfaz ObservadorControlConfiguracionCliente
    //-------------------------------------------------
    /**
     * Este metodo es llamado cuando cambia la configuracion del cliente
     * @param obj Objeto ControlConfiguracionCliente
     * @param propiedades Conjunto de propiedades que ha cambiado
     */
    @Override
    public void cambioEnPropiedades(ControlConfiguracionCliente obj, Properties propiedades) {
        String sNuevoValor;
        for (Enumeration e = propiedades.propertyNames(); e.hasMoreElements() ; ) {
            // Obtenemos el objeto
            Object objeto = e.nextElement();
            if (objeto.toString().compareTo (PropiedadCliente.PUERTO.obtenerLiteral()) == 0){
                sNuevoValor = propiedades.getProperty(objeto.toString());
                //TODO: Lo que se vaya a hacer cuando cambia de valor esta propiedad
            }
        }           
    }
}
