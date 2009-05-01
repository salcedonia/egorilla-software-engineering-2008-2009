/**
 * Paquete donde tenemos las clases que tratan con el disco.
 */
package gestorDeFicheros;

import mensajes.serverclient.*;
import datos.*;
import java.io.*;
import java.util.*;

/**
 * Guarda los fragmentos en el lugar correspondiente, ademas crea y mantiene actualizados los
 * indices de los temporales. Cuando se completo un archivo, lo manda al direc de completos
 *
 * @author Ivan Munsuri Ibanez
 */
public class Ensamblador{

  /**
   * Es la extesion que acompana a los archivos de indices.
   */
  private String _extesionIndices;

  /**
   * Es la extesion que acompana a los archivos temporales.
   */
  private String _extesionFicheroTemporal;

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
   * Es el propio gestor de disco que creado a dicho Ensamblador.
   */
  private GestorDisco _gestorDisco;


  /**
   * Constructor que recibe una instancia del disco para obtener la informacion necesaria para
   * el ensamblado de los archivos del usuario, y mantener actualizada dicha informacion en 
   * funcion de los nuevos archivos o partes de los mismos que se han ido recibiendo.
   * @param gestorDisco es la instancia del disco de donde obtengo y mantengo la informacion 
   *                    referente a los archivos.
   */
  public Ensamblador( GestorDisco gestorDisco){
    _directorioCompletos = gestorDisco.getDirectorioCompletos();
    _directorioTemporales = gestorDisco.getDirectorioTemporales();
    _gestorDisco = gestorDisco;

    _extesionIndices = gestorDisco.getExtesionIndices();
    _extesionFicheroTemporal = gestorDisco.getExtesionFicheroTemporal();
    _tamanioBytesFragmento = gestorDisco.getTamanioBytesFragmento();
  }

  /**
   * Reserva una cantidad de espacio, indicada por parámetro, requerido para un fichero 
   * concreto. El contenido de este fichero es aleatorio y sera ira completando adecuadamente a
   * medida que obtengamos los byte's de cada fragmento del fichero original.
   * @param fichero indica la ruta y nombre del fichero nuevo que sera creado.
   * @param size cantidad de bytes que seran reservados en el disco para el fichero.
   */
  public void reservarEspacioFicheroNuevo( File fichero, long size ){
    byte[] bytes;
    int tamBuf = 9000;
    try{
      if( fichero.createNewFile() == true ){ //crea el fichero pero con 0 bytes
      FileOutputStream ficheroIndices = new FileOutputStream( fichero );
      BufferedOutputStream bufferedOutput = new BufferedOutputStream( ficheroIndices );
      //Meto bytes aleatorios (basura) para llegar al tamamno indicado
      if( tamBuf < size ){
        bytes = new byte[ tamBuf ];
        long i;
        for( i = size;  i > tamBuf;  i-=tamBuf )
          bufferedOutput.write( bytes, 0, bytes.length );
        if( i > 0 ){
          //Si quedan todavia bytes por escribir
          bytes = new byte[ (int)i ];
          bufferedOutput.write( bytes, 0, bytes.length );
        }
      }else{
        //Si es menor que 9000, pues grabo de un golpe todos los q sean
        bytes = new byte[ (int)size ];
        bufferedOutput.write( bytes, 0, bytes.length );
      }
      bufferedOutput.close();
      //creo que tambien hace falta cerrar el otro
      ficheroIndices.close();
      }else{
        System.out.println( "Problemas al crear el archivo <"+fichero.getName()+">" );
      }
    }catch( Exception e ){
      e.printStackTrace();
    }
  }

