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

/*****************************************************************************/
/**
 * Esta clase implementa el hilo que se ejecuta para resolver una consulta.
 * Se encarga de localizar los datos requeridos por el cliente y reenviárselos.
 * 
 * @author pitidecaner
 */
public class QueryResolver extends Thread{
      
    // ATRIBUTOS
    private ListaArchivos _listaglobal;
    private PeerConn _conexion;
    private Query _consulta;
    
/*****************************************************************************/
    /**
     * La instancia necesita los siguientes datos poder resolver las consultas.
     * 
     * @param lista la lista de archivos global dados de alta en este servidor.
     * @param consulta la consulta recibida por el cliente.
     * @param conexion la conexion por la que contestar.
     */
    public QueryResolver(ListaArchivos lista, Query consulta, PeerConn conexion){
        _listaglobal = lista;
        _conexion = conexion;
        _consulta = consulta;
    }
    
/*****************************************************************************/
    /**
     * el hilo que realiza la consulta:
     * // busca coincidencias en la BBDD
       
        // TODO: mejorar esto,
        // la conjuncion de los dos conjuntos obtenidos es la solución. 
        // por ahora solo busca por nombre y tiene que ser exactamente igual

        // compone respuesta
     */
    @Override
    public void run(){
        
        // Busca coincidencias en la BBDD
        Archivo[] lista = _listaglobal.buscar(_consulta.getCadenaBusqueda());
        
        // TODO: mejorar esto,
        // la conjuncion de los dos conjuntos obtenidos es la solución. 
        // por ahora solo busca por nombre y tiene que ser exactamente igual

        // Compone respuesta
        QueryAnswer respuesta = new QueryAnswer(lista);

        // log
        System.out.println("consulta "+_consulta.getCadenaBusqueda()+" desde "+ _conexion.getIP());
        System.out.println("   "+ respuesta.getLista().length+" coincidencias");
        try {
            // Enviar a cliente.
            _conexion.enviarObjeto(respuesta);
        } 
        catch (IOException ex) {
          // Todo ha ido bien y ya le dara de baja el peerconnpool
        }

        // Si todo ha ido bien, hemos acabado aquí
        _conexion.listo();
    }

/*****************************************************************************/
    /**
     * Devuelve la versión asociada a las consultas.
     * 
     * @return 2
     */
    public int getVersion() {

        return 2;
    }
}
