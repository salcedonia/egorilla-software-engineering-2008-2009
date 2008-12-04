/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package presentacion.buscador;

/**
 *
 * @author Victor
 */
public class GUIBuscadorImpl extends GUIBuscador{

    private BuscadorPanel buscadorPanel;

	public GUIBuscadorImpl() {
		buscadorPanel = new BuscadorPanel();
	}

	@Override
	public void update(int event, Object param) {
		buscadorPanel.update(event, param);
	}

    public BuscadorPanel getPanel() {
		return buscadorPanel;
	}
}