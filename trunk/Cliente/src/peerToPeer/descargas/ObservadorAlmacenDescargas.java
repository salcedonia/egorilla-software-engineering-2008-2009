package peerToPeer.descargas;

/**
 * Interfaz que implementa los métodos necesarios para aquellos observadores
 * del Alamacén de descargas.
 * 
 * @author Jose Miguel Guerrero
 */
public interface ObservadorAlmacenDescargas {

    public void eliminarDescarga(String _hash);
    
    /**
     * Notifica una nueva descarga
     *
     * @param nombre Nombre del archivo a descargar.
     * @param hash Hash del archivo a descargar.
     * @param tamanio Tamanio del archivo a descargar.
     */
    public void nuevaDescarga(String nombre, String hash, int tamanio);
    
    /**
     * Notifica un fragmento completado
     * 
     * @param hash Hash del archivo al que corresponde el fragmento descargado
     */
    public void fragmentoDescargado(String hash); 
    
    public void descargaCompleta(String hash) ;
}
