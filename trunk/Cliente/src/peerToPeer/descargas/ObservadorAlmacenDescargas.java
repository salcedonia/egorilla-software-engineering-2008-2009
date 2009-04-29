/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package peerToPeer.descargas;

/**
 *
 * @author Jose Miguel Guerrero
 */
public interface ObservadorAlmacenDescargas {
    //notifica una nueva descarga
    public void nuevaDescarga(String nombre, String hash, int tamanio);
    //notifica un fragmento completado
    public void fragmentoDescargado(String hash);
}
