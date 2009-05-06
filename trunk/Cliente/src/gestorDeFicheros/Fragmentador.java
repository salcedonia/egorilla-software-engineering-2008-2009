/**
 * Paquete donde tenemos las clases que tratan con el disco.
 */
package gestorDeFicheros;

import mensajes.serverclient.*;
import datos.*;
import java.io.*;
import java.util.*;

/**
 * Fragmenta los archivos de los directorios especificados para los temporales y los completos.
 *
 * @author Ivan Munsuri Ibanez
 */
/*Es mas optimo diferencias el fragmentador de completos e incompletos, ya que preguntar 
 * por una variable es mas rapido que buscar en que lista esta el fichero.*/

/*Hacer de properties la extension de los archivos de indices*/
public class Fragmentador{

  //Este podria volver a leer los datos de las properties o que le pasa los valores el 
  //gestor de disco.
  //Hacer properties

  /**
   * Es la extesion que acompana a los archivos de indices.
   */
  private String _extesionIndices;

  //private String _extesionFicheroTemporal;

  /**
   * Es el tamano de bytes maximo que puede tener un fragmento.
   */
  private int _tamanioBytesFragmento;

  /**
   * Es la ruta relativa del directorio de los ficheros temporales.
   */
  private String _directorioTemporales;

  /**
   * Es la ruta relativa del directorio de los ficheros completos.
   */
  private String _directorioCompletos;

  /**
   * Es el propio gestor de disco que creado a dicho Fragmentador.
   */
  private GestorDisco _gestorDisco;


  /**
   * Constructor que recibe una instancia del disco para obtener la informacion necesaria para
   * el fragmentado de los archivos del usuario, temporales y completos. A traves del gestor de
   * disco, y gracias ensamblador, se podra fragmentar los archivos en estado temporal sin tener
   * que esperar a tenerlos completos.
   * @param gestorDisco es la instancia del disco de donde obtengo la informacion referente a 
   *                    los archivos.
   */
  public Fragmentador( GestorDisco gestorDisco ){
    _directorioCompletos = gestorDisco.getDirectorioCompletos();
    _directorioTemporales = gestorDisco.getDirectorioTemporales();
    _gestorDisco = gestorDisco;

    _extesionIndices = gestorDisco.getExtesionIndices();
    _tamanioBytesFragmento = gestorDisco.getTamanioBytesFragmento();
  }


  /**
   * Metodo auxiliar que convierto un array de objetos de Byte a un array de tipos primitivos 
   * byte.
   * @param bytes es un array de objetos del tipo Byte.
   * @return Devuelve un array de tipos primitivos byte.
   */
  private Byte[] primitivoAObjeto( byte[] bytes ){
    Byte[] oBytesFragmento = new Byte[ bytes.length ];

    for( int i = 0;  i < bytes.length;  i++ ){
      //oBytesFragmento[ i ] = new Byte( bytes[ i ] );
      oBytesFragmento[ i ] = Byte.valueOf( bytes[ i ] );
    }

    return oBytesFragmento;
  }

