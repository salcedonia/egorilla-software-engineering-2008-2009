package logica.datos;

import java.util.HashSet;

import logica.red.ConexionCliente;
import logica.red.ConexionPeer;

//*****************************************************************************//
/**
 * Clase que proporciona los métodos y atributos necesarios para el manejo de
 * tablas que contienen la información relativa a los clientes.
 * 
 * @author pitidecaner
 * @author Salcedonia
 */
public class TablaClientes extends HashSet<ConexionCliente>{
    
    // TODO:

	// CONSTANTES
	private static final long serialVersionUID = 1L;

//	*****************************************************************************//
	/**
	 * Añade un cliente a la lista de conexiones de clientes.
	 * 
	 * @param c Cliente nuevo a añadir.
	 */
	public void anadirCliente (ConexionCliente c){
        
		add(c);
    }

//	*****************************************************************************//
	/**
	 * Elimina un cliente de la lista de conexiones de clientes.
	 * 
	 * @param c Cliente a eliminar.
	 */
    public void eliminarCliente (ConexionCliente c){
        
    	remove(c);
    }

//  *****************************************************************************//
    /**
     * Elimina un cliente de la lista de clientes a través de su conexión. 
     * 
     * @param c Conexion con el cliente de donde se obtiene la información 
     * para eliminar el cliente.
     */
    public void eliminarCliente (ConexionPeer c){
        
    	for (ConexionCliente cliente : this) {
            if (cliente.getConexion() == c){
                remove(cliente);
                return;
            }
        }
    }
}


