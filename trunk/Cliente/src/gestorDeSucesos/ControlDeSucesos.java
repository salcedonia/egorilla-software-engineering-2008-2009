/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorDeSucesos;

import org.apache.log4j.Logger;

/**
 *
 * @author Qiang
 */
public class ControlDeSucesos {

    private static ControlDeSucesos instacia;
    private static final Logger log = Logger.getLogger(ControlDeSucesos.class);
    private final String RAIZ = "Mensaje de sucesos: ";

    private ControlDeSucesos() {
    }

    public static ControlDeSucesos dameInstancia() {
        if (instacia == null) {
            instacia = new ControlDeSucesos();
        }
        return instacia;
    }

    public void registrarError(int code) {
        log.info(RAIZ + Sucesos.dameMesaje(code));
    }

    public void registrarError(int code, String mensaje) {
        log.info(RAIZ + Sucesos.dameMesaje(code) + mensaje);
    }

    public void registrarError(int code, String mensaje, Throwable excepcion) {
        log.info(RAIZ + Sucesos.dameMesaje(code) + mensaje, excepcion);
    }

    public void registrarError(int code, String mensaje, Class path) {
        log.info(RAIZ + Sucesos.dameMesaje(code) + mensaje);
    }
}
