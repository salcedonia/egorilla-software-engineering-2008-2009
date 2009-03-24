/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

package protocoloEgorilla;

import gestorDeRed.GestorDeRed;
import gestorDeRed.GestorDeRedTCPimpl;
import gestorDeRed.NetError;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import mensajes.Mensaje;
import mensajes.serverclient.PeticionConsulta;
import mensajes.serverclient.PeticionDescarga;
import peerToPeer.Fragmento;
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
    
    
    private boolean _conectado;
    private String  _serverIP;
    private int  _serverPort;
    
    public GestorEgorilla(GestorDescargas gd) {
        _colaSalida = new LinkedList<Mensaje>();
        _gestorDescargas = gd;
        _gestorSubidas = new GestorSubidas(this);
    }
    
    /**
     * realiza la concexion con el servidor egorilla. 
     * este metodo es sincrono, por lo que estaremos dentro de el hasta que 
     * se realice toda la comunicación, ya sea correcta o erronea.
     * 
     * @return si la conexion ha sido satisfactoria
     */
    public boolean conectaServidor(){
        // realiza la conexion por pasos.
        // para ello envia los datos al servidor y espera respuesta
        
        
        _conectado = true;
        return _conectado;
    }
    
    /**
     * pone el p2p en modo escucha, de forma que se atiende a otros
     * clientes
     *
     */
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

    /**
     * se da la orden de comenzar una descarga.
     * esto dispara la colsulta de peers al servidor y 
     * despues comenzarán las negociaciones con estos
     * @param a archivo a descargar
     */
    public void nuevaDescarga(Archivo a){
        // se realiza la peticion de descarga al servidor
        
        Mensaje  msj = new PeticionDescarga(a._hash);
        msj.setDestino(_serverIP, _serverPort);
        
        // si hemos conectado antes...
        // lo envia al servidor.
        if (!_conectado){
            // TODO: notificar error
        }
        else
            this.addMensajeParaEnviar(msj);      
    } 
    
    /**
     * se realiza una consulta, el método es asincrono, así que no espera respuesta
     * la ejecución continua. 
     * La respuesta llegara a la parte serividora, la cual lo comunicará a sus listeners
     * 
     * @param query la consulta a hacer.
     */
    public void nuevaConsulta (String query){
        // crea el paquete consulta,
        Mensaje msj = new PeticionConsulta(query);
        msj.setDestino(_serverIP, _serverPort);
        
        // si hemos conectado antes...
        // lo envia al servidor.
        if (!_conectado){
            // TODO: notificar error
        }
        else
            this.addMensajeParaEnviar(msj);
        
    }
   
    /**
     * Se indica que este fichero se transmitira a este peer. 
     * 
     * 
     * @param a
     * @param ip
     * @param puerto
     * @param fragmentos
     */
    public void nuevaSubida(Archivo a, String ip, int puerto,ArrayList<Fragmento> fragmentos){
        
    }
            
    /**
     * se encola un mensaje para darle salida.
     * los mensajes se encolan primero para poder controlar el flujo.
     * TODO mensaje de salida DEBE pasar por aqui
     * @param msj
     */
    public synchronized void addMensajeParaEnviar(Mensaje msj){
        _colaSalida.add(msj);
    }
    
    public void run(){
        do{
            try {

                // por cada mensaje al que se le deba dar salida, se le da.
                if (!_colaSalida.isEmpty()){
                    Mensaje msj =_colaSalida.poll();       
                    _gestorDeRed.envia(msj, msj.destino(), msj.puerto());
                }
                //this.wait();
            }   catch (NetError ex) {
                    Logger.getLogger(GestorEgorilla.class.getName()).log(Level.SEVERE, null, ex);
            }
        }while (true);
    }
}
