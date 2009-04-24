/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package peerToPeer.descargas;

import datos.Archivo;
import datos.Fragmento;
import mensajes.p2p.HolaQuiero;
import peerToPeer.egorilla.GestorEgorilla;

/**
 *
 * @author Jose Miguel Guerrero
 */
public class Descargador{

        private Descarga _descargaActual=null;
        private GestorEgorilla _gestor=null;

        public Descargador(GestorEgorilla gestor){
            _gestor=gestor;
        }

        /**
         * Comprueba si el fragmento "contenido" esta dentro completamente de "contiene"
         *
         * @param contiene Fragmento en el que comprobar si esta dentro.
         * @param contenido Fragmento que queremos ver si esta contenido en "contiene"
         * @return true si esta contenido completamente, false en otro caso.
         */
        public boolean fragmentoContenidoCompleto(Fragmento contiene,Fragmento contenido){
            long inicioContiene=contiene.getOffset();
            long finContiene=inicioContiene+contiene.getTama();

            long inicioContenido=contenido.getOffset();
            long finContenido=inicioContiene+contenido.getTama();

            if((inicioContenido>=inicioContiene)&&(finContenido<=finContiene)){
                return true;
            }
            return false;
        }

        /**
         * Comprueba si el fragmento "contenido" esta dentro, completo o parcial, de "contiene"
         *
         * @param contiene Fragmento en el que comprobar si esta dentro.
         * @param contenido Fragmento que queremos ver si esta contenido en "contiene"
         * @return Fragmento, que fragmento tiene "contiene" de "contenido".
         */
        public Fragmento fragmentoContenidoParcial(Fragmento contiene,Fragmento contenido){
            Fragmento fragmento=null;
            long inicioContiene=contiene.getOffset();
            long finContiene=inicioContiene+contiene.getTama();

            long inicioContenido=contenido.getOffset();
            long finContenido=inicioContiene+contenido.getTama();

            if((inicioContenido>=inicioContiene)||(finContenido<=finContiene)){
                long tama;
                long offset;
                if(fragmentoContenidoCompleto(contiene,contenido)){
                    return contenido;
                }
                if(inicioContenido>=inicioContiene){
                    offset=inicioContenido;
                    tama=finContiene;
                }else{
                    offset=inicioContiene;
                    tama=finContenido;
                }
                Archivo arch=_descargaActual.getArchivo();
                fragmento=new Fragmento(arch.getNombre(),arch.getHash(),tama,offset);
                return fragmento;
            }
            return null;
        }


        public void iniciar(){
            //_descargaActual= Almacen.dameSiguiente();
            if(_descargaActual.getEstado()==0){
                HolaQuiero mensaje=new HolaQuiero(_descargaActual.getArchivo());
                _gestor.addMensajeParaEnviar(mensaje);
                _descargaActual.reiniciarEstado();
            }else{
                // TODO buscar fragmentos para pedir, generar DAME y enviar
                _descargaActual.decrementarEstado();
            }
        }
}
