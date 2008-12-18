/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import networking.PeerConn;
import control.chunk.FileChunk;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import servidoregorilla.paquete.Archivo;
import servidoregorilla.paquete.DownloadOrderAnswer;

/**
 *
 * @author Pitidecaner
 */
public class Download extends Thread{

    private DownloadOrderAnswer _ans;
    private String _deseado;

    public Download(DownloadOrderAnswer ans, String deseado){
        _ans = ans;
        _deseado = deseado;
    }

    public void run(){
        String ip;
        int puerto;
        try {

            if (_ans.getLista().length ==0)
                return;

            ip = _ans.getLista()[0].getIP();
            puerto = _ans.getLista()[0].getPuertoEscucha();

            PeerConn conn = new PeerConn(new Socket(ip,puerto));

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
