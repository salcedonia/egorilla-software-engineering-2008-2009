/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logica.protocolo.respuestas;

import java.io.Serializable;

import logica.paquete.Archivo;

//*****************************************************************************//
/**
 * Clase que implementa la respuesta a una petición de búsqueda de un archivo
 * en el servidor.
 * 
 * @author pitidecaner
 * @author Salcedonia
 */
public class RespuestaABusqueda implements Serializable{

    // CONSTANTES
	private static final long serialVersionUID = 1L;
	
	// ATRIBUTOS
    private Archivo[] _listaArchivos;

//  *****************************************************************************//
    /**
     * Constructor de la clase RespuestaABusqueda.
     * 
     * @param listaArchivos Lista de archivos asociada.
     */
    public RespuestaABusqueda(Archivo[] listaArchivos) {
        
       setLista(listaArchivos);
    }

/*****************************************************************************/
    /**
     * Devuelve la lista de archivos asociada a la respuesta a la consulta.
     * 
     * @return La lista de archivos asociada a la respuesta a la consulta.
     */
    public Archivo[] getLista() {
    	
        return _listaArchivos;
    }

/*****************************************************************************/
    /**
     * Establece _listaArchivos a valor lista.
     * 
     * @param lista Nuevo valor de la lista de archivos asociada.
     */
    public void setLista(Archivo[] lista) {
        
    	_listaArchivos = lista;
    }    
}
