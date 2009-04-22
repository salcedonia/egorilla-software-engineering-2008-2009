package gestorDeFicheros;


import datos.*;
import java.util.Vector;
import mensajes.serverclient.ListaArchivos;

/**
 * Clase que se encarga de gestionar todos los archivos compartidos del usuario.
 * Es un mediador para controlar los archivos compartidos y poder preguntar
 * por los fragmentos de un determinado archivo, asi como pedir los byte[]
 * de un fragmento.
 *
 * @author Jose Miguel Guerrero
 */
public class GestorCompartidos {

    // ATRIBUTOS
    private static GestorCompartidos _instancia=null;

    private Fragmentador _fragmentador=null;

    private GestorDisco _gestorDisco=null;

    
    //SINGLETON
    public static GestorCompartidos getInstancia(){
        if(_instancia==null){
            _instancia=new GestorCompartidos();
        }
        return _instancia;
    }

    /**
     * Metodo para asignar el gestor de disco asociado al gestor.
     * @param disco GestorDisco asociado al GestorCompartidos.
     */
    public void setGestorDisco(GestorDisco disco){
        _gestorDisco=disco;
        _fragmentador=disco.getFragmentador();
    }

    /**
     * Metodo para la peticion de la lista de archivos compartidos.
     * @return ListaArchivos - lista de los archivos compartidos.
     */
    public ListaArchivos getArchivosCompartidos() {
        return _gestorDisco.getListaArchivosTodos();
    }

    /**
     * Metodo para consultar fragmentos de un determinado archivo.
     * @param hash - Codigo hash del fichero.
     * @return Vector<Fragmento> del fichero indicado en el hash.
     */
    public Vector<Fragmento> queFragmentosTienes( String hash ){
        return _fragmentador.queFragmentosTienes(hash);
    }

    /**
     * Metodo para pedir un fragmento de un determinado archivo.
     * @param frag Fragmento deseado
     * @return Byte[] correspondiente a la informacion enviada del fragmento.
     */
    public Byte[] dameBytesDelFragmento( Fragmento frag ){
        return _fragmentador.dameBytesDelFragmento(frag);
    }
}