  //****Se debe actualizar las listas cada vez que se pone a bajar un nuevo archivo o cuando se
  //coloca un archivo nuevo a compartir y se "pincha en recargar"
  /**
   * Genera los archivos temporales, tanto el de indices como el temporal correspondiente, 
   * creando el indice por defecto y el fichero temporal con el tamano concreto. El fichero se 
   * dara de alta solo si no se encuentra actualmente en los completos o en los temporales.
   * @param archivoNuevo es un objeto que representa el nuevo fichero que vamos a dar de alta.
   * @return Devuelve un booleano que indica si se ha creado correctamente el nuevo temporal. 
   */
  public synchronized boolean nuevoArchivoTemporal( Archivo archivoNuevo ){
    boolean creado = false;
    Archivo archivoExistencia;
    ManejarIndices manejarIndices = _gestorDisco.getManejarIndices();
    ManejarListaArchivos manejarListaArchivos = _gestorDisco.getManejarListaArchivos();
    ListaArchivos listaCompletos = _gestorDisco.getListaArchivosCompletos();
    ListaArchivos listaTemporales = _gestorDisco.getListaArchivosTemporales();
    ListaArchivos listaTodos = _gestorDisco.getListaArchivosTodos();

    String hashFragmento = archivoNuevo.getHash();
    //Si el hash ya existe no le vuelvo a crear
    //Como es un archivo nuevo, temporal, debo crear un nuevo archivo de indices para este 
    //archivo

    //En principio debo mirar si ya existe en la lista de temporales, pero por si acaso miraria
    //tambien en la de completos, para que no se baje un archivo que ya tiene dos veces
    //Tal vez sea interesante distinguir el que se intente crear un archivo q esta en los temp
    //o el que se intente crear cuando se encuentra en los completos.
    archivoExistencia = manejarListaArchivos.buscarArchivoEnLista( listaTemporales, 
        hashFragmento );
    if( archivoExistencia == null ){
      System.out.println( "No esta en la lista de temporales" );
      //Entonces miro tambien en los completos
      archivoExistencia = manejarListaArchivos.buscarArchivoEnLista( listaCompletos, 
          hashFragmento );
      if( archivoExistencia == null ){
        System.out.println( "No esta en la lista de completos" );
        System.out.println( "Asi que creo el fichero temporal y el indice" );
        //El archivo no se encuentra, es nuevo! Lo creo en los temporales
        File fichero = new File( _directorioTemporales+"//" + archivoNuevo.getNombre()
            + _extesionIndices );
        //problema con el getNombre, puede qhaya otro con el mismo nombreee!
        manejarIndices.crearFicheroIndices( fichero, archivoNuevo, 
            fragmentosArchivoNuevo( archivoNuevo ) );
        //Creo el fichero con el tamano que se me indica, pero sin tener sentido
        fichero = new File( _directorioTemporales+"//" + archivoNuevo.getNombre() + 
            _extesionFicheroTemporal  );
        reservarEspacioFicheroNuevo( fichero, archivoNuevo.getSize() );
        
        //Y si todo ha ido bien actualizo las listas
        System.out.println( "Actualizo las listas de los archivos" );
        manejarListaArchivos.includirArchivoEnLista( archivoNuevo, listaTemporales );
        manejarListaArchivos.includirArchivoEnLista( archivoNuevo, listaTodos );
        creado = true;
      }else{
        System.out.println( "Esta en los archivos completos, ya ha sido descargado" );
        //Esta en los archivos completos, ya ha sido descargado.
      }
    }else{
      System.out.println( "Ya se encuentra en los temporales, actualmente en descarga" );
      //Ya se encuentra en los temporales, actualmente en descarga.
    }
    //Se puede dejar en un simple if-else sino me interesa diferenciar esos dos problemas
    return creado;
  }

  /**
   * Elimina los archivos temporales, tanto el de indices del fichero, identificado por su hash,
   * como el archivo temporal incompleto.
   * @param hash cadena que representa el valor de MD5 del fichero.
   * @return Devuelve un booleano que indica si se ha eliminado correctamente el fichero 
   *         temporal y el de indices correspondientes.
   */
  public boolean eliminarArchivoTemporal( String hash ){
    boolean eliminado = false;
    Archivo archivoExistencia;
    ManejarListaArchivos manejarListaArchivos = _gestorDisco.getManejarListaArchivos();
    ListaArchivos listaTemporales = _gestorDisco.getListaArchivosTemporales();
    ListaArchivos listaTodos = _gestorDisco.getListaArchivosTodos();

    archivoExistencia = manejarListaArchivos.buscarArchivoEnLista( listaTemporales, hash );
    if( archivoExistencia == null ){
      System.out.println("No se puede eliminar un archivo que no existe");
      eliminado = false;
    }else{
      File ficheroIndices = new File( _directorioTemporales+"//" + 
          archivoExistencia.getNombre() + _extesionIndices );

      File ficheroTemporal = new File( _directorioTemporales+"//" + 
          archivoExistencia.getNombre() + _extesionFicheroTemporal  );

      //Puede qsten en uso cuando los quiera borrar
      eliminado = ficheroIndices.delete() && ficheroTemporal.delete();
      if( eliminado == false )
        System.out.println("Problemas al eliminar el archivo temporal y/o de indices("+
            archivoExistencia.getNombre()+")");

      //comprobar que deja la lista actualizada
      manejarListaArchivos.eliminarArchivoDeLista( archivoExistencia, listaTemporales );
      manejarListaArchivos.eliminarArchivoDeLista( archivoExistencia, listaTodos );
    }
    return eliminado;
  }

