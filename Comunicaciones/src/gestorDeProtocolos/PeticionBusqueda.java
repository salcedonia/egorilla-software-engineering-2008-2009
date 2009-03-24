package gestorDeProtocolos;

import datos.TipoArchivo;
import java.io.Serializable;

/**
 * Clase que implementa peticiones de busquedas de archivos en 
 * el Servidor eGorilla.
 * 
 * @author Luis Ayuso, Ivan Munsuri, Javier Salcedo
 */
public class PeticionBusqueda implements Serializable, Peticion{
    
    // ATRIBUTOS
    private String _cadenaBusqueda;
    private TipoArchivo _tipo;
    
    /**
     * Devuelve la versión de las consultas.
     * 
     * @return 2
     */
    public int getVersion() {
   
        return 2;
    }

    /**
     * Obtiene la cadena de búsqueda de la consulta.
     * 
     * @return La cadena de búsqueda asociada a la consulta.
     */
    public String getCadenaBusqueda() {
        return _cadenaBusqueda;
    }

    /**
     * Establece la cadena de busqueda a valor cadenaBusqueda.
     * 
     * @param cadenaBusqueda Nuevo valor a establecer para la cadena de búsqueda.
     */
    public void setCadenaBusqueda(String cadenaBusqueda) {
        _cadenaBusqueda = cadenaBusqueda;
    }

    /**
     * Devuelve el tipo de archivo asociado.
     * 
     * @return El tipo de archivo asociado.
     */
    public TipoArchivo getTipo() {
        return _tipo;
    }

    /**
     * Establece el tipo de archivo a valor tipo.
     * 
     * @param tipo Nuevo valor del tipo de archivo a establecer.
     */
    public void setTipo(TipoArchivo tipo) {
        _tipo = tipo;
    }
}
