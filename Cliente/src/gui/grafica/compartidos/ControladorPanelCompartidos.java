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

package gui.grafica.compartidos;

import gestorDeFicheros.GestorCompartidos;
import java.io.File;
import mensajes.serverclient.ListaArchivos;
import peerToPeer.GestorP2P;

/**
 * Controlador del panel de compartidos. Recibe peticiones desde el panel de 
 * compartidos ante acciones de los usuarios y se las comunica al modelo.
 * 
 * @author Javier Salcedo, Mercedes Bernal
 */
public class ControladorPanelCompartidos {

    /**
     * GestorEGorilla de la aplicacion.
     */
    private GestorP2P _gestorEGorilla;
    
    /**
     * Constructor de la clase ControladorPanelCompartidos.
     * 
     * @param gestorEGorilla Gestor eGorilla de la aplicacion.
     */
    public ControladorPanelCompartidos(GestorP2P gestorEGorilla){
    
        _gestorEGorilla = gestorEGorilla;
    }
    
    /**
     * Devuelve la lista de todos los archivos compartidos del cliente. Para ello pregunta
     * al Gestor de compartidos que es el que tiene toda la informacion necesaria.
     * 
     * @return La lista de archivos compartidos del cliente.
     */
    public ListaArchivos peticionListarTodosCompartidos(){
    
        return GestorCompartidos.getInstancia().getArchivosCompartidos();       
    }

    /**
     * Devuelve la lista de archivos compartidos completos del cliente. Para ello pregunta
     * al Gestor de compartidos y este a su vez llama al gestor de disco para 
     * preguntarle todo lo necesario.
     * 
     * @return La lista de archivos compartidos completos del cliente.
     */
    public ListaArchivos peticionListarCompartidosCompletos() {
        
        return GestorCompartidos.getInstancia().getGestorDisco().getListaArchivosCompletos();   
    }
    
    /**
     * Devuelve la lista de archivos compartidos incompletos del cliente. Para ello pregunta
     * al Gestor de compartidos y este a su vez llama al gestor de disco para 
     * preguntarle todo lo necesario.
     * 
     * @return La lista de archivos compartidos incompletos del cliente.
     */
    public ListaArchivos peticionListarCompartidosIncompletos() {
        
        return GestorCompartidos.getInstancia().getGestorDisco().getListaArchivosTemporales();   
    }
    
    /**
     * Manda la peticion necesaria al Gestor de Disco para que actualice su lista
     * de archivos completos dado que solo la inicializa al iniciarse el sistema.
     */
    public void peticionRefrescarArchivosCompletos() {
    
        String dirCompletos = GestorCompartidos.getInstancia().getGestorDisco().getDirectorioCompletos();
        GestorCompartidos.getInstancia().getGestorDisco().listarArchivosCompletosIniciales( new File(dirCompletos) );
        GestorCompartidos.getInstancia().getGestorDisco().setListaArchivosTodos(
            GestorCompartidos.getInstancia().getGestorDisco().getManejarListaArchivos().unirListas( 
              GestorCompartidos.getInstancia().getGestorDisco().getListaArchivosTemporales(), 
              GestorCompartidos.getInstancia().getGestorDisco().getListaArchivosCompletos() ) 
            );
    }
}
