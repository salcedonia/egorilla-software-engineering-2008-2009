/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

package protocoloEgorilla;

import java.util.Hashtable;
import java.util.Vector;


/**
 *
 * @author Luis Ayuso
 */
public class GestorClientes {

    private Hashtable<String, Vector<Archivo>> _subidas;
    private Hashtable<String, Vector<Archivo>> _bajadas;
    private Hashtable<String, DatosCliente>    _datosClientes;

    public GestorClientes() {
        this._bajadas = new Hashtable<String, Vector<Archivo>>();
        this._datosClientes =  new Hashtable<String, DatosCliente>();
        this._subidas = new Hashtable<String, Vector<Archivo>>();
    }
    public void addClienteDescarga(String host, Archivo descarga){
        
        if (_bajadas.containsKey(host))
            _bajadas.get(host).add(descarga);
        else{
            Vector<Archivo> v = new Vector<Archivo>();
            v.add(descarga);
            _bajadas.put(host, v);
        }
        
    }
    public void addClienteSubida  (String host, Archivo descarga){
        
        if (_subidas.containsKey(host))
            _subidas.get(host).add(descarga);
        else{
            Vector<Archivo> v = new Vector<Archivo>();
            v.add(descarga);
            _subidas.put(host, v);
        }
        
    }
    public void removeClienteSubida  (String host, Archivo descarga){
        if (_subidas.containsKey(host)){
            _subidas.get(host).remove(descarga);
            if (_subidas.get(host).isEmpty())
                _subidas.remove(host);
        }
    }
    public void removeClienteBajada  (String host, Archivo descarga){
        if (_bajadas.containsKey(host)){
            _bajadas.get(host).remove(descarga);
            if (_bajadas.get(host).isEmpty())
                _bajadas.remove(host);
        }
    }
    
    public void removeCliente  (String host){
        _bajadas.remove(host);
        _subidas.remove(host);
    }
}
