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
package main;

import datos.ArchivoClientes;
import datos.ListadoClientes;
import gestorDeConfiguracion.ControlConfiguracion;
import gestorDeConfiguracion.ControlConfiguracionServidorException;
import gestorDeRed.GestorDeRed;
import gestorDeRed.TCP.GestorDeRedTCPimpl;
import java.io.IOException;
import mensajes.Mensaje;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import servidor.ServidorEgorilla;

/**
 * Clase Main de la aplicación HiloServidor de servidor.
 */
public class Main {

    // ATRIBUTOS
    public static boolean _loop = true;
    
    /**
     * Método main del servidor.
     * 
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) throws IOException, ControlConfiguracionServidorException {
        
        ControlConfiguracion config= new ControlConfiguracion();
        int puerto;
        PropertyConfigurator.configure("log4j.properties");
        Logger log =  Logger.getLogger(Main.class);
        ServidorEgorilla egorilla;
               
        ArchivoClientes archivosYclientes = new ArchivoClientes();
        ListadoClientes listaClientes = new ListadoClientes();
        
        try {        
            // Creamos el servidor
           
            config.leeConfig("Configuracion.properties");
            puerto=ControlConfiguracion.getPuerto();
   
            // crea la red, y la pone a escuchar
            GestorDeRed<Mensaje> red = new GestorDeRedTCPimpl<Mensaje>(puerto); 
            
            // meet each other
            egorilla = new ServidorEgorilla(red, listaClientes, archivosYclientes);
            red.registraReceptor(egorilla);
            
            red.comienzaEscucha();  // a partir de este momento escucha
        } 
        catch (IOException ex) {
        
            log.error("Puerto ocupado");
            return;
        }
        
        System.out.println("Escuchando por puerto " + puerto);
        
    }
}
