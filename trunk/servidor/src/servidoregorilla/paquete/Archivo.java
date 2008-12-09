/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servidoregorilla.paquete;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.security.DigestException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que trata todo lo relacionado con los archivos que se comparten en
 * el servidor. 
 * 
 * @author pitidecaner
 * @author Salcedonia
 */
public class Archivo implements Serializable{

    // ATRIBUTOS
    public String _nombre;
    public String _hash;
    public long _tamaño;
    public TipoArchivo _tipo;

    /**
     * Constructor de la clase Archivo.
     */
    public Archivo() {
        
    }
        
    /**
     * Constructor de la clase Archivo. Genera la información de un archivo a 
     * partir un identificador de fichero.
     * 
     * @param f Identificador de fichero.
     */
    public Archivo(File f){
        
        //TODO: si el fichero no existe o no es un fichero... excepcion
        FileInputStream fs = null;

        try {

            // Aplicamos md5 al fichero para asignarle un hash único
            MessageDigest ms = MessageDigest.getInstance("md5");
            
            // Recibimos los datos relativos al archivo
            fs = new FileInputStream(f);

            int leidos = 0;
            int total = 0;
            int chunk = 1024 * 4;
            byte[] buff = new byte[chunk];

            do {

                fs.read (buff, chunk, leidos);
                ms.digest(buff, total, leidos);
                total += leidos;
            } while (leidos == chunk);

            fs.close();

            // Configuramos el tipo de archivo en función de los datos recibidos
            _hash = new String (ms.digest());
            _nombre = f.getName();
            _tamaño = f.length();
            // TODO: Asignarle el tipo de archivo
        } 
        catch (DigestException ex) {

            Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) {

            Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (NoSuchAlgorithmException ex) {

            Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
        } 
        finally {

            try {

                // Cerramos el flujo de entrada
                fs.close();
            } 
            catch (IOException ex) {

                Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
