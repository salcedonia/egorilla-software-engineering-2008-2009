package gestorDeFicheros;

import mensajes.serverclient.*;
import datos.*;
import java.io.*;
import java.util.*;

/**
 * Fragmenta los archivos de los directorios especificados para los temporales y los completos.
 *
 */
/*Es mas optimo diferencias el fragmentador de completos e incompletos, ya que preguntar 
 * por una variable es mas rapido que buscar en que lista esta el fichero.*/

/*Hacer de properties la extension de los archivos de indices*/
public class Fragmentador extends ManejarListaArchivos {

  //Este podria volver a leer los datos de las properties o que le pasa los valores el 
  //gestor de disco.
  //Hacer properties

  private String extesionIndices = ".part.met";

  private String extesionFicheroTemporal = ".tmp";

  private int tamanioBytesFragmento = 512;

  private String _directorioTemporales;

  private String _directorioCompletos;

  //Lista de todos (temporales+completos)
  private ListaArchivos _listaTodos;

  //Lista de los temporales
  private ListaArchivos _listaTemporales;

  //Lista de los completos
  private ListaArchivos _listaCompletos; //Se necesita de un accion automatica o de recargar

  private GestorDisco _gestorDisco;

  /**
   */
  public Fragmentador( GestorDisco gestorDisco, String directorioCompletos, 
      String directorioTemporales){
    _directorioCompletos = directorioCompletos;
    _directorioTemporales = directorioTemporales;
    _gestorDisco = gestorDisco;

    //Obtengo sus referencias, para poder tener las listas actualizarlas
    _listaCompletos = gestorDisco.getListaArchivosCompletos();
    _listaTemporales = gestorDisco.getListaArchivosTemporales();
    _listaTodos = gestorDisco.getListaArchivosTodos();
  }

  private Indices leeFicheroIndices( File fichero ){
    Indices indices = null;
    try{
    //Deserializo el Array de fragmentos de archivo de indices
    FileInputStream ficheroIndices = new FileInputStream( fichero );
    byte[] bytes = new byte[ (int)fichero.length() ];        
    int byteIndicesLeidos = ficheroIndices.read( bytes );
    ficheroIndices.close();
    //para el ensamblador q es el q guarda, seria mejor guardar los bytes adicionales, es
    //decir, guadar los ultimos fragmentos añadidos, aunque me parece q sera mas facil, xo
    //ineficiente, recuperar el array de fragmentos anterior, añadir los fragmentos, y
    //sobreescribir todo
       
    ByteArrayInputStream bs = new ByteArrayInputStream( bytes ); // bytes es el byte[]
    ObjectInputStream is = new ObjectInputStream( bs );
    indices = (Indices)is.readObject();
    is.close();
    }catch( Exception e ){
      e.printStackTrace();
      return indices;
    }
    return indices;
  }

  private Byte[] primitivoAObjeto( byte[] bytes ){
    Byte[] oBytesFragmento = new Byte[ bytes.length ];

    for( int i = 0;  i < bytes.length;  i++ ){
      //oBytesFragmento[ i ] = new Byte( bytes[ i ] );
      oBytesFragmento[ i ] = Byte.valueOf( bytes[ i ] );
    }

    return oBytesFragmento;
  }


