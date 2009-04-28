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
    private boolean _do;

        public Descargador(GestorEgorilla gestor, AlmacenDescargas almacen){
            _gestor=gestor;
            _almacen=almacen;
            _fragmentosPedidos=new ArrayList<Integer>();
            _do = true;
        }

    @Override
    public synchronized void run() {

        while (_do) {
            try {
                while (_almacen.getListaDescargas().size() != 0) {
                    this.wait(100);

                    _descargaActual = _almacen.dameSiguiente();

                    if (_descargaActual.getEstado() == 0) {
                        HolaQuiero mensaje = null;
                        for (DatosCliente propietario : _descargaActual.getListaPropietarios()) {
                            mensaje = new HolaQuiero(_descargaActual.getArchivo());
                            mensaje.setDestino(propietario.getIP(), propietario.getPuertoEscucha());
                            _gestor.addMensajeParaEnviar(mensaje);
                        }

                        if (_descargaActual.getListaPropietarios().size() != 0) {
                            _descargaActual.reiniciarEstado();
                        } else {
                            try {
                                // si no, esperamos a enviar datos a los usuarios
                                this.wait(100);
                            } catch (InterruptedException ex) {
                                // do nothing
                            }
                        }
                    } else {
                        Vector<Fragmento> listado = _descargaActual.getListaFragmentosPendientes();
                        Random aleatorio = new Random();
                        while (_fragmentosPedidos.size() < MAX_PEDIDOS) {
                            int intentos = 0;
                            //obtengo uno de los fragmentos de forma aleatoria
                            int fragmentoAPedir = ((int) (aleatorio.nextDouble() * listado.size()));
                            //mientras no haya pedido ese fragmento, la lista de pedidos sea
                            //menor de tamaño que los que necesito (si fuera igual se supone que hemos
                            //pedido el numero maximo de los que podemos) y no hayamos agotado los intentos de pedir
                            while ((_fragmentosPedidos.indexOf(fragmentoAPedir) != -1) && (listado.size() > _fragmentosPedidos.size()) && (intentos < MAX_INTENTOS)) {
                                //pedimos otro hasta el maximo de intentos
                                fragmentoAPedir = ((int) (aleatorio.nextDouble() * listado.size()));
                                intentos++;
                            }
                            //si realmente no esta en la lista es que podemos pedirlo
                            if (_fragmentosPedidos.indexOf(fragmentoAPedir) != -1) {
                                //generamos los valores para el mensaje
                                Fragmento frag = listado.get(fragmentoAPedir);
                                String nombre = _descargaActual.getArchivo().getNombre();
                                String hash = _descargaActual.getArchivo().getHash();
                                Cliente cli = _descargaActual.dameClienteQueTiene(frag);
                                //creamos el mensaje
                                Dame mensajeDame = new Dame(nombre, hash, frag, cli.getIP(), cli.getPuerto());
                                //lo añadimos para el envio
                                _gestor.addMensajeParaEnviar(mensajeDame);
                            }

                        }
                        _fragmentosPedidos = new ArrayList<Integer>();
                        _descargaActual.decrementarEstado();
                    }
                }

                this.wait(100);
            } catch (InterruptedException ex) {
            }
        }
    }

    public void parar(){
        _do = false;
        notify();
    }

}
