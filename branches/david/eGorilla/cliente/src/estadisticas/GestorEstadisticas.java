/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package estadisticas;

import java.util.Date;
import java.util.List;

/**
 * <p>Gestor de estadisticas: Es el encargado de almacenar y ordenar la canatidad de
 * datos recibidos y enviados para ofrecerlo a quien se los solicites como por
 * ejemplo la GUI. </p>
 * Datos almacenados:
 * Se dispondras de dos lineas de datos, una que ira recogiendo los datos globales de todas
 * las sesiones y otra que recogera los datos de la sesion actual.
 *
 * Datos globales:
 * <ul>
 *      <li>Se almacenan los datos registrados por periodos de dias de los ultimos 30 dias.</li>
 *      <li>Para los datos anteriores a los ultimos 30 dias solo re almacena el global
 *          de datos registrados</li>
 * </ul>
 *
 * Datos de sesion:
 * <ul>
 *      <li>Se almacenan los datos regitrados por periodos de 1 minuto durante los ultimos 10 minutos</li>
 *      <li>Se almacenan los datos registrados por periodos de 10 minutos durante las ultimas 24 hroas</li>
 *      <li>Se almacenan los datos registrados por periodos de 1 dia durante los ultimos 30 dias.
 *      <li>Los datos anteriores a los ultimos 30 dias solo se almacenara el total de volumen de datos registrados</li>
 * </ul>
 * @author Qiang
 */
public interface GestorEstadisticas {
    /**
     * Inicia el gestor de estadisticas para poder comenzar a almacenar los
     * datos sobre las estadisticas.
     */
    void inicioSesion();
    /**
     * Pone a cero los datos guardados de la sesion y los datos globales.
     */
    void reiniciarTodo();
    /**
     * Pone a cero los datos guardados de la sesion.
     */
    void reniciarSesion();
    /**
     * Cierra el sistema gestor de estadisticas. Debe ser llamado al eliminar
     * el objeto.
     */
    void cerrar();
    /**
     * Devuelve una lista con los datos recibido entre las dos fechas indicadas
     * los datos devueltos son en intervalos de dias.
     * @param inicio fecha desde la cual comienza los datos a recibir.
     * @param fin fecha hasta la cual finaliza los datos a recibir.
     * @return Lista de bytes recibidos  en intervalo de dias.
     */
    List<Long> getDatosEntreDosFechas(Date inicio,Date fin);
    /**
     *  Bytes recibidos  durante el dia indicado.
     * @param dia
     * @return
     */
    long getDatosUnaFecha(Date dia);
    /**
     * Lista de bytes recibidos durante la sesion.
     * @return
     */
    List<Long> getDatosSesion();
    /**
     * Numero de ficheros descargados en el global de las estadisticas.
     * @return
     */
    int  getFicherosDescargadosGlobal();
    /**
     * Numero de ficheros descargados durante la sesion.
     * @return
     */
    int  getFicherosDescargadosSesion();
    /**
     * Velocidad de descarga actual.
     * @return
     */
    double getVelocidadActual();
    /**
     * Numero total de bytes descargados durante la sesion.
     * @return
     */
    long getTotalDatosSesion();
}
