package gui.grafica.trafico;

import peerToPeer.GestorP2P;
import peerToPeer.egorilla.GestorEgorilla;

/**
 * Controlador del panel de trafico.
 * 
 * @author Javier Salcedo
 */
public class ControladorPanelTrafico {

    /**
     * Gestor eGorilla de la aplicacion.
     */
    private GestorP2P _gestorEGorilla;
    
    /**
     * Constructor de la clase ControladorPanelTrafico.
     * 
     * @param gestorEGorilla Gestor eGorilla de la aplicacion.
     */
    public ControladorPanelTrafico(GestorP2P gestorEGorilla) {
        
        _gestorEGorilla = gestorEGorilla;
    }
    
    /**
     * Devuelve el GestorEGorilla de la aplicación.
     * 
     * @return El GestorEGorilla de la aplicación.
     */
    public GestorP2P getGestorEGorilla() {
        return _gestorEGorilla;
    }
}
