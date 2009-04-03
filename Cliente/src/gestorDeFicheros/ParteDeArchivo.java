package gestorDeFicheros;

import mensajes.serverclient.ListaArchivos;
import datos.*;
import java.io.*;
import java.util.*;

/**
 * Gestiona las partes en las que se compone un archivo.
 *
 * @author Luis Ayuso, Ivan Munsuri, Javier Salcedo
 */
public class ParteDeArchivo implements Serializable {
    
    private int _partesEnviadas;
    private ArrayList<Byte[]> _partes;
    private String _directorio;
    private String _nombreFichero;
    private String _hashFichero;

    /**
     * 
     * @param directorioCompartidos
     * @param hashFichero
     * @throws java.io.IOException
     */
    public ParteDeArchivo(String directorioCompartidos, String hashFichero) throws IOException {
        /*
        Este lee _partes de un fichero existente, dirCompartidos
        Hacer q recupere el nombre de la lista de archivos compartidos del cliente
         */

        File fComp = new File(directorioCompartidos + "\\" + getNombreFichero(directorioCompartidos, hashFichero));
        FileInputStream fileInput = new FileInputStream(fComp);
        BufferedInputStream bufferedInput = new BufferedInputStream(fileInput);
        byte[] array = new byte[512];
        Byte[] parte;

        _partes = new ArrayList<Byte[]>();

        System.out.println("Ruta <" + fComp.getAbsolutePath() + ">");

        int leidos = bufferedInput.read(array);
        int totalLeidos = 0;
        
        while (leidos > 0) {
            
            parte = new Byte[leidos];
            
            for (int i = 0; i < leidos; i++) {
                parte[i] = new Byte(array[i]);
            }
            _partes.add(parte);
            totalLeidos += leidos;
            leidos = bufferedInput.read(array);
        }
        
        bufferedInput.close();
        System.out.println("Bytes totales <" + totalLeidos + ">");
        System.out.println("El fichero tiene <" + _partes.size() + "> parte/es");
    }

    /**
     * 
     * @param directorioDescargas
     * @param nombreFicheroDescargado
     * @param hashFicheroDescargado
     */
    public ParteDeArchivo(String directorioDescargas, String nombreFicheroDescargado, String hashFicheroDescargado) {
        
        _directorio = directorioDescargas;
        _nombreFichero = nombreFicheroDescargado;
        _hashFichero = hashFicheroDescargado;
    }

    //Tiene q haber otro constructor q guarde los _partes recibidos en un archivo
    //dirIncoming
    
    /**
     * 
     * @return
     */
    public Byte[] getParte() {
        
        _partesEnviadas++;
        return _partes.get(_partesEnviadas - 1);
    }
    
    /**
     * 
     * @param directorio
     * @param hash
     * @return
     */
    public String getNombreFichero(String directorio, String hash) {
        String nombre = "";
        boolean encontrado = false;
        GestorCompartidos compartidos = null;
        try {
            compartidos = new GestorCompartidos(new File(directorio));
        } catch (Exception e) {
        }
        
        ListaArchivos lista = compartidos.getArchivosCompartidos();

        for (int i = 0; i < lista.size() && encontrado == false; i++) {
            if (lista.get(i).getHash().compareTo(hash) == 0) {
                encontrado = true;
                nombre = lista.get(i).getNombre();
            }
        }

        return nombre;
    }

    /**
     * 
     * @return
     */
    public boolean masPartes() {
        //No se si es _partesEnviadas-1, +1 o sin nada
        return _partes.size() != _partesEnviadas;
    }

    /**
     * 
     * @return
     */
    public int getCantidadPartes() {
        //No se si es _partesEnviadas-1, +1 o sin nada
        return _partes.size();
    }

    /**
     * 
     * @param partesDescargadas
     * @throws java.io.IOException
     */
    public void setPartes(ArrayList<Byte[]> partesDescargadas) throws IOException {
        
        Byte[] parte;

        File fDes = new File(_directorio + "\\" + _nombreFichero);
        FileOutputStream fileOutput = new FileOutputStream(fDes);
        BufferedOutputStream bufferedOutput = new BufferedOutputStream(fileOutput);

        System.out.println("Ruta <" + fDes.getAbsolutePath() + ">");

        for (int i = 0; i < partesDescargadas.size(); i++) {
            //bufferedOutput.write( partesDescargadas.get( i ) );
            parte = partesDescargadas.get(i);
            //System.out.println("Cacho numero <"+ i +"> - parte "+parte+ "- bytes del parte <"+parte.length+">");
            for (int j = 0; j < parte.length; j++) {
                //System.out.println("Byte numero <"+ j+ ">");
                bufferedOutput.write(parte[j].byteValue());
            }
        }

        bufferedOutput.close();

    //Comprobar q coinciden los MD5
    }
}
