
package gestorDeConfiguracion;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import util.Util;

/**
 * Clase generica que permite manejar de la misma forma ficheros que almacenan informacion diversa. 
 * La informacion se guarda dentro de la clase como un ArrayList de objetos de la clase T
 * que se recibe como parametro.
 * @author F. Javier Sanchez Pardo
 */

public class FicheroInfo <T>{
    
    //Nombre del fichero que almacena la informacion manejada.
    protected String _sNombreFicheroInfo;
    //Estructura en memoria que almacena la info leida del fichero mediante un array 
    //de objetos de clase <T>.
    protected ArrayList<T> _info;
    
    /**
     * Se inicializa el objeto con el nombre del fichero de info especificado. 
     */
    public FicheroInfo(String sNomFicheroInfo) throws ControlConfiguracionClienteException {
        this._sNombreFicheroInfo = sNomFicheroInfo;
    }
    
    /**
     * Devuelve un ArrayList de objetos de clase T que contienen la informacion actual
     */
    public ArrayList<T> obtenerConjuntoInfo() {
        return this._info;
    }
    
    /**
     * Establece el arrayList de objetos de clase T y lo vuelca al disco.
     * @param alObjetos: ArrayList de objetos de clase T.
     * @throws gestorDeConfiguracion.ControlConfiguracionClienteException
     */
    public void establecerConjuntoInfo(ArrayList<T> alObjetos) throws ControlConfiguracionClienteException {
        this._info = alObjetos;
        grabarFicheroInfo();
    }
    
            
    /**
     * Carga en el ArrayList de objetos T toda la info existente en el fichero,
     * mediante deserializacion.
     */
    protected void cargarFicheroInfo() throws ControlConfiguracionClienteException {
        try {
            this._info = (ArrayList<T>) Util.deserializar (_sNombreFicheroInfo);
        } catch (FileNotFoundException fnfe) {
            throw new ControlConfiguracionClienteException("No se ha podido cargar el fichero" + this._sNombreFicheroInfo, fnfe);
        } catch (IOException ioe) {
            throw new ControlConfiguracionClienteException("No se ha podido cargar el fichero" + this._sNombreFicheroInfo, ioe);
        } catch (ClassNotFoundException cnfe) {
            throw new ControlConfiguracionClienteException("No se ha podido cargar el fichero" + this._sNombreFicheroInfo, cnfe);
        }
    }
    
    
    /**
     * Vuelca la info en memoria (el array de objetos T) en el fichero asociado (en disco)
     * mediante serializacion.
     */
    protected void grabarFicheroInfo() throws ControlConfiguracionClienteException {
        try {
            Util.serializar(this._info, this._sNombreFicheroInfo);
        } catch (FileNotFoundException fnfe) {
             throw new ControlConfiguracionClienteException("No se ha podido grabar en el fichero" + this._sNombreFicheroInfo, fnfe);
        } catch (IOException ioe) {
             throw new ControlConfiguracionClienteException("No se ha podido grabar en el fichero" + this._sNombreFicheroInfo, ioe);
        }
    }

}
