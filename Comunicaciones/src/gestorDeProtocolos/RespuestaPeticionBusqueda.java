package gestorDeProtocolos;

import datos.Archivo;
import java.io.Serializable;

/**
 * Clase que implementa la respuesta a una consulta.
 * 
 * @author Luis Ayuso, Ivan Munsuri, Javier Salcedo
 */
public class RespuestaPeticionBusqueda implements Serializable{

    // ATRIBUTOS
    private Archivo[] _lista;

    /**
     * Constructor de la clase QueryAnswer.
     * 
     * @param lista Lista de archivos asociada.
     */
    public RespuestaPeticionBusqueda(Archivo[] lista) {
        
       _lista = lista;
    }

    /**
     * Devuelve la lista de archivos asociada a la respuesta a la consulta.
     * 
     * @return La lista de archivos asociada a la respuesta a la consulta.
     */
    public Archivo[] getLista() {
        return _lista;
    }

    /**
     * Establece _lista a valor <b>lista</b>.
     * 
     * @param lista Nuevo valor de la lista de archivos asociada.
     */
    public void setLista(Archivo[] lista) {
        _lista = lista;
    }    
}
