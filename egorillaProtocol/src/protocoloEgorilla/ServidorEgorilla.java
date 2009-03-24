/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

package protocoloEgorilla;

import gestorDeRed.GestorDeRed;
import gestorDeRed.Receptor;
import mensajes.Mensaje;
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

    public void recibeMensaje(Mensaje msj) {
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
                
                // YA SE CONTESTARA CON TOMA, no aki
                
                break;
            case HolaQuiero:
                
                // si tengo el fichero
                
                // recupero fragmentos
                
                // apunto direccion del peer e interes
                               
                // CONTESTA: tengo
                
                break;
            case Tengo:
                
                // evaluar fragmentos que tiene tengo y tienen y decidir cuales quiero
                // por ahora todos!
                
                // CONTESTA dame
                break;
            case Toma:
                
                // conozco al peer??
                
                // envio fragmeto a gestor de descargas
                
                break;
        }
    }

}
