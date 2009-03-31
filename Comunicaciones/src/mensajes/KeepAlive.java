/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

package mensajes;

/**
 * El mensaje keepalive notifica que la conexion sigue establecida
 * para la implementación no orientada a conexion.
 * 
 * el envio de este mensaje implica que el otro host sigue en linea
 * y ademas especifica cuanto tiempo pasa hasta el siguiente envio
 * 
 * el otro host esperará un tiempo igual a 3 veces
 * el tiempo especificado antes de entrar en panico.
 *
 * @author Luis Ayuso
 */
public class KeepAlive implements Mensaje{

    private String _destinoIP;
    private int _destinoPuerto;
    
    public long segundosCadaKeepAlive;
    
    
    public TipoMensaje getTipoMensaje() {
        return TipoMensaje.keepAlive;
    }

    public void setDestino(String destino, int puerto) {
        _destinoIP = destino;
        _destinoPuerto = puerto;
    }

    public String destino() {
        return _destinoIP;
    }

    public int puerto() {
        return _destinoPuerto;
    }

}
