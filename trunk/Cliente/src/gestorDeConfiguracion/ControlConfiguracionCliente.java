package gestorDeConfiguracion;

import java.util.ArrayList;
import java.util.Properties;

/**
 * Clase que controla la gestion de la configuracion de la aplicacion Cliente eGorilla mediante 
 * 2 archivos de properties(uno que contiene la configuracion del usuario y otro que contiene
 * la configuracion por defecto) y 2 archivos de informacion general (uno para los servidores y otro 
 * para las descargas).
 * La clase esta disenada siguiendo el patron Singleton, y ademas implementa la interfaz Sujeto,
 * que permite informar a los Observadores cuando se producen cambios en su estado interno.
 * @author F. Javier Sanchez Pardo
 */
public class ControlConfiguracionCliente {

    /**
     * Variable estatica que contiene la instancia unica de la clase.
     */
    private static ControlConfiguracionCliente _instancia = null;
    /**
     * Fichero de propiedades principal. 
     */
    private FicheroPropiedades _propiedadesPrincipal;
    /**
     * Fichero de propiedades por defecto.
     */
    private FicheroPropiedades _propiedadesPorDefecto;
    /**
     * Variables con el fichero de info de servidores.
     */
    private FicheroInfo<InfoServidor> _infoServidores;
    /**
     * ArrayList de observadores.
     */
    private ArrayList<ObservadorControlConfiguracionCliente> _observadores =
            new ArrayList<ObservadorControlConfiguracionCliente>();
    
    /**
     * 
     * @param propiedadesPrincipal: Nombre del fichero principal que contiene la
     *        configuracion principal (valores de propiedades).
     * @param propiedadesPorDefecto: Nombre del fichero que contiene la configuracion
     *        por defecto (valores de propiedades).
     * @param InfoServidores: Nombre del fichero que contiene la info
     *        de servidores.
     * @throws gestorDeConfiguracion.ControlConfiguracionClienteException
     */
    protected ControlConfiguracionCliente(String propiedadesPrincipal, String propiedadesPorDefecto,
            String InfoServidores) throws ControlConfiguracionClienteException {
        super();
        _propiedadesPrincipal = new FicheroPropiedades(propiedadesPrincipal);
        _propiedadesPorDefecto = new FicheroPropiedades(propiedadesPorDefecto);
        _infoServidores = new FicheroInfo<InfoServidor>(InfoServidores);
    }

    /**
     * Obtiene la Unica instancia de esta clase que se permite crear (patron Singleton)
     * Este método se utiliza solamente LA PRIMERA VEZ, para inicializar el objeto con los ficheros asociados.
     * @param propiedadesPrincipal : Nombre del fichero principal que contiene la
     *        configuracion principal (valores de propiedades).
     * @param propiedadesPorDefecto: Nombre del fichero que contiene la configuracion
     *        por defecto (valores de propiedades).
     * @param InfoServidores: Nombre del fichero que contiene la info
     *        de servidores.
     * @return Unica instancia de esta clase
     * @throws gestorDeConfiguracion.ControlConfiguracionClienteException
     */
    public static ControlConfiguracionCliente obtenerInstancia(String propiedadesPrincipal, String propiedadesPorDefecto,
            String infoServidores) throws ControlConfiguracionClienteException {

        if (_instancia == null) {
            _instancia = new ControlConfiguracionCliente(propiedadesPrincipal, propiedadesPorDefecto,
                    infoServidores);

            //Se realiza la carga de los ficheros de Configuracion principal y por defecto.
            _instancia._propiedadesPrincipal.cargarFicheroPropiedades();
            _instancia._propiedadesPorDefecto.cargarFicheroPropiedades();
            //Se realiza la carga del fichero de Servidores.
            _instancia._infoServidores.cargarFicheroInfo();
        }
        return _instancia;
    }

    /**
     * Obtiene la Unica instancia de esta clase que se permite crear (patron Singleton)
     * Los ficheros asociados ya deben estar inicializados. 
     * @return Unica instancia de esta clase
     * @throws gestorDeConfiguracion.ControlConfiguracionClienteException
     */
    public static ControlConfiguracionCliente obtenerInstancia() throws ControlConfiguracionClienteException {
        if (_instancia == null) {
            throw new ControlConfiguracionClienteException("No se ha inicializado ControlConfiguracionCliente con los ficheros asociados.");
        }
        return _instancia;
    }

    //
    // METODOS PARA EL MANEJO DE LOS FICHEROS DE PROPERTIES.
    //
    /**
     * Busca el valor de una propiedad en el archivo de propiedades actual y lo devuelve.
     * @param sClave: clave a buscar
     */
    public String obtenerPropiedad(String sClave) {
        
        return _propiedadesPrincipal.obtenerPropiedad(sClave);
    }

