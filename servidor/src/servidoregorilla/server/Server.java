/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servidoregorilla.server;

import java.io.IOException;
import java.net.ServerSocket;
import servidoregorilla.Cliente;
import servidoregorilla.ListaArchivos;
import servidoregorilla.TablaClientes;

/**
 *
 * @author pitidecaner
 */
public class Server {

    private ServerSocket _soket;
    
    private ListaArchivos _l;
    private TablaClientes _t;
    
    public Server(int puerto, ListaArchivos l, TablaClientes t) throws IOException{
        _soket = new ServerSocket(puerto);
        
    }
    
    public Cliente listen() throws IOException{
        return new Cliente(_soket.accept(), _l, _t);
    }
}
