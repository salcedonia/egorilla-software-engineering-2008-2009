package gui.grafica.compartidos;

//************************************************************************************//
/**
 * Clase que proporciona los métodos necesarios para el control del panel 
 * de compartidos.
 * 
 * @author Victor
 * @author S@L-c
 */
public class ControlPanelCompartidos {

	// ATRIBUTOS
	@SuppressWarnings("unused")
	private GUIPanelCompartidos _panelCompartidos;

//	************************************************************************************//
	/**
	 * Constructor de la clase ControlPanelCompartidos.
	 * 
	 * @param panelCompartidos GUI asociado al control.
	 */
	public ControlPanelCompartidos(GUIPanelCompartidos panelCompartidos){
		
		_panelCompartidos = panelCompartidos;
	}
	
//	************************************************************************************//
    /**
     * Recoge el evento de acción que se ha producido sobre el panel de compartidos.
     * 
     * @param accion Acción asociada.
     * @param params Parámetros asociados a la acción.
     */
	public void accionPanelCompartidos(AccionPanelCompartidos accion, Object parametros){
		
	}
}