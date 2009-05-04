package gui.grafica.servidores;

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
     * @param direccionIP Direccion IP del servidor seleccionado.
     * @param puerto Puerto del servidor seleccionado.
     */
    public void servidorSeleccionado(String direccionIP, Integer puerto);
}
