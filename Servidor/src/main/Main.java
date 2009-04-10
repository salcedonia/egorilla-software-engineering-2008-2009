package main;

import datos.ArchivoClientes;
import datos.ListadoClientes;
import gestorDeConfiguracion.ControlConfiguracion;
import gestorDeConfiguracion.ControlConfiguracionServidorException;
import gestorDeRed.GestorDeRed;
import gestorDeRed.TCP.GestorDeRedTCPimpl;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mensajes.Mensaje;
import servidor.ServidorEgorilla;

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
        int puerto;
        
        ServidorEgorilla egorilla;
               
        ArchivoClientes archivosYclientes = new ArchivoClientes();
        ListadoClientes listaClientes = new ListadoClientes();
        
        try {        
            // Creamos el servidor
           
            config.leeConfig("Configuracion.properties");
            puerto=ControlConfiguracion.getPuerto();
   
            // crea la red, y la pone a escuchar
            GestorDeRed<Mensaje> red = new GestorDeRedTCPimpl<Mensaje>(puerto); 
            
            // meet each other
            egorilla = new ServidorEgorilla(red, listaClientes, archivosYclientes);
            red.registraReceptor(egorilla);
            
            red.comienzaEscucha();  // a partir de este momento escucha
        } 
        catch (IOException ex) {
        
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, 
                ex);
            return;
        }
        
        System.out.println("Escuchando por puerto " + puerto);
        
    }
}
