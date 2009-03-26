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

    public Paquete(E data, String ipRemite, int puertoRemite) {
        this._data = data;
        this._ipRemite = ipRemite;
        this._puertoRemite = puertoRemite;
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
}
