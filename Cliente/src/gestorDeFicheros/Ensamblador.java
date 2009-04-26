package gestorDeFicheros;

import mensajes.serverclient.*;
import datos.*;
import java.io.*;
import java.util.*;

/**
 * Guarda los fragmentos en el lugar correspondiente, ademas crea y mantiene actualizados los
 * indices de los temporales. Cuando se completo un archivo, lo manda al direc de completos
 *
 */
public class Ensamblador extends ManejarListaArchivos {

  //Este podria volver a leer los datos de las properties o que le pasa los valores el 
  //gestor de disco.
  
  //Hacer properties
  private int tamanioBytesFragmento = 512;

  private String _directorioTemporales;

  private String _directorioCompletos;

  //Lista de todos (temporales+completos)
  private ListaArchivos _listaTodos;

  //Lista de los temporales
  private ListaArchivos _listaTemporales;

  //Lista de los completos
  private ListaArchivos _listaCompletos;

  private GestorDisco _gestorDisco;


  public Ensamblador( GestorDisco gestorDisco, String directorioCompletos, String directorioTemporales){
    _directorioCompletos = directorioCompletos;
    _directorioTemporales = directorioTemporales;
    _gestorDisco = gestorDisco;

    //Obtengo sus referencias, para poder tener las listas actualizarlas
    _listaCompletos = gestorDisco.getListaArchivosCompletos();
    _listaTemporales = gestorDisco.getListaArchivosTemporales();
    _listaTodos = gestorDisco.getListaArchivosTodos();
  }

