package gestorDeFicheros;

import java.util.*;
import mensajes.serverclient.*;
import datos.*;

/**
 * Clase que ofrece las operaciones que se pueden realizar sobre una lista de archivos.
 *
 * @author Ivan Munsuri Ibanez
 */
public class ManejarListaArchivos {
  
  /**
   * Busca un archivo a traves de su hash en la lista de archivos especificada por parametro.
   * @param listaArchivos es la lista de archivos donde se hara la busqueda.
   * @param hash es el valor del MD5 a traves del cual se compara el archivo buscado.
   * @return Devuelve un objeto archivo con el resto de metadatos referentes al hash en caso de
   *         ser entrado. Sino fue encontrado se devuelve un archivo nulo.
   */
  protected Archivo buscarArchivoEnLista( ListaArchivos listaArchivos, String hash){
    boolean encontrado = false;
    Archivo encontradoArchivo = null;
    
    for( int i = 0;  i < listaArchivos.size() && encontrado == false;  i++ ){
      encontradoArchivo = listaArchivos.get( i );
      //System.out.println("Comparo hashA:"+encontradoArchivo.getHash()+" con hashB:"+hash);
      if( comparaHash( encontradoArchivo.getHash(), hash ) == true )
        encontrado = true;
    }
    if( encontrado == false ){
      //Sino lo ha encontrado devuelvo un nulo
      encontradoArchivo = null;
    }//Sino no tengo que cambiar nada
 
    return encontradoArchivo;
  }

  /**
   * .
   * @param hashA .
   * @param hashB .
   * @return .
   */
  protected boolean comparaHash( String hashA, String hashB){
    if( hashA.compareTo( hashB ) == 0 )
      return true;
    else
      return false;
  }

  /**
   *
   * @param archivo .
   * @param lista .
   */
  protected void includirArchivoEnLista( Archivo archivo, ListaArchivos lista ){
    lista.add( archivo );
  }

  /**
   *
   * @param archivo .
   * @param lista .
   */
  protected void eliminarArchivoDeLista( Archivo archivo, ListaArchivos lista ){
    //System.out.println( lista.remove( archivo ) );
    Archivo archAux = null;
    boolean encontrado = false;

    for( int i = 0;  i < lista.size() && encontrado == false;  i++ ){
      archAux = lista.get( i );
      //System.out.println("Comparo <"+fragAux.getOffset()+"> con <"+fragmento.getOffset()+">");
      if( archAux.equals( archivo ) == true ){
        //System.out.println("Eliminoooooo");
        //_indicesFaltan.remove( fragAux ); no elimina asi, no se xq
        lista.remove( i );
        encontrado = true;
      }
    }
  }

  /**
   *
   * @param archivo .
   * @param listaA .
   * @param listaB .
   */
  protected void moverArchivoEntreListas( Archivo archivo, ListaArchivos listaA, ListaArchivos listaB ){
    //Un archivo de la lista A se elimina, pasando a la listaB
    listaA.remove( archivo );
    listaB.add( archivo );
  }
 

  /**
   *
   * @param listaA .
   * @param listaB .
   */
  public ListaArchivos unirListas( ListaArchivos listaA, ListaArchivos listaB ){
    ListaArchivos listaNueva = new ListaArchivos();

    for( int i = 0;  i < listaA.size();  i++ ){
      listaNueva.add( listaA.get( i ) );
    }

    for( int i = 0;  i < listaB.size();  i++ ){
      listaNueva.add( listaB.get( i ) );
    }

    return listaNueva;
  }


  /**
   *
   * @param lista .
   */
  public void recorrerListaArchivos( ListaArchivos lista ){
    if( lista == null ){
      System.out.println("Lista nula. 0 ficheros.");
    }else{
    for( int i = 0;  i < lista.size();  i++) {
      //System.out.println( lista.elementAt(i).getNombre() );

      //System.out.print( lista.elementAt(i).getNombre() );
      //System.out.println( " - "+lista.elementAt(i).getHash() );
      
      System.out.println( lista.elementAt(i).toString() );
    }
    System.out.println("\n<" + lista.size() + "> ficheros.");
    }
  }
}
