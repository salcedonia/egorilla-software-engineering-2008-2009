/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

package peerToPeer;

import java.util.ArrayList;
import protocoloEgorilla.Archivo;

/**
 *
 * @author Luis Ayuso
 */
public class EstadoTemporal {

    /**
     * fragmentos que tenemos de este fichero.
     */
    private ArrayList<Fragmento> _fragmentos;
    
    private String nombre;
    private String hash;
    
    public EstadoTemporal(Archivo a) {
        nombre = a.getNombre();
        hash = a.getHash();
        
        _fragmentos = new ArrayList<Fragmento>();
    }

    /**
     * para conseguir los fragmentos que se tienen de un determinado fichero
     * @return la lista de fragmentos de existir.
     */
    public ArrayList<Fragmento> getFragmentos (){
        return _fragmentos;
    }
}

