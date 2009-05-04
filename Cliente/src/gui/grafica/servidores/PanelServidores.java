package gui.grafica.servidores;

import gestorDeConfiguracion.ControlConfiguracionCliente;
import gestorDeConfiguracion.ControlConfiguracionClienteException;
import gestorDeConfiguracion.PropiedadCliente;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 * Panel que contiene la información de los servidores disponibles en la 
 * aplicacion.
 * 
 * @author Javier Salcedo
 */
public class PanelServidores extends JPanel {

    /**
     * Lista de paneles de servidores (ServidorIndividual).
     */
    private ArrayList<ServidorIndividual> _listaServidores;
    /**
     * Panel principal del contenedor
     */
    private JPanel _panelPrincipal;
    /**
     * Controlador de la pestania de servidores.
     */
    private ControladorPanelServidores _controlador;
    /**
     * Panel de Servidores que contiene a esta clase.
     */
    private Vector<ObservadorPanelServidores> _observadores;
    
    /**
     * Constructor de la clase PanelServidores.
     */
    public PanelServidores(ControladorPanelServidores controlador) {
        
        _controlador = controlador;
        _listaServidores = new ArrayList<ServidorIndividual>();
        _observadores = new Vector<ObservadorPanelServidores>();
        _panelPrincipal = new JPanel();
        setLayout(new BorderLayout());
        add(_panelPrincipal, BorderLayout.NORTH);
        initComponent();
    }

    /**
     * Inicia los componentes del panel.
     */
    public void initComponent() {

        _panelPrincipal.setLayout(new GridLayout(0, 1, 0, 0));
        _panelPrincipal.add(new Cabecera());
        crearListaServidores();
    }

    /**
     * Añade un nuevo servidor a la lista de servidores.
     * 
     * @param direccionIP Direccion IP del servidor.
     * @param puerto Puerto de conexion del nuevo servidor.
     * @param nombre Nombre del servidor.
     * @param descripcion Descripcion del servidor.
     */
    void addServidor(String direccionIP, Integer puerto, String nombre, String descripcion) {

        ServidorIndividual servidor = new ServidorIndividual(direccionIP, puerto, nombre, descripcion);
        _listaServidores.add(servidor);
        _panelPrincipal.add(servidor);
        _panelPrincipal.repaint();
        repaint();
    }

    /**
     * Crea la lista de servidores a mostrar.
     * TODO: 
     * Solo se carga el servidor por defecto, por lo que habria que tener una lista
     * de servidores a los que se pueda conectar guardados en algun archivo.
     * 
     * @throws java.lang.NumberFormatException
     */
    private void crearListaServidores() throws NumberFormatException {

        try {

            Integer puerto = Integer.parseInt(ControlConfiguracionCliente.obtenerInstancia().obtenerPropiedad(PropiedadCliente.PUERTO_SERVIDOR.obtenerLiteral()));
            String direccionIP = ControlConfiguracionCliente.obtenerInstancia().obtenerPropiedad(PropiedadCliente.IP_SERVIDOR.obtenerLiteral());
            String nombre = ControlConfiguracionCliente.obtenerInstancia().obtenerPropiedad(PropiedadCliente.NOMBRE_SERVIDOR.obtenerLiteral());
            String descripcion = ControlConfiguracionCliente.obtenerInstancia().obtenerPropiedad(PropiedadCliente.DESCRIP_SERVIDOR.obtenerLiteral());
            ServidorIndividual servidor = new ServidorIndividual(direccionIP, puerto, nombre, descripcion);
            _listaServidores.add(servidor);
            _panelPrincipal.add(servidor);
            _panelPrincipal.repaint();
            repaint();
        } catch (ControlConfiguracionClienteException ex) {

            // Llamada al gestor de errores

        } catch (NumberFormatException ex) {

            // Llamada al gestor de errores
        }
    }

    /**
     * Cabecera de la tabla donde van a representarse los servidores.
     */
    private class Cabecera extends JPanel {

        /**
         * Etiqueta de la direccion IP del servidor.
         */
        private JLabel _lblDireccionIP;
        /**
         * Etiqueta del puerto del servidor.
         */
        private JLabel _lblPuerto;
        /**
         * Etiqueta del nombre del servidor.
         */
        private JLabel _lblNombre;
        /**
         * Etiqueta de la descripcion del servidor.
         */
        private JLabel _lblDescripcion;
        /**
         * Panel principal que contiene a todos los anteriores elementos.
         */
        private JPanel _panelPrincipal;

