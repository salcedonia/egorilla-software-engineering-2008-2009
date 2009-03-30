/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

package peerToPeer.egorilla;

import gestorDeRed.Receptor;
import mensajes.Mensaje;
import peerToPeer.descargas.GestorDescargas;


/**
 *
 * Esta clase es lo que implementa la parte servidora de un cliente
 * esto es, cuando recibe un mensaje, lo decodifica y opera con el
 * 
 * se encarga, basicamente de realizar toda la negociaci�n.
 * 
 * TODO: muy importante, cada vez que se ejecuta esto deberia hacerse
 * en un hilo paralelo, para permitir la concurrencia, ahora mismo no
 * lo hace y esto sera una perdida de rendimiento
 * 
 * @author Luis Ayuso
 * @author Iñaki Goffard
 */
public class ServidorP2PEgorilla implements Receptor<Mensaje>{

    private GestorEgorilla _gestor;
    private GestorDescargas _descargas;
    
    /**
     * constructor b�sico:
     * necesita de dos cosas que estan fuera de su hambito, el gestor de egorilla
     * que es el que basicamente dice que hay que hacer
     * y el gestor de descargas, que es un modulo exterior que comprueba todo lo 
     * necesario
     * 
     * @param gE gestor eGorilla
     * @param gD bestor de descargas
     */
    public ServidorP2PEgorilla(GestorEgorilla gE, GestorDescargas gD) {
       _gestor = gE;
       _descargas = gD;
    }

    /**
     * 
     * heredado de la interface, es el metodo por el cual se nos comunica que ha
     * llegado un mensaje nuevo
     * 
     * 
     * @param msj que?
     * @param ip de quien?
     * @param port dnd contesto?
     */
    public void recibeMensaje(Mensaje msj, String ip, int port) {
        
        HiloCliente hc = new HiloCliente(msj, this._gestor, this._descargas, ip, port);

        Thread hilo = new Thread (hc);
        
        hilo.start();
       
    }

    

}
