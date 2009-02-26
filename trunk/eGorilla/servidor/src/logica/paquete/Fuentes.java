package logica.paquete;

//*****************************************************************************//
/**
 * 
 * 
 * @author S@L-c
 */
public class Fuentes {

	// ATRIBUTOS
	public int _total;
	public int _conectadosActualmente;
	
//  *****************************************************************************//
	/**
	 * Constructor de la clase Fuentes.
	 * 
	 * @param total Total de fuentes que tienen el archivo.
	 * @param conectadosActualmente Fuentes que tienen el archivo y que están conectadas
	 * al servidor actualmente.
	 */
	public Fuentes(int total, int conectadosActualmente){
		
		setTotal(total);
		setConectadosActualmente(conectadosActualmente);
	}

//  *****************************************************************************//
	/**
	 * Devuelve el número de fuentes conectadas actualmente.
	 * 
	 * @return El número de fuentes conectadas actualmente.
	 */
	public int getConectadosActualmente() {
		
		return _conectadosActualmente;
	}

//  *****************************************************************************//
	/**
	 * Establece el valor de _conectadosActualmente a valor actualmente.
	 * 
	 * @param actualmente Nuevo valor a establecer.
	 */
	public void setConectadosActualmente(int actualmente) {
		
		_conectadosActualmente = actualmente;
	}

//  *****************************************************************************//
	/**
	 * Devuelve el número total de fuentes que tienen un archivo. 
	 * 
	 * @return El número total de fuentes que tienen un archivo.
	 */
	public int getTotal() {
		
		return _total;
	}

//  *****************************************************************************//
	/**
	 * Establece el valor _total a valor total.
	 * 
	 * @param total Nuevo valor a establecer.
	 */
	public void setTotal(int total) {
		
		_total = total;
	}
	
//  *****************************************************************************//
	/**
	 * Calcula la disponibilidad del archivo.
	 * 
	 * @return La disponibilidad del archivo.
	 */
	public double calcularDisponibilidad(){
		
		return _conectadosActualmente / _total;
	}
}
