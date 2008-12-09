/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servidoregorilla.protocolo;

/**
 * Interfaz que proporciona los métodos necesarios que implementan todos
 * aquellos objetos que siguen un tipo de protocolo en la aplicación relativa
 * a la parte del servidor.
 * 
 * @author pitidecaner
 * @author Salcedonia
 */
public interface Protocolo {

    /**
     * Comienza el hilo de ejecución del cliente que se conecta al servidor.
     */
    public void start();
}
