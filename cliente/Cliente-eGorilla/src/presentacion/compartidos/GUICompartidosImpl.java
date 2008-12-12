/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package presentacion.compartidos;

/**
 *
 * @author Victor
 */
public class GUICompartidosImpl extends GUICompartidos{

    private CompartidosPanel CompartidosPanel;

	public GUICompartidosImpl() {
		CompartidosPanel = new CompartidosPanel();
	}

	@Override
	public void update(int event, Object param) {
		CompartidosPanel.update(event, param);
	}

    public CompartidosPanel getPanel() {
		return CompartidosPanel;
	}
}