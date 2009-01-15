/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package presentacion;

import control.ControlAplicacion;
import presentacion.servidores.ServidoresControl;

/**
 *
 * @author Victor
 */
public class eGorillaControlGeneralImpl extends eGorillaControlGeneral{

    @Override
    public void action(int evento, Object params) {
        // Sólo se gestionan eventos que redirigen el control a los controladores específicos de cada módulo
		switch (evento) {
			case CONECTAR:

                if(!ControlAplicacion.conectado())
                    try {
                        ControlAplicacion.conectar();
                        GUIGeneral.instancia().update(GUIGeneral.MOSTRAR_CONECTADO, null);
                } catch (Exception ex) {
                    GUIGeneral.instancia().update(GUIGeneral.MOSTRAR_ERROR, "Error al intentar conectarse con el servidor");
                }
                
				break;
			case DESCONECTAR:
                try {
                        ControlAplicacion.close();
                        GUIGeneral.instancia().update(GUIGeneral.MOSTRAR_DESCONECTADO, null);
                } catch (Exception ex) {
                    GUIGeneral.instancia().update(GUIGeneral.MOSTRAR_ERROR, "Error al intentar desconectarse con el servidor");
                }

				break;

		}
    }

    @Override
    public void iniciar() {
        GUIGeneral.instancia().update(GUIGeneral.MOSTRAR_MENU, null);
    }

}
