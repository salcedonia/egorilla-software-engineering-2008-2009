package gestorDeConfiguracion;

import java.util.ArrayList;

/**
 * Clase de utilidad que permite Regenerar fácilmente ficheros a partir de la 
 * información de los servidores (InfoServidor) y de las descargas (InfoDescarga). 
 * ¡¡OJO!! SI SE HACE CUALQUIER MODIFICACION EN LAS LAS CLASES InfoDescarga 
 * e InfoServidor (que encapsulan la informacion que se guarda de la entidad
 * correspondiente) ES NECESARIO VOLVER A REGENERAR ESTOS FICHEROS (porque se 
 * manejan a bajo nivel mediante serializacion de objetos).
 * 
 * @author F. Javier Sanchez Pardo
 */
public class GeneradorFicherosInfo {
    
    private static FicheroInfo <InfoServidor> _oFicheroInfoServidores;
    
    public static void main(String[] args) throws ControlConfiguracionClienteException{
        
        _oFicheroInfoServidores = new FicheroInfo <InfoServidor> ("servidores.info");
        InfoServidor infoServidor;
        
        //Creo la informacion de los servidores mediante un arrayList de objetos InfoServidor
        //inicializados y lo escribo en el fichero en disco.
        ArrayList <InfoServidor> alServidores = new ArrayList<InfoServidor> ();
        infoServidor = new InfoServidor ("Servidor local", "127.0.0.1", 6969, "Servidor en local");
        alServidores.add(infoServidor);
        
        _oFicheroInfoServidores.setInfoFichero(alServidores);        
    }
}
