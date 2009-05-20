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

package gui.grafica.servidores;

import peerToPeer.GestorP2P;
import peerToPeer.egorilla.GestorEgorilla;

/**
 * Controlador de la pestania de servidores.
 * 
 * @author Javier Salcedo
 */
public class ControladorPanelServidores {

    /**
     * GestorEGorilla de la aplicacion.
     */
    private GestorP2P _gestorEGorilla;
    
    /**
     * Constructor de la clase ControladorPanelServidores.
     * 
     * @param gestorEGorilla Gestor eGorilla de la aplicacion.
     */
    public ControladorPanelServidores(GestorP2P gestorEGorilla){
    
        _gestorEGorilla = gestorEGorilla;
    }
    
    /**
     * Realiza una petición de conexión al servidor al GestorEGorilla.
     * 
     * @param IP IP del servidor.
     * @param puerto Puerto del servidor.
     * @throws java.lang.Exception
     */
    public void peticionConexionAServidor(String IP, int puerto) throws Exception {

        _gestorEGorilla.conectarAServidor(IP, puerto);
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
