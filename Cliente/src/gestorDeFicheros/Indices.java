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
/**
 * Paquete donde tenemos las clases que tratan con el disco.
 */
package gestorDeFicheros;

import java.util.*;
import java.io.*;

import datos.*;

/**
 * Clase que representa los indices de los archivos temporales. Dichos indices referencian a un
 * archivo en descarga, los cuales llevan la contabilidad de todos los fragmentos que tenemos y
 * que aun nos faltan de un fichero concreto.
 * @author Ivan Munsuri Ibanez
 */
public class Indices implements Serializable{

  /**
   * Archivo que actualmente se encuentra en descarga.
   */
  private Archivo _archivo;

  /**
   * Indica los fragmentos que tengo del archivo en concreto.
   */
  private Vector<Fragmento> _indicesTengo;

  /**
   * Indica los fragmentos que nos faltan del archivo en concreto.
   */
  private Vector<Fragmento> _indicesFaltan;


  /*public Indices(){
    _indicesTengo = new Vector<Fragmento>();
    _indicesFaltan = new Vector<Fragmento>();
  }*/

  /**
   * Constructor que crea el indice de un fichero concreto. Basicamente el indice contiene la
   * informacion del archivo del que lleva la cuenta, y los fragmentos que tengo y que faltan
   * del mismo.
   * @param archivo .
   * @param indicesTengo .
   * @param indicesFaltan .
   */
  public Indices( Archivo archivo, Vector<Fragmento> indicesTengo, Vector<Fragmento> 
      indicesFaltan ){
    _archivo = archivo;
    _indicesTengo = indicesTengo;
    _indicesFaltan = indicesFaltan;
  }

  /**
   * Obtiene el archivo en concreto al que pertenece el indice.
   * @return Devuelve un objeto archivo de dicho indice.
   */
  public Archivo getArchivo(){
    return _archivo;
  }

  /**
   * Obtiene una lista con los fragmentos que tenemos de un archivo en concreto.
   * @return Devuelve la lista de fragmento que tengo del archivo en concreto.
   */
  public Vector<Fragmento> getIndicesTengo(){
    return _indicesTengo;
  }

  /**
   * Obtiene una lista con los fragmentos que faltan de un archivo en concreto.
   * @return Devuelve la lista de fragmento que faltan del archivo en concreto.
   */
  public Vector<Fragmento> getIndicesFaltan(){
    return _indicesFaltan;
  }

  /**
   * Incluye un fragmento en la lista de fragmentos que tengo de un archivo concreto. Esto hace
   * que dicho fragmento que ahora tengo se elimine de la lista de los fragmento que faltan.
   * @param fragmento es el fragmento ahora ya tengo.
   */
  public void addTengo( Fragmento fragmento ){
    _indicesTengo.add( fragmento );
    removeFaltan( fragmento );
  }

  /**
   * Incluye un fragmento en la lista de fragmentos que faltan de un archivo concreto. Esto hace
   * que dicho fragmento que me falta se elimine de la lista de los fragmentos que tengo.
   * @param fragmento es el fragmento que ahora me falta.
   */
  public void addFaltan( Fragmento fragmento ){ //No creo q se llame nunca a este
    _indicesFaltan.add( fragmento );
    removeTengo( fragmento );
  }

  /*public Fragmento getIndice( int index ){
    return _indices.get( index );
  }*/

  /**
   * Devuelve un booleano indicando si el fragmento esta, o no, en la lista de los fragmentos que
   * tengo.
   * @param fragmento es el fragmento que se va a comprobar si esta.
   * @return Devuelve un booleano indicando si esta o no.
   */
  public boolean containsTengo( Fragmento fragmento ){ //comprobar q funciona el contains
    return _indicesTengo.contains( fragmento );
  }

  /**
   * Devuelve un booleano indicando si el fragmento esta, o no, en la lista de los fragmentos que
   * faltan.
   * @param fragmento es el fragmento que se va a comprobar si esta.
   * @return Devuelve un booleano indicando si esta o no.
   */
  public boolean containsFaltan( Fragmento fragmento ){
    return _indicesFaltan.contains( fragmento );
  }

  /**
   * Elimina un fragmento concreto de la lista de los fragmentos que tengo.
   * @param fragmento es el fragmento a eliminar.
   */
  public void removeTengo( Fragmento fragmento ){
    _indicesTengo.remove( fragmento );
  }

  /**
   * Elimina un fragmento concreto de la lista de los fragmentos que faltan.
   * @param fragmento es el fragmento a eliminar.
   */
  public void removeFaltan( Fragmento fragmento ){
    //_indicesFaltan.remove( fragmento ); probar!!!!!!
    
    Fragmento fragAux = null;
    boolean encontrado = false;

    for( int i = 0;  i < _indicesFaltan.size() && encontrado == false;  i++ ){
      fragAux = _indicesFaltan.get( i );
      //System.out.println("Comparo <"+fragAux.getOffset()+"> con <"+fragmento.getOffset()+">");
      if( fragAux.getOffset() == fragmento.getOffset() ){
        //System.out.println("Eliminoooooo");
        //_indicesFaltan.remove( fragAux ); no elimina asi, no se xq
        _indicesFaltan.remove( i );
        encontrado = true;
      }
    }
  }

}
