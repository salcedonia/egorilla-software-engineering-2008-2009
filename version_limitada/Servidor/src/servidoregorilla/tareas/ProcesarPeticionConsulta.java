/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servidoregorilla.tareas;

import red.PeerConn;
import java.io.IOException;
import servidoregorilla.datos.*;
import paquete.*;

/*****************************************************************************/
/**
 * Esta clase implementa el hilo que se ejecuta para resolver una consulta.
 * Se encarga de localizar los datos requeridos por el cliente y reenviárselos.
 * 
 * @author pitidecaner
 */
public class ProcesarPeticionConsulta extends Thread{
      
    // ATRIBUTOS
    private ArchivoClientes _listaGlobalArchivoClientes;
    private ConexionCliente _conexion;
    private PeticionConsulta _consulta;
    
/*****************************************************************************/
    /**
     * La instancia necesita los siguientes datos poder resolver las consultas.
     * 
     * @param lista la lista de archivos global dados de alta en este servidor.
     * @param consulta la consulta recibida por el cliente.
     * @param conexion la conexion por la que contestar.
     */
    public ProcesarPeticionConsulta(ArchivoClientes lista, PeticionConsulta consulta, ConexionCliente conexion){
        _listaGlobalArchivoClientes = lista;
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
        Archivo[] lista = _listaGlobalArchivoClientes.buscar( _consulta.getCadenaBusqueda() );
        
        // TODO: mejorar esto,
        // la conjuncion de los dos conjuntos obtenidos es la solución. 
        // por ahora solo busca por nombre y tiene que ser exactamente igual

        // Compone respuesta
        RespuestaPeticionConsulta respuestaConsulta = new RespuestaPeticionConsulta( lista );
        
        try {
            // Enviar a cliente.
            _conexion.getConexion().enviarObjeto( respuestaConsulta );
        } 
        catch (IOException ex) {
          // Todo ha ido bien y ya le dara de baja el peerconnpool
        }

        // Si todo ha ido bien, hemos acabado aquí
        _conexion.getConexion().listo();
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
