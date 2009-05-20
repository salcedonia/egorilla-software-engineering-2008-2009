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

import datos.*;
import gestorDeRed.GestorDeRed;
import gestorDeRed.NetError;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mensajes.Mensaje;
import mensajes.serverclient.Bienvenido;
import mensajes.serverclient.DatosCliente;

/**
 * Clase que implementa un hilo de ejecución que se expande por parte del 
 * servidor cuando alguien se identifica.
 * Mantiene los datos necesarios para la comunicación con un cliente. 
 * Para empezar comenzará un hilo de ejecución que recibirá todos los datos
 * que el cliente envía al conectar, estos son:
 * 
 *      - Nombre del usuario.
 *      - Puerto por el que el usuario escucha a otros clientes.
 *      - Lista de ficheros compartidos que tiene el cliente.
 *
 * @author pitidecaner
 * @author Salcedonia
 */
public class ConexionCliente extends Thread {

    // ATRIBUTOS
    private GestorDeRed<Mensaje> _red;
    
    private DatosCliente _datosCliente;
    private ListadoClientes _listaClientes;


    /**
     * 
     * @param conexion
     * @throws java.io.IOException
     */
    public ConexionCliente(GestorDeRed<Mensaje> conexion, 
                           ListadoClientes      listaClientes,
                           DatosCliente datos){
        _red = conexion;
        _datosCliente = datos;
        _listaClientes = listaClientes;
    }

    /**
     * Método que ejecuta el hilo.
     */
    @Override
    public void run() {
        Bienvenido b;
     
        _listaClientes.altaCliente(_datosCliente.getIP(),
                _datosCliente.getPuertoEscucha(),
                _datosCliente);

        b = new Bienvenido();
        b.numeroDePeers = _listaClientes.getClientes().size();
        // TODO: sacar esta info de algún sitio
        //b.numeroDeArchivos

        b.setDestino(_datosCliente.getIP(), _datosCliente.getPuertoEscucha());

        //TODO: logger
        System.out.println("conectando " + _datosCliente.getIP());

        try {
            _red.envia(b, _datosCliente.getIP(), _datosCliente.getPuertoEscucha());
        } catch (NetError ex) {
            _red.eliminaConexion(_datosCliente.getIP());
            //TODO: logger
            System.out.println("error de comunicacion con " + _datosCliente.getIP());
        }
 
    }
}
