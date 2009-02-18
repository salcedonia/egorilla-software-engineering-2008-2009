/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestorDeRed;

import java.util.Vector;


/**
 *  Punto que aglutina todas las comunicaciónes de red. 
 * Todos los objetos que se envian deben de implementar la interface mensaje
 *
 * esta clase es un SINGLETON, se obtiene la instancia con el método getInstance
 *
 * @author pitidecaner
 */
public class GestorDeRed {


    static GestorDeRed _singleton = null;   // singleton

    private Vector<RedListener> _listeners;

    /**
     * Constructor privado
     * 
     */
    private GestorDeRed() {
        _listeners = new Vector<RedListener>();
        //TODO: parametrizar si es necesario, no estoy seguro de que sea necesario
    }

    /**
     * nos devuelve la instancia unica de esta clase
     *
     * @return la unica instancia que puede haber
     */
    public static GestorDeRed getInstance(){
        if (_singleton == null)
            _singleton= new GestorDeRed();
        return _singleton;
    }

   /**
    * Cuando se reciba un mensaje sera publicitado entre los listeners.
    * Si queremos que nuestos artefactos "escuchen la red" debemos implementar
    * la interface RedListener y suscribirlo.
    *
    * @param l el modulo que estara a la espera de mensajes
    */
    public void addListener(RedListener l){
        _listeners.add(l);
    }

    /**
     * Cuando llega un mensaje este se despacha entre los listeners.
     *
     * @param msj el mensaje a despachar
     */
    private void despachaMensaje(Mensaje msj){
        // itera hasta que alguien lo acepta
        for (RedListener redListener : _listeners) {
            if (redListener.recibeMensaje(msj))
                break;
        }
    }
}
