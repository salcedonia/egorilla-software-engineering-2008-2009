/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package estadisticas;

import java.util.List;

/**
 * <p>Gestor de estadisticas: Es el encargado de almacenar y ordenar la canatidad de
 * datos recibidos y enviados para ofrecerlo a quien se los solicites como por
 * ejemplo la GUI. </p>
 * Datos almacenados:
 * <ul>
 *   <li>Se dispondras la información de los datos enviados y los datos descargados así como 
 *      la velocidades de subida y descarga.</li>
 *   <li>Para las velocidades se ofrecera los datos de la última hora en periodos de medio minuto
 *       por lo tanto estas variables se mostraran de forma discreta aunque se tendra en cuenta todo
 *       el rango de tiempo. La media de velocidades se calculara por defecto para periodos de 5 minutos;
 *   </li>   
 * </ul>
 * Para el uso del modulo de estadisticas :
 *  
 *  <ul>
 *   <li> 
 *       Creamos una pedimos una instancia del modulo instancia = GestorEstadisticas.getInstance()</li>
 *   <li>
 *       Iniciamos sesion:    instancia.inicioSesion();
 *   </li>   
 *   <li>
 *       Al finalizar cerramos: instancia.cerrar() 
 *   <li>   
 *  </ul>
 *   Es importante realizar el último paso de cerrar las estadisticas ya que con ello liberamos
 *   los recursos y hacemos persistente los datos,
 * @author Qiang
 */
public class  GestorEstadisticas  implements ObservadorDatos {
    
     
    protected static GestorEstadisticas intancia;
    AdministradorDescarga descarga;
    AdministradorSubida subida;
    protected  GestorEstadisticas() {
        descarga = new AdministradorDescarga();
        subida = new AdministradorSubida();
    }
    /**
     *  Devuelve la instacia del Gestor de estadiscticas activo.
     * @return Gestor de estadisticas.
     */
    static public GestorEstadisticas getInstacia() {
        if (intancia == null) {
           intancia = new GestorEstadisticas();
        }
        return intancia;
    }
    /**
     * Inicia el gestor de estadisticas para poder comenzar a almacenar los
     * datos sobre las estadisticas.
     */
    void inicioSesion(){
        descarga.inicioSesion();
        subida.inicioSesion();
    }
    /**
     * Pone a cero los datos guardados de la sesion y los datos globales.
     */
    void reiniciarTodo(){
        descarga.reniciarSesion();
        subida.inicioSesion();
    
    }
    /**
     * Pone a cero los datos guardados de la sesion.
     */
    void reniciarSesion(){
    }
    /**
     * Cierra el sistema gestor de estadisticas. Debe ser llamado al eliminar
     * el objeto.
     */
    void cerrar(){
        descarga.cerrar();
        subida.cerrar();
    }
    

    /**
     * Lista de velocidades medias recibidos durante la sesion.
     * @return
     */
    List<Double> getListaVelocidadMediaSubidaSesion() {
        return subida.getListaVelocidadMediaSesion();
    }
    
    /**
     * Lista de velocidades medias recibidos durante la sesion.
     * @return
     */
    List<Double> getListaVelocidadMediaBajadaSesion() {
        return descarga.getListaVelocidadMediaSesion();
    }
    /**
     * Numero de ficheros descargados en el global de las estadisticas.
     * @return
     */
    int  getFicherosDescargadosGlobal(){
        return descarga.getFicherosDescargadosGlobal();
    }
    /**
     * Numero de ficheros descargados durante la sesion.
     * @return
     */
    int  getFicherosDescargadosSesion(){
        return descarga.getFicherosDescargadosSesion();
    }
    /**
     * Velocidad de descarga actual.
     * @return
     */
    double getVelocidadActualDescarga() {
        return descarga.getVelocidadActual();
    }
    
    /**
     * Velocidad de subida actual.
     * @return
     */
    double getVelocidadActualSubida() {
        return subida.getVelocidadActual();
    }
            
    /**
     * Numero total de bytes descargados durante la sesion.
     * @return
     */
    double getTotalDatosDescargaSesion(){
        return descarga.getTotalDatosSesion();
    }
    
    double getTotalDatosDescargaGlobal() {
        return descarga.getTotalDatosGlobal();
    }
    /**
     * Numero total de bytes subidos durante la sesion.
     * @return
     */
    double getTotalDatosSubidaSesion(){
        return subida.getTotalDatosSesion();
    }
    
    double getTotalDatosSubidaGlobal() {
        return subida.getTotalDatosGlobal();
    }

    public void llegadaFichero(int cantidad) {
        descarga.llegadaFichero(cantidad);
    }

    public void llegadaDatosDescarga(double longitud) {
        descarga.llegadaDatos(longitud);
    }

    public void llegadaDatosSubida(double longitud) {
        subida.llegadaDatos(longitud);
    }
}
