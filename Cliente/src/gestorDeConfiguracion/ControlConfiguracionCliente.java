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
public class ControlConfiguracionCliente implements Sujeto{

    //Variable estatica que contiene la instancia unica de la clase.
    private static ControlConfiguracionCliente _instancia = null;
    //Variables con el nombre del fichero de propiedades principal y el de propiedades
    //por defecto.
    private FicheroPropiedades oFicheroPropsPpal;
    private FicheroPropiedades oFicheroPropsxDefecto;

    private ArrayList <Observador> listaObservadores = new ArrayList <Observador>();
    
    //
    //Implementacion de los metodos de la interfaz Sujeto
    //
    /**
     * Anade un objeto observador a la estructura de observadores
     * @param obs: objeto que implementa la interfaz Observador
     */
    public void anadirObservador(Observador obs) {
        listaObservadores.add(obs);
    }
    
    /**
     * Quita un objeto observador de la estructura de observadores.
     * @param obs: objeto que implementa la interfaz Observador
     */
    public void quitarObservador(Observador obs) {
        listaObservadores.remove(obs);
    }

    /**
     * Recorre todos los Observadores registros para notificarles que la
     * configuracion del cliente ha cambiado.
     */
    public void notificarObservadores() {
        Iterator<Observador> iterador = listaObservadores.iterator();
        while (iterador.hasNext()){
            iterador.next().actualizar (this);
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
        oFicheroPropsPpal = new FicheroPropiedades(sNomFicheroPropsPpal);
        oFicheroPropsxDefecto = new FicheroPropiedades(sNomFicheroPropsxDefecto);
    }

    /**
     * Obtiene la Unica instancia de esta clase que se permite crear (patron Singleton)
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
    
    // TODO: esto lo ha hecho ayuso para continuar a espera de que se implante esto definitivamente
    public static  ControlConfiguracionCliente obtenerInstanciaDefecto() throws ControlConfiguracionClienteException{
        if (_instancia == null)
             throw new ControlConfiguracionClienteException("no hay config inicializada");
        return _instancia;
    }

    /**
     * Busca el valor de una propiedad en el archivo de propiedades actual y lo devuelve.
     * @param sClave: clave a buscar
     */
    public String obtenerPropiedad(String sClave) {
        return this.oFicheroPropsPpal.obtenerPropiedad(sClave);
    }

    /**
     * Devuelve el objeto Properties que almacena todas las propiedades actuales.
     */
    public Properties obtenerConfiguracion() {
        return this.oFicheroPropsPpal.obtenerConjuntoPropiedades();
    }    
    
    /**
     * Busca el valor de una propiedad en el archivo de propiedades POR DEFECTO y lo devuelve.
     * @param sClave: clave a buscar
     */
    public String obtenerPropiedadxDefecto(String sClave) {
        return this.oFicheroPropsxDefecto.obtenerPropiedad(sClave);
    }

    /**
     * Devuelve el objeto Properties que almacena los valores por defecto de todas las propiedades.
     */
    public Properties obtenerConfiguracionxDefecto() {
        return this.oFicheroPropsxDefecto.obtenerConjuntoPropiedades();
    }    
    
    /**
     * Establece un nuevo valor para una propiedad, actualizando el fichero de propiedades
     * asociado.
     * @param sClave
     * @param sValor
     * @throws gestorDeConfiguracion.ControlConfiguracionClienteException
     */
    public void establecerPropiedad(String sClave, String sValor) throws ControlConfiguracionClienteException {
        this.oFicheroPropsPpal.establecerPropiedad(sClave, sValor);
        notificarObservadores();
    }

    /**
     * Establece los valores para el conjunto de propiedades recibido como parametro (no tienen porque estar
     * todas), y lo actualiza en memoria y en el disco (fichero asociado).
     * @param propiedades: objeto Properties que contiene determinadas claves y sus valores.
     * @throws gestorDeConfiguracion.ControlConfiguracionClienteException
     */
    public void establecerConfiguracion(Properties propiedades) throws ControlConfiguracionClienteException {
        oFicheroPropsPpal.establecerConjuntoPropiedades(propiedades);
        notificarObservadores();
    }

 
}
