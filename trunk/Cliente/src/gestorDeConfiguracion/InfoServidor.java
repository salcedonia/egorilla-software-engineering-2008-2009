package gestorDeConfiguracion;

import java.io.Serializable;

/**
 * Clase con la información que se quiere guardar de cada servidor.
 * Implementa la interfaz Serializable para poder ser guardada en el fichero.
 * 
 * @author F. Javier Sanchez Pardo, Javier Salcedo Gomez
 */
public class InfoServidor implements Serializable{
    /**
     * Nombre del servidor.
     */
    private String _nombre;
    /**
     * Direccion direccionIP del servidor.
     */
    private String _direccionIP;
    /**
     * Puerto de conexion con el servidor.
     */
    private int _puerto;
    /**
     * Descripcion asociada al servidor.
     */
    private String _descripcion;
    
    /**
     * Constructor de la clase InfoServidor.
     * 
     * @param nombre Nombre del servidor.
     * @param direccionIP Direccion direccionIP del servidor.
     * @param puerto Puerto de conexion del servidor.
     * @param descripcion Descripcion del servidor.
     */
    public InfoServidor (String nombre, String direccionIP, int puerto, String descripcion){
        
        _nombre = nombre;
        _direccionIP = direccionIP;
        _puerto = puerto;
        _descripcion = descripcion;
    }
    
    /**
     * Devuelve la descripcion del servidor.
     * 
     * @return La descripcion del servidor.
     */
    public String getDescripcion() {
        
        return _descripcion;
    }

    /**
     * Establece la descripcion a valor <b>descripcion</b>.
     * 
     * @param descripcion Nuevo valor a establecer.
     */
    public void setDescripcion(String descripcion) {
        
        _descripcion = descripcion;
    }

    /**
     * Devuelve la direccion direccionIP asociada al servidor.
     * 
     * @return La direccion direccionIP asociada al servidor.
     */
    public String getDireccionIP() {
        
        return _direccionIP;
    }

    /**
     * Establece la direccion direccionIP del servidor a valor <b>direccionIP</b>.
     * 
     * @param direccionIP Nuevo valor a establecer.
     */
    public void setDireccionIP(String direccionIP) {
        
        _direccionIP = direccionIP;
    }

    /**
     * Devuelve el nombre del servidor.
     * 
     * @return El nombre del servidor.
     */
    public String getNombre() {
        
        return _nombre;
    }

    /**
     * Establece el nombre del servidor a valor <b>nombre</b>.
     *
     * @param nombre Nuevo valor a establecer.
     */
    public void setNombre(String nombre) {
        
        _nombre = nombre;
    }

    /**
     * Devuelve el puerto de conexion del servidor.
     * 
     * @return El puerto de conexion del servidor.
     */
    public int getPuerto() {
        
        return _puerto;
    }

    /**
     * Establece el puerto del servidor a valor <b>puerto</b>.
     *
     * @param puerto Nuevo valor a establecer.
     */
    public void setPuerto(int puerto) {
        
        _puerto = puerto;
    }

    @Override
    public String toString(){
        return _nombre + "\t" + _direccionIP + "\t" + _puerto + "\t" + _descripcion;
    }

    @Override
    public boolean equals(Object obj) {
        
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final InfoServidor other = (InfoServidor) obj;
        if (!_nombre.matches(other._nombre) && (_nombre == null || !_nombre.equals(other._nombre))) {
            return false;
        }
        if (!_direccionIP.matches(other._direccionIP) && (_direccionIP == null || !_direccionIP.equals(other._direccionIP))) {
            return false;
        }
        if (_puerto != other._puerto) {
            return false;
        }
        if (!_descripcion.matches(other._descripcion) && (_descripcion == null || !_descripcion.equals(other._descripcion))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + (_nombre != null ? _nombre.hashCode() : 0);
        hash = 47 * hash + (_direccionIP != null ? _direccionIP.hashCode() : 0);
        hash = 47 * hash + _puerto;
        hash = 47 * hash + (_descripcion != null ? _descripcion.hashCode() : 0);
        return hash;
    }
}

