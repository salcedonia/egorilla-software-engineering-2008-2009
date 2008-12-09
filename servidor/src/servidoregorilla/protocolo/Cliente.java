/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servidoregorilla.protocolo;

import Networking.PeerConn;
import servidoregorilla.Datos.ListaArchivos;
import servidoregorilla.Datos.TablaClientes;
import java.io.IOException;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que implementa un hilo de ejecución que se expande por parte del 
 * servidor cuando alguien se identifica.
 * Mantiene los datos necesarios para la comunicación con un cliente. 
 * Para empezar comenzará un hilo de ejecución que recibirá todos los datos
 * que el cliente envía al conectar, estos son:
 * 
 *      - Nombre del usuario.
 *      - Puerto por el que el usuario escucha a otros clientes.
 *      - Lista de ficheros compartidos que tiene el cliente.
 *
 * @author pitidecaner
 * @author Salcedonia
 */
public class Cliente extends Thread implements Protocolo{
    
    // ATRIBUTOS
    private String _nombre;
    private InetAddress _IP;
    private int _puerto;
    private PeerConn _conexion;
    private ListaArchivos _listaArchivos;
    private TablaClientes _tablaClientes;
    
    /**
     * Constructor de la clase Cliente. Almacenamos los datos proporcionados
     * por el servidor al conectarse.
     * 
     * @param conexion Conexión recién abierta con el cliente.
     * @param lista Lista de ficheros del servidor, los que tienen todos los 
     * usuarios conectados en conjunto.
     * @param tabla La lista de clientes conectados al servidor en este momento.
     */
    public Cliente(PeerConn conexion, ListaArchivos lista, TablaClientes tabla){
       
        _conexion  = conexion;
        _tablaClientes = tabla;
        _listaArchivos = lista;
    }
    
    /**
     * Método que ejecuta el hilo.
     */
    @Override
    public void run () {
        
        try {
            
            // Recibe datos del cliente conectado

            // Obtenemos IP & puerto de escucha para otros clientes.
            _IP = _conexion.getIP();
            _puerto = _conexion.reciveInt();
            _nombre = (String) _conexion.reciveObject();

            // Crea estructuras
            // TODO:
                 
            // Recibe lista de ficheros.
            ListaArchivos archivosCliente = (ListaArchivos)_conexion.reciveObject();
            
            // TODO: Esta lista de archivos se añade a la tabla de archivos 
            // y se marca esos archivos como que este cliente los tiene
            
            // TODO: Atiende peticiones.
               
            // TODO: Recibe datos

            // TODO: Crea estructuras

            // TODO: recibe lista de ficheros.

            // TODO: atiende peticiones. 
    
        } 
        catch (IOException ex) {
            
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (ClassNotFoundException ex) {
            
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }       
    } 
}
