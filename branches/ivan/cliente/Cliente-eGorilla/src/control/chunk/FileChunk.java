/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package control.chunk;

import java.io.Serializable;

/**
 *
 * un calchito de un fichero, para enviarlo por el frio y oscuro internet
 *
 * @author Pitidecaner
 */
public class FileChunk implements Serializable{
    public boolean valido;
    public Byte[] buffer;
}
