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
package servidor.tareas;

import datos.Archivo;
import datos.ArchivoClientes;
import gestorDeRed.GestorDeRed;
import mensajes.Mensaje;
import mensajes.serverclient.DatosCliente;
import mensajes.serverclient.ListaArchivos;

/**
 *
 * @author Luis Ayuso
 */
public class ProcesaListaDeFicheros extends Thread {

    // ATRIBUTOS
    private GestorDeRed<Mensaje> _red;
    
    private DatosCliente _datosCliente;
    private ArchivoClientes _listaficheros;
    private ListaArchivos _archivosEnviados;


    /**
     * 
     * @param conexion
     * @throws java.io.IOException
     */
    public ProcesaListaDeFicheros(GestorDeRed<Mensaje> conexion, 
                           ArchivoClientes      listaficheros,
                           DatosCliente datos,
                           ListaArchivos archivos){
        _red = conexion;
        _datosCliente = datos;
        _listaficheros = listaficheros;
        _archivosEnviados = archivos;
    }
    
    @Override
    public void run(){
        
        _listaficheros.actualizarDesdeListaCliente(_datosCliente, _archivosEnviados);

           //TODO: logger
        System.out.println("\nLista de archivos recibida de " + _datosCliente.getIP());   
        System.out.println("===================================================");
        for (Archivo a : _archivosEnviados) {
            System.out.println(a);
        }
        System.out.println();       
    }
}
