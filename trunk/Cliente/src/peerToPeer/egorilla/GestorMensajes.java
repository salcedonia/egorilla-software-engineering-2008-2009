package peerToPeer.egorilla;

import gestorDeErrores.ControlDeErrores;
import gestorDeErrores.ErrorEGorilla;
import gestorDeRed.GestorDeRed;
import gestorDeRed.NetError;
import java.util.LinkedList;
import java.util.Queue;
import mensajes.Mensaje;

/**
 * gestiona la salida de mensajes
 * TODOS los mensajes salen desde esta clase.
 * si se implementa algún control de flujo de subida DEBE ser aqui
 * @author Luis Ayuso
 */
public class GestorMensajes extends Thread{
    private Queue<Mensaje> _cola;
    private GestorDeRed<Mensaje> _red;

    public GestorMensajes(GestorDeRed<Mensaje> red) {
        _cola = new LinkedList<Mensaje>();
        _red = red;
        this.setName("Cola de Mensajes");
        this.run();
    }

    public synchronized  void addMensajeParaEnviar(Mensaje msj){
        _cola.add(msj);
        this.notify();
    }

    @Override
    public void run() {
        try {
            wait();
            while (!_cola.isEmpty()) {
                Mensaje msj = _cola.poll();
                try {
                    _red.envia(msj, msj.ipDestino(), msj.puertoDestino());
                } catch (NetError ex) {
                    ControlDeErrores.getInstancia().registrarError(ErrorEGorilla.ERROR_RED,
                            "error al enviar mensaje a " + msj.ipDestino() + ": " +
                            ex.getError());
                }
            }
        } catch (InterruptedException ex) {
            // paramos y listo!
        }
    }

    synchronized void parar(){
        _cola.clear();
        this.interrupt();
    }
}
