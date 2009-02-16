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
     void llegadaDatos(long longitud);
     void llegadaFichero(int cantidad);

}
