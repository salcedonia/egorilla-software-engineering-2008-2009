/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servidoregorilla.datos;

import servidoregorilla.protocolo.ConexionCliente;
import java.util.HashSet;
import networking.PeerConn;

/**
 * Clase que proporciona los métodos y atributos necesarios para el manejo de
 * tablas que contienen la información relativa a los clientes.
 * 
 * @author pitidecaner
 * @author Salcedonia
 */
public class TablaClientes extends HashSet<ConexionCliente>{
    
    // TODO:


    public void addCliente (ConexionCliente c){
        this.add(c);
    }

    public void removeCliente (ConexionCliente c){
        this.remove(c);
    }

    public void removeCliente (PeerConn c){
        for (ConexionCliente cliente : this) {
            if (cliente.getConnexion() == c){
                this.remove(cliente);
                return;
            }
        }
    }
}

