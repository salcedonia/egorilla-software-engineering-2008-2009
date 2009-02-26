package logica.paquete;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

import logica.paquete.md5.MD5Sum;

//*****************************************************************************//
/**
 * Clase que trata todo lo relacionado con los archivos que se comparten en
 * el servidor. 
 * 
 * @author pitidecaner
 * @author Salcedonia
 */
public class Archivo implements Serializable{

    // CONSTANTES
	private static final long serialVersionUID = 1L;
	
	// ATRIBUTOS
    public String _nombre;
    public String _hash;
    public long _tamano;
    public Fuentes _fuentes;
    public double _disponibilidad; // En %
    public TipoArchivo _tipo;

//  *****************************************************************************//
    /**
     * Constructor de la clase Archivo.
     */
    public Archivo() {
        
    }
        
//  *****************************************************************************//
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
            
            // Recibimos los datos relativos al archivo
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

            // Configuramos el tipo de archivo en función de los datos recibidos
            _hash = MD5Sum.getFileMD5Sum(f);
            _nombre = f.getName();
            _tamano = f.length();
            //_fuentes = new Fuentes(1,1);
            //_disponibilidad = _fuentes.calcularDisponibilidad();            
            _tipo = TipoArchivo.IMAGEN;
            //_tipo = asignarTipoArchivo(f);
        }
        catch (IOException ex) {

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

//  *****************************************************************************//
    /**
     * Devuelve el enumerado con el tipo de archivo correspondiente a la extensión 
     * que se pasa como parámetro.
     * 
     * @param f Referencia al fichero.
     * @return El enumerado con el tipo de archivo correspondiente a la extensión 
     * que se pasa como parámetro.
     */
	@SuppressWarnings("unused")
	private TipoArchivo asignarTipoArchivo(File f) {
        
        String fileName = null;   
        String extension;  
        fileName = fileName.trim();

        int posicionPunto = fileName.lastIndexOf(".");
        extension = fileName.substring(posicionPunto);

        return TipoArchivo.devuelveTipo(extension);
    }

//  *****************************************************************************//
    /**
     * Recupera el nombre de este archivo.
     * 
     * @return El nombre del archivo.
     */
    public String getNombre() {
        
    	return _nombre;
    }

//  *****************************************************************************//
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

//  *****************************************************************************//
    /**
     * Recupera el tamaño de este archivo.
     * 
     * @return El tamaño del archivo.
     */
    public long  getSize() {
    	
        return _tamano;
    }

//  *****************************************************************************//
    /**
     * Compara dos instancias para saber si referencian al mismo archivo.
     *
     * @param archivo, el archivo a comparar con este.
     */
    public boolean comparaArchivo(Archivo a) {
        
    	return _hash.equals(a.getHash());
    }
}
