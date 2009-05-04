package gui.grafica.servidores;

import peerToPeer.egorilla.GestorEgorilla;

/**
 * Controlador de la pestania de servidores.
 * 
 * @author Javier Salcedo
 */
public class ControladorPanelServidores {

    /**
     * GestorEGorilla de la aplicacion.
     */
    private GestorEgorilla _gestorEGorilla;
    
    /**
     * Constructor de la clase ControladorPanelServidores.
     * 
     * @param gestorEGorilla Gestor eGorilla de la aplicacion.
     */
    public ControladorPanelServidores(GestorEgorilla gestorEGorilla){
    
        _gestorEGorilla = gestorEGorilla;
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
}
