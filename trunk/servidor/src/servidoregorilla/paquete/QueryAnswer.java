/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servidoregorilla.paquete;

import java.io.Serializable;

/**
 *
 * @author pitidecaner
 */
public class QueryAnswer implements Serializable{

    public Archivo[] lista;

    public QueryAnswer(Archivo[] l) {
       lista = l;
    }
    
}
