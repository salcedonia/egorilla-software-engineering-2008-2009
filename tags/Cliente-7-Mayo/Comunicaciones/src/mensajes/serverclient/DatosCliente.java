/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

package mensajes.serverclient;


import java.io.Serializable;
import mensajes.Mensaje;
import mensajes.TipoMensaje;

/*****************************************************************************/
/**
 * Clase que gestiona todos los datos relativos al cliente.
 * 
 * @author Pitidecaner
 * @author Salcedonia
 */
public class DatosCliente implements Serializable, Mensaje{

    // ATTRIBUTOS
    private String _nombreUsuario;
    private String _IP;
    private int    _puertoEscucha;

    /*****************************************************************************/
    /**
     * Devuelve la direccion IP asociada.
     * 
     * @return La direcci�n IP asociada.
     */
    public String getIP() {
        return _IP;
    }

    /*****************************************************************************/
    /**
     * Establece la direcci�n IP del cliente a valor IP.
     * @param IP Nuevo valor de la direcci�n IP a establecer.
     */
    public void setIP(String IP) {
        _IP = IP;
    }

    /*****************************************************************************/
    /**
     * Devuelve el nombre de usuario.
     * @return El nombre de usuario.
     */
    public String getNombreUsuario() {
        return _nombreUsuario;
    }

    /*****************************************************************************/
    /**
     * Establece el nombre de usuario a valor nombreUsuario.
     * @param nombreUsuario El nuevo valor del nombre de usuario del cliente.
     */
    public void setNombreUsuario(String nombreUsuario) {
        _nombreUsuario = nombreUsuario;
    }

    /*****************************************************************************/
    /**
     * Devuelve el puerto de escucha del cliente.
     * @return El puerto de escucha del cliente.
     */
    public int getPuertoEscucha() {
        return _puertoEscucha;
    }

    /*****************************************************************************/
    /**
     * Establece el puerto de escucha del cliente a valor puertoEscucha.
     * @param _puertoEscucha Nuevo valor del puerto de escucha del cliente. 
     */
    public void setPuertoEscucha(int puertoEscucha) {
        _puertoEscucha = puertoEscucha;
    }

    public TipoMensaje getTipoMensaje() {
        return TipoMensaje.DatosCliente;
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
