/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servidoregorilla.Datos;

import java.util.Vector;
import servidoregorilla.protocolo.ConexionCliente;

/**

 * 
 * @author pitidecaner
 * @author Salcedonia
 */
public class Cliente_Archivo {

    public String         _hash;
    public Vector<String> _nombresArchivo;
    public Vector<ConexionCliente> _propietarios;

    public Cliente_Archivo(String hash,String nomb, ConexionCliente con){
        super();
        _hash = hash;
        _nombresArchivo = new Vector<String>();
        _nombresArchivo.add(nomb);
        _propietarios = new Vector<ConexionCliente>();
    }
}
