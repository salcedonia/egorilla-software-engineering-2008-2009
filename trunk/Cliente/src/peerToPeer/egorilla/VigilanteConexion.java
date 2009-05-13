package peerToPeer.egorilla;

import java.util.ArrayList;
import peerToPeer.EstadoP2P;
import peerToPeer.ObservadorP2P;

/**
 * El vigilante de conexion se encarga de realizar una espera para asegurar que
 * la conexion se realiza en un tiempo de 2 segundos.
 *
 * @author Luis Ayuso
 */
public class VigilanteConexion extends Thread{

    ArrayList<ObservadorP2P> _observadores;

    public VigilanteConexion() {
        _observadores = new ArrayList<ObservadorP2P>();
        this.setName("vigilante conexión");
    }

    @Override
    public synchronized void run (){
        try {
            wait(10000);

            synchronized (this) {
                for (ObservadorP2P observadorP2P : _observadores) {
                    observadorP2P.cambioEstado(EstadoP2P.DESCONECTADO, null, -1);
                }
            }
        } catch (InterruptedException ex) {
             System.out.println("*** Soy el vigiliante de la conexion, todo ha ido guay");
        }
    }

    /**
     * la conexion se ha realizado satisfactoriamente, por lo que se acaba con
     * este hilo
     */
    public synchronized  void conexionCompletada(){
        this.interrupt();
    }

    /**
     * agrega un observador para notificar errores de conexion
     * @param obs
     */
    public void agregarObservador(ObservadorP2P obs){
        _observadores.add(obs);
    }
}
