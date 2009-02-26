/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package presentacion.configuracion;

/**
 *
 * @author Victor
 */
public class GUIConfiguracionImpl extends GUIConfiguracion{

    private ConfiguracionPanel buscadorPanel;

	public GUIConfiguracionImpl() {
		buscadorPanel = new ConfiguracionPanel();
	}

	@Override
	public void update(int event, Object param) {
		buscadorPanel.update(event, param);
	}

    public ConfiguracionPanel getPanel() {
		return buscadorPanel;
	}
}