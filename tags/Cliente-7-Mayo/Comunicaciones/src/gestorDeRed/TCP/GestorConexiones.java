/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestorDeRed.TCP;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Hashtable;


/**
 *El gestor de conexiones mantiene comunicacion para comprobar el enlace entre los
 * peers, de forma que comunica
 *
 * se realizaran envios cada 30 segundos, si no es posible se
 * tendra que dar por perdido el host.
 *
 * estos datos son así y van en el codigo, no son parametrizables
 *
 * @author pitidecaner
 */
public class GestorConexiones extends Thread{

    /** los host a los que testeamos */
    ArrayList<String> _listaHosts;
    /** los puertos por los que escuchan */
    Hashtable<String, Integer> _puertos;

    GestorDeRedTCPimpl _red;

    public GestorConexiones(GestorDeRedTCPimpl redTCPimpl) {
        _red = redTCPimpl;

        _listaHosts = new ArrayList<String>();
        _puertos = new Hashtable<String, Integer>();
    }

    @Override
    public synchronized void run() {

        this.setName("gestorConexiones");
        this.setPriority(MIN_PRIORITY);

        long t = 30000; // el tiempo a esperar para el siguiente envio
        try {
            while (true) {

                if (_listaHosts.size() ==0)
                    wait();
                else
                    wait(t);

                // array list para almacenar los que fallen la prueba
                ArrayList<String> tmp = new ArrayList<String>();

                // prueba las conexiones a todos los hosts
                for (String host : _listaHosts) {
                    if (!testConn(host,_puertos.get(host))){
                        tmp.add(host);
                    }
                }

                // elimina los que no han funcionado
                for (String host : tmp) {
                    _red.generaErrorConexion(host);
                    _listaHosts.remove(host);

                    _puertos.remove(host);
                }
            }
        } catch (InterruptedException ex) {
        }
    }

    /**
     * envia un mensaje de keepAlive para comprobar que la conexión sigue
     * en linea
     *
     * @param ip destino al que enviar
     * @param puerto puerto por el que escucha
     * @return si el envio se realizo correctamente tenemos conexion
     */
    private boolean testConn(String ip, int puerto){
        try {
            Socket conn = new Socket(ip, puerto);

            // envia un mensaje paquete sin carga.
            Paquete p = new Paquete(null, ip, puerto);
            new ObjectOutputStream(conn.getOutputStream()).writeObject(p);

            conn.close();
        } catch (UnknownHostException ex) {
           return false;
        } catch (IOException ex) {
            return false;
        }
        return true;
    }

    /**
     * termina con esto, deja de comprobar conexiones
     */
    public void parar(){
        this._listaHosts.clear();
        this._puertos.clear();
        this.interrupt();
    }

    /**
     * añade un host para estar pendiente de que se mantenga la conexión con el
     * 
     * @param host
     * @param puerto
     */
    public synchronized void addConexion(String host, int puerto){
        _listaHosts.add(host);
        _puertos.put(host, puerto);
        this.notify();
    }

    /**
     * elimina la conexion con un peer
     *
     * @param host
     */
    public synchronized void eliminaConexion(String host){
        _listaHosts.remove(host);
        _puertos.remove(host);
        if (_listaHosts.size() == 0)
            this.interrupt();
    }
}
