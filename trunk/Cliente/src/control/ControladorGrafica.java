package control;

import gestorDeRed.GestorDeRed;
import mensajes.Mensaje;
//import peerToPeer.descargas.GestorDescargas;
import peerToPeer.egorilla.GestorEgorilla;

/**
 * Controlador de la aplicación en modo gráfico. 
 * Se encarga de recibir los eventos producidos en la interfaz y se los 
 * notifica a la lógica de la aplicación para que ésta actúe en consecuencia.
 * 
 * @author Javier Salcedo Gómez
 */
public class ControladorGrafica {

    /**
     * Gestor de red de la aplicación.
     */
    private GestorDeRed<Mensaje> _gestorDeRed;
    /**
     * Gestor de descargas de la aplicación.
     */
    //private GestorDescargas _gestorDeDescargas;
    /**
     * Gestor eGorilla.
     */
    private GestorEgorilla _gestorDeEgorilla;
    
    
    /**
     * Constructor de la clase ControladorGrafica.
     * 
     * @param gestorDeRed Gestor de red de la aplicación.
     * @param gestorDeDescargas Gestor de descargas de la aplicación.
     * @param gestorEgorilla Gestor eGorilla.
     */
    public ControladorGrafica(GestorDeRed<Mensaje> gestorDeRed, GestorEgorilla gestorEgorilla){
    
        _gestorDeRed = gestorDeRed;
        //_gestorDeDescargas = gestorDeDescargas;
        _gestorDeEgorilla = gestorEgorilla;
    }
    
    public void peticionConexionAServidor(String IP, int puerto) throws Exception {

        _gestorDeEgorilla.conectaServidor(IP, puerto);
    }
    public void peticionDeDesconexionDeServidor() {

        _gestorDeEgorilla.desconectar();

        // tambien acabamos con el p2p
        _gestorDeRed.terminaEscucha();
    }
}
