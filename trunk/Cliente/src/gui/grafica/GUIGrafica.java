package gui.grafica;

import control.ControladorGrafica;
import gui.grafica.principal.GUIVentanaPrincipal;

/**
 * Clase que se encarga de gestionar la gui de la aplicación en modo gráfico.
 * 
 * @author Javier Salcedo Gómez
 */
public class GUIGrafica {
    
    /**
     * Constructor de la clase GUIGrafica.
     * 
     * @param controlador Controlador de la aplicacion en modo grafica.
     */
    public GUIGrafica(ControladorGrafica controlador){  
        
        new GUIVentanaPrincipal(controlador);
    }
}
