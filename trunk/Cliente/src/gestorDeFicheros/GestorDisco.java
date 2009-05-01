/**
 * Paquete donde tenemos las clases que tratan con el disco.
 */
package gestorDeFicheros;

import java.io.*;
import java.util.Properties;
import mensajes.serverclient.*;
import datos.*;
import gestorDeConfiguracion.*;
import java.util.Enumeration;


/**
 * Esta clase se encarga de hacer un listado previo de todos los archivos completos y temporales
 * que tiene el usuario en el momento de arrancar la aplicacion. Ademas, se encarga de tener 
 * todo la informacion previa necesaria para el Ensamblador y Fragmentador de archivos.
 * 
 * Esta clase utiliza parametros de configuracion por tanto va a ser observadora de 
 * ControlConfiguracionCliente y sera notificada cuando cambie la configuracion dando un 
 * tratamiento adecuado al cambio (o no hacer nada).
 *
 * @author Ivan Munsuri Ibanez
 */
public class GestorDisco  implements ObservadorControlConfiguracionCliente {

  
  //Sacar valores de properties
  //Puede ser un array de directorio temporales
  /**
   * Es la ruta relativa del directorio de los ficheros temporales.
   */
  private String _directorioTemporales;

  //Puede ser un array de directorio completos
  /**
   * Es la ruta relativa del directorio de los ficheros completos.
   */
  private String _directorioCompletos;

  /**
   * Es la extesion que acompana a los archivos de indices.
   */
  private String _extesionIndices;

  /**
   * Es la extesion que acompana a los archivos temporales.
   */
  private String _extesionFicheroTemporal;

  /**
   * Es el tamano de bytes maximo que puede tener un Fragmento.
   */
  private int _tamanioBytesFragmento;

  //Lista de todos (temporales+completos)
  /**
   * Es la lista de todos los archivos que tiene el usuario.
   */
  private ListaArchivos _listaTodos;

  //Lista de los temporales
  /**
   * Es la lista de todos los archivos temporales que tiene el usuario.
   */
  private ListaArchivos _listaTemporales;

  //Lista de los completos
  /**
   * Es la lista de todos los archivos completos que tiene el usuario.
   */
  private ListaArchivos _listaCompletos;

  //TODO: Convertir esta clase en dinamica, leer la extensiones de un fichero
  //vease un properties
  //No y si, si el usuario toca esto se puede descojonar, haciendo fijo todos usuran lo mismo
  private TipoArchivo _tipo;

  /**
   * Es el Ensamblador que se utilizara para tratar los archivos de indices/temporales que tiene
   * el usuario.
   */
  private Ensamblador _ensamblador;

  /**
   * Es el Fragmentador que se utilizara para obtener partes de los ficheros completos y 
   * temporales del usuario.
   */
  private Fragmentador _fragmentador;

  /**
   * Es la clase que contiene la funcionalidad para tratar adecuadamente los archivos de 
   * indices.
   */
  private ManejarIndices _manejarIndices;

  /**
   * Es la clase que contiene la funcionalidad para tratar adecuadamente las listas de archivos.
   */
  private ManejarListaArchivos _manejarListaArchivos;


