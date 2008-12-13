/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servidoregorilla.paquete;

import java.io.Serializable;

/**
 *
 * @author Pitidecaner
 */
public class DatosCliente implements Serializable, Peticion{

    public String nombreUsuario;
    public String ip;
    public int    puertoEscucha;

    public int getVersion() {
        return 1;
    }  
}
