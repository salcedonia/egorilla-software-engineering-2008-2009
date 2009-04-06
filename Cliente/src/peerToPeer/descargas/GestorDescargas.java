/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

package peerToPeer.descargas;

import datos.Archivo;
import java.util.Hashtable;
import java.util.Vector;
import mensajes.serverclient.DatosCliente;


/**
 *
 * @author Luis Ayuso
 */
public class GestorDescargas {

    
    private Vector<String> _ficherosSinInformación;
    private Hashtable<String,Vector<DatosCliente>> _ficherosConInformación;
    
    public GestorDescargas() {
       _ficherosSinInformación = new Vector<String>();
       _ficherosConInformación = new Hashtable<String, Vector<DatosCliente>>();
    }
    
    public void nuevaDescargaPendiente(Archivo a){
        _ficherosSinInformación.add(a.getHash());
    }
   
/**
 *  rellena la información para realizar la descarga de un fichero, además se 
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
        if (!_ficherosSinInformación.contains(hash)){
            
            if(_ficherosConInformación.containsKey(hash)){
                for (int i = 0; i < propietarios.length; i++) {
                    _ficherosConInformación.get(hash).add(propietarios[i]);
                 }
                return true;
            }
            return false;
        }
        
        // si la información esta vacia, seguimos sin info.
        if (propietarios.length == 0)
            return  true;
        
        _ficherosSinInformación.remove(hash);
        
        Vector<DatosCliente> datos = new Vector<DatosCliente>();
        for (int i = 0; i < propietarios.length; i++) 
            datos.add(propietarios[i]);
            
        _ficherosConInformación.put(hash, datos);
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
