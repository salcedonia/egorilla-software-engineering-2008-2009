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
public class DownloadOrder implements Peticion,Serializable{
    public String hash;

    public DownloadOrder(String h){
        hash = h;
    }
    
    public int getVersion() {
        return 3;
}
}
