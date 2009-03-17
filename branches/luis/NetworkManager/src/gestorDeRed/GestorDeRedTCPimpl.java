/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestorDeRed;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Stack;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pitidecaner
 */
public class GestorDeRedTCPimpl<E> extends Thread implements GestorDeRed<E> {

    private int _puerto;
    private Vector<Receptor<E>> _receptores;

    public GestorDeRedTCPimpl(int port) {
        _puerto = port;
        _receptores = new Stack<Receptor<E>>();
    }

    /**
     * envía un paquete de tipo E a la dirección especificada
     * @param var el paquete a enviar
     * @param destino destino en formato ip v4
     */
    public synchronized void envia(E var, Inet4Address destino, int puerto) throws NetError {
        try {
            Socket s = new Socket(destino, puerto);

            new ObjectOutputStream(s.getOutputStream()).writeObject(var);

        } catch (IOException ex) {
           throw new NetError("error al conectar con " + destino.getHostAddress() + ":" + puerto);
        }
    }
    /**
     * envía un paquete de tipo E a la dirección especificada
     * @param var el paquete a enviar
     * @param host el nombre de host o ip
     * @param port el puerto
     */
    public synchronized void envia(E var, String host, int port)  throws NetError{
        try {
            Socket s = new Socket(host, port);

            new ObjectOutputStream(s.getOutputStream()).writeObject(var);

        } catch (IOException ex) {
             throw new NetError("error al conectar con " + host  + ":" + port);
        }
    }

    /**
     * registra un receptor que se hara cargo de los mensajes recibidos por el
     * puerto especificado en el constructor
     *
     * @param r el receptor debe implementar esta interface.
     */
    public void registraReceptor(Receptor<E> r) {
        this._receptores.add(r);
    }

    /**
     * A partir del momento en el que se llama a este método se expande un
     * hilo el cual estará atento de escuchar un determinado puerto.
     * dicho puerto se deja a discrección de quien instancie la implementación
     * de esta interface.
     */
    public void comienzaEscucha()  throws NetError{
       this.start();
    }

    @Override
    public void run() {
        try {
            ServerSocket sock = new ServerSocket(_puerto);

            // TODO: hacer que esto no sea para siempre, poder pararlo de alguna forma
            while (true){
               Socket s = sock.accept();
                try {
                    E paquete = (E) new ObjectInputStream(s.getInputStream()).readObject();
                    for (Receptor<E> receptor : _receptores) {
                       receptor.recibeMensaje(paquete);
                }
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(GestorDeRedTCPimpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(GestorDeRedTCPimpl.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
