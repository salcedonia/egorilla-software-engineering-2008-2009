package gui.grafica.trafico;

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
    private GestorEgorilla _gestorEGorilla;
    
    /**
     * Constructor de la clase ControladorPanelTrafico.
     * 
     * @param gestorEGorilla Gestor eGorilla de la aplicacion.
     */
    public ControladorPanelTrafico(GestorEgorilla gestorEGorilla) {
        
        _gestorEGorilla = gestorEGorilla;
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
