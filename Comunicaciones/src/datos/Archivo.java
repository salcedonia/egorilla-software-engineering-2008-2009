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

package datos;

import java.io.Serializable;

/**
 * Clase que trata todo lo relacionado con los archivos que se comparten en
 * el servidor. 
 * 
 * @author Luis Ayuso, Ivan Munsuri, Javier Salcedo
 */
public class Archivo implements Serializable {

    // ATRIBUTOS
    public String _nombre;  // El archivo debe tener varios nombres, ya q si el 
                            // primer nombre es fake, no podra saberse el nombre real
    public String _hash;
    public long _tamano;
    public TipoArchivo _tipo;

    /**
     * Constructor por defecto de la clase Archivo.
     */
    public Archivo() {
    
    }
    
    public  Archivo(String nombre, String hash) {
        setNombre(nombre);
        setHash(hash);
    }
    
    /**
     * Constructor de la clase Archivo.
     * 
     * @param nombre Nombre del archivo.
     * @param hash Hash del archivo.
     * @param tamano Tamaño del archivo.
     * @param tipo Tipo del archivo.
     */
    public Archivo(String nombre, String hash, long tamano, TipoArchivo tipo) {
        
        setInfoArchivo(nombre, hash, tamano, tipo);
    }

    /**
     * Devuelve el enumerado con el tipo de archivo correspondiente a la extensión 
     * que se pasa como parámetro.
     * 
     * @param f Referencia al fichero.
     * 
     * @return El enumerado con el tipo de archivo correspondiente a la extensión 
     * que se pasa como parámetro.
     */
    /* private TipoArchivo asignarTipoArchivo(File f) {
    
    String fileName = null;   
    String extension;  
    fileName = fileName.trim();
    
    int dotPos = fileName.lastIndexOf(".");
    extension = fileName.substring(dotPos);
    
    return TipoArchivo.devuelveTipo(extension);
    }*/
    
    /**
     * Recupera el nombre de este archivo
     * 
     * @return El nombre del archivo
     */
    public String getNombre() {

        return _nombre;
    }

    /**
     * Establece el nombre del fichero a valor <b>nombre</b>.
     * 
     * @param nombre Nuevo valor a establecer.
     */
    public void setNombre(String nombre) {

        _nombre = nombre;
    }

    /**
     * Recupera el hash de este archivo. El hash es unico para el archivo 
     * independientemente del nombre que tenga por lo que dos archivos con el 
     * mismo hash serán el mismo siempre.
     * 
     * @return el hash del archivo.
     */
    public String getHash() {
        
        return _hash;
    }

    /**
     * Establece el hash del archivo a valor <b>hash</b>.
     * 
     * @param hash Nuevo valor a establecer.
     */
    public void setHash(String hash) {
       
        _hash = hash;
    }

    /**
     * Devuelve el tipo de archivo asociado al archivo.
     * 
     * @return El tipo de archivo asociado al archivo.
     */
    public TipoArchivo getTipo() {
        
        return _tipo;
    }

    /**
     * Establece el tipo del archivo a valor <b>tipo</b>.
     * 
     * @param tipo Nuevo valor a establecer.
     */
    public void setTipo(TipoArchivo tipo) {
        
        _tipo = tipo;
    }

    /**
     * Recupera el tamaño de este archivo.
     * 
     * @return El tamaño del archivo.
     */
    public long getSize() {
        
        return _tamano;
    }

    /**
     * Establece el tamano del archivo a valor <b>tamano</b>.
     * 
     * @param tamano Nuevo valor a establecer.
     */
    public void setSize(long tamano) {
        
        _tamano = tamano;
    }
    
    /**
     * Establece la informacion del archivo con la informacion correspondiente.
     * 
     * @param nombre Nombre del archivo.
     * @param hash Hash del archivo.
     * @param tamano Tamano del archivo
     * @param tipo Tipo del archivo
     */
    public void setInfoArchivo(String nombre, String hash, long tamano, TipoArchivo tipo) {
        
        setNombre(nombre);
        setHash(hash);
        setSize(tamano);
        setTipo(tipo);
    }
        
    /**
     * Compara dos instancias para saber si referencian al mismo archivo.
     *
     * @param archivo, el archivo a comparar con este.
     * 
     * @return Verdadero si las dos instancias apuntan al mismo archivo.
     */
    public boolean comparaArchivo(Archivo a) {
        
        return _hash.equals(a.getHash());
    }

    @Override
    public boolean equals(Object o) {
     //System.out.println("equals archivo");
      try{

        Archivo a = (Archivo) o;

        boolean tipo = _tipo == a.getTipo(), 
                tama = _tamano == a.getSize(), 
                hash = _hash.compareTo( a.getHash() ) == 0,
                nombre = _nombre.compareTo( a.getNombre() ) == 0;

        return tipo && tama && hash && nombre;

      }catch( Exception e ){
        //Si salta la excepcion en el casting es que no era una instacia de.
        return false;
        //e.printStackTrace();
      }/*finally{
        
      }*/
    }
    
    
    @Override
    public String toString (){
        return _nombre + "\t" + _hash + "\t" + this._tamano + "\t" +_tipo.name(); 
    }
}
