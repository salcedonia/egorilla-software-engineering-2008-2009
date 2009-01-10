package interfaz.estadisticas;

//************************************************************************************//
/**
 * Clase que proporciona los métodos necesarios para el control del panel 
 * de estadísticas.
 * 
 * @author Victor
 * @author S@L-c
 */
public class ControlPanelEstadisticas {

	// ATRIBUTOS
	@SuppressWarnings("unused")
	private GUIPanelEstadisticas _panelEstadisticas;

//	************************************************************************************//
	/**
	 * Constructor de la clase ControlPanelEstadisticas.
	 * 
	 * @param panelEstadisticas GUI asociado al control.
	 */
	public ControlPanelEstadisticas(GUIPanelEstadisticas panelEstadisticas){
		
		_panelEstadisticas = panelEstadisticas;
	}
	
//	************************************************************************************//
	/**
	 * Recoge la acción producida sobre el panel de estadisticas.
	 * 
	 * @param accion Acción producida.
	 * @param parametros Parametros asociados a la acción.
	 */
	public void tratarAccion(AccionPanelEstadisticas accion, Object parametros){
		
	}
}