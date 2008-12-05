/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clienteTest;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import servidoregorilla.paquete.Archivo;
import servidoregorilla.Datos.ListaArchivos;

/**
 *
 * @author pitidecaner
 */
public class ClienteTest {

    public static void main(String[] args) {
        try {

            Socket con = new Socket("127.0.0.1", 6969);
            
            ObjectOutputStream out = new ObjectOutputStream(con.getOutputStream());
            
            out.writeInt(1);                             // protocol version
            out.writeInt(4000);                          // puerto de escucha otros clientes.
            out.writeObject(new String("pitidecaner"));  // id usu
         
            ListaArchivos arch = new ListaArchivos();
            Archivo a = new Archivo();
            a.hash = "abc";
            a.nombre = "hola que tal";
            a.size = 1231;
            arch.addArchivo(a);
            
            a = new Archivo();
            a.hash = "abcd";
            a.nombre = "adios";
            a.size = 123431;
            arch.addArchivo(a);
            
            out.writeObject(arch);
            
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClienteTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClienteTest.class.getName()).log(Level.SEVERE, null, ex);
        }
      
                
    }
}
