/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

package mensajes.p2p;

import java.io.Serializable;
import mensajes.TipoMensaje;
import mensajes.Mensaje;
import datos.Fragmento;

/**
 *
 * @author Luis Ayuso, José Miguel Guerrero
 */
public class Dame implements Mensaje, Serializable{
    
    private String _nombre;
    private String _hash;    
    private Fragmento _fragmento;    
    private String _destino;
    private int    _puerto;

    public Dame( String nombre, String hash, Fragmento fragmento, String destino, int puerto ){
    _nombre = nombre;
    _hash = hash;
    _fragmento = fragmento;
    _destino = destino;
    _puerto = puerto;
  }

  public Dame( String nombre, String hash, Fragmento fragmento ){
    _nombre = nombre;
    _hash = hash;
    _fragmento = fragmento;
  }

  public String getNombre(){
    return _nombre;
  }

  public String getHash(){
    return _hash;
  }

  public Fragmento getFragmento(){
    return _fragmento;
  }

    public TipoMensaje getTipoMensaje() {
        return TipoMensaje.Dame;
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
