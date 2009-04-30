package gui.grafica;

import control.ControladorGrafica;
import gestorDeConfiguracion.ControlConfiguracionClienteException;
import gui.grafica.principal.GUIVentanaPrincipal;

/**
 * Clase que se encarga de gestionar la gui de la aplicación en modo gráfico.
 * 
 * @author Javier Salcedo
 */
public class GUIGrafica {
    
    /**
     * Constructor de la clase GUIGrafica.
     * 
     * @param controlador Controlador de la aplicacion en modo grafica.
     */
    public GUIGrafica(ControladorGrafica controlador) throws ControlConfiguracionClienteException{  
        
        new GUIVentanaPrincipal(controlador);
    }
}
