/* 
	This file is part of eGorilla.

    eGorilla is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    eGorilla is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with eGorilla.  If not, see <http://www.gnu.org/licenses/>.
*/

package peerToPeer.subidas;

import gestorDeFicheros.GestorCompartidos;
import java.util.LinkedList;
import java.util.Queue;
import mensajes.p2p.Dame;
import mensajes.p2p.Toma;
import peerToPeer.egorilla.GestorEgorilla;

/**
 * 
 * @author JosÃ© Miguel Guerrero
 */

public class AlmacenSubidas extends Thread{

    private Queue<Dame> _cola;
    private GestorEgorilla _gestor;
    
    public AlmacenSubidas(GestorEgorilla gestor){
        _gestor=gestor;
        _cola = new LinkedList<Dame>();
        setName("Almacen Subidas");
        start();
    }
    
    public synchronized void nuevaSubida (Dame mensaje){
        _cola.add(mensaje);
        this.notify();
    }

    @Override
    public synchronized void run() {
        while(true){
            try {
                wait();
                while (!_cola.isEmpty()) {
                    Dame msjDame = _cola.poll();
                    //ahora se pide uno solo, si se envia Vector<Fragmento> crearlo en bucle
                    Byte[] informacion=GestorCompartidos.getInstancia().dameBytesDelFragmento(msjDame.getFragmento());
                    Toma mensajeToma=new Toma(msjDame.getNombre(),msjDame.getHash(),msjDame.getFragmento().getOffset(),informacion,msjDame.ipDestino(),msjDame.puertoDestino());
                    _gestor.addMensajeParaEnviar(mensajeToma);
                }
            } catch (InterruptedException ex) {
                System.out.println("*** Soy Almacen de Subidas: "+ex);
            }
        }
    }

    public void parar(){
        this.interrupt();
    }

    public synchronized void despierta (){
         this.notify();
    }
}
