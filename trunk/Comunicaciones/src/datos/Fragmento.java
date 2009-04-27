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
                tama = _tama == f.getTama(), 
                hash = _hash.compareTo( f.getHash() ) == 0,
                nombre = _nombre.compareTo( f.getNombre() ) == 0;

        return offset && tama && hash && nombre;

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
