/* 
	This file is part of eGorilla.

    eGorilla is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    eGorilla is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with eGorilla.  If not, see <http://www.gnu.org/licenses/>.
*/
package gestorDeConfiguracion;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import util.Util;

/**
 * Clase generica que permite manejar de la misma forma ficheros que almacenan informacion diversa. 
 * La informacion se guarda dentro de la clase como un ArrayList de objetos de la clase T
 * que se recibe como parametro.
 * 
 * @author F. Javier Sanchez Pardo
 */
public class FicheroInfo <T> implements Serializable{
    
    /**
     * Nombre del fichero que almacena la informacion manejada.
     */
    private String _nombreFicheroInfo;
    /**
     * Estructura en memoria que almacena la info leida del fichero mediante un 
     * array de objetos de clase <T>.
     */ 
    private ArrayList<T> _infoFichero;
    
    /**
     * Se inicializa el objeto con el nombre del fichero de info especificado. 
     */
    public FicheroInfo(String sNomFicheroInfo) throws ControlConfiguracionClienteException {
        
        _nombreFicheroInfo = sNomFicheroInfo;
    }

    /**
     * Devuelve el nombre del fichero de informacion de servidores.
     * 
     * @return El nombre del fichero de informacion de servidores.
     */
    public String getNombreFicheroInfo() {
        
        return _nombreFicheroInfo;
    }

    /**
     * Establece el valor del nombre del fichero de servidores a valor <b>nombreFicheroInfo</b>.
     * 
     * @param nombreFicheroInfo Nuevo valor a establecer.
     */
    public void setNombreFicheroInfo(String nombreFicheroInfo) {
        
        _nombreFicheroInfo = nombreFicheroInfo;
    }
    
    
    /**
     * Devuelve un ArrayList de objetos de clase T que contienen la informacion actual.
     * 
     * @return Un ArrayList de objetos de clase T que contienen la informacion actual.
     */
    public ArrayList<T> getInfoFichero() {
        
        return _infoFichero;
    }
    
    /**
     * Establece el arrayList de objetos de clase T y lo vuelca al disco.
     * 
     * @param alObjetos: ArrayList de objetos de clase T.
     * @throws gestorDeConfiguracion.ControlConfiguracionClienteException
     */
    public void setInfoFichero(ArrayList<T> alObjetos) throws ControlConfiguracionClienteException {
        
        _infoFichero = alObjetos;
        grabarFicheroInfo();
    }
    
            
    /**
     * Carga en el ArrayList de objetos T toda la info existente en el fichero,
     * mediante deserializacion.
     */
    public void cargarFicheroInfo() throws ControlConfiguracionClienteException {
        try {
            _infoFichero = (ArrayList<T>) Util.deserializar (_nombreFicheroInfo);
        } catch (FileNotFoundException fnfe) {
            throw new ControlConfiguracionClienteException("No se ha podido cargar el fichero" + _nombreFicheroInfo, fnfe);
        } catch (IOException ioe) {
            throw new ControlConfiguracionClienteException("No se ha podido cargar el fichero" + _nombreFicheroInfo, ioe);
        } catch (ClassNotFoundException cnfe) {
            throw new ControlConfiguracionClienteException("No se ha podido cargar el fichero" + _nombreFicheroInfo, cnfe);
        }
    }
    
    
    /**
     * Vuelca la info en memoria (el array de objetos T) en el fichero asociado (en disco)
     * mediante serializacion.
     */
    protected void grabarFicheroInfo() throws ControlConfiguracionClienteException {
        try {
            Util.serializar(_infoFichero, _nombreFicheroInfo);
        } catch (FileNotFoundException fnfe) {
             throw new ControlConfiguracionClienteException("No se ha podido grabar en el fichero" + _nombreFicheroInfo, fnfe);
        } catch (IOException ioe) {
             throw new ControlConfiguracionClienteException("No se ha podido grabar en el fichero" + _nombreFicheroInfo, ioe);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FicheroInfo<T> other = (FicheroInfo<T>) obj;
        if (!_nombreFicheroInfo.matches(other._nombreFicheroInfo) && (_nombreFicheroInfo == null || !_nombreFicheroInfo.equals(other._nombreFicheroInfo))) {
            return false;
        }
        if (_infoFichero != other._infoFichero && (_infoFichero == null || !_infoFichero.equals(other._infoFichero))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (this._nombreFicheroInfo != null ? this._nombreFicheroInfo.hashCode() : 0);
        hash = 59 * hash + (this._infoFichero != null ? this._infoFichero.hashCode() : 0);
        return hash;
    }
}
