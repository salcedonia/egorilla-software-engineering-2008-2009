
package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Métodos generales para la serializacion/deserializacion de objetos hacia/desde un fichero.
 * 
 * @author F. Javier Sanchez Pardo
 */
public class Util {
    public static void serializar (Object objeto, String sNomFichero) throws FileNotFoundException, IOException{
        FileOutputStream fos = new FileOutputStream (sNomFichero);
        ObjectOutputStream oos = new ObjectOutputStream (fos);
        oos.writeObject (objeto);
        oos.flush();
        oos.close();
    }
    
    public static Object deserializar (String sNomFichero) throws FileNotFoundException, IOException, ClassNotFoundException{
        FileInputStream fis = new FileInputStream (sNomFichero);
        ObjectInputStream ois = new ObjectInputStream (fis);
        Object objeto = ois.readObject();
        ois.close();
        return objeto;
    }

    public static Process lanzarVisualizadorDefecto(String filepath) {
        String url = new File(filepath).getAbsolutePath();

        //En funcion del Sistema Operativo decidimos el programa de linea de comandos
        //a ejecutar.
        try {
            if(System.getProperty("os.name").toUpperCase().indexOf("95") != -1) {
                return Runtime.getRuntime().exec(new String[]{"command.com", "/C", "start", url});
            }
            if(System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1) {
                return Runtime.getRuntime().exec(new String[]{"cmd.exe", "/C", "start", url});
            }
            if(System.getProperty("os.name").toUpperCase().indexOf("MAC") != -1) {
                return Runtime.getRuntime().exec(new String[]{"open", url});
            }
            if (System.getProperty("os.name").toUpperCase().indexOf("LINUX") != -1 ) {
                //Para Linux se lanza por defecto KDE. 
                //TODO: determinar cual es el entorno
//                if(getLinuxDesktop().equals("kde")) {
                    return Runtime.getRuntime().exec(new String[]{"kfmclient", "exec", url});
//                } else {
//                    return Runtime.getRuntime().exec(new String[]{"gnome-open", url});
//                }
            }
        } catch(IOException ioex) {
        }
        return null;
    }
}