  /**
   * Obtiene un bloque de byte's concreto correspondiente al archivo que indica el fragmento 
   * recibido. El archivo que indica el fragmento que se recibe debe encontrarse en alguna de
   * las listas de archivos, temporales o completos.
   * @param fragmento es el fragmento que indicara de que archivo se recuperaran los bytes.
   * @return Devuelve un array de objetos Byte.
   */
  public Byte[] dameBytesDelFragmento( Fragmento fragmento ){
    int bytesLeidos = 0, off, len = 0;
    byte[] bytesFragmento;
    Byte[] oBytesFragmento = null;
    Archivo archivoRequerido;
    File fichero;
    RandomAccessFile punteroFichero;
    ManejarIndices manejarIndices = _gestorDisco.getManejarIndices();
    ManejarListaArchivos manejarListaArchivos = _gestorDisco.getManejarListaArchivos();
    String hashFragmento = fragmento.getHash();
    ListaArchivos listaCompletos = _gestorDisco.getListaArchivosCompletos();
    ListaArchivos listaTemporales = _gestorDisco.getListaArchivosTemporales();
    
    //Compruebo si tengo el fichero con ese hash (aunque se supone que siempre estara)
    
    //Debo buscar por hash y no por nombre, yaq el nombre no tiene xq coincidir 
    //buscar en las listas
    archivoRequerido = manejarListaArchivos.buscarArchivoEnLista( listaCompletos, 
        hashFragmento );
    if( archivoRequerido == null ){
      //No esta en los completos, asi que miramos en los incompletos
      archivoRequerido = manejarListaArchivos.buscarArchivoEnLista( listaTemporales, 
          hashFragmento );
      if( archivoRequerido == null ){
        //El fichero no EXISTE - devuelvo un null - ERROR
      }else{
        //Voy al fichero de indices y miro si esa parte del fragmento (offset)     
        System.out.println("Obteniendo bytes del archivo temporal <"+ archivoRequerido.getNombre()+">");
        fichero = new File( _directorioTemporales+"//" + archivoRequerido.getNombre()
            + _extesionIndices );
        Indices indices = manejarIndices.leeFicheroIndices( fichero );
        
        //Miro si el fragmento esta en ese array
        if( indices.containsTengo( fragmento ) == true ){
          //Si esta -> Obtengo los bytes del fragmento que me han pedido
          try{
          punteroFichero = new RandomAccessFile( fichero, "r" );
          off = (int)fragmento.getOffset();
          int tamanioBytesFragmentoAux = (int)(fragmento.getTama() - fragmento.getOffset() );
          if( tamanioBytesFragmentoAux < _tamanioBytesFragmento )
            len = tamanioBytesFragmentoAux;
          else
            len = _tamanioBytesFragmento;
          bytesFragmento = new byte[ len ];
          //System.out.println("offset "+off +" len "+len+" point "+
          //punteroFichero.getFilePointer() );
          punteroFichero.seek( off );
          //bytesLeidos = punteroFichero.read( bytesFragmento, off, len );
          bytesLeidos = punteroFichero.read( bytesFragmento );
          oBytesFragmento = primitivoAObjeto( bytesFragmento );
          punteroFichero.close();
          }catch( Exception e ){
            e.printStackTrace();
          }
          //bytesLeidos debe coincidir con fragmento.getTama() - fragmento.getOffset()
          //Fragmento corrupto! o archivo corrupto
          //bytesLeidos > len - no es posible, se lee hasta un maximo de len bytes
          //bytesLeidos == len - guay!
          //bytesLeidos < len - informacion del fragmento inadecuada
          if( bytesLeidos < len ){
            //logger de error
          }
        }else{
          //No hago nada, devuelvo un null - o bueno, puede devolver el array vacio, VER, ERROR
          //Sino esta -> null, vacio, ERROR
        }        
      }
    }else{
      //Obtengo los bytes del fragmento que me han pedido - como esta completo no 
      //tengo que hacer na
      try{
        System.out.println("Obteniendo bytes del archivo completo <"+ archivoRequerido.getNombre()+">");
      fichero = new File( _directorioCompletos+"//"+ archivoRequerido.getNombre() );
      punteroFichero = new RandomAccessFile( fichero, "r" );
      off = (int)fragmento.getOffset();
      int tamanioBytesFragmentoAux = (int)(fragmento.getTama() - fragmento.getOffset() );
      if( tamanioBytesFragmentoAux < _tamanioBytesFragmento )
        len = tamanioBytesFragmentoAux;
      else
        len = _tamanioBytesFragmento;
      bytesFragmento = new byte[ len ];
      //System.out.println("offset "+off +" len "+len+" point "+
      //punteroFichero.getFilePointer() );
      punteroFichero.seek( off );
      //bytesLeidos = punteroFichero.read( bytesFragmento, off, len );
      bytesLeidos = punteroFichero.read( bytesFragmento );
      //System.out.println("point "+punteroFichero.getFilePointer() );
      oBytesFragmento = primitivoAObjeto( bytesFragmento );
      punteroFichero.close();
      }catch( Exception e ){
        System.out.println("Auch! se escogorcio el fichero <"+ archivoRequerido.getNombre()+">");
        //e.printStackTrace();
      }
      //bytesLeidos debe coincidir con fragmento.getTama() - fragmento.getOffset()
      //Fragmento corrupto! o archivo corrupto
      //bytesLeidos > len - no es posible, se lee hasta un maximo de len bytes
      //bytesLeidos == len - guay!
      //bytesLeidos < len - informacion del fragmento inadecuada
      if( bytesLeidos < len ){
        //logger de error
      }
    }    
    return oBytesFragmento;
  }


