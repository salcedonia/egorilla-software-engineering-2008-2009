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
import peerToPeer.GestorP2P;
import peerToPeer.egorilla.GestorEgorilla;

/**
 * Controlador de la pestania de busquedas.
 * 
 * @author Javier Salcedo
 */
public class ControladorPanelBuscador {

    /**
     * Gestor eGorilla de la aplicacion.
     */
    private GestorP2P _gestorEGorilla;
    
    /**
     * Constructor de la clase ControladorPanelBuscador.
     * 
     * @param gestorEGorilla Gestor eGorilla de la aplicacion.
     */
    public ControladorPanelBuscador(GestorP2P gestorEGorilla) {
    
        _gestorEGorilla = gestorEGorilla;
    }
    
    /**
     * Realiza una petición al GestorEGorilla de una búsqueda de un fichero.
     * 
     * @param nombre Nombre del fichero a buscar.
     */
    public void peticionBuscarFichero(String nombre) {

        _gestorEGorilla.nuevaConsulta(nombre);
    }
    
    /**
     * Da la orden para proceder a peticionDescargarFichero un fichero.
     *
     * @param Archivo El archivo que se pretende descargar.
     */
    public void peticionDescargarFichero(Archivo archivo) {

        _gestorEGorilla.nuevaDescarga(archivo);
    }
    
    /**
     * Comprueba si estamos conectados al servidor.
     * 
     * @return Verdadero si estamos conectados al servidor y falso en caso contrario.
     */
    public boolean conectado() {

        return _gestorEGorilla.estaConectadoAServidor();
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
