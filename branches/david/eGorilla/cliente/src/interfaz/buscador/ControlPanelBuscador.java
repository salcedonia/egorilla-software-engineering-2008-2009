package interfaz.buscador;

import logica.protocolo.respuestas.RespuestaABusqueda;
import control.ControlAplicacion;

//************************************************************************************//
/**
 * Clase que proporciona los métodos necesarios para el control del panel 
 * de buscador.
 * 
 * @author Victor
 * @author S@L-c
 */
public class ControlPanelBuscador {

	// ATRIBUTOS
	@SuppressWarnings("unused")
	private GUIPanelBuscador _panelBuscador;
	
//	************************************************************************************//
	/**
	 * Constructor de la clase ControlPanelBuscador.
	 * 
	 * @param panelBuscador GUI asociado al control.
	 */
	public ControlPanelBuscador(GUIPanelBuscador panelBuscador){
		
		_panelBuscador = panelBuscador;
	}

//	************************************************************************************//
	/**
	 * Recoge la acción producida sobre el panel de búsquedas.
	 * 
	 * @param accion Acción producida.
	 * @param parametros Parametros asociados a la acción.
	 */
	public void accionPanelBuscador(AccionPanelBuscador accion, Object parametros){
		
		switch(accion){
		
			case BUSCAR_ARCHIVO_SERVIDOR: 
				
				// Si estamos conectados
			    if(ControlAplicacion.getConectado()){
			
			        // El contenido del textbox
			        String consulta = (String) parametros;
			
			        if(consulta.length()!=0){
			
			        	// Recibimos la respuesta a la consulta
			        	RespuestaABusqueda respuesta = ControlAplicacion.peticionBusquedaArchivo(consulta);
		                
			        	if (respuesta.getLista().length > 0)
		                  
		                	_panelBuscador.tratarEvento(EventoPanelBuscador.MOSTRAR_RESULTADO_BUSQUEDA, respuesta.getLista());
		                else{
		                	
		                	// Mostramos la lista vacía
		                	_panelBuscador.tratarEvento(EventoPanelBuscador.MOSTRAR_RESULTADO_BUSQUEDA, respuesta.getLista());
			                  
		                	// Mostramos el mensaje informativo
		                	_panelBuscador.tratarEvento(EventoPanelBuscador.MOSTRAR_MENSAJE_BUSQUEDA_SIN_COINCIDENCIAS, null);
		                }
			        }
			        else
			        	_panelBuscador.tratarEvento(EventoPanelBuscador.MOSTRAR_ERROR_NOMBRE_NO_INTRODUCIDO, null);
			    }
			    else			
			    	_panelBuscador.tratarEvento(EventoPanelBuscador.MOSTRAR_ERROR_NO_CONECTADO_SERVIDOR, null);
				
			    break;
				
			case DESCARGAR_ARCHIVO_SELECCIONADO: 
				break;
		}
	}
}