/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servidoregorilla.datos;

import java.util.Vector;
import servidoregorilla.protocolo.ConexionCliente;

/*****************************************************************************/
/**
 * Clase que controla los propietarios de un determinado archivo y los distintos
 * nombres conexion el que referencian al mismo dentro de la red.
 * 
 * @author pitidecaner
 * @author Salcedonia
 */
public class Cliente_Archivo {

    // ATRIBUTOS
    public String         _hash; // Hash del archivo
    public Vector<String> _nombresArchivo; // Lista de nombres asociadas al hash
    public Vector<ConexionCliente> _propietarios; // Lista de propietarios del archivo

/*****************************************************************************/
    /**
     * Constructor de la clase Cliente_Archivo.
     * 
     * @param hash hash del archivo.
     * @param nombre Nombre del archivo.
     * @param conexion Cliente que tiene el archivo.
     */
    public Cliente_Archivo(String hash, String nombre, ConexionCliente conexion){
        super();
        _hash = hash;
        _nombresArchivo = new Vector<String>();
        _nombresArchivo.add(nombre);
        _propietarios = new Vector<ConexionCliente>();
        _propietarios.add(conexion);
    }
}
