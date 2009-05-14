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

    public static final int PIDEASERVIDOR = 13;
    public static final int PIDEALOSPROPIETARIOS = 1;
    public static final int DESCARGA = 25;
    public static final int MAX_ESPERA = -100;

    private int _estado, _estado_aux;
    private Archivo _archivo;
    private Vector<Fragmento> _listaFragmentosPendientes;
    private ArrayList<DatosCliente> _propietarios;
    private ArrayList<Par> _listaQuienTieneQue;
    //estado 0 es que debe hacer HolaQuiero, cada 20 veces que pase por el Descargador se hara
    private int _posicion=0;
    
    public Descarga(Archivo archivo){
        _archivo=archivo;
        _estado = PIDEASERVIDOR;
        _estado_aux = PIDEASERVIDOR;
        _listaFragmentosPendientes = new Vector<Fragmento>();
        _propietarios = new ArrayList<DatosCliente>();
        _listaQuienTieneQue = new ArrayList<Par>();
        
        _posicion = 0;
    }

    public Archivo getArchivo(){
        return _archivo;
    }

    public Vector<Fragmento> getListaFragmentosPendientes(){
        return _listaFragmentosPendientes;
    }

    public void actualiza(){
        _listaFragmentosPendientes=GestorCompartidos.getInstancia().queFragmentosTienesPendientes(_archivo.getHash());
    }

    public void actualizaPropietarios(DatosCliente[] datos){
        _propietarios = new ArrayList<DatosCliente>();
        _listaQuienTieneQue = new ArrayList<Par>();
        for(int i=0;i<datos.length;i++){
             _propietarios.add(datos[i]);
        }
        _estado_aux = PIDEALOSPROPIETARIOS;
        _estado = PIDEALOSPROPIETARIOS;
    }

    public void actualizaQuienTieneQue(Tengo mensaje){
        Cliente cliente=new Cliente(mensaje.ipDestino(),mensaje.puertoDestino());
        Par par=new Par(cliente,mensaje.getFragmentos());
        _listaQuienTieneQue.add(par);
        _estado_aux = DESCARGA;
        _estado=DESCARGA;
    }

    public Cliente dameClienteQueTiene(Fragmento frag){
        Cliente cliente=null;        
        //nos posicionamos de forma aleatoria en un cliente
        if(_listaQuienTieneQue.size()>0){
            //_posicion = (int)(Math.random()*((_listaQuienTieneQue.size()-1)));
            int max = _listaQuienTieneQue.size()-1;
            int min = 0;
            _posicion = (int)Math.floor(Math.random()*(max-min+1)+min);
            int posicionNueva = (int)Math.floor(Math.random()*(max-min+1)+min);
            if( _posicion == posicionNueva ){
                if (_posicion < posicionNueva){//No hemos superado al último
                    _posicion ++;//Actualizamos posición para obtener el siguiente
                }else if(_posicion > posicionNueva){//Acabamos de superar al último
                    _posicion--;//El siguiente por tanto será el primero
                }else{
                    _posicion=0;
                }
            }

            Par par=_listaQuienTieneQue.get(_posicion);
            int fragmento=par.getfragmentos().indexOf(frag);
            if(fragmento!=-1){
                cliente=par.getCliente();
            }
        }
        return cliente;
    }

    public void eliminarCliente(String IP){
        
        ArrayList<Par> tmp = new ArrayList<Par>();
        for (Par quien:_listaQuienTieneQue) {
            if (quien.getCliente().getIP().equals(IP))
                tmp.add(quien);
        }
        for (Par par : tmp) {
            _listaQuienTieneQue.remove(par);
        }
        
        ArrayList<DatosCliente> tmp2 = new ArrayList<DatosCliente>();
        for (DatosCliente quien:_propietarios) {
            if (quien.getIP().equals(IP))
                tmp2.add(quien);
        }
        for (DatosCliente dat : tmp2) {
            _propietarios.remove(dat);
        }
    }

    public int getEstado(){
        return _estado;
    }
    
    public boolean  fragmentoDescargado(Fragmento frag){
        return _listaFragmentosPendientes.remove(frag);
    }

    public void decrementaEstado(){
        _estado_aux--;
        if(_estado_aux == PIDEASERVIDOR){
            _estado=PIDEASERVIDOR;
            _estado_aux=PIDEALOSPROPIETARIOS+1;
        }else if(_estado_aux == PIDEALOSPROPIETARIOS){
            _estado = PIDEALOSPROPIETARIOS;
            _estado_aux=PIDEASERVIDOR+1;
        }else if(_estado_aux == MAX_ESPERA){
            _estado=PIDEASERVIDOR;
            _estado_aux=PIDEALOSPROPIETARIOS+1;
        }else{
            _estado = DESCARGA;
        }
    }

    //---------- CLASES AUXILIARES

    public class Cliente{
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

        public Cliente getCliente(){
            return _cliente;
        }

        public Vector<Fragmento> getfragmentos(){
            return _listaFragmentosTiene;
        }
    }

    public ArrayList<DatosCliente> getListaPropietarios(){
        return  _propietarios;
    }
}