  /**
   * TODO: sin implementar.
   * @param hash .
   * @return .
   */
  public int cantidadFragmentosArchivo( String hash ){
    int cantidadFragmentos;
    //Busco en las listas el archivo y llamo a la otra
    ListaArchivos listaTemporales = _gestorDisco.getListaArchivosTemporales();
    ManejarListaArchivos manejarListaArchivos = _gestorDisco.getManejarListaArchivos();
    Archivo archivoRequerido = manejarListaArchivos.buscarArchivoEnLista( listaTemporales, hash );
    if( archivoRequerido == null ){
        cantidadFragmentos = 0;
    }else{
      cantidadFragmentos = cantidadFragmentosArchivo( archivoRequerido );
    }
    return cantidadFragmentos;
  }


  /**
   * .
   * @param archivo .
   * @return .
   */
  public int cantidadFragmentosArchivo( Archivo archivo ){
    long tamanio = archivo.getSize();
    int cantidadFragmentos;

    cantidadFragmentos = (int)tamanio / _tamanioBytesFragmento;

    if( tamanio % _tamanioBytesFragmento == 0 ){
      //Al no haber decimales, todas las partes tiene el mismo tamano
    }else{
      cantidadFragmentos+=1;
    }

    return cantidadFragmentos;
  }

  public Vector<Fragmento> queFragmentosTienes( String hash ){
    //Lo primero que hago es bucar en hash en la lista de temporales y de completos
    Archivo archivoRequerido;
    Vector<Fragmento> listaFragmento = null;
    ManejarIndices manejarIndices = _gestorDisco.getManejarIndices();
    ManejarListaArchivos manejarListaArchivos = _gestorDisco.getManejarListaArchivos();
    ListaArchivos listaCompletos = _gestorDisco.getListaArchivosCompletos();
    ListaArchivos listaTemporales = _gestorDisco.getListaArchivosTemporales();
    //Compruebo si tengo el fichero con ese hash (aunque se supone que siempre estara)    
    //Debo buscar por hash y no por nombre, yaq el nombre no tiene xq coincidir 
    //buscar en las listas
    archivoRequerido = manejarListaArchivos.buscarArchivoEnLista( listaCompletos, hash );
    if( archivoRequerido == null && hash != null){
      //Debo buscarlo en los temporales
      //No esta en los completos, asi que miramos en los incompletos
      System.out.println("El archivo <"+hash+"> NO esta en los completos");
      archivoRequerido = manejarListaArchivos.buscarArchivoEnLista( listaTemporales, hash );
      if( archivoRequerido == null ){
        System.out.println("El archivo <"+hash+"> TAMPOCO esta en los temporales - ERROR");
        //El fichero no EXISTE - devuelvo un null - ERROR
      }else{
        System.out.println("El archivo <"+archivoRequerido.getNombre()+"> SI esta en los temporales - Abro fichero indices...");
        //Voy al fichero de indices y miro si esa parte del fragmento (offset)        
        File fichero = new File( _directorioTemporales+"//" + archivoRequerido.getNombre()
            + _extesionIndices );
        Indices indices = manejarIndices.leeFicheroIndices( fichero );
        listaFragmento = indices.getIndicesTengo();
      }
    }else{
      System.out.println("El archivo <"+archivoRequerido.getNombre()+"> se encuentra en los completos");
      //Tengo todo los fragmentos, asi que los digo todos!
      //Esto igual seria mejor: serializarlo y guardado - No va a cambiar asi no tengo que 
      //recarcular ni volver a crear todos los objetos o guardarlo en mem.
      //Puedo calcular la cantidad de partes si los bytes del mismo sin fijos
      //Si es variable mejor con un while
      //TODO: metodo ya creado por ahi
      listaFragmento = new Vector<Fragmento>();
      Fragmento fragmento = new Fragmento( archivoRequerido.getNombre(), 
          archivoRequerido.getHash(), _tamanioBytesFragmento, 0 ); //0 por ser el primero
      listaFragmento.add( fragmento );
      for( int i = 1;  fragmento.getOffset()+_tamanioBytesFragmento < fragmento.getTama();  i++ ){
        fragmento = new Fragmento( archivoRequerido.getNombre(), archivoRequerido.getHash(), 
            _tamanioBytesFragmento, i*_tamanioBytesFragmento );
        listaFragmento.add( fragmento );
      }
      //Esto ultimo sobra, simplemente que el ultimo fragmento tiene
      //un tamano de   fragmento.getTama() - fragmento.getOffset()   y no tiene xq ser de 512
      /*if( fragmento.getOffset() < fragmento.getTama() ){
        long diferencia = fragmento.getTama() - fragmento.getOffset();
        long offsetDiff = fragmento.getOffset()+diferencia;
        fragmento = new Fragmento( archivoRequerido.getNombre(),archivoRequerido.getHash(), 
            archivoRequerido.getSize(), offsetDiff );
        listaFragmento.add( fragmento );
      }*/
    }
    return listaFragmento;
  }

  
  public Vector<Fragmento> queFragmentosFaltan( String hash ){
    //Lo primero que hago es bucar en hash en la lista de temporales y de completos
    Archivo archivoRequerido;
    Vector<Fragmento> listaFragmento = null;
    ManejarIndices manejarIndices = _gestorDisco.getManejarIndices();
    ManejarListaArchivos manejarListaArchivos = _gestorDisco.getManejarListaArchivos();
    ListaArchivos listaCompletos = _gestorDisco.getListaArchivosCompletos();
    ListaArchivos listaTemporales = _gestorDisco.getListaArchivosTemporales();

    //Compruebo si tengo el fichero con ese hash (aunque se supone que siempre estara)
    
    //Debo buscar por hash y no por nombre, yaq el nombre no tiene xq coincidir 
    //buscar en las listas
    archivoRequerido = manejarListaArchivos.buscarArchivoEnLista( listaCompletos, hash );
    if( archivoRequerido == null && hash != null){
      //Debo buscarlo en los temporales
      //No esta en los completos, asi que miramos en los incompletos
      System.out.println("El archivo <"+hash+"> NO esta en los completos");
      archivoRequerido = manejarListaArchivos.buscarArchivoEnLista( listaTemporales, hash );
      if( archivoRequerido == null ){
        //El fichero no EXISTE - devuelvo un null - ERROR
        System.out.println("El archivo <"+hash+"> TAMPOCO esta en los temporales - ERROR");
      }else{
        System.out.println("El archivo <"+archivoRequerido.getNombre()+"> esta en los temporales");
        //Voy al fichero de indices y miro si esa parte del fragmento (offset)        
        File fichero = new File( _directorioTemporales+"//" + archivoRequerido.getNombre()
            + _extesionIndices );
        Indices indices = manejarIndices.leeFicheroIndices( fichero );
        listaFragmento = indices.getIndicesFaltan();
      }
    }else{
      System.out.println("El archivo <"+archivoRequerido.getNombre()+"> esta en los completos");
      //Tengo todo los fragmentos, asi que los digo todos!
      //Esto igual seria mejor: serializarlo y guardado - No va a cambiar asi no tengo que 
      //recarcular ni volver a crear todos los objetos o guardarlo en mem.
      //Puedo calcular la cantidad de partes si los bytes del mismo sin fijos
      //Si es variable mejor con un while
      listaFragmento = new Vector<Fragmento>();//le creo vacio xq no falta ninguno
      /*Fragmento fragmento = new Fragmento( archivoRequerido.getNombre(), 
          archivoRequerido.getHash(), 0, archivoRequerido.getSize() ); 0 por ser el primero
      listaFragmento.add( fragmento );
      for( int i = 0;  fragmento.getOffset() == fragmento.getTama();  i++ ){
        fragmento = new Fragmento( archivoRequerido.getNombre(),archivoRequerido.getHash(), 
            i*tamanioBytesFragmento, archivoRequerido.getSize() );
        listaFragmento.add( fragmento );
      }*/
    }
    return listaFragmento;
  }
}

