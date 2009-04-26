package gui.grafica.principal;

import control.ControladorGrafica;
import gestorDeConfiguracion.ControlConfiguracionCliente;
import gui.grafica.buscador.GUIPanelBuscador;
import gui.grafica.compartidos.GUIPanelCompartidos;
import gui.grafica.configuracion.GUIPanelConfiguracion;
import gui.grafica.estadisticas.GUIPanelEstadisticas;
import gui.grafica.servidores.GUIPanelServidores;
import gui.grafica.trafico.GUIPanelTrafico;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//************************************************************************************//
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * Clase que gestiona la ventana principal de la aplicación.
 * 
 * @author S@L-c
 */
public class GUIVentanaPrincipal extends JFrame {

    // CONSTANTES
    private static final long serialVersionUID = 1L;
    private static final String RUTA_RECURSOS = "/recursos/interfaz/principal/";
    // ATRIBUTOS
    private JButton _btnBuscar;
    private JButton _btnCompartidos;
    private JButton _btnConectar;
    private JButton _btnConfiguracion;
    private JButton _btnEstadisticas;
    private JButton _btnServidores;
    private JButton _btnTrafico;
    private JButton _btnAyuda;
    private JLabel _lblConexion;
    private JLabel _lblEstado;
    private JPanel _panelEstado;
    private JPanel _panelConexion;
    private JSeparator _separador1;
    private JSeparator _separador2;
    private JToolBar _botonera;
    private JPanel _panelPrincipal;
    // CONTROL
    private ControladorGrafica _controlador;
    // IMAGENES
    private ImageIcon _iconoVentana = new ImageIcon(getClass().getResource(RUTA_RECURSOS + "iconos/icono.png"));
    private ImageIcon _imgConectar = new ImageIcon(getClass().getResource(RUTA_RECURSOS + "botones/btnConectar.png"));
    private ImageIcon _imgDesconectar = new ImageIcon(getClass().getResource(RUTA_RECURSOS + "botones/btnDesconectar.png"));
    private ImageIcon _imgServidores = new ImageIcon(getClass().getResource(RUTA_RECURSOS + "botones/btnServidores.png"));
    private ImageIcon _imgBuscar = new ImageIcon(getClass().getResource(RUTA_RECURSOS + "botones/btnBuscar.png"));
    private ImageIcon _imgTrafico = new ImageIcon(getClass().getResource(RUTA_RECURSOS + "botones/btnTrafico.png"));
    private ImageIcon _imgCompartidos = new ImageIcon(getClass().getResource(RUTA_RECURSOS + "botones/btnCompartidos.png"));
    private ImageIcon _imgEstadisticas = new ImageIcon(getClass().getResource(RUTA_RECURSOS + "botones/btnEstadisticas.png"));
    private ImageIcon _imgConfiguracion = new ImageIcon(getClass().getResource(RUTA_RECURSOS + "botones/btnConfiguracion.png"));
    private ImageIcon _imgAyuda = new ImageIcon(getClass().getResource(RUTA_RECURSOS + "botones/btnAyuda.png"));

//	************************************************************************************//
    /**
     * Constructor de la clase VentanaPrincipal.
     */
    public GUIVentanaPrincipal(ControladorGrafica controlador) {

        
        _controlador = controlador;
        iniciarComponentes();
        setExtendedState(MAXIMIZED_BOTH);
        setVisible(true);
    }

//	************************************************************************************//
    /**
     * Inicia todos los componentes de la ventana.
     */
    private void iniciarComponentes() {

        _panelPrincipal = new JPanel();
        _panelEstado = new JPanel();
        _panelConexion = new JPanel();
        _btnConectar = new JButton();
        _btnServidores = new JButton();
        _btnBuscar = new JButton();
        _btnTrafico = new JButton();
        _btnCompartidos = new JButton();
        _btnEstadisticas = new JButton();
        _btnConfiguracion = new JButton();
        _btnAyuda = new JButton();
        _separador1 = new JSeparator(); // Separa el panel estado del principal

        _separador2 = new JSeparator(); // Separa el panel estado con el de conexión

        _botonera = new JToolBar();
        _lblEstado = new JLabel();
        _lblConexion = new JLabel();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("eGorilla - v.Eclipse");
        setBounds(new Rectangle(0, 0, 0, 0));
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        setExtendedState(MAXIMIZED_BOTH);
        setForeground(new Color(255, 255, 255));
        setIconImage(_iconoVentana.getImage());
        setLocationByPlatform(true);
        setMinimumSize(new Dimension(560, 500));
        setName("VentanaPrincipal");

        _panelPrincipal.setName("panelPrincipal");
        _panelPrincipal.setPreferredSize(new Dimension(800, 600));
        _panelPrincipal.setLayout(new CardLayout());
        _panelPrincipal.add("Servidores", new GUIPanelServidores());
        _panelPrincipal.add("Buscador", new GUIPanelBuscador());
        _panelPrincipal.add("Configuracion", new GUIPanelConfiguracion());
        _panelPrincipal.add("Descargas", new GUIPanelTrafico());
        _panelPrincipal.add("Compartidos", new GUIPanelCompartidos());
        _panelPrincipal.add("Estadisticas", new GUIPanelEstadisticas());

        getContentPane().add(_panelPrincipal, BorderLayout.CENTER);

        _panelEstado.setName("panelEstado");
        _panelEstado.setLayout(new BorderLayout());

        _separador1.setName("separador1");
        _panelEstado.add(_separador1, BorderLayout.NORTH);

        _lblEstado.setText("Bienvenido a eGorilla");
        _lblEstado.setName("estado");
        _panelEstado.add(_lblEstado, BorderLayout.CENTER);

        _panelConexion.setMinimumSize(new Dimension(40, 20));
        _panelConexion.setName("panelConexion");
        _panelConexion.setPreferredSize(new Dimension(100, 20));
        //_panelConexion.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        _separador2.setOrientation(SwingConstants.VERTICAL);
        _separador2.setName("separador2");
        //_panelConexion.add(_separador2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 20));

        _lblConexion.setText("Desconectado");
        _lblConexion.setName("lblConexion");
        //_panelConexion.add(_lblConexion, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 0, 90, -1));

        _panelEstado.add(_panelConexion, BorderLayout.EAST);
        getContentPane().add(_panelEstado, BorderLayout.PAGE_END);

        // BOTONERA
        _botonera.setBorder(BorderFactory.createTitledBorder("Menu"));
        _botonera.setForeground(new Color(235, 233, 237));
        _botonera.setRollover(true);
        _botonera.setAutoscrolls(true);
        _botonera.setDoubleBuffered(true);
        _botonera.setName("botonera");
        _botonera.setOpaque(false);

        // BOTON CONECTAR
        _btnConectar.setFont(new Font("Tahoma", Font.BOLD, 11));
        _btnConectar.setName("btnConectar");
        _btnConectar.setText("Conectar");
        _btnConectar.setIcon(_imgConectar);
        _btnConectar.setBorderPainted(false);
        _btnConectar.setAlignmentX(0.5F);
        _btnConectar.setDoubleBuffered(true);
        _btnConectar.setFocusCycleRoot(true);
        _btnConectar.setFocusPainted(false);
        _btnConectar.setFocusable(false);
        _btnConectar.setHorizontalTextPosition(SwingConstants.CENTER);
        _btnConectar.setIconTextGap(-5);
        _btnConectar.setMargin(new Insets(0, 10, 0, 10));
        _btnConectar.setPreferredSize(new Dimension(70, 70));
        _btnConectar.setVerticalAlignment(SwingConstants.BOTTOM);
        _btnConectar.setVerticalTextPosition(SwingConstants.BOTTOM);

        // Añadimos el evento de pulsación del ratón
        _btnConectar.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent evt) {
                pulsacionBotonConectar(evt);
            }
        });
        _botonera.add(_btnConectar);

        // BOTON SERVIDORES
        _btnServidores.setFont(new Font("Tahoma", Font.BOLD, 11));
        _btnServidores.setName("btnServidores");
        _btnServidores.setText("Servidores");
        _btnServidores.setIcon(_imgServidores);
        _btnServidores.setBorderPainted(false);
        _btnServidores.setAlignmentX(0.5F);
        _btnServidores.setDoubleBuffered(true);
        _btnServidores.setFocusCycleRoot(true);
        _btnServidores.setFocusPainted(true);
        _btnServidores.setFocusable(true);
        _btnServidores.setHorizontalTextPosition(SwingConstants.CENTER);
        _btnServidores.setIconTextGap(-5);
        _btnServidores.setMargin(new Insets(0, 10, 0, 10));
        _btnServidores.setPreferredSize(new Dimension(70, 70));
        _btnServidores.setVerticalAlignment(SwingConstants.BOTTOM);
        _btnServidores.setVerticalTextPosition(SwingConstants.BOTTOM);

        // Añadimos el evento de pulsación del ratón
        _btnServidores.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent evt) {
                pulsacionBotonServidores(evt);
            }
        });
        _botonera.add(_btnServidores);

        // BOTON BUSCAR
        _btnBuscar.setFont(new Font("Tahoma", Font.BOLD, 11));
        _btnBuscar.setName("btnBuscar");
        _btnBuscar.setText("Buscar");
        _btnBuscar.setIcon(_imgBuscar);
        _btnBuscar.setBorderPainted(false);
        _btnBuscar.setAlignmentX(0.5F);
        _btnBuscar.setDoubleBuffered(true);
        _btnBuscar.setFocusCycleRoot(true);
        _btnBuscar.setFocusPainted(true);
        _btnBuscar.setFocusable(true);
        _btnBuscar.setHorizontalTextPosition(SwingConstants.CENTER);
        _btnBuscar.setIconTextGap(-5);
        _btnBuscar.setMargin(new Insets(0, 10, 0, 10));
        _btnBuscar.setPreferredSize(new Dimension(70, 70));
        _btnBuscar.setVerticalAlignment(SwingConstants.BOTTOM);
        _btnBuscar.setVerticalTextPosition(SwingConstants.BOTTOM);

        // Añadimos el evento de pulsación del ratón
        _btnBuscar.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent evt) {
                pulsacionBotonBuscar(evt);
            }
        });
        _botonera.add(_btnBuscar);

        // BOTON TRAFICO
        _btnTrafico.setFont(new Font("Tahoma", Font.BOLD, 11));
        _btnTrafico.setName("btnTrafico");
        _btnTrafico.setText("Tráfico");
        _btnTrafico.setIcon(_imgTrafico);
        _btnTrafico.setBorderPainted(false);
        _btnTrafico.setAlignmentX(0.5F);
        _btnTrafico.setDoubleBuffered(true);
        _btnTrafico.setFocusCycleRoot(true);
        _btnTrafico.setFocusPainted(true);
        _btnTrafico.setFocusable(true);
        _btnTrafico.setHorizontalTextPosition(SwingConstants.CENTER);
        _btnTrafico.setIconTextGap(-7);
        _btnTrafico.setMargin(new Insets(0, 10, 0, 10));
        _btnTrafico.setPreferredSize(new Dimension(70, 70));
        _btnTrafico.setVerticalAlignment(SwingConstants.BOTTOM);
        _btnTrafico.setVerticalTextPosition(SwingConstants.BOTTOM);

        // Añadimos el evento de pulsación del ratón
        _btnTrafico.addMouseListener(new java.awt.event.MouseAdapter() {

            public void mousePressed(java.awt.event.MouseEvent evt) {
                pulsacionBotonTrafico(evt);
            }
        });
        _botonera.add(_btnTrafico);

        // BOTON COMPARTIDOS
        _btnCompartidos.setFont(new Font("Tahoma", Font.BOLD, 11));
        _btnCompartidos.setName("btnCompartidos");
        _btnCompartidos.setText("Compartidos");
        _btnCompartidos.setIcon(_imgCompartidos);
        _btnCompartidos.setBorderPainted(false); // No pinta los bordes de los botones

        _btnCompartidos.setAlignmentX(0.5F);
        _btnCompartidos.setDoubleBuffered(true);
        _btnCompartidos.setFocusCycleRoot(true);
        _btnCompartidos.setFocusPainted(true);
        _btnCompartidos.setFocusable(true);
        _btnCompartidos.setHorizontalTextPosition(SwingConstants.CENTER);
        _btnCompartidos.setIconTextGap(-5);
        _btnCompartidos.setMargin(new Insets(0, 10, 0, 10));
        _btnCompartidos.setPreferredSize(new Dimension(70, 70));
        _btnCompartidos.setVerticalAlignment(SwingConstants.BOTTOM);
        _btnCompartidos.setVerticalTextPosition(SwingConstants.BOTTOM);

        // Añadimos el evento de pulsación del ratón
        _btnCompartidos.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent evt) {
                pulsacionBotonCompartidos(evt);
            }
        });
        _botonera.add(_btnCompartidos);

        // BOTON ESTADISTICAS
        _btnEstadisticas.setFont(new Font("Tahoma", Font.BOLD, 11));
        _btnEstadisticas.setName("btnEstadisticas");
        _btnEstadisticas.setText("Estadísticas");
        _btnEstadisticas.setIcon(_imgEstadisticas);
        _btnEstadisticas.setBorderPainted(false);
        _btnEstadisticas.setAlignmentX(0.5F);
        _btnEstadisticas.setDoubleBuffered(true);
        _btnEstadisticas.setFocusCycleRoot(true);
        _btnEstadisticas.setFocusPainted(true);
        _btnEstadisticas.setFocusable(true);
        _btnEstadisticas.setHorizontalTextPosition(SwingConstants.CENTER);
        _btnEstadisticas.setIconTextGap(-5);
        _btnEstadisticas.setMargin(new Insets(0, 10, 0, 10));
        _btnEstadisticas.setPreferredSize(new Dimension(70, 70));
        _btnEstadisticas.setVerticalAlignment(SwingConstants.BOTTOM);
        _btnEstadisticas.setVerticalTextPosition(SwingConstants.BOTTOM);

        // Añadimos el evento de pulsación del ratón
        _btnEstadisticas.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent evt) {
                pulsacionBotonEstadisticas(evt);
            }
        });
        _botonera.add(_btnEstadisticas);

        // BOTON CONFIGURACION
        _btnConfiguracion.setFont(new Font("Tahoma", Font.BOLD, 11));
        _btnConfiguracion.setName("btnConfiguracion");
        _btnConfiguracion.setText("Configuración");
        _btnConfiguracion.setIcon(_imgConfiguracion);
        _btnConfiguracion.setBorderPainted(false);
        _btnConfiguracion.setAlignmentX(0.5F);
        _btnConfiguracion.setDoubleBuffered(true);
        _btnConfiguracion.setFocusCycleRoot(true);
        _btnConfiguracion.setFocusPainted(true);
        _btnConfiguracion.setFocusable(true);
        _btnConfiguracion.setHorizontalTextPosition(SwingConstants.CENTER);
        _btnConfiguracion.setIconTextGap(-5);
        _btnConfiguracion.setMargin(new Insets(0, 10, 0, 10));
        _btnConfiguracion.setPreferredSize(new Dimension(70, 70));
        _btnConfiguracion.setVerticalAlignment(SwingConstants.BOTTOM);
        _btnConfiguracion.setVerticalTextPosition(SwingConstants.BOTTOM);
        // Añadimos el evento de pulsación del ratón
        _btnConfiguracion.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent evt) {
                pulsacionBotonConfiguracion(evt);
            }
        });
        _botonera.add(_btnConfiguracion);

        // BOTON AYUDA
        _btnAyuda.setFont(new Font("Tahoma", Font.BOLD, 11));
        _btnAyuda.setName("btnAyuda");
        _btnAyuda.setText("Ayuda");
        _btnAyuda.setIcon(_imgAyuda);
        _btnAyuda.setBorderPainted(false);
        _btnAyuda.setAlignmentX(0.5F);
        _btnAyuda.setDoubleBuffered(true);
        _btnAyuda.setFocusCycleRoot(true);
        _btnAyuda.setFocusPainted(true);
        _btnAyuda.setFocusable(true);
        _btnAyuda.setHorizontalTextPosition(SwingConstants.CENTER);
        _btnAyuda.setIconTextGap(-5);
        _btnAyuda.setMargin(new Insets(0, 10, 0, 10));
        _btnAyuda.setPreferredSize(new Dimension(70, 70));
        _btnAyuda.setVerticalAlignment(SwingConstants.BOTTOM);
        _btnAyuda.setVerticalTextPosition(SwingConstants.BOTTOM);
        // Añadimos el evento de pulsación del ratón
        _btnAyuda.addMouseListener(new MouseAdapter() {

            public void mousePressed(MouseEvent evt) {
                pulsacionBotonAyuda(evt);
            }
        });
        _botonera.add(_btnAyuda);

        getContentPane().add(_botonera, BorderLayout.PAGE_START);

        pack();
    }

    /**
     * Muestra el panel de servidores en el panel principal de la ventana.
     * 
     * @param evt Evento de pulsación del ratón sobre el botón _btnServidores.
     */
    private void pulsacionBotonServidores(MouseEvent evt) {

        ((CardLayout) _panelPrincipal.getLayout()).show(_panelPrincipal, "Servidores");
    }

    /**
     * Muestra el panel de búsquedas en el panel principal de la ventana.
     * 
     * @param evt Evento de pulsación del ratón sobre el botón _btnBuscar.
     */
    private void pulsacionBotonBuscar(MouseEvent evt) {

        ((CardLayout) _panelPrincipal.getLayout()).show(_panelPrincipal, "Buscador");
    }

    /**
     * Muestra el panel de tráfico en el panel principal de la ventana.
     * 
     * @param evt Evento de pulsación del ratón sobre el botón _btnTrafico.
     */
    private void pulsacionBotonTrafico(MouseEvent evt) {

        ((CardLayout) _panelPrincipal.getLayout()).show(_panelPrincipal, "Descargas");
    }