        /**
         * Constructor de la clase Cabecera.
         */
        private Cabecera() {

            iniciarComponentes();
        }

        /**
         * Inicia los componentes de la cabecera.
         */
        private void iniciarComponentes() {

            _panelPrincipal = new JPanel();
            _lblDireccionIP = new JLabel("IP Servidor");
            _lblPuerto = new JLabel("Puerto Servidor");
            _lblNombre = new JLabel("Nombre Servidor");
            _lblDescripcion = new JLabel("Descripcion Servidor");
            _panelPrincipal.setLayout(new GridLayout(0, 4, 25, 25));
            _panelPrincipal.setBackground(Color.BLUE);

            _lblDireccionIP.setForeground(Color.WHITE);
            _lblPuerto.setForeground(Color.WHITE);
            _lblNombre.setForeground(Color.WHITE);
            _lblDescripcion.setForeground(Color.WHITE);

            _panelPrincipal.add(_lblNombre);
            _panelPrincipal.add(_lblDireccionIP);
            _panelPrincipal.add(_lblPuerto);
            _panelPrincipal.add(_lblDescripcion);
            setLayout(new BorderLayout());
            add(_panelPrincipal, BorderLayout.NORTH);
            _panelPrincipal.repaint();
        }
    }

    /**
     * Representa un archivo de la lista devuelta por el servidor.
     */
    private class ServidorIndividual extends JPanel {

        /**
         * Direccion IP asociada al servidor.
         */
        private String _direccionIP;
        /**
         * Puerto de conexion con el servidor.
         */
        private Integer _puerto;
        /**
         * Nombre del servidor.
         */
        private String _nombre;
        /**
         * Descripcion del servidor.
         */
        private String _descripcion;
        /**
         * Etiqueta que muestra el valor de la direccion IP del servidor.
         */
        private JLabel _lblDireccionIP;
        /**
         * Etiqueta que muestra el valor del puerto de conexion del servidor.
         */
        private JLabel _lblPuerto;
        /**
         * Etiqueta que muestra el valor del nombre del servidor.
         */
        private JLabel _lblNombre;
        /**
         * Etiqueta que muestra el valor de la descripcion del servidor.
         */
        private JLabel _lblDescripcion;
        /**
         * Opción conectar del _popup.
         */
        private JMenuItem _menuConectar;
        /**
         * Para procesar pulsaciones sobre las opciones del _popup.
         */
        private OyenteBoton _oyenteBoton;
        /*
         * Para procesar los eventos del raton sobre el panel. 
         */
        private EventosRaton _eventosRaton;
        /**
         * Panel principal contenedor.
         */
        private JPanel _panelPrincipal;

        /**
         * Constructor de la clase BusquedaIndividual.
         * 
         * @param direccionIP Direccion IP del servidor.
         * @param puerto Puerto de conexion al servidor.
         * @param nombre Nombre del servidor.
         * @param descripcion Descripcion del servidor.
         */
        private ServidorIndividual(String direccionIP, Integer puerto, String nombre, String descripcion) {

            _direccionIP = direccionIP;
            _puerto = puerto;
            _nombre = nombre;
            _descripcion = descripcion;

            _eventosRaton = new EventosRaton();
            _oyenteBoton = new OyenteBoton();

            _panelPrincipal = new JPanel();
            _panelPrincipal.addMouseListener(_eventosRaton);
            _lblDireccionIP = new JLabel(direccionIP);
            _lblDireccionIP.addMouseListener(_eventosRaton);
            _lblPuerto = new JLabel(Integer.toString(puerto));
            _lblPuerto.addMouseListener(_eventosRaton);
            _lblNombre = new JLabel(nombre);
            _lblNombre.addMouseListener(_eventosRaton);
            _lblDescripcion = new JLabel(descripcion);
            _lblDescripcion.addMouseListener(_eventosRaton);

            iniciarComponentes();

            createPopupMenu();
        }

        /**
         * Inicia los componentes de una busqueda individual
         */
        private void iniciarComponentes() {

            _panelPrincipal.setLayout(new GridLayout(0, 4, 25, 25));
            _panelPrincipal.add(_lblNombre);
            _panelPrincipal.add(_lblDireccionIP);
            _panelPrincipal.add(_lblPuerto);
            _panelPrincipal.add(_lblDescripcion);
            setLayout(new BorderLayout());
            add(_panelPrincipal, BorderLayout.NORTH);
            _panelPrincipal.setBackground(Color.WHITE);
            _panelPrincipal.repaint();
            repaint();
        }

