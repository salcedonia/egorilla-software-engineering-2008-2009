/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mensajes.serverclient;

import java.io.Serializable;
import mensajes.Mensaje;
import mensajes.TipoMensaje;

/*****************************************************************************/
/**
 * Clase que implementa los m�todos necesarios para la gesti�n de las consultas
 * de descarga que los clientes realizan al servidor.
 * 
 * @author Pitidecaner
 * @author Salcedonia
 */
public class PeticionDescarga implements Mensaje, Serializable{
    
    // ATRIBUTOS
    private String _hash;
    private String _nombre;

/*****************************************************************************/
    /**
     * Constructor de la clase DownloadOrder.
     * 
     * @param h Hash asociado.
     */
    public PeticionDescarga(String n,String h){
        _nombre = n;
        _hash = h;
    }
    

/*****************************************************************************/
    /**
     * Devuelve el hash asociado al archivo de descarga asociado.
     * 
     * @return El hash asociado al archivo de descarga asociado.
     */
    public String getHash() {
        return _hash;
    }

/*****************************************************************************/
    /**
     * Establece el valor de hash a valor hash.
     * 
     * @param hash Nuevo valor a establecer.
     */
    public void setHash(String hash) {
        _hash = hash;
    }

    public String getNombre(){
        return _nombre;
    }
    
    public void setNombre(String nmb){
        _nombre = nmb;
    }
    
    public TipoMensaje getTipoMensaje() {
        return TipoMensaje.PeticionDescarga;
    }
    
    private String _destino;
    private int    _puerto;
    
    public void setDestino(String destino, int puerto) {
        _destino = destino;
        _puerto  = puerto;
    }
    public String ipDestino() {
        return _destino;
    }
    public int puertoDestino() {
        return _puerto;
    }
}
