/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clienteegorilla;

import clienteegorilla.control.*;
import clienteegorilla.control.gestionficheros.GestorCompartidos;
import paquete.*;
import java.io.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase que implemeta un test de prueba del servidor.
 * 
 * @author pitidecaner
 * @author Salcedonia
 */
public class ClienteTest {


//Creacion del flujo para leer datos
		public static InputStreamReader isr=new InputStreamReader(System.in);
		
		//Creacion del filtro para optimizar la lectura de datos
		public static BufferedReader br=new BufferedReader(isr);
    /**
     * Método main del test.
     * 
     * @param args Argumentos de la aplicación de la línea de comandos.
     */
    public static void main(String[] args)throws IOException{

      String cad = null;

      // TODO: ojo con esto, el puerto de ecucha esta puesto muy mal
      // hay que leerlo de la configuracion. properties o lo que sea
      ServidorFicheros fs = new ServidorFicheros(4000);
      fs.start();
        
        String nombreDirectorio = "compartidos";
           ControlAplicacion.compartidos( nombreDirectorio );

        /*try {
            Thread.sleep(3001);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        //eGorillaControlGeneral.instancia().iniciar();

      char op;
      do{
        op = menu();
        switch(op){
          case '1': System.out.println("\nConectando...");
                      ControlAplicacion.conectar();
                      System.out.println("\nConectado.");
		      break;

          case '2': System.out.println("\nDesconectando...");
                      ControlAplicacion.close();
                      System.out.println("\nDesconectado.");
		      break;

          case '3': System.out.print("\nNombre a buscar: "); /*Mostrar mensaje de error si no se ha conectado antes*/
                      cad = br.readLine();
                      //creo q hay que quitar el retorno de carro
                      RespuestaPeticionConsulta respuestaConsulta = ControlAplicacion.consultar( cad );
                      if( respuestaConsulta.getLista().length > 0) {
                        System.out.println( "Archivos en el table: <"+ respuestaConsulta.getLista().length +">" );
                        insertarBusquedas( respuestaConsulta.getLista() );
                      }else{
                        System.out.print("\nSin resultados.\n");
                      }
		      break;

          case '4': System.out.print("\nMD5 del fichero a descargar: ");
                      cad = br.readLine();
                      //creo q hay que quitar el retorno de carro
                      RespuestaPeticionDescarga respuestaDescarga = ControlAplicacion.bajar( cad );
                      if( respuestaDescarga.getLista().length > 0) {
                        System.out.println( "<"+respuestaDescarga.getLista().length+"> clientes con el archivo <"+ cad +">" );
                        insertarDescargas( respuestaDescarga.getLista() );
                      }else{
                        System.out.print("\nNo existe el fichero.\n");
                      }
		      break;

          case '0':   System.out.print("\n\t\t\t\t\t\t\t\t\tBye!\n");
                      System.exit(-1);
	              break;
          
          default:    System.out.print("ERROR, opcion no valida\n");
        }
      }while( op != '0' );

    }

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

    public static void insertarBusquedas(Archivo[] archivo){
        
        System.out.println( "Nombre   " + "Tamano   "+ "Disponibilidad   "+ "Fuentes   "+ "Tipo   "+ "Identificador de archivo" );
        for (int i=0; i < archivo.length; i++) {
            System.out.print( archivo[i]._nombre); System.out.print("   ");
            System.out.print( String.valueOf(archivo[i]._tamano) );System.out.print("   ");
            System.out.print( "---" );System.out.print("   ");
            System.out.print( "---" );System.out.print("   ");
            System.out.print( archivo[i]._tipo.toString() );System.out.print("   ");
            System.out.print( archivo[i]._hash );System.out.println("");
            //System.out.println("Resultados anadidos; "+i);
        }
                
    }

    public static void insertarDescargas(DatosCliente[] datos){
        
        System.out.println( "Nombre     " + "  IP   " );
        for (int i=0; i < datos.length; i++) {
            System.out.print( datos[i].getNombreUsuario() ); System.out.print("   ");
            System.out.print( datos[i].getIP() ); System.out.println("");
            //System.out.println("Resultados anadidos; "+i);
        }
                
    }
}
