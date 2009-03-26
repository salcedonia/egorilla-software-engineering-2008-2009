package tareas;

import java.io.IOException;
import datos.*;
import mensajes.serverclient.DatosCliente;
import mensajes.serverclient.PeticionDescarga;
import mensajes.serverclient.RespuestaPeticionDescarga;


/**
 * Realiza la busqueda de todos los clientes que tienen el fichero indicado
 * 
 * @author pitidecaner
 * @author Salcedonia
 */
public class ProcesarPeticionDescarga extends Thread {

    // ATRIBUTOS
    private ConexionCliente _conexion; // Conexión asociada
    private PeticionDescarga _peticionDescarga; // Orden de descarga
    private ArchivoClientes _listaGlobal; // Lista de archivos global
    
    /**
     * Constructor de la clase DownloadOrderResolver.
     * 
     * @param lista la lista global de archivos dados de alta en el sistema
     * @param ordenDescarga la orden dada por el cliente
     * @param conexion la connexion por la que debemos contestar.
     */
    public ProcesarPeticionDescarga(ArchivoClientes lista, PeticionDescarga pDescarga, ConexionCliente conexion) {
      
      _peticionDescarga = pDescarga;
      _conexion = conexion;
      _listaGlobal = lista;
    }
 
    /**
     * Realiza la búsqueda de las direcciones de todos los clientes que tengan 
     * el fichero indicado. 
     */
    @Override
    public void run(){
       
        // Busca a todos los clientes con este archivo.
        DatosCliente[] propietarios = _listaGlobal.getPropietarios( _peticionDescarga.getHash() );
        
        // Compone respuesta
        RespuestaPeticionDescarga respuesta = new RespuestaPeticionDescarga(_peticionDescarga.getNombre(),
                                                                            _peticionDescarga.getHash(),
                                                                            propietarios );
        
        // Envía la respuesta al cliente que la solicitó
        try {
            // enviar a cliente.
            _conexion.getConexion().enviarObjeto(respuesta);
            
        } catch (IOException ex) {
         // TODO: Si se ha interrumpido la conexion el pool se habrá dado cuenta
            // y actuará en consecuencia
        }
        
        // Hemos acabado aquí
        _conexion.getConexion().listo();
    }    
}
