package main;

import gestorDeConfiguracion.ControlConfiguracionCliente;
import control.*;
import gestorDeConfiguracion.ControlConfiguracionClienteException;
import gestorDeConfiguracion.PropiedadCliente;
import gestorDeFicheros.GestorCompartidos;
import gestorDeFicheros.GestorDisco;
import gestorDeRed.GestorDeRed;
import gestorDeRed.TCP.GestorDeRedTCPimpl;
import gestorDeSucesos.ControlDeSucesos;
import gui.consola.GUIConsola;
import gui.grafica.GUIGrafica;
import jargs.gnu.CmdLineParser;
import jargs.gnu.CmdLineParser.IllegalOptionValueException;
import jargs.gnu.CmdLineParser.UnknownOptionException;
import java.io.IOException;
import mensajes.Mensaje;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import peerToPeer.egorilla.GestorEgorilla;

/**
 * Clase principal de la aplicación Cliente eGorilla
 * 
 * @author Luis Ayuso, Ivan Munsuri, Javier Salcedo
 */
public class Main {

    private static GestorDeRed<Mensaje> _gestorDeRed;
    private static GestorEgorilla _gestorEGorilla;
    private static GestorCompartidos _gestorDeCompartidos;
    private static GestorDisco _gestorDeDisco;
    private static ControlDeSucesos _controlDeSucesos;

    /**
     * Método main de la aplicacion Cliente eGorilla.
     * 
     * @param args Argumentos de la aplicación de la línea de comandos.
     */
    public static void main(String[] args) throws ControlConfiguracionClienteException {

        //TODO Configurar los logs.
        PropertyConfigurator.configure("log4j.properties");
        Logger log = Logger.getLogger(Main.class);

        CmdLineParser parser = new CmdLineParser();
        CmdLineParser.Option gui = parser.addStringOption('i', "interface");

        try {

            parser.parse(args);

            String[] masArgumentos = parser.getRemainingArgs();
            
            if (masArgumentos.length > 0) {

                System.err.println("Número de argumentos incorrecto.");
            }
            else{
            
                String modo = (String) parser.getOptionValue(gui);

                ControlConfiguracionCliente controlConfiguracionCliente = ControlConfiguracionCliente.obtenerInstancia("cliente.properties", "cliente_default.properties");
                int puertoDeEscuchaCliente = Integer.parseInt(controlConfiguracionCliente.obtenerPropiedad(PropiedadCliente.PUERTO.obtenerLiteral()));

                crearModulosCliente(puertoDeEscuchaCliente, modo);
            }
        } catch (IllegalOptionValueException ex) {
            log.info("Error no indentificado", ex); 
        } catch (UnknownOptionException ex) {
            log.info("Error no indentificado", ex);
        } catch (Exception ex) {
            log.info("Error no indentificado", ex);
        }
    }

    /**
     * Crea todo los componentes de la aplicación.
     * 
     * @param puertoDeEscuchaCliente Puerto de escucha del siguiente. 
     * @param modo Modo de la GUI
     * @throws java.lang.Exception
     */
    private static void crearModulosCliente(int puertoDeEscuchaCliente, String modo) throws Exception {

        _gestorDeRed = new GestorDeRedTCPimpl<Mensaje>(puertoDeEscuchaCliente);
        _gestorDeDisco = new GestorDisco();
        _gestorDeCompartidos = GestorCompartidos.getInstancia();
        _gestorDeCompartidos.setGestorDisco(_gestorDeDisco);
        _gestorEGorilla = new GestorEgorilla(_gestorDeRed,_gestorDeDisco);
        _controlDeSucesos = ControlDeSucesos.dameInstancia();

        crearTipoGUI(modo, _gestorDeRed, _gestorEGorilla);
    }

    /**
     * Crea la GUI de la aplicación en función de los parámetros de entrada
     * de la aplicación.
     * 
     * @param modo Modo seleccionado desde la línea de comandos
     * @param gestorDeRed Gestor de Red de la aplicación.
     * @param gestorEGorilla GestorEGorilla de la aplicación.
     */
    private static void crearTipoGUI(String modo, GestorDeRed gestorDeRed, GestorEgorilla gestorEGorilla) throws IOException, ControlConfiguracionClienteException, Exception{

        if (modo.equalsIgnoreCase("grafico") || modo.equals("")) {

            ControladorGrafica controladorGrafica = new ControladorGrafica(gestorDeRed, gestorEGorilla);
            new GUIGrafica(controladorGrafica);
        }
        else 
            if (modo.equalsIgnoreCase("consola")) {
                
                ControladorConsola controladorConsola = new ControladorConsola(gestorDeRed, gestorEGorilla);
                new GUIConsola(controladorConsola);
            } else
                System.err.println ("Los parámetros introducidos no son válidos.");
    }
}
