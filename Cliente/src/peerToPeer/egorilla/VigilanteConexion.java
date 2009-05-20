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

package peerToPeer.egorilla;

import java.util.ArrayList;
import peerToPeer.EstadoP2P;
import peerToPeer.ObservadorP2P;

/**
 * El vigilante de conexion se encarga de realizar una espera para asegurar que
 * la conexion se realiza en un tiempo de 2 segundos.
 *
 * @author Luis Ayuso
 */
public class VigilanteConexion extends Thread{

    ArrayList<ObservadorP2P> _observadores;

    public VigilanteConexion() {
        _observadores = new ArrayList<ObservadorP2P>();
        this.setName("vigilante conexión");
    }

    @Override
    public synchronized void run (){
        try {
            wait(10000);

            synchronized (this) {
                for (ObservadorP2P observadorP2P : _observadores) {
                    observadorP2P.cambioEstado(EstadoP2P.DESCONECTADO, null, -1);
                }
            }
        } catch (InterruptedException ex) {
             System.out.println("*** Soy el vigiliante de la conexion, todo ha ido guay");
        }
    }

    /**
     * la conexion se ha realizado satisfactoriamente, por lo que se acaba con
     * este hilo
     */
    public synchronized  void conexionCompletada(){
        this.interrupt();
    }

    /**
     * agrega un observador para notificar errores de conexion
     * @param obs
     */
    public void agregarObservador(ObservadorP2P obs){
        _observadores.add(obs);
    }
}
