package main;

import gestorDeConfiguracion.ControlConfiguracionCliente;
import control.*;
import gestorDeConfiguracion.ControlConfiguracionClienteException;
import gui.GUIConsola;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que implemeta un test de prueba del servidor.
 * 
 * @author Luis Ayuso, Ivan Munsuri, Javier Salcedo
 */
public class Main {
    
    /**
     * Método main de la aplicacion Cliente eGorilla.
     * 
     * @param args Argumentos de la aplicación de la línea de comandos.
     */
    public static void main(String[] args) throws ControlConfiguracionClienteException{

        String cad = null;
        
        ControlConfiguracionCliente oCtrlConfigCliente = ControlConfiguracionCliente.obtenerInstancia("cliente.properties", "cliente_default.properties");
        int iPuerto = Integer.parseInt(oCtrlConfigCliente.obtenerPropiedad("Puerto"));
        ServidorFicheros fs = new ServidorFicheros(iPuerto);

//        ServidorFicheros fs = new ServidorFicheros(4000);
        fs.start();

//        String nombreDirectorio = "compartidos";
        ControlAplicacion.compartidos(oCtrlConfigCliente.obtenerPropiedad("Dir_Compartidos"));
        
        // Mostramos la interfaz de consola
        try {

            new GUIConsola(cad);
            
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
