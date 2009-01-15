package control;

import control.gestion_ficheros.ChunkArchivo;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import logica.protocolo.respuestas.RespuestaADescarga;
import logica.red.ConexionPeer;

//************************************************************************************//
/**
 * Clase que implementa la descarga de un archivo.
 * 
 * @author Pitidecaner
 * @author S@L-c
 */
public class DescargaArchivo extends Thread{

	// ATRIBUTOS
    private RespuestaADescarga _respuesta;
    private String _nombreArchivo;

//  ************************************************************************************//
    /**
     * Constructor de la clase DescargaArchivo.
     * 
     * @param respuesta
     * @param archivo
     */
    public DescargaArchivo(RespuestaADescarga respuesta, String archivo){
        
    	_respuesta = respuesta;
        _nombreArchivo = archivo;
    }

//  ************************************************************************************//
    /**
     * Hilo que se encarga de procesar la descarga.
     */
    public void run(){

    	String ip;
    	int puerto;
        
    	try {

            if (_respuesta.getLista().length ==0)
                return;

            ip = _respuesta.getLista()[0].getIP();
            puerto = _respuesta.getLista()[0].getPuertoEscucha();

            ConexionPeer conexion = new ConexionPeer(new Socket(ip,puerto));

            conexion.enviarObjeto(_nombreArchivo);

            @SuppressWarnings("unused")
			ChunkArchivo chunk = (ChunkArchivo)conexion.recibirObjeto();

            conexion.cerrarComunicacion();
        } 
        catch (ClassNotFoundException ex) {
        	
            Logger.getLogger(DescargaArchivo.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) {
        
        	Logger.getLogger(DescargaArchivo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
