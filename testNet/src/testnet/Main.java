/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package testnet;

import gestorDeRed.GestorDeRed;
import gestorDeRed.GestorDeRedTCPimpl;
import gestorDeRed.Receptor;

/**
 *
 * @author pitidecaner
 */
public class Main implements Receptor<String>{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        GestorDeRed<String> g = new GestorDeRedTCPimpl<String>(6969);

        Main r = new Main();
        
        g.registraReceptor(r);

        g.comienzaEscucha();

        g.envia("hola", "127.0.0.1", 6969);
                g.envia("hola", "127.0.0.1", 6969);
                        g.envia("hola", "127.0.0.1", 6969);
                                g.envia("hola", "127.0.0.1", 6969);
    }

    public void recibeMensaje(String msj) {
        System.out.println (msj);
    }

}
