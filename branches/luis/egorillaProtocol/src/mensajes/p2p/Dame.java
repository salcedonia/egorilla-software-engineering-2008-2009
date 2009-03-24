/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

package mensajes.p2p;

import java.io.Serializable;
import mensajes.TipoMensaje;
import mensajes.Mensaje;

/**
 *
 * @author Luis Ayuso
 */
public class Dame implements Mensaje, Serializable{

    public TipoMensaje getTipoMensaje() {
        return TipoMensaje.Dame;
    }

}
