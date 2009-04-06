package main;

import gestorDeConfiguracion.ControlConfiguracion;
import gestorDeConfiguracion.ControlConfiguracionServidorException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase Main de la aplicación HiloServidor de servidor.
 */
public class Main {

    // ATRIBUTOS
    public static boolean _loop = true;
    
    /**
     * Método main del servidor.
     * 
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) throws IOException, ControlConfiguracionServidorException {
        
        ControlConfiguracion config= new ControlConfiguracion();
        try {        
            // Creamos el servidor
           
            config.leeConfig("Configuracion.properties");
            int puerto=config.getPuerto();
      //      servidor = new HiloServidor(puerto);
        } 
        catch (IOException ex) {
        
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, 
                ex);
            return;
        }
        
        System.out.println("Escuchando por puerto 6969");
        
//
//        //  Escuchamos conexiones 
//        while (_loop){
//            
//            try {
//                
//                // Escuchamos conexiones
//             //   servidor.escuchar();
//                
//            } 
//            catch (IOException ex) {
//                
//                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, 
//                    null, ex);
//            }
//        }
    }
}
