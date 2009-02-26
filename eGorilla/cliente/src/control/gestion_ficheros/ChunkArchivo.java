package control.gestion_ficheros;

import java.io.Serializable;

//************************************************************************************//
/**
 * Un trozo de un fichero, para ser enviado por la red.
 *
 * @author Pitidecaner
 * @author S@L-c
 */
public class ChunkArchivo implements Serializable{
   
	// CONSTANTES
	private static final long serialVersionUID = 1L;
	
	// ATRIBUTOS
	public boolean _valido;
    public Byte[] _buffer;
}
