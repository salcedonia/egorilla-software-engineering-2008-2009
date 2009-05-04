package gui.grafica.buscador;

import datos.Archivo;
import peerToPeer.egorilla.GestorEgorilla;

/**
 * Controlador de la pestania de busquedas.
 * 
 * @author Javier Salcedo
 */
public class ControladorPanelBuscador {

    /**
     * Gestor eGorilla de la aplicacion.
     */
    private GestorEgorilla _gestorEGorilla;
    
    /**
     * Constructor de la clase ControladorPanelBuscador.
     * 
     * @param gestorEGorilla Gestor eGorilla de la aplicacion.
     */
    public ControladorPanelBuscador(GestorEgorilla gestorEGorilla) {
    
        _gestorEGorilla = gestorEGorilla;
    }
    
    /**
     * Realiza una petición al GestorEGorilla de una búsqueda de un fichero.
     * 
     * @param nombre Nombre del fichero a buscar.
     */
    public void peticionBuscarFichero(String nombre) {

        _gestorEGorilla.nuevaConsulta(nombre);
    }
    
    /**
     * Da la orden para proceder a peticionDescargarFichero un fichero.
     *
     * @param Archivo El archivo que se pretende descargar.
     */
    public void peticionDescargarFichero(Archivo archivo) {

        _gestorEGorilla.nuevaDescarga(archivo);
    }
    
    /**
     * Comprueba si estamos conectados al servidor.
     * 
     * @return Verdadero si estamos conectados al servidor y falso en caso contrario.
     */
    public boolean conectado() {

        return _gestorEGorilla.estaConectadoAServidor();
    }
    
    /**
     * Devuelve el GestorEGorilla de la aplicación.
     * 
     * @return El GestorEGorilla de la aplicación.
     */
    public GestorEgorilla getGestorEGorilla() {

        return _gestorEGorilla;
    }
}
