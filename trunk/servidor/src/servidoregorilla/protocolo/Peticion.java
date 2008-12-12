/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servidoregorilla.protocolo;

import servidoregorilla.datos.*;

/**
 * Interfaz que proporciona los métodos necesarios que implementan todos
 * aquellos objetos que siguen un tipo de peticion en la aplicación relativa
 * a la parte del servidor.
 * 
 * @author pitidecaner
 * @author Salcedonia
 */
public interface Peticion {

    public int getVersion();
    
    /**
     * Comienza el hilo de ejecución del cliente que se conecta al servidor.
     */
    public void start();
    
    public void addTablaClientes(TablaClientes t);
    public void addListaArchivos(ListaArchivos l);
}
