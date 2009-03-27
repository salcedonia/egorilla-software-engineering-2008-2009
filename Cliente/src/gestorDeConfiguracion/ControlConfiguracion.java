package gestorDeConfiguracion;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Controla toda la configuracion de la aplicacion Cliente eGorilla mediante 
 * archivos de properties.
 * Implementación mediante un patrón Singleton.
 * @author Javier Sánchez Pardo
 */
public class ControlConfiguracion {

    private static ControlConfiguracion _instancia = null;
    //Variable que contiene el nombre del fichero de propiedades.
    private String ficheroProperties;
    protected ControlConfiguracion(){
        super();
    };

    public static ControlConfiguracion getInstancia(){
        if (_instancia == null)
            _instancia = new ControlConfiguracion();
        return _instancia;
    }
    
    
    /**
     * Carga todas las propiedades existentes en el fichero de properties.
     * @return Properties
     */
    public Properties getPropiedades() throws ControlConfiguracionException {
      Properties propiedades = new Properties();      
      try {
          FileInputStream fichero = new FileInputStream(this.ficheroProperties);          
          propiedades.load(fichero);
          fichero.close();
      } catch (IOException ioe) {
          throw new ControlConfiguracionException();
      }   
      return propiedades;
    } // Fin getPropiedades()
    
//    public String toString(){
//        if ( this._instancia == null )
//            return "";
//        Properties propiedades = getPropiedades();
//        for (Enumeration e = prop.keys(); e.hasMoreElements() ; ) {
//    // Obtenemos el objeto
//    Object obj = e.nextElement();
//    System.out.println(obj + ": " + prop.getProperty(obj.toString()));
//}
//    }
}
