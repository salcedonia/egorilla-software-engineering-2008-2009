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

    public TipoMensaje getTipoMensaje() {
        return TipoMensaje.Toma;
    }
    
    public String nombre;
    public String hash;
    
    public long offset;
    public Byte[] chunk;
    
    
    private String _destino;
    private int    _puerto;
    
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
