/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servidoregorilla.server;

import Networking.PeerConn;
import java.io.IOException;
import java.net.ServerSocket;
import servidoregorilla.protocolo.Cliente;
import servidoregorilla.Datos.ListaArchivos;
import servidoregorilla.protocolo.Protocolo;
import servidoregorilla.Datos.TablaClientes;
import servidoregorilla.paquete.TipoArchivo;

/**
 * Clase que implementa el servidor.
 * 
 * @author pitidecaner
 * @author Salcedonia
 */
public class Server {

    // ATRIBUTOS
    private ServerSocket _serverSocket; // Servidor de escucha
    private ListaArchivos _listaArchivos; // Lista de archivos asociada al servidor
    private TablaClientes _tablaClientes; // Tabla de clientes asociada al servidor
    
    /**
     * Constructor de la clase Server. Crea un servidor de escucha en el 
     * puerto indicado. Almacenamos la lista de archivos y la tabla de 
     * clientes asociada a ese servidor.
     * 
     * @param puerto Puerto en el que se queda escuchando el servidor.
     * @param lista Lista de archivos asociada al servidor.
     * @param tabla Tabla de archivos asociada al servidor.
     * @throws java.io.IOException Se lanza la excepción cuando ocurre un error
     * al crear el servidor de escucha.
     */
    public Server(int puerto, ListaArchivos lista, TablaClientes tabla) throws IOException{
        
        _serverSocket = new ServerSocket(puerto);
        _listaArchivos = lista;
        _tablaClientes = tabla;
        
        // Inicializamos los datos relativos a las extensiones de archivo
        TipoArchivo.iniciarTiposArchivo();
    }
    
    /**
     * Escucha y crea conexiones de tipo peer según van llegando.
     * 
     * @return Un objeto de tipo Protocolo que se está utilizando.
     * @throws java.io.IOException Se genera la excepcion cuando se produce
     * algún problema en la red.
     */
    public Protocolo listen() throws IOException{

        // Crea una conexión de tipo peer según va llegando
        PeerConn conexion = new PeerConn(_serverSocket.accept());
        
        // Leemos la versión del protocolo y actuamos en consecuencia
        switch (conexion.reciveInt()){ 
            
            case 1:
                // Versión del cliente
                return new Cliente(conexion, _listaArchivos, _tablaClientes);
            case 2:
                // TODO: Modo consola o lo que sea
                break;
            default:
                // Lanzamos una excepción por error de protocolo
                throw new IOException("Versión de protocolo no válida");
        }
        
        return null;
    }
}
