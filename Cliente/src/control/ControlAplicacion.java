package control;

import datos.Archivo;
import gestorDeFicheros.GestorCompartidos;
import gestorDeRed.GestorDeRed;
import gestorDeRed.TCP.GestorDeRedTCPimpl;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import mensajes.Mensaje;
import mensajes.serverclient.DatosCliente;
import mensajes.serverclient.PeticionConsulta;
import mensajes.serverclient.PeticionDescarga;
import mensajes.serverclient.RespuestaPeticionConsulta;
import mensajes.serverclient.RespuestaPeticionDescarga;
import peerToPeer.descargas.GestorDescargas;
import peerToPeer.egorilla.GestorEgorilla;
import peerToPeer.egorilla.ServidorP2PEgorilla;

/**
 * Un control rudimentario para la aplicacion. Responde a las llamadas de 
 * la interfaz de forma imperativa.
 * 
 * @author Luis Ayuso, Ivan Munsuri, Javier Salcedo
 */
public class ControlAplicacion {

    // ATRIBUTOS
    private static boolean _conectado = false;
    private static GestorCompartidos _gestorCompartidos = null;
    private static DatosCliente _datosCliente;
    private static PeticionConsulta _peticionConsulta;
    private static PeticionDescarga _peticionDescarga;

    
    private static GestorDeRed<Mensaje> _red = new GestorDeRedTCPimpl<Mensaje>(4545);
    private static GestorDescargas      _descargas = new GestorDescargas();
    private static GestorEgorilla       _egorilla = new GestorEgorilla(_descargas, _red);
    
    
    /**
     * 
     */
    public ControlAplicacion() {
    }

    /**
     * Configura el gestor de archivos compartidos del cliente a partir del 
     * nombre del directorio que el usuario del Cliente eGorilla comparte.
     * 
     * @param nombreDirectorio
     */
    public static void compartidos(String nombreDirectorio) {
        try {
            _gestorCompartidos = new GestorCompartidos(new File(nombreDirectorio));
        } catch (IOException ex) {
            // hacer algo, como poner un directorio por defecto (no)
            // tal vez lo detecte dentro como no directorio o comprobar path dentro.
        }
    }

    /**
     * Indica si estamos conectados a un servidor o no.
     * 
     * @return Verdadero si estamos conectados a un servidor y falso en caso
     * contrario.
     */
    public static boolean conectado() {
        return _conectado;
    }

    /**
     * Realiza la conexion a un servidor de este cliente.
     *
     * @throws java.io.IOException
     */
    public static void conectar(String IP, int puerto) throws IOException {
        
        _datosCliente = new DatosCliente();
        _datosCliente.setNombreUsuario("dePruebas");
        _datosCliente.setPuertoEscucha(4545);
        System.out.println("Envio nombre-user <" + _datosCliente.getNombreUsuario() + "> y su port-Listen <" + _datosCliente.getPuertoEscucha() + ">");
        _egorilla.addMensajeParaEnviar(_datosCliente);

//        // Mandamos la _lista de archivos asociada al cliente
//        System.out.println("Se mando info de <" + _gestorCompartidos.getArchivosCompartidos().size() + "> archivos compartidos");
//        _conexionPeer.enviarObjeto(_gestorCompartidos.getArchivosCompartidos());

        // ademas, debemos activar el p2p
        _red.registraReceptor(new ServidorP2PEgorilla(_egorilla,_descargas));
        _red.comienzaEscucha();
        
        _conectado = true;
    }

    /**
     * Cierra la conexion con el servidor.
     */
    public static void close() {
   
        
        
        // tambien acabamos con el p2p
        _red.terminaEscucha();
        _conectado = false;
    }

    /**
     * Pregunta al servidor por algun fichero con algunos datos proporcionados por
     * el cliente.
     *
     * //TODO: buscar algo mas que por el nombre
     *
     * @param cad nombre de fichero buscado
     */
    public static void consultar(String cad) {
        
        RespuestaPeticionConsulta respuestaConsulta = null;
        _peticionConsulta = new PeticionConsulta(cad);

        _egorilla.addMensajeParaEnviar(_peticionConsulta);

    }

    /**
     * Da la orden para proceder a bajar un fichero.
     *
     * @param hash El identificador unico de este fichero.
     */
    public static void bajar(String nmb,String hash) {
        _egorilla.nuevaDescarga(new Archivo(nmb, hash));
    }
}
