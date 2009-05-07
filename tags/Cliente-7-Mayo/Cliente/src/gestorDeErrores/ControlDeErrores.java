package gestorDeErrores;

import org.apache.log4j.Logger;

/**
 * Clase encargada de proporcionar los metodos necesarios para la gestion de 
 * los posibles errores producidos en el funcionamiento de la aplicacion.
 * 
 * @author Qiang
 */
public class ControlDeErrores {

    /**
     * Instancia unica de la clase ControlDeErrores.
     */
    private static ControlDeErrores _instancia;
    /**
     * Logger para la generacion de un fichero de _logger con el informe de todos
     * los errores producidos en el funcionamiento de la aplicacion.
     */
    private static final Logger _logger = Logger.getLogger(ControlDeErrores.class);
    /**
     * Mensaje de errores.
     */
    private final String _mensaje = "Error: ";

    /**
     * Constructor por defecto de la clase ControlDeErrores.
     */
    private ControlDeErrores() {
        
    }

    /**
     * Devuelve la instancia unica de la clase ControlDeErrores.
     * 
     * @return La instancia unica de la clase ControlDeErrores.
     */
    public static ControlDeErrores getInstancia() {
        if (_instancia == null) {
            _instancia = new ControlDeErrores();
        }
        return _instancia;
    }

    /**
     * Registra el error asociado al codigo <b>codigo</b>.
     * 
     * @param codigo Codigo asociado al error a registrar.
     */
    public void registrarError(int codigo) {
        _logger.info(_mensaje + ErrorEGorilla.dameMensaje(codigo));
    }

    public void registrarError(int code, String mensaje) {
        _logger.info(_mensaje + ErrorEGorilla.dameMensaje(code) + mensaje);
    }

    public void registrarError(int code, String mensaje, Throwable excepcion) {
        _logger.info(_mensaje + ErrorEGorilla.dameMensaje(code) + mensaje, excepcion);
    }

    public void registrarError(int code, String mensaje, Class path) {
        _logger.info(_mensaje + ErrorEGorilla.dameMensaje(code) + mensaje);
    }
}
