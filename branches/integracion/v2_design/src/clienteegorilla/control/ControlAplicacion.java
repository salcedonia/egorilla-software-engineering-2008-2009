/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clienteegorilla.control;

import red.PeerConn;
import clienteegorilla.control.gestionficheros.GestorCompartidos;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import datos.ListaArchivos;
import paquete.*;

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

    /*private static Vector<BuscadorPanel> _panelesBuscador = new Vector<BuscadorPanel>();

    public static void addBuscadorListener(BuscadorPanel p) {
        _panelesBuscador.add(p);
    }*/

    public ControlAplicacion(){
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
            //System.out.println("Adios0");
            System.out.println("Envio nombre-user <"+me.getNombreUsuario()+"> y su port-Listen <"+me.getPuertoEscucha()+">");
            _conn.enviarObjeto(me);
            //System.out.println("Adios0b");

            ListaArchivos arch;

            // ojo, puede no tener ningun archivo, pero el servidor los espera.
            if (_compartidos != null){
                arch = _compartidos.getArchivosCompartidos();
            }
            else{
              System.out.println("No hay ficheros compartidos, lista vacia");
                arch = new ListaArchivos();
            }
				
		     //System.out.println("Adios1");
             /*for(int i = 0; i < arch.size(); i++){
               System.out.println(arch.elementAt(i).getNombre());
			}*/
            // Mandamos la _lista de archivos asociada al cliente
            System.out.println("Se mando info de <"+ arch.size() +"> archivos compartidos");
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
    public static RespuestaPeticionConsulta consultar(String cad){
        RespuestaPeticionConsulta respuestaConsulta = null;
        PeticionConsulta pConsulta = new PeticionConsulta();

        pConsulta.setCadenaBusqueda(cad);
        try {
            _conn.enviarObjeto( pConsulta );
            respuestaConsulta = (RespuestaPeticionConsulta)_conn.recibirObjeto();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControlAplicacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ControlAplicacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return respuestaConsulta;
        //TODO: procesar answer!!!!!!
        /*for (BuscadorPanel buscador : _panelesBuscador) {
            buscador.update(GUIBuscador.EVENTO_RECIBIR_BUSQUEDA, answer);
        }*/
    }


    /**
     *
     * da la orden para proceder a bajar un fichero.
     *
     * @param hash el identificador unico de este fichero.
     */
    /*CAMBIADO DEL VOID A ...*/
    public static RespuestaPeticionDescarga bajar(String hash){

        PeticionDescarga pDescarga = new PeticionDescarga( hash );
        RespuestaPeticionDescarga respuestaDescarga = null;
        try {
            _conn.enviarObjeto( pDescarga );
            respuestaDescarga = (RespuestaPeticionDescarga)_conn.recibirObjeto();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ControlAplicacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ControlAplicacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (respuestaDescarga != null){
            Download d = new Download(respuestaDescarga, hash);
            d.start();
        }
        return respuestaDescarga;
    }
}
