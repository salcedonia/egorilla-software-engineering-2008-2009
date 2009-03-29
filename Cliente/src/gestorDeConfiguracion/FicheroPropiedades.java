package gestorDeConfiguracion;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * Esta clase encapsula el manejo de un fichero de configuracion proporcionando
 * los métodos necesarios para su manipulación.
 * @author F. Javier Sánchez Pardo
 * 
 */
public class FicheroPropiedades {

    //Nombre del fichero que almacena las propiedades manejadas.
    private String sNombreFicheroProp;
    //Estructura en memoria que almacena las propiedades leidas del fichero.
    private Properties propiedades;

    /**
     * Se inicializa el objeto con el nombre del fichero de propiedades especificado. 
     * Además se intenta leer dicho fichero y cargar las propiedades que contiene dentro del objeto (en la variable propiedades)
     */
    public FicheroPropiedades(String sNomFicheroProps) throws ControlConfiguracionException {
        this.sNombreFicheroProp = sNomFicheroProps;
        this.propiedades = new Properties();
        cargarFicheroPropiedades();
    }

    /**
     * Carga en el objeto (en la variable properties asociada) todas las propiedades existentes en el fichero de properties.
     * Las propiedades que se leen del fichero SE AÑADEN Y SOBREESCRIBEN las que ya existían en el properties (caso de que hubiera
     * alguna antes)
     */
    protected void cargarFicheroPropiedades() throws ControlConfiguracionException {
        try {
            FileInputStream fichero = new FileInputStream(this.sNombreFicheroProp);
            this.propiedades.load(fichero);
            fichero.close();
        } catch (IOException ioe) {
            throw new ControlConfiguracionException();
        }
    }

    /**
     * Vuelca las propiedades en memoria (en el objeto properties asociado) al fichero asociado (en disco).
     */
    protected void grabarFicheroPropiedades() throws ControlConfiguracionException {
        try {
            FileOutputStream fichero = new FileOutputStream(this.sNombreFicheroProp);
            this.propiedades.store(fichero, "");
            fichero.close();
        } catch (IOException ioe) {
            throw new ControlConfiguracionException();
        }
    }

    /**
     * Busca el valor de una propiedad (en memoria, en el objeto properties asociado) y lo devuelve.
     */
    public String obtenerPropiedad(String sClave) {
        return this.propiedades.getProperty(sClave);
    }

    /**
     * Devuelve un nuevo objeto Properties con una copia de las propiedades actuales.
     */
    public Properties obtenerConjuntoPropiedades() {
        return new Properties (this.propiedades);
    }
    
    /**
     * Establece un nuevo valor para una propiedad, y lo actualiza en memoria (objeto properties asociado)
     * y en el disco (fichero asociado).
     * @param sClave
     * @param sValor
     * @throws gestorDeConfiguracion.ControlConfiguracionException
     */
    public void establecerPropiedad(String sClave, String sValor) throws ControlConfiguracionException {
        this.propiedades.setProperty(sClave, sValor);
        grabarFicheroPropiedades();
    }

    /**
     * Establece los valores para el conjunto de propiedades recibido como parámetro (no tienen porqué estar
     * todas), y lo actualiza en memoria (objeto properties asociado) y en el disco (fichero asociado).
     * @param propiedades: objeto Properties que contiene claves y sus valores.
     * @throws gestorDeConfiguracion.ControlConfiguracionException
     */
    public void establecerConjuntoPropiedades(Properties propiedades) throws ControlConfiguracionException {
        for (Enumeration e = propiedades.propertyNames(); e.hasMoreElements() ; ) {
            // Obtenemos el objeto
            Object obj = e.nextElement();
            this.propiedades.setProperty(obj.toString(), propiedades.getProperty(obj.toString()));
        }          
        grabarFicheroPropiedades();
    }

    /**
    * Devuelve una cadena con el contenido del objeto properties asociado.
    * Útil para depurar.
    */
    public String toString() {
        String sCadena = "";
        for (Enumeration e = this.propiedades.propertyNames(); e.hasMoreElements() ; ) {
            // Obtenemos el objeto
            Object obj = e.nextElement();
            sCadena += obj.toString() + ": " + this.propiedades.getProperty(obj.toString()) + "\n";
        }  
        return sCadena;
    }
}
