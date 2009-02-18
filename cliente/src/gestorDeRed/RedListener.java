/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestorDeRed;

/**
 * Todo artefacto que pretende recibir paquetes de la red, debe inplementar esta
 * interfaz.
 *
 *
 * @author pitidecaner
 */
public interface RedListener {

    /**
     * Se le pasa un mensaje recibido de la red.
     * 
     * @param msj el mensaje en cuenstion
     * @return si este mensaje es para este listener devolvera cierto.
     */
    public boolean recibeMensaje(Mensaje msj);
}
