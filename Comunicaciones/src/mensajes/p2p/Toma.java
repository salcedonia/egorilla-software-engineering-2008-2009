/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

package mensajes.p2p;

import java.io.Serializable;
import mensajes.Mensaje;
import mensajes.TipoMensaje;

/**
 *
 * @author Luis Ayuso
 */
public class Toma implements Mensaje, Serializable{

  private String _nombre;
  
  private String _hash;
  
  private long _offset;
  
  private Byte[] _parte;
  
  private String _destino;
  
  private int _puerto;


  public Toma( String nombre, String hash, long offset, Byte[] parte, String destino, int puerto ){
    _nombre = nombre;
    _hash = hash;
    _offset = offset;
    _parte = parte;
    _destino = destino;
    _puerto = puerto;
  }
  
  public TipoMensaje getTipoMensaje(){
    return TipoMensaje.Toma;
  }
  
  public void setDestino(String destino, int puerto) {
    _destino = destino;
    _puerto  = puerto;
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

  public Byte[] getParte(){
    return _parte;
  }
  
  public String ipDestino() {
    return _destino;
  }
  
  public int puertoDestino() {
    return _puerto;
  }
}
