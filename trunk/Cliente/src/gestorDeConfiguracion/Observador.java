/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestorDeConfiguracion;

/**
 * Interfaz general de un observador siguiendo el patrón Observador
 * (el objeto observado lo denomino "el Sujeto").
 * @author F. Javier Sánchez Pardo
 */
public interface Observador {
    /**
     * 
     * @param suj : objeto que ha sufrido un cambio de estado del que depende el 
     * observador. Pasando como parámetro al sujeto se permite que el mismo objeto observador 
     * observe a varios sujetos.
     */
    public void actualizar (Sujeto suj);
}
