/**
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorDeRed.TCP;

import gestorDeRed.*;
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
 * implementacion de la interfaz de red usnado comunicaciones TCP
 *
 * @author Luis Ayuso
 */
public class GestorDeRedTCPimpl<E> extends Thread implements GestorDeRed<E> {

    private int _puerto;
    private Vector<Receptor<E>> _receptores;
    private ServerSocket _sock;

    private GestorConexiones _conexiones;

    public GestorDeRedTCPimpl(int port) {
        _puerto = port;
        _receptores = new Stack<Receptor<E>>();
        _conexiones  = new GestorConexiones(this);
        _conexiones.start();
    }

    /**
     * envía un paquete de tipo E a la dirección especificada
     * @param var el paquete a enviar
     * @param destino destino en formato ip v4
     */
    @Override
    public synchronized void envia(E var, Inet4Address destino, int puerto) throws NetError {
        this.envia(var, destino.getHostAddress(), puerto);
    }

    /**
     * envía un paquete de tipo E a la dirección especificada
     * 
     * @param var el paquete a enviar
     * @param host el nombre de host o ip
     * @param port el puerto
     */
    @Override
    public synchronized void envia(E var, String host, int port) throws NetError {     
        try {
            Socket s = new Socket(host, port);
            Paquete<E> p = new Paquete<E>(var, s.getLocalAddress().getHostAddress(), _puerto);

            new ObjectOutputStream(s.getOutputStream()).writeObject(p);
            
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException ex) {
//                ex.printStackTrace();
//            }

            s.close();
        } catch (IOException ex) {
            this.generaErrorConexion(host);
            throw new NetError("error en envio a " + host);
        }
    }

    /**
     * registra un receptor que se hara cargo de los mensajes recibidos por el
     * puerto especificado en el constructor
     *
     * @param r el receptor debe implementar esta interface.
     */
    @Override
    public void registraReceptor(Receptor<E> r) {
        this._receptores.add(r);
    }

    /**
     * A partir del momento en el que se llama a este método se expande un
     * hilo el cual estará atento de escuchar un determinado puerto.
     * dicho puerto se deja a discrección de quien instancie la implementación
     * de esta interface.
     */
    @Override
    public void comienzaEscucha() {
        this.start();
    }

    /**
     * acaba con la escucha y libera los recursos
     */
    @Override
    public void terminaEscucha() {

        this.interrupt();
    }

    @Override
    public void run() {

        this.setName("Gestor TCP");

        try {
            try {
                _sock = new ServerSocket(_puerto);

                // TODO: hacer que esto no sea para siempre, poder pararlo de alguna forma
                while (true) {
                    Socket s = _sock.accept();
                    try {
                        Paquete<E> paquete = (Paquete<E>) new ObjectInputStream(s.getInputStream()).readObject();

                        /***********************
                         *    NOTA
                         * 
                         * la ip, se usa la publica,
                         * que es la que se ve desde el lado
                         * del que recibe, 
                         * 
                         * mientras que el puerto se usa el que 
                         * nos dice el otro peer, no por el que
                         * envio el fichero.
                         * 
                         ***********************/
                        String ip = s.getInetAddress().getHostAddress();
                        
                        // solo si el paquete lleva carga util se envia a los receptores
                        if (paquete.getDatos() != null) {
                            for (Receptor<E> receptor : _receptores) {
                                receptor.recibeMensaje(paquete.getDatos(), ip, paquete.getPuertoRemite());
                            }
                        }
                    } catch (ClassNotFoundException ex) {
                        ex.printStackTrace();
                     
                    }
                }
            } catch (IOException ex) {
                // problemas con el socket?? solo si es la primera vez, sino es que 
                // hemos acabado con la ejecucion
                ex.printStackTrace();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    /**
     * comunica a los oyentes la imposibilidad de hablar con determinado host
     *
     * @param host la ip del destino perdido
     */
    public synchronized  void generaErrorConexion (String host){
        for (Receptor<E> receptor : _receptores) {
                receptor.perdidaDeConexion(host);
            }
    }


    @Override
   public void addConexion(String host, int puerto){
        _conexiones.addConexion(host, puerto);
    }

    @Override
    public void eliminaConexion(String host){
        _conexiones.addConexion(host, _puerto);
    }
}
