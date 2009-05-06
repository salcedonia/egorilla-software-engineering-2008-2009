
package util;

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

        
}
