/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

package mensajes.p2p;

import java.io.Serializable;
import mensajes.TipoMensaje;
import mensajes.Mensaje;
import datos.Fragmento;
import java.util.ArrayList;

/**
 *
 * @author Luis Ayuso, José Miguel Guerrero
 */
public class Dame implements Mensaje, Serializable{
    
    private String _nombre;
    private String _hash;    
    private ArrayList<Fragmento> _fragmentos;    
    private String _destino;
    private int    _puerto;

    public Dame( String nombre, String hash, Fragmento[] fragmentos, String destino, int puerto ){
    _nombre = nombre;
    _hash = hash;
    _fragmentos = new ArrayList<Fragmento>();
    for (int i=0; i< fragmentos.length; i++){
        _fragmentos.add(fragmentos[i]);
    }
    _destino = destino;
    _puerto = puerto;
  }

  public Dame( String nombre, String hash, Fragmento[] fragmentos ){
    _nombre = nombre;
    _hash = hash;
    _fragmentos = new ArrayList<Fragmento>();
    for (int i=0; i< fragmentos.length; i++){
        _fragmentos.add(fragmentos[i]);
    }
  }

  public String getNombre(){
    return _nombre;
  }

  public String getHash(){
    return _hash;
  }

  public ArrayList<Fragmento> getFragmento(){
    return _fragmentos;
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
