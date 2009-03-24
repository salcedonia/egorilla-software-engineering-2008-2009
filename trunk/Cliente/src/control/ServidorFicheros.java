package control;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import tareas.SubidaArchivo;

/**
 *
 * La parte servidora del cliente, esperará a que otro cliente se conecte para
 * atender sus peticiones
 *
 * @author Luis Ayuso, Ivan Munsuri, Javier Salcedo
 */
public class ServidorFicheros extends Thread {

    // ATRIBUTOS
    int _puerto;
    private boolean _loop;

    /**
     * 
     * @param puerto
     */
    public ServidorFicheros(int puerto) {
        _puerto = puerto;
        _loop = true;
    }

    /**
     * 
     */
    @Override
    public void run() {

        try {
            ServerSocket s = new ServerSocket(_puerto);


            while (_loop) {

                Socket conn = s.accept();

                // tenemos una subida.
                SubidaArchivo u = new SubidaArchivo(conn);
                u.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorFicheros.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
    }
}
