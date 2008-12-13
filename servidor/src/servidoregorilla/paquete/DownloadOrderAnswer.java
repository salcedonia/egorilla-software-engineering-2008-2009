/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servidoregorilla.paquete;

import java.io.Serializable;



/**
 *
 * @author pitidecaner
 */
public class DownloadOrderAnswer implements Serializable{
    public DatosCliente[] lista;

    public DownloadOrderAnswer(DatosCliente[] l) {
       lista = l;
    }
    
}
