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
package gui.consola;

import datos.Archivo;

/**
 * Almacena los resultados de la última búsqueda realizada al servidor.
 *
 * @author Luis Ayuso
 */
class GUIBusqueda {
    
    /**
     * Datos de la última búsqueda realizada.
     */
    private  Archivo[] _ultimaBusqueda;

    /**
     * Devuelve el archivo asociado a un hash de entre los archivos que
     * ha devuelto la última búsqueda.
     * 
     * @param hash Hash del archivo a averiguar.
     * 
     * @return El archivo asociado a un hash de entre los archivos que
     * ha devuelto la última búsqueda.
     */
    public Archivo dameArchivoPorHash (String hash){
        
        if (_ultimaBusqueda == null)
            return null;
        else
            for (Archivo arch: _ultimaBusqueda){
                if (arch._hash.equals(hash))
                    return arch;
            }
        return null;
    }

    /**
     * Establece _ultimaBusqueda a valor <b>ultimaBusqueda</b>.
     * 
     * @param ultimaBusqueda Nuevo valor a establecer.
     */
    public void setBusqueda( Archivo[]  ultimaBusqueda){
        
        _ultimaBusqueda = ultimaBusqueda;
    }
}
