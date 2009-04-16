package control;

import datos.Archivo;
import gestorDeRed.GestorDeRed;
import java.io.*;
import mensajes.Mensaje;
import peerToPeer.descargas.GestorDescargas;
import peerToPeer.egorilla.GestorEgorilla;

/**
 * Un control rudimentario para la aplicacion. Responde a las llamadas de 
 * la interfaz de forma imperativa.
 * 
 * implementa la interfacee observador gestor egorilla para estar al tanto de
 * los eventos
 * 
 * @author Luis Ayuso, Ivan Munsuri, Javier Salcedo, Jose Miguel Guerrero
 */
public class ControlAplicacion {

    /**
     * Gestor de red de la aplicación.
     */
    private GestorDeRed<Mensaje> _gestorDeRed;
    /**
     * Gestor de descargas de la aplicación.
     */
    private GestorDescargas _gestorDeDescargas;
    /**
     * Gestor eGorilla.
     */
    private GestorEgorilla _gestorDeEgorilla;

    /**
     * Constructor de la clase ControlAplicacion.
     * 
     * @param gestorDeRed Gestor de red.
     * @param gestorDeDescargas Gestor de descargas.
     * @param gestorEgorilla Gestor eGorilla.
     */
    public ControlAplicacion(GestorDeRed<Mensaje> gestorDeRed, GestorDescargas gestorDeDescargas, GestorEgorilla gestorEgorilla) {
        
        _gestorDeRed = gestorDeRed;
        _gestorDeDescargas = gestorDeDescargas;
        _gestorDeEgorilla = gestorEgorilla;
    }

    /**
     * Configura el gestor de archivos compartidos del cliente a partir del 
     * nombre del directorio que el usuario del Cliente eGorilla comparte.
     * 
     * @param nombreDirectorio
     */
    private void compartidos(String nombreDirectorio) {
        // TODO: inicializa el gestor de compartidos o lo que sea
    }

    /**
     * Realiza la conexion a un servidor de este cliente.
     *
     * @throws java.io.IOException
     */
    public void peticionConexionAServidor(String IP, int puerto) throws Exception {

        _gestorDeEgorilla.conectaServidor(IP, puerto);
    }

    /**
     * Cierra la conexion con el servidor.
     */
    public void peticionDeDesconexionDeServidor() {

        _gestorDeEgorilla.desconectar();

        // tambien acabamos con el p2p
        _gestorDeRed.terminaEscucha();
    }

    /**
     * Pregunta al servidor por algun fichero con algunos datos proporcionados por
     * el cliente.
     *
     * //TODO: buscar algo mas que por el nombre
     *
     * @param cad nombre de fichero buscado
     */
    public void consultar(String cad) {

        _gestorDeEgorilla.nuevaConsulta(cad);
    }

    /**
     * Da la orden para proceder a bajar un fichero.
     *
     * @param hash El identificador unico de este fichero.
     */
    public void bajar(String nmb, String hash) {
        
        _gestorDeEgorilla.nuevaDescarga(new Archivo(nmb, hash));
    }
}
