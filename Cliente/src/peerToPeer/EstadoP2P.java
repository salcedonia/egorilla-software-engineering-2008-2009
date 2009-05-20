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

package peerToPeer;

/**
 *  Este enumerado indica el estado que se encuentra el P2P.
 * <ul>
 *  <li>DESCONECTADO : Estado desconectado, todos los servicios deben estar parados.</li>
 *  <li>NEGOCIANDO: Estado de negociacion, los servicios estan parados, se ha enviado
 *  una petición de conexión al servidor pero todavia no se ha obtenido 
 *  respuesta</li>
 *  <li>CONECTADO: Estado conectado, el servidor ha respondido correctamente, todos los
 *  servicios estan activos.</li>
 * <ul>
 * @author Qiang
 */
public enum EstadoP2P {
    DESCONECTADO,
    NEGOCIANDO,
    CONECTADO
}
