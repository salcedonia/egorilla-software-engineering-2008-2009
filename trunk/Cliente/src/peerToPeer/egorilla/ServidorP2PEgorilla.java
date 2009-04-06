/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

package peerToPeer.egorilla;

import datos.Archivo;
import datos.Fragmento;
import gestorDeRed.Receptor;
import java.util.ArrayList;
import mensajes.Mensaje;
import mensajes.p2p.Dame;
import mensajes.p2p.HolaQuiero;
import mensajes.p2p.Tengo;
import mensajes.p2p.Toma;
import mensajes.serverclient.RespuestaPeticionDescarga;
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
        switch (msj.getTipoMensaje()){
     
       //------------------ Mensajes del peer to peer  -------------------------
            case Altoo:
                
                // elimino toda ocurrencia del peer en mis listas
                
                // NO SE CONTESTA
                
                break;
                
                
            case Conozco:
                
                // por ahora nada
                
                break;
                
                
            case Dame:
                
                // Apunto para servir m�s adelante.
                Dame pkg = (Dame) msj;
              
                // TODO: en este punto debemos hacer algo que evalue los fragmentos
                // que se están enviando, cuales quiero y a quien se los pido
                // ya que puedo haber pedido este fichero a varios peers.
                
                
                
                // indico al gestor de subidas que vot a subir determinados fragmentos 
                // de un fichero a un peer identificado por ip.
                _gestor.nuevaSubida(new Archivo(pkg.nombre, pkg.hash), ip, port, pkg.fragmentos);
                
                // YA SE CONTESTARA CON TOMA, no aki
                
                break;
                
                
            case HolaQuiero:
                
                HolaQuiero quiero = (HolaQuiero) msj;
                     
                Tengo resp = new Tengo();
                resp.hash = quiero.hash;
                resp.nombre = quiero.nombre;  //TODO: deberia poner el nombre que tengo yo para este fichero
                    
                
                // si tengo el fichero
                // TODO: hay que buscar entre las descargas pendientes y los ficheros
                // completos
              
                
                // apunto direccion del peer e interes
                               
                // CONTESTA: tengo
                _gestor.addMensajeParaEnviar(resp);
                
                
            case Tengo:
                
                // evaluar fragmentos que tiene tengo y tienen y decidir cuales quiero
                // por ahora todos! 
                
                Tengo reciv = (Tengo) msj;
                Dame respuesta =  new Dame();
                
                respuesta.hash = reciv.hash;
                respuesta.nombre = reciv.nombre;
                
                // si el Tengo viene vacio, se acaba aqui la ejecución
                if((reciv.fragmentos == null)||(reciv.fragmentos.isEmpty()))
                    return;
                
                // comprobar al menos que no estemos hablando un conjunto vacio
                // en ese caso es que el peer no tiene el archivo que buscamos
                respuesta.fragmentos = reciv.fragmentos;
                
                // CONTESTA dame
                _gestor.addMensajeParaEnviar(respuesta);
                
                break;
                
                
            case Toma:
                // este es el m�s importante, no es la negociacion, es el envio
                // de paquetes
                
                // conozco al peer??
                //TODO: comprobar esto, por ahora asumo que si
                
                Toma paquete = (Toma)msj;
                                
                Fragmento f = new Fragmento();
                f.hash   = paquete.hash;
                f.nombre = paquete.nombre;
                f.offset = paquete.offset;
                f.tama   = paquete.chunk.length;
                
                //TODO:  envio fragmeto a gestor de descargas o lo que sea
               
                
                break;
                
   //-------------------------   Comunicaciones con el servidor ----------------             
                
            case RespuestaPeticionDescarga:
                
                RespuestaPeticionDescarga respDes = (RespuestaPeticionDescarga) msj;
                
                Archivo a =new Archivo(respDes.nombre, respDes.hash);
                                
                _gestor.DescargaFichero(a, respDes.getLista());
                
                break;
                
            case RespuestaPeticionConsulta:
                
                //TODO:  notifica el resutado de la busqueda
                // patron observador?? iria bien
                
                break;
                
            case Bienvenido:
                
                // esto indica que estamos conectados
                _gestor.conectado();
                _gestor.enviaListaArchivos();
        }
    }

    public void perdidaDeConexion(String ip) {
       // TODO: se ha perdido la conexión con este tipo
    }

}
