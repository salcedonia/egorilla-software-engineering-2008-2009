package tareas;

import datos.*;
import gestorDeProtocolos.DatosCliente;
import gestorDeRed.ConexionPeer;
import java.io.IOException;
import java.net.*;
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
public class ConexionCliente extends Thread {

    // ATRIBUTOS
    private ConexionPeer _conexion;
    private DatosCliente _datosCliente;
    private ArchivoClientes _listaGlobalArchivoClientes;
    private String _direccion;

    /**
     * 
     * @param conexion
     * @throws java.io.IOException
     */
    public ConexionCliente(Socket conexion)  throws IOException {
        
        _conexion = new ConexionPeer( conexion );
    }
    
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
    public void set(ArchivoClientes archivoClientes, DatosCliente datos ) {
        _listaGlobalArchivoClientes = archivoClientes;
        _datosCliente = datos;
    }

    /**
     * Devuelve la conexión asociada a ese cliente.
     * 
     * @return La conexión asociada a ese cliente.
     */
    public ConexionPeer getConexion() {
        return _conexion;
    }

    /**
     * Devuelve los datos del cliente asociado.
     * 
     * @return Los datos del cliente asociado.
     */
    public DatosCliente getDatosCliente() {
        return _datosCliente;
    }

    /**
     * Devuelve la dirección.
     * 
     * @return La dirección.
     */
    public String getDireccion() {
        return _direccion;
    }

    /**
     * Método que ejecuta el hilo.
     */
    @Override
    public void run() {

        try {
            // Recibe los archivos del cliente 
            ListaArchivos archivosCliente = (ListaArchivos) _conexion.recibirObjeto();
                
            //for para DEBUG
			for(int i = 0; i < archivosCliente.size(); i++){
				System.out.println(archivosCliente.elementAt(i).getNombre());
			}
			System.out.println("Archivos del cliente <" + archivosCliente.size() +">");
                        //fin for para DEBUG

            // Averigua la dirección de origen.
            _datosCliente.setIP(_conexion.getIP());

            // Alta del cliente en el sistema
            //_tablaDeClientes.add(this);

            // Alta de los archivos en el sistema
            _listaGlobalArchivoClientes.actualizarDesdeListaCliente(this, archivosCliente);

            //for para DEBUG
            System.out.println("Archivos en la lista global <" +_listaGlobalArchivoClientes.getNumeroArchivos() + ">");
	    System.out.println(_listaGlobalArchivoClientes.getListaArchivos());
            //System.out.println(" Fin archivos en la lista global.");
                        //fin for para DEBUG
                        
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
