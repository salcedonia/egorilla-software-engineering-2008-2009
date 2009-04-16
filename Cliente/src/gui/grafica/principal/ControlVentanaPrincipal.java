package gui.grafica.principal;

import control.ControlAplicacion;

//************************************************************************************//
/**
 * Clase que proporciona los métodos y constantes necesarias para
 * el control de la ventana principal de la aplicación.
 * 
 * @author Victor
 * @author S@L-c
 */
public class ControlVentanaPrincipal {

    // ATRIBUTOS
    private GUIVentanaPrincipal _ventanaPrincipal;
    
//	************************************************************************************//
    /**
     * Constructor de la clase ControlVentanaPrincipal.
     * 
     * @param ventanaPrincipal GUI asociado al control.
     */
    public ControlVentanaPrincipal(GUIVentanaPrincipal ventanaPrincipal){
    	
    	_ventanaPrincipal = ventanaPrincipal;
    }
    
//	************************************************************************************//
    /**
     * Recoge el evento de acción que se ha producido sobre la ventana o sobre
     * alguno de sus elementos.
     * 
     * @param accion Acción asociada.
     * @param params Parámetros asociados a la acción.
     */
    public void tratarAccion(AccionVentanaPrincipal accion, Object params){
    	
    	/*switch (accion) {
			
			case CONECTAR_SERVIDOR:

                // Si no estamos conectados a un servidor
				if(!ControlAplicacion.getConectado()){
                    try {
                    	
                    	// Nos conectamos
                        Object[] parametros=new Object[2];
                        parametros[0]="127.0.0.1";
                        parametros[1]=6969;
                        ControlAplicacion.conectarAServidor(parametros);
                        
                        // Avisamos al GUI de la ventana principal para que se actualice
                        _ventanaPrincipal.tratarEvento(EventoVentanaPrincipal.MOSTRAR_ESTADO_CONECTADO, null);
                    } 
                	catch (Exception ex) {
                		
                		_ventanaPrincipal.tratarEvento(EventoVentanaPrincipal.MOSTRAR_MENSAJE_ERROR, "Error al intentar conectarse con el servidor");
                	}
                }
				else
					_ventanaPrincipal.tratarEvento(EventoVentanaPrincipal.MOSTRAR_MENSAJE_ERROR, "¡Ya estás conectado a un servidor!");
            	
				break;
			
			case DESCONECTAR_SERVIDOR:
                
				try {
					
					// Realizamos la desconexión con el servidor
					ControlAplicacion.cerrarConexionConServidor();
					
					// Avisamos al GUI de la ventana principal para que se actualice
                    _ventanaPrincipal.tratarEvento(EventoVentanaPrincipal.MOSTRAR_ESTADO_DESCONECTADO, null);
                } 
				catch (Exception ex) {
                    
                    _ventanaPrincipal.tratarEvento(EventoVentanaPrincipal.MOSTRAR_MENSAJE_ERROR, "Error al intentar desconectarse con el servidor");
                }

				break;
		}*/
    }
}
