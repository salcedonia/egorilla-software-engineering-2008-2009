/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servidoregorilla.protocolo;

import networking.PeerConn;
import java.io.Serializable;
import servidoregorilla.datos.ListaArchivos;
import servidoregorilla.datos.TablaClientes;

/**
 *
 * @author pitidecaner
 */
public class QueryResolver extends Thread{
      
    private TablaClientes _tabCli;
    private ListaArchivos _listarch;
    
    public void run(){
        
        
        
    }

    public int getVersion() {
        return 2;
    }

    public void addTablaClientes(TablaClientes t) {
        _tabCli = t;
    }

    public void addListaArchivos(ListaArchivos l) {
        _listarch = l;
    }
}
