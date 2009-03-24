package gestorDeProtocolos;

import java.io.Serializable;

/**
 * Clase que implementa los métodos necesarios para la gestión de las consultas
 * de descarga que los clientes realizan al servidor.
 * 
 * @author Luis Ayuso, Ivan Munsuri, Javier Salcedo
 */
public class PeticionDescarga implements Peticion, Serializable{
    
    // ATRIBUTOS
    private String _hash;

    /**
     * Constructor de la clase DownloadOrder.
     * 
     * @param h Hash asociado.
     */
    public PeticionDescarga(String h){
        _hash = h;
    }
    
    /**
     * Devuelve la versión asociada a la consulta de descarga.
     * 
     * @return 3
     */
    public int getVersion() {
        return 3;
    }

    /**
     * Devuelve el hash asociado al archivo de descarga asociado.
     * 
     * @return El hash asociado al archivo de descarga asociado.
     */
    public String getHash() {
        return _hash;
    }

    /**
     * Establece el valor de hash a valor hash.
     * 
     * @param hash Nuevo valor a establecer.
     */
    public void setHash(String hash) {
        _hash = hash;
    }
}
