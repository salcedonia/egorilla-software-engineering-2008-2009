package gui.grafica.configuracion;

import gestorDeConfiguracion.ControlConfiguracionCliente;
import gestorDeConfiguracion.ObservadorControlConfiguracionCliente;
import gestorDeConfiguracion.PropiedadCliente;
import java.util.Properties;
import javax.swing.*;
import java.awt.*;

/**
 * Panel que se encarga de la configuración de la aplicación.
 * El panel implementa la interfaz "observador sobre el objeto 
 * ControlConfiguracionCliente", ya que dicho objeto es el que gestiona la configuración
 * del cliente (es la parte del Modelo en el patrón MVC). El Panel es la parte de la
 * vista. 
 *
 * @author S@L-c
 * @author F. Javier Sánchez Pardo
 * 
 */
public class GUIPanelConfiguracion extends JPanel implements ObservadorControlConfiguracionCliente{

    // CONSTANTES
    private static final long serialVersionUID = 1L;

    // ATRIBUTOS

    /**
     * En la Vista (PATRÓN MVC) se guarda una referencia al Modelo que en este caso es 
     * un objeto ControlConfiguracionCliente y una referencia al Controlador que en
     * este caso es ControladorPanelConfiguracion.
     */
    private ControlConfiguracionCliente _objetoModelo;
    private ControladorPanelConfiguracion _objetoControlador;
    
    // COMPONENTES GRÁFICOS
    private JButton _btnAceptar;
    private JButton _btnRestaurar;
    private JButton _btnDeshacer;
    private JLabel _lblNumDescargasSim;
    private JLabel _lblLimVelocidadSubida;
    private JLabel _lblLimVelocidadBajada;
    private JLabel _lblPuerto;
    private JLabel _lblDirLlegada;
    private JLabel _lblDirCompartidos;
    private JLabel _lblIPServidor;
    private JLabel _lblPuertoServidor;
    private JLabel _lblNombreServidor;
    private JLabel _lblDescripServidor;
    private JLabel _lblNombreUsuario;
    private JPanel _panelConexion;
    private JPanel _panelDirectorios;
    private JPanel _panelServidor;
    private JPanel _panelUsuario;
    private JPanel _panelBotones;
    private JTextField _txtNumDescargasSim;
    private JTextField _txtLimVelocidadSubida;
    private JTextField _txtLimVelocidadBajada;
    private JTextField _txtPuerto;
    private JTextField _txtDirLlegada;
    private JTextField _txtDirCompartidos;
    private JTextField _txtIPServidor;
    private JTextField _txtPuertoServidor;
    private JTextField _txtNombreServidor;
    private JTextField _txtDescripServidor;
    private JTextField _txtNombreUsuario;
        
    /**
     * Constructor de la clase PanelConfiguración.
     * @param oControlConfiguracionCliente Objeto ControlConfiguracionCliente.
     *        Mediante este parametro la Vista (este JPanel) tiene una referencia al Modelo
     *        (el objeto ControlConfiguracionCliente) y le permite pedir información 
     *        al Modelo para actualizarse convenientemente.
     * 
     */
    public GUIPanelConfiguracion(ControlConfiguracionCliente oControlConfiguracionCliente) {
        _objetoModelo = oControlConfiguracionCliente;
        _objetoControlador = new ControladorPanelConfiguracion(_objetoModelo, this);
        createGUI();
        inicializarCampos (_objetoModelo.obtenerConfiguracion());        
    }

