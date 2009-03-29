
package gestorDeConfiguracion;


/**
 *
 * @author F. Javier S. Pardo
 * Esta clase gestiona las excepciones específicas que se pueden producir dentro
 * del paquete Cliente.gestorDeConfiguracion
 * 
 */


public class ControlConfiguracionClienteException extends Exception{

    public ControlConfiguracionClienteException (Exception e){
        super(e);
    }

    public ControlConfiguracionClienteException (String sMensaje){
        super(sMensaje);
    }
    
    public ControlConfiguracionClienteException (String sMensaje, Exception e){
        super (sMensaje, e);
    }
}
