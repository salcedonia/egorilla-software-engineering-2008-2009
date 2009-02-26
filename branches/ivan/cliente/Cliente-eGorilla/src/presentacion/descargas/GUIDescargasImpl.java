/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package presentacion.descargas;

/**
 *
 * @author Victor
 */
public class GUIDescargasImpl extends GUIDescargas{

    private DescargasPanel buscadorPanel;

	public GUIDescargasImpl() {
		buscadorPanel = new DescargasPanel();
	}

	@Override
	public void update(int event, Object param) {
		buscadorPanel.update(event, param);
	}

    public DescargasPanel getPanel() {
		return buscadorPanel;
	}
}