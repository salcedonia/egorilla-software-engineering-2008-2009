package interfaz.trafico;

//************************************************************************************//
/**
 * Clase que proporciona los métodos necesarios para el control del panel 
 * de tráfico.
 * 
 * @author Victor
 * @author S@L-c
 */
public class ControlPanelTrafico {

	// ATRIBUTOS
	@SuppressWarnings("unused")
	private GUIPanelTrafico _panelTrafico;

//	************************************************************************************//
	/**
	 * Constructor de la clase ControlPanelTrafico.
	 * 
	 * @param panelTrafico GUI asociado al control.
	 */
	public ControlPanelTrafico(GUIPanelTrafico panelTrafico){
		
		_panelTrafico = panelTrafico;
	}
	
//	************************************************************************************//
	/**
	 * Recoge la acción producida sobre el panel de tráfico.
	 * 
	 * @param accion Acción producida.
	 * @param parametros Parametros asociados a la acción.
	 */
	public void tratarAccion(AccionPanelTrafico evento, Object parametros){
		
	}
}