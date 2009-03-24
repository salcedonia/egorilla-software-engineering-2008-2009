/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

package peerToPeer;

import java.util.Hashtable;
import protocoloEgorilla.Archivo;

/**
 *
 * @author Luis Ayuso
 */
public class GestorDescargas {

    private Hashtable<String,EstadoTemporal> _temporales;

    public GestorDescargas() {
        _temporales = new Hashtable<String, EstadoTemporal>();
    }
    
    public void altaFichero(Archivo a){
        EstadoTemporal e = new EstadoTemporal(a);
        _temporales.put(a.getHash(), e);
    }
   
    public void llegaFragmento(Fragmento f, Byte[] chunk){
        
        // esta este archivo entre los buscados?
        
        // esta este fragmento entre los que no tenemos?
        
        // actualiza lista fragmentos
        
        // envia a gestor de disco
    }
}
