/* 
	This file is part of eGorilla.

    eGorilla is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    eGorilla is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with eGorilla.  If not, see <http://www.gnu.org/licenses/>.
*/
/**
 * Paquete donde tenemos las clases que tratan con el disco.
 */
package gestorDeFicheros;

import java.io.*;
import mensajes.serverclient.*;
import datos.*;
import gestorDeConfiguracion.*;
import gestorDeErrores.ControlDeErrores;
import gestorDeErrores.ErrorEGorilla;
import java.util.*;

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
public class GestorDisco implements ObservadorControlConfiguracionCliente {

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
     * Constructor por defecto del Gestor de Disco, el cual realiza todas una serie de operaciones 
     * iniciales sobre el disco. Se encarga de crear las listas de los archivos del usuario, tanto la
     * de los archivos temporales como la de completos y la union de las dos, establece el tamano maximo
     * del fragmento, los directorios de temporales y completos e incluso las extensiones que se usaran 
     * para los archivos creados para los archivos temporales.
     */
    public GestorDisco() {

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
        _tamanioBytesFragmento = 369069; //antes era 512, en bytes, luego 369069

        try {
            ControlConfiguracionCliente config = ControlConfiguracionCliente.obtenerInstancia();
            _directorioTemporales = config.obtenerPropiedad(PropiedadCliente.DIR_LLEGADA.obtenerLiteral());
            _directorioCompletos = config.obtenerPropiedad(PropiedadCliente.DIR_COMPARTIDOS.obtenerLiteral());
        } catch (Exception e) {
            /*System.out.println("No se encuentra el fichero de configuracion. Estableciendo" +
                    "valores por defecto.");*/
            ControlDeErrores.getInstancia().registrarError(ErrorEGorilla.ERROR_DISCO,
                                "No se encuentra el fichero de configuracion. Estableciendo" +
                    "valores por defecto." );
            _directorioTemporales = "temp";
            _directorioCompletos = "compartidos";
        }

        File fDirectorioTemporales = new File(_directorioTemporales),
                fDirectorioCompletos = new File(_directorioCompletos);

        if( fDirectorioTemporales.exists() == false ){
            if( fDirectorioTemporales.mkdir() == false ){
                //System.out.println("Problemas al crear el directorio <"+fDirectorioTemporales.getName()+">");
                ControlDeErrores.getInstancia().registrarError(ErrorEGorilla.ERROR_DISCO,
                                "Problemas al crear el directorio <"+fDirectorioTemporales.getName()+">" );
            }
        }

        if( fDirectorioCompletos.exists() == false ){
            if( fDirectorioCompletos.mkdir() == false ){
                //System.out.println("Problemas al crear el directorio <"+fDirectorioCompletos.getName()+">");
                ControlDeErrores.getInstancia().registrarError(ErrorEGorilla.ERROR_DISCO,
                                "Problemas al crear el directorio <"+fDirectorioCompletos.getName()+">" );
            }
        }

        //Cuando haya multiples directorio, hacer un for para comprobar todos

        if (fDirectorioTemporales.isDirectory() == false ||
                fDirectorioCompletos.isDirectory() == false) {
            //System.out.println("Directorio de temporales o de completos no valido");
            ControlDeErrores.getInstancia().registrarError(ErrorEGorilla.ERROR_DISCO,
                                "Directorio de temporales o de completos no valido" );
        //throw new IOException("Algun directorio no es un directorio valido");
        }

        System.out.println("\nDirectorio de temporales: ");
        System.out.println(fDirectorioTemporales.getAbsolutePath() + "\n");

        System.out.println("\nDirectorio de completos: ");
        System.out.println(fDirectorioCompletos.getAbsolutePath() + "\n");

        listarArchivosTemporalesIniciales( fDirectorioTemporales );

        listarArchivosCompletosIniciales( fDirectorioCompletos );

        //Creo una nueva lista con todos los ficheros actuales
        setListaArchivosTodos( getManejarListaArchivos().unirListas(_listaTemporales, _listaCompletos) );
        //Cuando haya varios directorio por cada una se hara la union , usando un for
        //incluso, como son atributos de clase no hace falta pasarlos como parametros

        _fragmentador = new Fragmentador(this);
        _ensamblador = new Ensamblador(this);
    }
    //-1 sin acceder a disco, 0 se puede, 1 detenido, 2 - ya se puede cerrar, xq no va a ver mas escrituras

