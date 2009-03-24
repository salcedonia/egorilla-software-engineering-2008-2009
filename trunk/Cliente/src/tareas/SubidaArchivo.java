package tareas;

import gestorDeFicheros.ParteDeArchivo;
import gestorDeRed.ConexionPeer;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que gestiona las peticiones de subida aceptadas por el Cliente eGorilla.
 * Lee el fichero solicitado y despues procedera a subirlo al servidor.
 *
 * @author Luis Ayuso, Ivan Munsuri, Javier Salcedo
 */
public class SubidaArchivo extends Thread {
    
    // ATRIBUTOS
    private ConexionPeer _conexionPeer;

    /**
     * 
     * @param conn
     * @throws java.io.IOException
     */
    public SubidaArchivo(Socket conn) throws IOException {
        
        _conexionPeer = new ConexionPeer(conn);
    }

    /**
     * 
     */
    @Override
    public void run() {
        try {

            // recibimos el hash del archivo buscado
            String target = (String) _conexionPeer.recibirObjeto();

            System.out.println("Un cliente ha solicitado el fichero <" + target + ">");
            //De momento no contempla el fichero a partes, es decir,
            //comienza el fichero de 0, no contempla q el cliente tenga ya ciertas
            //partes del mismo y menos q esten desordenadas.
            //Se envian los partes del principio al final

            //para este archivo recibido. enviaselo.
            ParteDeArchivo partirdorFicheroPartes = new ParteDeArchivo("compartidos", target);
            Byte[] parte = null;

            //Le decimos al cliente cuantos cachos va a recibir
            _conexionPeer.enviarEntero(partirdorFicheroPartes.getCantidadPartes());
            //si hay partes por enviar, los enviamos
            while (partirdorFicheroPartes.masPartes()) {
                parte = partirdorFicheroPartes.getParte();
                System.out.println("Envio una parte");
                _conexionPeer.enviarObjeto(parte);
            }

            _conexionPeer.cerrarComunicacion();

        } catch (IOException ex) {
            Logger.getLogger(SubidaArchivo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(SubidaArchivo.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
