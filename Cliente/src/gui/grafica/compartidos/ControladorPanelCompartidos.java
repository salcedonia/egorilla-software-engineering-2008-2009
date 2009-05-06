package gui.grafica.compartidos;

import gestorDeFicheros.GestorCompartidos;
import mensajes.serverclient.ListaArchivos;
import peerToPeer.egorilla.GestorEgorilla;

/**
 * Controlador del panel de compartidos. Recibe peticiones desde el panel de 
 * compartidos ante acciones de los usuarios y se las comunica al modelo.
 * 
 * @author Javier Salcedo, Mercedes Bernal
 */
public class ControladorPanelCompartidos {

    /**
     * GestorEGorilla de la aplicacion.
     */
    private GestorEgorilla _gestorEGorilla;
    
    /**
     * Constructor de la clase ControladorPanelCompartidos.
     * 
     * @param gestorEGorilla Gestor eGorilla de la aplicacion.
     */
    public ControladorPanelCompartidos(GestorEgorilla gestorEGorilla){
    
        _gestorEGorilla = gestorEGorilla;
    }
    
    /**
     * Devuelve la lista de archivos compartidos del cliente. Para ello pregunta
     * al Gestor de compartidos que es el que tiene toda la informacion necesaria.
     * 
     * @return La lista de archivos compartidos del cliente.
     */
    public ListaArchivos peticionListarArchivosCompartidos(){
    
        return GestorCompartidos.getInstancia().getArchivosCompartidos();       
    }
}
