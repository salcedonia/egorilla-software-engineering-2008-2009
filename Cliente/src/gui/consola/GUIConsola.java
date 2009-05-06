package gui.consola;

import datos.Archivo;
import gestorDeConfiguracion.ControlConfiguracionCliente;
import gestorDeConfiguracion.PropiedadCliente;
import mensajes.serverclient.DatosCliente;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;
import mensajes.serverclient.ListaArchivos;
import peerToPeer.egorilla.GestorEgorilla;
import peerToPeer.egorilla.ObservadorGestorEgorilla;

/**
 * Interfaz en modo consola de la aplicacion Cliente eGorilla.
 * 
 * @author Iván Munsuri, Javier Salcedo, Javier Sánchez
 */
public class GUIConsola implements ObservadorGestorEgorilla {

    /**
     * Log para recopilar la información en un fichero de log.
     */
    private static final Logger _logger = Logger.getLogger(GUIConsola.class.getName());
    /**
     * Controlador de la aplicación en modo consola.
     */
    private ControladorConsola _controlador;
    /**
     * Creacion del flujo para leer datos.
     */
    public InputStreamReader _inputStreamReader = new InputStreamReader(System.in);
    /**
     * Creacion del filtro para optimizar la lectura de datos.
     */
    public BufferedReader _bufferedReader = new BufferedReader(_inputStreamReader);
    /**
     * Indica si está conectado a un servidor o no.
     */
    private static boolean _conectado = false;
    /**  
     * Última busqueda para tener todos los datos de los archivos al descargar 
     */
    private GUIBusqueda _busqueda;

    /**
     * Constructor de la clase GUIConsola.
     * 
     * @param controlador Controlador de la aplicación en modo consola.
     * @throws java.io.IOException
     */
    public GUIConsola(ControladorConsola controlador) throws IOException, Exception {

        _controlador = controlador;

        // Registramos la vista como observador del GestorEGorilla
        _controlador.getGestorEGorilla().agregarObservador(this);

        _busqueda = new GUIBusqueda();

        run();
    }

    /**
     * Muestra un mensaje por pantalla.
     * 
     * @param mensaje Mensaje a mostrar.
     */
    public synchronized void mostrarMensaje(String mensaje) {

        System.out.println(mensaje);
    }

    /**
     * Muestra el menú de la aplicación en modo consola e invoca a los métodos 
     * correspondientes del ControladorConsola en función de la opción elegida
     * por el usuario.
     * 
     * @throws java.io.IOException Se lanza cuando se produce algún error de IO.
     */
    public void run() throws Exception {

        char op;
        do {

            mostrarMenu();

            op = elegirOpcion();
            switch (op) {
                case '1':
                    opcionConexionAServidor();
                    break;
                case '2':
                    opcionConexionAServidorPorDefecto();
                    break;
                case '3':
                    opcionDesconexionDeServidor();
                    break;
                case '4':
                    opcionBuscarFichero();
                    break;
                case '5':
                    opcionDescargarFichero();
                    break;
                case '6':
                    opcionMostrarCompartidos();
                    break;
                case '0':
                    opcionSalir();
                    break;
                default:
                    mostrarMensaje("ERROR, La opción introducida no es válida!!\n");
            }
        } while (op != '0');
    }

    /**
     * Muestra el menú y devuelve la opción elegida por el usuario.
     * 
     * @return La opción introducida por el usuario.
     * @throws IOException
     */
    public char elegirOpcion() throws IOException {

        String op;
        do {

            op = _bufferedReader.readLine();
        } while (op.length() == 0);

        return op.charAt(0);
    }

    /**
     * Muestra las descargas del usuario.
     * 
     * @param datos Datos del cliente.
     */
    public static void insertarDescargas(DatosCliente[] datos) {

        System.out.println("Nombre     " + "  IP   ");
        for (int i = 0; i < datos.length; i++) {
            System.out.print(datos[i].getNombreUsuario());
            System.out.print("   ");
            System.out.print(datos[i].getIP());
            System.out.println("");
            System.out.println("Resultados anadidos; " + i);
        }
    }

    /**
     * Indica si estamos conectados a un servidor o no.
     * 
     * @return Verdadero si estamos conectados a un servidor y falso en caso
     * contrario.
     */
    public boolean conectado() {

        return _conectado;
    }

