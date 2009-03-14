/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package clienteegorilla.control.chunk;


import clienteegorilla.control.gestionficheros.*;
import datos.*;
import java.io.*;
import java.util.*;

/**
 *
 * un calchito de un fichero, para enviarlo por el frio y oscuro internet
 *
 * @author Pitidecaner
 */
public class FileChunk implements Serializable{
    
    //private int cachosDisponibles;
    private int cachosEnviados;
    private ArrayList< Byte[] > cachos;

    private String _directorio;
    private String _nombreFichero;
    private String _hashFichero;

    public FileChunk(String directorioCompartidos, String hashFichero) throws IOException{ //Este lee cachos de un fichero existente, dirCompartidos
      //Hacer q recupere el nombre de la lista de archivos compartidos del cliente
      //FileInputStream fileInput = new FileInputStream( new File( directorioCompartidos+"\\"+getNombreFichero( hashFichero ) ) );
      //File fComp = new File( directorioCompartidos+"\\"+ "IMAGE8.GIF" );
      //File fComp = new File( "C:\\egorilla\\branches\\cliente\\v2_design\\compartidos\\IMAGE8.GIF" );
      File fComp = new File( directorioCompartidos+"\\"+ getNombreFichero( directorioCompartidos, hashFichero ) );
      FileInputStream fileInput = new FileInputStream( fComp );
      BufferedInputStream bufferedInput = new BufferedInputStream( fileInput );
      byte[] array = new byte[512];
      Byte[] cacho;

      //String res = new String();
      cachos = new ArrayList< Byte[] >();

      System.out.println( "Ruta <"+fComp.getAbsolutePath()+">" );

      int leidos = bufferedInput.read( array );
      //System.out.println( "Le "+leidos );
      int totalLeidos = 0;
      //ArrayList<Byte> arrayB = new ArrayList<Byte>();
      //String res = new String( array, 0, leidos );
      while( leidos > 0 ){
        //res += new String( array, 0, leidos );
        cacho = new Byte[leidos];
        for( int i = 0;  i < leidos;  i++ ){
          cacho[ i ] = new Byte( array[ i ] );
        }
        cachos.add( cacho );
        totalLeidos += leidos;
	leidos = bufferedInput.read( array );
        //System.out.println( "Le "+leidos );
      }
      bufferedInput.close();
      System.out.println( "Bytes totales <"+totalLeidos+">" );
      System.out.println( "El fichero tiene <"+cachos.size()+"> parte/es" );
    }

    public FileChunk( String directorioDescargas, String nombreFicheroDescargado, String hashFicheroDescargado ){
      _directorio = directorioDescargas;
      _nombreFichero = nombreFicheroDescargado;
      _hashFichero = hashFicheroDescargado;
    }

    //Tiene q haber otro constructor q guarde los cachos recibidos en un archivo
    //dirIncoming

    public Byte[] getCacho(){
      cachosEnviados++;
      return cachos.get( cachosEnviados - 1 );
    }

    public String getNombreFichero( String directorio, String hash ){
      String nombre = "";
      boolean encontrado = false;
      GestorCompartidos compartidos = null;
      try{
      compartidos = new GestorCompartidos( new File( directorio ) );
      }catch(Exception e){}
      ListaArchivos lista = compartidos.getArchivosCompartidos();
      
      for( int i = 0;  i < lista.size() && encontrado == false;  i++ ){
        if( lista.get( i ).getHash().compareTo( hash ) == 0 ){
          encontrado = true;
          nombre = lista.get( i ).getNombre(); 
        }
      }

      return nombre;
    }

    public boolean masCachos(){
      //No se si es cachosEnviados-1, +1 o sin nada
      return cachos.size() != cachosEnviados;
    }

    public int getCantidadCachos(){
      //No se si es cachosEnviados-1, +1 o sin nada
      return cachos.size();
    }

    public void setCachos( ArrayList< Byte[] > cachosDescargados ) throws IOException{
      Byte[] cacho;

      File fDes = new File( _directorio+"\\"+_nombreFichero );
      FileOutputStream fileOutput = new FileOutputStream( fDes );
      BufferedOutputStream bufferedOutput = new BufferedOutputStream( fileOutput );

      System.out.println( "Ruta <"+fDes.getAbsolutePath()+">" );

      for( int i = 0;  i < cachosDescargados.size(); i++ ){
        //bufferedOutput.write( cachosDescargados.get( i ) );
        cacho = cachosDescargados.get( i );
        //System.out.println("Cacho numero <"+ i +"> - cacho "+cacho+ "- bytes del cacho <"+cacho.length+">");
        for(int j = 0;  j < cacho.length ;  j++ ){
          //System.out.println("Byte numero <"+ j+ ">");
          bufferedOutput.write( cacho[ j ].byteValue() );
        }
      }
      
      bufferedOutput.close();

      //Comprobar q coinciden los MD5
    }

}
