/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servidoregorilla.datos;

import servidoregorilla.paquete.Archivo;
import java.io.Serializable;
import java.util.Hashtable;

/**
 * Clase de Lista de archivos que proporciona todos los métodos necesarios para
 * el tratamiento de éste tipo de objetos.
 * La estructura empleada es una tabla hash ya que proporciona una eficiencia
 * mayor que otras estructuras para éste propósito.
 * 
 * @author pitidecaner
 * @author Salcedonia
 */
public class ListaArchivos extends Hashtable<String,Archivo> implements Serializable{
    
    /**
     * Añade un archivo a la lista de archivos.
     * 
     * @param a Archivo a añadir a la lista de archivos.
     */
    public synchronized void addArchivo(Archivo a){
        
        put(a._hash, a);
    }
    
    /**
     * Elimina un archivo de la lista de archivos.
     * 
     * @param a Archivo a eliminar de la lista de archivos.
     */
    public synchronized void removeArchivo(Archivo a){
        
        this.remove(a._hash);
    }
}
