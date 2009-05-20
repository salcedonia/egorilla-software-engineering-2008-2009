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

package gestorDeEstadisticas;

/**
 * Esta intefaz se utiliza para que  el modulo que reciba los datos notifique
 * de su llegada a los observadores registrados.
 * Ejemplo de uso:
 *
 *  class clase1 implements NotificadorDatos{
 *      List<ObservadorDatos> listaDeObservadores;
 *      void addObservador(ObservadorDatos observador){
 *          listaDeObservadores.add(observador);
 *      }
 *      void addDatos(long longitud) {
 *          for (ObservadorDatos obs : listaDeObservadores)
 *              obs.llegadaDatos(longitud);
 *      }
 *      void addFichero() {
 *          for (ObservadorDatos obs : listaDeObservadores)
 *              obs.llegadaFichero(1);
 *      }
 *      
 *  }
 * @author Qiang
 */
public interface NotificadorDatos {

    void addSubidaDatos(double longitud);
    //void addDescargaDatos(double longitud);
    //void addFichero();
    void addObservador(ObservadorDatos observador);
 }
