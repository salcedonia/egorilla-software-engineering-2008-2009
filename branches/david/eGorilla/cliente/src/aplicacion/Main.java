package aplicacion;

import control.ControlAplicacion;
import control.ClienteServidor;
import control.gestion_ficheros.GestorCompartidos;
import interfaz.principal.GUIVentanaPrincipal;
import interfaz.splash_screen.SplashScreen;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

//************************************************************************************//
/**
 * Clase principal de la aplicación.
 * 
 * @author Salcedonia
 */
public class Main {

//	************************************************************************************//
	/**
     * Método main de la aplicación.
     * 
     * @param args Argumentos de entrada de la aplicación.
     */
    public static void main(String[] args) {

        // Lanzamos la ventana de Splash
    	SplashScreen sp = new SplashScreen();
        sp.open(3000);
 
        // TODO: ojo con esto, el puerto de ecucha esta puesto muy mal
        // hay que leerlo de la configuracion. properties o lo que sea
        ClienteServidor fs = new ClienteServidor(4000);
        fs.start();

        try {
            
        	// TODO: Mejorarlo
        	// Pasamos los archivos compartidos al servidor
        	GestorCompartidos gestionCompartidos = new GestorCompartidos(new File("./compartidos"));
            ControlAplicacion.setCompartidos (gestionCompartidos);
        
            // Paramos el hilo principal hasta que se cargue el splashScreen
            Thread.sleep(3001);
        } 
        catch (IOException ex) {
            
        	Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (InterruptedException e) {
			
        	e.printStackTrace();
		}

        // Iniciamos la ventana
        @SuppressWarnings("unused")
		GUIVentanaPrincipal ventanaPrincipal = new GUIVentanaPrincipal();
    }
}
