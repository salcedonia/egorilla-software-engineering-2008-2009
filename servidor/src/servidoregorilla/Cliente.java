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
 *
 * @author pitidecaner
 */
public class Cliente extends Thread{
    
    private String _nmb;
    private String _ip;
    private int _puerto;
    private Socket _conn;
    
    /**
     * La clase cliente mantiene los datos necesarios para la comunicación 
     * con un cliente. 
     * Para empezar comenzará un hilo de ejecución que recibira todos los datos
     * que el cliente envia al conectar, estos son:
     * 
     * - nombre usuario,
     * - puerto por el que el usuario escucha a otros clientes.
     * 
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
        _conn  =conexion;
    }
    
    @Override
    public void run (){
        try {
            
            ObjectInputStream flujo = new ObjectInputStream(_conn.getInputStream());

            // recibe datos
            // read version!!!
            if (Config.protocolVersion() == flujo.readInt()){
                // TODO:  elevar excepción 
            } 
                       
            _puerto = flujo.readInt();
                        
            // obtener IP & puerto de escucha para otros clientes.
            _ip = _conn.getRemoteSocketAddress().toString();
            _nmb = (String) flujo.readObject();

            
            // crea estructuras
            // recibe lista de ficheros.
            ListaArchivos archivosCliente = (ListaArchivos)flujo.readObject();
            
            
            
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
