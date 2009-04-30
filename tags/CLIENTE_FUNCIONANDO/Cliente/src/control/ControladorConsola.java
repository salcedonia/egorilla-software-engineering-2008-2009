package control;

import datos.Archivo;
import gestorDeRed.GestorDeRed;
import mensajes.Mensaje;
import peerToPeer.egorilla.GestorEgorilla;

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
     * Gestor de red de la aplicación.
     */
    private GestorDeRed<Mensaje> _gestorDeRed;

    /**
     * Gestor eGorilla.
     */
    private GestorEgorilla _gestorEGorilla;
    
    /**
     * Constructor de la clase.
     * 
     * @param gestorDeRed Gestor de red.
     * @param gestorDeDescargas Gestor de descargas.
     * @param gestorEgorilla Gestor eGorilla.
     */
    public ControladorConsola(GestorDeRed<Mensaje> gestorDeRed, GestorEgorilla gestorEgorilla) {
        
        _gestorDeRed = gestorDeRed;
        _gestorEGorilla = gestorEgorilla;
    }

    public GestorEgorilla getGestorEGorilla() {
    
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
        
        _gestorEGorilla.conectaServidor(IP, puerto);
    }

    /**
     * Cierra la conexion con el servidor.
     */
    public void peticionDesconexionDeServidor() {
        
        _gestorEGorilla.desconectar();
        _gestorDeRed.terminaEscucha();
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
}
