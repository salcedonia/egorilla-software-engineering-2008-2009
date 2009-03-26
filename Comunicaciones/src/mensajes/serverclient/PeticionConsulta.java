/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mensajes.serverclient;

import java.io.Serializable;
import mensajes.Mensaje;
import mensajes.TipoMensaje;
import datos.TipoArchivo;

/*****************************************************************************/
/**
 * Clase que implementa consultas.
 * 
 * @author Pitidecaner
 */
public class PeticionConsulta implements Serializable, Mensaje{
    
    // ATRIBUTOS
    private String _cadenaBusqueda;
    private TipoArchivo _tipo;

     /**
     * Establece la cadena de busqueda a valor cadenaBusqueda.
     * 
     * @param cadenaBusqueda Nuevo valor a establecer para la cadena de b�squeda.
     */
    public  PeticionConsulta(String cadenaBusqueda) {
        _cadenaBusqueda = cadenaBusqueda;
    }   
    
/*****************************************************************************/
    /**
     * Obtiene la cadena de b�squeda de la consulta.
     * 
     * @return La cadena de b�squeda asociada a la consulta.
     */
    public String getCadenaBusqueda() {
        return _cadenaBusqueda;
    }

/*****************************************************************************/
    /**
     * Establece la cadena de busqueda a valor cadenaBusqueda.
     * 
     * @param cadenaBusqueda Nuevo valor a establecer para la cadena de b�squeda.
     */
    public void setCadenaBusqueda(String cadenaBusqueda) {
        _cadenaBusqueda = cadenaBusqueda;
    }

/*****************************************************************************/
    /**
     * Devuelve el tipo de archivo asociado.
     * 
     * @return El tipo de archivo asociado.
     */
    public TipoArchivo getTipo() {
        return _tipo;
    }

/*****************************************************************************/
    /**
     * Establece el tipo de archivo a valor tipo.
     * 
     * @param tipo Nuevo valor del tipo de archivo a establecer.
     */
    public void setTipo(TipoArchivo tipo) {
        _tipo = tipo;
    }

    public TipoMensaje getTipoMensaje() {
       return TipoMensaje.PeticionConsulta;
    }
    
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