  /**
   * Constructor por defecto del Gestor de Disco, el cual realiza todas una serie de operaciones   * iniciales sobre el disco.
   */
  public GestorDisco(){  

    _listaTodos = new ListaArchivos();
    _listaTemporales = new ListaArchivos();
    _listaCompletos = new ListaArchivos();

    _manejarIndices = new ManejarIndices();
    _manejarListaArchivos = new ManejarListaArchivos();

    //Inicializo la clase que contiene el tipo de extensiones que reconocemos
    TipoArchivo.iniciarTiposArchivo();

    //Hacer properties - Finalmente no, xq puede dar problemas si el usuario es un poco bruto,
    //es decir, si las cambia habiendo algun archivo temporal con estas extensiones se perderan
    _extesionIndices = ".part.met";
    _extesionFicheroTemporal = ".tmp";

    //Esto en principio tampoco va como properties, xq "creo" qademas de los problemas 
    //anteriores puede haber problemas si el resto de clientes no usan el mismo tamano, yaq 
    //variara el numero de fragmentos de un mismo fichero
    _tamanioBytesFragmento = 512;
    
    try{
    ControlConfiguracionCliente config = ControlConfiguracionCliente.obtenerInstancia();
    _directorioTemporales = config.obtenerPropiedad(PropiedadCliente.DIR_LLEGADA.obtenerLiteral());
    _directorioCompletos = config.obtenerPropiedad(PropiedadCliente.DIR_COMPARTIDOS.obtenerLiteral());
    }catch( Exception e ){
      System.out.println( "No se encuentra el fichero de configuracion. Estableciendo"+
          "valores por defecto." );
      _directorioTemporales = "temp";
      _directorioCompletos = "compartidos";
    }

    File fDirectorioTemporales = new File( _directorioTemporales ), 
         fDirectorioCompletos = new File( _directorioCompletos );

    //Cuando haya multiples directorio, hacer un for para comprobar todos

    if( fDirectorioTemporales.isDirectory() == false ||
        fDirectorioCompletos.isDirectory() == false) {
      System.out.println("Directorio de temporales o de completos no valido");
      //throw new IOException("Algun directorio no es un directorio valido");
    }

    System.out.println("\nDirectorio de temporales: ");
    System.out.println( fDirectorioTemporales.getAbsolutePath() + "\n");

    System.out.println("\nDirectorio de completos: ");
    System.out.println( fDirectorioCompletos.getAbsolutePath() + "\n");
    
    listarArchivosTemporalesIniciales( fDirectorioTemporales );

    listarArchivosCompletosIniciales( fDirectorioCompletos );    

    //Creo una nueva lista con todos los ficheros actuales
    _listaTodos = getManejarListaArchivos().unirListas( _listaTemporales, _listaCompletos );
    //Cuando haya varios directorio por cada una se hara la union , usando un for
    //incluso, como son atributos de clase no hace falta pasarlos como parametros
    
    _fragmentador = new Fragmentador( this );
    _ensamblador = new Ensamblador( this );
  }

  /**
   * .
   */
  private void listarArchivosTemporalesIniciales( File fDirectorioTemporales ){
    //Debo filtrar los ficheros y leer solo los .part.met
    File[] ficherosTemporales = fDirectorioTemporales.listFiles( new IndicesFileFilter( 
          getExtesionIndices() ) );
    //si el length() es 0 digo que el dir esta vacio
    if( ficherosTemporales.length > 0 ){
      System.out.println( "Procesando archivos de indices..." );
      //TODO No se si hacerlo aqui o llamar al ensamblador para que haga
      //la lista.
      //Debo recorrer los archivos de indices y no los archivos temporales
      for( File f : ficherosTemporales){
        if( f.isFile() == true ) { //Creo que no hace falta esta comprobacion
          _listaTemporales.add( procesarArchivoIndices( f ) );
        }//Sino es fichero no le aniado
      }
      getManejarListaArchivos().recorrerListaArchivos( _listaTemporales );
    }else{
      System.out.println( "Directorio de temporales vacio.\n" );
    }
  }

  /**
   * .
   */
  private void listarArchivosCompletosIniciales( File fDirectorioCompletos ){
    File[] ficherosCompletos = fDirectorioCompletos.listFiles();

    if( ficherosCompletos.length > 0 ){
      System.out.println( "Procesando archivos completos..." );
      for( File f : ficherosCompletos){
        if( f.isFile() == true ) {
          _listaCompletos.add( procesarArchivoCompartido( f ) );
        }//Sino es fichero no le aniado
      }
      getManejarListaArchivos().recorrerListaArchivos( _listaCompletos );
    }else{
      System.out.println( "Directorio de compartidos vacio.\n" );
    }
  }

  /**
   * Obtiene la ruta relativa del directorio de los ficheros temporales.
   * @return Devuelve la cadena que representa dicha ruta.
   */
  public String getDirectorioTemporales(){
    return _directorioTemporales;
  }

  /**
   * Obtiene la ruta relativa del directorio de los ficheros completos.
   * @return Devuelve la cadena que representa dicha ruta.
   */
  public String getDirectorioCompletos(){
    return _directorioCompletos;
  }

  /**
   * Obtiene la extension actual de los archivos de indices.
   * @return Devuelve la cadena que representa a la extension de los indices.
   */
  public String getExtesionIndices(){
    return _extesionIndices;
  }

  /**
   * Obtiene la extension actual de los archivos de temporales.
   * @return Devuelve la cadena que representa a la extension de los temporales.
   */
  public String getExtesionFicheroTemporal(){
    return _extesionFicheroTemporal;
  }

  /**
   * Obtiene el tamano maximo que puede tener un Fragmento.
   * @return Devuelve la cantidad maxima de bytes que tendra un Fragmento.
   */
  public int getTamanioBytesFragmento(){
    return _tamanioBytesFragmento;
  }

