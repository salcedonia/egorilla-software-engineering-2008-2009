/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

package gestorDeRed.test;

import gestorDeRed.GestorDeRed;
import gestorDeRed.NetError;
import gestorDeRed.Receptor;
import java.net.Inet4Address;
import java.util.Stack;
import java.util.Vector;

/**
 *
 * es una implementacion de la clase del protocolo de red que no hace nada
 * para pruebas
 * 
 * @author Luis Ayuso
 */
public class GestorDeRedDummyImpl<E> implements GestorDeRed<E>{

    private boolean _escuchando;

    private Vector<E> _enviados;
    
     private Vector<Receptor<E>> _receptores;
    
    public GestorDeRedDummyImpl() {
        _escuchando = false;
        _enviados = new Vector<E>();
        _receptores = new Stack<Receptor<E>>();
    }
    
    public void comienzaEscucha() {
        _escuchando = true;
    }

    public void envia(E var, Inet4Address destino, int port) throws NetError {
        _enviados.add(var);
    }

    public void envia(E var, String host, int port) throws NetError {
        _enviados.add(var);
    }

    public void registraReceptor(Receptor<E> r) {
        _receptores.add(r);
        
    }

    
    /**
     * devuelve todos los paquetes enviados
     * @return los paquetes enviados
     */
    public Vector<E> testGetEnviados(){
        return _enviados;
    }
        
    /**
     * finge la llegada del paquete E por la red
     * @param paquete
     */
    public void testFingeLLegada(E paquete, String ip, int port){
        if (_escuchando){
             for (Receptor<E> receptor : _receptores) 
                receptor.recibeMensaje(paquete,ip, port);
        }
    }

    public void terminaEscucha() {
       _escuchando = false;
    }
}
