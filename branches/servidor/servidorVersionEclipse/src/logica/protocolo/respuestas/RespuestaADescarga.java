/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logica.protocolo.respuestas;

import java.io.Serializable;

import logica.datos.DatosCliente;

//*****************************************************************************//
/**
 * Clase que implementa los métodos necesarios para las respuestas a consultas
 * de descarga de archivos.
 * 
 * @author pitidecaner
 * @author Salcedonia
 */
public class RespuestaADescarga implements Serializable{
    
    // CONSTANTES
	private static final long serialVersionUID = 1L;
	
	// ATRIBUTOS
    private DatosCliente[] _listaDatosCliente;

//  *****************************************************************************//
    /**
     * Constructor de la clase RespuestaADescarga.
     * 
     * @param lista Lista de clientes asociados.
     */
    public RespuestaADescarga(DatosCliente[] lista) {
       
    	setLista(lista);
    }

//  *****************************************************************************//
    /**
     * Devuelve la lista de clientes asociados a la consulta de descarga de un
     * archivo.
     * 
     * @return La lista de clientes asociados a la consulta de descarga de un
     * archivo.
     */
    public DatosCliente[] getLista() {
        
    	return _listaDatosCliente;
    }

//  *****************************************************************************//
    /**
     * Establece un nuevo valor para la lista de clientes asociados a la consulta.
     * 
     * @param lista Nuevo valor de la lista de archivos asociada a establecer.
     */
    public void setLista(DatosCliente[] lista) {
        
    	_listaDatosCliente = lista;
    }    
}
