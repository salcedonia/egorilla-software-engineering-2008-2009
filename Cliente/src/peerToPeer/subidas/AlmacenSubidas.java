package peerToPeer.subidas;

import gestorDeFicheros.GestorCompartidos;
import java.util.LinkedList;
import java.util.Queue;
import mensajes.p2p.Dame;
import mensajes.p2p.Toma;
import peerToPeer.egorilla.GestorMensajes;

/**
 * 
 * @author José Miguel Guerrero
 */

public class AlmacenSubidas extends Thread{

    private Queue<Dame> _cola;
    private GestorMensajes _envioMensajes;
    
    public AlmacenSubidas(GestorMensajes envioMensajes){
        _envioMensajes= envioMensajes;
        _cola = new LinkedList<Dame>();
        setName("Almacen Subidas");
        //start();
    }
    
    public void nuevaSubida (Dame mensaje){
        _cola.add(mensaje);
        notify();
    }

    @Override
    public void run() {
        try {
            
            while (true) {
                if (_cola.isEmpty()) {
                    wait();        
                }
                Dame msjDame = _cola.poll();
                //ahora se pide uno solo, si se envia Vector<Fragmento> crearlo en bucle
                Byte[] informacion=GestorCompartidos.getInstancia().dameBytesDelFragmento(msjDame.getFragmento());
                Toma mensajeToma=new Toma(msjDame.getNombre(),msjDame.getHash(),msjDame.getFragmento().getOffset(),informacion,msjDame.ipDestino(),msjDame.puertoDestino());
                _envioMensajes.addMensajeParaEnviar(mensajeToma);
            }
        } catch (InterruptedException ex) {
            System.out.println("*** Soy Almacen de Subidas: "+ex);
        }
    }

    public void parar(){
        interrupt();
    }

    public synchronized void despierta (){
         notify();
    }
}
