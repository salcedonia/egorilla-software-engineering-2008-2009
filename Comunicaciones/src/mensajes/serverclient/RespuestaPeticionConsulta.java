/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

package mensajes.serverclient;

import java.io.Serializable;
import mensajes.Mensaje;
import mensajes.TipoMensaje;
import datos.Archivo;

/*****************************************************************************/
/**
 * Clase que implementa la respuesta a una consulta.
 * 
 * @author pitidecaner
 * @author Salcedonia
 */
public class RespuestaPeticionConsulta implements Mensaje,Serializable{

    // ATRIBUTOS
    private Archivo[] _lista;

/*****************************************************************************/
    /**
     * Constructor de la clase QueryAnswer.
     * 
     * @param lista Lista de archivos asociada.
     */
    public RespuestaPeticionConsulta(Archivo[] lista) {
        
       _lista = lista;
    }

/*****************************************************************************/
    /**
     * Devuelve la lista de archivos asociada a la respuesta a la consulta.
     * 
     * @return La lista de archivos asociada a la respuesta a la consulta.
     */
    public Archivo[] getLista() {
        return _lista;
    }

/*****************************************************************************/
    /**
     * Establece _lista a valor lista.
     * 
     * @param lista Nuevo valor de la lista de archivos asociada.
     */
    public void setLista(Archivo[] lista) {
        _lista = lista;
    }

    public TipoMensaje getTipoMensaje() {
        return TipoMensaje.RespuestaPeticionConsulta;
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
