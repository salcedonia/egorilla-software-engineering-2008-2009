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

package gestorDeConfiguracion;

import java.util.Properties;

/**
 * Interfaz que deben implementar los observadores sobre el objeto ControlConfiguracionCliente
 * @author F. Javier Sanchez Pardo
 */
public interface ObservadorControlConfiguracionCliente {
    /**
     * @param obj El objeto ControlConfiguracionCliente se pasa siempre por si 
     * es necesario acceder a él para recuperar más información para tratar el cambio.
     * @param propiedades: propiedades que han cambiado (clave/valor nuevo).
     */    
    public void cambioEnPropiedades (ControlConfiguracionCliente obj, Properties propiedades);
}