    /**
     * Inicia los componentes del panel de Configuración.
     */
    private void createGUI() {
    	GridBagConstraints gridBagConstraints;

        _panelConexion = new JPanel();
        _lblNumDescargasSim = new JLabel();
        _txtNumDescargasSim = new JTextField(5);
        _lblLimVelocidadSubida = new JLabel();
        _txtLimVelocidadSubida = new JTextField(5);
        _lblLimVelocidadBajada = new JLabel();
        _txtLimVelocidadBajada = new JTextField(5);
        _lblPuerto = new JLabel();
        _txtPuerto = new JTextField(4);
        
        _panelDirectorios = new JPanel();
        _lblDirLlegada = new JLabel();
        _txtDirLlegada = new JTextField(40);
        _lblDirCompartidos = new JLabel();
        _txtDirCompartidos = new JTextField(40);
        
        _panelServidor = new JPanel();
        _lblIPServidor = new JLabel();
        _lblPuertoServidor = new JLabel();
        _lblNombreServidor = new JLabel();
        _lblDescripServidor = new JLabel();        
        _txtIPServidor = new JTextField(15);
        _txtPuertoServidor = new JTextField(4);
        _txtNombreServidor = new JTextField(25);
        _txtDescripServidor = new JTextField(40);
        
        _panelUsuario = new JPanel();
        _lblNombreUsuario = new JLabel();
        _txtNombreUsuario = new JTextField(25);
    
        _panelBotones = new JPanel();
        _btnAceptar = new JButton();
        _btnRestaurar = new JButton();
        _btnDeshacer =  new JButton();

        setBorder(BorderFactory.createTitledBorder("Configuración")); 
        setName("PanelConfiguracion"); 
        setLayout(new GridBagLayout());

        //----------------
        // PANEL CONEXION
        //----------------
        _panelConexion.setName("panelConexion"); 
        _panelConexion.setLayout(new GridBagLayout());

        //
        //LABEL NUMERO DE DESCARGAS SIMULTANEAS
        _lblNumDescargasSim.setText(PropiedadCliente.NUM_DESCARGAS_SIM.obtenerLiteralEdicion()); 
        _lblNumDescargasSim.setName("lblNumDescargasSim"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = GridBagConstraints.RELATIVE;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 100, 0, 0);
//        _panelConexion.add(_lblNumDescargasSim, gridBagConstraints);
        this.add(_lblNumDescargasSim, gridBagConstraints);
        
        
        //TEXTBOX NUMERO DE DESCARGAS SIMULTANEAS
        _txtNumDescargasSim.setText(""); 
        _txtNumDescargasSim.setMinimumSize(new Dimension(100, 20));
        _txtNumDescargasSim.setMaximumSize(new Dimension(100, 20));
        _txtNumDescargasSim.setPreferredSize(new Dimension(100, 20));        
        _txtNumDescargasSim.setName("txtNumDescargasSim"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 100, 0, 0);
//        _panelConexion.add(_txtNumDescargasSim, gridBagConstraints);
        this.add(_txtNumDescargasSim, gridBagConstraints);

        //
        //LABEL LIMITE DE VELOCIDAD DE SUBIDA
        _lblLimVelocidadSubida.setText(PropiedadCliente.LIM_VELOCIDAD_SUBIDA.obtenerLiteralEdicion()); 
        _lblLimVelocidadSubida.setName("lblLimVelocidadSubida");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = GridBagConstraints.RELATIVE;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 100, 0, 0);
//        _panelConexion.add(_lblLimVelocidadSubida, gridBagConstraints);
        this.add(_lblLimVelocidadSubida, gridBagConstraints);

        //TEXTBOX LIMITE DE VELOCIDAD DE SUBIDA
        _txtLimVelocidadSubida.setText(""); 
        _txtLimVelocidadSubida.setMinimumSize(new Dimension(100, 20));
        _txtLimVelocidadSubida.setMaximumSize(new Dimension(100, 20));
        _txtLimVelocidadSubida.setPreferredSize(new Dimension(100, 20));
        _txtLimVelocidadSubida.setName("txtLimVelocidadSubida"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 100, 0, 0);
//        _panelConexion.add(_txtLimVelocidadSubida, gridBagConstraints);
        this.add(_txtLimVelocidadSubida, gridBagConstraints);

        //
        //LABEL LIMITE DE VELOCIDAD DE BAJADA
        _lblLimVelocidadBajada.setText(PropiedadCliente.LIM_VELOCIDAD_BAJADA.obtenerLiteralEdicion()); 
        _lblLimVelocidadBajada.setName("lblLimVelocidadBajada");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = GridBagConstraints.RELATIVE;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 100, 0, 0);
