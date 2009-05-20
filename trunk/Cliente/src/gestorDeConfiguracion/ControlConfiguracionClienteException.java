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


/**
 *
 * @author F. Javier Sanchez Pardo
 * Esta clase gestiona las excepciones especificas que se pueden producir dentro
 * del paquete Cliente.gestorDeConfiguracion
 * 
 */


public class ControlConfiguracionClienteException extends Exception{

    public ControlConfiguracionClienteException (Exception e){
        super(e);
    }

    public ControlConfiguracionClienteException (String sMensaje){
        super(sMensaje);
    }
    
    public ControlConfiguracionClienteException (String sMensaje, Exception e){
        super (sMensaje, e);
    }
}
