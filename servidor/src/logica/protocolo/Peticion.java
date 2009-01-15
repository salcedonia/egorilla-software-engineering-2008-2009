/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package logica.protocolo;

//*****************************************************************************//
/**
 * Interfaz que proporciona los métodos necesarios que implementan todos
 * aquellos objetos que siguen un tipo de petición en la aplicación relativa
 * a la parte del servidor.
 * 
 * @author pitidecaner
 * @author Salcedonia
 */
public interface Peticion{

//	*****************************************************************************//
    /**
     * Devuelve la versión del protocolo correspondiente.
     * 
     * @return La versión del protocolo correspondiente.
     */
	public Protocolo getProtocolo();
}
