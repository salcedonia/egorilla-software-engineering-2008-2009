/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servidoregorilla.paquete;

import java.io.Serializable;

/*****************************************************************************/
/**
 * Clase que gestiona todos los datos relativos al cliente.
 * 
 * @author Pitidecaner
 * @author Salcedonia
 */
public class DatosCliente implements Serializable, Peticion{

    // ATTRIBUTOS
    private String _nombreUsuario;
    private String _IP;
    private int    _puertoEscucha;

    /*****************************************************************************/
    /**
     * Devuelve la versión que usa el cliente.
     * 
     * @return Por defecto es la 1.
     */
    public int getVersion() {
        return 1;
    }

    /*****************************************************************************/
    /**
     * Devuelve la direccion IP asociada.
     * 
     * @return La dirección IP asociada.
     */
    public String getIP() {
        return _IP;
    }

    /*****************************************************************************/
    /**
     * Establece la dirección IP del cliente a valor IP.
     * @param IP Nuevo valor de la dirección IP a establecer.
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
}
