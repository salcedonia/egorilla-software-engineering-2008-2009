/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servidoregorilla.protocolo;

import Networking.PeerConn;
import java.io.IOException;
import java.io.Serializable;
import servidoregorilla.Datos.ListaArchivos;
import servidoregorilla.Datos.TablaClientes;
import servidoregorilla.paquete.DatosCliente;
import servidoregorilla.paquete.DownloadOrder;
import servidoregorilla.paquete.DownloadOrderAnswer;

/**
 *
 * @author pitidecaner
 */
public class DownloadOrderResolver extends Thread {

    private PeerConn _conn;
    private DownloadOrder _orden;
    private ListaArchivos _listaGlobal;
    
    public DownloadOrderResolver(ListaArchivos l,DownloadOrder downloadOrder, PeerConn conn) {
      
        _orden = downloadOrder;
        _conn = conn;
    }

    
    /**
     * 
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