  /**
   * Crea automaticamente todos los fragmentos que tendria un fichero nuevo, en funcion del 
   * tamano maximo del fragmento y el tamano del fichero nuevo.
   * @param archivo representa al archivo nuevo de cual voy a generar los fragmentos que 
   *                necesito.
   * @return Devuelve todos los fragmentos de los que se compone el archivo nuevo.
   */
  public Vector<Fragmento> fragmentosArchivoNuevo( Archivo archivo ){
    Vector<Fragmento> listaFragmento = new Vector<Fragmento>();
   
    Fragmento fragmento = new Fragmento( archivo.getNombre(), 
          archivo.getHash(), archivo.getSize(), 0 ); //0 por ser el primero
      listaFragmento.add( fragmento );
      for( int i = 1; fragmento.getOffset()+_tamanioBytesFragmento < fragmento.getTama(); i++ ){
        fragmento = new Fragmento( archivo.getNombre(),archivo.getHash(), 
            archivo.getSize(), i*_tamanioBytesFragmento );
        listaFragmento.add( fragmento );
      }
      return listaFragmento;
  }



  /**
   * Metodo auxiliar que convierto un array de objetos de Byte a un array de tipos primitivos 
   * byte.
   * @param bytes es un array de objetos del tipo Byte.
   * @return Devuelve un array de tipos primitivos byte.
   */
  private byte[] objetoAPrimitivo( Byte[] bytes ){
    byte[] pBytesFragmento = new byte[ bytes.length ];

    for( int i = 0;  i < bytes.length;  i++ ){
      pBytesFragmento[ i ] = bytes[ i ].byteValue();
    }

    return pBytesFragmento;
  }


  //Este ya debe comprobar mediante el part.met si le llegan byte[]-fragmentos duplicados o con
  //error, etc
  /**
   * Guarda el array de byte de la parte del archivo que nos pasan, en el archivo y lugar 
   * correspondiente indicado por el fragmento. En caso de error 
   * @param fragmento es el que indica de que archivo es el fragmento y donde se colocara el 
   * array de bytes.
   * @param byteArchivo es el array de objetos de byte que lleva una parte de la informacion del
   *                    fichero.
   * return Devuelve un booleano en funcion de si se ha guardado el fragmento o no.
   */
  public synchronized boolean guardarFragmentoEnArchivo(Fragmento fragmento, Byte[] byteArchivo)
  {
    boolean guardado = false;
    Archivo archivoExistencia;
    int off, len = 0;
    File fichero = null;
    String hashFragmento = fragmento.getHash();
    ManejarIndices manejarIndices = _gestorDisco.getManejarIndices();
    ManejarListaArchivos manejarListaArchivos = _gestorDisco.getManejarListaArchivos();
    ListaArchivos listaCompletos = _gestorDisco.getListaArchivosCompletos();
    ListaArchivos listaTemporales = _gestorDisco.getListaArchivosTemporales();

    //Tengo qcomprobar cuando completo un archivo, para moverlo a completos

    archivoExistencia = manejarListaArchivos.buscarArchivoEnLista( listaTemporales, 
        hashFragmento );
    if( archivoExistencia == null ){
      //No esta en la lista, posible Fragmento corrupto
      System.out.println( "No esta en la lista temporales, posiblemente fragmento corrupto o"+
          "tardio (ver completos)" );
      //guardado = false;
    }else{
      //Guardo la parte donde toque
      try{
        System.out.println( "Esta en los temporales, guardamos par Fragmento-Byte's" );
      fichero = new File( _directorioTemporales+"//" + archivoExistencia.getNombre()+
          _extesionFicheroTemporal );
      RandomAccessFile punteroFichero = new RandomAccessFile( fichero, "rw" );
      /*FileOutputStream archivo = new FileOutputStream( fichero );
      BufferedOutputStream bufferedOutput = new BufferedOutputStream( archivo );*/   
      off = (int)fragmento.getOffset();
      //len = (int)(fragmento.getTama() - fragmento.getOffset() );
      //len = tamanioBytesFragmento;
      int tamanioBytesFragmentoAux = (int)(fragmento.getTama() - fragmento.getOffset() );
      if( tamanioBytesFragmentoAux < _tamanioBytesFragmento )
        len = tamanioBytesFragmentoAux;
      else
        len = _tamanioBytesFragmento;
      byte[] bytes = objetoAPrimitivo( byteArchivo );
      punteroFichero.seek( off );
      punteroFichero.write( bytes );
      guardado = true;
      //bufferedOutput.write( bytes, off, len );
      //bufferedOutput.close();
      //creo que tambien hace falta cerrar el otro
      //archivo.close();
      punteroFichero.close();
      }catch( Exception e ){
        e.printStackTrace();
        return false;
      }

      //Actualizo el fichero de indices
      File ficheroIndices = new File( _directorioTemporales+"//" + archivoExistencia.getNombre()
            + _extesionIndices );
      Indices indices = manejarIndices.leeFicheroIndices( ficheroIndices );
      indices.addTengo( fragmento );
      manejarIndices.guardarFicheroIndices( ficheroIndices, indices );

      if( indices.getIndicesFaltan().size() == 0 ){
        System.out.println( "Acabo de completar el fichero, lo muevo a los completos" );
        manejarListaArchivos.eliminarArchivoDeLista( indices.getArchivo(), listaTemporales );
        manejarListaArchivos.includirArchivoEnLista( indices.getArchivo(), listaCompletos );

        File ficheroCompleto = new File( _directorioCompletos+"//"+
            indices.getArchivo().getNombre() );
        mover( fichero, ficheroCompleto );
        
        if( manejarIndices.borrarFicheroIndices( ficheroIndices ) == false )
          System.out.println( "Problemas al borrar el archivo de indices" );      

        /*_gestorDisco.recorrerListaArchivos( _listaTemporales );
        _gestorDisco.recorrerListaArchivos( _listaCompletos );*/
        //Y no la incluyo en listaTodos yaq tiene que estar
        //Tendre que eliminar el archivo de indices o algo asi        
      }      
      
      //Y la cantidad de fragmentos que tengo o me faltan segun como lo este haciendo
    }
    return guardado;
  }

