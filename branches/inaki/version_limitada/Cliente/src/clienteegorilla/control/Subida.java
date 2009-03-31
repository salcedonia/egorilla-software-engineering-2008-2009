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
import paquete.Archivo;

/**
 *
 * Upload corresponde con las peticiones de subida aceptadas por nuestro cliente.
 *
 * leera el fichero solicitado y despues procedera a subirlo
 *
 * @author Pitidecaner
 */
public class Subida extends Thread{
    private PeerConn _conn;

    Subida(Socket conn) throws IOException {
        _conn = new PeerConn(conn);
    }

    public void run(){
        try {

            // recibimos el hash del archivo buscado
            String target = (String) _conn.recibirObjeto();

            System.out.println( "Un cliente ha solicitado el fichero <"+target+">" );
            //De momento no contempla el fichero a cachos, es decir,
            //comienza el fichero de 0, no contempla q el cliente tenga ya ciertas
            //partes del mismo y menos q esten desordenadas.
            //Se envian los cachos del principio al final

            //para este archivo recibido. enviaselo.
            FileChunk partirdorFicheroCachos = new FileChunk( "compartidos", target );
            Byte[] cacho = null;

            //Le decimos al cliente cuantos cachos va a recibir
            _conn.enviarEntero( partirdorFicheroCachos.getCantidadCachos() );
            //si hay cachos por enviar, los enviamos
            while( partirdorFicheroCachos.masCachos() ){
              cacho = partirdorFicheroCachos.getCacho();
              System.out.println("Envio un cacho");
              _conn.enviarObjeto( cacho );
            }

            _conn.cerrarComunicacion();

        } catch (IOException ex) {
            Logger.getLogger(Subida.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Subida.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
