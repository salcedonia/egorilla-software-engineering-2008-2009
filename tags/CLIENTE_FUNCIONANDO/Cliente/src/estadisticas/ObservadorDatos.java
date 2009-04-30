/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package estadisticas;

/**
 *
 * @author Qiang
 */
public interface ObservadorDatos {
     void llegadaDatosDescarga(double longitud);
     void llegadaDatosSubida(double longitud);
     void llegadaFichero(int cantidad);

}
