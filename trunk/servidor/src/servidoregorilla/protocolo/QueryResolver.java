/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servidoregorilla.protocolo;

import Networking.PeerConn;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import servidoregorilla.Datos.ListaArchivos;
import servidoregorilla.paquete.Archivo;
import servidoregorilla.paquete.Query;
import servidoregorilla.paquete.QueryAnswer;

/**
 *
 * @author pitidecaner
 */
public class QueryResolver extends Thread{
      
    private ListaArchivos _listaglobal;
    private PeerConn _con;
    private Query _query;
    
    
    public QueryResolver(ListaArchivos l, Query q, PeerConn con){
        _listaglobal = l;
        _con = con;
        _query = q;
    }
    
    public void run(){
        
        Archivo[] lista;
        
        // busca coincidencias en la BBDD
        lista = _listaglobal.buscar(_query.cadenaBusqueda);
        
        // TODO: mejorar esto,
        // la conjuncion de los dos conjuntos obtenidos es la solución. 
        // por ahora solo busca por nombre y tiene que ser exactamente igual

        // compone respuesta
        QueryAnswer respuesta = new QueryAnswer(lista);
        try {
            // enviar a cliente.
            _con.sendObject(respuesta);
        } catch (IOException ex) {
         // TODO: tenemos un problema, hacer algo, habra que darlo de baja, o no?
            // si se ha interrumpido la conexion se dara cuenta el peerconnpool
            // si 
    }

        // si todo ha ido bien, hemos acabado aki
        _con.setReady();
    }

    public int getVersion() {
        return 2;
    }
}
