package main;

import gestorDeConfiguracion.ControlConfiguracionCliente;
import control.*;
import gestorDeConfiguracion.ControlConfiguracionClienteException;
import gestorDeFicheros.GestorCompartidos;
import gestorDeFicheros.GestorDisco;
import gestorDeRed.GestorDeRed;
import gestorDeRed.TCP.GestorDeRedTCPimpl;
import gui.consola.GUIConsola;
import gui.grafica.GUIGrafica;
import java.util.logging.Level;
import java.util.logging.Logger;
import mensajes.Mensaje;
import peerToPeer.descargas.GestorDescargas;
import peerToPeer.egorilla.GestorEgorilla;

/**
 * Clase que implemeta un test de prueba del servidor.
 * 
 * @author Luis Ayuso, Ivan Munsuri, Javier Salcedo
 */
public class Main {

    /**
     * Método main de la aplicacion Cliente eGorilla.
     * 
     * @param args Argumentos de la aplicación de la línea de comandos.
     */
    public static void main(String[] args) throws ControlConfiguracionClienteException {


        ControlConfiguracionCliente oCtrlConfigCliente = ControlConfiguracionCliente.obtenerInstancia("cliente.properties", "cliente_default.properties");
        int iPuerto = Integer.parseInt(oCtrlConfigCliente.obtenerPropiedad("Puerto"));

        GestorDeRed<Mensaje> gestorDeRed = new GestorDeRedTCPimpl<Mensaje>(iPuerto);
        GestorDisco gestorDeDisco = new GestorDisco();
        GestorDescargas gestorDeDescargas = new GestorDescargas(gestorDeDisco);
        GestorCompartidos gestorDeCompartidos = GestorCompartidos.getInstancia();
        gestorDeCompartidos.setGestorDisco(gestorDeDisco);
        GestorEgorilla gestorEGorilla = new GestorEgorilla(gestorDeDescargas, gestorDeRed);

//      String nombreDirectorio = "compartidos";

        //ControladorConsola controladorConsola = new ControladorConsola(gestorDeRed, gestorDeDescargas, gestorEGorilla);
        ControladorGrafica controladorGrafica = new ControladorGrafica(gestorDeRed, gestorDeDescargas, gestorEGorilla);

        // Mostramos la interfaz de consola
        try {
            //new GUIConsola(controladorConsola).mostrarMenu();
            new GUIGrafica(controladorGrafica);

        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
