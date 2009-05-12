/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package peerToPeer;

/**
 *  Este enumerado indica el estado que se encuentra el P2P.
 * <ul>
 *  <li>DESCONECTADO : Estado desconectado, todos los servicios deben estar parados.</li>
 *  <li>NEGOCIANDO: Estado de negociacion, los servicios estan parados, se ha enviado
 *  una petición de conexión al servidor pero todavia no se ha obtenido 
 *  respuesta</li>
 *  <li>CONECTADO: Estado conectado, el servidor ha respondido correctamente, todos los
 *  servicios estan activos.</li>
 * <ul>
 * @author Qiang
 */
public enum EstadoP2P {
    DESCONECTADO,
    NEGOCIANDO,
    CONECTADO
}
