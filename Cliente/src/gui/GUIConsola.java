package gui;

import control.ControlAplicacion;
import datos.Archivo;
import gestorDeConfiguracion.ControlConfiguracionCliente;
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

    private ControlAplicacion _control;
    
    /**
     * Creacion del flujo para leer datos.
     */
    public  InputStreamReader isr = new InputStreamReader(System.in); 
    
    /**
     * Creacion del filtro para optimizar la lectura de datos.
     */
    public  BufferedReader br = new BufferedReader(isr);

    /**
     * 
     * @param cad
     * @throws java.io.IOException
     */
    public GUIConsola(ControlAplicacion  control) throws IOException{
    
        _control = control;
    }
    
    /**
     * 
     * @param cad
     * @throws java.io.IOException
     * @throws java.lang.NumberFormatException
     */
    public  void mostrarMenu() throws Exception {

        String cad;
        
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
                    _control.conectar(sIP, puerto);
                    System.out.println("\nConectando....");
                    break;
                    
                case '2':
                      String ServerHost = ControlConfiguracionCliente.obtenerInstanciaDefecto().obtenerPropiedad("IpServidor");
                      int    puertoS    = Integer.parseInt(ControlConfiguracionCliente.obtenerInstanciaDefecto().obtenerPropiedad("PuertoServidor"));
                    
                      System.out.print("\nConectando a ");
                      System.out.print(ServerHost +":"+ puertoS);
                      _control.conectar(ServerHost, puertoS);     
                   break;
                   
                case '3':
                    System.out.println("\nDesconectando...");
                    _control.close();
                    System.out.println("\nDesconectado.");
                    break;
                    
                case '4':
                    System.out.print("\nNombre a buscar: "); /*Mostrar mensaje de error si no se ha conectado antes*/
                    cad = br.readLine();
                    //creo q hay que quitar el retorno de carro
                    _control.consultar(cad);
                    // TODO: feedback es una comunicación asincrona
                    
//                    RespuestaPeticionConsulta respuestaConsulta = ControlAplicacion.consultar(cad);
//                    if (respuestaConsulta.getLista().length > 0) {
//                        System.out.println("Archivos en el table: <" + respuestaConsulta.getLista().length + ">");
//                        insertarBusquedas(respuestaConsulta.getLista());
//                    } else {
//                        System.out.print("\nSin resultados.\n");
//                    }
                    break;
                case '5':
                    System.out.print("\nMD5 del fichero a descargar: ");
                    cad = br.readLine();       
             
                    
                    // TODO: coms asincronas, no hahy feedback
                    _control.bajar("nombre",cad);
                    
//                    RespuestaPeticionDescarga respuestaDescarga = ControlAplicacion.bajar("nombre",cad);
//                    if (respuestaDescarga.getLista().length > 0) {
//                        System.out.println("<" + respuestaDescarga.getLista().length + "> clientes con el archivo <" + cad + ">");
//                        insertarDescargas(respuestaDescarga.getLista());
//                    } else {
//                        System.out.print("\nNo existe el fichero.\n");
//                    }
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
    public char menu() throws IOException {
        char op;

        System.out.print("\n\n\t************** :::: M e n u :::: **************\n\n");
        System.out.print("\t1. Conectar.\n");
        System.out.print("\t2. Conectar servidor por defecto.\n");
        System.out.print("\t3. Desconectar.\n");
        System.out.print("\t4. Buscar.          \n");
        System.out.print("\t5. Descargar.          \n");
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
    public void insertarBusquedas(Archivo[] archivo) {

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
