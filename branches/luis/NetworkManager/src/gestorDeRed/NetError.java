/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestorDeRed;

/**
 *
 * @author pitidecaner
 */
public class NetError extends Exception {

    private String _cad;

    public NetError(String cadena) {
        _cad = cadena;
    }

    public String getError(){
        return _cad;
    }
}
