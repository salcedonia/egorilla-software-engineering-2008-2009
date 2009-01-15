/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package logica.red;

import logica.datos.*;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

//*****************************************************************************//
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
public class ConexionCliente extends Thread {

    // ATRIBUTOS
    private ConexionPeer _conexion;
    private DatosCliente _datosCliente;
    private ListaArchivos _listaGlobalArchivos;
    private TablaClientes _tablaDeClientes;
    private String _direccion;

//  *****************************************************************************//
    /**
     * Constructor de la clase Cliente. Almacenamos los datos proporcionados
     * por el servidor al conectarse.
     * 
     * @param conexion Conexión recién abierta con el cliente.
     * @param datos Datos del cliente.
     * @param archivos Lista de ficheros del servidor, los que tienen todos los 
     * usuarios conectados en conjunto.
     * @param clientes La lista de clientes conectados al servidor en este momento.
     */
    public ConexionCliente(ConexionPeer conexion, DatosCliente datos, ListaArchivos archivos, TablaClientes clientes) {
        _conexion = conexion;
        _datosCliente = datos;
        _listaGlobalArchivos = archivos;
        _tablaDeClientes = clientes;
    }

//  *****************************************************************************//
    /**
     * Devuelve la conexión asociada a ese cliente.
     * 
     * @return La conexión asociada a ese cliente.
     */
    public ConexionPeer getConexion() {
        return _conexion;
    }

//  *****************************************************************************//
    /**
     * Devuelve los datos del cliente asociado.
     * 
     * @return Los datos del cliente asociado.
     */
    public DatosCliente getDatosCliente() {
        return _datosCliente;
    }

//  *****************************************************************************//
    /**
     * Devuelve la dirección.
     * 
     * @return La dirección.
     */
    public String getDireccion() {
        return _direccion;
    }

//  *****************************************************************************//
    /**
     * Método que ejecuta el hilo.
     */
    @Override
    public void run() {

        try {
            // Recibe los archivos del cliente 
            ListaArchivos archivosCliente = (ListaArchivos) _conexion.recibirObjeto();

			for(int i = 0; i < archivosCliente.size(); i++){
				System.out.println(archivosCliente.elementAt(i).getNombre());
			}
			System.out.println("Archivos del cliente <" + archivosCliente.size() +">");
            // Averigua la dirección de origen.
            _datosCliente.setIP(_conexion.getIP());

            // Alta del cliente en el sistema
            _tablaDeClientes.add(this);

            // Alta de los archivos en el sistema
            _listaGlobalArchivos.actualizarDesdeListaCliente(this, archivosCliente);
            System.out.println("Archivos en la lista global <" +_listaGlobalArchivos.size() + ">");
			for(int i = 0; i < _listaGlobalArchivos.size(); i++){
				System.out.println(_listaGlobalArchivos.elementAt(i).getNombre());
			}
            // Listo para usar!
			
            _conexion.listo();           
        } 
        catch (IOException ex) {

            Logger.getLogger(ConexionCliente.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (ClassNotFoundException ex) {

            Logger.getLogger(ConexionCliente.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (Exception ex) {

            Logger.getLogger(ConexionCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
