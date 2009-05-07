package gestorDeFicheros;

import java.util.*;
import java.io.*;

import datos.*;

/**
 *
 * @author Ivan Munsuri Ibanez
 */
public class ManejarIndices {

  public void crearFicheroIndices( File fichero, Archivo archivo, Vector<Fragmento> fragmentos ){
    Vector<Fragmento> fragTengo = new Vector<Fragmento>(),
      fragFaltan = fragmentos;
    Indices indices = new Indices( archivo, fragTengo, fragFaltan );
    ByteArrayOutputStream bs = new ByteArrayOutputStream();
    try{      
      ObjectOutputStream os = new ObjectOutputStream( bs );
      os.writeObject( indices );
      os.close();
      
    }catch( IOException e ){
        System.out.println("Error -> posibles causas: ");
        System.out.println( "\tProblemas al crear un flujo de bytes serializable" );
        System.out.println( "\tProblemas al serializar el objeto indice" );
        System.out.println( "\tProblemas al cerrar el flujo serializable" );
        //e.printStackTrace();
    }

    byte[] bytes = bs.toByteArray(); // devuelve byte[]

    try{
      FileOutputStream ficheroIndices = new FileOutputStream( fichero );
      BufferedOutputStream bufferedOutput = new BufferedOutputStream( ficheroIndices );
      bufferedOutput.write( bytes, 0, bytes.length );
      bufferedOutput.close();
      //creo que tambien hace falta cerrar el otro
      ficheroIndices.close();
    }catch( FileNotFoundException e ){
        System.out.println( "No existe el fichero <"+fichero.getName()+">" );
    }catch( IOException e ){
        System.out.println("Error -> posibles causas: ");
        System.out.println( "\tProblemas al escribir en el fichero <"+fichero.getName()+">" );
        System.out.println( "\tProblemas al cerrar el flujo o el fichero <"+fichero.getName()+">" );
        //e.printStackTrace();
    }
  }

  public boolean borrarFicheroIndices( File fichero ){
    //En principio lo borro directamente
    return fichero.delete();
  }

  //Fusionar el crear y el guardar, argumento en crear y se unen
  //Ademas, llevar estos a una clase del estilo TratarIndices

  public void guardarFicheroIndices( File fichero, Indices indices ){
    try{      
      ByteArrayOutputStream bs = new ByteArrayOutputStream();
      ObjectOutputStream os = new ObjectOutputStream( bs );
      os.writeObject( indices );
      os.close();
      byte[] bytes = bs.toByteArray(); // devuelve byte[]

      FileOutputStream ficheroIndices = new FileOutputStream( fichero );
      BufferedOutputStream bufferedOutput = new BufferedOutputStream( ficheroIndices );
      //Pese a existir ya el fichero, como voy anadiendo fragmentos, el file aumenta
      //por lo q sobreescribo el fichero con mas bytes y no menos, ya que si fueran menos
      //quedarian bytes malos en el file
      bufferedOutput.write( bytes, 0, bytes.length );
      bufferedOutput.close();
      //creo que tambien hace falta cerrar el otro
      ficheroIndices.close();
    }catch( Exception e ){
      e.printStackTrace();
    }
  }

  public Indices leeFicheroIndices( File fichero ){
    Indices indices = null;
    try{
    //Deserializo el Array de fragmentos de archivo de indices
    FileInputStream ficheroIndices = new FileInputStream( fichero );
    byte[] bytes = new byte[ (int)fichero.length() ];        
    int byteIndicesLeidos = ficheroIndices.read( bytes );
    ficheroIndices.close();
    //para el ensamblador q es el q guarda, seria mejor guardar los bytes adicionales, es
    //decir, guadar los ultimos fragmentos anadidos, aunque me parece q sera mas facil, xo
    //ineficiente, recuperar el array de fragmentos anterior, anadir los fragmentos, y
    //sobreescribir todo
       
    ByteArrayInputStream bs = new ByteArrayInputStream( bytes ); // bytes es el byte[]
    ObjectInputStream is = new ObjectInputStream( bs );
    indices = (Indices)is.readObject();
    is.close();
    }catch( Exception e ){
      e.printStackTrace();
      return indices;
    }
    return indices;
  }

}