//        _panelConexion.add(_lblLimVelocidadBajada, gridBagConstraints);
        this.add(_lblLimVelocidadBajada, gridBagConstraints);

        //TEXTBOX LIMITE DE VELOCIDAD DE BAJADA
        _txtLimVelocidadBajada.setText(""); 
        _txtLimVelocidadBajada.setMinimumSize(new Dimension(100, 20));
        _txtLimVelocidadBajada.setMaximumSize(new Dimension(100, 20));
        _txtLimVelocidadBajada.setPreferredSize(new Dimension(100, 20));
        _txtLimVelocidadBajada.setName("txtLimVelocidadBajada"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 100, 0, 0);
//        _panelConexion.add(_txtLimVelocidadBajada, gridBagConstraints);
        this.add(_txtLimVelocidadBajada, gridBagConstraints);

        //
        //LABEL PUERTO
        _lblPuerto.setText(PropiedadCliente.PUERTO.obtenerLiteralEdicion()); 
        _lblPuerto.setName("lblPuerto"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = GridBagConstraints.RELATIVE;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 100, 0, 0);
//        _panelConexion.add(_lblPuerto, gridBagConstraints);
        this.add(_lblPuerto, gridBagConstraints);

        //TEXTBOX PUERTO
        _txtPuerto.setText(""); 
        _txtPuerto.setMinimumSize(new Dimension(100, 20));
        _txtPuerto.setMaximumSize(new Dimension(100, 20));
        _txtPuerto.setPreferredSize(new Dimension(100, 20));
        _txtPuerto.setName("txtPuerto"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 100, 0, 0);
