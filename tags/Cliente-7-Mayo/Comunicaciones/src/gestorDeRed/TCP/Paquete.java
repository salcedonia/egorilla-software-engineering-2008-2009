/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

package gestorDeRed.TCP;

import java.io.Serializable;

/**
 *
 * el paquete en si enviado por GestorDeRedTCP
 * 
 * 
 * @author Luis Ayuso
 */
class Paquete<E> implements Serializable{

    private E _data;
    private String _ipRemite;
    private int    _puertoRemite;
    
    private boolean _esAck;
    private int     _ackTimeout;
    private boolean _bomba;
    
    public Paquete(E data, String ipRemite, int puertoRemite) {
        this._data = data;
        this._ipRemite = ipRemite;
        this._puertoRemite = puertoRemite;
        this._bomba = false;
    }
    
    public E getDatos(){
        return _data;
    }
    
    public String getRemite(){
        return _ipRemite;
    }
    
    public int getPuertoRemite(){
        return _puertoRemite;
    }
    
    public boolean esAck(){
        return _esAck;
    }
    
    public int getAckTimeOut(){
        return _ackTimeout;
    }

    void creaBomba(){
        _bomba  =true;
    }
    
    boolean esBomba() {
        return _bomba;
    }
}
