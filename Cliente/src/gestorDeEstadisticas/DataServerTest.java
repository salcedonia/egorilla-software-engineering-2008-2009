/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestorDeEstadisticas;

import java.util.logging.Level;
import java.util.logging.Logger;
import peerToPeer.descargas.ObservadorAlmacenDescargas;

/**
 *
 * @author Qiang
 */
public class DataServerTest  extends Thread implements ObservadorAlmacenDescargas{
    GestorEstadisticas gestor = GestorEstadisticas.getInstacia();
    public DataServerTest() {
        
    }

    @Override
    public void run() {
        
        while (true) {
            try {
                fragmentoDescargado("");
                Thread.sleep(15000);
            } catch (InterruptedException ex) {
                Logger.getLogger(DataServerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void nuevaDescarga(String nombre, String hash, int tamanio) {
        //
    }

    @Override
    public void fragmentoDescargado(String hash) {
        gestor.llegadaDatosDescarga(512);
    }

    @Override
    public void eliminarDescarga(String _hash) {
      // descargado
    }

}
