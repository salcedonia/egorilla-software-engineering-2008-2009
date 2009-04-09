/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package peerToPeer.egorilla;

/**
 * Observador utilizado para la notificacion de eventos al GestorEgorilla
 * 
 * @author Jose Miguel Guerrero
 */
public interface ObservadorGestorEgorilla {
    //para notificar que la conexion ha sido completada
    public void conexionCompleta();
    //para notificar que se han recibido los resultados de la busqueda
    public void resultadosBusqueda();
    //para notificar que la descarga ha sido completada
    public void finDescarga();
    //para notificar que la conexion se ha perdido
    public void perdidaConexion();
}
