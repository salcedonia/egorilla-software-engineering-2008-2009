package gui.consola;

import control.ControladorConsola;
import datos.Archivo;
import gestorDeConfiguracion.ControlConfiguracionCliente;
import mensajes.serverclient.DatosCliente;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Logger;
import peerToPeer.egorilla.ObservadorGestorEgorilla;

/**
 * Interfaz en modo consola de la aplicacion Cliente eGorilla.
 * 
 * @author Ivan Munsuri, Javier Salcedo, Javier Sánchez
 */
public class GUIConsola implements ObservadorGestorEgorilla {

    /**
     * Log para recopilar la información en un fichero de log.
     */
    private static final Logger _log = Logger.getLogger(GUIConsola.class.getName());
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
     * Constructor de la clase GUIConsola.
     * 
     * @param controlador Controlador de la aplicación en modo consola.
     * @throws java.io.IOException
     */
    public GUIConsola(ControladorConsola controlador) throws IOException, Exception {

        //Asocio la Vista (el objeto GUIConsola) con su controlador.
        _controlador = controlador;
        //Asocio al Controlador con su Vista.
        controlador._guiConsola = this;
        
        //Arranca el menú de consola de la aplicación.
        mostrarMenu();
    }
    
    

    /**
     * pinta este mensaje por pantalla
     * 
     * @param string
     */
    public synchronized void mostrarMensaje(String string) {
        System.out.print(string);
    }

    /**
     * Muestra el menú de la aplicación en modo consola e invoca a los métodos 
     * correspondientes del ControladorConsola en función de la opción elegida
     * por el usuario.
     * 
     * @throws java.io.IOException Se lanza cuando se produce algún error de IO.
     */
    public void mostrarMenu() throws Exception {
        String cad;
        char op;
        do {
            op = menu();
            switch (op) {
                case '1':
                    mostrarMensaje("\n\tIntroduce IP del servidor: ");
                    String sIP = _bufferedReader.readLine();
                    mostrarMensaje("\n\tIntroduce puerto del servidor: ");
                    String sPuerto = _bufferedReader.readLine();
                    int puerto = Integer.parseInt(sPuerto);
                    //Invoco el método correspondiente del controlador.
                    _controlador.peticionConexionAServidor(sIP, puerto);
                    break;

                case '2':
                    String ServerHost = ControlConfiguracionCliente.obtenerInstanciaDefecto().obtenerPropiedad("IpServidor");
                    int puertoS = Integer.parseInt(ControlConfiguracionCliente.obtenerInstanciaDefecto().obtenerPropiedad("PuertoServidor"));

                    mostrarMensaje("\nConectando a ");
                    mostrarMensaje(ServerHost + ":" + puertoS);
                    _controlador.peticionConexionAServidor(ServerHost, puertoS);
                    break;

                case '3':
                    //Invoco el método correspondiente del controlador.
                    _controlador.peticionDesconexionDeServidor();
                    break;

                case '4':
                    mostrarMensaje("\nNombre a buscar: "); /*Mostrar mensaje de error si no se ha conectado antes*/
                    cad = _bufferedReader.readLine();
                    //creo q hay que quitar el retorno de carro
                    _controlador.peticionBuscarFichero(cad);
                    // TODO: feedback es una comunicación asincrona

//                    RespuestaPeticionConsulta respuestaConsulta = ControlAplicacion.peticionBuscarFichero(cad);
//                    if (respuestaConsulta.getLista().length > 0) {
//                        System.out.println("Archivos en el table: <" + respuestaConsulta.getLista().length + ">");
//                        insertarBusquedas(respuestaConsulta.getLista());
//                    } else {
//                        System.out.print("\nSin resultados.\n");
//                    }
                    break;
                case '5':
                    mostrarMensaje("\nMD5 del fichero a descargar: ");
                    cad = _bufferedReader.readLine();


                    // TODO: coms asincronas, no hahy feedback
                    _controlador.peticionDescargarFichero("nombre", cad);

//                    RespuestaPeticionDescarga respuestaDescarga = ControlAplicacion.peticionDescargarFichero("nombre",cad);
//                    if (respuestaDescarga.getLista().length > 0) {
//                        System.out.println("<" + respuestaDescarga.getLista().length + "> clientes con el archivo <" + cad + ">");
//                        insertarDescargas(respuestaDescarga.getLista());
//                    } else {
//                        System.out.print("\nNo existe el fichero.\n");
//                    }
                    break;
                case '0':
                    mostrarMensaje("\n\t\t\t\t\t\t\t\t\tBye!\n");
                    System.exit(-1);
                    break;
                default:
                    mostrarMensaje("ERROR, opcion no valida\n");
            }
        } while (op != '0');
    }

    /**
     * Pinta el menú de consola y devuelve la opción elegida por el usuario.
     * @return Opción tecleada por el usuario.
     * @throws IOException
     */
    public char menu() throws IOException {
        char op;
        String aux;
        do {
            System.out.print("\n\n\t************** :::: M e n u :::: **************\n\n");
            System.out.print("\t1. Conectar.\n");
            System.out.print("\t2. Conectar servidor por defecto.\n");
            System.out.print("\t3. Desconectar.\n");
            System.out.print("\t4. Buscar.          \n");
            System.out.print("\t5. Descargar.          \n");
            System.out.print("\t0. Salir.\n");
            System.out.print("\n\tOpcion: ");

            aux = _bufferedReader.readLine();

        } while (aux.length() == 0);

        op = aux.charAt(0);

        return op;
    }

    /**
     * Muestra el resultado de una búsqueda.
     * 
     * @param archivo Array de archivos correspondientes al resultado de la 
     * búsqueda.
     */
    public void mostrarBusquedas(Archivo[] archivo) {

        System.out.println("Nombre   " + "Tamano   " + "Disponibilidad   " + "Fuentes   " + "Tipo   " + "Identificador de archivo");
        for (int i = 0; i < archivo.length; i++) {
            System.out.print(archivo[i]._nombre);
            System.out.print("   ");
            System.out.print(String.valueOf(archivo[i]._tamano));
            System.out.print("   ");
            System.out.print("---");
            System.out.print("   ");
            System.out.print("---");
            System.out.print("   ");
            System.out.print(archivo[i]._tipo.toString());
            System.out.print("   ");
            System.out.print(archivo[i]._hash);
            System.out.println("");
            System.out.println("Resultados anadidos; " + i);
        }

    }

    /**
     * 
     * @param datos
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

    //--------------------------------------------------------------------------
    //           INTERFACE OBSERVADOREGORILLA
    //--------------------------------------------------------------------------
    public void conexionCompleta(String ip, int port) {

        _conectado = true;

        // notifica a la gui:
        mostrarMensaje("Conexión satisfactoria con Servidor " +
                ip + ":" + port + "\n");
    }

    public void resultadosBusqueda(String cad, Archivo[] lista) {

        // notifica a la gui:
        mostrarMensaje("\nresultados de la busqueda: " + cad);
        mostrarMensaje("================================================");

        if (lista.length > 0) {
            for (Archivo archivo : lista) {
                mostrarMensaje(archivo.toString());
            }
        } else {
            mostrarMensaje("no hubo resultados! \n");
        }
    }

    public void finDescarga() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void perdidaConexion() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
