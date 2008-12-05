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
 *
 * @author pitidecaner
 */
public class Archivo implements Serializable{

    public String nombre;
    public String hash;
    public long   size;
    //TODO: tipo
    
    public Archivo(){
    }
    
    /**
     * genera la información de un archivo a partir un identificador de fichero.
     * 
     * @param f
     */
    public Archivo(File f){
        {

            //TODO: si el fichero no existe o no es un fichero... excepcion
            
            FileInputStream fs = null;
            try {
                MessageDigest ms = MessageDigest.getInstance("md5");
                fs = new FileInputStream(f);
                
                int leidos = 0;
                int total = 0;
                int chunk = 1024 * 4;
                byte[] buff = new byte[chunk];

                do {
                    fs.read (buff, chunk, leidos);
                    ms.digest(buff, total, leidos);
                    total+= leidos;
                } while (leidos == chunk);
                
                fs.close();
                
                this.hash = new String (ms.digest());
                this.nombre = f.getName();
                this.size = f.length();
                
            } catch (DigestException ex) {
                Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NoSuchAlgorithmException ex) {
                Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fs.close();
                } catch (IOException ex) {
                    Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
