package main;

import gestorDeConfiguracion.ControlConfiguracionCliente;
import control.*;
import gestorDeConfiguracion.ControlConfiguracionClienteException;
import gui.GUIConsola;
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

        
        ControlConfiguracionCliente oCtrlConfigCliente = ControlConfiguracionCliente.obtenerInstancia("cliente.properties", "cliente_default.properties");
        int iPuerto = Integer.parseInt(oCtrlConfigCliente.obtenerPropiedad("Puerto"));

//        String nombreDirectorio = "compartidos";
       
        ControlAplicacion control = new  ControlAplicacion(iPuerto, oCtrlConfigCliente.obtenerPropiedad("Dir_Compartidos"));
        
        // Mostramos la interfaz de consola
        try {
            new GUIConsola(control).mostrarMenu();
            
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
