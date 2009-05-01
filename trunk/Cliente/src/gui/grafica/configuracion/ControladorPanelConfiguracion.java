package gui.grafica.configuracion;

import gestorDeConfiguracion.ControlConfiguracionCliente;
import gestorDeConfiguracion.ControlConfiguracionClienteException;
import gestorDeConfiguracion.PropiedadCliente;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase controladora (siguiendo el patron MVC) que se encarga de recibir los eventos
 * de la Vista (GUIPanelConfiguracion) y tratarlos adecuadamente, realizando operaciones 
 * sobre el Modelo (objeto ControlConfiguracionCliente) si es necesario.
 * 
 * @author F. Javier Sanchez Pardo
 */

public class ControladorPanelConfiguracion implements ActionListener{
    
    //ATRIBUTOS
    
    // Se guarda una referencia al Modelo (PATRÓN MVC) que en este caso es un objeto
    // ControlConfiguracionCliente (para poder realizar operaciones sobre el Modelo).
    private ControlConfiguracionCliente _objetoModelo;
    
    // Se guarda una referencia a la Vista (PATRÓN MVC) que en este caso es un objeto
    // GUIPanelConfiguracion (porque puede ser necesario modificarModelo/manipular los elementos
    // de la vista desde el controlador). Así todo queda atado.
    private GUIPanelConfiguracion _objetoVista;
    
    /**
     * Constructor que recibe una instancia del Modelo (objeto ControlConfiguracionCliente)
     * y otra de la Vista (objeto GUIPanelConfiguracion)
     * @param oCtrlConfigCli referencia al Modelo (patron MVC)
     * @param oGUIPanelConfig referencia a la Vista (patron MVC) 
     */
    public ControladorPanelConfiguracion(ControlConfiguracionCliente oCtrlConfigCli,
                                        GUIPanelConfiguracion oGUIPanelConfig){
        _objetoModelo = oCtrlConfigCli;
        _objetoVista = oGUIPanelConfig;
    }

    public void actionPerformed(ActionEvent evt) {
        Object source = evt.getSource();
        //Boton de Aceptar
        if (source == _objetoVista.obtenerBotonAceptar()){
            try {
                modificarModelo();
            } catch (ControlConfiguracionClienteException ex) {
                Logger.getLogger(ControladorPanelConfiguracion.class.getName()).log(Level.SEVERE, null, ex);
            }
        //Boton de Deshacer cambios: se carga el panel de nuevo con las propiedades del Modelo
        } else if (source == _objetoVista.obtenerBotonDeshacer()){
            _objetoVista.inicializarCampos(_objetoModelo.obtenerConfiguracion());
        //Boton de Restaurar valores por defecto: se carga el panel con las propiedades POR DEFECTO del Modelo
        }else if (source == _objetoVista.obtenerBotonRestaurar()){
            _objetoVista.inicializarCampos(_objetoModelo.obtenerConfiguracionPorDefecto());
        }
    }
    
