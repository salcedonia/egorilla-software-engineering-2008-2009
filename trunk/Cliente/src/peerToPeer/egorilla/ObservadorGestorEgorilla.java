/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

package peerToPeer.egorilla;

import datos.Archivo;

/**
 * Interfaz que deben implementar todos los observadores sobre el objeto GestorEgorilla
 * 
 * @author Jose Miguel Guerrero
 * modificado por Javier Sánchez.
 */
public interface ObservadorGestorEgorilla {
    //
    //Se pasa siempre el objeto GestorEgorilla al observador por si fuera necesario 
    //acceder a él para recuperar información adicional para tratar el cambio.    
    //
    //para notificar que la conexion ha sido completada
    public void conexionCompletada(GestorEgorilla obj, String ip, int port);
    //para notificar que se ha completado la desconexion
    public void desconexionCompletada(GestorEgorilla obj);
    //para notificar que se han recibido los resultados de la busqueda
    public void resultadosBusqueda(GestorEgorilla obj, String cad,  Archivo[] lista);
    //para notificar que la descarga ha sido completada
    public void finDescarga(GestorEgorilla obj);
    //para notificar que la conexion se ha perdido
    public void perdidaConexion(GestorEgorilla obj);
}
