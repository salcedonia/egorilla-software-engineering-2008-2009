/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

package peerToPeer.descargas;

import datos.Archivo;
import datos.Fragmento;
import java.util.ArrayList;
import java.util.Hashtable;
import peerToPeer.EstadoTemporal;


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
   
    /**
     * llega un fragmento, se comprueba el orden y se almacena
     * 
     * @param f descripcion del fragmento
     * @param chunk el fragmento en si
     */
    public void llegaFragmento(Fragmento f, Byte[] chunk){
        
        // esta este archivo entre los buscados?
        if (!_temporales.containsKey(f.hash))
            return;
        
        // esta este fragmento entre los que no tenemos?
        
        boolean encontrado = false;
        for (Fragmento frag : _temporales.get(f.hash).getFragmentos()) {
            encontrado = encontrado || frag.equals(f);
        }
        if (encontrado)return;
        
        // actualiza lista fragmentos
        _temporales.get(f.hash).getFragmentos().add(f);
        
        // envia a gestor de disco
        // TODO: interacctuar gestor disco
    }

    /**
     * puede alguien preguntarme por este fichero?
     * si lo tengo en compartidos o en descargas pendientes, entonces
     * puedo servirle partes
     * 
     * @param hash identificaci�n del fichero
     * @return si lo tengo o no entre los mios
     */
    public boolean puedoBajar(String hash) {
        // tengo este fichero entre los pendientes o completos??
        if (_temporales.containsKey(hash))// lo tengo en pendientes
            return true;
        
        // probar dnd lo puedo tener
        
        return false;
    }
}
