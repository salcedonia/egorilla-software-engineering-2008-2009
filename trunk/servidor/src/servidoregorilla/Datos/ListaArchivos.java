/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servidoregorilla.datos;

import servidoregorilla.paquete.Archivo;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Vector;
import servidoregorilla.protocolo.ConexionCliente;

/**
 * Clase de Lista de archivos que proporciona todos los métodos necesarios para
 * el tratamiento de éste tipo de objetos.
 * La estructura empleada es una tabla hash ya que proporciona una eficiencia
 * mayor que otras estructuras para éste propósito.
 * 
 * @author pitidecaner
 * @author Salcedonia
 */
public class ListaArchivos extends HashSet<Archivo> implements Serializable {

    Vector<Cliente_Archivo> _relaccion;

    public ListaArchivos() {
        super();
        _relaccion = new Vector<Cliente_Archivo>();
    }

    /**
     * Añade un archivo a la lista de archivos.
     * 
     * @param a Archivo a añadir a la lista de archivos.
     */
    public synchronized void addArchivo(Archivo a) {
    }

    /**
     * Elimina un archivo de la lista de archivos.
     * 
     * @param a Archivo a eliminar de la lista de archivos.
     */
    public synchronized void removeArchivo(Archivo a) {

        this.remove(a._hash);
    }

    /**
     * Actualiza la BBDD del servidor con los ficheros que tiene un cliente recien
     * conectado, se comprueba si cada uno de los archivos pertenecian antes al sistema.
     * en tal caso solo se añade la referencia a este cliente y el posible nombre
     * distinto del archivo.
     *
     * @param c la conexion con el cliente.
     * @param listarchvos la lista de archivos de este cliente.
     */
    public void actualizarDesdeListaCliente(ConexionCliente c, ListaArchivos listarchvos) {

        boolean encontrado = false;
        for (Archivo ar : listarchvos) {

            // buscalo en la BBDD
            for (Archivo arch : this) {
                encontrado = arch.comparaArchivo(ar);
            }

            // si no existe darlo de alta
            if (!encontrado) {
                this.add(ar);
                this._relaccion.add(new Cliente_Archivo(ar.getHash(), ar.getNombre(), c));
            } else {
                // tomar nota de la relacción, de existir.
                for (Cliente_Archivo rel : _relaccion) {
                    if (rel._hash.contentEquals(ar.getHash())) {
                        rel._propietarios.add(c);
                        rel._nombresArchivo.add(ar.getNombre());
                    }
                }
            }
        }
    }
}
