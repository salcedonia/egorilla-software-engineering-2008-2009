/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

package mensajes.serverclient;

import mensajes.Mensaje;
import mensajes.TipoMensaje;

/**
 *
 * Este mensaje se emite como confirmación de conexion con el servidor.
 * 
 * 
 * @author Luis Ayuso
 */
public class Bienvenido implements Serializable, Mensaje{

    private String _destino;
    private int _puerto;
    
    /**
     *  numero de archivos dados de alta en el sistema
     */
    public long numeroDeArchivos;
    /**
     * numero de peers conectados
     */
    public long numeroDePeers;
   
    public TipoMensaje getTipoMensaje() {
       return TipoMensaje.Bienvenido;
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
