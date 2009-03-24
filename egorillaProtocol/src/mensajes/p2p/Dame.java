/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

package mensajes.p2p;

import java.io.Serializable;
import java.util.ArrayList;
import mensajes.TipoMensaje;
import mensajes.Mensaje;
import peerToPeer.Fragmento;

/**
 *
 * @author Luis Ayuso
 */
public class Dame implements Mensaje, Serializable{

    public TipoMensaje getTipoMensaje() {
        return TipoMensaje.Dame;
    }

    
    public String nombre;
    public String hash;
    
    public ArrayList<Fragmento> fragmentos;
    
    
    private String _destino;
    private int    _puerto;
    
    public void setDestino(String destino, int puerto) {
        _destino = destino;
        _puerto  = puerto;
    }
    public String destino() {
        return _destino;
    }
    public int puerto() {
        return _puerto;
    }
}
