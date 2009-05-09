package gui.grafica.servidores;

import gestorDeConfiguracion.InfoServidor;

/**
 * Interfaz que implementa los metodos necesarios para que los observadores que
 * la implementan se enteren de los eventos producidos sobre la clase PanelServidores.
 * 
 * @author Javier Salcedo
 */
public interface ObservadorPanelServidores {

    /**
     * Avisa que se ha seleccionado una fila en el panel de servidores.
     * 
     * @param servidorSeleccionado Informacion del servidor seleccionado.
     */
    public void servidorSeleccionado(InfoServidor servidorSeleccionado);
}
