package control;

import gestorDeRed.GestorDeRed;
import mensajes.Mensaje;
import peerToPeer.egorilla.GestorEgorilla;

/**
 * Controlador de la aplicación en modo gráfico. 
 * Se encarga de recibir los eventos producidos en la interfaz y se los 
 * notifica a la lógica de la aplicación para que ésta actúe en consecuencia.
 * 
 * @author Javier Salcedo
 */
public class ControladorGrafica {

    /**
     * Gestor de red de la aplicación.
     */
    private GestorDeRed<Mensaje> _gestorDeRed;
    /**
     * Gestor eGorilla.
     */
    private GestorEgorilla _gestorDeEgorilla;
        
    /**
     * Constructor de la clase ControladorGrafica.
     * 
     * @param gestorDeRed Gestor de red de la aplicación.
     * @param gestorEgorilla Gestor eGorilla.
     */
    public ControladorGrafica(GestorDeRed<Mensaje> gestorDeRed, GestorEgorilla gestorEgorilla){
    
        _gestorDeRed = gestorDeRed;
        _gestorDeEgorilla = gestorEgorilla;
    }

    /**
     * Comprueba si estamos conectados al servidor.
     * 
     * @return Verdadero si estamos conectados al servidor y falso en caso contrario.
     */
    public boolean conectado() {

        return _gestorDeEgorilla.estaConectadoAServidor();
    }

    /**
     * Devuelve el GestorEGorilla de la aplicación.
     * 
     * @return El GestorEGorilla de la aplicación.
     */
    public GestorEgorilla getGestorEGorilla() {
        
        return _gestorDeEgorilla;
    }

    /**
     * Realiza una petición al GestorEGorilla de una búsqueda de un fichero.
     * 
     * @param nombre Nombre del fichero a buscar.
     */
    public void peticionBuscarFichero(String nombre) {
        
        _gestorDeEgorilla.nuevaConsulta(nombre);
    }
    
    /**
     * Realiza una petición de conexión al servidor al GestorEGorilla.
     * 
     * @param IP IP del servidor.
     * @param puerto Puerto del servidor.
     * @throws java.lang.Exception
     */
    public void peticionConexionAServidor(String IP, int puerto) throws Exception {

        _gestorDeEgorilla.conectaServidor(IP, puerto);
    }
    
    /**
     * Realiza una petición de desconexión al servidor al GestorEGorilla.
     */
    public void peticionDeDesconexionDeServidor() {

        _gestorDeEgorilla.desconectar();
        _gestorDeRed.terminaEscucha();
    }
}
