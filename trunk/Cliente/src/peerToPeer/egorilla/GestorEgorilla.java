/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

package peerToPeer.egorilla;

import datos.Archivo;
import datos.Fragmento;
import gestorDeConfiguracion.ControlConfiguracionCliente;
import gestorDeConfiguracion.ControlConfiguracionClienteException;
import gestorDeRed.GestorDeRed;
import gestorDeRed.NetError;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import mensajes.Mensaje;
import mensajes.p2p.Altoo;
import mensajes.p2p.HolaQuiero;
import mensajes.serverclient.DatosCliente;
import mensajes.serverclient.ListaArchivos;
import mensajes.serverclient.PeticionConsulta;
import mensajes.serverclient.PeticionDescarga;
import peerToPeer.GestorClientes;
import peerToPeer.descargas.GestorDescargas;

/**
 *
 * @author Luis Ayuso, José Miguel Guerrero
 */
public class GestorEgorilla extends Thread{
    
    private Queue<Mensaje> _colaSalida;
   
    private GestorDescargas _gestorDescargas;
    private GestorDeRed<Mensaje> _gestorDeRed;
    
    
    private GestorClientes _gestorClientes;
    
    private boolean _conectado;
    private String  _serverIP;
    private int  _serverPort;
    private boolean _doP2P;

    private ArrayList<ObservadorGestorEgorilla> _listaObservadores;

    public GestorEgorilla(GestorDescargas gd, GestorDeRed<Mensaje> gr) {
        _colaSalida = new LinkedList<Mensaje>();
        _gestorDescargas = gd;
        _gestorDeRed = gr; 
        _gestorClientes = new GestorClientes();
        _listaObservadores= new ArrayList<ObservadorGestorEgorilla>();
        _doP2P = false;
    }
    
    /**
     * realiza la concexion con el servidor egorilla. 
     * este metodo es sincrono, por lo que estaremos dentro de el hasta que 
     * se realice toda la comunicaci�n, ya sea correcta o erronea.
     * 
     * @return si la conexion ha sido satisfactoria
     */
    public void conectaServidor(String ipServidor, int puertoServidor) throws ControlConfiguracionClienteException{
        // realiza la conexion.
        // para ello envia los datos al servidor
        DatosCliente misDatos = new DatosCliente();
        
        // los datos se leen directamente del fichero de configuración
        ControlConfiguracionCliente config = ControlConfiguracionCliente.obtenerInstanciaDefecto();
        
        int puerto = Integer.parseInt(config.obtenerPropiedad("Puerto"));
        String nmb = config.obtenerPropiedad("NmbUsuario");
        
        misDatos.setNombreUsuario(nmb); 
        misDatos.setPuertoEscucha(puerto);
        
       _serverPort = puertoServidor;
       _serverIP = ipServidor;
        misDatos.setDestino(_serverIP, _serverPort);
        
        // envia mis datos
        this.addMensajeParaEnviar(misDatos);
        
        this.comienzaP2P();
    }
    
    /** 
     * notifica que esta conectado a partir de este momento
     */
    void conectado() {
        _conectado = true;
        //TODO: MOLARIA AKI UN OBSERVADOR Y DECIRSELO A TO DIOS???
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
    }
    
    
    /**
     * pone el p2p en modo escucha, de forma que se atiende a otros
     * clientes
     *
     */
    private void comienzaP2P(){
        
        _gestorDeRed.registraReceptor(new ServidorP2PEgorilla(this, _gestorDescargas));
        _gestorDeRed.comienzaEscucha();
        _doP2P = true;
        this.start();
    }
    
    
    
    /**
     * se realiza una consulta, el m�todo es asincrono, as� que no espera respuesta
     * la ejecuci�n continua. 
     * La respuesta llegara a la parte serividora, la cual lo comunicar� a sus listeners
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
     * Se indica que este fichero se transmitira a este peer. 
     * 
     * 
     * @param a
     * @param ip
     * @param puerto
     * @param fragmentos
     */
    public void nuevaSubida(Archivo a, String ip, int puerto,ArrayList<Fragmento> fragmentos){
        //TODO:
    }
           
