/* 
	This file is part of eGorilla.

    eGorilla is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    eGorilla is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with eGorilla.  If not, see <http://www.gnu.org/licenses/>.
*/
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
        start();
    }

    @Override
    public synchronized void run() {
        this.setName("Descargador");
        try {
            while (true) {
                if (_almacen.getListaDescargas().isEmpty())
                    wait();
                else 
                    wait(1000);
                
                // 3 casos:
                // no se nada -> espero al servidor
                // se quien tiene -> envio holaquiero
                // se quien tiene que -> envio Dame

                Descarga d = _almacen.dameSiguiente();
                if(d!=null){
                    switch (d.getEstado()) {
                        case Descarga.PIDEASERVIDOR:
                            //envia consulta a servidor
                            _gestor.pedirPropietariosaServidor(d.getArchivo());
                            break;
                        case Descarga.PIDEALOSPROPIETARIOS:
                            synchronized (this){
                                if (d.getListaPropietarios().size() != 0) {
                                    for (DatosCliente propietario : d.getListaPropietarios()) {
                                        HolaQuiero msg = new HolaQuiero(d.getArchivo());
                                        msg.setDestino(propietario.getIP(), propietario.getPuertoEscucha());

                                        _gestor.addMensajeParaEnviar(msg);
                                    }
                                }
                            }
                            break;
                        case Descarga.DESCARGA:
                            // envia DAME a los propietarios
                            
                            // TODO: piensate esto
                            

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
                    }
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
