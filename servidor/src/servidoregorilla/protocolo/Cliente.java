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
 * Cuando haces un hello se expande este hilo por parte del servidor.
 * @author pitidecaner
 */
public class Cliente extends Thread implements Protocolo{
    
    private String _nmb;
    private InetAddress _ip;
    private int _puerto;
    private PeerConn _conn;
    private ListaArchivos _listaArchivos;
    private TablaClientes _tablaClientes;
    
    /**
     * La clase cliente mantiene los datos necesarios para la comunicación 
     * con un cliente. 
     * Para empezar comenzará un hilo de ejecución que recibirá todos los datos
     * que el cliente envía al conectar, estos son:
     * 
     * - nombre usuario,
     * - puerto por el que el usuario escucha a otros clientes.
     * - lista de ficheros compartidos que tiene el cliente.
     * 
     * El cliente nos suministrará estos datos.
     * 
     * @param conexion  conexion recien abierta con el cliente
     * @param l         lista de ficheros del servidor, los que tienen todos los 
     *                  usuarios conectados en conjunto.
     * @param t         La lista de clientes conectados al servidor en este momento.
     */
    public Cliente(PeerConn conexion, ListaArchivos l, TablaClientes t){
       
        _conn  = conexion;
        _tablaClientes = t;
        _listaArchivos = l;
    }
    
    @Override
    public void run (){
        try {
            
            // recibe datos

            // obtener IP & puerto de escucha para otros clientes.
            _ip = _conn.getip();
            _puerto = _conn.reciveInt();
            _nmb = (String) _conn.reciveObject();

            
            // crea estructuras
            // TODO:
            
            
            // recibe lista de ficheros.
            ListaArchivos archivosCliente = (ListaArchivos)_conn.reciveObject();
            // TODO: Esta lista de archivos se añade a la tabla de archivos 
            // y se marca esos archivos como que este cliente los tiene
            
            // atiende peticiones.
       
        
            // recibe datos

            // crea estructuras

            // recibe lista de ficheros.


            // atiende peticiones. 

            
        } catch (IOException ex) {
            
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    
}
