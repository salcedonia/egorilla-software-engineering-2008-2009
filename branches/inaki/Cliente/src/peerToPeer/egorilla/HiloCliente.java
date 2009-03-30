/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package peerToPeer.egorilla;

import datos.Archivo;
import datos.Fragmento;
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
 * @author Iñaki Goffard
 */
public class HiloCliente implements Runnable{
    
    private Mensaje _mensaje;
    
    private GestorEgorilla _gestor;
    private GestorDescargas _descargas;
    
    private String _ip;
    private int _port;
    
    public HiloCliente (Mensaje msj, GestorEgorilla ge, GestorDescargas gd, String ip, int port){
        this._mensaje = msj;
        this._gestor = ge;
        this._descargas = gd;
        this._ip = ip;
        this._port = port;
    }

    public synchronized void run() {
        switch (this._mensaje.getTipoMensaje()) {
            case Altoo:
                
                // elimino toda ocurrencia del peer en mis listas
                
                // NO SE CONTESTA
                
                break;
                
                
            case Conozco:
                
                // por ahora nada
                
                break;
                
                
            case Dame:
                
                // Apunto para servir m�s adelante.
                Dame pkg = (Dame) this._mensaje;
              
                _gestor.nuevaSubida(new Archivo(pkg.nombre, pkg.hash), this._ip, this._port, pkg.fragmentos);
                
                // YA SE CONTESTARA CON TOMA, no aki
                
                break;
                
                
            case HolaQuiero:
                
                HolaQuiero quiero = (HolaQuiero) this._mensaje;
                     
                Tengo resp = new Tengo();
                resp.hash = quiero.hash;
                resp.nombre = quiero.nombre;  //TODO: deberia poner el nombre que tengo yo para este fichero
                    
                
                // si tengo el fichero
                // TODO: hay que buscar entre las descargas pendientes y los ficheros
                // completos
                if (_descargas.puedoBajar(quiero.hash)){
                
                // recupero fragmentos
                resp.fragmentos =  _descargas.getFragmentos(quiero.hash);
                
                // apunto direccion del peer e interes
                               
                // CONTESTA: tengo
                _gestor.addMensajeParaEnviar(resp);
                }
                else{
                    // no lo tengo, envio una estructura vacia
                    resp.fragmentos = new ArrayList<Fragmento>();
                }
                break;
                
                
            case Tengo:
                
                // evaluar fragmentos que tiene tengo y tienen y decidir cuales quiero
                // por ahora todos! 
                
                Tengo reciv = (Tengo) this._mensaje;
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
                
                Toma paquete = (Toma)this._mensaje;
                                
                Fragmento f = new Fragmento();
                f.hash   = paquete.hash;
                f.nombre = paquete.nombre;
                f.offset = paquete.offset;
                f.tama   = paquete.chunk.length;
                
                // envio fragmeto a gestor de descargas
                _descargas.llegaFragmento(f, paquete.chunk);
                
                break;
                
            case RespuestaPeticionDescarga:
                
                RespuestaPeticionDescarga respDes = (RespuestaPeticionDescarga) this._mensaje;
                
                Archivo a =new Archivo(respDes.nombre, respDes.hash);
                                
                _gestor.DescargaFichero(a, respDes.getLista());
                
                break;
        }
    }

}
