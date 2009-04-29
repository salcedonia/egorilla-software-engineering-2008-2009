/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package peerToPeer.descargas;

import datos.Archivo;
import datos.Fragmento;
import gestorDeFicheros.GestorCompartidos;
import java.util.ArrayList;
import java.util.Iterator;
import mensajes.p2p.Tengo;
import mensajes.p2p.Toma;
import mensajes.serverclient.DatosCliente;

/**
 * Esta clase implementa el almacén de descargas que contiene todas las descargas
 * que un cliente está efectuando
 * @author Iñaki Goffard, José Miguel Guerrero
 */
public class AlmacenDescargas {

    /**lista circular de descargas*/
    private ArrayList<Descarga> _listaDescargas;

    /**En qué posición de la lista nos encontramos(para saber el siguiente)*/
    private int _posListaDescargas;

    /** el descargador que ira despachando las descargas */
    private Descargador _descargador;
    
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
        
        Descarga des = this.buscaDescarga(arch);
        /*Sólo la añade a la lista si no existe ya la descarga para ese archivo
         * identificado por su hash */
        if (des == null){
            des = new Descarga(arch);
            _listaDescargas.add(des);
        }
        
        
    }
    
    /**
     * Método que elimina una descarga de la lista (resultado del mensaje Altoo)
     * Usa la función privada buscaDescarga para buscar la descarga
     * @param arch de tipo Archivo para buscar la descarga a eliminar
     */
    
    public void eliminaDescarga (Archivo arch){
        
        Descarga des = this.buscaDescarga(arch);
        if (des != null){
            _listaDescargas.remove(des);
        }
        
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
            enc = des.getArchivo().comparaArchivo(arch);//buscamos por hash
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
    public synchronized void respuestaPeticionDescarga (Archivo arch, DatosCliente[] datos){
        
        Descarga des = this.buscaDescarga(arch);
        if (des!=null){
            des.actualizaPropietarios(datos);
        }

        _descargador.despierta();
    }
    
    /**
     * Método que actualiza los fragmentos que tiene un determinado cliente, de la
     * descarga asociada al archivo que se pasa como parámetro
     * Usa la función privada buscaDescarga para encontrar la descarga
     * @param arch Archivo de la descarga
     * @param msj Tengo, que es una mensaje que informa de los fragmentos que tiene el cliente
     */
    
    public synchronized  void actualizaDescarga (Tengo msj){
        Archivo arch=new Archivo(msj.getNombre(),msj.getHash());
        Descarga des = this.buscaDescarga(arch);
        if (des != null) {
            des.actualizaQuienTieneQue(msj);
        }
        _descargador.despierta();
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
    
    /**
     * Método que va actualizando (eliminando) fragmentos en la descarga correspondiente
     * del archivo arch conforme le van llegando nuevos mensajes Toma
     * @param frag Fragmento a eliminar de la lista de pendientes
     */

    public boolean fragmentoDescargado(Fragmento frag){
        Archivo arch=new Archivo(frag.getNombre(),frag.getHash());
        Descarga desc=buscaDescarga(arch);
        boolean respuesta = false;
        if (desc != null){
            //Eliminamos el fragmento de la lista de pendientes de la descarga
            respuesta = desc.fragmentoDescargado(frag);
        }
        return respuesta;
    }

   void registraDescargador(Descargador des){
       _descargador = des;
     //  _descargador.
   }
}
