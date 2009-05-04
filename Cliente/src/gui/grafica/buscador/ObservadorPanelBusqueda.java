package gui.grafica.buscador;

import datos.Archivo;

/**
 * Interfaz que implementa los metodos necesarios para que los observadores que
 * la implementan se enteren de los eventos producidos sobre la clase PanelBusquedas.
 * 
 * @author Javier Salcedo
 */
public interface ObservadorPanelBusqueda {

    /**
     * Avisa que se ha seleccionado una fila en el panel de busquedas.
     * 
     * @param archivo Archivo seleccionado.
     */
    public void archivoSeleccionado(Archivo archivo);
}
