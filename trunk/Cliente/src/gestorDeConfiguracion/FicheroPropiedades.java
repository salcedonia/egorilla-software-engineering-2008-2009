package gestorDeConfiguracion;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Esta clase encapsula el manejo de un fichero de configuracion proporcionando
 * los métodos necesarios para su manipulación.
 * @author F. Javier Sanchez Pardo
 * 
 */
public class FicheroPropiedades {

    //Nombre del fichero que almacena las _propiedades manejadas.
    private String _sNombreFicheroProp;
    //Estructura en memoria que almacena las _propiedades leidas del fichero.
    private Properties _propiedades;

    /**
     * Se inicializa el objeto con el nombre del fichero de _propiedades especificado. 
     * Además se intenta leer dicho fichero y cargar las _propiedades que contiene dentro del objeto (en la variable _propiedades)
     */
    public FicheroPropiedades(String sNomFicheroProps) throws ControlConfiguracionClienteException {
        this._sNombreFicheroProp = sNomFicheroProps;
        this._propiedades = new Properties();
        cargarFicheroPropiedades();
    }

    /**
     * Carga en el objeto (en la variable properties asociada) todas las _propiedades existentes en el fichero de properties.
     * Las _propiedades que se leen del fichero SE AÑADEN Y SOBREESCRIBEN las que ya existían en el properties (caso de que hubiera
     * alguna antes)
     */
    protected void cargarFicheroPropiedades() throws ControlConfiguracionClienteException {
        try {
            FileInputStream fichero = new FileInputStream(this._sNombreFicheroProp);
            this._propiedades.load(fichero);
            fichero.close();
        } catch (IOException ioe) {
            throw new ControlConfiguracionClienteException("No se ha podido cargar el fichero" + this._sNombreFicheroProp, ioe);
        }
    }

    /**
     * Vuelca las _propiedades en memoria (en el objeto properties asociado) al fichero asociado (en disco).
     */
    protected void grabarFicheroPropiedades() throws ControlConfiguracionClienteException {
        try {
            FileOutputStream fichero = new FileOutputStream(this._sNombreFicheroProp);
            this._propiedades.store(fichero, "");
            fichero.close();
        } catch (IOException ioe) {
            throw new ControlConfiguracionClienteException("No se ha podido grabar en el fichero" + this._sNombreFicheroProp, ioe);
        }
    }

    /**
     * Busca el valor de una propiedad (en memoria, en el objeto properties asociado) y lo devuelve.
     */
    public String obtenerPropiedad(String sClave) {
        return this._propiedades.getProperty(sClave);
    }

    /**
     * Devuelve un nuevo objeto Properties con una copia de las _propiedades actuales.
     */
    public Properties obtenerConjuntoPropiedades() {
        return new Properties (this._propiedades);
    }
    
    /**
     * Establece un nuevo valor para una propiedad, y lo actualiza en memoria (objeto properties asociado)
     * y en el disco (fichero asociado).
     * @param sClave
     * @param sValor
     * @throws gestorDeConfiguracion.ControlConfiguracionClienteException
     */
    public void establecerPropiedad(String sClave, String sValor) throws ControlConfiguracionClienteException {
        this._propiedades.setProperty(sClave, sValor);
        grabarFicheroPropiedades();
    }

    /**
     * Establece los valores para el conjunto de _propiedades recibido como parámetro (no tienen porqué estar
     * todas), y lo actualiza en memoria (objeto properties asociado) y en el disco (fichero asociado).
     * @param _propiedades: objeto Properties que contiene claves y sus valores.
     * @throws gestorDeConfiguracion.ControlConfiguracionClienteException
     */
    public void establecerConjuntoPropiedades(Properties propiedades) throws ControlConfiguracionClienteException {
        for (Enumeration e = propiedades.propertyNames(); e.hasMoreElements() ; ) {
            // Obtenemos el objeto
            Object obj = e.nextElement();
            this._propiedades.setProperty(obj.toString(), propiedades.getProperty(obj.toString()));
        }          
        grabarFicheroPropiedades();
    }

    /**
    * Devuelve una cadena con el contenido del objeto properties asociado.
    * Util para depurar.
    */
    public String toString() {
        String sCadena = "";
        for (Enumeration e = this._propiedades.propertyNames(); e.hasMoreElements() ; ) {
            // Obtenemos el objeto
            Object obj = e.nextElement();
            sCadena += obj.toString() + ": " + this._propiedades.getProperty(obj.toString()) + "\n";
        }  
        return sCadena;
    }
}
