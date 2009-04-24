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
public class Ensamblador {

  //Este podria volver a leer los datos de las properties o que le pasa los valores el 
  //gestor de disco.

  private String _directorioTemporales;

  private String _directorioCompletos;


  public boolean nuevoArchivoTemporal( Archivo archivoNuevo ){
    //Si el hash ya existe no le vuelvo a crear
    //Como es un archivo nuevo, temporal, debo crear un nuevo archivo de indices para este archivo
    return true;
  }

  //****Se debe actualizar las listas cada vez que se pone a bajar un nuevo archivo o cuando se
  //coloca un archivo nuevo a compartir y se "pincha en recargar"


  public Ensamblador(  GestorDisco gestorDisco, String directorioCompletos, String directorioTemporales){
    _directorioCompletos = directorioCompletos;
    _directorioTemporales = directorioTemporales;
  }

  //Este ya debe comprobar mediante el part.met si le llegan byte[]-fragmentos duplicados o con error, etc
  public boolean guardarFragmentoEnArchivo(Fragmento fragmento, Byte[] byteArchivoSolicitado){
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

