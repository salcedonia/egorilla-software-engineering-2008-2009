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
     * Para notificar que la conexion ha sido completada.
     *
     * @param gestorEGorilla GestorEGorilla de la aplicación.
     * @param ip IP del servidor.
     * @param puerto Puerto del servidor.
     */
    public void conexionCompletada(GestorEgorilla gestorEGorilla, String ip, int puerto);

    /**
     * Para notificar que se ha completado la desconexion.
     * 
     * @param gestorEGorilla GestorEGorilla de la aplicación.
     */
    public void desconexionCompletada(GestorEgorilla GestorEGorilla);
    
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
     * Para notificar que la conexion se ha perdido.
     *
     * @param gestorEGorilla GestorEGorilla de la aplicación.
     */
    public void perdidaConexion(GestorEgorilla GestorEGorilla);
    
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
