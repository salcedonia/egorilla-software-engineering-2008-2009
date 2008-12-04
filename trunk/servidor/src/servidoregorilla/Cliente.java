/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servidoregorilla;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Cuando haces un hello se expande este hilo por parte del servidor.
 * @author pitidecaner
 */
public class Cliente extends Thread{
    
    private String _nmb;
    private String _ip;
    private int _puerto;
    private Socket _conn;
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
    public Cliente(Socket conexion, ListaArchivos l, TablaClientes t){
       
        _conn  = conexion;
        _tablaClientes = t;
        _listaArchivos = l;
    }
    
    @Override
    public void run (){
        try {
            
            ObjectInputStream flujo = new ObjectInputStream(_conn.getInputStream());

            // recibe datos
            // read version!!! Si la version no es correcta lanza excepcion
            if (Config.protocolVersion(flujo.readInt())){
                // TODO:  Trabajar en ESA EXCEPCION 
            } 
                              
            // obtener IP & puerto de escucha para otros clientes.
            _ip = _conn.getRemoteSocketAddress().toString();
            _puerto = flujo.readInt();
            _nmb = (String) flujo.readObject();

            
            // crea estructuras
            // TODO:
            
            
            // recibe lista de ficheros.
            ListaArchivos archivosCliente = (ListaArchivos)flujo.readObject();
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
