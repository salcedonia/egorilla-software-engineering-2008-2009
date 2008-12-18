/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clienteTest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import servidoregorilla.paquete.Archivo;
import servidoregorilla.Datos.ListaArchivos;
import servidoregorilla.paquete.DatosCliente;
import servidoregorilla.paquete.DownloadOrder;
import servidoregorilla.paquete.DownloadOrderAnswer;
import servidoregorilla.paquete.Query;
import servidoregorilla.paquete.QueryAnswer;
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
            ObjectInputStream in = new ObjectInputStream(conexion.getInputStream());

            DatosCliente me = new DatosCliente();
            me.setNombreUsuario("dePruebas");
            me.setPuertoEscucha(4000);

            out.writeObject(me);

            // Creamos una _lista con 2 archivos
            ListaArchivos arch = new ListaArchivos();
            Archivo a = new Archivo();
          //  a._hash = "abc";
            a._nombre = "hola que tal";
            a._tamaño = 1231;
            a._tipo = TipoArchivo.VIDEO;
            arch.añadirArchivo(a);
            
            a = new Archivo();
          //  a._hash = "abcd";
            a._nombre = "adios";
            a._tamaño = 123431;
            a._tipo = TipoArchivo.AUDIO;
            arch.añadirArchivo(a);
            
            // Mandamos la _lista de archivos asociada al cliente
            out.writeObject(arch);
            
            // prueba de Query
            Query q =  new Query();
            q.setCadenaBusqueda("adios");
       
            out.writeObject(q);
            
            QueryAnswer respuesta = (QueryAnswer) in.readObject();
            
            for (int i=0; i< respuesta.getLista().length; i++) 
                System.out.println(respuesta.getLista()[i]._nombre);
             
            // prueba el download order.
            DownloadOrder orden = new DownloadOrder("abcd");
            out.writeObject(orden);
            
            DownloadOrderAnswer resp =(DownloadOrderAnswer) in.readObject();
            
            for (int i=0; i< resp.getLista().length; i++) 
                System.out.println(resp.getLista()[i].getNombreUsuario());
                  
            System.out.print("pulsa una tecla");
            System.in.read();
        
        } 
        catch (IOException ex) {
            Logger.getLogger(ClienteTest.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(ClienteTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
