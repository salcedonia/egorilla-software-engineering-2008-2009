
package gestorDeConfiguracion;

/**
 * Interfaz de los observadores sobre el GestorDeConfiguracion
 * @author F. Javier Sanchez Pardo
 */
public interface ObservadorGestorDeConfiguracion {
    /**
     * 
     * @param suj : objeto que ha sufrido un cambio de estado del que depende el 
     * observador. Pasando como parámetro al sujeto se permite que el mismo objeto observador 
     * observe a varios sujetos.
     */
    public void actualizar (Sujeto suj);
}
