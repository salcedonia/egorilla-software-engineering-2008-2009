/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package presentacion.servidores;

/**
 *
 * @author Victor
 */
public class GUIServidoresImpl extends GUIServidores{

    private ServidoresPanel servidoresPanel;


	public GUIServidoresImpl() {
		servidoresPanel = new ServidoresPanel();
	}

	@Override
	public void update(int event, Object param) {
		servidoresPanel.update(event, param);
	}

    public ServidoresPanel getPanel() {
		return servidoresPanel;
	}
}
