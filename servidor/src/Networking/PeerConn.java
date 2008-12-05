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
 * Se trata de un wrapper para encapsular todas las comunicaciones con un peer.
 * esto nos evita el uso de streams y otras clases en todas las comunicaciones.
 * 
 * @author pitidecaner
 */
public class PeerConn {

    private Socket             _conn;
    private ObjectInputStream  _objIn;
    private ObjectOutputStream _objOut;
    
    /**
     * Conexion con peer, constructor:
     * 
     * Se genera una clase que representa la conexion recibida. Esta conexion se
     * puede utilizar para enviar y recibir datos con un operador remoto sea quien
     * sea.
     * 
     * @param s socket abierto con peer.
     */
    public PeerConn(Socket s) throws IOException{
        _conn = s;
        _objIn = new ObjectInputStream(s.getInputStream());
        _objOut = new ObjectOutputStream(s.getOutputStream());
    }

    public InetAddress getip() {
        // TODO: comentar y parsear la dirección
        return _conn.getInetAddress();
    }
 
    /**
     * lee un integer transmitido por el peer.
     * esta lectura es bloqueante, por lo que si no se produce, el hilo se quedara
     * bloqueado en este punto sin progresar hasta recibir la instancia.
     * 
     * @return el entero recibido.
    * @throws java.io.IOException en caso de error con la conexion
     */
    public synchronized int reciveInt() throws IOException{
        return _objIn.readInt();
    }
    
    /**
     * Envia un numero entero con signo.
     * 
     * @param i en entero a enviar
    * @throws java.io.IOException en caso de error con la conexion
     */
    public synchronized void sendInt(int i) throws IOException{
        _objOut.writeInt(i);
    }
    
    /**
     * lee un objeto transimitido por el peer.
     * esta lectura es bloqueante, por lo que si no se produce, el hilo se quedara
     * bloqueado en este punto sin progresar hasta recibir la instancia.
     * 
     * @return el objeto leido
     * @throws java.io.IOException en caso de error de transmisión.
     * @throws java.lang.ClassNotFoundException si el objeto no puede ser reconicido.
     */
    public synchronized  Object reciveObject() throws IOException, ClassNotFoundException{
        return _objIn.readObject();   
    }
    
   /**
    * envia un objeto por la conexion establecida.
    * 
    * @param o el objeto a transmitir, debe ser serializable.
    * @throws java.io.IOException en caso de error con la conexion
    */
    public void sendObject(Object o) throws IOException{
        _objOut.writeObject(o);
    }
    
    /**
     * Finaliza conexion con peer.
     * esto termina toda comunicación con este.
     * 
     * @throws java.io.IOException  en caso de error. si la connexion ya esta 
     * cerrada por ejemplo.
     */
    public synchronized   void close() throws IOException{
        _objIn.close();
        _objOut.close();
        _conn.close();
    }
}
