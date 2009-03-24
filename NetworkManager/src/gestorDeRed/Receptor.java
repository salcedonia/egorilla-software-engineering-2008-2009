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
}
