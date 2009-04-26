
package gestorDeConfiguracion;

import java.util.Properties;

/**
 * Interfaz que deben implementar los observadores sobre el objeto ControlConfiguracionCliente
 * @author F. Javier Sanchez Pardo
 */
public interface ObservadorControlConfiguracionCliente {
    /**
     * 
     * @param obj El objeto ControlConfiguracionCliente se pasa siempre por si 
     * es necesario acceder a él para tratar el cambio.
     * @param propiedades: propiedades que han cambiado (clave/valor nuevo).
     */    
    public void cambioEnPropiedades (ControlConfiguracionCliente obj, Properties propiedades);
}
