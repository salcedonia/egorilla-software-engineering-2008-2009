/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clienteegorilla.control;

import red.PeerConn;
import clienteegorilla.control.chunk.FileChunk;
import java.io.IOException;
import java.net.Socket;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import paquete.*;

/**
 *
 * @author Pitidecaner
 */
public class Descarga extends Thread{

    private RespuestaPeticionDescarga _respuestaDescarga;
    private String _deseado;
    private String _nombre;
    private String _directorio;

    public Descarga(RespuestaPeticionDescarga respuestaDescarga, String deseado, String nombreArchivo){
        _respuestaDescarga = respuestaDescarga;
        _deseado = deseado;
        _directorio = "incoming";
        _nombre = nombreArchivo;
    }

    @Override
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
                //En principio solo se pide partes a uno de los usuarios q tiene el archivo.
                //Problema de solo a uno esq al q se lo pide puede estar ocupado, subiendo ya a
                //varios clientes y entonces, al no controlar esto, fallo.
                //En el futuro permitir q se pida a varios clientes.
            ip = _respuestaDescarga.getLista()[0].getIP();
            puerto = _respuestaDescarga.getLista()[0].getPuertoEscucha();

            PeerConn conn = new PeerConn( new Socket( ip, puerto ) );

            conn.enviarObjeto( _deseado );

            ArrayList< Byte[] > cachos = new ArrayList< Byte[] >();
            //'Bucle' donde va recibiendo todos los cachos.
            
            //Si tiene mas de una parte, necesito saber primero cuantas partes voy a recibir
            //Para saber cuando parar el bucle de recibir
            int cantidadDeCachos = conn.recibirEntero(); //cantidad de partes

            for( int i = 0;  i < cantidadDeCachos; i++ ){
              Byte[] cacho = ( Byte[] )conn.recibirObjeto();
              //System.out.println("Cacho recibido");
              cachos.add( cacho );
              //System.out.println("<"+ cachos.size() +"> partes recibidas");
            }

            //Escribir los cachos que forman el fichero.
            FileChunk escritorFicheroCachos = new FileChunk( _directorio, "ficheroDescargado"+"_"+_nombre, _deseado );
            
            escritorFicheroCachos.setCachos( cachos );

            conn.cerrarComunicacion();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger( Descarga.class.getName() ).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger( Descarga.class.getName() ).log(Level.SEVERE, null, ex);
        }
        }
    }
}
