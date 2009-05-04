package gui.consola;

import datos.Archivo;

/**
 * Almacena los resultados de la última búsqueda realizada al servidor.
 *
 * @author Luis Ayuso
 */
class GUIBusqueda {
    
    /**
     * Datos de la última búsqueda realizada.
     */
    private  Archivo[] _ultimaBusqueda;

    /**
     * Devuelve el archivo asociado a un hash de entre los archivos que
     * ha devuelto la última búsqueda.
     * 
     * @param hash Hash del archivo a averiguar.
     * 
     * @return El archivo asociado a un hash de entre los archivos que
     * ha devuelto la última búsqueda.
     */
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

    /**
     * Establece _ultimaBusqueda a valor <b>ultimaBusqueda</b>.
     * 
     * @param ultimaBusqueda Nuevo valor a establecer.
     */
    public void setBusqueda( Archivo[]  ultimaBusqueda){
        
        _ultimaBusqueda = ultimaBusqueda;
    }
}
