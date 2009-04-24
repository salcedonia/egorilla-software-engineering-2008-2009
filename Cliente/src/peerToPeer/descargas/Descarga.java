/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package peerToPeer.descargas;

import datos.Archivo;
import datos.Fragmento;
import gestorDeFicheros.GestorCompartidos;
import java.util.ArrayList;
import java.util.Vector;
import mensajes.p2p.Tengo;
import mensajes.serverclient.DatosCliente;

/**
 *
 * @author Jose Miguel Guerrero
 */
public class Descarga {
    private final int TIMER=20;
    private Archivo _archivo=null;
    private Vector<Fragmento> _listaFragmentosTengo=null;
    private Vector<Fragmento> _listaFragmentosPendientes=null;
    private ArrayList<DatosCliente> _propietarios=new ArrayList<DatosCliente>();
    private ArrayList<Par> _listaQuienTieneQue=new ArrayList<Par>();
    //estado 0 es que debe hacer HolaQuiero, cada 20 veces que pase por el Descargador se hara
    private int _estado=TIMER;
    
    public Descarga(Archivo archivo){
        _archivo=archivo;
        actualiza();
    }

    public Archivo getArchivo(){
        return _archivo;
    }

    public Vector<Fragmento> getListaFragmentosTengo(){
        return _listaFragmentosTengo;
    }

    public void actualiza(){
        _listaFragmentosTengo=GestorCompartidos.getInstancia().queFragmentosTienes(_archivo.getHash());
        //TODO generar Vector<Fragmento> con las partes que necesito
    }

    public void actualizaPropietarios(DatosCliente[] datos){
        for(int i=0;i<datos.length;i++){
             _propietarios.add(datos[i]);
        }
    }

    public void actualizaQuienTieneQue(Tengo mensaje){
        Cliente cliente=new Cliente(mensaje.ipDestino(),mensaje.puertoDestino());
        Par par=new Par(cliente,mensaje.getFragmentos());
        _listaQuienTieneQue.add(par);
    }

    public int getEstado(){
        return _estado;
    }

    public void decrementarEstado(){
        _estado--;
    }

    public void reiniciarEstado(){
        _estado=TIMER;
    }

    private class Cliente{
        private String _ip;
        private int _puerto;

        public Cliente(String ip,int puerto){
            _ip=ip;
            _puerto=puerto;
        }

        public String getIP(){
            return _ip;
        }

        public int getPuerto(){
            return _puerto;
        }
    }

    private class Par{
        private Cliente _cliente;
        private Vector<Fragmento> _listaFragmentosTiene;

        public Par(Cliente cliente,Vector<Fragmento> listaFragmentosTiene){
            _cliente=cliente;
            _listaFragmentosTiene=listaFragmentosTiene;
        }

        public Cliente getIP(){
            return _cliente;
        }

        public Vector<Fragmento> getPuerto(){
            return _listaFragmentosTiene;
        }
    }

}
