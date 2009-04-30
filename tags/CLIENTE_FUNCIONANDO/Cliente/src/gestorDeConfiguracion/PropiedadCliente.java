
package gestorDeConfiguracion;

/**
 * ¡¡ANTES DE TOCAR LEER ESTO POR FAVOR!!
 * Defino en esta clase constantes para nombrar a las propiedades
 * del fichero de configuracion del cliente. Si se quiere cambiar el nombre de una propiedad
 * basta con cambiar su nombre en los 2 ficheros de propiedades (cliente.properties y
 * cliente_default.properties) y cambiar en esta clase su literal de propiedad asociado
 * y su texto asociado para edicion, con lo cual las pantallas tambien se actualizan automaticamente.
 * Por ultimo, si lo que se quiere cambiar es el nombre de la constante asociada a la
 * propiedad se hace un refactor en todo el codigo ¡¡listo!!.
 * @author F. Javier Sanchez Pardo
 */
public enum PropiedadCliente {
    //Lista de propiedades
    NUM_DESCARGAS_SIM ("Num_descargas_sim", "Nº de Descargas Simultaneas"),
    LIM_VELOCIDAD_SUBIDA ("Lim_subida", "Limite de Velocidad de subida (KB/s)"),
    LIM_VELOCIDAD_BAJADA ("Lim_bajada", "Limite de Velocidad de bajada (KB/s)"),
    PUERTO ("Puerto", "Puerto"),
    DIR_LLEGADA ("Dir_Temporales", "Directorio de llegada"),
    DIR_COMPARTIDOS ("Dir_Completos", "Directorio de compartidos"),
    IP_SERVIDOR ("IpServidor", "IP Servidor"),
    PUERTO_SERVIDOR ("PuertoServidor", "Puerto Servidor"),
    NOMBRE_SERVIDOR ("NombreServidor", "Nombre Servidor"),
    DESCRIP_SERVIDOR ("Descripcion", "Descripcion Servidor"),
    NOMBRE_USUARIO ("NmbUsuario", "Nombre usuario");
    
    //Literal asociado a la propiedad en el fichero properties.
    private final String _literal;
    //Label que se muestra en pantalla cuando se va a editar esta propiedad.
    private final String _literalEdicion;
    
    PropiedadCliente (String literal, String literalEdicion){
        this._literal = literal;
        this._literalEdicion = literalEdicion;
    }
    
    /**
     * Método que devuelve el literal asociado a cada propiedad.
     * @return literal de la propiedad
     */
    public String obtenerLiteral() {
        return this._literal;
    }

    /**
     * Método que devuelve el literal para edicion asociado a cada propiedad.
     * @return literal de la propiedad
     */
    public String obtenerLiteralEdicion() {
        return this._literalEdicion;
    }
}
