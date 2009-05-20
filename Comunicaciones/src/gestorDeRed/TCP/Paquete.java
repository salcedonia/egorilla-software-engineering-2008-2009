/* 
	This file is part of eGorilla.

    eGorilla is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    eGorilla is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with eGorilla.  If not, see <http://www.gnu.org/licenses/>.
*/
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
