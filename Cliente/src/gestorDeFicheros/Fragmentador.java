package gestorDeFicheros;

import datos.*;
import java.io.*;
import java.util.*;

/**
 * Fragmenta los archivos de los directorios especificados para los temporales y los completos.
 *
 */
//Es mas optimo diferencias el fragmentador de completos e incompletos, ya que preguntar por una variable es mas rapido que buscar en que lista esta el fichero.
public class Fragmentador {

  //Este podria volver a leer los datos de las properties o que le pasa los valores el 
  //gestor de disco.

  private String _directorioTemporales;

  private String _directorioCompletos;

  /**
   */
  public Fragmentador( String directorioCompletos, String directorioTemporales){
    _directorioCompletos = directorioCompletos;
    _directorioTemporales = directorioTemporales;
  }

  public Byte[] dameBytesDelFragmento( Fragmento fragmentoSolicitados ){
    Byte[] bytesDeFragmento;

    //Debo comprobar primero que tengo esos Fragmentos antes de devlverlos
    
    //En caso de no tener todos puede hacer varias cosas
		//Devolver los que tengo, aunque no tenga todos
		//No devolver ninguno
		//Devolver los que tengo avisando de alguna manera (que manera?)
    return null;
  }

  public Vector<Fragmento> queFragmentosTienes( String hash ){
    return null;
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
   * directorio especificado. Esto es �til cuando por ejemplo queremos tener fragmentados
   * todos los ficheros de un directorio. Adem�s, puede servir para separar la fragmentaci�n
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
   Habr�a que comprobar que el tipo indicado es posible
  }*/

  /**
   * Crea un fragmentador sobre cada uno de los ficheros del directorio especificado. Esto es 
   * �til cuando por ejemplo queremos tener fragmentados todos los ficheros de un directorio. 
   * Adem�s, puede servir para separar la fragmentaci�n de los archivos finalizados (sin 
   * part.met/.dat.met) de los archivos temporales (con part.met/.dat.met).
   * @param directorioDescargas directorio de ficheros a fragmentar.
   */

  /*public Fragmentador(String directorioDescargas){
    _directorio = directorioDescargas;
  }*/
  //Tal vez seria mejor crear un fragmentador para los temporales diferentes de los completos,
  //ya que de los completos siempre sepodr�n servidor las partes solicitadas, en cambio para
  //un archivo temporal solo se podran servir las que tengamos indicadas en el part.met (+rapid)
  //ademas de que sin el part.met no sabemos que partes de archivo tenemos realmente, por que el
  //metodo de poner todo a 0 ser� siempre fiable.

  /*Como esta completo tiene todos los fragmentos que forman el fichero*/
  /*Como el fragmento no tiene el array de bytes que lo forma tengo que devolver un par Fragmento-Byte's*/
  /*A parte del indicar el hash debe indicarme que fragmentos del fichero quiere, lo hace a trav�s de un Array de Fragmento?*/
  /*public ArrayList<Byte[]> getBytesDeFragmentos(String hash, ArrayList<Fragmento> fragmentosSolicitados ){
    
    if( _tipoFragmentador == Fragmentador.COMPLETO ){
      Accede directamente al archivo completo
    }else
      if( _tipoFragmentador == Fragmentador.INCOMPLETO ){
        Debo leer los .dat.met que tiene la info de que partes/Fragmento's tengo del fichero incompleto
      }else{
	Mensaje o excepcion de que dicho tipo Fragmentador no es conocido/posible
	Aunque esto no deber�a darse si lo controlamos en el constructor
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
	Aunque esto no deber�a darse si lo controlamos en el constructor
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

