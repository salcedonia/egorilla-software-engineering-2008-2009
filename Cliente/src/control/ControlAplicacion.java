package control;

import datos.Archivo;
import gestorDeFicheros.GestorCompartidos;
import gestorDeRed.GestorDeRed;
import gestorDeRed.TCP.GestorDeRedTCPimpl;
import java.io.*;
import mensajes.Mensaje;
import mensajes.serverclient.DatosCliente;
import mensajes.serverclient.PeticionConsulta;
import mensajes.serverclient.PeticionDescarga;
import mensajes.serverclient.RespuestaPeticionConsulta;
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
    private static PeticionConsulta _peticionConsulta;

    private static GestorDeRed<Mensaje> _red;
    private static GestorDescargas      _descargas;
    private static GestorEgorilla       _egorilla;
    
    
    public ControlAplicacion(int puerto, String compartidos){
        _red = new GestorDeRedTCPimpl<Mensaje>(puerto);
        this.compartidos(compartidos);
      _descargas = new GestorDescargas();
      _egorilla = new GestorEgorilla(_descargas, _red);
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
     * Indica si estamos conectados a un servidor o no.
     * 
     * @return Verdadero si estamos conectados a un servidor y falso en caso
     * contrario.
     */
    public boolean conectado() {
        return _conectado;
    }

    /**
     * Realiza la conexion a un servidor de este cliente.
     *
     * @throws java.io.IOException
     */
    public void conectar(String IP, int puerto) throws Exception {
////        
////        // primero, debemos activar el p2p
////        _red.registraReceptor(new ServidorP2PEgorilla(_egorilla,_descargas));
////        _red.comienzaEscucha();
//        
//        // dormimos 1 segundo el hilo para que pueda abrirse el socket y no haya
//        // problemas con las pruebas en local
//        Thread.sleep(1000);
        
        _egorilla.conectaServidor(IP, puerto);
  
        _conectado = true;  
    }

    /**
     * Cierra la conexion con el servidor.
     */
    public void close() {
   
        _egorilla.desconectar();
        
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
    public void consultar(String cad) {
        
       _egorilla.nuevaConsulta(cad);
    }

    /**
     * Da la orden para proceder a bajar un fichero.
     *
     * @param hash El identificador unico de este fichero.
     */
    public void bajar(String nmb,String hash) {
        _egorilla.nuevaDescarga(new Archivo(nmb, hash));
    }
}
