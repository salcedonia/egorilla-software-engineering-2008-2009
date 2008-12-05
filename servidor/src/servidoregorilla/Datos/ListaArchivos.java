/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servidoregorilla.Datos;

import servidoregorilla.paquete.Archivo;
import java.io.Serializable;
import java.util.Hashtable;

/**
 *
 * @author pitidecaner
 */
public class ListaArchivos extends Hashtable<String,Archivo> implements Serializable{
    public synchronized void addArchivo(Archivo a){
        this.put(a.hash, a);
    }
}
