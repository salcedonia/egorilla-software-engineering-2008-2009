package gestorDeConfiguracion;

import java.util.Properties;

/**
 * Clase que controla toda la configuracion de la aplicacion Cliente eGorilla mediante 
 * archivos de properties.
 * Implementación mediante un patrón Singleton.
 * @author Javier Sánchez Pardo
 */
public class ControlConfiguracion {

    private static ControlConfiguracion _instancia = null;
    //Variable que contiene el nombre del fichero de propiedades.
    FicheroPropiedades oFicheroPropsPpal;
    FicheroPropiedades oFicheroPropsxDefecto;

    protected ControlConfiguracion(String sNomFicheroPropsPpal, String sNomFicheroPropsxDefecto) throws ControlConfiguracionException {
        super();
        oFicheroPropsPpal = new FicheroPropiedades(sNomFicheroPropsPpal);
        oFicheroPropsxDefecto = new FicheroPropiedades(sNomFicheroPropsxDefecto);
    }
    ;

    public static ControlConfiguracion obtenerInstancia(String sNomFicheroPropsPpal, String sNomFicheroPropsxDefecto) throws ControlConfiguracionException {
        if (_instancia == null) {
            _instancia = new ControlConfiguracion(sNomFicheroPropsPpal, sNomFicheroPropsxDefecto);
        }
        return _instancia;
    }

    /**
     * Busca el valor de una propiedad en el archivo de propiedades actual y lo devuelve.
     * @param sClave: clave a buscar
     */
    public String obtenerPropiedad(String sClave) {
        return this.oFicheroPropsPpal.obtenerPropiedad(sClave);
    }

    /**
     * Devuelve el objeto Properties que almacena todas las propiedades actuales.
     */
    public Properties obtenerConfiguracion() {
        return this.oFicheroPropsPpal.obtenerConjuntoPropiedades();
    }    
    
    /**
     * Busca el valor de una propiedad en el archivo de propiedades POR DEFECTO y lo devuelve.
     * @param sClave: clave a buscar
     */
    public String obtenerPropiedadxDefecto(String sClave) {
        return this.oFicheroPropsxDefecto.obtenerPropiedad(sClave);
    }

    /**
     * Devuelve el objeto Properties que almacena los valores por defecto de todas las propiedades.
     */
    public Properties obtenerConfiguracionxDefecto() {
        return this.oFicheroPropsxDefecto.obtenerConjuntoPropiedades();
    }    
    
    /**
     * Establece un nuevo valor para una propiedad, actualizando el fichero de propiedades
     * asociado.
     * @param sClave
     * @param sValor
     * @throws gestorDeConfiguracion.ControlConfiguracionException
     */
    public void establecerPropiedad(String sClave, String sValor) throws ControlConfiguracionException {
        this.oFicheroPropsPpal.establecerPropiedad(sClave, sValor);
    }

    /**
     * Establece los valores para el conjunto de propiedades recibido como parámetro (no tienen porqué estar
     * todas), y lo actualiza en memoria y en el disco (fichero asociado).
     * @param propiedades: objeto Properties que contiene determinadas claves y sus valores.
     * @throws gestorDeConfiguracion.ControlConfiguracionException
     */
    public void establecerConfiguracion(Properties propiedades) throws ControlConfiguracionException {
        oFicheroPropsPpal.establecerConjuntoPropiedades(propiedades);
    }
 
}