    /**
     * Muestra los resultados de una petición de búsqueda de un archivo
     * al servidor.
     * 
     * @param nombre Nombre del archivo solicitado.
     * @param lista Lista de coincidencias.
     */
    public void mostrarResultadosDeBusqueda(String nombre, Archivo[] lista) {

        _busqueda.setBusqueda(lista);

        mostrarMensaje("\nResultados de la búsqueda de " + nombre);
        mostrarMensaje("================================================");

        if (lista.length > 0) {
            for (Archivo archivo : lista) {
                mostrarMensaje(archivo.toString());
            }
        } else {
            mostrarMensaje("La búsqueda finalizó sin resultados. \n");
        }
    }

    /**
     * Muestra el menú de opciones al usuario.
     */
    private void mostrarMenu() {

        System.out.print("\n\n\t************** :::: M e n u :::: **************\n\n");
        System.out.print("\t1. Conexión a Servidor.\n");
        System.out.print("\t2. Conexión a Servidor por defecto.\n");
        System.out.print("\t3. Desconexión de Servidor.\n");
        System.out.print("\t4. Búsqueda de un Fichero.\n");
        System.out.print("\t5. Descarga de un Fichero.\n");
        System.out.print("\t6. Mostrar Archivos Compartidos.\n");
        System.out.print("\t0. Salir de la Aplicación.\n");
        System.out.print("\n\tElige la Opción: ");
    }

    /**
     * Solicita una petición de búsqueda de un fichero al servidor.
     * Para ello se le solicita al usuario el nombre del fichero 
     * a buscar.
     * 
     * @throws java.io.IOException
     */
    private void opcionBuscarFichero() throws IOException {

        mostrarMensaje("\nNombre del fichero a buscar: ");
        String nombre = _bufferedReader.readLine();

        _controlador.peticionBuscarFichero(nombre);
    }

    /**
     * Solicita una petición de conexión a un servidor.
     * Para ello se le solicitará al usuario la dirección IP y el 
     * puerto por el que se desea establecer la conexión con dicho
     * servidor.
     */
    private void opcionConexionAServidor() throws IOException, Exception {

        mostrarMensaje("\n\tIntroduce la IP del Servidor: ");
        String sIP = _bufferedReader.readLine();
        mostrarMensaje("\n\tIntroduce el Puerto del Servidor: ");
        String sPuerto = _bufferedReader.readLine();
        int puerto = Integer.parseInt(sPuerto);

        mostrarMensaje("\nConectando a servidor....");

        if (!_controlador.getGestorEGorilla().estaConectadoAServidor()) {
            _controlador.peticionConexionAServidor(sIP, puerto);
        } else {
            mostrarMensaje("Ya estás conectado a un Servidor.\n");
        }
    }

    /**
     * Solicita una petición de conexión al servidor que el cliente tenga
     * establecido por defecto.
     * 
     * @throws java.io.IOException
     * @throws java.lang.Exception
     */
    private void opcionConexionAServidorPorDefecto() throws IOException, Exception {

        // Recuperamos los parámetros del fichero de configuración
        String IPServidor = ControlConfiguracionCliente.obtenerInstancia().obtenerPropiedad(PropiedadCliente.IP_SERVIDOR.obtenerLiteral());
        int puertoServidor = Integer.parseInt(ControlConfiguracionCliente.obtenerInstancia().obtenerPropiedad(PropiedadCliente.PUERTO_SERVIDOR.obtenerLiteral()));

        mostrarMensaje("\nConectando a servidor....");
        if (!_controlador.getGestorEGorilla().estaConectadoAServidor()) {
            _controlador.peticionConexionAServidor(IPServidor, puertoServidor);
        } else {
            mostrarMensaje("Ya estas conectado a un servidor.\n");
        }
    }

    /**
     * Solicita una petición de descarga de un archivo al servidor.
     * Para ello se le solicita al usuario que introduzca el
     * hash del fichero a descargar.
     * 
     * @throws java.io.IOException
     */
    private void opcionDescargarFichero() throws IOException {

        mostrarMensaje("\nIntroduce el Hash del fichero a descargar: ");
        String hash = _bufferedReader.readLine();

        // TODO: coms asincronas, no hahy feedback
        _controlador.peticionDescargarFichero(_busqueda.dameArchivoPorHash(hash));
    }

