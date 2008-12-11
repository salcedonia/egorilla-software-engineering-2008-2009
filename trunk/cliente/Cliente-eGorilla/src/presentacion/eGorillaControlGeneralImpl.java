/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package presentacion;

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
                GUIGeneral.instancia().update(GUIGeneral.MOSTRAR_CONECTADO, null);
				break;
			case DESCONECTAR:
				GUIGeneral.instancia().update(GUIGeneral.MOSTRAR_DESCONECTADO, null);
				break;

		}
    }

    @Override
    public void iniciar() {
        GUIGeneral.instancia().update(GUIGeneral.MOSTRAR_MENU, null);
    }

}
