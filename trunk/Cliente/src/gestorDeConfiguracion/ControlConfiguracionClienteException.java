
package gestorDeConfiguracion;


/**
 *
 * @author F. Javier Sanchez Pardo
 * Esta clase gestiona las excepciones especificas que se pueden producir dentro
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
