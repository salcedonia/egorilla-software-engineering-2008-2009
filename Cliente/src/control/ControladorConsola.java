package control;

import datos.Archivo;
import gestorDeRed.GestorDeRed;
import gui.consola.GUIConsola;
import mensajes.Mensaje;
import peerToPeer.descargas.GestorDescargas;
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
     * Gestor de descargas de la aplicación.
     */
    private GestorDescargas _gestorDeDescargas;
    /**
     * Gestor eGorilla.
     */
    private GestorEgorilla _gestorDeEgorilla;

    /** Vista asociada a este controlador. Mediante esta referencia el controlador
     * puede comunicarse con la Vista.
     */
    public GUIConsola _guiConsola;
    
    /**
     * Constructor de la clase.
     * 
     * @param gestorDeRed Gestor de red.
     * @param gestorDeDescargas Gestor de descargas.
     * @param gestorEgorilla Gestor eGorilla.
     */
    public ControladorConsola(GestorDeRed<Mensaje> gestorDeRed, GestorDescargas gestorDeDescargas, GestorEgorilla gestorEgorilla) {
        
        _gestorDeRed = gestorDeRed;
        _gestorDeDescargas = gestorDeDescargas;
        _gestorDeEgorilla = gestorEgorilla;
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
     * Realiza la conexion a un servidor de este cliente.
     *
     * @throws java.io.IOException
     */
    public void peticionConexionAServidor(String IP, int puerto) throws Exception {
        _guiConsola.mostrarMensaje("\nConectando a servidor....");
        if ( !_gestorDeEgorilla.estaConectadoAServidor() ) {
            _gestorDeEgorilla.conectaServidor(IP, puerto);
        } else {
            _guiConsola.mostrarMensaje("Ya estas conectado a un servidor.\n");
        }
    }

    /**
     * Cierra la conexion con el servidor.
     */
    public void peticionDesconexionDeServidor() {
        _guiConsola.mostrarMensaje("\nDesconectando del servidor...");
        _gestorDeEgorilla.desconectar();

        // tambien acabamos con el p2p
        _gestorDeRed.terminaEscucha();
}

    /**
     * Pregunta al servidor por algun fichero con algunos datos proporcionados por
     * el cliente.
     *
     * //TODO: buscar algo mas que por el nombre
     *
     * @param cad nombre de fichero buscado
     */
    public void peticionBuscarFichero(String cad) {
        
        _gestorDeEgorilla.nuevaConsulta(cad);
    }

    /**
     * Da la orden para proceder a peticionDescargarFichero un fichero.
     *
     * @param hash El identificador unico de este fichero.
     */
    public void peticionDescargarFichero(String nmb, String hash) {
        
        _gestorDeEgorilla.nuevaDescarga(new Archivo(nmb, hash));
    }
}
