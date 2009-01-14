package logica.datos;

import java.io.Serializable;
import java.util.Vector;

import logica.paquete.Archivo;
import logica.paquete.TipoArchivo;
import logica.red.ConexionCliente;

//*****************************************************************************//
/**
 * Clase de Lista de archivos que proporciona todos los métodos necesarios para
 * el tratamiento de éste tipo de objetos. La estructura empleada es una tabla
 * hash ya que proporciona una eficiencia mayor que otras estructuras para éste
 * propósito.
 * 
 * @author pitidecaner
 * @author Salcedonia
 */
public class ListaArchivos extends Vector<Archivo> implements Serializable {

	// CONSTANTES
	private static final long serialVersionUID = 1L;

	// ATRIBUTOS
	public Vector<ClientesDeArchivo> _relacion;

//	*****************************************************************************//
	/**
	 * Constructor de la clase ListaArchivos.
	 */
	public ListaArchivos() {

		super();
		_relacion = new Vector<ClientesDeArchivo>();
	}

//	*****************************************************************************//
	/**
	 * A�ade un archivo a la lista de archivos.
	 * 
	 * @param a
	 *            Archivo a a�adir a la lista de archivos.
	 */
	public synchronized void anadirArchivo(Archivo a) {

		add(a);
	}

	/** ************************************************************************** */
	/**
	 * Elimina un archivo de la lista de archivos.
	 * 
	 * @param a
	 *            Archivo a eliminar de la lista de archivos.
	 */
	public synchronized void eliminarArchivo(Archivo a) {

		// TODO: esto no puede funcionar asi
		remove(a);
	}

	/** ************************************************************************** */
	/**
	 * Actualiza la BBDD del servidor con los ficheros que tiene un cliente
	 * recién conectado, se comprueba si cada uno de los archivos pertenecían
	 * antes al sistema. en tal caso sólo se añade la referencia a este
	 * cliente y el posible nombre distinto del archivo.
	 * 
	 * @param conexion
	 *            la conexión con el cliente.
	 * @param listaArchivos
	 *            la lista de archivos de este cliente.
	 */
	public void actualizarDesdeListaCliente(ConexionCliente conexion,
			ListaArchivos listaArchivos) {

		// No me gusta esto aqui, pero no se ha tenido en cuenta el caso

		// Si es el primer cliente, es decir, servidor sin ficheros...
		if (getNumeroArchivos() == 0) {
			for (int i = 0; i < listaArchivos.getNumeroArchivos(); i++) {
				// Meto todos los ficheros compartidos del cliente
				add(listaArchivos.get(i));
			}
		} 
		else { // Sino...
		
			boolean encontrado = false;

			for (int i = 0; i < listaArchivos.getNumeroArchivos(); i++) {
				// flag encontrado ya que si le encuentro el primero en la lista
				// no pierdo tiempo buscando en el resto xq no va a estar repe
				for (int j = 0; j < getNumeroArchivos() && encontrado == false; j++) {
					encontrado = listaArchivos.get(i).comparaArchivo(get(j));
				}
				if (encontrado == false) {
				
					System.out.println("No encontrado en el servidor");
					add(listaArchivos.get(i));
					_relacion.add(new ClientesDeArchivo(listaArchivos.get(i)
							.getHash(), listaArchivos.get(i).getNombre(),
							conexion));
				} 
				else {
					System.out.println("Encontrado en el servidor");
					// Hago las relaciones para el encontrado
					// Lo pongo a false para seguir buscando
					encontrado = false;
				}
			}
		}
	}

//	*****************************************************************************//
	/**
	 * Devuelve el número de archivos que tiene la lista.
	 * 
	 * @return El número de archivos que tiene la lista.
	 */
	public int getNumeroArchivos() {
	
		return size();
	}

//	*****************************************************************************//
	/**
	 * Devuelve el número de clientes que tienen ese archivo.
	 * 
	 * @return 1 De momento.
	 */
	public int getNumeroClientes() {
		
		// TODO: En fin...
		return 1;
	}

//	*****************************************************************************//
	/**
	 * Buscar por nombre, la versión actual busca aquellos archivos que se
	 * llaman como el nombre indicado. <b>No distingue entre mayúsculas y minúsculas.<b>
	 * 
	 * @param nombre
	 *            Nombre del archivo buscado.
	 * 
	 * @return Un array con los datos de los clientes que tienen ese archivo.
	 */
	public Archivo[] buscarArchivoPorNombre(String nombre) {

		Vector<Archivo> lista = new Vector<Archivo>();

		for (Archivo a : this) {
			if (a._nombre.toLowerCase().contains(nombre.toLowerCase()))
				lista.add(a);
		}
		
		System.out.println("Archivos encontrados: <" + lista.size() + ">");
		Archivo[] ars = new Archivo[lista.size()];
		return lista.toArray(ars);
	}

//	*****************************************************************************//
	/**
	 * Buscar por tipo de archivo.
	 * 
	 * @param tipo
	 *            Tipo del archivo buscado.
	 * 
	 * @return Un array con los datos de los clientes que tienen ese archivo.
	 */
	public Archivo[] buscarArchivoPorTipo(TipoArchivo tipo) {
		
		Vector<Archivo> lista = new Vector<Archivo>();

		for (Archivo a : this) {
			if (a._tipo == tipo)
				lista.add(a);
		}

		Archivo[] ars = new Archivo[lista.size()];
		return lista.toArray(ars);
	}

//	*****************************************************************************//
	/**
	 * Búsqueda por hash.
	 * 
	 * @param hash
	 *            Hash del archivo a buscar.
	 * 
	 * @return Un array con los datos de los clientes que tienen ese archivo.
	 */
	public DatosCliente[] getPropietarios(String hash) {
		
		Vector<DatosCliente> lista = new Vector<DatosCliente>();

		for (ClientesDeArchivo r : _relacion) {
			if (r._hash.contentEquals(hash)) {
				for (ConexionCliente cliente : r._propietarios) {
					lista.add(cliente.getDatosCliente());
				}
				break;
			}
		}

		DatosCliente[] clientes = new DatosCliente[lista.size()];
		return lista.toArray(clientes);
	}
}
