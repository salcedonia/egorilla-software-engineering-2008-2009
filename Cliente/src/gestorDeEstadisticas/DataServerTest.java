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

import java.util.logging.Level;
import java.util.logging.Logger;
import peerToPeer.descargas.ObservadorAlmacenDescargas;

/**
 *
 * @author Qiang
 */
public class DataServerTest  extends Thread implements ObservadorAlmacenDescargas{
    GestorEstadisticas gestor = GestorEstadisticas.getInstacia();
    public DataServerTest() {
        
    }

    @Override
    public void run() {
        
        while (true) {
            int cont = 0;
            try {
                fragmentoDescargado("");
                descargaCompleta("");
                Thread.sleep(15000);
            } catch (InterruptedException ex) {
                Logger.getLogger(DataServerTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void nuevaDescarga(String nombre, String hash, int tamanio) {
        //
    }

    @Override
    public void fragmentoDescargado(String hash) {
        gestor.llegadaDatosDescarga(512);
    }

    @Override
    public void eliminarDescarga(String _hash) {
      // TODO ELIMINADO
    }

    @Override
    public void descargaCompleta(String hash) {
        gestor.descargaCompleta("");
    }

    @Override
    public void descargaPausada(String hash) {
        //TODO PAUSA DESCARGA
    }

}
