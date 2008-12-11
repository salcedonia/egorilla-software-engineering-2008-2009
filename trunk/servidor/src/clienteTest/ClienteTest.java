/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clienteTest;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import servidoregorilla.paquete.Archivo;
import servidoregorilla.Datos.ListaArchivos;
import servidoregorilla.paquete.TipoArchivo;

/**
 * Clase que implemeta un test de prueba del servidor.
 * 
 * @author pitidecaner
 * @author Salcedonia
 */
public class ClienteTest {

    /**
     * Método main del test.
     * 
     * @param args Argumentos de la aplicación de la línea de comandos.
     */
    public static void main(String[] args){
        
        try {
            
            // Crea una conexion de prueba
            Socket conexion = new Socket("127.0.0.1", 6969);
            
            ObjectOutputStream out = new ObjectOutputStream(conexion.getOutputStream());
            
            // Realizamos la identificación
            out.writeInt(1);                             // Versión del protocolo.
            out.writeInt(4000);                          // puerto de escucha otros clientes.
            out.writeObject(new String("pitidecaner"));  // id del usuario.
         
            // Creamos una lista con 2 archivos
            ListaArchivos arch = new ListaArchivos();
            Archivo a = new Archivo();
            a._hash = "abc";
            a._nombre = "hola que tal";
            a._tamaño = 1231;
            a._tipo = TipoArchivo.VIDEO;
            arch.addArchivo(a);
            
            a = new Archivo();
            a._hash = "abcd";
            a._nombre = "adios";
            a._tamaño = 123431;
            a._tipo = TipoArchivo.AUDIO;
            arch.addArchivo(a);
            
            // Mandamos la lista de archivos asociada al cliente
            out.writeObject(arch);
        } 
        catch (UnknownHostException ex) {
        
            Logger.getLogger(ClienteTest.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) {
        
            Logger.getLogger(ClienteTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
