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
 * Se dispondras de dos lineas de datos, una que ira recogiendo los datos globales de todas
 * las sesiones y otra que recogera los datos de la sesion actual.
 * @author Qiang
 */
public class  GestorEstadisticas  implements ObservadorDatos {
    
     
    GestorEstadisticas intancia = new GestorEstadisticas();
    AdministradorDescarga descarga;
    AdministradorSubida subida;
    private GestorEstadisticas() {
        descarga = new AdministradorDescarga();
        subida = new AdministradorSubida();
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
        return getFicherosDescargadosSesion();
    }
    /**
     * Velocidad de descarga actual.
     * @return
     */
    double getVelocidadActualDescarga() {
        //TODO: Mejorar esto.
        return descarga.getVelocidadActual();
    }
    
    /**
     * Velocidad de subida actual.
     * @return
     */
    double getVelocidadActualSubida() {
        //TODO: Mejorar esto.
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
