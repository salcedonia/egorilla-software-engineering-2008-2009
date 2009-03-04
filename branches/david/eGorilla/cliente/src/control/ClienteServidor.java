package control;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

//************************************************************************************//
/**
 * 
 * La parte servidora del cliente, esperará a que otro cliente se conecte para
 * atender sus peticiones
 * 
 * @author Pitidecaner
 */
public class ClienteServidor extends Thread {

	// ATRIBUTOS
	public int _puerto;
	private boolean _loop;

	// ************************************************************************************//
	/**
	 * Constructor de la clase ClienteServidor.
	 * 
	 * @param puerto
	 *            Puerto de escucha.
	 */
	public ClienteServidor(int puerto) {
		
		_puerto = puerto;
		_loop = true;
	}

//	************************************************************************************//
	/**
	 * Hilo que se encarga de escuchar las peticiones de otros clientes para hacer de 
	 * servidor de los archivos que tenga compartidos y que han sido solicitados por 
	 * dichos clientes.
	 */
	public void run() {

		try {

			ServerSocket s = new ServerSocket(_puerto);

			while (_loop) {

				Socket conn = s.accept();

				// Tenemos una subida.
				SubidaArchivo u = new SubidaArchivo(conn);
				u.start();
			}
		} 
		catch (IOException ex) {
			
			Logger.getLogger(ClienteServidor.class.getName()).log(Level.SEVERE, null, ex);
			return;
		}
	}
}
