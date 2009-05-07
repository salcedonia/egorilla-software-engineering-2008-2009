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
   * .
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
   * .
   * @return .
   */
  public Archivo getArchivo(){
    return _archivo;
  }

  /**
   * .
   * @return .
   */
  public Vector<Fragmento> getIndicesTengo(){
    return _indicesTengo;
  }

  /**
   * .
   * @return .
   */
  public Vector<Fragmento> getIndicesFaltan(){
    return _indicesFaltan;
  }

  /**
   * .
   * @param fragmento .
   */
  public void addTengo( Fragmento fragmento ){
    _indicesTengo.add( fragmento );
    removeFaltan( fragmento );
  }

  /**
   * .
   * @param fragmento .
   */
  public void addFaltan( Fragmento fragmento ){ //No creo q se llame nunca a este
    _indicesFaltan.add( fragmento );
    removeTengo( fragmento );
  }

  /*public Fragmento getIndice( int index ){
    return _indices.get( index );
  }*/

  /**
   * .
   * @param fragmento .
   * @return .
   */
  public boolean containsTengo( Fragmento fragmento ){ //comprobar q funciona el contains
    return _indicesTengo.contains( fragmento );
  }

  /**
   * .
   * @param fragmento .
   * @return .
   */  public boolean containsFaltan( Fragmento fragmento ){
    return _indicesFaltan.contains( fragmento );
  }

  /**
   * .
   * @param fragmento .
   */
  public void removeTengo( Fragmento fragmento ){
    _indicesTengo.remove( fragmento );
  }

  /**
   * .
   * @param fragmento .
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
