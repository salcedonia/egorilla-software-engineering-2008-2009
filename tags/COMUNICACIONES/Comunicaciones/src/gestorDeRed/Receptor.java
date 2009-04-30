/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestorDeRed;

/**
 *
 * @author pitidecaner
 */
public interface Receptor<E> {

   public void recibeMensaje (E msj, String ip, int port);
   
   /**
    * se notifica a los receptores que se ha perdido la conexion con
    * un peer
    * 
    * @param ip ip del host con el que se ha perdido la conexión
    */
   public void perdidaDeConexion (String ip);
}
