package gestorDeFicheros;

import java.io.*;
import datos.*;

/**
 * Clase que se encarga de gestionar todos los archivos compartidos del usuario.
 * Lee la estructura de directorios del directorio que el usuario comparte 
 * para tener una lista de los archivos del mismo. 
 *
 * @author Luis Ayuso, Ivan Munsuri, Javier Salcedo
 */
public class GestorCompartidos {

    // ATRIBUTOS
    private ListaArchivos _lista;
    private TipoArchivo _tipo;

    /**
     * 
     * @param path
     * @throws java.io.IOException
     */
    public GestorCompartidos(File path) throws IOException {

        _lista = new ListaArchivos();

        if (!path.isDirectory()) {
            throw new IOException("el path no es un directorio válido");
        }
        System.out.println("\nDirectorio de compartidos: ");
        System.out.println(path.getAbsolutePath() + "\n");

        File[] ficherosComp = path.listFiles();
        for (File f : ficherosComp) {
            if (!f.isDirectory()) {
                _lista.add(procesarArchivoCompartido(f));
            }
        }

        for (int i = 0; i < _lista.size(); i++) {
            System.out.println(_lista.elementAt(i).getNombre());
        }
        System.out.println("\n<" + _lista.size() + "> ficheros compartidos.");
    }

    /**
     * 
     * @param f
     * @return
     * @throws java.io.IOException
     */
    public Archivo procesarArchivoCompartido(File f) throws IOException {

        String nombre = null;

        nombre = f.getName();
        String[] extensiones = nombre.split("\\.");
        //Cambiar de sitio este crear, ineficiente-static-no_poo
        TipoArchivo.iniciarTiposArchivo();
        if (extensiones.length != 0) {
            _tipo = TipoArchivo.devuelveTipo(extensiones[extensiones.length - 1].toLowerCase());
        } else {
            _tipo = TipoArchivo.OTRO;        //Nombre-Hash-Tamano-Tipo
        //tengo que cerrar f?
        //f.close();
        }
        return new Archivo(nombre, MD5Sum.getFileMD5Sum(f), f.length(), _tipo);
    }

    /**
     * recupera la lista de archivos para ser enviada al servidor.
     *
     * @return la lista de archivos en la carpeta compartidos
     */
    public ListaArchivos getArchivosCompartidos() {
        return _lista;
    }
}