    /**
     * Devuelve el objeto Properties que almacena todas las propiedades actuales.
     */
    public Properties obtenerConfiguracion() {
        
        return _propiedadesPrincipal.obtenerConjuntoPropiedades();
    }

    /**
     * Busca el valor de una propiedad en el archivo de propiedades POR DEFECTO y lo devuelve.
     * @param sClave: clave a buscar
     */
    public String obtenerPropiedadPorDefecto(String sClave) {
        
        return _propiedadesPorDefecto.obtenerPropiedad(sClave);
    }

    /**
     * Devuelve el objeto Properties que almacena los valores por defecto de todas las propiedades.
     */
    public Properties obtenerConfiguracionPorDefecto() {
        
        return _propiedadesPorDefecto.obtenerConjuntoPropiedades();
    }

    /**
     * Establece un nuevo valor para una propiedad, actualizando el fichero de propiedades
     * asociado. Notifica a todos sus observadores del cmabio en la propiedad.
     * @param sClave
     * @param sValor
     * @throws gestorDeConfiguracion.ControlConfiguracionClienteException
     */
    public void establecerPropiedad(String sClave, String sValor) throws ControlConfiguracionClienteException {
        _propiedadesPrincipal.establecerPropiedad(sClave, sValor);
        Properties propiedades = new Properties();
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
        if (propiedades.size() == 0) {
            return;
        }
        _propiedadesPrincipal.establecerConjuntoPropiedades(propiedades);
        notificarCambioEnPropiedades(propiedades);
    }
    //
    // METODOS PARA EL MANEJO DEL FICHERO CON INFORMACION DE SERVIDORES.
    //
    /**
     * Devuelve un ArrayList de objetos InfoServidor con toda la info actual.
     */
    public ArrayList<InfoServidor> obtenerInfoServidores() {
        
        return _infoServidores.getInfoFichero();
    }

    /**
     * Actualiza en memoria y en el disco la Informacion de los servidores.
     * EN PRINCIPIO NO ES NECESARIO NOTIFICAR A LOS OBSERVADORES DE LOS CAMBIOS EN ESTOS VALORES.
     * @param alOBjetos: ArrayList de objetos InfoServidor
     * @throws gestorDeConfiguracion.ControlConfiguracionClienteException
     */
    public void establcerInfoServidores(ArrayList<InfoServidor> infoServidores) throws ControlConfiguracionClienteException {
        
        _infoServidores.setInfoFichero(infoServidores);
    }

    /**
     * Anade un nuevo servidor al archivo de servidores.
     * 
     * @param infoServidor Informacion del nuevo servidor a aniadir.
     *
     * @throws gestorDeConfiguracion.ControlConfiguracionClienteException
     */
    public void anadirServidor(InfoServidor infoServidor) throws ControlConfiguracionClienteException {

        //Anado el nuevo elemento a la lista de servidores si no estaba antes.
        if(!_infoServidores.getInfoFichero().contains(infoServidor)){
            
            _infoServidores.getInfoFichero().add(infoServidor);

            //Actualizo el fichero de configuracion de servidores.
            _infoServidores.grabarFicheroInfo(); 
        }
        else
            throw new ControlConfiguracionClienteException("El servidor ya se encontraba añadido en la lista");       
    }

    /**
     * Elimina un servidor de la lista de servidores.
     *  
     * @param infoServidor Informacion del servidor a eliminar.
     * 
     * @throws gestorDeConfiguracion.ControlConfiguracionClienteException
     */
    public void eliminarServidor(InfoServidor infoServidor) throws ControlConfiguracionClienteException {

        // Elimino el nuevo elemento a la lista de servidores.
        _infoServidores.getInfoFichero().remove(infoServidor);

        //Actualizo el fichero de configuracion de servidores.
        _infoServidores.grabarFicheroInfo(); 
    }
    
    //------------------------------\\
    //      interfaz Observable     \\
    //------------------------------\\
    
    /**
     * Anade un objeto observador a la estructura de observadores
     * @param obs: objeto que implementa la interfaz ObservadorControlConfiguracionCliente
     */
    public void anadirObservador(ObservadorControlConfiguracionCliente obs) {
        //Aniado el observador solo si no esta en la estructura de observadores
        if (_observadores.contains(obs)) {
            _observadores.add(obs);
        }
    }

    /**
     * Quita un objeto observador de la estructura de observadores.
     * @param obs: objeto que implementa la interfaz ObservadorControlConfiguracionCliente
     */
    public void quitarObservador(ObservadorControlConfiguracionCliente obs) {
        _observadores.remove(obs);
    }

    /**
     * Recorre todos los Observadores registrados para notificarles del cambio 
     * en una propiedad en la configuracion del cliente.
     */
    public void notificarCambioEnPropiedades(Properties propiedades) {
        
        for(ObservadorControlConfiguracionCliente observador : _observadores)
            observador.cambioEnPropiedades(this, propiedades);
    }

}
