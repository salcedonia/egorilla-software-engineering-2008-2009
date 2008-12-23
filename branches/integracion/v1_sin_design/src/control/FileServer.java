/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

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
public class FileServer extends Thread{

    int _puerto;
    private boolean _loop;

    public FileServer(int puerto){
        _puerto = puerto;
        _loop = true;
    }

   public  void run(){

        try {
            ServerSocket s = new ServerSocket(_puerto);
       
        
        while (_loop){

            Socket conn = s.accept();

                // tenemos una subida.
                Upload u = new Upload(conn);
                u.start();
        }
        } catch (IOException ex) {
            Logger.getLogger(FileServer.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
    }
}
