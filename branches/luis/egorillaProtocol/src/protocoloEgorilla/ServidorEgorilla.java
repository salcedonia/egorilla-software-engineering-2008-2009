/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

package protocoloEgorilla;

import gestorDeRed.GestorDeRed;
import gestorDeRed.Receptor;
import mensajes.Mensaje;
import mensajes.p2p.Dame;
import mensajes.p2p.HolaQuiero;
import mensajes.p2p.Tengo;
import mensajes.p2p.Toma;
import mensajes.serverclient.RespuestaPeticionDescarga;
import peerToPeer.Fragmento;
import peerToPeer.GestorDescargas;

/**
 *
 * @author Luis Ayuso
 */
public class ServidorEgorilla implements Receptor<Mensaje> {

    private GestorEgorilla _gestor;
    private GestorDescargas _descargas;
    
    ServidorEgorilla(GestorEgorilla gE, GestorDescargas gD) {
       _gestor = gE;
       _descargas = gD;
    }

    public void recibeMensaje(Mensaje msj, String ip, int port) {
        switch (msj.getTipoMensaje()){
            case Altoo:
                
                // elimino toda ocurrencia del peer en mis listas
                
                // NO SE CONTESTA
                
                break;
                
                
            case Conozco:
                
                // por ahora nada
                
                break;
                
                
            case Dame:
                
                // Apunto para servir más adelante.
                Dame pkg = (Dame) msj;
              
                _gestor.nuevaSubida(new Archivo(pkg.nombre, pkg.hash), ip, port, pkg.fragmentos);
                
                // YA SE CONTESTARA CON TOMA, no aki
                
                break;
                
                
            case HolaQuiero:
                
                HolaQuiero quiero = (HolaQuiero) msj;
                
                // si tengo el fichero
                if (_descargas.puedoBajar(quiero.hash)){
                
                    
                Tengo resp = new Tengo();
                resp.hash = quiero.hash;
                resp.nombre = quiero.nombre;  //TODO: deberia poner el nombre que tengo yo para este fichero
                    
                // recupero fragmentos
                resp.fragmentos =  _descargas.getFragmentos(quiero.hash);
                
                // apunto direccion del peer e interes
                               
                // CONTESTA: tengo
                _gestor.addMensajeParaEnviar(resp);
                }
                else{
                    // no lo tengo, envio una estructura vacia
                    
                }
                break;
                
                
            case Tengo:
                
                // evaluar fragmentos que tiene tengo y tienen y decidir cuales quiero
                // por ahora todos! 
                
                Tengo reciv = (Tengo) msj;
                Dame respuesta =  new Dame();
                
                respuesta.hash = reciv.hash;
                respuesta.nombre = reciv.nombre;
                
                // comprobar al menos que no estemos hablando un conjunto vacio
                // en ese caso es que el peer no tiene el archivo que buscamos
                respuesta.fragmentos = reciv.fragmentos;
                
                // CONTESTA dame
                _gestor.addMensajeParaEnviar(respuesta);
                
                break;
                
                
            case Toma:
                // este es el más importante, no es la negociacion, es el envio
                // de paquetes
                
                // conozco al peer??
                //TODO: comprobar esto, por ahora asumo que si
                
                Toma paquete = (Toma)msj;
                                
                Fragmento f = new Fragmento();
                f.hash   = paquete.hash;
                f.nombre = paquete.nombre;
                f.offset = paquete.offset;
                f.tama   = paquete.chunk.length;
                
                // envio fragmeto a gestor de descargas
                _descargas.llegaFragmento(f, paquete.chunk);
                
                break;
                
            case RespuestaPeticionDescarga:
                
                RespuestaPeticionDescarga respDes = (RespuestaPeticionDescarga) msj;
                
                Archivo a =new Archivo(respDes.nombre, respDes.hash);
                                
                _gestor.DescargaFichero(a, respDes.getLista());
                
                break;
        }
    }

}
