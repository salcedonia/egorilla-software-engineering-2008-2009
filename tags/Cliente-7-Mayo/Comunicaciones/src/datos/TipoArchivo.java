package datos;

import java.util.Vector;

/**
 * Enumerado que gestiona los tipos de archivo que gestiona la aplicación.
 * 
 * @author Luis Ayuso, Ivan Munsuri, Javier Salcedo
 */
public enum TipoArchivo {
  
    ARCHIVO, AUDIO, DOCUMENTO, IMAGEN, OTRO, VIDEO;
    
    // ATRIBUTOS
    private static Vector<String> _extArchivo;
    private static Vector<String> _extAudio;
    private static Vector<String> _extDocumento;
    private static Vector<String> _extImagenes;
    private static Vector<String> _extVideo;

    /**
     * Crea e inicializa los vectores con las extensiones de archivo.
     */
    public static void iniciarTiposArchivo(){
    
        crearArchivo();
        crearAudio();
        crearDocumento();
        crearImagenes();
        crearVideo();
    }

    /**
     * Devuelve el tipo de archivo asociado a una extensión.
     * 
     * @param extension Extensión del archivo.
     * @return El tipo de archivo asociado a una extensión.
     */
    public static TipoArchivo devuelveTipo(String extension) {
        
        if(_extArchivo.contains(extension)) return ARCHIVO;
        if(_extAudio.contains(extension)) return AUDIO;
        if(_extDocumento.contains(extension)) return DOCUMENTO;
        if(_extImagenes.contains(extension)) return IMAGEN;
        if(_extVideo.contains(extension)) return VIDEO;
        
        return OTRO;
    }

    /**
     * Crea e inicializa el vector con los Strings referentes al tipo de 
     * extensiones de archivos de tipo archivo.
     */
    private static void crearArchivo() {

         _extArchivo = new Vector<String>();
         _extArchivo.add("zip");
         _extArchivo.add("rar");
         _extArchivo.add("jar");
         _extArchivo.add("mpc");
         _extArchivo.add("zip");
         _extArchivo.add("tar");
         _extArchivo.add("gz");
         _extArchivo.add("gzip");       
    }

    /**
     * Crea e inicializa el vector con los Strings referentes al tipo de 
     * extensiones de archivos de audio.
     */
    private static void crearAudio() {

        // Extensiones sacadas del estandar MIME
        _extAudio = new Vector<String>();
        _extAudio.add("mp3");
        _extAudio.add("mp4");
        _extAudio.add("wav");
        _extAudio.add("mid");
        _extAudio.add("midi");
        _extAudio.add("au");
        _extAudio.add("snd");
        _extAudio.add("aif");
        _extAudio.add("aiff");
        _extAudio.add("aifc");
    }

    /**
     * Crea e inicializa el vector con los Strings referentes al tipo de 
     * extensiones de archivos de documento.
     */
    private static void crearDocumento() {

        // Extensiones sacadas del estandar MIME
        _extDocumento = new Vector<String>();
        _extDocumento.add("doc");
        _extDocumento.add("pdf");
        _extDocumento.add("xls");
        _extDocumento.add("ppt");
        _extDocumento.add("txt");
        _extDocumento.add("atom");
        _extDocumento.add("iges");
        _extDocumento.add("bin");
        _extDocumento.add("ai");
        _extDocumento.add("eps");
        _extDocumento.add("ps");
        _extDocumento.add("rtf");
        _extDocumento.add("sgml");
        _extDocumento.add("xml");
        _extDocumento.add("html");
        _extDocumento.add("htm");
        _extDocumento.add("asp");
    }

    /**
     * Crea e inicializa el vector con los Strings referentes al tipo de 
     * extensiones de archivos de imagenes.
     */
    private static void crearImagenes() {

        // Extensiones sacadas del estandar MIME
        _extImagenes = new Vector<String>();
        _extImagenes.add("jpeg");
        _extImagenes.add("jpg");
        _extImagenes.add("jpe");
        _extImagenes.add("man");
        _extImagenes.add("gif");
        _extImagenes.add("giff");
        _extImagenes.add("tiff");
        _extImagenes.add("tif");
        _extImagenes.add("pbm");
        _extImagenes.add("pjm");
        _extImagenes.add("ppm");
        _extImagenes.add("png");
        _extImagenes.add("raw");     
    }

    /**
     * Crea e inicializa el vector con los Strings referentes al tipo de 
     * extensiones de archivos de vídeo.
     */
    private static void crearVideo() {

        // Extensiones sacadas del estandar MIME
        _extVideo = new Vector<String>();
        _extVideo.add("avi");
        _extVideo.add("3gp");
        _extVideo.add("mkv");
        _extVideo.add("mpeg");
        _extVideo.add("mpg");
        _extVideo.add("mpe");
        _extVideo.add("mov");
        _extVideo.add("qt");
        _extVideo.add("divx");
        _extVideo.add("wmv");
        _extVideo.add("wma");
    }
}
