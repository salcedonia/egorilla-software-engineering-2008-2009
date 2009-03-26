package tareas;

import java.io.IOException;
import datos.*;
import mensajes.serverclient.PeticionConsulta;
import mensajes.serverclient.RespuestaPeticionConsulta;


/**
 * Esta clase implementa el hilo que se ejecuta para resolver una consulta.
 * Se encarga de localizar los datos requeridos por el cliente y reenviárselos.
 * 
 * @author pitidecaner
 */
public class ProcesarPeticionBusqueda extends Thread{
      
    // ATRIBUTOS
    private ArchivoClientes _listaGlobalArchivoClientes;
    private ConexionCliente _conexion;
    private PeticionConsulta _peticionBusqueda;
    
    /**
     * La instancia necesita los siguientes datos poder resolver las consultas.
     * 
     * @param lista la lista de archivos global dados de alta en este servidor.
     * @param consulta la consulta recibida por el cliente.
     * @param conexion la conexion por la que contestar.
     */
    public ProcesarPeticionBusqueda(ArchivoClientes lista, PeticionConsulta consulta, ConexionCliente conexion){
        _listaGlobalArchivoClientes = lista;
        _conexion = conexion;
        _peticionBusqueda = consulta;
    }
    
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
        Archivo[] lista = _listaGlobalArchivoClientes.buscar( _peticionBusqueda.getCadenaBusqueda() );
        
        // TODO: mejorar esto,
        // la conjuncion de los dos conjuntos obtenidos es la solución. 
        // por ahora solo busca por nombre y tiene que ser exactamente igual

        // Compone respuesta
        RespuestaPeticionConsulta respuestaBusqueda = new RespuestaPeticionConsulta( lista );
        
        try {
            // Enviar a cliente.
            _conexion.getConexion().enviarObjeto( respuestaBusqueda  );
        } 
        catch (IOException ex) {
          // Todo ha ido bien y ya le dara de baja el peerconnpool
        }

        // Si todo ha ido bien, hemos acabado aquí
        _conexion.getConexion().listo();
    }

    /**
     * Devuelve la versión asociada a las consultas.
     * 
     * @return 2
     */
    public int getVersion() {

        return 2;
    }
}
