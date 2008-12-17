/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package presentacion;

import control.FileServer;
import javax.swing.UIManager;

/**
 *
 * @author Victor
 */
public class Main {

    /**
     * Método main de la aplicación.
     * @param args
     */
    public static void main(String[] args) {

        try {
            
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } 
        catch (Exception ex) {
        
        }

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance().getContext().getResourceMap(GeneralFrame.class);

        SplashScreen sp = new SplashScreen(resourceMap.getImageIcon("imageSplash.icon").getImage());
        sp.open(3000);

        
        // TODO: ojo con esto, el puerto de ecucha esta puesto muy mal
        FileServer fs = new FileServer(4000);
        fs.start();

        try {
            Thread.sleep(3001);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        eGorillaControlGeneral.instancia().iniciar();
    }
}