    /**
     * Procesa el directorio donde se encuentra los archivos temporales, filtrando a partir de los archivos
     * de indice de cada fichero temporal. Al filtrar por extension, debemos asegurarnos de usar una comun
     * para todos los archivos de indices y de no borrarlos, ya que en tal caso perderemos todo lo que 
     * llevabamos descargado, pese a mantener el fichero temporal de byte's.
     * @param fDirectorioTemporales directorio donde se encuentran los archivos temporales.
     */
    private void listarArchivosTemporalesIniciales(File fDirectorioTemporales) {
        //Debo filtrar los ficheros y leer solo los .part.met
        File[] ficherosTemporales = fDirectorioTemporales.listFiles(new IndicesFileFilter(
                getExtesionIndices()));
        //si el length() es 0 digo que el dir esta vacio
        if (ficherosTemporales.length > 0) {
            System.out.println("Procesando archivos de indices...");
            //TODO No se si hacerlo aqui o llamar al ensamblador para que haga
            //la lista.
            //Debo recorrer los archivos de indices y no los archivos temporales
            for (File f : ficherosTemporales) {
                if (f.isFile() == true) { //Creo que no hace falta esta comprobacion

                    _listaTemporales.add(procesarArchivoIndices(f));
                }//Sino es fichero no le aniado

            }
            getManejarListaArchivos().recorrerListaArchivos(_listaTemporales);
        } else {
            System.out.println("Directorio de temporales vacio.\n");
        }
    }

    /**
     * Procesa el directorio donde se encuentra los archivos completos.
     * @param fDirectorioCompletos directorio donde se encuentran los archivos completos.
     */
    public void listarArchivosCompletosIniciales(File fDirectorioCompletos) {
        File[] ficherosCompletos = fDirectorioCompletos.listFiles();

        _listaCompletos = new ListaArchivos();
        
        if (ficherosCompletos.length > 0) {
            System.out.println("Procesando archivos completos...");
            for (File f : ficherosCompletos) {
                if (f.isFile() == true) {
                    _listaCompletos.add(procesarArchivoCompartido(f));
                }//Sino es fichero no le aniado

            }
            getManejarListaArchivos().recorrerListaArchivos(_listaCompletos);
        } else {
            System.out.println("Directorio de compartidos vacio.\n");
        }
    }

    /**
     * Obtiene la ruta relativa del directorio de los ficheros temporales.
     * @return Devuelve la cadena que representa dicha ruta.
     */
    public String getDirectorioTemporales() {
        return _directorioTemporales;
    }

    /**
     * Obtiene la ruta relativa del directorio de los ficheros completos.
     * @return Devuelve la cadena que representa dicha ruta.
     */
    public String getDirectorioCompletos() {
        return _directorioCompletos;
    }

    /**
     * Obtiene la extension actual de los archivos de indices.
     * @return Devuelve la cadena que representa a la extension de los indices.
     */
    public String getExtesionIndices() {
        return _extesionIndices;
    }

    /**
     * Obtiene la extension actual de los archivos de temporales.
     * @return Devuelve la cadena que representa a la extension de los temporales.
     */
    public String getExtesionFicheroTemporal() {
        return _extesionFicheroTemporal;
    }

    /**
     * Obtiene el tamano maximo que puede tener un Fragmento.
     * @return Devuelve la cantidad maxima de bytes que tendra un Fragmento.
     */
    public int getTamanioBytesFragmento() {
        return _tamanioBytesFragmento;
    }

    /**
     * Obtiene la lista de los archivos temporales que tiene el usuario.
     * @return Devuelve la lista de los archivos temporales.
     */
    public ListaArchivos getListaArchivosTemporales() {
        return _listaTemporales;
    }

    /**
     * Obtiene la lista de los archivos completos que tiene el usuario.
     * @return Devuelve la lista de los archivos completos.
     */
    public ListaArchivos getListaArchivosCompletos() {
        return _listaCompletos;
    }

    /**
     * Obtiene la lista de todos los archivos que tiene el usuario, tanto los completos como los
     * incompletos.
     * @return Devuelve la lista de todos los archivos.
     */
    public ListaArchivos getListaArchivosTodos() {
      return _listaTodos;
    }

