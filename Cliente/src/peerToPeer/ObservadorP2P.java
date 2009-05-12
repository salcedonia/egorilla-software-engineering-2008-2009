package peerToPeer;

import peerToPeer.egorilla.*;
import datos.Archivo;

/**
 * Interfaz que deben implementar todos los observadores sobre el objeto 
 * GestorEgorilla.
 * Se pasa siempre el objeto GestorEgorilla al observador por si fuera necesario 
 * acceder a él para recuperar información adicional para tratar el cambio.    
 *
 * @author Jose Miguel Guerrero, Javier Sánchez.
 */
public interface ObservadorP2P {
    
    /**
     * Notifica cualquier cambio de estado del protocolo P2P
     *
     * @param estado EstadoP2P de la aplicación. Este el en el nuevo estado en el 
     * que se encuentra la conexión de la aplicación.
     * @param ip IP del servidor. Este párametro se utiliza cuando se pasa al estado
     *  EstadoP2P.CONECTADO asi indica al servidor que se conectó, en el resto de los
     *  estados será ignorado.
     * @param puerto Puerto del servidor. Este párametro se utiliza cuando se pasa al estado
     *  EstadoP2P.CONECTADO asi indica al puerto que se conectó, en el resto de los
     *  estados será ignorado.
     */
    public void cambioEstado(EstadoP2P estado, String ip, int puerto);

    
    /**
     * Para notificar que se han recibido los resultados de la busqueda.
     *
     * @param gestorEGorilla GestorEGorilla de la aplicación.
     * @param nombre Nombre del archivo a buscar.
     * @param lista Lista de archivos de la última búsqueda.
     */
    public void resultadosBusqueda(GestorEgorilla GestorEGorilla, String nombre,  Archivo[] lista);
    
    /**
     * Para notificar que la descarga ha sido completada.
     * 
     * @param gestorEGorilla GestorEGorilla de la aplicación.
     * @param arch el archivo finalizado
     */
    public void finDescarga(GestorEgorilla GestorEGorilla, Archivo arch);
   
    
    /**
     * Para notificar que la descarga ha sido pausada.
     * 
     * @param gestorEGorilla GestorEGorilla de la aplicación.
     * @param arch el archivo finalizado
     */
    public void pausaDescarga(GestorEgorilla GestorEGorilla, Archivo arch);
    
    /**
     * Para notificar que la descarga ha sido pausada.
     * 
     * @param gestorEGorilla GestorEGorilla de la aplicación.
     * @param arch el archivo finalizado
     */
    public void eliminarDescarga(GestorEgorilla GestorEGorilla, Archivo arch);

   
}
