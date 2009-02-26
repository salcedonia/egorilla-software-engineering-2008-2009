/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servidoregorilla;

import servidoregorilla.datos.ListaArchivos;
import servidoregorilla.datos.TablaClientes;
import servidoregorilla.paquete.Peticion;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import servidoregorilla.server.Server;

/*****************************************************************************/
/**
 * Clase Main de la aplicación Servidor de servidor.
 * 
 * @author pitidecaner
 * @author Salcedonia
 */
public class Main {

    // ATRIBUTOS
    public static boolean _loop = true;
    
/*****************************************************************************/
    /**
     * Método main del servidor.
     * 
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        
        Server servidor;
        Peticion peticion;
        TablaClientes tablaClientes;
        ListaArchivos archivos;
        
       /* 
        * Iniciamos la tabla de clientes y la lista de archivos asociados
        * al servidor.
        * TODO: Integración con base de datos?
        */
        tablaClientes = new TablaClientes();
        archivos =  new ListaArchivos();
        
        try {
        
            // Creamos el servidor
            servidor = new Server(6969, archivos, tablaClientes);
        } 
        catch (IOException ex) {
        
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
        
        System.out.println("Escuchando por puerto 6969");

        //  Escuchamos conexiones 
        while (_loop){
            
            try {
                
                // Escuchamos conexiones
                servidor.escuchar();
                
            } 
            catch (IOException ex) {
                
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