    /**
     * Establece la lista de todos los archivos que tiene el usuario, tanto los completos como los
     * incompletos.
     * @param listaTodos es la lista de todos los archivos.
     */
    public void setListaArchivosTodos( ListaArchivos listaTodos ) {
      _listaTodos = listaTodos;
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
    public Fragmentador getFragmentador() {
        return _fragmentador;
    }

    /**
     * Obtiene la instancia adecuada para el ensamblado de los archivos temporales del usuario.
     * @return Devuelve la instancia del Ensamblador.
     */
    public Ensamblador getEnsamblador() {
        return _ensamblador;
    }

    /**
     * Obtiene la instancia adecuada que ofrece la funcionalidad para tratar adecuadamente los 
     * archivos de indices.
     * @return Devuelve la instancia del manejador de indices.
     */
    public ManejarIndices getManejarIndices() {
        return _manejarIndices;
    }

    /**
     * Obtiene la instancia adecuada que ofrece la funcionalidad para tratar adecuadamente las listas de 
     * archivos del usuario.
     * @return Devuelve la instancia del manejador de las listas de archivos.
     */
    public ManejarListaArchivos getManejarListaArchivos() {
        return _manejarListaArchivos;
    }

    /**
     * Proceso el fichero recibido, obteniendo del mismo ciertos metadatos, utilizados para crear un tipo
     * Archivo.
     * @param f fichero del que obtendremos ciertos metadatos.
     * @return Devuelve un objeto Archivo con los metadatos del fichero.
     */
    private Archivo procesarArchivoCompartido(File f) /*throws IOException*/ {

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

    /**
     * Proceso un fichero de indices, obteniendo del mismo el objeto Archivo, con los metadatos 
     * correspondientes.
     * @param f fichero de indices del que obtendremos el Archivo.
     * @return Devuelve un objeto Archivo con los metadatos del fichero.
     */
    public Archivo procesarArchivoIndices(File f) /*throws IOException*/ {
        Indices indices = getManejarIndices().leeFicheroIndices(f);
        return indices.getArchivo();
    }

  /**
   * Crea automaticamente todos los fragmentos que tendria un fichero nuevo, en funcion del 
   * tamano maximo del fragmento y el tamano del fichero nuevo.
   * @param archivo representa al archivo nuevo de cual voy a generar los fragmentos que 
   *                necesito.
   * @return Devuelve todos los fragmentos de los que se compone el archivo nuevo.
   */
  public Vector<Fragmento> fragmentosArchivoNuevo( Archivo archivoRequerido ){
    Vector<Fragmento> listaFragmento = null;
    Fragmento fragmento = null;      
      int tamBytes;
      int cantidadFragmentos = cantidadFragmentosArchivo( archivoRequerido.getSize() );
      listaFragmento = new Vector<Fragmento>( cantidadFragmentos );

      //System.out.println("Capacidad "+listaFragmento.capacity());

      //Se supone que nunca va decir 0 cantidadFragmentos
      if( cantidadFragmentos == 1 ){
        tamBytes = (int)archivoRequerido.getSize();
        fragmento = new Fragmento( archivoRequerido.getNombre(), 
          archivoRequerido.getHash(), tamBytes, 0 ); //0 por ser el primero
        listaFragmento.add( fragmento );
      }else{
        //Empiezo por el ultimo fragmento q es el unico que puede tener diferente tamanio
        int i;
        for( i = 0;  i < cantidadFragmentos - 1;  i++ ){
          fragmento = new Fragmento( archivoRequerido.getNombre(), archivoRequerido.getHash(), 
            _tamanioBytesFragmento, i*_tamanioBytesFragmento );
          listaFragmento.add( fragmento );
        }
        tamBytes = (int)(cantidadFragmentos*_tamanioBytesFragmento-archivoRequerido.getSize() );
        tamBytes = _tamanioBytesFragmento-tamBytes;
        fragmento = new Fragmento( archivoRequerido.getNombre(), //tam - offset
          archivoRequerido.getHash(), tamBytes, i*_tamanioBytesFragmento );
        listaFragmento.add( fragmento );
      }
      return listaFragmento;
  }

  /**
   * Crea automaticamente todos los fragmentos que tendria un fichero nuevo, en funcion del 
   * tamano maximo del fragmento y el tamano del fichero nuevo.
   * @param archivo representa al archivo nuevo de cual voy a generar los fragmentos que 
   *                necesito.
   * @return Devuelve todos los fragmentos de los que se compone el archivo nuevo.
   */
  public Vector<Fragmento> fragmentosArchivoCompleto( Archivo archivo ){
    return fragmentosArchivoNuevo( archivo );
  }


  /**
   * Calcula la cantidad de fragmento de los que se compone un archivo determinado.
   * @param hash es el identificador unico de un archivo determinado.
   * @return Devuelve la cantidad de fragmentos en funcion de tamanio del archivo.
   */
  public int cantidadFragmentosArchivo( String hash ){
    int cantidadFragmentos;
    //Busco en las listas el archivo y llamo a la otra
    ListaArchivos listaTemporales = getListaArchivosTemporales();
    ManejarListaArchivos manejarListaArchivos = getManejarListaArchivos();
    Archivo archivoRequerido = manejarListaArchivos.buscarArchivoEnLista( listaTemporales, hash );
    if( archivoRequerido == null ){
        cantidadFragmentos = 0;
    }else{
      cantidadFragmentos = cantidadFragmentosArchivo( archivoRequerido );
    }
    return cantidadFragmentos;
  }


  /**
   * Calcula la cantidad de fragmento de los que se compone un archivo determinado.
   * @param archivo .
   * @return Devuelve la cantidad de fragmentos en funcion de tamanio del archivo.
   */
  public int cantidadFragmentosArchivo( Archivo archivo ){
    long tamanio = archivo.getSize();
    return cantidadFragmentosArchivo( tamanio );
  }

  /**
   * Calcula la cantidad de fragmento de los que se compone un archivo determinado.
   * @param long es la cantidad de bytes que ocupa un archivo determinado.
   * @return Devuelve la cantidad de fragmentos en funcion de tamanio del archivo.
   */
  public int cantidadFragmentosArchivo( long tamanio ){
    int cantidadFragmentos;
    cantidadFragmentos = (int)tamanio / _tamanioBytesFragmento;

    if( tamanio == 0 )
        cantidadFragmentos = 1;
    else{
        if( tamanio % _tamanioBytesFragmento == 0 ){
          //Al no haber decimales, todas las partes tiene el mismo tamano
        }else{
          cantidadFragmentos+=1;
        }
    }
    return cantidadFragmentos;
  }

    /**
     * Este metodo es llamado cuando cambia la configuracion del cliente.
     * @param obj Objeto ControlConfiguracionCliente.
     * @param propiedades Conjunto de propiedades que ha cambiado.
     */
    public void cambioEnPropiedades(ControlConfiguracionCliente obj, Properties propiedades) {
        String sNuevoValor;
        for (Enumeration e = propiedades.propertyNames(); e.hasMoreElements();) {
            // Obtenemos el objeto
            Object objeto = e.nextElement();
            if (objeto.toString().compareTo(PropiedadCliente.DIR_LLEGADA.obtenerLiteral()) == 0) {
                sNuevoValor = propiedades.getProperty(objeto.toString());
            //TODO: Lo que se vaya a hacer cuando cambia de valor esta propiedad
            } else if (objeto.toString().compareTo(PropiedadCliente.DIR_COMPARTIDOS.obtenerLiteral()) == 0) {
                sNuevoValor = propiedades.getProperty(objeto.toString());
            //TODO: Lo que se vaya a hacer cuando cambia de valor esta propiedad
            }
        }
    }
}

/**
 * Clase que filtra los archivos de un directorio, haciendo validos solo aquellos que tengan la extension
 * adecuada.
 */
class IndicesFileFilter implements FileFilter {

    /**
     * Contiene el valor de la extension de los archivos de indices.
     */
    private String _extesionIndices;

    /**
     * Constructor que establece el valor de la extension de los archivos de indices para el filtro.
     * @param extensionIndices contiene el valor de la extension de los archivos de indices.
     */
    public IndicesFileFilter(String extesionIndices) {
        _extesionIndices = extesionIndices;
    }

    /**
     * Evalua si acepta o no el fichero en funcion de la extension que tenga.
     * @param f fichero a analizar.
     * @return Devuelve un booleando indicando si se acepto o no el fichero.
     */
    public boolean accept(File f) {
        return f.getName().toLowerCase().endsWith(_extesionIndices);
    //return f.isDirectory() || f.getName().toLowerCase().endsWith(".part.met");
    }

    /**
     * Obtiene informacion sobre que extensiones acepta el filtro.
     * @return Devuelve una cadena con informacion sobre la extesion de los ficheros que filtra.
     */
    public String getDescription() {
        return _extesionIndices + " files";
    }
}
