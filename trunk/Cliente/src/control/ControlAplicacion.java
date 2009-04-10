package control;

import datos.Archivo;
import gestorDeFicheros.GestorDisco;
import gestorDeRed.GestorDeRed;
import gestorDeRed.TCP.GestorDeRedTCPimpl;
import gui.GUIConsola;
import java.io.*;
import mensajes.Mensaje;
import peerToPeer.descargas.GestorDescargas;
import peerToPeer.egorilla.GestorEgorilla;
import peerToPeer.egorilla.ObservadorGestorEgorilla;

/**
 * Un control rudimentario para la aplicacion. Responde a las llamadas de 
 * la interfaz de forma imperativa.
 * 
 * implementa la interfacee observador gestor egorilla para estar al tanto de
 * los eventos
 * 
 * @author Luis Ayuso, Ivan Munsuri, Javier Salcedo
 */
public class ControlAplicacion implements ObservadorGestorEgorilla {

    // ATRIBUTOS
    private static boolean _conectado = false;
    
    private  GestorDeRed<Mensaje> _red;
    private  GestorDescargas      _descargas;
    private  GestorEgorilla       _egorilla;
    
    private  GUIConsola _gui;
    
    
    public ControlAplicacion(int puerto, String compartidos){
        _red = new GestorDeRedTCPimpl<Mensaje>(puerto);
        this.compartidos(compartidos);
        
      GestorDisco disco = new GestorDisco();  
      _descargas = new GestorDescargas(disco);
      _egorilla = new GestorEgorilla(_descargas, _red);
          _egorilla.agregarObservador(this);
    }

    public void regristraGUI(GUIConsola gui) {
      _gui = gui;
    }
    
    /**
     * Configura el gestor de archivos compartidos del cliente a partir del 
     * nombre del directorio que el usuario del Cliente eGorilla comparte.
     * 
     * @param nombreDirectorio
     */
    private void compartidos(String nombreDirectorio) {
        // TODO: inicializa el gestor de compartidos o lo que sea
    }
    
    /**
     * Indica si estamos conectados a un servidor o no.
     * 
     * @return Verdadero si estamos conectados a un servidor y falso en caso
     * contrario.
     */
    public boolean conectado() {
        return _conectado;
    }

    /**
     * Realiza la conexion a un servidor de este cliente.
     *
     * @throws java.io.IOException
     */
    public void conectar(String IP, int puerto) throws Exception {
        if (!_conectado)
            _egorilla.conectaServidor(IP, puerto);
        else
            _gui.mostrarMensaje("ya estas conectado a " + _egorilla.getServerIP() + "\n");
    }

    /**
     * Cierra la conexion con el servidor.
     */
    public void close() {
   
        _egorilla.desconectar();
        
        // tambien acabamos con el p2p
        _red.terminaEscucha();
        _conectado = false;
    }

    /**
     * Pregunta al servidor por algun fichero con algunos datos proporcionados por
     * el cliente.
     *
     * //TODO: buscar algo mas que por el nombre
     *
     * @param cad nombre de fichero buscado
     */
    public void consultar(String cad) {
        
       _egorilla.nuevaConsulta(cad);
    }

    /**
     * Da la orden para proceder a bajar un fichero.
     *
     * @param hash El identificador unico de este fichero.
     */
    public void bajar(String nmb,String hash) {
        _egorilla.nuevaDescarga(new Archivo(nmb, hash));
    }

    
   //--------------------------------------------------------------------------
   //           INTERFACE OBSERVADOREGORILLA
   //--------------------------------------------------------------------------
    
    public void conexionCompleta(String ip, int port) {
        this._conectado = true;
        
        // notifica a la gui:
        _gui.mostrarMensaje ("Conexión satisfactoria con Servidor "+
                             ip+ ":" +port  +"\n");
    }

    public void resultadosBusqueda(String cad,  Archivo[] lista) {
        
         // notifica a la gui:
        _gui.mostrarMensaje ("\nresultados de la busqueda: " + cad);
        _gui.mostrarMensaje ("================================================");
        
        if (lista.length > 0){
        for (Archivo archivo : lista) {
            _gui.mostrarMensaje(archivo.toString());
        }}
        else
            _gui.mostrarMensaje("no hubo resultados! \n");   
    }

    public void finDescarga() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void perdidaConexion() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