//        _panelConexion.add(_txtPuerto, gridBagConstraints);
        this.add(_txtPuerto, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
//        gridBagConstraints.ipadx = 80;
//        gridBagConstraints.ipady = 40;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 200, 0, 0);
//        add(_panelConexion, gridBagConstraints);


        //----------------
        // PANEL SERVIDOR
        //----------------
        _panelServidor.setName("panelServidor"); 
        _panelServidor.setLayout(new GridBagLayout());

        //
        //LABEL IP SERVIDOR
        _lblIPServidor.setText(PropiedadCliente.IP_SERVIDOR.obtenerLiteralEdicion()); 
        _lblIPServidor.setName("lblIPServidor"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = GridBagConstraints.RELATIVE;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 100, 0, 0);
//        _panelServidor.add(_lblIPServidor, gridBagConstraints);
        this.add(_lblIPServidor, gridBagConstraints);

        //TEXTBOX IP SERVIDOR
        _txtIPServidor.setText(""); 
        _txtIPServidor.setMinimumSize(new Dimension(100, 20));
        _txtIPServidor.setMaximumSize(new Dimension(100, 20));
        _txtIPServidor.setPreferredSize(new Dimension(100, 20));        
        _txtIPServidor.setName("txtIPServidor"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 100, 0, 0);
//        _panelServidor.add(_txtIPServidor, gridBagConstraints);
        this.add(_txtIPServidor, gridBagConstraints);
        
        //
        //LABEL PUERTO SERVIDOR
        _lblPuertoServidor.setText(PropiedadCliente.PUERTO_SERVIDOR.obtenerLiteralEdicion()); 
        _lblPuertoServidor.setName("lblPuertoServidor"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = GridBagConstraints.RELATIVE;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 100, 0, 0);
//        _panelServidor.add(_lblPuertoServidor, gridBagConstraints);
        this.add(_lblPuertoServidor, gridBagConstraints);

        //TEXTBOX PUERTO SERVIDOR
        _txtPuertoServidor.setText(""); 
        _txtPuertoServidor.setMinimumSize(new Dimension(100, 20));
        _txtPuertoServidor.setMaximumSize(new Dimension(100, 20));
        _txtPuertoServidor.setPreferredSize(new Dimension(100, 20));        
        _txtPuertoServidor.setName("txtPuertoServidor"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 100, 0, 0);
//        _panelServidor.add(_txtPuertoServidor, gridBagConstraints);
        this.add(_txtPuertoServidor, gridBagConstraints);
        
        //
        //LABEL NOMBRE SERVIDOR
        _lblNombreServidor.setText(PropiedadCliente.NOMBRE_SERVIDOR.obtenerLiteralEdicion()); 
        _lblNombreServidor.setName("lblNombreServidor"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = GridBagConstraints.RELATIVE;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 100, 0, 0);
//        _panelServidor.add(_lblNombreServidor, gridBagConstraints);
        this.add(_lblNombreServidor, gridBagConstraints);

        //TEXTBOX NOMBRE SERVIDOR
        _txtNombreServidor.setText(""); 
        _txtNombreServidor.setMinimumSize(new Dimension(100, 20));
        _txtNombreServidor.setMaximumSize(new Dimension(100, 20));
        _txtNombreServidor.setPreferredSize(new Dimension(100, 20));        
        _txtNombreServidor.setName("txtNombreServidor"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 100, 0, 0);
//        _panelServidor.add(_txtNombreServidor, gridBagConstraints);
        this.add(_txtNombreServidor, gridBagConstraints);
                
        //
        //LABEL DESCRIPCION SERVIDOR
        _lblDescripServidor.setText(PropiedadCliente.DESCRIP_SERVIDOR.obtenerLiteralEdicion()); 
        _lblDescripServidor.setName("lblDescripServidor"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = GridBagConstraints.RELATIVE;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 100, 0, 0);
//        _panelServidor.add(_lblDescripServidor, gridBagConstraints);
        this.add(_lblDescripServidor, gridBagConstraints);

        //TEXTBOX DESCRIPCION SERVIDOR
        _txtDescripServidor.setText(""); 
        _txtDescripServidor.setMinimumSize(new Dimension(100, 20));
        _txtDescripServidor.setMaximumSize(new Dimension(100, 20));
        _txtDescripServidor.setPreferredSize(new Dimension(100, 20));        
        _txtDescripServidor.setName("txtDescripServidor"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 100, 0, 0);
//        _panelServidor.add(_txtDescripServidor, gridBagConstraints);
        this.add(_txtDescripServidor, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = GridBagConstraints.REMAINDER;
//        gridBagConstraints.ipadx = 0;
//        gridBagConstraints.ipady = 0;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 200, 0, 0);
//        add(_panelServidor, gridBagConstraints);
        
        
        //----------------
        // PANEL DIRECTORIOS
        //----------------
        _panelDirectorios.setName("panelDirectorios"); 
        _panelDirectorios.setLayout(new GridBagLayout());
        
        //
        //LABEL DIRECTORIO DE LLEGADA
        _lblDirLlegada.setText(PropiedadCliente.DIR_LLEGADA.obtenerLiteralEdicion()); 
        _lblDirLlegada.setName("lblDirLlegada"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = GridBagConstraints.RELATIVE;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 100, 0, 0);
//        _panelDirectorios.add(_lblDirLlegada, gridBagConstraints);
        this.add(_lblDirLlegada, gridBagConstraints);

        //TEXTBOX NUMERO DE DESCARGAS SIMULTANEAS
        _txtDirLlegada.setText(""); 
        _txtDirLlegada.setMinimumSize(new Dimension(100, 20));
        _txtDirLlegada.setMaximumSize(new Dimension(100, 20));
        _txtDirLlegada.setPreferredSize(new Dimension(100, 20));        
        _txtDirLlegada.setName("txtDirLlegada"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 100, 0, 0);
//        _panelDirectorios.add(_txtDirLlegada, gridBagConstraints);
        this.add(_txtDirLlegada, gridBagConstraints);

        //
        //LABEL DIRECTORIO DE COMPARTIDOS
        _lblDirCompartidos.setText(PropiedadCliente.DIR_COMPARTIDOS.obtenerLiteralEdicion()); 
        _lblDirCompartidos.setName("lblDirCompartidos"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = GridBagConstraints.RELATIVE;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 100, 0, 0);
//        _panelDirectorios.add(_lblDirCompartidos, gridBagConstraints);
        this.add(_lblDirCompartidos, gridBagConstraints);

        //TEXTBOX DIRECTORIO DE COMPARTIDOS
        _txtDirCompartidos.setText(""); 
        _txtDirCompartidos.setMinimumSize(new Dimension(100, 20));
        _txtDirCompartidos.setMaximumSize(new Dimension(100, 20));
        _txtDirCompartidos.setPreferredSize(new Dimension(100, 20));        
        _txtDirCompartidos.setName("txtDirCompartidos"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 100, 0, 0);
//        _panelDirectorios.add(_txtDirCompartidos, gridBagConstraints);
        this.add(_txtDirCompartidos, gridBagConstraints);
        
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = GridBagConstraints.RELATIVE;
//        gridBagConstraints.ipadx = 80;
//        gridBagConstraints.ipady = 40;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 200, 0, 0);
//        add(_panelDirectorios, gridBagConstraints);

        
        //----------------
        // PANEL USUARIO
        //----------------
        _panelUsuario.setName("panelUsuario"); 
        _panelUsuario.setLayout(new GridBagLayout());
        
        //
        //LABEL NOMBRE USUARIO
        _lblNombreUsuario.setText(PropiedadCliente.NOMBRE_USUARIO.obtenerLiteralEdicion()); 
        _lblNombreUsuario.setName("lblNombreUsuario"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = GridBagConstraints.RELATIVE;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 100, 0, 0);
//        _panelUsuario.add(_lblNombreUsuario, gridBagConstraints);
        this.add(_lblNombreUsuario, gridBagConstraints);

        //TEXTBOX NOMBRE USUARIO
        _txtNombreUsuario.setText(""); 
        _txtNombreUsuario.setMinimumSize(new Dimension(100, 20));
        _txtNombreUsuario.setMaximumSize(new Dimension(100, 20));
        _txtNombreUsuario.setPreferredSize(new Dimension(100, 20));        
        _txtNombreUsuario.setName("txtNombreUsuario"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 100, 0, 0);
//        _panelUsuario.add(_txtNombreUsuario, gridBagConstraints);
        this.add(_txtNombreUsuario, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = GridBagConstraints.REMAINDER;
//        gridBagConstraints.ipadx = 80;
//        gridBagConstraints.ipady = 40;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 200, 0, 0);
//        add(_panelUsuario, gridBagConstraints);

        
        //----------------
        // PANEL BOTONES
        //----------------
        _panelBotones.setName("panelBotones"); 
        _panelBotones.setLayout(new GridBagLayout());

        //
        //BOTON ACEPTAR
        _btnAceptar.setText("Aceptar"); 
        _btnAceptar.setMaximumSize(new Dimension(100, 23));
        _btnAceptar.setMinimumSize(new Dimension(100, 23));
        _btnAceptar.setName("btnAceptar"); 
        _btnAceptar.setPreferredSize(new Dimension(100, 23));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 100, 0, 0);
        _panelBotones.add(_btnAceptar, gridBagConstraints);
        //Pongo al objeto controlador a "escuchar" al boton.
        _btnAceptar.addActionListener(_objetoControlador);

        //
        //BOTON DESHACER
        _btnDeshacer.setText("Deshacer cambios"); 
        _btnDeshacer.setName("btnDeshacer"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 100, 0, 0);
        _panelBotones.add(_btnDeshacer, gridBagConstraints);
        //Pongo al objeto controlador a "escuchar" al boton.
        _btnDeshacer.addActionListener(_objetoControlador);

        //
        //BOTON RESTAURAR
        _btnRestaurar.setText("Config. defecto");
        _btnRestaurar.setName("btnRestaurar"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 100, 0, 0);
        _panelBotones.add(_btnRestaurar, gridBagConstraints);
        //Pongo al objeto controlador a "escuchar" al boton.
        _btnRestaurar.addActionListener(_objetoControlador);

        gridBagConstraints = new GridBagConstraints();
//        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = GridBagConstraints.RELATIVE;
//        gridBagConstraints.gridwidth = 2;
//        gridBagConstraints.ipadx = 23;
//        gridBagConstraints.ipady = 107;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 200, 0, 0);
        add(_panelBotones, gridBagConstraints);

        getAccessibleContext().setAccessibleName("Configuracion");    
    }

    /**
     * Este metodo inicializa los campos del panel con los valores
     * contenidos en el objeto properties.
     */
    public void inicializarCampos(Properties properties){
        _txtNumDescargasSim.setText(properties.getProperty(PropiedadCliente.NUM_DESCARGAS_SIM.obtenerLiteral()));
        _txtLimVelocidadSubida.setText(properties.getProperty(PropiedadCliente.LIM_VELOCIDAD_SUBIDA.obtenerLiteral()));
        _txtLimVelocidadBajada.setText(properties.getProperty(PropiedadCliente.LIM_VELOCIDAD_BAJADA.obtenerLiteral()));
        _txtPuerto.setText(properties.getProperty(PropiedadCliente.PUERTO.obtenerLiteral()));
        _txtDirLlegada.setText(properties.getProperty(PropiedadCliente.DIR_LLEGADA.obtenerLiteral()));
        _txtDirCompartidos.setText(properties.getProperty(PropiedadCliente.DIR_COMPARTIDOS.obtenerLiteral()));
        _txtIPServidor.setText(properties.getProperty(PropiedadCliente.IP_SERVIDOR.obtenerLiteral()));
        _txtPuertoServidor.setText(properties.getProperty(PropiedadCliente.PUERTO_SERVIDOR.obtenerLiteral()));
        _txtNombreServidor.setText(properties.getProperty(PropiedadCliente.NOMBRE_SERVIDOR.obtenerLiteral()));
        _txtDescripServidor.setText(properties.getProperty(PropiedadCliente.DESCRIP_SERVIDOR.obtenerLiteral()));
        _txtNombreUsuario.setText(properties.getProperty(PropiedadCliente.NOMBRE_USUARIO.obtenerLiteral()));
    }

    /**
     * Este metodo (implementacion de la interfaz ObservadorControlConfiguracionCliente) permite 
     * reflejar en la Vista (este panel) los cambios que se han producido en el Modelo (objeto 
     * ControlConfiguracionCliente). En el parametro propiedades están las propiedades
     * que han cambiado de valor (no tienen porqué haber cambiado todas). EN ESTE CASO NO
     * HACE FALTA tratar de manera diferenciada cada posible cambio: se muestra en el panel
     * el contenido del objeto entero.
     * @param obj Objeto ControlConfiguracionCliente que almacena la configuracion
     * @param propiedades Conjunto de propiedades que han cambiado en el objeto anterior.
     */
    public void cambioEnPropiedades(ControlConfiguracionCliente obj, Properties propiedades) {
        inicializarCampos (_objetoModelo.obtenerConfiguracion()); 
    }
    
    //
    //Metodos auxiliares que devuelven los valores de los controles de entrada
    //
    public String obtenerNumDescargasSim(){
        return _txtNumDescargasSim.getText();
    }
    
    public String obtenerLimVelocidadSubida(){
        return _txtLimVelocidadSubida.getText();
    }
    
    public String obtenerLimVelocidadBajada(){
        return _txtLimVelocidadBajada.getText();
    }

    public String obtenerPuerto(){
        return _txtPuerto.getText();
    }

    public String obtenerDirLlegada(){
        return _txtDirLlegada.getText();
    }

    public String obtenerDirCompartidos(){
        return _txtDirCompartidos.getText();
    }

    public String obtenerIPServidor(){
        return _txtIPServidor.getText();
    }

    public String obtenerPuertoServidor(){
        return _txtPuertoServidor.getText();
    }

    public String obtenerNombreServidor(){
        return _txtNombreServidor.getText();
    }

    public String obtenerDescripServidor(){
        return _txtDescripServidor.getText();
    }

    public String obtenerNombreUsuario(){
        return _txtNombreUsuario.getText();
    }

    //
    //Metodos auxiliares que devuelven referencias a los botones
    //
    public Object obtenerBotonAceptar(){
        return _btnAceptar;
    }
    
    public Object obtenerBotonDeshacer(){
        return _btnDeshacer;
    }
    
    public Object obtenerBotonRestaurar(){
        return _btnRestaurar;
    }
}
