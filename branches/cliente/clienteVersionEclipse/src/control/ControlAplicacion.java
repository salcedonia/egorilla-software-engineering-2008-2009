package control;

import control.gestion_ficheros.GestorCompartidos;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import logica.datos.DatosCliente;
import logica.datos.ListaArchivos;
import logica.protocolo.peticiones.PeticionBusqueda;
import logica.protocolo.peticiones.PeticionDescarga;
import logica.protocolo.respuestas.RespuestaABusqueda;
import logica.protocolo.respuestas.RespuestaADescarga;
import logica.red.ConexionPeer;

//************************************************************************************//
/**
 * Un control rudimentario para la aplicación.
 * Responde a las llamadas de la interfaz de forma imperativa.
 *
 * @author pitidecaner
 * @author S@L-c
 */
public class ControlAplicacion {
    
    // ATRIBUTOS
	private static ConexionPeer _conexion;
    private static boolean  _conectado = false;
    private static GestorCompartidos _gestorCompartidos = null;

//  ************************************************************************************//
    /**
     * Establece el gestor de compartidos a valor comp.
     * 
     * @param comp Nuevo valor de compartidos a establecer.
     */
    public static void setCompartidos(GestorCompartidos comp) {
       
    	_gestorCompartidos = comp;
    }

//  ************************************************************************************//
    /**
     * Devuelve la variable _conectado.
     * 
     * @return La variable _conectado.
     */
    public static boolean getConectado() {
        
    	return _conectado;
    }

//  ************************************************************************************//
    /**
     * Realiza la conexión a un servidor de este cliente.
     *
     * @throws IOException Se lanza la excepción cuando se produce 
     * algún fallo entre la comunicación entre el cliente y el servidor.
     */
    public static void conectarAServidor() throws IOException{
         
    	// Crea una conexion de prueba
		Socket conexion = new Socket("127.0.0.1", 6969);

		// Crea el wrapper
		_conexion = new ConexionPeer(conexion);
		
		DatosCliente me = new DatosCliente();
		me.setNombreUsuario("dePruebas");
		me.setPuertoEscucha(4000);
		_conexion.enviarObjeto(me);

		ListaArchivos arch;

		// Puede no tener ningun archivo, pero el servidor los espera.
		if (_gestorCompartidos != null)
			arch = _gestorCompartidos.getArchivosCompartidos();
		else
			arch = new ListaArchivos(); // Le mandamos la lista vacía
		
		// Mandamos la _lista de archivos asociada al cliente
		_conexion.enviarObjeto(arch);
		_conectado = true;
    }

// ************************************************************************************//
    /**
	 * Cierra la conexión con el servidor.
	 */
    public static void cerrarConexionConServidor(){
    	
        try {
        	
            _conexion.cerrarComunicacion();
            _conectado = false;
        } 
        catch (IOException ex) {
        	
        	ex.printStackTrace();
        }        
    }

//  ************************************************************************************//
    /**
     * Pregunta al servidor por algún fichero con algunos datos proporcionados por
     * el cliente.
     *
     * //TODO: buscar algo mas que por el nombre
     *
     * @param cad nombre de fichero buscado
     */
    public static RespuestaABusqueda peticionBusquedaArchivo(String cad){
    	
    	try {
            
    		// Enviamos la consulta
    		PeticionBusqueda q = new PeticionBusqueda();
    		q.setCadenaBusqueda(cad);
            _conexion.enviarObjeto(q);
            
            RespuestaABusqueda respuesta = (RespuestaABusqueda)_conexion.recibirObjeto();
            
            // Devolvemos la respuesta del servidor
            return respuesta;
        } 
        catch (ClassNotFoundException ex) {
            
        	Logger.getLogger(ControlAplicacion.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) {
            
        	Logger.getLogger(ControlAplicacion.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }

//  ************************************************************************************//
    /**
     * Da la orden para proceder a bajar un fichero.
     *
     * @param hash el identificador único de este fichero.
     */
    public static void peticionDescargarArchivo(String hash){

    	PeticionDescarga orden = new PeticionDescarga(hash);
    	RespuestaADescarga answer = null;
        
        try {
            _conexion.enviarObjeto(orden);
            answer = (RespuestaADescarga)_conexion.recibirObjeto();
        } 
        catch (ClassNotFoundException ex) {
            Logger.getLogger(ControlAplicacion.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (IOException ex) {
            Logger.getLogger(ControlAplicacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (answer != null){

            DescargaArchivo d = new DescargaArchivo(answer, hash);
            d.start();
        }
    }
}
