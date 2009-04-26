package gestorDeConfiguracion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

/**

 * Clase que controla la gestion de la configuracion de la aplicacion Cliente eGorilla mediante 
 * 2 archivos de properties: uno que contiene la configuracion del usuario y otro que contiene
 * la configuracion por defecto.
 * La clase esta disenada siguiendo el patron Singleton, y ademas implementa la interfaz Sujeto,
 * que permite informar a los Observadores cuando se producen cambios en su estado interno.
 * @author F. Javier Sanchez Pardo
 */
public class ControlConfiguracionCliente{

    //Variable estatica que contiene la instancia unica de la clase.
    private static ControlConfiguracionCliente _instancia = null;
    //Variables con el nombre del fichero de propiedades principal y el de propiedades
    //por defecto.
    private FicheroPropiedades _oFicheroPropsPpal;
    private FicheroPropiedades _oFicheroPropsPorDefecto;

    private ArrayList <ObservadorControlConfiguracionCliente> _listaObservadores = 
            new ArrayList <ObservadorControlConfiguracionCliente>();
    
    //
    //Implementacion de los metodos equivalentes a la interfaz Observable
    //
    
    /**
     * Anade un objeto observador a la estructura de observadores
     * @param obs: objeto que implementa la interfaz ObservadorControlConfiguracionCliente
     */
    public void anadirObservador(ObservadorControlConfiguracionCliente obs) {
        int iIndice =_listaObservadores.indexOf(obs);
        //Aniado el observador solo si no esta en la estructura de observadores
        if ( iIndice == -1 )
            _listaObservadores.add(obs);
    }
    
    /**
     * Quita un objeto observador de la estructura de observadores.
     * @param obs: objeto que implementa la interfaz ObservadorControlConfiguracionCliente
     */
    public void quitarObservador(ObservadorControlConfiguracionCliente obs) {
        _listaObservadores.remove(obs);
    }

    /**
     * Recorre todos los Observadores registrados para notificarles del cambio 
     * en una propiedad en la configuracion del cliente.
     */
    public void notificarCambioEnPropiedades(Properties propiedades) {
        Iterator<ObservadorControlConfiguracionCliente> iterador = _listaObservadores.iterator();
        while (iterador.hasNext()){
            iterador.next().cambioEnPropiedades (this, propiedades);
        }
    }

    /**
     * 
     * @param sNomFicheroPropsPpal: Nombre del fichero principal que contiene la
     *        configuracion principal (valores de propiedades).
     * @param sNomFicheroPropsxDefecto: Nombre del fichero que contiene la configuracion
     *        por defecto (valores de propiedades).
     * @throws gestorDeConfiguracion.ControlConfiguracionClienteException
     */   
    protected ControlConfiguracionCliente(String sNomFicheroPropsPpal, String sNomFicheroPropsxDefecto) throws ControlConfiguracionClienteException {
        super();
        _oFicheroPropsPpal = new FicheroPropiedades(sNomFicheroPropsPpal);
        _oFicheroPropsPorDefecto = new FicheroPropiedades(sNomFicheroPropsxDefecto);
    }

    /**
     * Obtiene la Unica instancia de esta clase que se permite crear (patron Singleton)
     * Este método se utiliza solamente LA PRIMERA VEZ, para inicializar el objeto con los ficheros asociados.
     * @param sNomFicheroPropsPpal : Nombre del fichero principal que contiene la
     *        configuracion principal (valores de propiedades).
     * @param sNomFicheroPropsxDefecto: Nombre del fichero que contiene la configuracion
     *        por defecto (valores de propiedades).
     * @return Unica instancia de esta clase
     * @throws gestorDeConfiguracion.ControlConfiguracionClienteException
     */
    public static ControlConfiguracionCliente obtenerInstancia(String sNomFicheroPropsPpal, String sNomFicheroPropsxDefecto) throws ControlConfiguracionClienteException {
        if (_instancia == null) {
            _instancia = new ControlConfiguracionCliente(sNomFicheroPropsPpal, sNomFicheroPropsxDefecto);
        }
        return _instancia;
    }
    
    /**
     * Obtiene la Unica instancia de esta clase que se permite crear (patron Singleton)
     * Los ficheros asociados ya deben estar inicializados. 
     * @return Unica instancia de esta clase
     * @throws gestorDeConfiguracion.ControlConfiguracionClienteException
     */public static  ControlConfiguracionCliente obtenerInstancia() throws ControlConfiguracionClienteException{
        if (_instancia == null)
             throw new ControlConfiguracionClienteException("No se ha inicializado ControlConfiguracionCliente con los ficheros asociados.");
        return _instancia;
    }

    /**
     * Busca el valor de una propiedad en el archivo de propiedades actual y lo devuelve.
     * @param sClave: clave a buscar
     */
    public String obtenerPropiedad(String sClave) {
        return this._oFicheroPropsPpal.obtenerPropiedad(sClave);
    }

    /**
     * Devuelve el objeto Properties que almacena todas las propiedades actuales.
     */
    public Properties obtenerConfiguracion() {
        return this._oFicheroPropsPpal.obtenerConjuntoPropiedades();
    }    
    
    /**
     * Busca el valor de una propiedad en el archivo de propiedades POR DEFECTO y lo devuelve.
     * @param sClave: clave a buscar
     */
    public String obtenerPropiedadPorDefecto(String sClave) {
        return this._oFicheroPropsPorDefecto.obtenerPropiedad(sClave);
    }

    /**
     * Devuelve el objeto Properties que almacena los valores por defecto de todas las propiedades.
     */
    public Properties obtenerConfiguracionPorDefecto() {
        return this._oFicheroPropsPorDefecto.obtenerConjuntoPropiedades();
    }    
    
    /**
     * Establece un nuevo valor para una propiedad, actualizando el fichero de propiedades
     * asociado. Notifica a todos sus observadores del cmabio en la propiedad.
     * @param sClave
     * @param sValor
     * @throws gestorDeConfiguracion.ControlConfiguracionClienteException
     */
    public void establecerPropiedad(String sClave, String sValor) throws ControlConfiguracionClienteException {
        this._oFicheroPropsPpal.establecerPropiedad(sClave, sValor);
        Properties propiedades = new Properties ();
        propiedades.setProperty(sClave, sValor);
        notificarCambioEnPropiedades(propiedades);
    }

    /**
     * Establece los valores para el conjunto de propiedades recibido como parametro (no tienen porque estar
     * todas), y lo actualiza en memoria y en el disco (fichero asociado).
     * Notifica a todos sus observadores de las propiedades que han cambiado.
     * @param propiedades: objeto Properties que contiene determinadas claves y sus valores.
     * @throws gestorDeConfiguracion.ControlConfiguracionClienteException
     */
    public void establecerConfiguracion(Properties propiedades) throws ControlConfiguracionClienteException {
        _oFicheroPropsPpal.establecerConjuntoPropiedades(propiedades);
        notificarCambioEnPropiedades (propiedades);
    }

 
}
