package peerToPeer.subidas;

import gestorDeFicheros.GestorCompartidos;
import java.util.LinkedList;
import java.util.Queue;
import mensajes.p2p.Dame;
import mensajes.p2p.Toma;
import peerToPeer.egorilla.GestorEgorilla;

/**
 * 
 * @author JosÃ© Miguel Guerrero
 */

public class AlmacenSubidas extends Thread{

    private Queue<Dame> _cola;
    private GestorEgorilla _gestor;
    
    public AlmacenSubidas(GestorEgorilla gestor){
        _gestor=gestor;
        _cola = new LinkedList<Dame>();
        setName("Almacen Subidas");
        start();
    }
    
    public synchronized void nuevaSubida (Dame mensaje){
        _cola.add(mensaje);
        this.notify();
    }

    @Override
    public synchronized void run() {
        try {
            wait();
            while (!_cola.isEmpty()) {
                Dame msjDame = _cola.poll();
                //ahora se pide uno solo, si se envia Vector<Fragmento> crearlo en bucle
                Byte[] informacion=GestorCompartidos.getInstancia().dameBytesDelFragmento(msjDame.getFragmento());
                Toma mensajeToma=new Toma(msjDame.getNombre(),msjDame.getHash(),msjDame.getFragmento().getOffset(),informacion,msjDame.ipDestino(),msjDame.puertoDestino());
                _gestor.addMensajeParaEnviar(mensajeToma);
            }
        } catch (InterruptedException ex) {
            System.out.println("*** Soy Almacen de Subidas: "+ex);
        }
    }

    public void parar(){
        this.interrupt();
    }

    public synchronized void despierta (){
         this.notify();
    }
}
