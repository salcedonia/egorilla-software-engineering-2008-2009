/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteegorilla.control.gestionficheros;

import java.util.Vector;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import datos.*;
import paquete.*;

/**
 * esta clase se encarga de gestionar todos los archiovos compartidos del usuario
 *
 * leera la estructura de directorios para tener una lista de los archivos
 *
 *
 * @author layuso
 */
public class GestorCompartidos {

    private ListaArchivos _lista;
    private TipoArchivo _tipo;

    public GestorCompartidos(File path) throws IOException {

        _lista = new ListaArchivos();

        if (!path.isDirectory()) {
            throw new IOException("el path no es un directorio válido");
        }
        System.out.println( "\nDirectorio de compartidos: " );
        System.out.println( path.getAbsolutePath()+"\n" );

        File[] ficherosComp = path.listFiles();
        for (File f : ficherosComp) {
            if (!f.isDirectory()){
	      /*System.out.println( new Archivo(a).getNombre() );
              _lista.add(new Archivo(a));*/
              _lista.add( procesarArchivoCompartido( f ) );
            }
        }
       
        for(int i = 0; i < _lista.size(); i++){
	  System.out.println(_lista.elementAt(i).getNombre());
        }
        System.out.println( "\n<"+_lista.size()+"> ficheros compartidos." );
    }

    public Archivo procesarArchivoCompartido(File f) throws IOException {
      //FileInputStream fs = null;
      String nombre = null;

            nombre = f.getName();
            String[] extensiones = nombre.split("\\.");
            //Cambiar de sitio este crear, ineficiente-static-no_poo
            TipoArchivo.iniciarTiposArchivo();
            if( extensiones.length != 0 ){
              /*System.out.println("Tipo "+ extensiones[ extensiones.length-1 ].toLowerCase());*/
              _tipo = TipoArchivo.devuelveTipo( extensiones[ extensiones.length-1 ].toLowerCase() );
            }else
              _tipo = TipoArchivo.OTRO;

            //Nombre-Hash-Tamano-Tipo
            //tengo que cerrar f?
            //f.close();
            
        return new Archivo( nombre, MD5Sum.getFileMD5Sum( f ), f.length(), _tipo );
    }

    /**
     * recupera la lista de archivos para ser enviada al servidor.
     *
     * @return la lista de archivos en la carpeta compartidos
     */
    public ListaArchivos getArchivosCompartidos(){
        return _lista;
    }
}
