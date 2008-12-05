/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servidoregorilla;

import servidoregorilla.Datos.ListaArchivos;
import servidoregorilla.Datos.TablaClientes;
import servidoregorilla.protocolo.Protocolo;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import servidoregorilla.server.Server;

/**
 *
 * @author pitidecaner
 */
public class Main {

    public static boolean _loop = true;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // variables we need
        Server eGorilla;
        Protocolo p;
        TablaClientes tablaClientes;
        ListaArchivos archivos;
        
        //  read config and set enviroment
        
        
        //  initiate stuff
        tablaClientes = new TablaClientes();
        archivos =  new ListaArchivos();
        
        try {
             eGorilla = new Server(6969,archivos, tablaClientes);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        System.out.println("escuchando por puerto 6969");

        //  listen connectinons
        while (_loop){
            try {
                p = eGorilla.listen();
                
                // read version!!! Si la version no es correcta lanza excepcion
                // diferentes versiones pueden tener diferente modo de actuación 
                // en el servidor.
                
                System.out.println("cliente conectado");
               
                p.start();
                
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }

}
