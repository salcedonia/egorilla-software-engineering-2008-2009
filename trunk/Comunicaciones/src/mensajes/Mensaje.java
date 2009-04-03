/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

package mensajes;

import mensajes.TipoMensaje;

/**
 *
 * @author Luis Ayuso
 */
public interface Mensaje {
    public TipoMensaje getTipoMensaje();
    
    public void setDestino (String destino, int puerto);
    public String ipDestino();
    public int puertoDestino();
}
