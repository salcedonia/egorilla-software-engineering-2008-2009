/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clienteegorilla.control;

import red.PeerConn;
import clienteegorilla.control.chunk.FileChunk;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import paquete.*;

/**
 *
 * @author Pitidecaner
 */
public class Download extends Thread{

    private RespuestaPeticionDescarga _respuestaDescarga;
    private String _deseado;

    public Download(RespuestaPeticionDescarga respuestaDescarga, String deseado){
        _respuestaDescarga = respuestaDescarga;
        _deseado = deseado;
    }

    public void run(){
        String ip;
        int puerto;
        

          //Si el fichero no tiene usuarios conectados (aunque no deberia estar ni el fichero en la lista)
          //Luego esto no deberia cumplirse nunca
            if (_respuestaDescarga.getLista().length == 0 ){
              System.err.println( "No hay fuentes, ningun cliente tiene el archivo." );
            }else{
              //System.out.println( "Conecta con un usuario." );
              try {
            ip = _respuestaDescarga.getLista()[0].getIP();
            puerto = _respuestaDescarga.getLista()[0].getPuertoEscucha();

            PeerConn conn = new PeerConn( new Socket( ip, puerto ) );

            conn.enviarObjeto(_deseado);

            FileChunk chunk = (FileChunk)conn.recibirObjeto();

            conn.cerrarComunicacion();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Download.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Download.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
    }
}
