package gui.consola;

import datos.Archivo;
import gestorDeFicheros.GestorCompartidos;
import peerToPeer.GestorP2P;

/**
 * Controlador de la aplicación en modo consola. 
 * Se encarga de recibir los eventos producidos en la interfaz y se los 
 * notifica a la lógica de la aplicación para que ésta actúe en consecuencia.
 * 
 * @author Luis Ayuso, Ivan Munsuri, Javier Salcedo, Jose Miguel Guerrero
 * Modificado por Javier Sánchez.
 */
public class ControladorConsola {

    /**
     * Gestor eGorilla.
     */
    private GestorP2P _gestorEGorilla;
    
    /**
     * Constructor de la clase.
     * 
     * @param gestorDeRed Gestor de red.
     * @param gestorDeDescargas Gestor de descargas.
     * @param gestorEgorilla Gestor eGorilla.
     */
    public ControladorConsola(GestorP2P gestorEgorilla) {
     
        _gestorEGorilla = gestorEgorilla;
    }

    /**
     * Devuelve el GestorEGorilla de la aplicación.
     * 
     * @return El GestorEGorilla de la aplicación.
     */
    public GestorP2P getGestorEGorilla() {
    
        return _gestorEGorilla;
    }

    /**
     * Realiza la conexion a un servidor de este cliente.
     * 
     * @param IP IP del servidor.
     * @param puerto Puerto del servidor. 
     * 
     * @throws java.io.IOException
     */
    public void peticionConexionAServidor(String IP, int puerto) throws Exception {
        _gestorEGorilla.conectarAServidor(IP, puerto);
    }

    /**
     * Cierra la conexion con el servidor.
     */
    public void peticionDesconexionDeServidor() {
        
        _gestorEGorilla.desconectar();
}

    /**
     * Pregunta al servidor por algun fichero con algunos datos proporcionados por
     * el cliente.
     *
     * @param nombre nombre de fichero buscado
     */
    public void peticionBuscarFichero(String nombre) {
        
        _gestorEGorilla.nuevaConsulta(nombre);
    }

    /**
     * Da la orden para proceder a peticionDescargarFichero un fichero.
     *
     * @param Archivo El archivo que se pretende descargar.
     */
    public void peticionDescargarFichero(Archivo archivo) {
        
        _gestorEGorilla.nuevaDescarga(archivo);
    }
    
        /**
     * Devuelve la lista de todos los archivos compartidos del cliente. Para ello pregunta
     * al Gestor de compartidos que es el que tiene toda la informacion necesaria.
     * 
     * @return La lista de archivos compartidos del cliente.
     */
    void peticionListarTodosCompartidos(){
    
        GestorCompartidos.getInstancia().getGestorDisco().getManejarListaArchivos().recorrerListaArchivos(GestorCompartidos.getInstancia().getGestorDisco().getListaArchivosTodos());       
    }

    /**
     * Devuelve la lista de archivos compartidos completos del cliente. Para ello pregunta
     * al Gestor de compartidos y este a su vez llama al gestor de disco para 
     * preguntarle todo lo necesario.
     * 
     * @return La lista de archivos compartidos completos del cliente.
     */
    void peticionListarCompartidosCompletos() {
        
        GestorCompartidos.getInstancia().getGestorDisco().getManejarListaArchivos().recorrerListaArchivos(GestorCompartidos.getInstancia().getGestorDisco().getListaArchivosCompletos());
    }
    
    /**
     * Devuelve la lista de archivos compartidos incompletos del cliente. Para ello pregunta
     * al Gestor de compartidos y este a su vez llama al gestor de disco para 
     * preguntarle todo lo necesario.
     * 
     * @return La lista de archivos compartidos incompletos del cliente.
     */
    void peticionListarCompartidosIncompletos() {
        
        GestorCompartidos.getInstancia().getGestorDisco().getManejarListaArchivos().recorrerListaArchivos(GestorCompartidos.getInstancia().getGestorDisco().getListaArchivosTemporales());
    }
}
