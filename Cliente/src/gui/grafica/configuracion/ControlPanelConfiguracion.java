package gui.grafica.configuracion;

//************************************************************************************//
/**
 * Clase que proporciona los métodos necesarios para el control del panel 
 * de configuración.
 * 
 * @author Victor
 * @author S@L-c
 */
public class ControlPanelConfiguracion {

	// ATRIBUTOS
	@SuppressWarnings("unused")
	private GUIPanelConfiguracion _panelConfiguracion;

//	************************************************************************************//
	/**
	 * Constructor de la clase ControlPanelConfiguracion.
	 * 
	 * @param panelConfiguracion GUI asociado al control.
	 */
	public ControlPanelConfiguracion(GUIPanelConfiguracion panelConfiguracion){
	
		_panelConfiguracion = panelConfiguracion;
	}
	
//	************************************************************************************//
	/**
	 * Recoge la acción producida sobre el panel de configuración.
	 * 
	 * @param accion Acción producida.
	 * @param parametros Parametros asociados a la acción.
	 */
	public void tratarAccion(int accion, Object parametros){
		
	}
}