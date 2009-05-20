/* 
	This file is part of eGorilla.

    eGorilla is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    eGorilla is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with eGorilla.  If not, see <http://www.gnu.org/licenses/>.
*/
package gestorDeFicheros;

import java.util.*;
import java.io.*;

import datos.*;
import gestorDeErrores.ControlDeErrores;
import gestorDeErrores.ErrorEGorilla;

/**
 * Clase que ofrece las operaciones que se pueden realizar sobre los indices de los archivos.
 *
 * @author Ivan Munsuri Ibanez
 */
public class ManejarIndices {

  /**
   * Crea el fichero de indices con la informacion inicial necesaria para un archivo concreto.
   * @param fichero es el fichero de indices que tendra toda la informacion.
   * @param archivo es el objeto que representa el archivo en concreto.
   * @param fragmentos es la lista de fragmentos que faltan de dicho archivo.
   */
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
        /*System.out.println("Error -> posibles causas: ");
        System.out.println( "\tProblemas al crear un flujo de bytes serializable" );
        System.out.println( "\tProblemas al serializar el objeto indice" );
        System.out.println( "\tProblemas al cerrar el flujo serializable" );*/
        ControlDeErrores.getInstancia().registrarError(ErrorEGorilla.ERROR_DISCO,
                                "Error -> posibles causas: "+
                                "\tProblemas al crear un flujo de bytes serializable"+
                                "\tProblemas al serializar el objeto indice"+
                                "\tProblemas al cerrar el flujo serializable");
        //e.printStackTrace();
    }

    byte[] bytes = bs.toByteArray(); // devuelve byte[]

    try{
      if( fichero.exists() == true ){
        String nombreNuevoFichero = fichero.getName();
        nombreNuevoFichero += "_"+archivo.getHash();
        fichero = new File( nombreNuevoFichero );
      }
      FileOutputStream ficheroIndices = new FileOutputStream( fichero );
      BufferedOutputStream bufferedOutput = new BufferedOutputStream( ficheroIndices );
      bufferedOutput.write( bytes, 0, bytes.length );
      bufferedOutput.close();
      //creo que tambien hace falta cerrar el otro
      ficheroIndices.close();
    }catch( FileNotFoundException e ){
        System.out.println( "No existe el fichero <"+fichero.getName()+">" );
    }catch( IOException e ){
        /*System.out.println("Error -> posibles causas: ");
        System.out.println( "\tProblemas al escribir en el fichero <"+fichero.getName()+">" );
        System.out.println( "\tProblemas al cerrar el flujo o el fichero <"+fichero.getName()+">" );*/
        ControlDeErrores.getInstancia().registrarError(ErrorEGorilla.ERROR_DISCO,
                                "Error -> posibles causas: "+
                                "\tProblemas al escribir en el fichero <"+fichero.getName()+">"+
                                "\tProblemas al cerrar el flujo o el fichero <"+fichero.getName()+">" );
        //e.printStackTrace();
    }
  }

  /**
   * Se encarga de borrar el fichero de indices correspondiente a un archivo determinado.
   * @param fichero es el fichero de indices que sera borrado.
   * @return Devuelve un booleano que indica si se pueod borrar o no.
   */
  public boolean borrarFicheroIndices( File fichero ){
    //En principio lo borro directamente
    return fichero.delete();
  }

  //Fusionar el crear y el guardar, argumento en crear y se unen
  //Ademas, llevar estos a una clase del estilo TratarIndices

  /**
   * Guarda la nueva informacion sobre los indices de un archivo concreto en su fichero correspondiente.
   * @param fichero es el fichero donde se almacenara el indice.
   * @param indices es el objeto que contiene la informacion sobre lo que tenemos del archivo.
   */
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

  /**
   * Se encarga de leer y obtener de un fichero el indice de un archivo concreto.
   * @param fichero es el fichero donde se tiene almacenado el indice de un archivo concreto.
   * @return Devuelve el objeto de indices que contiene la infomacion sobre un archivo concreto.
   */
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
    bs.close();
    }catch( Exception e ){
      e.printStackTrace();
      return indices;
    }
    return indices;
  }

}
