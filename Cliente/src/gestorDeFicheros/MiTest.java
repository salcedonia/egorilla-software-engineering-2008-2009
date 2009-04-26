package gestorDeFicheros;

import datos.*;
import java.util.*;
import java.io.*;

public class MiTest {
  
  public static void main(String[] args){

    GestorDisco gestorDisco = new GestorDisco();

    Fragmentador fragmentador = gestorDisco.getFragmentador();

    Ensamblador ensamblador = gestorDisco.getEnsamblador();

    String hashBueno = "1fde78d1baadbfb3c2554f3651b40330";
    Vector<Fragmento> fragmentosBueno;
    //framentos de un fichero completo
    fragmentosBueno = fragmentador.queFragmentosTienes( hashBueno );
    System.out.println( "cantidadFragmentosBueno "+fragmentosBueno.size() );
    for( int i = 0;  i < fragmentosBueno.size();  i++ ){
      System.out.println( fragmentosBueno.get( i ).toString() );
    }    

    //framentos de un fichero que no existe
    String hashMalo = "1fde78d1baadbfb3c2554f3651b40331";
    Vector<Fragmento> fragmentosMalo = fragmentador.queFragmentosTienes( hashMalo );
    System.out.println( "cantidadFragmentosMalo "+fragmentosMalo );

    String nombreFileComJar = "comunicaciones.jar";
    File fileComJar = new File( nombreFileComJar );
    String hashAux = MD5Sum.getFileMD5Sum( fileComJar );
    Archivo archivoComJar = new Archivo( fileComJar.getName(), hashAux, fileComJar.length(), 
        TipoArchivo.ARCHIVO);
    System.out.println( archivoComJar.toString() );
    ensamblador.nuevoArchivoTemporal( archivoComJar );
    //Debo comprobar que el archivo generado tiene el tamaño indicado   
    File fileTemp = new File( gestorDisco.getDirectorioTemporales()+"//"+nombreFileComJar+".tmp" );
    //No hace falta comprobarlo, pero casi en un 99% el hash debe ser diferente
    String hashTemp = MD5Sum.getFileMD5Sum( fileTemp );
    //hashTemp es el hash del archivo temporal en cada momento, es decir, cambiara en funcion de 
    //los fragmentos, hasta tener todos, qentonces coincidira con el iriginal
    Archivo archivoTemp = new Archivo( fileTemp.getName(), hashTemp, fileTemp.length(), 
        TipoArchivo.ARCHIVO);
    System.out.println( archivoTemp.toString() );

    //framentos de un fichero incompleto - para esto se necesita previa creacion    
    Vector<Fragmento> fragmentosSin = fragmentador.queFragmentosTienes( hashAux );
    //Como acabo de crear el fichero temporal no tengo ningun fragmento en los indices
    System.out.println( "cantidadFragmentosSin "+fragmentosSin.size() );
    for( int i = 0;  i < fragmentosSin.size();  i++ ){
      //System.out.println( fragmentos.get( i ).toString() );
    }

    System.out.println( "cantidadFragmentosBueno "+fragmentosBueno.size() );
    Byte[] bytesFragmento3 = fragmentador.dameBytesDelFragmento( fragmentosBueno.get( 3 ) );
    Byte[] bytesFragmento0 = fragmentador.dameBytesDelFragmento( fragmentosBueno.get( 0 ) );
    Byte[] bytesFragmento11 = fragmentador.dameBytesDelFragmento( fragmentosBueno.get( 11 ) );
    Byte[] bytesFragmento16 = fragmentador.dameBytesDelFragmento( fragmentosBueno.get( 16 ) );
    Byte[] bytesFragmento6 = fragmentador.dameBytesDelFragmento( fragmentosBueno.get( 6 ) );
    System.out.println( "bF2:"+bytesFragmento3.length+" bF0:"+bytesFragmento0.length+" bF11:"+
        bytesFragmento11.length+" bF16:"+ bytesFragmento16.length+" bF6:"+ bytesFragmento6.length);
    //Comprobar al menos que no salen null cuando deberían funcionar
    //Y que son lecturas de 512 menos, posiblemente, el ultimo fragmento del fichero

    //Pido los bytes de un fragmento con hash malo de NO existir
    Fragmento fragmentoErroneoHash = new Fragmento( fragmentosBueno.get( 1 ).getNombre(), 
        "1fde78d1baadbfb3c2554f3651b40331", fragmentosBueno.get( 1 ).getTama(), 
        fragmentosBueno.get( 1 ).getOffset() );
    Byte[] bytesFragmentoErroneoHash = fragmentador.dameBytesDelFragmento( fragmentoErroneoHash );
    System.out.println( "bFErroneoHash:"+bytesFragmentoErroneoHash );

    //Pido los bytes de un fragmento con hash malo de existir en otro file
    Fragmento fragmentoErroneoHash2 = new Fragmento( fragmentosBueno.get( 1 ).getNombre(), 
        fragmentosBueno.get( 4 ).getHash(), fragmentosBueno.get( 1 ).getTama(), 
        fragmentosBueno.get( 1 ).getOffset() );
    Byte[] bytesFragmentoErroneoHash2 = fragmentador.dameBytesDelFragmento( fragmentoErroneoHash2 );
    System.out.println( "bFErroneoHash2:"+bytesFragmentoErroneoHash2 );

    //Pido los bytes de un fragmento con offset malo
    Fragmento fragmentoErroneoOffset = new Fragmento( fragmentosBueno.get( 1 ).getNombre(), 
        fragmentosBueno.get( 1 ).getHash(), fragmentosBueno.get( 1 ).getTama(), 
        fragmentosBueno.get( 16 ).getOffset() );
    Byte[] bytesFragmentoErroneoOffset = fragmentador.dameBytesDelFragmento( fragmentoErroneoOffset );
    System.out.println( "bFErroneoOffset:"+bytesFragmentoErroneoOffset );
    //Hacer mas pruebas de este


    //Prueba de guardar un Fragmento-Byte sobre un fichero del que tengo 0 partes 
    //(esta dado de alta en temporales)
    Vector<Fragmento> fragmentosActualesComJar = fragmentador.queFragmentosTienes( 
        "538b5b9d439778e2527a3e163a1e623b" );
    System.out.println( "cantidadFragmentosActualesConJar "+fragmentosActualesComJar.size() );

    Byte[] bytesFragmentoComJar = bytesFragmento3; //cojo cualquier array de bytes para esta prueba
    Fragmento fragmentoNuevoComJar =  new Fragmento( nombreFileComJar, 
        "538b5b9d439778e2527a3e163a1e623b", 14724, 1024 );
    ensamblador.guardarFragmentoEnArchivo( fragmentoNuevoComJar, bytesFragmentoComJar );
    fragmentosActualesComJar = fragmentador.queFragmentosTienes( 
        "538b5b9d439778e2527a3e163a1e623b" );
    System.out.println( "cantidadFragmentosActualesConJar "+fragmentosActualesComJar.size() );

    //probar a guardar uno al que solo le falta un fragmento y qse complete bien
    
    //probar otro que solo le falta un fragmento y q el utlimo fragmento este mal
    //(no coincidira el hash final)

  }
}
