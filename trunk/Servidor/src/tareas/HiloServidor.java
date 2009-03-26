package tareas;

import java.io.*;
import java.net.*;
import java.util.*;
import datos.*;
import mensajes.Mensaje;
import mensajes.serverclient.DatosCliente;
import mensajes.serverclient.PeticionConsulta;
import mensajes.serverclient.PeticionDescarga;


/**
 * Clase que implementa el servidor.
 */
public class HiloServidor extends Thread {

    // ATRIBUTOS
    private ServerSocket _servidorDeEscucha; // HiloServidor de escucha
    private ArchivoClientes _archivoClientes;
    private boolean _loopConnPool;
    private Vector<ConexionCliente> _conexionesActivas;
    private Vector<ConexionCliente> _conexionesEnEspera;

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
    public HiloServidor(int puerto) throws IOException {

        _servidorDeEscucha = new ServerSocket( puerto );

        //Inicializo la tabla de archivo - clientes[] (global)
        _archivoClientes = new ArchivoClientes();

        // prepare connections pools
        _conexionesEnEspera = new Vector<ConexionCliente>();
        _conexionesActivas = new Vector<ConexionCliente>();

        // Activamos el pool de conexiones.
        _loopConnPool = true;
        
        // Lanzamos el hilo de ejecución
        start();
    }

    /**
     * Escucha y crea conexiones de tipo peer según van llegando.
     * 
     * @return Un objeto de tipo peticion que se está utilizando.
     * @throws java.io.IOException Se genera la excepcion cuando se produce
     * algún problema en la red.
     */
    public void escuchar() throws IOException {

        // Se crea una conexion con cliente.
        Socket conexion = _servidorDeEscucha.accept();

        //Le pasamos el socket al cliente, quien crea el PeerConn correspondiente.
        ConexionCliente conexionAceptada = new ConexionCliente( conexion ); 

        // Encolamos la conexiones cliente al pool de conexiones en espera
        _conexionesEnEspera.add( conexionAceptada );
        
        // Le decimos que espere
        conexionAceptada.getConexion().espera();
        
        try {
            
            // tratamos el paquete recibido
            procesarDatosRecibidos( (Mensaje) conexionAceptada.getConexion().recibirObjeto(), conexionAceptada );
        } 
        catch (ClassNotFoundException ex) {
        
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
    public synchronized void procesarDatosRecibidos(Mensaje peticion, ConexionCliente conn) throws IOException {

        switch (peticion.getTipoMensaje()) {

            case DatosCliente:

                // Tratamos la conexion creada
                //ConexionCliente altaCliente = _conexionesEnEspera.lastElement();

                //Con los datos de obtengamos de la conexion actualizamos la tabla archivoClientes
                conn.set( _archivoClientes, (DatosCliente) peticion );
                
                System.out.println("Cliente conectado");
                
                // Lanzamos el hilo de ejecución asociado a la conexión
                conn.start();
                break;

            case PeticionConsulta:

                // resuelve query
                System.out.println("PeticionConsulta recibida desde cliente.");
                //PeticionConsulta pConsulta
                ProcesarPeticionBusqueda procesarConsulta = new ProcesarPeticionBusqueda( _archivoClientes, (PeticionConsulta) peticion, conn );
                procesarConsulta.start();
                //QueryResolver qresolutor = new QueryResolver(_archivoClientes, (Query) peticion, conn);
                
                break;

            case PeticionDescarga:

                // resuelve download order
                System.out.println("PeticionDescarga recibida desde cliente.");
                //DownloadOrderResolver dresolutor = new DownloadOrderResolver(_listaArchivos, (DownloadOrder) peticion, conn);
                //dresolutor.start();
                ProcesarPeticionDescarga procesarDescarga = new ProcesarPeticionDescarga( _archivoClientes, (PeticionDescarga) peticion, conn );
                procesarDescarga.start();

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
    @Override
    public void run() {
        ConexionCliente p;
        while (_loopConnPool) {
            for (int i = 0; i < _conexionesActivas.size(); i++) {
                p = _conexionesActivas.elementAt(i);
                try {

                    // leer un objeto, se hara timeout a los 10 milisegundos.
                    Mensaje pet = (Mensaje) p.getConexion().recibirObjeto(10);

                    // y si el objeto es válido, se procesa.
                    //procesarDatosRecibidos(pet, p);
                    procesarDatosRecibidos( pet, p );

                } catch (ClassNotFoundException ex) {
                    //TODO: no entiende el peticion
                } catch (SocketTimeoutException timeoutEx) {
                    // timeout, a otra cosa mariposa
                } catch (Exception ex) {
                    // error io, perdida la comunicación.
                    _conexionesActivas.remove(p);

                    // TODO: dar de baja al tipo en todas partes.
                    //_tablaClientes.removeCliente(p);

                    //TODO: logear la desconexion
                    System.out.println("cliente desconectado");

                    break;
                }
            }

            // if waitconns are getListo, put back into active

            for (int i = 0; i < _conexionesEnEspera.size(); i++) {
                p = _conexionesEnEspera.elementAt(i);
                if (p.getConexion().getListo()) {
                    _conexionesEnEspera.remove(p);
                    _conexionesActivas.add(p);
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
