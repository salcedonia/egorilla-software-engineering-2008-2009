/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servidoregorilla.protocolo;

import Networking.PeerConn;
import java.io.IOException;
import servidoregorilla.Datos.ListaArchivos;
import servidoregorilla.paquete.Archivo;
import servidoregorilla.paquete.Query;
import servidoregorilla.paquete.QueryAnswer;

/**
 * Esta clase implementa el hilo que se ejecuta para resolver una consulta.
 * Se encarga de localizar los datos requeridos por el cliente y reenviarselos
 * 
 * @author pitidecaner
 */
public class QueryResolver extends Thread{
      
    private ListaArchivos _listaglobal;
    private PeerConn _con;
    private Query _query;
    
    /**
     * La instancia necesita los siguientes datos poder resolver las consultas.
     * 
     * @param l la lista de archivos global dados de alta en este servidor.
     * @param q la consulta recibida por el cliente.
     * @param con la conexion por la que contestar.
     */
    public QueryResolver(ListaArchivos l, Query q, PeerConn con){
        _listaglobal = l;
        _con = con;
        _query = q;
    }
    
    /**
     * el hilo que realiza la consulta:
     * // busca coincidencias en la BBDD
       
        // TODO: mejorar esto,
        // la conjuncion de los dos conjuntos obtenidos es la solución. 
        // por ahora solo busca por nombre y tiene que ser exactamente igual

        // compone respuesta
     */
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
          // do nothing. todo ha ido bien y ya le dara de baja el peerconnpool
        }

        // si todo ha ido bien, hemos acabado aki
        _con.setReady();
    }

    /**
     * La versión para los queries es 2
     * 
     * @return 2
     */
    public int getVersion() {
        return 2;
    }
}