//	************************************************************************************//
    /**
     * Muestra el panel de servidores en el panel principal de la ventana.
     * 
     * @param evt Evento de pulsación del ratón sobre el botón _btnServidores.
     */
    private void pulsacionBotonCompartidos(MouseEvent evt) {

        ((CardLayout) _panelPrincipal.getLayout()).show(_panelPrincipal, "Compartidos");
    }

//	************************************************************************************//
    /**
     * Muestra el panel de estadísticas en el panel principal de la ventana.
     * 
     * @param evt Evento de pulsación del ratón sobre el botón _btnEstadísticas.
     */
    private void pulsacionBotonEstadisticas(MouseEvent evt) {

        ((CardLayout) _panelPrincipal.getLayout()).show(_panelPrincipal, "Estadisticas");
    }

//	************************************************************************************//
    /**
     * Muestra el panel de configuración en el panel principal de la ventana.
     * 
     * @param evt Evento de pulsación del ratón sobre el botón _btnConfiguracion.
     */
    private void pulsacionBotonConfiguracion(MouseEvent evt) {

        ((CardLayout) _panelPrincipal.getLayout()).show(_panelPrincipal, "Configuracion");
    }

//	************************************************************************************//
    /**
     * Muestra el manual de ayuda de la aplicación.
     * 
     * @param evt Evento de pulsación del ratón sobre el botón _btnAyuda.
     */
    private void pulsacionBotonAyuda(MouseEvent evt) {
        // TODO: En pagina web, en otra ventana, se admiten sugerencias
    }

