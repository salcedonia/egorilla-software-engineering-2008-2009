/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servidoregorilla.server;

import networking.PeerConn;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;
import java.util.Vector;
import servidoregorilla.datos.ListaArchivos;
import servidoregorilla.protocolo.*;
import servidoregorilla.datos.TablaClientes;
import servidoregorilla.paquete.TipoArchivo;

/**
 * Clase que implementa el servidor.
 * 
 * @author pitidecaner
 * @author Salcedonia
 */
public class Server extends Thread{

    // ATRIBUTOS
    private ServerSocket _serverSocket; // Servidor de escucha
    private ListaArchivos _listaArchivos; // Lista de archivos asociada al servidor
    private TablaClientes _tablaClientes; // Tabla de clientes asociada al servidor
    private boolean _loop;
    
    private Vector<PeerConn> _activeCons;
    private Vector<PeerConn> _waitCons;
    
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
     * @return Un objeto de tipo peticion que se está utilizando.
     * @throws java.io.IOException Se genera la excepcion cuando se produce
     * algún problema en la red.
     */
    public Peticion listen() throws IOException{

        // Crea una conexión de tipo peer según va llegando
        PeerConn conexion = new PeerConn(_serverSocket.accept());
        
        Peticion peticion = null;
        
        // encolamos el peercon al peerconnpool
        _waitCons.add(conexion);
        conexion.setWait();
        
        // Leemos la versión del peticion y actuamos en consecuencia
        peticion = new ConexionCliente(conexion, _listaArchivos, _tablaClientes);
        proccesRecivedData(peticion);
             
        return null;
    }
    

    public synchronized void proccesRecivedData(Peticion peticion) throws IOException {
        switch (peticion.getVersion()) {

            case 1:
                // Versión del cliente
                // Lanzamos el hilo de ejecución asociado a la conexión
                System.out.println("Cliente conectado");
                peticion.start();
            case 2:
                // resuelve query
                peticion.addListaArchivos(_listaArchivos);
                peticion.addTablaClientes(_tablaClientes);
                
                peticion.start();
               
                break;
            default:
                /*
                 * Si la version no es correcta lanza excepcion
                 * diferentes versiones pueden tener diferente modo de actuación
                 * en el servidor.
                 */
                throw new IOException("Versión de peticion no válida");
        }
    }
    
    
      public void run() {

        while (_loop) {
         
            for (PeerConn peerConn : _activeCons) {
                try {

                    // leer un objeto, se hara timeout.
                    Peticion pet = (Peticion) peerConn.reciveObject();
                    
                    // y si el objeto es válido, se procesa.
                    this.proccesRecivedData(pet);
                   
                } catch (ClassNotFoundException ex) {
                    //TODO: no entiende el peticion
                } catch (SocketTimeoutException timeoutEx) {
                    //TODO: timeout, a otra cosa mariposa
                } catch (Exception ex) {
                    //TODO: error grande, hay desconexio, elimina del manojo y continua.
                }
            }
            
            // if waitconns are ready, put back into active
            for (PeerConn p : _waitCons) {
                if (p.ready()){
                    _waitCons.remove(p);
                    _activeCons.add(p);
                }
            }
            
            try {
                // dormir 2 segundos
                this.sleep(2000);
            } catch (InterruptedException ex) {
                // TODO: algo que hacer si es despertado aqui??
            }
        }
    }

}
