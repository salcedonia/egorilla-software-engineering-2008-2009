/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package peerToPeer.egorilla;

import datos.Archivo;

/**
 * Observador utilizado para la notificacion de eventos al GestorEgorilla
 * 
 * @author Jose Miguel Guerrero
 */
public interface ObservadorGestorEgorilla {
    //para notificar que la conexion ha sido completada
    public void conexionCompleta(String ip, int port);
    //para notificar que se han recibido los resultados de la busqueda
    public void resultadosBusqueda(String cad,  Archivo[] lista);
    //para notificar que la descarga ha sido completada
    public void finDescarga();
    //para notificar que la conexion se ha perdido
    public void perdidaConexion();
}
