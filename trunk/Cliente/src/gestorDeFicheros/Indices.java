package gestorDeFicheros;

import java.util.*;
import java.io.*;

import datos.*;

public class Indices implements Serializable{

  private Archivo _archivo;

  private Vector<Fragmento> _indicesTengo;

  private Vector<Fragmento> _indicesFaltan;


  /*public Indices(){
    _indicesTengo = new Vector<Fragmento>();
    _indicesFaltan = new Vector<Fragmento>();
  }*/

  public Indices( Archivo archivo, Vector<Fragmento> indicesTengo, Vector<Fragmento> 
      indicesFaltan ){
    _archivo = archivo;
    _indicesTengo = indicesTengo;
    _indicesFaltan = indicesFaltan;
  }

  public Archivo getArchivo(){
    return _archivo;
  }

  public Vector<Fragmento> getIndicesTengo(){
    return _indicesTengo;
  }

  public Vector<Fragmento> getIndicesFaltan(){
    return _indicesFaltan;
  }

  public void addTengo( Fragmento fragmento ){
    _indicesTengo.add( fragmento ); //comprobar que se elimina bien
    removeFaltan( fragmento );
  }

  public void addFaltan( Fragmento fragmento ){ //No creo q se llame nunca a este
    _indicesFaltan.add( fragmento );
    removeTengo( fragmento );
  }

  /*public Fragmento getIndice( int index ){
    return _indices.get( index );
  }*/

  public boolean containsTengo( Fragmento fragmento ){ //comprobar q funciona el contains
    return _indicesTengo.contains( fragmento );
  }

  public boolean containsFaltan( Fragmento fragmento ){
    return _indicesFaltan.contains( fragmento );
  }

  public void removeTengo( Fragmento fragmento ){
    _indicesTengo.remove( fragmento );
  }

  public void removeFaltan( Fragmento fragmento ){
    //_indicesFaltan.remove( fragmento );
    
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
