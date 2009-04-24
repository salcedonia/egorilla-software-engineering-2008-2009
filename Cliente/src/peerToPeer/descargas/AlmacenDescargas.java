/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package peerToPeer.descargas;

import datos.Archivo;
import java.util.ArrayList;
import java.util.Iterator;
import mensajes.p2p.Tengo;
import mensajes.serverclient.DatosCliente;

/**
 * Esta clase implementa el almacén de descargas que contiene todas las descargas
 * que un cliente está efectuando
 * @author Iñaki Goffard
 */
public class AlmacenDescargas {
    
    private ArrayList<Descarga> _listaDescargas;//lista circular de descargas
    
    private int _posListaDescargas;//En qué posición de la lista nos encontramos(para saber el siguiente)
    
    /**
     * Constructor del almacén de descargas. Crea el ArrayList de descargas e inicializa
     * el atributo posiciónListaDescargas a -1, pues en dameSiguiente() siempre se va a sumar 1 a este
     * valor, salvo que alcance el tamaño actual de la lista, en cuyo caso vuelve a 0 (primera posición)
     */
    public AlmacenDescargas (){
        
        _listaDescargas = new ArrayList<Descarga>();
        _posListaDescargas = -1;
    }
    
    /**
     * Accesora a _listaDescargas
     * @return ArrayList<Descarga> que es la lista de descargas
     */
    
    public ArrayList<Descarga> getListaDescargas(){
        
        return _listaDescargas;
    }
    
    /**
     * Mutador de la _listaDescargas
     * @param lista de tipo ArrayList<Descarga>
     */
    
    public void setListaDescargas (ArrayList<Descarga> lista){
        
        _listaDescargas = lista;
    }
    
    /**
     * Accesora a _posListaDescargas
     * @return int con la posición en la que estamos
     */
    
    public int getPosLista (){
        
        return _posListaDescargas;
    }
    
    /**
     * Mutador de _posListaDescargas
     * @param pos de tipo int
     */
    
    public void setPosLista (int pos){
        
        _posListaDescargas = pos;
    }
    
    /**
     * Método que crea una nueva descarga y la añade a la lista
     * @param arch es el Archivo de esta nueva descarga
     */
    public void nuevaDescarga (Archivo arch){
        
        Descarga des = new Descarga(arch);
        _listaDescargas.add(des);
        
    }
    
    /**
     * Función privada auxiliar, que permite buscar una descarga a través del hash
     * de su archivo correspondiente
     * @param arch es el Archivo cuyo hash se va a buscar en la lista de descargas
     * @return la Descarga correspondiente a Archivo si existe, y si no, null
     */
    
    private Descarga buscaDescarga (Archivo arch){
        
        Iterator<Descarga> iterador = _listaDescargas.iterator();
        boolean enc = false;
        Descarga respuesta = null;
        while (iterador.hasNext() && !enc){
            Descarga des = iterador.next();
            enc = des.getArchivo().getHash().equals(arch.getHash());//buscamos por hash
            if (enc){
                respuesta = des;
            }
        }
        return respuesta;
        
    }
    
    /**
     * Método que actualiza la lista de propietarios de una descarga 
     * Usa la función privada buscaDescarga para encontrar la descarga
     * @param arch Archivo correspondiente a la descarga que hay que actualizar
     * @param datos DatosCliente[] que es la lista de mensajes con los datos de los propietarios
     */
    public void respuestaPeticionDescarga (Archivo arch, DatosCliente[] datos){
        
        Descarga des = this.buscaDescarga(arch);
        if (des!=null){
            des.actualizaPropietarios(datos);
        }
        
    }
    
    /**
     * Método que actualiza los fragmentos que tiene un determinado cliente, de la
     * descarga asociada al archivo que se pasa como parámetro
     * Usa la función privada buscaDescarga para encontrar la descarga
     * @param arch Archivo de la descarga
     * @param msj Tengo, que es una mensaje que informa de los fragmentos que tiene el cliente
     */
    
    public void actualizaDescarga (Archivo arch, Tengo msj){
        
        Descarga des = this.buscaDescarga(arch);
        if (des != null) {
            des.actualizaQuienTieneQue(msj);
        }
        
    }
    
    /**
     * Función que proporciona el siguiente elemento de la lista.
     * Va a ser solicitado por el objeto Descargador. 
     * Se trata de una lista circular, con lo que el elmento suiguiente del último
     * será otra vez el primero
     * @return Descarga que será la siguiente descarga de la lista
     */
    
    public Descarga dameSiguiente(){
       
        int tamLista = _listaDescargas.size();
        if (_posListaDescargas < tamLista-1){//No hemos superado al último
            _posListaDescargas ++;//Actualizamos posición para obtener el siguiente
        } else {//Acabamos de superar al último
            _posListaDescargas = 0;//El siguiente por tanto será el primero
        }
        return _listaDescargas.get(_posListaDescargas);
       
    }
    
    

}
