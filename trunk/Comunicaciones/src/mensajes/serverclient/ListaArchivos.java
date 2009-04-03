package mensajes.serverclient;

import datos.*;
import java.io.Serializable;
import java.util.Vector;
import mensajes.Mensaje;
import mensajes.TipoMensaje;

/**
 * Clase de Lista de archivos que proporciona todos los métodos necesarios para
 * el tratamiento de éste tipo de objetos.
 * La estructura empleada es una tabla hash ya que proporciona una eficiencia
 * mayor que otras estructuras para éste propósito.
 * 
 * @author Luis Ayuso, Ivan Munsuri, Javier Salcedo
 */
public class ListaArchivos extends Vector<Archivo> implements Mensaje, Serializable {

    /**
     * Constructor de la clase ListaArchivos.
     */
    public ListaArchivos() {
        super();
    }

    public TipoMensaje getTipoMensaje() {
      return TipoMensaje.ListaDeArchivos;
    }

    
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
