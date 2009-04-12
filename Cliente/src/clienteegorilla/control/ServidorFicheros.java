/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clienteegorilla.control;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * La parte servidora del cliente, esperará a que otro cliente se conecte para
 * atender sus peticiones
 *
 * @author Pitidecaner
 */
public class ServidorFicheros extends Thread{

    int _puerto;
    private boolean _loop;

    public ServidorFicheros(int puerto){
        _puerto = puerto;
        _loop = true;
    }

   public  void run(){

        try {
            ServerSocket s = new ServerSocket(_puerto);
       
        
        while (_loop){

            Socket conn = s.accept();

                // tenemos una subida.
                Subida u = new Subida(conn);
                u.start();
        }
        } catch (IOException ex) {
            Logger.getLogger(ServidorFicheros.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
    }
}
