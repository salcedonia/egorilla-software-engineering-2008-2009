package servidoregorilla;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * El objeto config es el almacen de la configuración persistente del servidor.
 * Se leerá a partir de un fichero de configuración y será accesible allí donde 
 * sea necesario.
 * 
 * Una vez inicializado se podran leer todos los campos en cualquier hambito. 
 * Por ello es necesario que se inicialice en el arranque del servidor.
 * 
 * @author pitidecaner
 */
class Config{   
    
    
    static private Properties _prop;
    static private File       _file;
    
    static private int[] _protoVersion;
    static private int   _puerto;
    
    /**
     * para inicializar esta clase se lee el fuichero de configuración indicado.
     * se almacena el path para escribir este fichero cuando sea necesario.
     * 
     * @param f el fichero de configuración 
     * @throws java.io.IOException en caso de no encontrar el fichero de 
     * configuración indicado.
     */
    static public void leeConfig(File f) throws IOException {

        _prop = new Properties();
        _prop.load(new FileInputStream(f));

        /*
         * Primer parámetro: version del protocolo.
         * será una lista con todas las  dersiones  del protocolo admintidos por
         * el servidor separados por : , ó ;
         * Las diferentes versiones se indicarán con un número entero.
         */
        String tmp = _prop.getProperty("VersionProtocolo");
        String[] split = tmp.split(":,;");
        _protoVersion = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            _protoVersion[i] = Integer.parseInt(split[i]);
        }
        
        /*
         * puerto por el que escucha el servidor las conexiones de los clientes.
         * Será un número entero.
         */
        _puerto = Integer.parseInt(_prop.getProperty("puerto"));
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
//        while ((!existe) &&(i<_protoVersion.length))
//            existe = (_protoVersion[i++]== v)? true: false;
//        return existe;
//    }
  
    /**
     * puerto por el que escucha el servidor las conexiones de los clientes.
     * Será un número entero.
     */
    static int puerto() {
        return _puerto;
    }
}
