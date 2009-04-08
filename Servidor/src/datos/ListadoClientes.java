/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

package datos;

import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;
import mensajes.serverclient.DatosCliente;

/**
 * la estructura almacena los clientes que actualmente estan dados de alta 
 * en el servidor
 *
 * @author Luis Ayuso
 */
public class ListadoClientes {
    Vector<String> _listaIPs;
    Hashtable<String,Integer> _puertos;
    Hashtable<String,DatosCliente> _datosClientes;

    public ListadoClientes() {
        _listaIPs = new Stack<String>();
        _puertos  = new Hashtable<String, Integer>();
        _datosClientes = new Hashtable<String, DatosCliente>();
    }
    
    public void altaCliente(String ip, int puerto, DatosCliente datos){
        
        if (!_listaIPs.contains(ip)){
            _listaIPs.add(ip);
            _puertos.put(ip, puerto);
            _datosClientes.put(ip, datos);
        }
    }
    
    /**
     * devuelve todos los clientes dados de alta
     * 
     * @return todos los clientes
     */
    public Vector<String> getClientes(){
        return _listaIPs;
    }
    
    /**
     * devuelve el puerto por el que un cliente escucha
     * 
     * @param ip la ip del cliente que se busca
     * @return el puerto por el que escucha
     */
    public int getPuerto(String ip){
        return _puertos.get(ip).intValue();
    }
    
    /**
     * elimina un cliente de la lista
     * @param ip cliente a eliminar
     */
    public void eliminaCliente(String ip){
        if(_listaIPs.contains(ip)){
            _listaIPs.remove(ip);
            _puertos.remove(ip);
        }
    }
    
    /**
     * si el cliente ha sido dado de alta
     * 
     * @param ip su ip
     * @return si esta en la lista
     */
    public boolean estaDeAlta(String ip){
        return _listaIPs.contains(ip);
    }
    
    /**
     * devuelve la infotmacion de un cliente 
     * 
     * @param ip la ip de este cliente
     * @return sus datos
     */
    public DatosCliente getDatosCliente (String ip){
        return _datosClientes.get(ip);
    }
}
