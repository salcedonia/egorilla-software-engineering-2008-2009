package servidoregorilla;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import servidoregorilla.servidor.Servidor;


/**
 * Clase Main de la aplicación Servidor de servidor.
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
        
        Servidor servidor;
        
        try {        
            // Creamos el servidor
            servidor = new Servidor(6969);
        } 
        catch (IOException ex) {
        
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, 
                ex);
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
                
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, 
                    null, ex);
            }
        }
    }
}
