/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package peerToPeer.descargas;

import datos.Fragmento;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
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

        private final int MAX_PEDIDOS=5;
        private final int MAX_INTENTOS=30;
        private Descarga _descargaActual=null;
        private GestorEgorilla _gestor=null;
        private AlmacenDescargas _almacen=null;
        private ArrayList<Integer>  _fragmentosPedidos=null;

        public Descargador(GestorEgorilla gestor, AlmacenDescargas almacen){
            _gestor=gestor;
            _almacen=almacen;
            _fragmentosPedidos=new ArrayList<Integer>();
    }

    @Override
    public synchronized void run() {
        this.setName("Descargador");

        try {
            while (true) {

                wait();
            }
        } catch (InterruptedException ex) {
            //  donothing
            }
    }

    public void parar(){
        this.interrupt();
    }

}
