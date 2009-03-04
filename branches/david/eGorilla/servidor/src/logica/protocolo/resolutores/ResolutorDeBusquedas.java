package logica.protocolo.resolutores;

import logica.datos.ListaArchivos;
import logica.paquete.Archivo;
import logica.protocolo.ProtocoloServidor;
import logica.protocolo.peticiones.PeticionBusqueda;
import logica.protocolo.respuestas.RespuestaABusqueda;
import logica.red.ConexionPeer;

import java.io.IOException;

//*****************************************************************************//
/**
 * Esta clase implementa el hilo que se ejecuta para resolver una petición de 
 * búsqueda de un archivo.
 * Se encarga de localizar los datos requeridos por el cliente y reenviárselos.
 * 
 * @author pitidecaner
 * @author S@L-c
 */
public class ResolutorDeBusquedas extends Thread{
      
    // ATRIBUTOS
    private ListaArchivos _listaglobal;
    private ConexionPeer _conexion;
    private PeticionBusqueda _consulta;
    
//  *****************************************************************************//
    /**
     * La instancia necesita los siguientes datos poder resolver las consultas.
     * 
     * @param lista la lista de archivos global dados de alta en este servidor.
     * @param peticionBusqueda la consulta recibida por el cliente.
     * @param conexion la conexion por la que contestar.
     */
    public ResolutorDeBusquedas(ListaArchivos lista, PeticionBusqueda peticionBusqueda, ConexionPeer conexion){
        
    	_listaglobal = lista;
        _conexion = conexion;
        _consulta = peticionBusqueda;
    }
    
//  *****************************************************************************//
    /**
     * El hilo que realiza la consulta.
     */
    @Override
    public void run(){
        
        // Busca coincidencias en la BBDD
        Archivo[] lista = _listaglobal.buscarArchivoPorNombre(_consulta.getCadenaBusqueda());
        
        // TODO: mejorar esto,
        // la conjuncion de los dos conjuntos obtenidos es la solución. 
        // por ahora solo busca por nombre y tiene que ser exactamente igual

        // Compone respuesta
        RespuestaABusqueda respuesta = new RespuestaABusqueda(lista);
        
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

//  *****************************************************************************//
    /**
     * Devuelve la versión asociada a las consultas.
     * 
     * @return El protocolo asociado a la búsqueda de archivos.
     */
    public ProtocoloServidor getProtocolo() {

        return ProtocoloServidor.BUSQUEDA_ARCHIVOS;
    }
}
