/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

package mensajes.p2p;

import java.io.Serializable;
import mensajes.TipoMensaje;
import mensajes.Mensaje;
import protocoloEgorilla.Archivo;

/**
 *
 * @author Luis Ayuso
 */
public class HolaQuiero implements Mensaje, Serializable{

    public TipoMensaje getTipoMensaje() {
       return TipoMensaje.HolaQuiero;
    }

    public HolaQuiero(Archivo a) {
        nombre = a.getNombre();
        hash = a.getHash();
    }
    
    public String nombre;
    public String hash;
    
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
