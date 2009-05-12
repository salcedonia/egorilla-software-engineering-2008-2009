package peerToPeer.egorilla;

import java.util.ArrayList;
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
    public void run (){
        try {
            wait(2000);

            synchronized (this) {
                for (ObservadorP2P observadorP2P : _observadores) {
                    observadorP2P.conexionNoCompletada();
                }
            }
        } catch (InterruptedException ex) {
            // se ha interrumpido, por lo que se ha realizado la conexión.
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
