package gui.grafica.principal;

import gestorDeRed.GestorDeRed;
import mensajes.Mensaje;
import peerToPeer.egorilla.GestorEgorilla;

/**
 *
 * @author Javier Salcedo
 */
public class ControladorVentanaPrincipal {

    /**
     * Gestor de red de la aplicación.
     */
    private GestorDeRed<Mensaje> _gestorDeRed;
    /**
     * Gestor eGorilla.
     */
    private GestorEgorilla _gestorEGorilla;
    
    /**
     * Constructor de la clase ControladorGrafica.
     * 
     * @param gestorDeRed Gestor de red de la aplicación.
     * @param gestorEgorilla Gestor eGorilla.
     */
    public ControladorVentanaPrincipal(GestorDeRed<Mensaje> gestorDeRed, GestorEgorilla gestorEgorilla) {

        _gestorDeRed = gestorDeRed;
        _gestorEGorilla = gestorEgorilla;
    }
    
    /**
     * Realiza una petición de conexión al servidor al GestorEGorilla.
     * 
     * @param IP IP del servidor.
     * @param puerto Puerto del servidor.
     * @throws java.lang.Exception
     */
    public void peticionConexionAServidor(String IP, int puerto) throws Exception {

        _gestorEGorilla.conectaServidor(IP, puerto);
    }

    /**
     * Realiza una petición de desconexión al servidor al GestorEGorilla.
     */
    public void peticionDeDesconexionDeServidor() {

        _gestorEGorilla.desconectar();
        _gestorDeRed.terminaEscucha();
    }
    
    /**
     * Devuelve el GestorEGorilla de la aplicación.
     * 
     * @return El GestorEGorilla de la aplicación.
     */
    public GestorEgorilla getGestorEGorilla() {

        return _gestorEGorilla;
    }
}
