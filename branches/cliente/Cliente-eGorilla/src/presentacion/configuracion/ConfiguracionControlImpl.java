/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package presentacion.configuracion;

/**
 *
 * @author Victor
 */
public class ConfiguracionControlImpl extends ConfiguracionControl{

    @Override
    public void action(int event, Object params) {
        //FachadaServicios fachada = FactoriaFachadaServicios.instancia().crearFachadaServicios();;
		// Se distingue el tipo de evento
		switch (event) {
			// Las operaciones siguientes llaman al método correspondiente de la fachada de servicios y devuelven los resultados a la GUICliente
			case ACCION1:
                //Accede a la fachada de servicios
				GUIConfiguracion.instancia().update(GUIConfiguracion.EVENTO1, new Object());
				break;
			case ACCION2:
                //Accede a la fachada de servicios
                            
                                
				GUIConfiguracion.instancia().update(GUIConfiguracion.EVENTO2, new Object());
				break;

		}
    }

    @Override
    public void iniciar() {
        GUIConfiguracion.instancia().update(GUIConfiguracion.EVENTO1, null);
    }
}
