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

/**
 *
 * @author pitidecaner
 */
public interface Receptor<E> {

   public void recibeMensaje (E msj, String ip, int port);
   
   /**
    * se notifica a los receptores que se ha perdido la conexion con
    * un peer
    * 
    * @param ip ip del host con el que se ha perdido la conexión
    */
   public void perdidaDeConexion (String ip);
}
