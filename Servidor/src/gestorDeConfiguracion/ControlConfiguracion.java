package gestorDeConfiguracion;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Properties;

/**
 * La clase ControlConfiguracion es el almacén de la configuración persistente del servidor.
 * Se leerá a partir de un fichero de configuración y será accesible allí donde 
 * sea necesario.
 * 
 * Una vez inicializado se podrán leer todos los campos en cualquier ámbito 
 * siendo necesario que se inicialice en el arranque del servidor.
 * 
 * @author pitidecaner
 */
public class ControlConfiguracion{   
    
    private FicheroConfiguracionServidor fich;
    static private int[] _versionpeticion; 
    static private int   _puerto;
    static private InetAddress _dirIP;
   
    
   
    
    // ATRIBUTOS
    
    


    /**
     * Para inicializar esta clase se lee el fichero de configuración indicado.
     * 
     * @param f El fichero de configuración asociado.
     * @throws java.io.IOException Se lanza en caso de no encontrar el fichero de 
     * configuración indicado.
     */
    /*static*/ public void leeConfig(String fichero) throws IOException, ControlConfiguracionServidorException {

        // Cargamos el fichero de properties
        this.fich=new FicheroConfiguracionServidor(fichero);
        
        String tmp = this.fich.getProperty("Versionpeticion");
        String[] split = tmp.split(":,;");
        _versionpeticion = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            _versionpeticion[i] = Integer.parseInt(split[i]);
        }
        

         /**   
         * Primer parámetro: Versión del peticion.
         * 
         * Consistirá en una lista con todas las versiones del peticion admitidos 
         * por el servidor separados por : , ó ;
         * Las diferentes versiones se indicarán con un número entero.
         */
       
       
        /*
         * Puerto por el que escucha el servidor las conexiones de los clientes.
         * Será un número entero.
         */
        _puerto = Integer.parseInt(this.fich.getProperty("puerto"));
        _dirIP= InetAddress.getLocalHost();
        this.fich.setProperty("direccionIP",_dirIP.toString());
    }

//    /**
//     * comprueba si la version del peticion está entre las toleradas por esta
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
//        while ((!existe) &&(i<_versionpeticion.length))
//            existe = (_versionpeticion[i++]== v)? true: false;
//        return existe;
//    }
  
    /**
     * Devuelve la versión del peticion que se está utilizando.
     * 
     * @return La versión del peticion que se está utilizando.
     */
    static public int[] getVersion(){
        
        return _versionpeticion;
    }
    
    /**
     * Devuelve el número entero que identifica al puerto por el que escucha el 
     * servidor las conexiones de los clientes.
     * 
     * @return El número entero que identifica al puerto por el que escucha el 
     * servidor las conexiones de los clientes.
     */
    static public int getPuerto() {
        
        return _puerto;
    }
    static public InetAddress getDirIP()
    {
        return _dirIP;
    }
}
