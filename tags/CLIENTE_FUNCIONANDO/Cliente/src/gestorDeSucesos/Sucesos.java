/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestorDeSucesos;

/**
 *
 * @author Qiang
 */
public class Sucesos {

    public static final int ERROR_NO_INDENTIFICADO = 100;
    public static final int ERROR_RED = 101;
    public static final int ERROR_DISCO = 102;
    public static String dameMesaje(int code) {
        switch (code)  {
          case ERROR_NO_INDENTIFICADO : return "Error no identificado";
          case ERROR_RED : return "Error en la red del sistema";
          case ERROR_DISCO : return "Error en disco";
        }
        return "Error no identificado";
    }
}
