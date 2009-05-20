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

package datos;

import java.io.*;
/**
 * 
 * representa la información que define un fragmento de un
 * fichero.
 *
 * @author Luis Ayuso
 */
public class Fragmento implements Serializable{

    /**
     * nombre del fichero al que pertence
     */
    private String _nombre;
    /**
     * hash que lo identifica
     */
    private String _hash;
    
    /**
     * tamaño del fragmento
     */
    private long _tama;
    /**
     * byte inical del fragmento
     */
    private long _offset;
    

    public Fragmento(){
    }

    public Fragmento(String nombre, String hash, long tama, long offset){
      _nombre = nombre;
      _hash = hash;
      _tama = tama;
      _offset = offset;
    }

    public String getNombre(){
      return _nombre;
    }

  public String getHash(){
    return _hash;
  }

  public long getOffset(){
    return _offset;
  }

  public long getTama(){
    return _tama;
  }

  @Override
  public String toString(){
    return "Nombre:"+getNombre()+" Hash:"+getHash()+" Offset:"+getOffset()+" Tama:"+getTama();
  }
  
  @Override
    public boolean equals(Object o) {
      try{

        Fragmento f = (Fragmento) o;

        boolean offset = _offset == f.getOffset(), 
                /*tama = _tama == f.getTama(), */
                hash = _hash.compareTo( f.getHash() ) == 0;
                /*nombre = _nombre.compareTo( f.getNombre() ) == 0;*/

        return offset && /*tama &&*/ hash /*&& nombre*/;

      }catch( Exception e ){
        //Si salta la excepcion en el casting es que no era una instacia de.        
        //e.printStackTrace();
        return false;
      }/*finally{
        System.out.println("No era un fragmento, error, false");
        return false;
      }*/
    }
}
