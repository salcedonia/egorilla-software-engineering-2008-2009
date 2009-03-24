/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

package peerToPeer;

import java.util.ArrayList;
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

    public ArrayList<Fragmento> getFragmentos(String hash) {
    
        // si lo tengo entre los temporales, lo busco ahi, si no lo busco en 
        // otro sitio
        
        return _temporales.get(hash).getFragmentos();       
    }
   
    public void llegaFragmento(Fragmento f, Byte[] chunk){
        
        // esta este archivo entre los buscados?
        
        // esta este fragmento entre los que no tenemos?
        
        // actualiza lista fragmentos
        
        // envia a gestor de disco
    }

    public boolean puedoBajar(String hash) {
        // tengo este fichero entre los pendientes o completos??
        if (_temporales.containsKey(hash))// lo tengo en pendientes
            return true;
        
        // probar dnd lo puedo tener
        
        return false;
    }
}
