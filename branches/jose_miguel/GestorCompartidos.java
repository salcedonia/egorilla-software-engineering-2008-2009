package gestorDeFicheros;

import java.io.*;
import datos.*;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Clase que se encarga de gestionar todos los archivos compartidos del usuario.
 * Lee la estructura de directorios del directorio que el usuario comparte 
 * para tener una lista de los archivos del mismo. 
 *
 * @author Luis Ayuso, Ivan Munsuri, Javier Salcedo, Jose Miguel Guerrero
 */
public class GestorCompartidos {

    // ATRIBUTOS
    private ListaArchivos _lista;
    private TipoArchivo _tipo;
    private Hashtable<String, Archivo> _archivosCompartidos=new Hashtable<String, Archivo>();

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
        String hash=MD5Sum.getFileMD5Sum(f);
        Archivo arch=new Archivo(nombre, hash, f.length(), _tipo);
        _archivosCompartidos.put(hash, arch);
        return arch;
    }

    /**
     * recupera la lista de archivos para ser enviada al servidor.
     *
     * @return la lista de archivos en la carpeta compartidos
     */
    public ListaArchivos getArchivosCompartidos() {
        return _lista;
    }

    //------------------

    public boolean existe(String hashFichero){
        for(int i=0;i<_lista.size();i++){
            if(_lista.get(i).getHash().equalsIgnoreCase(hashFichero)){
                return true;
            }
        }
        //TODO: buscar en el gestorDescargas, pasarle el objeto creado o crear singleton
        return false;
    }

    public ArrayList<Fragmento> getFragmentos(String hash) {
        ArrayList<Fragmento> listaFrag=new ArrayList<Fragmento>();
        Fragmento frag=null;
        Archivo arch=null;
        if(_archivosCompartidos.contains(hash)){
            arch=_archivosCompartidos.get(hash);
            frag=new Fragmento(arch.getNombre(),arch.getHash(),arch.getSize(),0);
            listaFrag.add(frag);
            return listaFrag;
        }
        //TODO: pedir al gestor de descargas los fragmentos del archivo si lo tiene
        return listaFrag;
    }

    public void nuevoArchivoCompartido(Archivo a){
        _archivosCompartidos.put(a.getHash(), a);
    }

}
