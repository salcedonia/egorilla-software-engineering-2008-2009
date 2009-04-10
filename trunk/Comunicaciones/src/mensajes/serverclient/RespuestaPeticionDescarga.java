/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

package mensajes.serverclient;


import java.io.Serializable;
import mensajes.Mensaje;
import mensajes.TipoMensaje;

/*****************************************************************************/
/**
 * Clase que implementa los m�todos necesarios para las respuestas a consultas
 * de descarga de archivos.
 * 
 * @author pitidecaner
 * @author Salcedonia
 */
public class RespuestaPeticionDescarga implements Mensaje, Serializable{
    
    public String hash;
    public String nombre;
    
    // ATRIBUTOS
    private DatosCliente[] _lista;

/*****************************************************************************/
    /**
     * Constructor de la clase DownloadOrderAnswer.
     * 
     * @param lista Lista de clientes asociados.
     */
    public RespuestaPeticionDescarga(String nmb, String h, DatosCliente[] lista) {
        nombre = nmb;
        hash = h;
       _lista = lista;
    }

/*****************************************************************************/
    /**
     * Devuelve la lista de clientes asociados a la consulta de descarga de un
     * archivo.
     * 
     * @return La lista de clientes asociados a la consulta de descarga de un
     * archivo.
     */
    public DatosCliente[] getLista() {
        return _lista;
    }

/*****************************************************************************/
    /**
     * Establece un nuevo valor para la lista de clientes asociados a la consulta.
     * 
     * @param lista Nuevo valor de la lista de archivos asociada a establecer.
     */
    public void setLista(DatosCliente[] lista) {
        _lista = lista;
    }

    public TipoMensaje getTipoMensaje() {
        return TipoMensaje.RespuestaPeticionDescarga;
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
