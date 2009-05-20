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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestorDeRed;

import java.net.Inet4Address;

/**
 *
 * @author pitidecaner
 */
public interface GestorDeRed<E>{

    /**
     * A partir del momento en el que se llama a este método se expande un
     * hilo el cual estará atento de escuchar un determinado puerto.
     * dicho puerto se deja a discrección de quien instancie la implementación
     * de esta interface.
     */
    public void comienzaEscucha();

    /**
     * acaba con la escucha y libera los recursos
     */
    public void terminaEscucha();
    
    /**
     * envía un paquete de tipo E a la dirección especificada
     * @param var el paquete a enviar
     * @param destino destino en formato ip v4
     * @param port puerto a enviar
     */
    public void envia(E var, Inet4Address destino, int port) throws NetError;
    /**
     * envía un paquete de tipo E a la dirección especificada
     * @param var el paquete a enviar
     * @param host el nombre de host o ip
     * @param port el puerto
     */
    public void envia(E var, String host, int port) throws NetError;

    /**
     * registra un receptor que se hara cargo de los mensajes recibidos por el
     * puerto especificado en el constructor
     *
     * @param r el receptor debe implementar esta interface.
     */
    public void registraReceptor(Receptor<E> r);

    /**
     * indica que mantendremos una conexion con este host, si se pierde la comunicación
     * se propagará un error al los receptores
     *
     * @param host el host al que mantener vigilado
     * @param puerto el puerto por el que escucha
     */
    public void addConexion(String host, int puerto);

    /**
     * elimina la conexion con determinado host, de forma que si ya no necesitamos
     * saber de él se elimina
     *
     * @param host el host que no deseamos saber si esta online
     */
    public void eliminaConexion(String host);
    
}