 public Byte[] dameBytesDelFragmento( Fragmento fragmento ){
    int bytesLeidos = 0, off, len = 0;
    byte[] bytesFragmento;
    Byte[] oBytesFragmento = null;
    Archivo archivoRequerido;
    File fichero;
    RandomAccessFile punteroFichero;

    String hashFragmento = fragmento.getHash();
    
    //Compruebo si tengo el fichero con ese hash (aunque se supone que siempre estará)
    
    //Debo buscar por hash y no por nombre, yaq el nombre no tiene xq coincidir 
    //buscar en las listas
    archivoRequerido = buscarArchivoEnLista( _listaCompletos, hashFragmento );
    if( archivoRequerido == null ){
      //No esta en los completos, asi que miramos en los incompletos
      archivoRequerido = buscarArchivoEnLista( _listaTemporales, hashFragmento );
      if( archivoRequerido == null ){
        //El fichero no EXISTE - devuelvo un null - ERROR
      }else{
        //Voy al fichero de indices y miro si esa parte del fragmento (offset)        
        fichero = new File( _directorioTemporales+"//" + archivoRequerido.getNombre()
            + extesionIndices );
        Indices indices = leeFicheroIndices( fichero );
        
        //Miro si el fragmento está en ese array
        if( indices.containsTengo( fragmento ) == true ){
          //Si esta -> Obtengo los bytes del fragmento que me han pedido
          try{
          punteroFichero = new RandomAccessFile( fichero, "r" );
          off = (int)fragmento.getOffset();
          int tamanioBytesFragmentoAux = (int)(fragmento.getTama() - fragmento.getOffset() );
          if( tamanioBytesFragmentoAux < tamanioBytesFragmento )
            len = tamanioBytesFragmentoAux;
          else
            len = tamanioBytesFragmento;
          bytesFragmento = new byte[ len ];
          //System.out.println("offset "+off +" len "+len+" point "+punteroFichero.getFilePointer() );
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
      fichero = new File( _directorioCompletos+"//"+ archivoRequerido.getNombre() );
      punteroFichero = new RandomAccessFile( fichero, "r" );
      off = (int)fragmento.getOffset();
      int tamanioBytesFragmentoAux = (int)(fragmento.getTama() - fragmento.getOffset() );
      if( tamanioBytesFragmentoAux < tamanioBytesFragmento )
        len = tamanioBytesFragmentoAux;
      else
        len = tamanioBytesFragmento;
      bytesFragmento = new byte[ len ];
      //System.out.println("offset "+off +" len "+len+" point "+punteroFichero.getFilePointer() );
      punteroFichero.seek( off );
      //bytesLeidos = punteroFichero.read( bytesFragmento, off, len );
      bytesLeidos = punteroFichero.read( bytesFragmento );
      //System.out.println("point "+punteroFichero.getFilePointer() );
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
    }    
    return oBytesFragmento;
  }


 public int cantidadFragmentosArchivo( String hash ){
    //Busco en las listas el archivo y llamo a la otra
    //int cantidadFragmentosArchivo( Archivo archivo )
    return 0;
  }


  public int cantidadFragmentosArchivo( Archivo archivo ){
    long tamanio = archivo.getSize();
    int cantidadFragmentos;

    cantidadFragmentos = (int)tamanio / tamanioBytesFragmento;

    if( tamanio % tamanioBytesFragmento == 0 ){
      //Al no haber decimales, todas las partes tiene el mismo tamaño
    }else{
      cantidadFragmentos+=1;
    }

    return cantidadFragmentos;
  }

  public Vector<Fragmento> queFragmentosTienes( String hash ){
    //Lo primero que hago es bucar en hash en la lista de temporales y de completos
    Archivo archivoRequerido;
    Vector<Fragmento> listaFragmento = null;
    //Compruebo si tengo el fichero con ese hash (aunque se supone que siempre estará)    
    //Debo buscar por hash y no por nombre, yaq el nombre no tiene xq coincidir 
    //buscar en las listas
    archivoRequerido = buscarArchivoEnLista( _listaCompletos, hash );
    if( archivoRequerido == null ){
      //Debo buscarlo en los temporales
      //No esta en los completos, asi que miramos en los incompletos
      System.out.println("No esta en los completos");
      archivoRequerido = buscarArchivoEnLista( _listaTemporales, hash );
      if( archivoRequerido == null ){
        System.out.println("No esta en los temporales tampoco - ERROR");
        //El fichero no EXISTE - devuelvo un null - ERROR
      }else{
        System.out.println("Si esta en los temporales - Abro fichero indices");
        //Voy al fichero de indices y miro si esa parte del fragmento (offset)        
        File fichero = new File( _directorioTemporales+"//" + archivoRequerido.getNombre()
            + extesionIndices );
        Indices indices = leeFicheroIndices( fichero );
        listaFragmento = indices.getIndicesTengo();
      }
    }else{
      System.out.println("Si esta en los completos");
      //Tengo todo los fragmentos, asi que los digo todos!
      //Esto igual seria mejor: serializarlo y guardado - No va a cambiar asi no tengo que 
      //recarcular ni volver a crear todos los objetos o guardarlo en mem.
      //Puedo calcular la cantidad de partes si los bytes del mismo sin fijos
      //Si es variable mejor con un while
      //TODO: metodo ya creado por ahi
      listaFragmento = new Vector<Fragmento>();
      Fragmento fragmento = new Fragmento( archivoRequerido.getNombre(), 
          archivoRequerido.getHash(), archivoRequerido.getSize(), 0 ); //0 por ser el primero
      listaFragmento.add( fragmento );
      for( int i = 1;  fragmento.getOffset()+tamanioBytesFragmento < fragmento.getTama();  i++ ){
        fragmento = new Fragmento( archivoRequerido.getNombre(),archivoRequerido.getHash(), 
            archivoRequerido.getSize(), i*tamanioBytesFragmento );
        listaFragmento.add( fragmento );
      }
      //Esto último sobra, simplemente que el ultimo fragmento tiene
      //un tamaño de   fragmento.getTama() - fragmento.getOffset()   y no tiene xq ser de 512
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

    //Compruebo si tengo el fichero con ese hash (aunque se supone que siempre estará)
    
    //Debo buscar por hash y no por nombre, yaq el nombre no tiene xq coincidir 
    //buscar en las listas
    archivoRequerido = buscarArchivoEnLista( _listaCompletos, hash );
    if( archivoRequerido == null ){
      //Debo buscarlo en los temporales
      //No esta en los completos, asi que miramos en los incompletos
      archivoRequerido = buscarArchivoEnLista( _listaTemporales, hash );
      if( archivoRequerido == null ){
        //El fichero no EXISTE - devuelvo un null - ERROR
        System.out.println("No esta en los temporales tampoco - ERROR");
      }else{
        //Voy al fichero de indices y miro si esa parte del fragmento (offset)        
        File fichero = new File( _directorioTemporales+"//" + archivoRequerido.getNombre()
            + extesionIndices );
        Indices indices = leeFicheroIndices( fichero );
        listaFragmento = indices.getIndicesFaltan();
      }
    }else{
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

  /** Igual borrar
   * @param directorioDescargas
   * @param nombreFicheroDescargado
   * @param hashFicheroDescargado
   */
  /*public Fragmentador(String directorioDescargas, String nombreFicheroDescargado, String hashFicheroDescargado){
    _directorio = directorioDescargas;
    _nombreFichero = nombreFicheroDescargado;
    _hashFichero = hashFicheroDescargado;
  }*/

  /**
   * @param directorioDescargas
   * @param hashFicheroDescargado
   */
  /*public Fragmentador(String directorioDescargas, String hashFicheroDescargado){
    _directorio = directorioDescargas;
    _hashFichero = hashFicheroDescargado;
  }*/

  /**
   * Crea un fragmentador del tipo especificado sobre cada uno de los ficheros del 
   * directorio especificado. Esto es ï¿½til cuando por ejemplo queremos tener fragmentados
   * todos los ficheros de un directorio. Ademï¿½s, puede servir para separar la fragmentaciï¿½n
   * de los archivos finalizados (sin part.met/.dat.met) de los archivos temporales 
   * (con part.met/.dat.met).
   * @param directorio directorio de ficheros a fragmentar.
   * @param tipoFragmentador indica el tipo de fragmentador a aplicar.
   */
  /*public Fragmentador( String directorio, int tipoFragmentador){
    _tipoFragmentador = tipoFragmentador;
    if( tipoFragmentador == COMPLETO ){
      this.directorioCompletos = directorio;
    }else
      if( tipoFragmentador == INCOMPLETO ){
        this.directorioIncompletos = directorio;
      }
   Habrï¿½a que comprobar que el tipo indicado es posible
  }*/

  /**
   * Crea un fragmentador sobre cada uno de los ficheros del directorio especificado. Esto es 
   * ï¿½til cuando por ejemplo queremos tener fragmentados todos los ficheros de un directorio. 
   * Ademï¿½s, puede servir para separar la fragmentaciï¿½n de los archivos finalizados (sin 
   * part.met/.dat.met) de los archivos temporales (con part.met/.dat.met).
   * @param directorioDescargas directorio de ficheros a fragmentar.
   */

  /*public Fragmentador(String directorioDescargas){
    _directorio = directorioDescargas;
  }*/
  //Tal vez seria mejor crear un fragmentador para los temporales diferentes de los completos,
  //ya que de los completos siempre sepodrï¿½n servidor las partes solicitadas, en cambio para
  //un archivo temporal solo se podran servir las que tengamos indicadas en el part.met (+rapid)
  //ademas de que sin el part.met no sabemos que partes de archivo tenemos realmente, por que el
  //metodo de poner todo a 0 serï¿½ siempre fiable.

  /*Como esta completo tiene todos los fragmentos que forman el fichero*/
  /*Como el fragmento no tiene el array de bytes que lo forma tengo que devolver un par Fragmento-Byte's*/
  /*A parte del indicar el hash debe indicarme que fragmentos del fichero quiere, lo hace a travï¿½s de un Array de Fragmento?*/
  /*public ArrayList<Byte[]> getBytesDeFragmentos(String hash, ArrayList<Fragmento> fragmentosSolicitados ){
    
    if( _tipoFragmentador == Fragmentador.COMPLETO ){
      Accede directamente al archivo completo
    }else
      if( _tipoFragmentador == Fragmentador.INCOMPLETO ){
        Debo leer los .dat.met que tiene la info de que partes/Fragmento's tengo del fichero incompleto
      }else{
	Mensaje o excepcion de que dicho tipo Fragmentador no es conocido/posible
	Aunque esto no deberï¿½a darse si lo controlamos en el constructor
      }
    return null;
  }*/

  /*public ArrayList<Fragmento> queFragmentosTienes( String hash ){
    ArrayList<Fragmento> fragmentosDisponibles;

    System.out.println("El fragmentador es de tipo <"+hash+"> <"+_tipoFragmentador+">");

    if( _tipoFragmentador == Fragmentador.COMPLETO ){
      System.out.println("El fragmentador es de tipo COMPLETO");
      Accede directamente al archivo completo, si es completo tiene disponibles TODOS los Fragmentos
      Tendria que construir todos los Fragmentos por cada fichero (entonces tal vez mejor tb 
      crear archivos de indices para los completos)
      String nombre = "";
      boolean encontrado = false;
      GestorCompartidos compartidos = null;
      
      try{
        compartidos = new GestorCompartidos( new File( directorioCompletos ) );
      
    
      ListaArchivos lista = compartidos.getArchivosCompartidos();
      for( int i = 0;  i < lista.size() && encontrado == false;  i++){
        if( lista.get( i ).getHash().compareTo( hash ) == 0 ){
          encontrado = true;
          nombre = lista.get( i ).getNombre();
          Tengo que convertir el fichero en un array de Fragmentos
          File fComp = new File( directorioCompletos + "//" + nombre );
          FileInputStream fileInput = new FileInputStream( fComp );
          BufferedInputStream bufferedInput = new BufferedInputStream( fileInput );
          
          byte[] array = new byte[ 512 ];
          Byte[] parte;
    
          ArrayList<Byte[]> _partes = new ArrayList<Byte[]>();
          
          System.out.println("Ruta <" + fComp.getAbsolutePath() + ">");
          fragmentosDisponibles = new ArrayList<Fragmento>();
          int leidos = bufferedInput.read( array );
          int totalLeidos = 0;
          while( leidos > 0 ){
            parte = new Byte[ leidos ];
            for( int j = 0;  j < leidos;  j++){
              parte[j] = new Byte( array[ j ] );
            }
            _partes.add( parte );
            fragmentosDisponibles.add( new Fragmento( nombre, hash, leidos, totalLeidos-leidos ) );
            totalLeidos += leidos;
            leidos = bufferedInput.read( array );
          }
          
          bufferedInput.close();
          System.out.println("Bytes totales <" + totalLeidos + ">");
          System.out.println("El fichero tiene <" + _partes.size() + "> parte/es");
        }
      }
      }catch( Exception e ){
        e.printStackTrace();
      }

      return new ArrayList<Fragmento>();
    }else
      if( _tipoFragmentador == Fragmentador.INCOMPLETO ){
        System.out.println("El fragmentador es de tipo INCOMPLETO");
        Debo leer los .dat.met que tiene la info de que partes/Fragmento's tengo del fichero incompleto
      }else{
        System.out.println("Tipo desconocido");
	Mensaje o excepcion de que dicho tipo Fragmentador no es conocido/posible
	Aunque esto no deberï¿½a darse si lo controlamos en el constructor
      }
    return new ArrayList<Fragmento>();
  }*/

  /*public ArrayList<Byte[]> dameBytesDeLosFragmentos( String hash, ArrayList<Fragmento> fragmentosSolicitados ){
    ArrayList<Byte[]> bytesDeFragmentos;

    Debo comprobar primero que tengo esos Fragmentos antes de devlverlos
    
    En caso de no tener todos puede hacer varias cosas
		Devolver los que tengo, aunque no tenga todos
		No devolver ninguno
		Devolver los que tengo avisando de alguna manera (que manera?)
    return null;
  }*/

  /*public Byte[] dameBytesDeLosFragmentos( Fragmento fragmentoSolicitados ){
    Byte[] bytesDeFragmento;

    Debo comprobar primero que tengo esos Fragmentos antes de devlverlos
    
    En caso de no tener todos puede hacer varias cosas
		Devolver los que tengo, aunque no tenga todos
		No devolver ninguno
		Devolver los que tengo avisando de alguna manera (que manera?)
    return null;
  }*/

  /**
   * 
   * @return
   */
  /*public Byte[] getParte(){
    _partesEnviadas++;
    return _partes.get( _partesEnviadas - 1 );
  }*/

  //Tal vez deba ser yo el que genere la lista de los archivos compartidos y/o temporales

 /* public ArrayList<Fragmento> getPartesFichero( String hash ){
    return null;
  }*/

  /**
   * @param directorio
   * @param hash
   * @return
   */
  /*public String getNombreFichero(String directorio, String hash){
    String nombre = "";
    boolean encontrado = false;
    GestorCompartidos compartidos = null;
    
    try{
      compartidos = new GestorCompartidos( new File( directorio ) );
    }catch( Exception e ){
      e.printStackTrace();
    }
    
    ListaArchivos lista = compartidos.getArchivosCompartidos();
    for( int i = 0;  i < lista.size() && encontrado == false;  i++){
      if( lista.get( i ).getHash().compareTo( hash ) == 0 ){
        encontrado = true;
        nombre = lista.get( i ).getNombre();
      }
    }
    return nombre;
  }*/

  /**
   * 
   * @return
   */
  /*public boolean masPartes(){
    com No se si es _partesEnviadas-1, +1 o sin nada
    return _partes.size() != _partesEnviadas;
  }*/

  /**
   * 
   * @return
   */
  /*public int getCantidadPartes(){
    com No se si es _partesEnviadas-1, +1 o sin nada
    return _partes.size();
  }*/

}

