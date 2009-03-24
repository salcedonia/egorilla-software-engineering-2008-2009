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
public class Tengo implements Mensaje, Serializable{

    public TipoMensaje getTipoMensaje() {
       return TipoMensaje.Tengo;
    }

    public String nombre;
    public String hash;
    
    public ArrayList<Fragmento> fragmentos;
}
