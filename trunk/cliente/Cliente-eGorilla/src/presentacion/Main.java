/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package presentacion;

import javax.swing.UIManager;

/**
 *
 * @author Victor
 */
public class Main {

    /**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			  UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

        } catch (Exception ex) {}

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance().getContext().getResourceMap(GeneralFrame.class);

        SplashScreen sp = new SplashScreen(resourceMap.getImageIcon("imageSplash.icon").getImage());
        sp.open(3000);

        try {
          Thread.sleep(3001);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }

		 eGorillaControlGeneral.instancia().iniciar();
	}
}
