package gestorDeFicheros;

import java.util.*;
import java.io.*;

import datos.*;

public class ManejarIndices {

  public void crearFicheroIndices( File fichero, Archivo archivo, Vector<Fragmento> fragmentos ){
    Vector<Fragmento> fragTengo = new Vector<Fragmento>(),
      fragFaltan = fragmentos;
    Indices indices = new Indices( archivo, fragTengo, fragFaltan );
    try{      
      ByteArrayOutputStream bs = new ByteArrayOutputStream();
      ObjectOutputStream os = new ObjectOutputStream( bs );
      os.writeObject( indices );
      os.close();
      byte[] bytes = bs.toByteArray(); // devuelve byte[]

      FileOutputStream ficheroIndices = new FileOutputStream( fichero );
      BufferedOutputStream bufferedOutput = new BufferedOutputStream( ficheroIndices );
      bufferedOutput.write( bytes, 0, bytes.length );
      bufferedOutput.close();
      //creo que tambien hace falta cerrar el otro
      ficheroIndices.close();
    }catch( Exception e ){
      e.printStackTrace();
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
      //Pese a existir ya el fichero, como voy añadiendo fragmentos, el file aumenta
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
    //decir, guadar los ultimos fragmentos añadidos, aunque me parece q sera mas facil, xo
    //ineficiente, recuperar el array de fragmentos anterior, añadir los fragmentos, y
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
