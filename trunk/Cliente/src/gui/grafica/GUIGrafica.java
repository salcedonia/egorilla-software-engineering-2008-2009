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

package gui.grafica;

import gestorDeConfiguracion.ControlConfiguracionClienteException;
import gui.grafica.principal.ControladorVentanaPrincipal;
import gui.grafica.principal.GUIVentanaPrincipal;

/**
 * Clase que se encarga de gestionar la gui de la aplicación en modo gráfico.
 * 
 * @author Javier Salcedo
 */
public class GUIGrafica {
    
    /**
     * Constructor de la clase GUIGrafica.
     * 
     * @param controlador Controlador de la ventana principal en modo grafico.
     */
    public GUIGrafica(ControladorVentanaPrincipal controlador) throws ControlConfiguracionClienteException{  
        
        new GUIVentanaPrincipal(controlador);
    }
}
