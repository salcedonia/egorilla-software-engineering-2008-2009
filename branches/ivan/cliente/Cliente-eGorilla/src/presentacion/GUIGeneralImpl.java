/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package presentacion;

/**
 *
 * @author Victor
 */
public class GUIGeneralImpl extends GUIGeneral{

    //Interfaz principal
	private GeneralFrame generalFrame;


	public GUIGeneralImpl() {
		generalFrame = new GeneralFrame();
	}

	@Override
	public void update(int evento, Object params) {
		generalFrame.update(evento, params);
	}
}
