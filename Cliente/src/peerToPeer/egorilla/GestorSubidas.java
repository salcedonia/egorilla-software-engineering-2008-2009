/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

package peerToPeer.egorilla;


import datos.Fragmento;
import java.util.*;
import gestorDeFicheros.*;
import mensajes.p2p.*;

/**
 *
 * @author Luis Ayuso
 */
public class GestorSubidas extends Thread{
    
    /*private Hashtable<String, Fragmento> _paraEnviar;
    private Hashtable<String, Integer> _direcctorio;*/
  //Unificado en una sola tabla, destino es un par de IP-Puerto, (una IP puede tener varios puertos)
  private Hashtable<UsuarioDestino, Vector<Fragmento>> _paraEnviar;

  //private GestorDisco _gestorDisco;
  private Fragmentador _fragmentador;

  private GestorEgorilla _gestor;

    
    public GestorSubidas(GestorEgorilla gestor, GestorDisco gestorDisco) {
        /*_paraEnviar = new Hashtable<String, Fragmento>();
        _direcctorio = new Hashtable<String, Integer>();*/
      _paraEnviar = new Hashtable<UsuarioDestino, Vector<Fragmento>>();
      _fragmentador = gestorDisco.getFragmentador();
    }
    
    /**
     * establece que se enviar�n estos fragmentos a este cliente
     * @param ip ip del cliente destino
     * @param puerto puerto a enviar los datos
     * @param fragmentos lista de fragmentos aceptados por el otro cliente
     */
    public void comenzarSubida(String ip, int puerto, Vector<Fragmento> fragmentos){
      
      boolean esta = false;
      Vector<Fragmento> fragmentosAux = null;
      UsuarioDestino usuarioDestino = new UsuarioDestino( ip, puerto ), usuarioAux;

      //TODO: para optimizar recorro la tabla para que en caso de que este, no tener que buscar donde
      //y poder asi insertar los fragmentos en caso de existir
      Iterator it = _paraEnviar.entrySet().iterator();
      while ( it.hasNext() ){
        Map.Entry e = (Map.Entry)it.next();
        usuarioAux = (UsuarioDestino) e.getKey();
        //fragmentosAux = (Vector<Fragmento>) e.getValue();
        if( usuarioDestino.compareTo( usuarioAux ) == 0 ){
          //recupero la referencia de los fragmentos que ya tiene asociados
          fragmentosAux = (Vector<Fragmento>) e.getValue();
          esta = true;
        }
      }

      //esta = estaDestinoEnEnviar( usuarioDestino );
      
      // ya esta la ip-puerto
      if( esta == true ){
        // aqui no se si deberia comprobarse si los fragmentos que se aniaden ya estan
        // si estan y le aniado puede tener duplicados (tal esto no vaya a ocurrir),
        // se sabe cuando un cacho ha llegado bien? Cuando llega bien entonces si le quito
        fragmentosAux.addAll( fragmentos );
        // de aqui. Todo esto me plantea la duda de que se hace cuando se recibe un fragmento-byte
        // que ya tenemos, se descarta (si se descarta, donde), se cuela o que.      

      //sino esta la IP-puerto pues se aniade a la tabla el nuevo destino y sus fragmentos
      }else{
        _paraEnviar.put( usuarioDestino, fragmentos );
      }
    }

    public void run(){
      //Los fragmentos no tiene por que pertencer al mismo fichero
      //Enumeration<Fragmento> elems;
      
      Toma mensajeToma;

      Byte[] bytesFragmento;

      UsuarioDestino destinoActual;
      Vector<Fragmento> fragmentosDestinoActual;

      Fragmento fragmentoSeleccionado;
      
      //Como CREO se puede ir llenando(por otro lado) cuando este recorriendo la tabla, 
      //compruebo xsi acaso no esta vacia, igual es redundante, segun como funcione realmente
      while( _paraEnviar.isEmpty() == false ){
        Iterator it = _paraEnviar.entrySet().iterator();
        //Recorro la tabla para ir enviando lo que se pide, puede dar problemas de sincronismo?
        //Tal vez esto ya valga como el isEmpty
        while ( it.hasNext() ){
          Map.Entry e = (Map.Entry)it.next();
          destinoActual = (UsuarioDestino) e.getKey();
          fragmentosDestinoActual = (Vector<Fragmento>) e.getValue();
          
          //No me mola todo esto para enviar un solo Fragmento de/a cada Destino
          if( fragmentosDestinoActual.size() > 0 ){
            fragmentoSeleccionado = fragmentosDestinoActual.get( 0 );
            //Posiblemente luego fusione los dos fragmentadores, completos e incompletos
            //Idea: simplemente mirar que hash tiene el fragmento y con eso ya discernir
            //Es decir, que el fragmentador busco el solito a que lista pertenece
            // ir al gestor, recoger el chunk          
            bytesFragmento = _fragmentador.dameBytesDelFragmento( fragmentoSeleccionado );
            // crear paquete Toma
            // igual pasarle al constructor algo tipo String[] getInfoFragmento
            mensajeToma = new Toma( fragmentoSeleccionado.getNombre(), fragmentoSeleccionado.getHash(), 
            fragmentoSeleccionado.getOffset(), bytesFragmento, destinoActual.getIP(), destinoActual.getPuerto() );
            _gestor.addMensajeParaEnviar( mensajeToma );
          }else{
            //Como ya no tienes fragmentos para enviar a este usuario, lo quitamos de la tabla
            _paraEnviar.remove( destinoActual );
          }
        }
      }
    } //fin run    
}

class UsuarioDestino implements Comparable<UsuarioDestino>{
  private String IP;
  private int puerto;

  public UsuarioDestino(String IP, int puerto){
    this.IP = IP;
    this.puerto = puerto;
  }

  @Override
  public boolean equals(Object objeto){
    UsuarioDestino userAux = (UsuarioDestino)objeto;
    //Tratar posible exception al hacer el casting
    return IP.equals( userAux.IP ) && puerto == userAux.puerto;

  }

  public String getIP(){
    return IP;
  }

  public int getPuerto(){
    return puerto;
  }

  public int compareTo(UsuarioDestino usuarioDestino) {
    // se comprueba que va al mismo puerto (dos egorillas en misma IP, dif port)
    if( IP.compareTo( usuarioDestino.IP ) == 0 ){
      if( puerto == usuarioDestino.puerto ){
        //IP y puerto igual, pues 0
        return 0;
      }else{
        //IP igual, pero puerto no, pues 1, > 0
        //Significa que hay al menos dos egorillas bajo la misma IP
        return 1;
      }
    }else{
        //Si ni la IP no concide, pues na
        return -1;
    }
  }
}