//	************************************************************************************//
    /**
     * Cambia el estado del botón conectar. Lo pone al estado Conectar si estaba en Desconectar
     * y viceversa, además de actualizar las etiquetas informativas correspondientes.
     * 
     * @param evt Evento de pulsación del ratón sobre el botón _btnConectar.
     */
    private void pulsacionBotonConectar(MouseEvent evt) {

        try {
        // Si el botón conectar tiene el texto Conectar
        if (_btnConectar.getText().equals("Conectar")) // Avisamos al Control de la ventana principal para que realice la acción de conectar con el servidor
        {
            int sPuerto = Integer.parseInt(ControlConfiguracionCliente.obtenerInstancia().obtenerPropiedad("PuertoServidor"));
            String serverHost = ControlConfiguracionCliente.obtenerInstancia().obtenerPropiedad("IpServidor");
            _controlador.peticionConexionAServidor(serverHost, sPuerto);
            tratarEvento(EventoVentanaPrincipal.MOSTRAR_ESTADO_CONECTADO, null);
        } else // Avisamos al Control de la ventana principal para que realice la acción de desconectar con el servidor
        {
            _controlador.peticionDeDesconexionDeServidor();
            tratarEvento(EventoVentanaPrincipal.MOSTRAR_ESTADO_DESCONECTADO, null);
        }
        } catch (Exception ex) {
            tratarEvento(EventoVentanaPrincipal.MOSTRAR_MENSAJE_ERROR, "Error al intentar conectarse con el servidor");
            Logger.getLogger(GUIPanelServidores.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

//	************************************************************************************//
    /**
     * Cambia el estado del botón conectar. Lo pone al estado Conectar si estaba en Desconectar
     * y viceversa, además de actualizar las etiquetas informativas correspondientes.
     * 
     * @param evento Evento producido sobre la ventana principal.
     * @param params Parametros asociados a ese evento.
     */
    public void tratarEvento(EventoVentanaPrincipal evento, Object params) {

        switch (evento) {

            case MOSTRAR_ESTADO_CONECTADO:

                // La imagen y el texto del botón ahora son Desconectar
                _btnConectar.setText("Desconectar");
                _btnConectar.setIcon(_imgDesconectar);

                // Cambiamos las etiquetas de estado
                _lblConexion.setText("Conectado");
                _lblEstado.setText("eGorilla conectado");
                break;

            case MOSTRAR_ESTADO_DESCONECTADO:

                // La imagen y el texto del botón ahora son Desconectar
                _btnConectar.setText("Conectar");
                _btnConectar.setIcon(_imgConectar);

                // Cambiamos las etiquetas de estado
                _lblConexion.setText("Desconectado");
                _lblEstado.setText("eGorilla desconectado");
                break;

            case MOSTRAR_MENSAJE_ERROR:

                // Mostramos el mensaje de error correspondiente
                JOptionPane.showMessageDialog(null,
                        params,
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                break;
        }
    }
}
