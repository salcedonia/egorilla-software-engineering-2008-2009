/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

package peerToPeer.descargas;

import datos.Archivo;
import java.util.Hashtable;
import java.util.Vector;
import mensajes.serverclient.DatosCliente;

import gestorDeFicheros.*;


/**
 *
 * @author Luis Ayuso
 */
public class GestorDescargas {

    
    private Vector<String> _ficherosSinInformacion;
    private Hashtable<String,Vector<DatosCliente>> _ficherosConInformacion;

    private GestorDisco _gestorDisco;

    //Guarda los byte[] a disco
    private Ensamblador _ensamblador;
    
    //Creo que no se necesita el fragmentador en este caso
    //private Fragmentador _fragmentador;

    public GestorDescargas() {
       _ficherosSinInformacion = new Vector<String>();
       _ficherosConInformacion = new Hashtable<String, Vector<DatosCliente>>();
    
       /*_gestorDisco = new GestorDisco();*/
       _ensamblador = _gestorDisco.getEnsamblador();
       //Que le ha pasado a GestorDescarga, donde llegan los Fragmentos-Byte[]
       //para poder guardarlos en disco?
       //if( guardarFragmentoEnArchivo( fragmento, partes ) == true ) guay else verqhacer
    }
    
    public void nuevaDescargaPendiente(Archivo a){
        _ficherosSinInformacion.add(a.getHash());
    }
   
/**
 *  rellena la informacion para realizar la descarga de un fichero, además se 
 * verifica que el fichero estubiese pendiente de descarga, ya que puede ser un
 * mensaje erroneo.
 * 
 * @param hash
 * @param propietarios
 * @return
 */
    public boolean completaInformacion (String hash, DatosCliente[] propietarios){
        
        // puede ser que ya tengamos info de este archivo, miraremos en el
        // array de archivos que ya la tienen
        if (!_ficherosSinInformacion.contains(hash)){
            
            if(_ficherosConInformacion.containsKey(hash)){
                for (int i = 0; i < propietarios.length; i++) {
                    _ficherosConInformacion.get(hash).add(propietarios[i]);
                 }
                return true;
            }
            return false;
        }
        
        // si la informacion esta vacia, seguimos sin info.
        if (propietarios.length == 0)
            return  true;
        
        _ficherosSinInformacion.remove(hash);
        
        Vector<DatosCliente> datos = new Vector<DatosCliente>();
        for (int i = 0; i < propietarios.length; i++) 
            datos.add(propietarios[i]);
            
        _ficherosConInformacion.put(hash, datos);
        return true;
    }
    
    /**
     * se da de baja a un propietario en todas las
     * descargas pendientes que tengamos.
     */
    public void eliminaPropietario(String ip){
       // TODO: para cada entrada, quita a este cliente
    }
}
