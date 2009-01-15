package logica.protocolo.resolutores;

import logica.datos.DatosCliente;
import logica.datos.ListaArchivos;
import logica.protocolo.peticiones.PeticionDescarga;
import logica.protocolo.respuestas.RespuestaADescarga;
import logica.red.ConexionPeer;

import java.io.IOException;

//*****************************************************************************//
/**
 * Realiza la busqueda de todos los clientes que tienen el fichero indicado.
 * 
 * @author pitidecaner
 * @author Salcedonia
 */
public class ResolutorDeDescargas extends Thread {

    // ATRIBUTOS
    private ConexionPeer _conexion; // Conexión asociada
    private PeticionDescarga _ordenDescarga; // Orden de descarga
    private ListaArchivos _listaGlobal; // Lista de archivos global
    
//  *****************************************************************************//
    /**
     * Constructor de la clase ResolutorDeDescargas.
     * 
     * @param lista la lista global de archivos dados de alta en el sistema
     * @param ordenDescarga la orden dada por el cliente
     * @param conexion la connexion por la que debemos contestar.
     */
    public ResolutorDeDescargas(ListaArchivos lista, PeticionDescarga ordenDescarga, ConexionPeer conexion) {
      
        _ordenDescarga = ordenDescarga;
        _conexion = conexion;
        _listaGlobal = lista;
    }
 
//  *****************************************************************************//
    /**
     * Realiza la búsqueda de las direcciones de todos los clientes que tengan 
     * el fichero indicado. 
     */
    @Override
    public void run(){
       
        // Busca a todos los clientes con este archivo.
        DatosCliente [] propietarios = _listaGlobal.getPropietarios(_ordenDescarga.getHash());
        
        // Compone respuesta
        RespuestaADescarga respuesta = new RespuestaADescarga(propietarios);
        
        // Envía la respuesta al cliente que la solicitó
        try {
            // enviar a cliente.
            _conexion.enviarObjeto(respuesta);
            
        } catch (IOException ex) {
         // TODO: Si se ha interrumpido la conexion el pool se habrá dado cuenta
            // y actuará en consecuencia
        }
        
        // Hemos acabado aquí
        _conexion.listo();
    }
    
}
