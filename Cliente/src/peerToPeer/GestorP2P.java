/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package peerToPeer;

import datos.Archivo;
import peerToPeer.descargas.AlmacenDescargas;

/**
 *
 * @author pitidecaner
 */
public interface GestorP2P {

    /**
     * desconecta del servidor y finaliza las descargas
     */
    public void desconectar();
    /**
     * devuelve el almacen de descargas, para poder obtener datos de
     * estas.
     * @return
     */
    public AlmacenDescargas getAlmacenDescargas();

    /**
     * realiza una consulta al sevidor, para ello debe estar conectado al servidor
     *
     * @param cadena la cadena que se envia para contrastar en servidor.
     */
    public void nuevaConsulta(String cadena);

    /**
     * genera una descarga, para ello actualiza la lista de descargas y
     * comienza con un descargador.
     * DEBE estar conectado al servidor.
     * @param a el archivo a descargar.
     */
    public void nuevaDescarga(Archivo a);

    /**
     * @return si esta conectado a un servidor.
     */
    public boolean estaConectadoAServidor();

    /**
     * pausa una descarga en proceso.
     * @param a archivo asociado a la descarga.
     */
    public void pausarDescarga(Archivo a);

    /**
     * elimina una descarga, se elimina todo dato temporal y se deja de
     * descargar.
     * @param a archivo asociado a la descarga
     */
    public void eliminarDescarga(Archivo a);

    /**
     * continua  una descarga previamente pausada
     * @param a archivo asociado a la descarga
     */
    public void reanudarDescarga(Archivo a);

    /**
     * comenza la conexion con un servidor, si esta no se realiza en 2
     * segundos, se recibirá un error.
     * @param ip la ip del servidor
     * @param puerto puerto por el que se escucha
     */
    public void conectarAServidor(String ip, int puerto);

    /**
     * agrega un observador
     * @param obs dicho observador.
     */
     public void agregarObservador(ObservadorP2P obs);

    /**
     * elimina un observador
     * @param obs dicho observador.
     */
     public void eliminaObservador(ObservadorP2P obs);
}
