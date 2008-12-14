package servidoregorilla.server;

import servidoregorilla.paquete.Peticion;
import Networking.PeerConn;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;
import java.util.Vector;
import servidoregorilla.Datos.ListaArchivos;
import servidoregorilla.protocolo.*;
import servidoregorilla.Datos.TablaClientes;
import servidoregorilla.paquete.DatosCliente;
import servidoregorilla.paquete.DownloadOrder;
import servidoregorilla.paquete.Query;
import servidoregorilla.paquete.TipoArchivo;

/**
 * Clase que implementa el servidor.
 * 
 * @author pitidecaner
 * @author Salcedonia
 */
public class Server extends Thread {

    // ATRIBUTOS
    private ServerSocket _serverSocket; // Servidor de escucha
    private ListaArchivos _listaArchivos; // Lista de archivos asociada al servidor
    private TablaClientes _tablaClientes; // Tabla de clientes asociada al servidor
    private boolean _loopConnPool;
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
    public Server(int puerto, ListaArchivos lista, TablaClientes tabla) throws IOException {

        _serverSocket = new ServerSocket(puerto);
        _listaArchivos = lista;
        _tablaClientes = tabla;

        // prepare connections pools
        _waitCons = new Vector<PeerConn>();
        _activeCons = new Vector<PeerConn>();

        // Inicializamos los datos relativos a las extensiones de archivo
        TipoArchivo.iniciarTiposArchivo();


        //activamos el pool de conexiones.
        this._loopConnPool = true;
        this.start();
    }

    /**
     * Escucha y crea conexiones de tipo peer según van llegando.
     * 
     * @return Un objeto de tipo peticion que se está utilizando.
     * @throws java.io.IOException Se genera la excepcion cuando se produce
     * algún problema en la red.
     */
    public void listen() throws IOException {

        // Crea una conexión de tipo peer según va llegando
        PeerConn conexion = new PeerConn(_serverSocket.accept());

        // encolamos el peercon al peerconnpool
        _waitCons.add(conexion);
        conexion.setWait();
        try {
            // tratamos el paquete recibido
            proccesRecivedData((Peticion) conexion.reciveObject(),conexion);
        } catch (ClassNotFoundException ex) {
            System.err.print("recibida version desconocida");
        }
    }

    /**
     * Este método analiza el paquete recibido y lanza un hilo de ejecución que 
     * se encarga de procesar la orden.
     * 
     * @param peticion la peticion del cliente. puede ser una conexion, un query, Download order
     * @param conn la conexion con el cliente que solicito
     * @throws java.io.IOException puede haber un porblema si no se reconoce el paquete recibido.
     */
    public synchronized void proccesRecivedData(Peticion peticion, PeerConn conn) throws IOException {

        switch (peticion.getVersion()) {

            case 1:

                ConexionCliente  altaCliente = new ConexionCliente(conn, (DatosCliente)peticion, _listaArchivos, _tablaClientes);
                // Lanzamos el hilo de ejecución asociado a la conexión
                System.out.println("Cliente conectado");
                altaCliente.start();
                break;
                
            case 2:

                // resuelve query
                QueryResolver qresolutor = new QueryResolver(_listaArchivos, (Query)peticion, conn);
                qresolutor.start();

                break;

            case 3:

                // resuelve download order
                DownloadOrderResolver dresolutor = new DownloadOrderResolver(_listaArchivos,(DownloadOrder)peticion ,conn);
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

    
    /**
     * Esto es el famoso PeerConnPool
     * 
     * Se encarga de escuchar a TODOS los clientes, esto tiene un poblema de 
     * rendimiento y habra que paralelizarlo un poco.
     * 
     * Es la versión preliminar, conexiones TCP que se mantienen durante toda la 
     * sesión del cliente.
     */
    public void run() {
         PeerConn p;
        while (_loopConnPool) {
            for (int i=0; i< _activeCons.size(); i++){
                p = _activeCons.elementAt(i);
                try {

                    // leer un objeto, se hara timeout a los 10 milisegundos.
                    Peticion pet = (Peticion) p.reciveObject(10);

                    // y si el objeto es válido, se procesa.
                    this.proccesRecivedData(pet, p);

                } catch (ClassNotFoundException ex) {
                    //TODO: no entiende el peticion
                } catch (SocketTimeoutException timeoutEx) {
                    // timeout, a otra cosa mariposa
                } catch (Exception ex) {
                    // error io, perdida la comunicación.
                    _activeCons.remove(p);

                    // TODO: dar de baja al tipo en todas partes.
                    _tablaClientes.removeCliente(p);

                    //TODO: logear la desconexion
                    System.out.print("cliente desconectado");

                    break;
                }
            }

            // if waitconns are ready, put back into active
           
            for (int i=0; i<_waitCons.size(); i++){
                p = _waitCons.elementAt(i);
                if (p.ready()) {
                    _waitCons.remove(p);
                    _activeCons.add(p);
                    i--;
                }
            }

            try {
                // dormir 100 milisegundos
                this.sleep(100);
            } catch (InterruptedException ex) {
                // TODO: algo que hacer si es despertado aqui??
            }
        }
    }
}
