/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestorDeConfiguracion;

/**
 * Interfaz general que define la interfaz de un Sujeto siguiendo el patrón
 * Observador.
 * @author F. Javier Sanchez Pardo
 */
public interface Sujeto {
    public void anadirObservador (Observador obs);
    public void quitarObservador (Observador obs);
    public void notificarObservadores ();
}
