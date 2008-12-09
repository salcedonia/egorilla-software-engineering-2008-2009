package servidoregorilla;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * La clase Config es el almacén de la configuración persistente del servidor.
 * Se leerá a partir de un fichero de configuración y será accesible allí donde 
 * sea necesario.
 * 
 * Una vez inicializado se podrán leer todos los campos en cualquier ámbito 
 * siendo necesario que se inicialice en el arranque del servidor.
 * 
 * @author pitidecaner
 */
public class Config{   
    
    // ATRIBUTOS
    static private Properties _properties;
    static private File       _file;
    
    static private int[] _versionProtocolo;
    static private int   _puerto;
    
    /**
     * Para inicializar esta clase se lee el fichero de configuración indicado.
     * 
     * @param f El fichero de configuración asociado.
     * @throws java.io.IOException Se lanza en caso de no encontrar el fichero de 
     * configuración indicado.
     */
    static public void leeConfig(File f) throws IOException {

        // Cargamos el fichero de properties
        _properties = new Properties();
        _properties.load(new FileInputStream(f));

        /*
         * Primer parámetro: Versión del protocolo.
         * 
         * Consistirá en una lista con todas las versiones del protocolo admitidos 
         * por el servidor separados por : , ó ;
         * Las diferentes versiones se indicarán con un número entero.
         */
        String tmp = _properties.getProperty("VersionProtocolo");
        String[] split = tmp.split(":,;");
        _versionProtocolo = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            _versionProtocolo[i] = Integer.parseInt(split[i]);
        }
        
        /*
         * Puerto por el que escucha el servidor las conexiones de los clientes.
         * Será un número entero.
         */
        _puerto = Integer.parseInt(_properties.getProperty("puerto"));
    }

//    /**
//     * comprueba si la version del protocolo está entre las toleradas por esta
//     * implementación.
//     * 
//     * Este número nos permite discernir si un cliente habla la versión correcta
//     * o no.
//     * 
//     * en caso de que no resulte compatible, se deshechara esta conexion.
//     * 
//     * @param v la version del cliente a comprobar .
//     * @return si la versión v es compatible o no.
//     */
//    static boolean protocolVersion(int v){
//        boolean existe = false;
//        int i = 0;
//        while ((!existe) &&(i<_versionProtocolo.length))
//            existe = (_versionProtocolo[i++]== v)? true: false;
//        return existe;
//    }
  
    /**
     * Devuelve la versión del protocolo que se está utilizando.
     * 
     * @return La versión del protocolo que se está utilizando.
     */
    static int[] getVersion(){
        
        return _versionProtocolo;
    }
    
    /**
     * Devuelve el número entero que identifica al puerto por el que escucha el 
     * servidor las conexiones de los clientes.
     * 
     * @return El número entero que identifica al puerto por el que escucha el 
     * servidor las conexiones de los clientes.
     */
    static int getPuerto() {
        
        return _puerto;
    }
}
