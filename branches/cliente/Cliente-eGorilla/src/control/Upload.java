/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import Networking.PeerConn;
import control.chunk.FileChunk;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import servidoregorilla.paquete.Archivo;

/**
 *
 * Upload corresponde con las peticiones de subida aceptadas por nuestro cliente.
 *
 * leera el fichero solicitado y despues procedera a subirlo
 *
 * @author Pitidecaner
 */
public class Upload extends Thread{
    private PeerConn _conn;

    Upload(Socket conn) throws IOException {
        _conn = new PeerConn(conn);
    }

    public void run(){
        try {

            // recibimos el hash del archivo buscado
            String target = (String) _conn.recibirObjeto();

            //para este archivo recibido. enviaselo.
            FileChunk cacho = new FileChunk();

            // leer archivo, chunkear y enviar
            cacho.valido = false;

            _conn.enviarObjeto(cacho);

            _conn.cerrarComunicacion();
        } catch (IOException ex) {
            Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Upload.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