    /**
     * Solicita una petición de desconexión al servidor.
     */
    private void opcionDesconexionDeServidor() {

        mostrarMensaje("\nDesconectando del servidor...");
        _controlador.peticionDesconexionDeServidor();
    }

    /**
     * Muestra los archivos compartidos del cliente. Para ello muestra al usuario
     * otro submenu con las opciones de listar todos, solo los completos o 
     * solo los incompletos
     */
    private void opcionMostrarCompartidos() throws IOException, Exception {

        char op;
        do {

            System.out.print("\n\n\t************** :::: M e n u de Compartidos :::: **************\n\n");
            System.out.print("\t1. Listar todos los Archivos Compartidos.\n");
            System.out.print("\t2. Listar todos los Archivos Compartidos Completos.\n");
            System.out.print("\t3. Listar todos los Archivos Compartidos Incompletos.\n");
            System.out.print("\t0. Volver al menu principal.\n");
            System.out.print("\n\tElige la Opción: ");

            op = elegirOpcion();

            switch (op) {
                case '1':
                    _controlador.peticionListarTodosCompartidos();

                    // PINTAR LOS ARCHIVOS
                    break;
                case '2':
                    _controlador.peticionListarCompartidosCompletos();
                    
                    // PINTAR LOS ARCHIVOS
                    break;
                case '3':
                    _controlador.peticionListarCompartidosIncompletos();
                    
                    // PINTAR LOS ARCHIVOS
                    break;
                case '0':
                    // Volvemos al menu anterior
                    run();
                    break;
                default:
                    mostrarMensaje("ERROR, La opción introducida no es válida!!\n");
            }
        } while (op != '0');
    }

    /**
     * Muestra un mensaje de despedida cuando el usuario decide
     * salir de la aplicación.
     */
    private void opcionSalir() {

        mostrarMensaje("\n\t\t\t\t\t\t\t\t\tAdiós!\n");
        System.exit(-1);
    }

    //------------------------------------------\\
    //      INTERFACE OBSERVADOREGORILLA        \\
    //------------------------------------------\\
    /**
     * Muestra un mensaje indicativo cuando la conexión con el servidor 
     * se ha realizado con éxito.
     * 
     * @param obj GestorEGorilla de la aplicación.
     * @param ip IP de la conexión.
     * @param port Puerto de la conexión.
     */
    @Override
    public void conexionCompletada(GestorEgorilla obj, String ip, int port) {

        mostrarMensaje("\nConectado a servidor, IP: " + ip + " Puerto: " + port);
    }

    /**
     * Muestra un mensaje indicativo cuando la desconexión con el servidor 
     * se ha realizado con éxito.
     * 
     * @param obj GestorEGorilla de la aplicación.
     */
    @Override
    public void desconexionCompletada(GestorEgorilla obj) {

        mostrarMensaje("\nDesconectado.");
    }

    /**
     * Muestra los resultados de una petición de búsqueda de un archivo
     * al servidor.
     * 
     * @param obj GestorEGorilla de la aplicación.
     * @param nombre Nombre del archivo solicitado.
     * @param lista Lista de coincidencias.
     */
    @Override
    public void resultadosBusqueda(GestorEgorilla obj, String cad, Archivo[] lista) {

        mostrarResultadosDeBusqueda(cad, lista);
    }

    /**
     * Se produce cuando una descarga se ha completado con éxito.
     * 
     * @param obj GestorEGorilla de la aplicación.
     */
    @Override
    public void finDescarga(GestorEgorilla obj, Archivo arch) {
        // TODO: descarga completada    
    }

    /**
     * Se produce cuando se ha caído el servidor.
     * 
     * @param obj GestorEGorilla de la aplicación.
     */
    @Override
    public void perdidaConexion(GestorEgorilla obj) {
    }

    @Override
    public void pausaDescarga(GestorEgorilla GestorEGorilla, Archivo arch) {
        //TODO PAUSA DESCARGA
    }

    @Override
    public void eliminarDescarga(GestorEgorilla GestorEGorilla, Archivo arch) {
        //TODO DESCARGA ELIMINADA
    }
}
