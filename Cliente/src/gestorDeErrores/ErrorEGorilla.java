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

package gestorDeErrores;

/**
 * Clase que representa todos los posibles errores de la aplicacion.
 * 
 * @author Qiang
 */
public class ErrorEGorilla {

    /**
     * Codigo que representa un error no identificado.
     */
    public static final int ERROR_NO_INDENTIFICADO = 100;
    /**
     * Codigo que representa un error en el Gestor de Red.
     */
    public static final int ERROR_RED = 101;
    /**
     * Codigo que representa un error en el Gestor de Disco.
     */
    public static final int ERROR_DISCO = 102;
    /**
     * Codigo que representa un error en la conexion al servidor.
     */
    public static final int ERROR_CONEXION_SERVIDOR = 103;

    /**
     * Devuelve una cadena con el mensaje de error asociado al codigo
     * de error <b>codigo</b>.
     * 
     * @param codigo Codigo del error asociado.
     * 
     * @return Una cadena con el mensaje de error asociado al codigo
     * de error <b>codigo</b>.
     */
    public static String dameMensaje(int codigo) {
        switch (codigo) {
            case ERROR_NO_INDENTIFICADO:
                return "Error no identificado. ";
            case ERROR_RED:
                return "Error en la red del sistema. ";
            case ERROR_DISCO:
                return "Error en disco. ";
            case ERROR_CONEXION_SERVIDOR:
                return "Error de perdida de conexión con servidor. ";
        }
        return "Error no identificado";
    }
}
