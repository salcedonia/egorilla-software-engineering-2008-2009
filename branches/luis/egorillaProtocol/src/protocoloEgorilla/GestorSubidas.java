/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

package protocoloEgorilla;


import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import peerToPeer.Fragmento;

/**
 *
 * @author Luis Ayuso
 */
public class GestorSubidas extends Thread{

    
    private Hashtable<String, Fragmento> _paraEnviar;
    private Hashtable<String, Integer> _direcctorio;
    
    private GestorEgorilla _gestor;
    
    public GestorSubidas(GestorEgorilla gestor) {
        _paraEnviar = new Hashtable<String, Fragmento>();
        _direcctorio = new Hashtable<String, Integer>();
    }
    
    public void comenzarSubida(String ip, int puerto, ArrayList<Fragmento> fragmentos){
        if (_direcctorio.contains(ip)){
            // ya esta la ip, no hacemos nada
        }
        else{
            _direcctorio.put(ip, new Integer(puerto));
        }
        
        for (Fragmento fragmento : fragmentos) {
            _paraEnviar.put(ip, fragmento);
        }
    }

    public void run(){
        Enumeration<Fragmento> elems;
        do{
            
            elems = _paraEnviar.elements();
            for (elems =_paraEnviar.elements(); elems.hasMoreElements();) {
                // TODO: acabar esto
                // ir al gestor, recoger el chunk, 
                // crear paquete Toma
                // encolar para enviar
                elems.nextElement();
            }
            
            // TODO: hacer que esto pare de alguna forma
        }while (true);
    }
    
}
