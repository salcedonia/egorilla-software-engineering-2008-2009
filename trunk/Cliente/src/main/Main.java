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
import jargs.gnu.CmdLineParser;
import jargs.gnu.CmdLineParser.IllegalOptionValueException;
import jargs.gnu.CmdLineParser.UnknownOptionException;
import mensajes.Mensaje;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
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
        CmdLineParser parser = new CmdLineParser();
        CmdLineParser.Option gui = parser.addStringOption('i', "interface");
        //TODO Configurar los logs.
 	PropertyConfigurator.configure("log4j.properties");
        Logger log =  Logger.getLogger(Main.class);
        log.info("Muchas cosas mas");
        try {

            parser.parse(args);

            String[] errors = parser.getRemainingArgs();
            if (errors.length > 0) {            //TODO something}
            }
            String modo = (String) parser.getOptionValue(gui);


            ControlConfiguracionCliente oCtrlConfigCliente = ControlConfiguracionCliente.obtenerInstancia("cliente.properties", "cliente_default.properties");
            int iPuerto = Integer.parseInt(oCtrlConfigCliente.obtenerPropiedad("Puerto"));

            GestorDeRed<Mensaje> gestorDeRed = new GestorDeRedTCPimpl<Mensaje>(iPuerto);
            GestorDisco gestorDeDisco = new GestorDisco();
            GestorDescargas gestorDeDescargas = new GestorDescargas(gestorDeDisco);
            GestorCompartidos gestorDeCompartidos = GestorCompartidos.getInstancia();
            gestorDeCompartidos.setGestorDisco(gestorDeDisco);
            GestorEgorilla gestorEGorilla = new GestorEgorilla(gestorDeDescargas, gestorDeRed);


            if (modo.equalsIgnoreCase("consola")) {
                ControladorConsola controladorConsola = new ControladorConsola(gestorDeRed, gestorDeDescargas, gestorEGorilla);
                new GUIConsola(controladorConsola);
            } else if (modo.equalsIgnoreCase("grafico") || modo.equals("")) {
                ControladorGrafica controladorGrafica = new ControladorGrafica(gestorDeRed, gestorDeDescargas, gestorEGorilla);
                new GUIGrafica(controladorGrafica);
            } else {
                throw new Exception("Parametro introducidos no valido");
            }
        } catch (IllegalOptionValueException ex) {
        } catch (UnknownOptionException ex) {
            
        }catch(Exception ex) {
            log.info("Error no indentificado", ex);
        }
}

}