  /**
   * Mueve un fichero de una ruta a otra, o de la misma ruta a un nombre diferente sin necesidad
   * de tener que copiar el archivo. En caso de no poder mover el fichero se intentara copiar, 
   * borrando posteriormente el fichero que no se pudo mover previamente. 
   * @param source representa el fichero que queremos mover.
   * @param destination representa el fichero donde queremos mover el fichero origen.
   * @return Devuelve un booleano en funcion de si puedo mover o no el fichero, o en caso de no 
   *         poder mover si pueod copiar o no el fichero.
   */
  public boolean mover(File source, File destination) {
    if( !destination.exists() ) {
      // intentamos con renameTo
      boolean result = source.renameTo(destination);
      if( result == false ) {
        System.out.println("No se ha podido mover el temporal, copiando...");
        // intentamos copiar
        result = true;
        result &= copiar(source, destination);
        result &= source.delete();
      }
      return result;
    } else {
      // Si el fichero destination existe, cancelamos...
      return false;
    } 
  }

  /**
   * Copia el fichero origen en un fichero destino, en caso de no ocurrir problemas.
   * @param source representa el fichero que queremos copiar.
   * @param destination representa el fichero donde queremos copiar el fichero origen.
   * @return Devuelve un booleano en funcion de si puedo copiar o no el fichero.
   */
  public boolean copiar( File source, File destination ){
    boolean resultado = false;
    // declaracion del flujo
    FileInputStream sourceFile = null;
    FileOutputStream destinationFile = null;
    try {
      // creamos fichero
      if( destination.createNewFile() == true ){
      // abrimos flujo
      sourceFile = new FileInputStream(source);
      destinationFile = new FileOutputStream(destination);
      // lectura por segmentos de 0.5Mb
      //byte buffer[]=new byte[512*1024];
      byte buffer[]=new byte[9000];
      int nbLectura;
      while( (nbLectura = sourceFile.read(buffer)) != -1 ) {
        destinationFile.write(buffer, 0, nbLectura);
      } 
      // copia correcta
      resultado = true;
      }
    } catch( FileNotFoundException f ) {
        f.printStackTrace();
    } catch( IOException e ) {
        e.printStackTrace();
    } finally {
      // pase lo que pase, cerramos flujo
      try {
        sourceFile.close();
      } catch(Exception e) {
          e.printStackTrace();
      }
      try {
        destinationFile.close();
      } catch(Exception e) {
          e.printStackTrace();
      }
    } 
    return resultado;
  }
}