  /**
   * Obtiene la lista de los archivos temporales que tiene el usuario.
   * @return Devuelve la lista de los archivos temporales.
   */
  public ListaArchivos getListaArchivosTemporales(){
    return _listaTemporales;
  }

  /**
   * Obtiene la lista de los archivos completos que tiene el usuario.
   * @return Devuelve la lista de los archivos completos.
   */
  public ListaArchivos getListaArchivosCompletos(){
    return _listaCompletos;
  }

  /**
   * Obtiene la lista de todos los archivos que tiene el usuario, tanto los completos como los
   * incompletos.
   * @return Devuelve la lista de todos los archivos.
   */
  public ListaArchivos getListaArchivosTodos(){
    return _listaTodos;
  }

  /*
  public void setListaArchivosTemporales( ListaArchivos listaTemporales ){
    _listaTemporales = listaTemporales;
  }

  public void setListaArchivosCompletos( ListaArchivos listaCompletos){
    _listaCompletos = listaCompletos;
  }

  public void setListaArchivosTodos( ListaArchivos listaTodos ){
    _listaTodos = listaTodos;
  }*/

  /**
   * Obtiene la instancia adecuada para la fragmentacion de todos los archivos del usuario.
   * @return Devuelve la instancia del Fragmentador.
   */
  public Fragmentador getFragmentador(){
    return _fragmentador;
  }

  /**
   * Obtiene la instancia adecuada para el ensamblado de los archivos temporales del usuario.
   * @return Devuelve la instancia del Ensamblador.
   */
  public Ensamblador getEnsamblador(){
    return _ensamblador;
  }

  /**
   * Obtiene la instancia adecuada que ofrece la funcionalidad para tratar adecuadamente los 
   * archivos de indices.
   * @return Devuelve la instancia del manejador de indices.
   */
  public ManejarIndices getManejarIndices(){
    return _manejarIndices;
  }

  public ManejarListaArchivos getManejarListaArchivos(){
    return _manejarListaArchivos;
  }

  public Archivo procesarArchivoCompartido(File f) /*throws IOException*/ {

        String nombre = null;

        nombre = f.getName();
        String[] extensiones = nombre.split("\\.");
        //Cambiar de sitio este crear, ineficiente-static-no_poo
        //Movido al constructor:   TipoArchivo.iniciarTiposArchivo();
        if (extensiones.length != 0) {
            _tipo = TipoArchivo.devuelveTipo(extensiones[extensiones.length - 1].toLowerCase());
        } else {
            _tipo = TipoArchivo.OTRO;        //Nombre-Hash-Tamano-Tipo
        //tengo que cerrar f?
        //f.close();
        }
        return new Archivo(nombre, MD5Sum.getFileMD5Sum(f), f.length(), _tipo);
    }

  
  public Archivo procesarArchivoIndices(File f) /*throws IOException*/ {
      Indices indices = getManejarIndices().leeFicheroIndices( f );
      return indices.getArchivo();
    }

    /**
     * Este metodo es llamado cuando cambia la configuracion del cliente.
     * @param obj Objeto ControlConfiguracionCliente.
     * @param propiedades Conjunto de propiedades que ha cambiado.
     */
    @Override
    public void cambioEnPropiedades(ControlConfiguracionCliente obj, Properties propiedades) {
        String sNuevoValor;
        for (Enumeration e = propiedades.propertyNames(); e.hasMoreElements() ; ) {
            // Obtenemos el objeto
            Object objeto = e.nextElement();
            if (objeto.toString().compareTo (PropiedadCliente.DIR_LLEGADA.obtenerLiteral()) == 0){
                sNuevoValor = propiedades.getProperty(objeto.toString());
                //TODO: Lo que se vaya a hacer cuando cambia de valor esta propiedad
            } else if (objeto.toString().compareTo (PropiedadCliente.DIR_COMPARTIDOS.obtenerLiteral()) == 0){
                sNuevoValor = propiedades.getProperty(objeto.toString());
                //TODO: Lo que se vaya a hacer cuando cambia de valor esta propiedad
            }
        }           
    }

}
class IndicesFileFilter implements FileFilter {

  private String _extesionIndices;

  public IndicesFileFilter( String extesionIndices ){
    _extesionIndices = extesionIndices;
  }
  
  public boolean accept(File f) {
    return f.getName().toLowerCase().endsWith( _extesionIndices );
    //return f.isDirectory() || f.getName().toLowerCase().endsWith(".part.met");
  }
  
  
  public String getDescription() {
    return _extesionIndices+" files";
  }
}
