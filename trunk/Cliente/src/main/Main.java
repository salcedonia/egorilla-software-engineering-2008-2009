package main;

import control.*;
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
    public static void main(String[] args){

        String cad = null;

        // TODO: ojo con esto, el puerto de ecucha esta puesto muy mal
        // hay que leerlo de la configuracion. properties o lo que sea
        ServidorFicheros fs = new ServidorFicheros(4000);
        fs.start();

        String nombreDirectorio = "compartidos";
        ControlAplicacion.compartidos(nombreDirectorio);
        
        // Mostramos la interfaz de consola
        try {

            new GUIConsola(cad);
            
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
