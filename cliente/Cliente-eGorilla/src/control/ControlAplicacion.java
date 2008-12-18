/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import networking.PeerConn;
import control.filemanagement.GestorCompartidos;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import presentacion.buscador.BuscadorPanel;
import presentacion.buscador.GUIBuscador;
import servidoregorilla.datos.ListaArchivos;
import servidoregorilla.paquete.Archivo;
import servidoregorilla.paquete.DatosCliente;
import servidoregorilla.paquete.DownloadOrder;
import servidoregorilla.paquete.DownloadOrderAnswer;
import servidoregorilla.paquete.Query;
import servidoregorilla.paquete.TipoArchivo;

/**
 * Un control rudimentario para la aplicacion
 *
 * Basicamente responde a las llamadas de la interfaz de forma imperativa
 *
 * // TODO: hacer un control de verdad.
 *
 * @author usuario_local
 */
public class ControlAplicacion {
    
    private static PeerConn _conn;
    private static boolean  _conectado = false;
    private static GestorCompartidos _compartidos = null;

    private static Vector<BuscadorPanel> _panelesBuscador = new Vector<BuscadorPanel>();

    public static void addBuscadorListener(BuscadorPanel p) {
        _panelesBuscador.add(p);
    }

    public static void compartidos(GestorCompartidos comp) {
        _compartidos = comp;
    }

    public static boolean conectado() {
        return _conectado;
    }


    /**
     *
     * realiza la conexion a un servidor de este cliente.
     *
     * @throws java.io.IOException
     */
    public static void conectar() throws IOException{
              // Crea una conexion de prueba
         Socket conexion = new Socket("127.0.0.1", 6969);
        //Socket conexion = new Socket("192.168.1.32", 6969);


            _conn = new PeerConn(conexion);
          
            DatosCliente me = new DatosCliente();
            me.setNombreUsuario("dePruebas");
            me.setPuertoEscucha(4000);
            System.out.println("Adios0");
            _conn.enviarObjeto(me);
			System.out.println("Adios0b");

            ListaArchivos arch;

            // ojo, puede no tener ningun archivo, pero el servidor los espera.
            if (_compartidos != null){
			System.out.println("pollas2");
                arch = _compartidos.getArchivosCompartidos();
				System.out.println("pollas3");
				}
            else
                arch = new ListaArchivos();
				
		     System.out.println("Adios1");
             for(int i = 0; i < arch.size(); i++){
				System.out.println(arch.elementAt(i).getNombre());
			}
            // Mandamos la _lista de archivos asociada al cliente
            _conn.enviarObjeto(arch);    
            
            _conectado = true;
    }

    /**
     *
     * cierra la conexion con el servidor.
     *
     */
    public static void close(){
        try {
            _conn.cerrarComunicacion();
        } catch (IOException ex) {
          // nothing
        }
        _conectado = false;
    }


    /****
     *
     * pregunta al servidor por algun fichero con algunos datos proporcionados por
     * el cliente.
     *
     * //TODO: buscar algo mas que por el nombre
     *
     * @param cad nombre de fichero buscado
     */
    public static void query(String cad){
        servidoregorilla.paquete.QueryAnswer answer = null;
        servidoregorilla.paquete.Query q = new Query();
        q.setCadenaBusqueda(cad);
        try {
            _conn.enviarObjeto(q);
            answer = (servidoregorilla.paquete.QueryAnswer)_conn.recibirObjeto();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControlAplicacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ControlAplicacion.class.getName()).log(Level.SEVERE, null, ex);
        }

        //TODO: procesar answer!!!!!!
        for (BuscadorPanel buscador : _panelesBuscador) {
            buscador.update(GUIBuscador.EVENTO_RECIBIR_BUSQUEDA, answer);
        }
    }


    /**
     *
     * da la orden para proceder a bajar un fichero.
     *
     * @param hash el identificador unico de este fichero.
     */
    public static void bajar(String hash){

        DownloadOrder orden = new DownloadOrder(hash);
        DownloadOrderAnswer answer = null;
        try {
            _conn.enviarObjeto(orden);
            answer = (DownloadOrderAnswer)_conn.recibirObjeto();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControlAplicacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ControlAplicacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (answer != null){

            Download d = new Download(answer, hash);
            d.start();
        }
    }
}
