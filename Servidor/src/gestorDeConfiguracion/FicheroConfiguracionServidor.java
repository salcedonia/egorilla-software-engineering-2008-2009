/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestorDeConfiguracion;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *Clase que para la manipulacion de un fichero de propiedades
 * @author David
 */
public class FicheroConfiguracionServidor {
    
    //Atributo con la direccion del fichero de propiedades.
    private String nombreFicheroProperties;
    //Atributo con las propiedades leidas del fichero.
    private Properties properties;
    
    
    /**
     * Constructora que recibe como parametro la cadena que contiene la direccion del
     * fichero de propiedades, el cual se carga en el atributo properties y se graban en él 
     * todas las propiedades que tenga el fichero.
     * 
     */
    public FicheroConfiguracionServidor(String nomFichero) throws ControlConfiguracionServidorException {
        this.nombreFicheroProperties = nomFichero;
        this.properties = new Properties();
        try {
            FileInputStream fichero = new FileInputStream(this.nombreFicheroProperties);
            this.properties.load(fichero);
            fichero.close();
        } catch (IOException ioe) {
            throw new ControlConfiguracionServidorException("No se ha podido cargar el fichero: " + this.nombreFicheroProperties, ioe);
        }
    }
    
    /**
    * Método accesor que devuelve el valor de una propiedad existente en el objeto
     * properties y que atiende al valor de la clave
    */
    
    public String getProperty(String clave)
    {
        return this.properties.getProperty(clave);
    }
    /**
    * Metodo mutador que inserta en el objeto properties pares clave-valor y después se almacenan
    * en un fichero
    */
    public void setProperty(String clave,String valor) throws ControlConfiguracionServidorException
    {
        this.properties.setProperty(clave, valor);
        try {
            FileOutputStream fichero = new FileOutputStream(this.nombreFicheroProperties);
            this.properties.store(fichero, "");
            fichero.close();
        } catch (IOException ioe) {
            throw new ControlConfiguracionServidorException("No se ha podido grabar en el fichero" + this.nombreFicheroProperties, ioe);
        }
    }

}
