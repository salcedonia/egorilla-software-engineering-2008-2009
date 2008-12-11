/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Networking;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Clase que hace las veces de un wrapper para encapsular todas las comunicaciones 
 * con un peer determinado, evitando el uso de streams y otras clases en todas 
 * las comunicaciones relativas a los peers.
 * 
 * @author pitidecaner
 * @author Salcedonia 
 */
public class PeerConn {

    // ATRIBUTOS
    private Socket _conexion; // Conexión entre los peers
    private ObjectInputStream  _objetoEntrada; // Flujo de entrada para objetos
    private ObjectOutputStream _objetoSalida; // Flujo de salida para objetos
    
    /**
     * Constructor de la clase PeerConn. Se genera una clase que representa la 
     * conexión recibida. Esta conexion se puede utilizar para enviar y recibir 
     * datos con un operador remoto sea quien sea.
     * 
     * @param conexion Socket abierto con peer.
     * @throws java.io.IOException Se lanza en caso de error con la conexión.
     */
    public PeerConn(Socket conexion) throws IOException{
        
        _conexion = conexion;
        _objetoEntrada = new ObjectInputStream(conexion.getInputStream());
        _objetoSalida = new ObjectOutputStream(conexion.getOutputStream());
    }

    /**
     * Devuelve la dirección IP asociada a la conexión establecida.
     * 
     * @return La dirección IP asociada a la conexión establecida.
     */
    public InetAddress getIP() {
        
        // TODO: comentar y parsear la dirección
        return _conexion.getInetAddress();
    }
 
    /**
     * Lee un integer transmitido por el peer. Esta lectura es bloqueante, por 
     * lo que si no se produce, el hilo se quedará bloqueado en este punto sin 
     * progresar hasta recibir la instancia.
     * 
     * @return El entero recibido por el conexion.
     * @throws java.io.IOException Se lanza en caso de error con la conexión.
     */
    public synchronized int reciveInt() throws IOException{
        
        return _objetoEntrada.readInt();
    }
    
    /**
     * Envía un número entero con signo por el conexion.
     * 
     * @param num en entero a enviar por el conexion.
     * @throws java.io.IOException Se lanza en caso de error con la conexión.
     */
    public synchronized void sendInt(int num) throws IOException{
    
        _objetoSalida.writeInt(num);
    }
    
    /**
     * Lee un objeto transimitido por el peer. Esta lectura es bloqueante, por 
     * lo que si no se produce, el hilo se quedará bloqueado en este punto sin 
     * progresar hasta recibir la instancia.
     * 
     * @return El objeto leído del peer.
     * @throws java.io.IOException Se lanza en caso de error de transmisión.
     * @throws java.lang.ClassNotFoundException Se lanza si el objeto no puede 
     * ser reconocido.
     */
    public synchronized  Object reciveObject() throws IOException, ClassNotFoundException{
        
        return _objetoEntrada.readObject();   
    }
    
   /**
    * Envía un objeto por el conexion. 
    * 
    * @param o Objeto a transmitir, debe ser serializable.
    * @throws java.io.IOException Se lanza en caso de error con la conexión.
    */
    public void sendObject(Object o) throws IOException{
        
        _objetoSalida.writeObject(o);
    }
    
    /**
     * Finaliza conexión con peer terminando toda comunicación con éste, cerrando
     * la conexión y los flujos de entrada y de salida.
     * 
     * @throws java.io.IOException Se lanza en caso de error.
     */
    public synchronized void close() throws IOException{
        
        _objetoEntrada.close();
        _objetoSalida.close();
        _conexion.close();
    }
}