    /**
     * se da la orden de comenzar una descarga.
     * esto dispara la colsulta de peers al servidor y 
     * despues comenzar�n las negociaciones con estos
     * 
     * @param a archivo a descargar
     */
    public void nuevaDescarga(Archivo a) {
        _gestorDescargas.nuevaDescargaPendiente(a);
        
        // realizamos una consulta al servidor.
        // necesitamos 
        PeticionDescarga peticion = new PeticionDescarga(a._nombre,a._hash);
        
        
    }

    /**
     * tras la respuesta del servidor, se comienza la descarga de un fichero desde 
     * los clientes indicados
     * 
     * @param a fichero a descargar
     * @param lista lista de clientes que lo contienen
     */
    public void DescargaFichero(Archivo a, DatosCliente[] lista) {
        
        for (DatosCliente cliente : lista) {
            _gestorClientes.addClienteDescarga(cliente.getIP(), a);
        }
        _gestorDescargas.completaInformacion(a._hash, lista);
                
        // antes de nada, debo mirar si estoy entre los propietarios del
        // fichero, no quiero hablar conmigo mismo
        Vector<DatosCliente> sinMi = new Vector<DatosCliente>();
        for (DatosCliente cliente : lista) {
            // si no soy yo lo agrego 
            sinMi.add(cliente);
        }
        
        // ahora se envia el HolaQuiero a todos los clientes que lo tienen
        for (Iterator<DatosCliente> it = sinMi.iterator(); it.hasNext();) {
            DatosCliente cliente = it.next();
         
            HolaQuiero q = new HolaQuiero(a);
            q.setDestino(cliente.getIP(),cliente.getPuertoEscucha());
            this.addMensajeParaEnviar(q);
        }
    }
  
    /**
     * envia al SERVIDOR la lista de archivos compatidos por este cliente.
     * este metodo se invoca durante la conexión, pero puede ser invocado
     * por ejemplo cuando termine la descarga de un fichero o se comparta algo
     * nuevo para actualizar la BBDD del servidor.
     */
    void enviaListaArchivos() {
        // TODO: aqui enviamos la lista de archivos compartidos que se sube
        // esto incluye tanto compartidos como a medias.
        
        ListaArchivos l = new ListaArchivos();
        l.setDestino(_serverIP, _serverPort);
        
        // es un vector, por lo que hay que añadir los elementos en el consturctor
        // en un addall o algo
        
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
//        if (!this.isAlive())
//            this.start();
        this.notify();
    }
    
    @Override
    public synchronized void run(){
            try {
                while (_doP2P){
                
                    // por cada mensaje al que se le deba dar salida, se le da.
                    while (!_colaSalida.isEmpty()) {
                        Mensaje msj = _colaSalida.poll();
                        _gestorDeRed.envia(msj, msj.ipDestino(), msj.puertoDestino());
                    }
                try {

                    this.wait();

                    //TODO: añadiendo una espera aqui se retrasa la salida  reduce el
                    // trafico this.wait();
                } catch (InterruptedException ex) {
                   // continua
                }

                 //TODO: añadiendo una espera aqui se retrasa la salida  reduce el 
                // trafico this.wait();
                }
            }   catch (NetError ex) {
                // TODO: gestor de errores. hay no se ha podido hablar con quien dices
                    Logger.getLogger(GestorEgorilla.class.getName()).log(Level.SEVERE, null, ex);
            }
    }

    //--------- metodos de la lista de observadores

    public void agregarObservador(ObservadorGestorEgorilla obs){
        _listaObservadores.add(obs);
    }

    public void eliminarObservador(ObservadorGestorEgorilla obs){
        int indice=_listaObservadores.indexOf(obs);
        _listaObservadores.remove(indice);
    }

}
