/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestorDeRed;

import java.net.Inet4Address;

/**
 *
 * @author pitidecaner
 */
public interface GestorDeRed<E>{

    /**
     * A partir del momento en el que se llama a este método se expande un
     * hilo el cual estará atento de escuchar un determinado puerto.
     * dicho puerto se deja a discrección de quien instancie la implementación
     * de esta interface.
     */
    public void comienzaEscucha();

    /**
     * acaba con la escucha y libera los recursos
     */
    public void terminaEscucha();
    
    /**
     * envía un paquete de tipo E a la dirección especificada
     * @param var el paquete a enviar
     * @param destino destino en formato ip v4
     * @param port puerto a enviar
     */
    public void envia(E var, Inet4Address destino, int port) throws NetError;
    /**
     * envía un paquete de tipo E a la dirección especificada
     * @param var el paquete a enviar
     * @param host el nombre de host o ip
     * @param port el puerto
     */
    public void envia(E var, String host, int port) throws NetError;

    /**
     * registra un receptor que se hara cargo de los mensajes recibidos por el
     * puerto especificado en el constructor
     *
     * @param r el receptor debe implementar esta interface.
     */
    public void registraReceptor(Receptor<E> r);

    /**
     * indica que mantendremos una conexion con este host, si se pierde la comunicación
     * se propagará un error al los receptores
     *
     * @param host el host al que mantener vigilado
     * @param puerto el puerto por el que escucha
     */
    public void addConexion(String host, int puerto);

    /**
     * elimina la conexion con determinado host, de forma que si ya no necesitamos
     * saber de él se elimina
     *
     * @param host el host que no deseamos saber si esta online
     */
    public void eliminaConexion(String host);
    
}