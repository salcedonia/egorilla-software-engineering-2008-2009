package gestorDeFicheros;

import java.io.*;
import mensajes.serverclient.*;
import datos.*;

/**
 * Gestor de la lista de archivos, tanto temporales como completos que tenemos en el disco. 
 *
 */
public class GestorDisco {

  //Sacar valores de properties
  //Puede ser un array de directorio temporales
  private String _directorioTemporales = "temp";

  //Puede ser un array de directorio completos
  private String _directorioCompletos = "compartidos";

  //Lista de todos (temporales+completos)
  private ListaArchivos _listaTodos;

  //Lista de los temporales
  private ListaArchivos _listaTemporales;

  //Lista de los completos
  private ListaArchivos _listaCompletos;

  //TODO: Convertir esta clase en dinamica, leer la extensiones de un fichero
  //vease un properties
  private TipoArchivo _tipo;

  private Ensamblador _ensamblador;

  private Fragmentador _fragmentador;

  /**
   * 
   * @param path
   * @throws java.io.IOException
   */
  public GestorDisco() /*throws IOException*/ {  

    _listaTodos = new ListaArchivos();
    _listaTemporales = new ListaArchivos();
    _listaCompletos = new ListaArchivos();

    File fDirectorioTemporales = new File( _directorioTemporales ), 
         fDirectorioCompletos = new File( _directorioCompletos );

    //Cuando haya multiples directorio, hacer un for para comprobar todos

    if( fDirectorioTemporales.isDirectory() == false ||
        fDirectorioCompletos.isDirectory() == false) {
      //throw new IOException("Algun directorio no es un directorio valido");
    }

    System.out.println("\nDirectorio de temporales: ");
    System.out.println( fDirectorioTemporales.getAbsolutePath() + "\n");

    System.out.println("\nDirectorio de completos: ");
    System.out.println( fDirectorioCompletos.getAbsolutePath() + "\n");
    
    //Debo filtrar los ficheros y leer solo los .part.met
    File[] ficherosTemporales = fDirectorioTemporales.listFiles( new PartMetFileFilter() );
    //si el length() es 0 digo que el dir esta vacio
    if( ficherosTemporales.length > 0 ){
      System.out.println( "Procesando archivos de indices...<" );
      //TODO No se si hacerlo aqui o llamar al ensamblador para que haga
      //la lista.
      recorrerListaArchivos( _listaTemporales );
    }else{
      System.out.println( "Directorio de temporales vacio.\n" );
    }

    File[] ficherosCompletos = fDirectorioCompletos.listFiles();

    if( ficherosCompletos.length > 0 ){
      System.out.println( "Procesando archivos completos..." );
      for( File f : ficherosCompletos){
        if( f.isFile() == true ) {
          _listaCompletos.add( procesarArchivoCompartido( f ) );
        }//Sino es fichero no le aniado
      }
      recorrerListaArchivos( _listaCompletos );
    }else{
      System.out.println( "Directorio de compartidos vacio.\n" );
    }

    //Creo una nueva lista con todos los ficheros actuales
    _listaTodos = unirListas( _listaTemporales, _listaCompletos );
    //Cuando haya varios directorio por cada una se hara la union , usando un for
    //incluso, como son atributos de clase no hace falta pasarlos como parametros
    
    _fragmentador = new Fragmentador( this, _directorioCompletos, _directorioTemporales );
    _ensamblador = new Ensamblador( this, _directorioCompletos, _directorioTemporales );
  }


  //estos metodos estrictamente no deberian estar, q se pregunte a las properties
  public String getDirectorioTemporales(){
    return _directorioTemporales;
  }

  //estos metodos estrictamente no deberian estar, q se pregunte a las properties
  public String getDirectorioCompletos(){
    return _directorioCompletos;
  }

  public ListaArchivos getListaArchivosTemporales(){
    return _listaTemporales;
  }

  public ListaArchivos getListaArchivosCompletos(){
    return _listaCompletos;
  }

  public ListaArchivos getListaArchivosTodos(){
    return _listaTodos;
  }

  public void setListaArchivosTemporales( ListaArchivos listaTemporales ){
    _listaTemporales = listaTemporales;
  }

  public void setListaArchivosCompletos( ListaArchivos listaCompletos){
    _listaCompletos = listaCompletos;
  }

  public void setListaArchivosTodos( ListaArchivos listaTodos ){
    _listaTodos = listaTodos;
  }

  public Fragmentador getFragmentador(){
    return _fragmentador;
  }

  public Ensamblador getEnsamblador(){
    return _ensamblador;
  }

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

    /**
     * 
     * @param f
     * @return
     * @throws java.io.IOException
     */
    public Archivo procesarArchivoCompartido(File f) /*throws IOException*/ {

        String nombre = null;

        nombre = f.getName();
        String[] extensiones = nombre.split("\\.");
        //Cambiar de sitio este crear, ineficiente-static-no_poo
        TipoArchivo.iniciarTiposArchivo();
        if (extensiones.length != 0) {
            _tipo = TipoArchivo.devuelveTipo(extensiones[extensiones.length - 1].toLowerCase());
        } else {
            _tipo = TipoArchivo.OTRO;        //Nombre-Hash-Tamano-Tipo
        //tengo que cerrar f?
        //f.close();
        }
        return new Archivo(nombre, MD5Sum.getFileMD5Sum(f), f.length(), _tipo);
    }
}

class PartMetFileFilter implements FileFilter {
    public boolean accept(File f) {
      return f.getName().toLowerCase().endsWith(".part.met");
        //return f.isDirectory() || f.getName().toLowerCase().endsWith(".part.met");
    }
    
    public String getDescription() {
        return ".part.met files";
    }
}