        /**
         * Crea el menu que aparecera al hacer click con el boton derecho del raton
         * asignando los componentes que apareceran.
         */
        private void createPopupMenu() {

            JPopupMenu popup = new JPopupMenu();

            _menuConectar = new JMenuItem("Conectar");
            _menuConectar.addActionListener(_oyenteBoton);
            popup.add(_menuConectar);

            MouseListener popupListener = new PopupListener(popup);
            _lblDireccionIP.addMouseListener(popupListener);
            _lblPuerto.addMouseListener(popupListener);
            _lblNombre.addMouseListener(popupListener);
            _lblDescripcion.addMouseListener(popupListener);
            
            this.addMouseListener(popupListener);
        }

        /**
         * Clase que implementa los métodos necesarios para procesar 
         * eventos producidos sobre los diferentes botones del panel.
         */
        class OyenteBoton implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent event) {
                if (event.getActionCommand().equals("Conectar")) {
                    try {

                        _controlador.peticionConexionAServidor(_direccionIP, _puerto);
                    } catch (Exception ex) {

                        JOptionPane.showMessageDialog(null, "Error de conexión",
                                "Error al conectarse al servidor",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }

        /**
         * Clase que implementa los métodos necesarios para procesar 
         * eventos del raton producidos sobre el panel.
         */
        class EventosRaton extends MouseAdapter {

            @Override
            public void mouseClicked(MouseEvent evt) {

                if (evt.getClickCount() == 2) {
                    try {

                        _controlador.peticionConexionAServidor(_direccionIP, _puerto);
                    } catch (Exception ex) {

                        JOptionPane.showMessageDialog(null, "Error de conexión",
                                "Error al conectarse al servidor",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else if (evt.getClickCount() == 1) {

                    borrarSeleccionAnterior();
                    // Solo se queda marcado el que ha sido seleccionado
                    _panelPrincipal.setBackground(Color.CYAN);
                    _panelPrincipal.repaint();
                    repaint();
                    
                    // Aviso a los observadores
                    avisarServidorSeleccionado(_direccionIP, _puerto);        
                }
            }

            /**
             * Establece el color blanco de fondo para todos los componentes
             * del panel.
             */
            private void borrarSeleccionAnterior() {

                for (ServidorIndividual b : _listaServidores) {

                    b._panelPrincipal.setBackground(Color.WHITE);
                    b._panelPrincipal.repaint();
                    repaint();
                }
            }
        }
    }

    /**
     * Clase que interpreta los eventos de ratón para mostrar un popup
     * asociado.
     */
    class PopupListener extends MouseAdapter {

        /**
         * Popup del panel.
         */
        private JPopupMenu _popup;

        /**
         * Constructor de la clase gestorEGorilla.
         * 
         * @param popupMenu Popup asociado.
         */
        public PopupListener(JPopupMenu popupMenu) {
            _popup = popupMenu;
        }

        /**
         * Muestra el menu contextual.
         * 
         * @param e Evento del raton.
         */
        private void mostrarMenuRaton(MouseEvent e) {
            if (e.isPopupTrigger()) {
                _popup.show(e.getComponent(), e.getX(), e.getY());
            }
        }
        
        @Override
        public void mousePressed(MouseEvent e) {
            mostrarMenuRaton(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            mostrarMenuRaton(e);
        }
    }
    
    /**
     * Aniade un observador a la lista de observadores.
     * 
     * @param observador Nuevo observador a aniadir a la lista.
     */
    public void addObservador(ObservadorPanelServidores observador){
    
        if(!_observadores.contains(observador))
            _observadores.add(observador);
    }
    
    /**
     * Elimina un observador de la lista de observadores.
     * 
     * @param observador Observador a eliminar de la lista.
     */
    public void eliminaObservador(ObservadorPanelServidores observador){
    
        _observadores.remove(observador);
    }
    
    /**
     * Avisa a todos los observadores registrados en la lista de la 
     * seleccion de un servidor en la lista de servidores.
     * 
     * @param direccionIP Direccion IP del servidor seleccionado.
     * @param puerto Puerto del servidor seleccionado.
     */
    private void avisarServidorSeleccionado(String direccionIP, Integer puerto){
    
        for(ObservadorPanelServidores observador : _observadores)
            observador.servidorSeleccionado(direccionIP, puerto);
    } 
}
