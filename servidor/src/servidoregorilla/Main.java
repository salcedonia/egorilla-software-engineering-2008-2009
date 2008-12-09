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
 * Clase Main de la aplicación Servidor de servidor.
 * 
 * @author pitidecaner
 * @author Salcedonia
 */
public class Main {

    // ATRIBUTOS
    public static boolean _loop = true;
    
    /**
     * Método main del servidor.
     * 
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        
        Server servidor;
        Protocolo protocolo;
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
                protocolo = servidor.listen();
                
                /*
                 * Si la version no es correcta lanza excepcion
                 * diferentes versiones pueden tener diferente modo de actuación 
                 * en el servidor.
                 */
                System.out.println("Cliente conectado");
               
                // Lanzamos el hilo de ejecución asociado a la conexión
                protocolo.start();
            } 
            catch (IOException ex) {
                
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
