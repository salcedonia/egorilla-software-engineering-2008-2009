/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

package protocoloEgorilla;

import gestorDeRed.GestorDeRed;
import gestorDeRed.GestorDeRedTCPimpl;
import gestorDeRed.NetError;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import mensajes.Mensaje;
import peerToPeer.GestorDescargas;

/**
 *
 * @author Luis Ayuso
 */
public class GestorEgorilla extends Thread{
    
    private Queue<Mensaje> _colaSalida;
   
    private GestorDescargas _gestorDescargas;
    private GestorDeRed<Mensaje> _gestorDeRed;
    
    private GestorSubidas _gestorSubidas;
    
    public GestorEgorilla(GestorDescargas gd) {
        _colaSalida = new LinkedList<Mensaje>();
        _gestorDescargas = gd;
        _gestorSubidas = new GestorSubidas(this);
    }
    
    public void comienzaP2P(){
        // TODO: 6969 fijado a capon
        _gestorDeRed = new GestorDeRedTCPimpl<Mensaje>(6969);
        _gestorDeRed.registraReceptor(new ServidorEgorilla(this, _gestorDescargas));
        try {
            _gestorDeRed.comienzaEscucha();
        } catch (NetError ex) {
            Logger.getLogger(GestorEgorilla.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void nuevaDescarga(Archivo a){
        // TODO: de dnd sacamos los peers?
       
        
    } 
   
    public void nuevaSubida(String nmb, String ip, String puerto){
        
    }
            
    private synchronized void addMensajeParaEnviar(Mensaje msj){
        _colaSalida.add(msj);
    }
    
    public void run(){
        {
        try {

            // por cada mensaje al que se le deba dar salida, se le da.
            this.wait();
        } catch (InterruptedException ex) {
            Logger.getLogger(GestorEgorilla.class.getName()).log(Level.SEVERE, null, ex);
        }
        }while (true);
    }
}
