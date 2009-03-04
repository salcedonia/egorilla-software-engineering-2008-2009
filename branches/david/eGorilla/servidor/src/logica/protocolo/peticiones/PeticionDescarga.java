package logica.protocolo.peticiones;

import java.io.Serializable;

import logica.protocolo.Mensaje;
import logica.protocolo.ProtocoloServidor;


//*****************************************************************************//
/**
 * Clase que implementa los métodos necesarios para la gestión de las consultas
 * de descarga que los clientes realizan al servidor.
 * 
 * @author Pitidecaner
 * @author Salcedonia
 */
public class PeticionDescarga implements Mensaje, Serializable{
    
    // CONSTANTES
	private static final long serialVersionUID = 1L;
	
	// ATRIBUTOS
    private String _hash;

//  *****************************************************************************//
    /**
     * Constructor de la clase PeticionDescarga.
     * 
     * @param h Hash asociado.
     */
    public PeticionDescarga(String h){

    	_hash = h;
    }
    
//  *****************************************************************************//
    /**
     * Devuelve el protocolo utilizado en la consulta de descarga.
     * 
     * @return El protocolo que estamos utilizando
     */
    public ProtocoloServidor getProtocolo() {

    	return ProtocoloServidor.DESCARGA_ARCHIVOS;
    }

//  *****************************************************************************//
    /**
     * Devuelve el hash asociado al archivo de descarga asociado.
     * 
     * @return El hash asociado al archivo de descarga asociado.
     */
    public String getHash() {

    	return _hash;
    }

//  *****************************************************************************//
    /**
     * Establece el valor de hash a valor hash.
     * 
     * @param hash Nuevo valor a establecer.
     */
    public void setHash(String hash) {

    	_hash = hash;
    }
}