  public void reservarEspacioFicheroNuevo( File fichero, long size ){
    byte[] bytes;
    int tamBuf = 9000;
    try{
      if( fichero.createNewFile() == true ){ //crea el fichero pero con 0 bytes
      FileOutputStream ficheroIndices = new FileOutputStream( fichero );
      BufferedOutputStream bufferedOutput = new BufferedOutputStream( ficheroIndices );
      //Meto bytes aleatorios (basura) para llegar al tamamño indicado
      if( tamBuf < size ){
        bytes = new byte[ tamBuf ];
        long i;
        for( i = size;  i > tamBuf;  i-=tamBuf )
          bufferedOutput.write( bytes, 0, bytes.length );
        if( i > 0 ){
          //Si quedan todavía bytes por escribir
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
  public boolean nuevoArchivoTemporal( Archivo archivoNuevo ){
    boolean creado = false;
    Archivo archivoExistencia;

    String hashFragmento = archivoNuevo.getHash();
    //Si el hash ya existe no le vuelvo a crear
    //Como es un archivo nuevo, temporal, debo crear un nuevo archivo de indices para este 
    //archivo

    //En principio debo mirar si ya existe en la lista de temporales, pero por si acaso miraria
    //tambien en la de completos, para que no se baje un archivo que ya tiene dos veces
    //Tal vez sea interesante distinguir el que se intente crear un archivo q esta en los temp
    //o el que se intente crear cuando se encuentra en los completos.
    archivoExistencia = buscarArchivoEnLista( _listaTemporales, hashFragmento );
    if( archivoExistencia == null ){
      System.out.println( "No esta en la lista de temporales" );
      //Entonces miro tambien en los completos
      archivoExistencia = buscarArchivoEnLista( _listaCompletos, hashFragmento );
      if( archivoExistencia == null ){
        System.out.println( "No esta en la lista de completos" );
        System.out.println( "Asi que creo el fichero temporal y el indice" );
        //El archivo no se encuentra, es nuevo! Lo creo en los temporales
        File fichero = new File( _directorioTemporales+"//" + archivoNuevo.getNombre()
            + ".part.met" );
        //problema con el getNombre, puede qhaya otro con el mismo nombreee!
        crearFicheroIndices( fichero );
        //Creo el fichero con el tamaño que se me indica, pero sin tener sentido
        fichero = new File( _directorioTemporales+"//" + archivoNuevo.getNombre() +".tmp" );   
        reservarEspacioFicheroNuevo( fichero, archivoNuevo.getSize() );
        
        //Y si todo ha ido bien actualizo las listas
        System.out.println( "Actualizo las listas de los archivos" );
        includirArchivoEnLista( archivoNuevo, _listaTemporales );
        includirArchivoEnLista( archivoNuevo, _listaTodos );
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


  private void crearFicheroIndices( File fichero ){
    Indices indices = new Indices();
    try{      
      ByteArrayOutputStream bs = new ByteArrayOutputStream();
      ObjectOutputStream os = new ObjectOutputStream( bs );
      os.writeObject( indices );
      os.close();
      byte[] bytes = bs.toByteArray(); // devuelve byte[]

      FileOutputStream ficheroIndices = new FileOutputStream( fichero );
      BufferedOutputStream bufferedOutput = new BufferedOutputStream( ficheroIndices );
      bufferedOutput.write( bytes, 0, bytes.length );
      bufferedOutput.close();
      //creo que tambien hace falta cerrar el otro
      ficheroIndices.close();
    }catch( Exception e ){
      e.printStackTrace();
    }
  }

  //Fusionar el crear y el guardar, argumento en crear y se unen
  //Ademas, llevar estos a una clase del estilo TratarIndices

  private void guardarFicheroIndices( File fichero, Indices indices ){
    try{      
      ByteArrayOutputStream bs = new ByteArrayOutputStream();
      ObjectOutputStream os = new ObjectOutputStream( bs );
      os.writeObject( indices );
      os.close();
      byte[] bytes = bs.toByteArray(); // devuelve byte[]

      FileOutputStream ficheroIndices = new FileOutputStream( fichero );
      BufferedOutputStream bufferedOutput = new BufferedOutputStream( ficheroIndices );
      //Pese a existir ya el fichero, como voy añadiendo fragmentos, el file aumenta
      //por lo q sobreescribo el fichero con mas bytes y no menos, ya que si fueran menos
      //quedarian bytes malos en el file
      bufferedOutput.write( bytes, 0, bytes.length );
      bufferedOutput.close();
      //creo que tambien hace falta cerrar el otro
      ficheroIndices.close();
    }catch( Exception e ){
      e.printStackTrace();
    }
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

  private byte[] objetoAPrimitivo( Byte[] bytes ){
    byte[] pBytesFragmento = new byte[ bytes.length ];

    for( int i = 0;  i < bytes.length;  i++ ){
      pBytesFragmento[ i ] = bytes[ i ].byteValue();
    }

    return pBytesFragmento;
  }


  //Este ya debe comprobar mediante el part.met si le llegan byte[]-fragmentos duplicados o con error, etc
  public boolean guardarFragmentoEnArchivo(Fragmento fragmento, Byte[] byteArchivo){
    Archivo archivoExistencia;
    int off, len = 0;
    String hashFragmento = fragmento.getHash();

    //Tengo qcomprobar cuando completo un archivo, para moverlo a completos

    archivoExistencia = buscarArchivoEnLista( _listaTemporales, hashFragmento );
    if( archivoExistencia == null ){
      //No esta en la lista, posible Fragmento corrupto
    }else{
      //Guardo la parte donde toque
      try{
      File fichero = new File( _directorioTemporales+"//" + archivoExistencia.getNombre()+
          ".tmp" );
      RandomAccessFile punteroFichero = new RandomAccessFile( fichero, "rw" );
      /*FileOutputStream archivo = new FileOutputStream( fichero );
      BufferedOutputStream bufferedOutput = new BufferedOutputStream( archivo );*/   
      off = (int)fragmento.getOffset();
      //len = (int)(fragmento.getTama() - fragmento.getOffset() );
      //len = tamanioBytesFragmento;
      int tamanioBytesFragmentoAux = (int)(fragmento.getTama() - fragmento.getOffset() );
      if( tamanioBytesFragmentoAux < tamanioBytesFragmento )
        len = tamanioBytesFragmentoAux;
      else
        len = tamanioBytesFragmento;
      byte[] bytes = objetoAPrimitivo( byteArchivo );
      punteroFichero.seek( off );
      punteroFichero.write( bytes );
      //bufferedOutput.write( bytes, off, len );
      //bufferedOutput.close();
      //creo que tambien hace falta cerrar el otro
      //archivo.close();
      punteroFichero.close();

      //Actualizo el fichero de indices
      fichero = new File( _directorioTemporales+"//" + archivoExistencia.getNombre()
            + ".part.met" );
      Indices indices = leeFicheroIndices( fichero );
      indices.add( fragmento );
      guardarFicheroIndices( fichero, indices );

      }catch( Exception e ){
        e.printStackTrace();
      }
      //Y la cantidad de fragmentos que tengo o me faltan segun como lo este haciendo
    }
    return true;
  }

  //Realmente el ensamblador solo se crea sobre los archivos temporales, no tiene sentido sobre
  //los archivos completos, ya que no necesitan ensamblar mas partes de ellos

  /**
   * @param directorioCompartidos
   * @param hashFichero
   * @throws java.io.IOException
   */
  //public Ensamblador( String directorioCompartidos, String hashFichero ) throws IOException {
    /* Este lee _partes de un fichero existente, dirCompartidos
     * Hacer q recupere el nombre de la lista de archivos compartidos del cliente */

    //Esta ruta tiene obtenerse de un properties o del que gestione estas cosas
    //El nombre del fichero ha de ser el nombre con el que iniciamos la descarga del fichero
    /*File fComp = new File(directorioCompartidos + "\\" + getNombreFichero(directorioCompartidos, hashFichero) );
    FileInputStream fileInput = new FileInputStream( fComp );
    BufferedInputStream bufferedInput = new BufferedInputStream( fileInput );

    byte[] array = new byte[ 512 ];
    Byte[] parte;
    
    _partes = new ArrayList<Byte[]>();
    
    System.out.println("Ruta <" + fComp.getAbsolutePath() + ">");
    
    int leidos = bufferedInput.read( array );
    int totalLeidos = 0;
    while( leidos > 0 ){
      parte = new Byte[ leidos ];
      for( int i = 0;  i < leidos;  i++){
        parte[i] = new Byte( array[ i ] );
      }
      _partes.add( parte );
      totalLeidos += leidos;
      leidos = bufferedInput.read( array );
    }
    
    bufferedInput.close();
    System.out.println("Bytes totales <" + totalLeidos + ">");
    System.out.println("El fichero tiene <" + _partes.size() + "> parte/es");*/
  //}

  /*public String getNombreFichero( String directorio, String hash ){
      String nombre = "";
      boolean encontrado = false;
      GestorCompartidos compartidos = null;
      try{
      compartidos = new GestorCompartidos( new File( directorio ) );
      }catch(Exception e){}
      ListaArchivos lista = compartidos.getArchivosCompartidos();
      
      for( int i = 0;  i < lista.size() && encontrado == false;  i++ ){
        if( lista.get( i ).getHash().compareTo( hash ) == 0 ){
          encontrado = true;
          nombre = lista.get( i ).getNombre(); 
        }
      }

      return nombre;
    }*/

  /**
   * @param partesDescargadas
   * @throws java.io.IOException
   */
  /*public void setPartes( ArrayList<Byte[]> partesDescargadas ) throws IOException {
    
    Byte[] parte;
    File fDes = new File(_directorio + "\\" + _nombreFichero);
    FileOutputStream fileOutput = new FileOutputStream( fDes );
    BufferedOutputStream bufferedOutput = new BufferedOutputStream( fileOutput );
    
    System.out.println("Ruta <" + fDes.getAbsolutePath() + ">");
    
    for( int i = 0;  i < partesDescargadas.size();  i++){
      com bufferedOutput.write( partesDescargadas.get( i ) );
      parte = partesDescargadas.get(i);
      com System.out.println("Cacho numero <"+ i +"> - parte "+parte+ "- bytes del parte <"+parte.length+">");

      for( int j = 0;  j < parte.length;  j++){
        com System.out.println("Byte numero <"+ j+ ">");
        bufferedOutput.write( parte[ j ].byteValue() );
      }
    }
    bufferedOutput.close();
    coment Comprobar q coinciden los MD5
  }*/  
  

  public boolean guardarFragmentosEnArchivo(String hash, ArrayList <Fragmento> fragmentosArchivoSolicitado, 
      ArrayList <Byte[]> bytesArchivoSolicitado){
    //Debo comprobar primero que el hash original le tengo el archivo de indices (part.met)
    //Debo comprobar que hay el mismo numero de fragmentos que el de trozos de bytes
    //Y en caso de coincidir, antes de grabar dichos bytes tengo que hacer el MD5 de esos Byte[] y ver que coincide con el
    //que lleva el Fragmento
    return false;
  }

    public boolean guardarFragmentosEnArchivo(String hash, ArrayList <Fragmento> fragmentosArchivoSolicitado, 
        Byte[] byteArchivoSolicitado){
    //Debo comprobar primero que el hash original le tengo el archivo de indices (part.met)
    //Debo comprobar que hay el mismo numero de fragmentos que el de trozos de bytes
    //Y en caso de coincidir, antes de grabar dichos bytes tengo que hacer el MD5 de esos Byte[] y ver que coincide con el
    //que lleva el Fragmento
    return false;
    }

    /*public boolean guardarFragmentoEnArchivo(Fragmento fragmentoArchivoSolicitado, Byte[] byteArchivoSolicitado) throws FileNotFoundException{*/
      //Debe comprobar si esta completo, hash original con el hash una vez grabado cada bytes del Fragmento
      //Entonces movera el archivo a incoming
      /*Byte[] parte;
      File fDes = new File(directorioIncompletos + "//" + fragmentoArchivoSolicitado.getNombre());
      FileOutputStream fileOutput = new FileOutputStream( fDes );
      BufferedOutputStream bufferedOutput = new BufferedOutputStream( fileOutput );
    
      System.out.println("Ruta <" + fDes.getAbsolutePath() + ">");*/

      //Esto ya no vale, ahora hay que buscar el lugar del los bytes mediante el offset del fragmento
      
      /*for( int i = 0;  i < partesDescargadas.size();  i++){
        com bufferedOutput.write( partesDescargadas.get( i ) );
        parte = partesDescargadas.get(i);
        com System.out.println("Cacho numero <"+ i +"> - parte "+parte+ "- bytes del parte <"+parte.length+">");
        
        for( int j = 0;  j < parte.length;  j++){
          com System.out.println("Byte numero <"+ j+ ">");
          bufferedOutput.write( parte[ j ].byteValue() );
        }
      }
      
      bufferedOutput.close();*/
      //coment Comprobar q coinciden los MD5
      /*return true;
    }*/
}

