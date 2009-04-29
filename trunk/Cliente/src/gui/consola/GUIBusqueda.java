/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gui.consola;

import datos.Archivo;

/**
 *
 * Almacena los resultados de la ultima busqueda para que Ivan no llore
 *
 * @author pitidecaner
 */
class GUIBusqueda {
    private  Archivo[] _ultimaBusqueda;

    public Archivo dameArchivoPorHash (String hash){
        if (_ultimaBusqueda == null)
            return null;
        else
            for (Archivo arch: _ultimaBusqueda){
                if (arch._hash.equals(hash))
                    return arch;
            }
        return null;
    }

    public void setBusqueda( Archivo[]  busqueda){
        _ultimaBusqueda = busqueda;
    }
}
