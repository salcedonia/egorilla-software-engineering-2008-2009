package peerToPeer.egorilla;

import datos.Archivo;
import datos.Fragmento;
import gestorDeEstadisticas.GestorEstadisticas;
import gestorDeEstadisticas.NotificadorDatos;
import gestorDeEstadisticas.ObservadorDatos;
import gestorDeRed.Receptor;
import java.util.*;
import mensajes.Mensaje;
import mensajes.p2p.*;
import mensajes.serverclient.RespuestaPeticionDescarga;
import gestorDeFicheros.*;
import mensajes.serverclient.RespuestaPeticionConsulta;

/**
 * Esta clase es lo que implementa la parte servidora de un cliente
 * esto es, cuando recibe un mensaje, lo decodifica y opera con el.
 * 
 * se encarga, basicamente de realizar toda la negociaci�n.
 * 
 * TODO: muy importante, cada vez que se ejecuta esto deberia hacerse
 * en un hilo paralelo, para permitir la concurrencia, ahora mismo no
 * lo hace y esto sera una perdida de rendimiento
 * 
 * @author Luis Ayuso, José Miguel Guerrero
 */
public class ServidorP2PEgorilla implements Receptor<Mensaje>, NotificadorDatos{

    private GestorEgorilla _gestor;
    
    private List<ObservadorDatos> listaDeObservadores;
       
    /**
     * constructor b�sico:
     * necesita de dos cosas que estan fuera de su hambito, el gestor de egorilla
     * que es el que basicamente dice que hay que hacer
     * y el gestor de descargas, que es un modulo exterior que comprueba todo lo 
     * necesario
     * 
     * @param gE gestor eGorilla
     * @param gD bestor de descargas
     */
    public ServidorP2PEgorilla(GestorEgorilla gE) {
       _gestor = gE;
       listaDeObservadores = new ArrayList<ObservadorDatos>();
       addObservador(GestorEstadisticas.getInstacia());
       //_descargas = gD;
       //Tal vez no sea el mejor sitio para iniciar el gestorDisco, mejor antes para que se pueda
       //ver los temporales y compartidos sin tener q instanciar el servidor.
       //_gestorDisco = new GestorDisco();
       //_fragmentador = gestorDisco.getFragmentador();
    }

    /**
     * 
     * heredado de la interface, es el metodo por el cual se nos comunica que ha
     * llegado un mensaje nuevo
     * 
     * 
     * @param msj que?
     * @param ip de quien?
     * @param port dnd contesto?
     */
    @Override
    public void recibeMensaje(Mensaje msj, String ip, int port) {
        switch (msj.getTipoMensaje()){
     
       //------------------ Mensajes del peer to peer  -------------------------
            case Altoo:
                
                // elimino toda ocurrencia del peer en mis listas
                
                // NO SE CONTESTA
                
                break;
                
                
            case Conozco:
                
                // por ahora nada
                
                break;
                
                
            case Dame:
                
                Dame msjDame = (Dame) msj;
                //en el mensaje le digo a quien debe enviarselo
                msjDame.setDestino(ip, port);
                
                _gestor.getAlmacenSubidas().nuevaSubida(msjDame);
                break;
                
                
            case HolaQuiero:
                
                HolaQuiero quiero = (HolaQuiero) msj;     
                // si tengo el fichero
                // TODO: hay que buscar entre las descargas pendientes y los ficheros
                // completos

                //Se supone que llega aqui xq el servidor le ha dicho que yo tengo el fichero, aun asi
                //cuando se va a pregutar que fragmentos tengo de ese fichero (xq no se sabe en que estado
                //le tengo) ya se hace, luego se comprueba quieroen funci�n de la salida queFragmentosTienes
                Vector<Fragmento> listaFragmentos;
                listaFragmentos = GestorCompartidos.getInstancia().queFragmentosTienes( quiero.getHash() );

                Tengo resp = new Tengo( quiero.getNombre(), quiero.getHash(), listaFragmentos, 
                    ip, port);
                /*resp.hash = quiero.hash;
                resp.nombre = quiero.nombre;*/  //TODO: deberia poner el nombre que tengo yo para este fichero
                
                // apunto direccion del peer e interes
                               
                // CONTESTA: tengo
                _gestor.addMensajeParaEnviar(resp);
                
                break;
                
            case Tengo:
                
                // evaluar fragmentos que tiene tengo y tienen y decidir cuales quiero
                // por ahora todos! 
                
                Tengo reciv = (Tengo) msj;
                msj.setDestino(ip, port);
                
                /*Dame respuesta =  new Dame();                
                respuesta.hash = reciv.hash;
                respuesta.nombre = reciv.nombre;*/
                
                // si el Tengo viene vacio, se acaba aqui la ejecución
                if((reciv.getFragmentos() == null)||(reciv.getFragmentos().isEmpty()))
                    return;
                
                // comprobar al menos que no estemos hablando un conjunto vacio
                // en ese caso es que el peer no tiene el archivo que buscamos
                //Dame respuesta =  new Dame( reciv.getHash(), reciv.getNombre(), reciv.getFragmentos() );
                
                // CONTESTA dame
                //_gestor.addMensajeParaEnviar(respuesta);
                _gestor.actualizaDescarga(reciv);
                break;
                
                
            case Toma:
                // este es el más importante, no es la negociacion, es el envio
                // de paquetes
                
                // conozco al peer??
                //TODO: comprobar esto, por ahora asumo que si
                
                Toma paquete = (Toma)msj;
                                
                Fragmento f = new Fragmento( paquete.getNombre(), paquete.getHash(),
                    paquete.getParte().length, paquete.getOffset() );
                _gestor.fragmentoDescargado(f,paquete.getParte());
                addSubidaDatos(f.getTama());
                /*f.hash   = paquete.hash;
                f.nombre = paquete.nombre;
                f.offset = paquete.offset;
                f.tama   = paquete.chunk.length;*/
                
                //TODO:  envio fragmeto a gestor de descargas o lo que sea
               
                
                break;
                
   //-------------------------   Comunicaciones con el servidor ----------------             
                
            case RespuestaPeticionDescarga:
                
                RespuestaPeticionDescarga respDes = (RespuestaPeticionDescarga) msj;
                
                Archivo a =new Archivo(respDes.nombre, respDes.hash, respDes.tama, respDes.tipo);
                                
                _gestor.DescargaFichero(a, respDes.getLista());
                
                break;
                
            case RespuestaPeticionConsulta:
                
                _gestor.resultadoBusqueda((RespuestaPeticionConsulta)msj);
                
                break;
                
            case Bienvenido:
                
                // esto indica que estamos conectados
                _gestor.conexionCompletada();
                _gestor.enviaListaArchivos();
                _gestor.reanudarDescargas();
        }
    }

    @Override
    public void perdidaDeConexion(String ip) {
       _gestor.perdidaDeConexion(ip);
    }
/* Estas funciones avisa al gestor de estadisticas que los datos que enviamos.
 
 */
    @Override
    public void addSubidaDatos(double longitud) {
          for (ObservadorDatos obs : listaDeObservadores)
               obs.llegadaDatosSubida(longitud);
    }

    @Override
    public  void addObservador(ObservadorDatos observador){
           listaDeObservadores.add(observador);
    }   
    

}
