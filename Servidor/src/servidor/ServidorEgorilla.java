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

package servidor;

import datos.ListadoClientes;
import datos.ArchivoClientes;
import gestorDeRed.GestorDeRed;
import gestorDeRed.Receptor;
import mensajes.Mensaje;
import mensajes.serverclient.DatosCliente;
import mensajes.serverclient.ListaArchivos;
import mensajes.serverclient.PeticionConsulta;
import mensajes.serverclient.PeticionDescarga;
import servidor.tareas.ConexionCliente;
import servidor.tareas.ProcesaListaDeFicheros;
import servidor.tareas.ProcesarPeticionBusqueda;
import servidor.tareas.ProcesarPeticionDescarga;

/**
 *
 * Esta clase es la que implementa todas las actividades del servidor, como 
 * es un servidor que realiza las acciones por petición, solo se desarrollan 
 * estas a consecuencia de una llamada
 * 
 * @author Luis Ayuso
 */
public class ServidorEgorilla implements Receptor<Mensaje> {

    private GestorDeRed<Mensaje> _red;
    private ArchivoClientes _archivoClientes;
    private ListadoClientes _clientes;
    
    
    public ServidorEgorilla(GestorDeRed<Mensaje> red, ListadoClientes clientes, ArchivoClientes archivoClientes) {
        _red = red;
        _clientes = clientes;
        _archivoClientes = archivoClientes;
    }
    
    public void recibeMensaje(Mensaje msj, String ip, int port) {
        DatosCliente datos;
        
        switch (msj.getTipoMensaje()){
            case DatosCliente:
                // da de alta al cliente
                
                // esto es una trampa, para averiguar la ip publica se rellena 
                // en el servidor el campo ip
                datos =  (DatosCliente) msj;
                datos.setIP(ip);
                
                //realiza la conexion del cliente
                ConexionCliente con = new ConexionCliente(_red,_clientes,datos);
                con.start();

                // como es una conexion mantendremos comprobacion del medio
                //TODO: mirate esto 
            
                 _red.addConexion(ip, port);

                // devuelve bienvenido, pero dentro del hilo
                break;
            case PeticionConsulta:
                // si no conocemos al peer se dumpea el mensaje
                if (!_clientes.estaDeAlta(ip))
                    break;
                
                // se procesa la consulta
                ProcesarPeticionBusqueda bus = new ProcesarPeticionBusqueda(_red,
                                                         _archivoClientes,
                                                         _clientes.getDatosCliente(ip),
                                                         (PeticionConsulta) msj);
                bus.start();

                // devuelve respuestaConsulta, pero dentro del hilo
                break;
            case PeticionDescarga:
                // si no conocemos al peer se dumpea el mensaje
                if (!_clientes.estaDeAlta(ip))
                    break;
                
                ProcesarPeticionDescarga des = new ProcesarPeticionDescarga(_red,
                                                         _archivoClientes,
                                                         _clientes.getDatosCliente(ip),
                                                         (PeticionDescarga) msj);
                des.start();
                break;
                
            case ListaDeArchivos:
                if (!_clientes.estaDeAlta(ip))
                    break;
                
                ProcesaListaDeFicheros p = new ProcesaListaDeFicheros(_red, 
                                                                      _archivoClientes, 
                                                                      _clientes.getDatosCliente(ip),
                                                                      (ListaArchivos) msj);
                                                                      
                p.start();
                break;
                
            case Altoo:

                // dejaremos de atender a este cliente
                _red.eliminaConexion(ip);

                // lo eliminaremos de todos los lugares dnd aparezca
                try{
                this.perdidaDeConexion(ip);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                break;
                
            default:
                // obvia el mensaje
                break;
        }
    }

    public void perdidaDeConexion(String ip) {
        if (_clientes.estaDeAlta(ip)){
            //TODO: logger
            System.out.print("perdida conexion con " + ip);
            
            DatosCliente d =_clientes.getDatosCliente(ip);
            _clientes.eliminaCliente(ip);
            _archivoClientes.eliminaPropietario(d);
        }
    }
}
