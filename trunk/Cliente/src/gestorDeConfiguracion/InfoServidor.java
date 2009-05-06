package gestorDeConfiguracion;

import java.io.Serializable;

/**
 * Clase con la información que se quiere guardar de cada servidor.
 * ¡¡OJO!! ¡Tiene que implementar la interfaz Serializable!!
 * 
 * @author F. Javier Sanchez Pardo
 */
public class InfoServidor implements Serializable{
    public String _sNombreServidor;
    public String _sIP;
    public String _sPuerto;
    public String _sDescripcion;
    
    public InfoServidor (String nombre, String IP, String puerto, String descrip){
        this._sNombreServidor = nombre;
        this._sIP = IP;
        this._sPuerto = puerto;
        this._sDescripcion = descrip;
    }
    
    @Override
    /**
     * Util para depurar
     */
    public String toString(){
        return _sNombreServidor + "\t" + _sIP + "\t" + _sPuerto + "\t" + _sDescripcion;
    }    
//    /**
//     * Dos objetos de esta clase van a ser iguales si tienen el mismo valor en el
//     * atributo sNombreServidor (se utiliza como clave del elemento).
//     */
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//                return true;
//        }
//        if ((o == null) || (this.getClass() != o.getClass())) {
//                return false;
//        }
//        InfoServidor infoOtro = (InfoServidor) o;
//        
//        if (this._sNombreServidor.compareTo(infoOtro._sNombreServidor) == 0)
//            return true;
//        return false;
//    }
}

