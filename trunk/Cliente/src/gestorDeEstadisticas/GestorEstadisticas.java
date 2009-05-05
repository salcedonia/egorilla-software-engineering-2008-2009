/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorDeEstadisticas;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import org.apache.log4j.Logger;
import peerToPeer.descargas.ObservadorAlmacenDescargas;

/**
 * <p>Gestor de estadisticas: Es el encargado de almacenar y ordenar la canatidad de
 * datos recibidos y enviados para ofrecerlo a quien se los solicites como por
 * ejemplo la GUI. </p>
 * Datos almacenados:
 * <ul>
 *   <li>Se dispondras la informacin de los datos enviados y los datos descargados as como 
 *      la velocidades de subida y descarga.</li>
 *   <li>Para las velocidades se ofrecera los datos de la ltima hora en periodos de medio minuto
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
 *   Es importante realizar el ltimo paso de cerrar las estadisticas ya que con ello liberamos
 *   los recursos y hacemos persistente los datos,
 * @author Qiang
 */
public class GestorEstadisticas implements ObservadorAlmacenDescargas {

    protected static GestorEstadisticas intancia;
    AdministradorDescarga descarga;
    AdministradorSubida subida;
    protected final static String PATH = "temp/statistics";
    private static Logger log = Logger.getLogger(GestorEstadisticas.class);

    protected GestorEstadisticas() {
        DataInputStream fichero = null;
        try {
            File fila = new File(PATH);
            log.info(fila.getAbsolutePath());
            if (fila.exists()) {
                InputStream stream = new FileInputStream(fila);
                fichero = new DataInputStream(stream);
            }
            descarga = new AdministradorDescarga(fichero);
            subida = new AdministradorSubida(fichero);

        } catch (FileNotFoundException ex) {
            log.error( "Error al abrir el fichero de estadisticas", ex);
        } catch (IOException ex) {
            log.error("Error al cargar los datos de estadisticas", ex);
        } finally {
            try {
                if (fichero != null)
                    fichero.close();
            } catch (IOException ex) {
                log.error("Error al cerrar los datos de estadisticas", ex);
            }
        }
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
    public void inicioSesion() {
        descarga.inicioSesion();
        subida.inicioSesion();
    }

    /**
     * Pone a cero los datos guardados de la sesion y los datos globales.
     */
    public void reiniciarTodo() {
        descarga.reniciarSesion();
        subida.inicioSesion();

    }

    /**
     * Pone a cero los datos guardados de la sesion.
     */
    public void reniciarSesion() {
    }

    /**
     * Cierra el sistema gestor de estadisticas. Debe ser llamado al eliminar
     * el objeto.
     */
   public  void cerrar() {
        OutputStream stream = null;
        try {
            DataOutputStream fichero;
            stream = new FileOutputStream(PATH);
            fichero = new DataOutputStream(stream);
            descarga.cerrar(fichero);
            subida.cerrar(fichero);

        } catch (FileNotFoundException ex) {
            log.error("Error al abrir el fichero de estadisticas", ex);
        } catch (IOException ex) {
            log.error("Error al escribir los datos de estadisticas", ex);
        } finally {
            try {
                stream.close();
            } catch (IOException ex) {
                log.error("Error al cerrar el fichero de estadisticas", ex);
            }
        }
    }

    /**
     * Lista de velocidades medias recibidos durante la sesion.
     * @return
     */
   public  ArrayList<Double> getListaVelocidadMediaSubidaSesion() {
       subida.llegadaDatos(0);
        return subida.getListaVelocidadMediaSesion();
    }

    /**
     * Lista de velocidades medias recibidos durante la sesion.
     * @return
     */
   public ArrayList<Double> getListaVelocidadMediaBajadaSesion() {
       descarga.llegadaDatos(0);
        return descarga.getListaVelocidadMediaSesion();
    }

    /**
     * Numero de ficheros descargados en el global de las estadisticas.
     * @return
     */
    public int getFicherosDescargadosGlobal() {
        return descarga.getFicherosDescargadosGlobal();
    }

    /**
     * Numero de ficheros descargados durante la sesion.
     * @return
     */
    public int getFicherosDescargadosSesion() {
        return descarga.getFicherosDescargadosSesion();
    }

    /**
     * Velocidad de descarga actual.
     * @return
     */
    public double getVelocidadActualDescarga() {
        return descarga.getVelocidadActual();
    }

    /**
     * Velocidad de subida actual.
     * @return
     */
    public double getVelocidadActualSubida() {
        return subida.getVelocidadActual();
    }

    /**
     * Numero total de bytes descargados durante la sesion.
     * @return
     */
   public  double getTotalDatosDescargaSesion() {
        return descarga.getTotalDatosSesion();
    }

    public double getTotalDatosDescargaGlobal() {
        return descarga.getTotalDatosGlobal();
    }

    /**
     * Numero total de bytes subidos durante la sesion.
     * @return
     */
    public double getTotalDatosSubidaSesion() {
        return subida.getTotalDatosSesion();
    }

    public double getTotalDatosSubidaGlobal() {
        return subida.getTotalDatosGlobal();
    }

    public void llegadaDatosDescarga(double longitud) {
        descarga.llegadaDatos(longitud);
    }

    public void llegadaDatosSubida(double longitud) {
        subida.llegadaDatos(longitud);
    }

    @Override
    public void nuevaDescarga(String nombre, String hash, int tamanio) {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void fragmentoDescargado(String hash) {
     llegadaDatosDescarga(512);
    }
    
    public Date getFechaInicio() {
        return descarga.getFechaInicio();
    }

    @Override
    public void eliminarDescarga(String _hash) {
        // do nothing
    }

    @Override
    public void descargaCompleta(String hash) {
         descarga.llegadaFichero(1);
    }
}
