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

package gui.grafica.trafico;

import peerToPeer.GestorP2P;
import peerToPeer.egorilla.GestorEgorilla;

/**
 * Controlador del panel de trafico.
 * 
 * @author Javier Salcedo
 */
public class ControladorPanelTrafico {

    /**
     * Gestor eGorilla de la aplicacion.
     */
    private GestorP2P _gestorEGorilla;
    
    /**
     * Constructor de la clase ControladorPanelTrafico.
     * 
     * @param gestorEGorilla Gestor eGorilla de la aplicacion.
     */
    public ControladorPanelTrafico(GestorP2P gestorEGorilla) {
        
        _gestorEGorilla = gestorEGorilla;
    }
    
    /**
     * Devuelve el GestorEGorilla de la aplicación.
     * 
     * @return El GestorEGorilla de la aplicación.
     */
    public GestorP2P getGestorEGorilla() {
        return _gestorEGorilla;
    }
}
