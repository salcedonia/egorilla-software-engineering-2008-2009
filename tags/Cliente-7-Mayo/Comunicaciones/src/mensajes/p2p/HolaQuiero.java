/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

package mensajes.p2p;

import java.io.Serializable;
import mensajes.TipoMensaje;
import mensajes.Mensaje;
import datos.Archivo;

/**
 *
 * @author Luis Ayuso
 */
public class HolaQuiero implements Mensaje, Serializable{

  private String _nombre;
  
  private String _hash;
    
  private String _destino;
  
  private int    _puerto;


    public HolaQuiero(Archivo a) {
        _nombre = a.getNombre();
        _hash = a.getHash();
    }

    public String getNombre(){
    return _nombre;
  }

  public String getHash(){
    return _hash;
  }

    public TipoMensaje getTipoMensaje() {
       return TipoMensaje.HolaQuiero;
    }
    
    public void setDestino(String destino, int puerto) {
        _destino = destino;
        _puerto  = puerto;
    }
    public String ipDestino() {
        return _destino;
    }
    public int puertoDestino() {
        return _puerto;
    }
    
}
