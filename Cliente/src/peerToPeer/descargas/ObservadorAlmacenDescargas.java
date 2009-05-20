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

package peerToPeer.descargas;

/**
 * Interfaz que implementa los métodos necesarios para aquellos observadores
 * del Alamacén de descargas.
 * 
 * @author Jose Miguel Guerrero
 */
public interface ObservadorAlmacenDescargas {

    
    /**
     * Notifica una nueva descarga
     *
     * @param nombre Nombre del archivo a descargar.
     * @param hash Hash del archivo a descargar.
     * @param tamanio Tamanio del archivo a descargar.
     */
    public void nuevaDescarga(String nombre, String hash, int tamanio);
    
    /**
     * Notifica un fragmento completado
     * 
     * @param hash Hash del archivo al que corresponde el fragmento descargado
     */
    public void fragmentoDescargado(String hash); 
    
    public void descargaCompleta(String hash) ;
    
    public void descargaPausada(String hash) ;
    
    public void eliminarDescarga(String hash);
}
