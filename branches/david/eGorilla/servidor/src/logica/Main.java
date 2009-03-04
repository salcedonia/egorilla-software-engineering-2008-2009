package logica;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import logica.datos.ListaArchivos;
import logica.datos.TablaClientes;
import logica.ejecucion.HiloDelServidor;

//*****************************************************************************//
/**
 * Clase Main de la aplicación Servidor de servidor.
 * 
 * @author pitidecaner
 * @author Salcedonia
 */
public class Main {

    // ATRIBUTOS
    public static boolean _loop = true;
    
//	*****************************************************************************//
    /**
     * Método main del servidor.
     * 
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        
        HiloDelServidor servidor;
        TablaClientes tablaClientes;
        ListaArchivos archivos;
        
       /* 
        * Iniciamos la tabla de clientes y la lista de archivos asociados
        * al servidor así como los tipos de archivos
        * TODO: Integración con base de datos?
        */
        tablaClientes = new TablaClientes();
        archivos =  new ListaArchivos();
        
        try {
        
            // Creamos el servidor
            servidor = new HiloDelServidor(6969, archivos, tablaClientes);
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
