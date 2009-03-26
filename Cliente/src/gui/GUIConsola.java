package gui;

import control.ControlAplicacion;
import datos.Archivo;
import mensajes.serverclient.DatosCliente;
import mensajes.serverclient.RespuestaPeticionConsulta;
import mensajes.serverclient.RespuestaPeticionDescarga;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Interfaz en modo consola de la aplicacion Cliente eGorilla.
 * 
 * @author Ivan Munsuri, Javier Salcedo
 */
public class GUIConsola {

    /**
     * Creacion del flujo para leer datos.
     */
    public static InputStreamReader isr = new InputStreamReader(System.in); 
    
    /**
     * Creacion del filtro para optimizar la lectura de datos.
     */
    public static BufferedReader br = new BufferedReader(isr);

    /**
     * 
     * @param cad
     * @throws java.io.IOException
     */
    public GUIConsola(String cad) throws IOException{
    
        mostrarMenu(cad);
    }
    
    /**
     * 
     * @param cad
     * @throws java.io.IOException
     * @throws java.lang.NumberFormatException
     */
    private static void mostrarMenu(String cad) throws IOException, NumberFormatException {

        char op;
        do {
            op = menu();
            switch (op) {
                case '1':
                    System.out.println("\nConectando...");
                    System.out.print("\n\tIntroduce IP del servidor: ");
                    String sIP = br.readLine();
                    System.out.print("\n\tIntroduce puerto del servidor: ");
                    String sPuerto = br.readLine();
                    int puerto = Integer.parseInt(sPuerto);
                    ControlAplicacion.conectar(sIP, puerto);
                    System.out.println("\nConectado.");
                    break;
                case '2':
                    System.out.println("\nDesconectando...");
                    ControlAplicacion.close();
                    System.out.println("\nDesconectado.");
                    break;
                case '3':
                    System.out.print("\nNombre a buscar: "); /*Mostrar mensaje de error si no se ha conectado antes*/
                    cad = br.readLine();
                    //creo q hay que quitar el retorno de carro
                    RespuestaPeticionConsulta respuestaConsulta = ControlAplicacion.consultar(cad);
                    if (respuestaConsulta.getLista().length > 0) {
                        System.out.println("Archivos en el table: <" + respuestaConsulta.getLista().length + ">");
                        insertarBusquedas(respuestaConsulta.getLista());
                    } else {
                        System.out.print("\nSin resultados.\n");
                    }
                    break;
                case '4':
                    System.out.print("\nMD5 del fichero a descargar: ");
                    cad = br.readLine();
                    //creo q hay que quitar el retorno de carro
                    RespuestaPeticionDescarga respuestaDescarga = ControlAplicacion.bajar(cad);
                    if (respuestaDescarga.getLista().length > 0) {
                        System.out.println("<" + respuestaDescarga.getLista().length + "> clientes con el archivo <" + cad + ">");
                        insertarDescargas(respuestaDescarga.getLista());
                    } else {
                        System.out.print("\nNo existe el fichero.\n");
                    }
                    break;
                case '0':
                    System.out.print("\n\t\t\t\t\t\t\t\t\tBye!\n");
                    System.exit(-1);
                    break;
                default:
                    System.out.print("ERROR, opcion no valida\n");
            }
        } while (op != '0');
    }
     
    /**
     * 
     * @return
     * @throws IOException
     */
    public static char menu() throws IOException {
        char op;

        System.out.print("\n\n\t************** :::: M e n u :::: **************\n\n");
        System.out.print("\t1. Conectar.\n");
        System.out.print("\t2. Desconectar.\n");
        System.out.print("\t3. Buscar.          \n");
        System.out.print("\t4. Descargar.          \n");
        System.out.print("\t0. Salir.\n");
        System.out.print("\n\tOpcion: ");

        //String texto1=br.readLine();
        //int num1=Integer.parseInt(texto1);
        String aux = br.readLine();
        op = aux.charAt(0);
        //op = (char)br.read();
        //br.skip(1); xa saltar el retorno de carro
        return op;
    }
        
    /**
     * 
     * @param archivo
     */
    public static void insertarBusquedas(Archivo[] archivo) {

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
            System.out.println("Resultados anadidos; "+i);
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
            System.out.println("Resultados anadidos; "+i);
        }

    }
}
