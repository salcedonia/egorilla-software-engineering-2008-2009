/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servidoregorilla.protocolo;

import java.io.Serializable;
import servidoregorilla.datos.ListaArchivos;
import servidoregorilla.datos.TablaClientes;

/**
 *
 * @author pitidecaner
 */
public class DownloadOrderResolver extends Thread {

    public int getVersion() {
      return 3;
    }

    public void addTablaClientes(TablaClientes t) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void addListaArchivos(ListaArchivos l) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
