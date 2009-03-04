/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.ejecucion;

import logica.datos.DatosCliente;
import logica.datos.ListaArchivos;
import logica.datos.TablaClientes;
import logica.protocolo.*;
import logica.protocolo.peticiones.PeticionBusqueda;
import logica.protocolo.peticiones.PeticionDescarga;
import logica.protocolo.resolutores.ResolutorDeBusquedas;
import logica.protocolo.resolutores.ResolutorDeDescargas;
import logica.red.ConexionCliente;
import logica.red.ConexionPeer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;
import java.util.Vector;

/*****************************************************************************/
/**
 * Clase que implementa el hilo de ejecución del servidor.
 * 
 * @author pitidecaner
 * @author Salcedonia
 */
public class HiloDelServidor extends Thread {

    // ATRIBUTOS
    private ServerSocket _servidorDeEscucha; // Servidor de escucha
    private ListaArchivos _listaArchivos; // Lista de archivos asociada al servidor
    private TablaClientes _tablaClientes; // Tabla de clientes asociada al servidor
    private boolean _loopConnPool;
    private Vector<ConexionPeer> _conexionesActivas;
    private Vector<ConexionPeer> _conexionesEnEspera;

//  *****************************************************************************//
    /**
     * Constructor de la clase HiloDelServidor. Crea un servidor de escucha en el 
     * puerto indicado. Almacenamos la lista de archivos y la tabla de 
     * clientes asociada a ese servidor.
     * 
     * @param puerto Puerto en el que se queda escuchando el servidor.
     * @param lista Lista de archivos asociada al servidor.
     * @param tabla Tabla de archivos asociada al servidor.
     * @throws java.io.IOException Se lanza la excepción cuando ocurre un error
     * al crear el servidor de escucha.
     */
    public HiloDelServidor(int puerto, ListaArchivos lista, TablaClientes tabla) throws IOException {

        _servidorDeEscucha = new ServerSocket(puerto);
        _listaArchivos = lista;
        _tablaClientes = tabla;

        // prepare connections pools
        _conexionesEnEspera = new Vector<ConexionPeer>();
        _conexionesActivas = new Vector<ConexionPeer>();

        // Activamos el pool de conexiones.
        _loopConnPool = true;
        
        // Lanzamos el hilo de ejecución
        start();
    }

//  *****************************************************************************//
    /**
     * Escucha y crea conexiones de tipo peer según van llegando.
     * 
     * @return Un objeto de tipo peticion que se está utilizando.
     * @throws java.io.IOException Se genera la excepcion cuando se produce
     * algún problema en la red.
     */
    public void escuchar() throws IOException {

        // Crea una conexión de tipo peer según va llegando
        ConexionPeer conexion = new ConexionPeer(_servidorDeEscucha.accept());

        // Encolamos la conexión al pool de conexiones en espera
        _conexionesEnEspera.add(conexion);
        
        // Le decimos que espere
        conexion.espera();
        
        try {
            
            // tratamos el paquete recibido
            procesarDatosRecibidos((Mensaje) conexion.recibirObjeto(), conexion);
        } 
        catch (ClassNotFoundException ex) {
        
            System.err.print("recibida version desconocida");
        }
    }

//  *****************************************************************************//
    /**
     * Este método analiza el paquete recibido y lanza un hilo de ejecución que 
     * se encarga de procesar la orden.
     * 
     * @param peticion la peticion del cliente. puede ser una conexion, un query, Download order
     * @param conexion la conexion con el cliente que solicito
     * @throws IOException puede haber un porblema si no se reconoce el paquete recibido.
     */
    private synchronized void procesarDatosRecibidos(Mensaje peticion, ConexionPeer conexion) throws IOException {

        switch (peticion.getProtocolo()) {

            case CONEXION_CLIENTE_SERVIDOR:

                // Creamos una nueva conexion con el cliente
                ConexionCliente altaCliente = new ConexionCliente(conexion, (DatosCliente) peticion, _listaArchivos, _tablaClientes);
                
                System.out.println("--> Cliente conectado.");
                
                // Lanzamos el hilo de ejecución asociado a la conexión
                altaCliente.start();
                break;

            case BUSQUEDA_ARCHIVOS:

                // Resuelve la petición de búsqueda de archivos
                ResolutorDeBusquedas resolutor = new ResolutorDeBusquedas(_listaArchivos, (PeticionBusqueda) peticion, conexion);
                resolutor.start();

                break;

            case DESCARGA_ARCHIVOS:

                // Resuelve la petición de descarga de archivos
                ResolutorDeDescargas dresolutor = new ResolutorDeDescargas(_listaArchivos, (PeticionDescarga) peticion, conexion);
                dresolutor.start();

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

//  *****************************************************************************//
    /**
     * Se encarga de escuchar a TODOS los clientes, esto tiene un poblema de 
     * rendimiento y habra que paralelizarlo un poco.
     * 
     * Es la versión preliminar, conexiones TCP que se mantienen durante toda la 
     * sesión del cliente.
     */
    @SuppressWarnings("static-access")
	public void run() {
        
    	ConexionPeer p;
        
    	while (_loopConnPool) {
            
    		for (int i = 0; i < _conexionesActivas.size(); i++) {
                
    			p = _conexionesActivas.elementAt(i);
                
    			try {

                    // leer un objeto, se hara timeout a los 10 milisegundos.
                    Mensaje pet = (Mensaje) p.recibirObjeto(10);

                    // y si el objeto es válido, se procesa.
                    this.procesarDatosRecibidos(pet, p);

                } 
    			catch (ClassNotFoundException ex) {
                    //TODO: no entiende el peticion
                } 
    			catch (SocketTimeoutException timeoutEx) {
                    // timeout, a otra cosa mariposa
                } 
    			catch (Exception ex) {
                
    				// error io, perdida la comunicación.
                    _conexionesActivas.remove(p);

                    // TODO: dar de baja al tipo en todas partes.
                    _tablaClientes.eliminarCliente(p);

                    //TODO: logear la desconexion
                    System.out.println("cliente desconectado");

                    break;
                }
            }

            for (int i = 0; i < _conexionesEnEspera.size(); i++) {
                
            	p = _conexionesEnEspera.elementAt(i);
                
            	// Si está listo lo quitamos de las de espera y las metemos en activas
            	if (p.getListo()) {
                    
            		_conexionesEnEspera.remove(p);
                    _conexionesActivas.add(p);
                    i--;
                }
            }

            try {
                
            	// dormir 100 milisegundos
                this.sleep(100);
            } 
            catch (InterruptedException ex) {
                // TODO: algo que hacer si es despertado aqui??
            }
        }
    }
}
