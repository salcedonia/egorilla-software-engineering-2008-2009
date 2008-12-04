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
			case SERVIDORES:
				ServidoresControl.instancia().iniciar();
				break;
			case BUSCAR:
				//GestionClientesControl.instancia().iniciar();
				break;
		}
    }

    @Override
    public void iniciar() {
        GUIGeneral.instancia().update(GUIGeneral.MOSTRAR_MENU, null);
    }

}
