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
    private static FicheroInfo <InfoDescarga> _oFicheroInfoDescargas;

    public static void main(String[] args) throws ControlConfiguracionClienteException{
        _oFicheroInfoServidores = new FicheroInfo <InfoServidor> ("servidores.info");
        _oFicheroInfoDescargas = new FicheroInfo <InfoDescarga> ("descargas.info");        
        InfoServidor infoServidor;
        InfoDescarga infoDescarga;
        
        //Creo la informacion de los servidores mediante un arrayList de objetos InfoServidor
        //inicializados y lo escribo en el fichero en disco.
        ArrayList <InfoServidor> alServidores = new ArrayList<InfoServidor> ();
        infoServidor = new InfoServidor ("Servidor 1x", "128.23.21.1", "9091", "prueba 1");
        alServidores.add(infoServidor);
        infoServidor = new InfoServidor ("Servidor 2x", "128.23.21.2", "9092", "prueba 2");
        alServidores.add(infoServidor);
        infoServidor = new InfoServidor ("Servidor 3x", "128.23.21.3", "9093", "prueba 3");
        alServidores.add(infoServidor);
        
        _oFicheroInfoServidores.establecerConjuntoInfo(alServidores);
        _oFicheroInfoServidores.grabarFicheroInfo();
        
        //Creo la informacion de las descargas mediante un arrayList de objetos InfoDescarga
        //inicializados y lo escribo en el fichero en disco.
        ArrayList <InfoDescarga> alDescargas = new ArrayList<InfoDescarga> ();
        infoDescarga = new InfoDescarga ("Descarga1", "valor11", "valor12", "valor13");
        alDescargas.add(infoDescarga);
        infoDescarga = new InfoDescarga ("Descarga2", "valor21", "valor22", "valor23");
        alDescargas.add(infoDescarga);
        infoDescarga = new InfoDescarga ("Descarga3", "valor31", "valor32", "valor33");
        alDescargas.add(infoDescarga);

        _oFicheroInfoDescargas.establecerConjuntoInfo(alDescargas);
        _oFicheroInfoDescargas.grabarFicheroInfo();
    }

}
