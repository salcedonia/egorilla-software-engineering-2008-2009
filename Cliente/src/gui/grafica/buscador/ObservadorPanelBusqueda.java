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

package gui.grafica.buscador;

import datos.Archivo;

/**
 * Interfaz que implementa los metodos necesarios para que los observadores que
 * la implementan se enteren de los eventos producidos sobre la clase PanelBusquedas.
 * 
 * @author Javier Salcedo
 */
public interface ObservadorPanelBusqueda {

    /**
     * Avisa que se ha seleccionado una fila en el panel de busquedas.
     * 
     * @param archivo Archivo seleccionado.
     */
    public void archivoSeleccionado(Archivo archivo);
}
