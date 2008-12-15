/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servidoregorilla.paquete;

import java.io.Serializable;

/*****************************************************************************/
/**
 * Clase que implementa la respuesta a una consulta.
 * 
 * @author pitidecaner
 * @author Salcedonia
 */
public class QueryAnswer implements Serializable{

    // ATRIBUTOS
    private Archivo[] _lista;

/*****************************************************************************/
    /**
     * Constructor de la clase QueryAnswer.
     * 
     * @param lista Lista de archivos asociada.
     */
    public QueryAnswer(Archivo[] lista) {
        
       _lista = lista;
    }

/*****************************************************************************/
    /**
     * Devuelve la lista de archivos asociada a la respuesta a la consulta.
     * 
     * @return La lista de archivos asociada a la respuesta a la consulta.
     */
    public Archivo[] getLista() {
        return _lista;
    }

/*****************************************************************************/
    /**
     * Establece _lista a valor lista.
     * 
     * @param lista Nuevo valor de la lista de archivos asociada.
     */
    public void setLista(Archivo[] lista) {
        _lista = lista;
    }    
}
