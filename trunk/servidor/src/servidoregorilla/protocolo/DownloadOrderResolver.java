package servidoregorilla.protocolo;

import Networking.PeerConn;
import java.io.IOException;
import servidoregorilla.Datos.ListaArchivos;
import servidoregorilla.paquete.DatosCliente;
import servidoregorilla.paquete.DownloadOrder;
import servidoregorilla.paquete.DownloadOrderAnswer;

/**
 * Realiza la busqueda de todos los clientes que tienen el fichero indicado
 * 
 * 
 * @author pitidecaner
 */
public class DownloadOrderResolver extends Thread {

    private PeerConn _conn;
    private DownloadOrder _orden;
    private ListaArchivos _listaGlobal;
    
    /**
     * 
     * 
     * @param l la lista global de archivos dados de alta en el sistema
     * @param downloadOrder la orden dada por el cliente
     * @param conn la connexion por la que debemos contestar.
     */
    public DownloadOrderResolver(ListaArchivos l,DownloadOrder downloadOrder, PeerConn conn) {
      
        _orden = downloadOrder;
        _conn = conn;
        _listaGlobal = l;
    }

    
    /**
     * realiza la busqueda de las direcciones de todos los clientes que tengan 
     * el fichero indicado 
     */
    public void run(){
       
        // busca a todos los clientes con este archivo.
        DatosCliente [] l = _listaGlobal.propietarios(_orden.hash);
        
        
        // compone respuesta
        DownloadOrderAnswer answer = new DownloadOrderAnswer(l);
        
        // envia
                try {
            // enviar a cliente.
            _conn.sendObject(answer);
        } catch (IOException ex) {
         // TODO: tenemos un problema, hacer algo, habra que darlo de baja, o no?
            // si se ha interrumpido la conexion se dara cuenta el peerconnpool
            // si 
    }

        // hemos acabado aqui
        _conn.setReady();
    }
    
}
