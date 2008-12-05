/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servidoregorilla.Datos;

import servidoregorilla.paquete.Archivo;
import servidoregorilla.protocolo.Cliente;

/**
 *  La clase cliente archivo es la relacción muchos a muchos entre los clientes
 * y los archivos. Un archivo puede tener muchos propietarios, lo mismo que un cliente
 * puede tener muchos archivos. 
 * 
 * Esta clase esta destinada a resolver las busquedas de los clientes que tienen un
 * archivo.
 * A la hora de comenzar una descarga, el cliente conecta con el servidor indicando 
 * el hash de este, y el servidor reenviará la IP y puerto de transferencia de archivo 
 * de los clientes propietarios.
 * 
 * @author pitidecaner
 */
public class Cliente_Archivo {

    /**
     * Añade un archivo a la relacción con su correspondiente propietario
     * Un usuario da de alta un archivo al conectarse al sistema, subiendo toda
     * la lista de archivos que tiene compartidos.
     * 
     * @param a Archivo
     * @param c Cliente propietario de este
     */
    public void addRellac(Archivo a, Cliente c){
        
    }
    
    /**
     * Da de baja una relacción por parte del cliente, el cliente esta desconectado
     * inhabilitado u otra circustancia. por lo que no se encuentra esta fuente.
     * Si el cliente era el único propietario del archivo. se dará de baja este.
     * 
     * @param c cliente a dar de baja
     */
    public void removeClient (Cliente c){
        
    }
}
