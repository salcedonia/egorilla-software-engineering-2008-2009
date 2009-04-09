/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

package datos;

/**
 * 
 * representa la informaci�n que define un fragmento de un
 * fichero.
 *
 * @author Luis Ayuso
 */
public class Fragmento {

    /**
     * nombre del fichero al que pertence
     */
    public String _nombre;
    /**
     * hash que lo identifica
     */
    public String _hash;
    
    /**
     * tama�o del fragmento
     */
    public long _tama;
    /**
     * byte inical del fragmento
     */
    public long _offset;
    

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
    public boolean equals(Object o) {
        
      //**Quito el instanceof, Gonazalo lo puso en la lista negra***
        /*if ( f instanceof Fragmento){
            Fragmento b = (Fragmento) f;
            return (offset == b.offset) && (tama == b.tama);
        }
        else
            return false;*/
      try{
        Fragmento f = (Fragmento) o;
        return ( _offset == f.getOffset()) && (_tama == f.getTama());
      }catch( Exception e ){
        //Si salta la excepcion en el casting es que no era una instacia de.        
        //e.printStackTrace();
      }finally{
        return false;
      }
    }
}
