package logica.protocolo.peticiones;

import java.io.Serializable;

import logica.paquete.TipoArchivo;
import logica.protocolo.Mensaje;
import logica.protocolo.ProtocoloServidor;

//*****************************************************************************//
/**
 * Clase que implementa los métodos necesarios para la gestión de las consultas
 * de búsqueda que los clientes realizan al servidor.
 * 
 * @author Pitidecaner
 */
public class PeticionBusqueda implements Serializable, Mensaje{
    
    // CONSTANTES
	private static final long serialVersionUID = 1L;
	
	// ATRIBUTOS
    private String _cadenaBusqueda;
    private TipoArchivo _tipoArchivo;
    
//  *****************************************************************************//
    /**
     * Devuelve el protocolo de búsqueda de archivos.
     * 
     * @return El protocolo de búsqueda de archivos.
     */
    public ProtocoloServidor getProtocolo() {
   
        return ProtocoloServidor.BUSQUEDA_ARCHIVOS;
    }

//  *****************************************************************************//
    /**
     * Obtiene la cadena de búsqueda de la consulta.
     * 
     * @return La cadena de búsqueda asociada a la consulta.
     */
    public String getCadenaBusqueda() {

    	return _cadenaBusqueda;
    }

//  *****************************************************************************//
    /**
     * Establece la cadena de busqueda a valor cadenaBusqueda.
     * 
     * @param cadenaBusqueda Nuevo valor a establecer para la cadena de búsqueda.
     */
    public void setCadenaBusqueda(String cadenaBusqueda) {

    	_cadenaBusqueda = cadenaBusqueda;
    }

//  *****************************************************************************//
    /**
     * Devuelve el tipo de archivo asociado.
     * 
     * @return El tipo de archivo asociado.
     */
    public TipoArchivo getTipo() {

    	return _tipoArchivo;
    }

//  *****************************************************************************//
    /**
     * Establece el tipo de archivo a valor tipo.
     * 
     * @param tipo Nuevo valor del tipo de archivo a establecer.
     */
    public void setTipo(TipoArchivo tipo) {

    	_tipoArchivo = tipo;
    }    
}
