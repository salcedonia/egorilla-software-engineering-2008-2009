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
public class Altoo implements Mensaje, Serializable{
    
    /**
     * en el caso de parar una descarga, se indica el fichero
     * que se estaba descargando
     */
    private String _hash;

    private String _destino;
    private int    _puerto;
    
    public String getHash(){
        return  _hash;
    }
    public void setHash(String hash){
        _hash = hash;
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

    public TipoMensaje getTipoMensaje() {
       return TipoMensaje.Altoo;
    }
}