    /**
     * Si el contenido de los controles de la pestaña (la Vista) es diferente de los valores 
     * de las propiedades en el objeto ControlConfiguracionCliente (Modelo) entonces
     * actualizo los valores de las propiedades en el Modelo.
     * 
     */
    private void modificarModelo () throws ControlConfiguracionClienteException{
        Properties propiedades = new Properties ();
        String sCadenaAux = null;
        
        sCadenaAux = _objetoVista.obtenerNumDescargasSim();
        if ( sCadenaAux.compareTo(_objetoModelo.obtenerPropiedad(PropiedadCliente.NUM_DESCARGAS_SIM.obtenerLiteral())) != 0)
            propiedades.setProperty(PropiedadCliente.NUM_DESCARGAS_SIM.obtenerLiteral(), sCadenaAux);
        
        sCadenaAux = _objetoVista.obtenerLimVelocidadSubida();
        if ( sCadenaAux.compareTo(_objetoModelo.obtenerPropiedad(PropiedadCliente.LIM_VELOCIDAD_SUBIDA.obtenerLiteral())) != 0)
            propiedades.setProperty(PropiedadCliente.LIM_VELOCIDAD_SUBIDA.obtenerLiteral(), sCadenaAux);
        
        sCadenaAux = _objetoVista.obtenerLimVelocidadBajada();
        if ( sCadenaAux.compareTo(_objetoModelo.obtenerPropiedad(PropiedadCliente.LIM_VELOCIDAD_BAJADA.obtenerLiteral())) != 0)
            propiedades.setProperty(PropiedadCliente.LIM_VELOCIDAD_BAJADA.obtenerLiteral(), sCadenaAux);
                
        sCadenaAux = _objetoVista.obtenerPuerto();
        if ( sCadenaAux.compareTo(_objetoModelo.obtenerPropiedad(PropiedadCliente.PUERTO.obtenerLiteral())) != 0)
            propiedades.setProperty(PropiedadCliente.PUERTO.obtenerLiteral(), sCadenaAux);

        sCadenaAux = _objetoVista.obtenerDirLlegada();
        if ( sCadenaAux.compareTo(_objetoModelo.obtenerPropiedad(PropiedadCliente.DIR_LLEGADA.obtenerLiteral())) != 0)
            propiedades.setProperty(PropiedadCliente.DIR_LLEGADA.obtenerLiteral(), sCadenaAux);
        
        sCadenaAux = _objetoVista.obtenerDirCompartidos();
        if ( sCadenaAux.compareTo(_objetoModelo.obtenerPropiedad(PropiedadCliente.DIR_COMPARTIDOS.obtenerLiteral())) != 0)
            propiedades.setProperty(PropiedadCliente.DIR_COMPARTIDOS.obtenerLiteral(), sCadenaAux);
        
        sCadenaAux = _objetoVista.obtenerIPServidor();
        if ( sCadenaAux.compareTo(_objetoModelo.obtenerPropiedad(PropiedadCliente.IP_SERVIDOR.obtenerLiteral())) != 0)
            propiedades.setProperty(PropiedadCliente.IP_SERVIDOR.obtenerLiteral(), sCadenaAux);
        
        sCadenaAux = _objetoVista.obtenerPuertoServidor();
        if ( sCadenaAux.compareTo(_objetoModelo.obtenerPropiedad(PropiedadCliente.PUERTO_SERVIDOR.obtenerLiteral())) != 0)
            propiedades.setProperty(PropiedadCliente.PUERTO_SERVIDOR.obtenerLiteral(), sCadenaAux);

        sCadenaAux = _objetoVista.obtenerNombreServidor();
        if ( sCadenaAux.compareTo(_objetoModelo.obtenerPropiedad(PropiedadCliente.NOMBRE_SERVIDOR.obtenerLiteral())) != 0)
            propiedades.setProperty(PropiedadCliente.NOMBRE_SERVIDOR.obtenerLiteral(), sCadenaAux);

        sCadenaAux = _objetoVista.obtenerDescripServidor();
        if ( sCadenaAux.compareTo(_objetoModelo.obtenerPropiedad(PropiedadCliente.DESCRIP_SERVIDOR.obtenerLiteral())) != 0)
            propiedades.setProperty(PropiedadCliente.DESCRIP_SERVIDOR.obtenerLiteral(), sCadenaAux);

        sCadenaAux = _objetoVista.obtenerNombreUsuario();
        if ( sCadenaAux.compareTo(_objetoModelo.obtenerPropiedad(PropiedadCliente.NOMBRE_USUARIO.obtenerLiteral())) != 0)
            propiedades.setProperty(PropiedadCliente.NOMBRE_USUARIO.obtenerLiteral(), sCadenaAux);
        
        //Si ha habido cambios grabo.
        if (propiedades.size() != 0)
            _objetoModelo.establecerConfiguracion(propiedades);                
    }

}
