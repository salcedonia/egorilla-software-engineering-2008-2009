
package gestorDeConfiguracion;

/**
 * Interfaz general que define la interfaz de un Sujeto siguiendo el patrón
 * ObservadorGestorDeConfiguracion.
 * @author F. Javier Sanchez Pardo
 */
public interface Sujeto {
    public void anadirObservador (ObservadorGestorDeConfiguracion obs);
    public void quitarObservador (ObservadorGestorDeConfiguracion obs);
    public void notificarObservadores ();
}
