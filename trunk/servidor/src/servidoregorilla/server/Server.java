/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servidoregorilla.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import servidoregorilla.protocolo.Cliente;
import servidoregorilla.Datos.ListaArchivos;
import servidoregorilla.protocolo.Protocolo;
import servidoregorilla.Datos.TablaClientes;

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
    
    public Protocolo listen() throws IOException{
        Socket conn  = _soket.accept();
        
        ObjectInputStream flujo = new ObjectInputStream(conn.getInputStream());
        switch (flujo.readInt()){    // read protocol version
            case 1:
                flujo.close();
                 return new Cliente(conn, _l, _t);
            case 2:
                // hacer otra cosa, otra version, modo consola o lo que sea
                break;
            default:
                throw new IOException("version protocolo invalida");
        }
        return null;
    }
}
