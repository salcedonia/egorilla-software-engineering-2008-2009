/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import Networking.PeerConn;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import servidoregorilla.Datos.ListaArchivos;
import servidoregorilla.paquete.Archivo;
import servidoregorilla.paquete.DatosCliente;
import servidoregorilla.paquete.TipoArchivo;

/**
 *
 * @author usuario_local
 */
public class ControlAplicacion {
    
    private static PeerConn _conn;
    private static boolean  _conectado = false;

    public static boolean conectado() {
        return _conectado;
    }
    
    public static void conectar() throws IOException{
              // Crea una conexion de prueba
            Socket conexion = new Socket("127.0.0.1", 6969);
            
            _conn = new PeerConn(conexion);
          
            DatosCliente me = new DatosCliente();
            me.setNombreUsuario("dePruebas");
            me.setPuertoEscucha(4000);

            _conn.enviarObjeto(me);

            // Creamos una _lista con 2 archivos
            ListaArchivos arch = new ListaArchivos();
            Archivo a = new Archivo();
            a._hash = "abc";
            a._nombre = "hola que tal";
            a._tamaño = 1231;
            a._tipo = TipoArchivo.VIDEO;
            arch.añadirArchivo(a);
            
            a = new Archivo();
            a._hash = "abcd";
            a._nombre = "adios";
            a._tamaño = 123431;
            a._tipo = TipoArchivo.AUDIO;
            arch.añadirArchivo(a);
            
            // Mandamos la _lista de archivos asociada al cliente
            _conn.enviarObjeto(arch);    
            
            _conectado = true;
    }
    
    public static void close(){
        try {
            _conn.cerrarComunicacion();
        } catch (IOException ex) {
          // nothing
        }
        _conectado = false;
    }

    public static void query(String cad){
        
    }
    
    public static void bajar(String hash){
        
    }
}
