/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datos;

import java.io.Serializable;
import java.util.Vector;

import paquete.*;

/*****************************************************************************/
/**
 * Clase de Lista de archivos que proporciona todos los métodos necesarios para
 * el tratamiento de éste tipo de objetos.
 * La estructura empleada es una tabla hash ya que proporciona una eficiencia
 * mayor que otras estructuras para éste propósito.
 * 
 * @author pitidecaner
 * @author Salcedonia
 */
public class ListaArchivos extends Vector<Archivo> implements Serializable {


    /**
     * Constructor de la clase ListaArchivos.
     */
    public ListaArchivos() {
        super();
    }

}
