package control.gestion_ficheros;

import java.io.File;
import java.io.IOException;

import logica.datos.ListaArchivos;
import logica.paquete.Archivo;

//************************************************************************************//
/**
 * Esta clase se encarga de gestionar todos los archivos compartidos del usuario.
 * Leerá la estructura de directorios para tener una lista de los archivos que 
 * éste comparte.
 *
 * @author pitidecaner
 * @author S@L-c
 */
public class GestorCompartidos {

	// ATRIBUTOS
    public ListaArchivos _listaCompartidos;

//	************************************************************************************//
    /**
     * Constructor de la clase GestorCompartidos.
     * 
     * @param directorioCompartidos Directorio de compartidos.
     *  
     * @throws IOException Se lanza la excepción cuando se produce algún
     * error en la apertura o lectura del directorio de compartidos.
     */
    public GestorCompartidos(File directorioCompartidos) throws IOException {

        _listaCompartidos = new ListaArchivos();

        // Comprobamos que sea un directorio válido
        if (!directorioCompartidos.isDirectory())
            throw new IOException("---> El Directorio de Compartidos no es un directorio válido.");
  
        // Recuperamos todos los archivos del directorio
        File[] ficherosComp = directorioCompartidos.listFiles();
        
        // Los introducimos en la lista
        for (File a : ficherosComp) 
            if (!a.isDirectory())
			    _listaCompartidos.add(new Archivo(a));
			
        // TODO: Actualizar la vista de compartidos por ejemplo
    }

//	************************************************************************************//
    /**
     * Devuelve la lista de archivos compartidos para ser enviada al servidor.
     *
     * @return la lista de archivos en la carpeta compartidos.
     */
    public ListaArchivos getArchivosCompartidos(){
    	
        return _listaCompartidos;
    }
}
