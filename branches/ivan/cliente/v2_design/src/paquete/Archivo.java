/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package paquete;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
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
    //El archivo debe tener varios nombres, ya q si el primer nombre es fake, no podra saberse el nombre real
    public String _nombre;
    public String _hash;
    public long _tamano;
    public TipoArchivo _tipo;

    /*****************************************************************************/
    /**
     * Constructor de la clase Archivo.
     */
    public Archivo() {
        
    }

    /*public Archivo(File f){
        
        FileInputStream fs = null;

        try {
            fs = new FileInputStream(f);

            int leidos = 0;
            int total = 0;
            int chunk = 1024 * 1024 * 4;
            byte[] buff = new byte[chunk];

            do {
                leidos = fs.read (buff, total, chunk);
                total += leidos;
            } while (leidos == chunk);

            fs.close();

            _hash = MD5Sum.getFileMD5Sum(f);
            _nombre = f.getName();
            _tamano = f.length();
            String[] extensiones = _nombre.split("\\.");
            TipoArchivo.iniciarTiposArchivo();
            if( extensiones.length != 0 ){
              _tipo = TipoArchivo.devuelveTipo( extensiones[ extensiones.length-1 ].toLowerCase() );
            }else
              _tipo = TipoArchivo.OTRO;
        }
        catch (IOException ex) {

            Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally {

            try {

                fs.close();
            } 
            catch (IOException ex) {

                Logger.getLogger(Archivo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }*/

    public Archivo(String nombre, String hash, long tamano, TipoArchivo tipo) {
      set( nombre, hash, tamano, tipo);
    }

    /*****************************************************************************/
    /**
     * Devuelve el enumerado con el tipo de archivo correspondiente a la extensión 
     * que se pasa como parámetro.
     * 
     * @param f Referencia al fichero.
     * @return El enumerado con el tipo de archivo correspondiente a la extensión 
     * que se pasa como parámetro.
     */
/*    private TipoArchivo asignarTipoArchivo(File f) {
        
        String fileName = null;   
        String extension;  
        fileName = fileName.trim();

        int dotPos = fileName.lastIndexOf(".");
        extension = fileName.substring(dotPos);

        return TipoArchivo.devuelveTipo(extension);
    }*/

    /*****************************************************************************/
    /**
     * Recupera el nombre de este archivo
     * @return El nombre del archivo
     */
    public String getNombre() {
        return _nombre;
    }

    public void setNombre(String nombre) {
        _nombre = nombre;
    }

    /*****************************************************************************/
    /**
     * Recupera el hash de este archivo. El hash es unico para el archivo 
     * independientemente del nombre que tenga por lo que dos archivos con el 
     * mismo hash serán el mismo siempre.
     * 
     * @return el hash del archivo.
     */
    public String getHash() {
        return _hash;
    }

    public void setHash(String hash) {
        _hash = hash;
    }

    public TipoArchivo getTipo() {
        return _tipo;
    }

    public void setTipo(TipoArchivo tipo) {
        _tipo = tipo;
    }

    /*****************************************************************************/
    /**
     * Recupera el tamaño de este archivo.
     * @return El tamaño del archivo.
     */
    public long  getSize() {
        return _tamano;
    }

    public void setSize(long tamano) {
        _tamano = tamano;
    }

    public void set( String nombre, String hash, long tamano, TipoArchivo tipo) {
      setNombre( nombre );
      setHash( hash );
      setSize( tamano );
      setTipo( tipo );
    }

    /*****************************************************************************/
    /**
     * Compara dos instancias para saber si referencian al mismo archivo
     *
     * @param archivo, el archivo a comparar con este
     *
     */
    public boolean comparaArchivo(Archivo a) {
        return _hash.equals(a.getHash());
    }
}
