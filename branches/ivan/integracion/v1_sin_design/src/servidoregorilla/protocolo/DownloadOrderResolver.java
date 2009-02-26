package servidoregorilla.protocolo;

import networking.PeerConn;
import java.io.IOException;
import servidoregorilla.datos.ListaArchivos;
import servidoregorilla.paquete.DatosCliente;
import servidoregorilla.paquete.DownloadOrder;
import servidoregorilla.paquete.DownloadOrderAnswer;

/*****************************************************************************/
/**
 * Realiza la busqueda de todos los clientes que tienen el fichero indicado
 * 
 * @author pitidecaner
 * @author Salcedonia
 */
public class DownloadOrderResolver extends Thread {

    // ATRIBUTOS
    private PeerConn _conexion; // Conexión asociada
    private DownloadOrder _ordenDescarga; // Orden de descarga
    private ListaArchivos _listaGlobal; // Lista de archivos global
    
/*****************************************************************************/
    /**
     * Constructor de la clase DownloadOrderResolver.
     * 
     * @param lista la lista global de archivos dados de alta en el sistema
     * @param ordenDescarga la orden dada por el cliente
     * @param conexion la connexion por la que debemos contestar.
     */
    public DownloadOrderResolver(ListaArchivos lista, DownloadOrder ordenDescarga, PeerConn conexion) {
      
        _ordenDescarga = ordenDescarga;
        _conexion = conexion;
        _listaGlobal = lista;
    }
 
/*****************************************************************************/
    /**
     * Realiza la búsqueda de las direcciones de todos los clientes que tengan 
     * el fichero indicado. 
     */
    @Override
    public void run(){
       
        // Busca a todos los clientes con este archivo.
        DatosCliente [] propietarios = _listaGlobal.getPropietarios(_ordenDescarga.getHash());
        
        // Compone respuesta
        DownloadOrderAnswer respuesta = new DownloadOrderAnswer(propietarios);
        
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
