package interfaz.servidores;

//************************************************************************************//
/**
 * Clase que proporciona los métodos necesarios para el control del panel 
 * de servidores.
 * 
 * @author Victor
 * @author S@L-c
 */
public class ControlPanelServidores {

	// ATRIBUTOS
	@SuppressWarnings("unused")
	private GUIPanelServidores _panelServidores;
		
//	************************************************************************************//
	/**
	 * Constructor de la clase ControlPanelServidores.
	 * 
	 * @param panelServidores GUI asociado al control.
	 */
	public ControlPanelServidores(GUIPanelServidores panelServidores){
		
		_panelServidores = panelServidores;
	}
	
//	************************************************************************************//
	/**
	 * Recoge la acción producida sobre el panel de servidores.
	 * 
	 * @param accion Acción producida.
	 * @param parametros Parametros asociados a la acción.
	 */
	public void accionPanelServidores(AccionPanelServidores accion, Object parametros){
            
            switch(accion){
		
                case AÑADIR_SERVIDOR:
                    _panelServidores.tratarEventos(EventoPanelServidores.MOSTRAR_SERVIDOR_PUERTO_IP_EN_PANEL, parametros);
                    
                    
		
            }
        }
            
}
