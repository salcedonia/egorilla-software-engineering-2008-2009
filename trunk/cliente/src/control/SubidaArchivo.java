package control;

import control.gestion_ficheros.ChunkArchivo;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import logica.red.ConexionPeer;

//************************************************************************************//
/**
 *
 * SubidaArchivo corresponde con las peticiones de subida aceptadas por nuestro cliente.
 *
 * leera el fichero solicitado y despues procedera a subirlo
 *
 * @author Pitidecaner
 * @author S@L-c
 */
public class SubidaArchivo extends Thread{

	// ATRIBUTOS
	private ConexionPeer _conexion;

//	************************************************************************************//
	/**
	 * Constructor de la clase SubidaArchivo.
	 * 
	 * @param conexion Conexión con el otro cliente al que se le pasa el archivo.
	 * 
	 * @throws IOException Cuando hay algún problema con la conexión.
	 */
    SubidaArchivo(Socket conexion) throws IOException {
        
    	_conexion = new ConexionPeer(conexion);
    }

//  ************************************************************************************//
    /**
     * Hilo que se encarga de gestionar las subidas.
     */
    public void run(){
        
    	try {

            // recibimos el hash del archivo buscado
            @SuppressWarnings("unused")
			String target = (String) _conexion.recibirObjeto();

            //para este archivo recibido. enviaselo.
            ChunkArchivo cacho = new ChunkArchivo();

            // leer archivo, chunkear y enviar
            cacho._valido = false;

            _conexion.enviarObjeto(cacho);

            _conexion.cerrarComunicacion();
        } 
    	catch (IOException ex) {
            
    		Logger.getLogger(SubidaArchivo.class.getName()).log(Level.SEVERE, null, ex);
        } 
    	catch (ClassNotFoundException ex) {
        
    		Logger.getLogger(SubidaArchivo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
