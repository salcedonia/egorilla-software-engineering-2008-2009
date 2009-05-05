/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package peerToPeer.descargas;

import datos.Fragmento;
import java.util.Vector;
import mensajes.p2p.Dame;
import mensajes.p2p.HolaQuiero;
import mensajes.serverclient.DatosCliente;
import peerToPeer.descargas.Descarga.Cliente;
import peerToPeer.egorilla.GestorEgorilla;

/**
 *
 * @author Jose Miguel Guerrero
 */
public class Descargador extends Thread{

        private GestorEgorilla _gestor=null;
        private AlmacenDescargas _almacen = null;

    public Descargador(GestorEgorilla gestor, AlmacenDescargas almacen) {
        _gestor = gestor;
        _almacen = almacen;
        _almacen.registraDescargador(this);
    }

    @Override
    public synchronized void run() {
        this.setName("Descargador");
        try {
            while (true) {
                if (_almacen.getListaDescargas().isEmpty())
                    wait();
                else 
                    wait(100);
                
                // 3 casos:
                // no se nada -> espero al servidor
                // se quien tiene -> envio holaquiero
                // se quien tiene que -> envio Dame

                Descarga d = _almacen.dameSiguiente();

                switch (d.getEstado()) {
                    case Descarga.PIDEASERVIDOR:
                        //envia consulta a servidor
                        _gestor.pedirPropietariosaServidor(d.getArchivo());
                        break;
                    case Descarga.PIDEALOSPROPIETARIOS:
                        if (d.getListaPropietarios().size() != 0) {
                            for (DatosCliente propietario : d.getListaPropietarios()) {
                                HolaQuiero msg = new HolaQuiero(d.getArchivo());
                                msg.setDestino(propietario.getIP(), propietario.getPuertoEscucha());

                                _gestor.addMensajeParaEnviar(msg);
                            }
                        }
                        break;
                    case Descarga.DESCARGA:
                        // envia DAME a los propietarios

                        Fragmento chunk = null;
                        Vector<Fragmento> listado=d.getListaFragmentosPendientes();
                        int i = (int)(Math.random()*((listado.size()-1)));
                        if (listado.size() != 0){                           
                            chunk = listado.get(i);
                            Cliente propietario = d.dameClienteQueTiene(chunk);
                            
                            if(propietario!=null){
                                System.out.println("Dame enviado a " + propietario.getIP() + " del chunk " + Integer.toString(i));
                    
                                Dame msj = new Dame(chunk.getNombre(), chunk.getHash(),
                                                chunk, propietario.getIP(), propietario.getPuerto());

                                _gestor.addMensajeParaEnviar(msj);
                            }
                        }
                        else {// hemos acabado
                            _gestor.descargaCompletada(d.getArchivo());
                        }
                        break;
                    default:
                         d.decrementaEstado();
                }

            }
        } catch (InterruptedException ex) {
            //  donothing
            }
    }

    public void parar(){
        this.interrupt();
    }
    
    synchronized void despierta (){
         this.notify();
     } 

}
