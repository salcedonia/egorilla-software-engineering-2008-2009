/* 
	This file is part of eGorilla.

    eGorilla is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    eGorilla is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with eGorilla.  If not, see <http://www.gnu.org/licenses/>.
*/

package gui.grafica.servidores;

import gestorDeConfiguracion.ControlConfiguracionClienteException;
import gestorDeConfiguracion.FicheroInfo;
import gestorDeConfiguracion.InfoServidor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.BevelBorder;

/**
 * Panel que contiene la información de los servidores disponibles en la 
 * aplicacion.
 * 
 * @author Javier Salcedo
 */
public class PanelServidores extends JPanel implements Serializable {

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
     * Color de selección.
     */
    private Color _colorSeleccion = new Color(102, 204, 255);
    /**
     * Color de fondo del panel.
     */
    private Color _colorFondo = Color.WHITE;
    /**
     * Color del borde del panel.
     */
    private Color _colorBorde = Color.BLACK;

    /**
     * Constructor de la clase PanelServidores.
     */
    public PanelServidores(ControladorPanelServidores controlador) {

        _controlador = controlador;
        _listaServidores = new ArrayList<ServidorIndividual>();
        _observadores = new Vector<ObservadorPanelServidores>();
        _panelPrincipal = new JPanel();
        _panelPrincipal.setBackground(_colorFondo);
        setBackground(_colorFondo);
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
     * Anade un nuevo servidor a la lista de servidores.
     * 
     * @param infoServidor Informacion del servidor.
     */
    public void anadirServidor(InfoServidor infoServidor) {

        ServidorIndividual servidor = new ServidorIndividual(infoServidor);
        _listaServidores.add(servidor);
        _panelPrincipal.add(servidor);
        _panelPrincipal.repaint();
        repaint();
    }

    /**
     * Elimina un servidor a la lista de servidores.
     * 
     * @param infoServidor Informacion del servidor.
     */
    public void eliminarServidor(InfoServidor infoServidor) {

        for (int i = 0; i < _listaServidores.size(); i++) {
            if (_listaServidores.get(i).getInfoServidor().equals(infoServidor)) {
                _listaServidores.remove(i);
                _panelPrincipal.setVisible(false);
                repintar();
                break;
            }
        }
        ServidorIndividual servidor = new ServidorIndividual(infoServidor);
        _listaServidores.remove(servidor);
        _panelPrincipal.remove(servidor);
        _panelPrincipal.repaint();
        repaint();
    }

    /**
     * Crea la lista de servidores a mostrar.
     * TODO: 
     * Solo se carga el infoServidor por defecto, por lo que habria que tener una lista
     * de servidores a los que se pueda conectar guardados en algun archivo.
     * 
     * @throws java.lang.NumberFormatException
     */
    private void crearListaServidores() throws NumberFormatException {

        try {

            // Creo el objeto que contiene la lista de servidores
            FicheroInfo<InfoServidor> ficheroServidores = new FicheroInfo<InfoServidor>("servidores.info");

            //Cargo el fichero de servidores
            ficheroServidores.cargarFicheroInfo();

            // Obtengo la lista de servidores
            ArrayList<InfoServidor> listaServidores = ficheroServidores.getInfoFichero();

            //Actualizo la interfaz con los servidores leídos del fichero.
            for (InfoServidor infoServidor : listaServidores) {

                ServidorIndividual servidor = new ServidorIndividual(infoServidor);
                _listaServidores.add(servidor);
                _panelPrincipal.add(servidor);
            }
            _panelPrincipal.repaint();
            repaint();
        } catch (ControlConfiguracionClienteException ex) {            // Llamada al gestor de errores
        } catch (NumberFormatException ex) {            // Llamada al gestor de errores
        }
    }

    /**
     * Repinta el panel de servidores.
     */
    public void repintar() {
        _panelPrincipal.removeAll();
        _panelPrincipal.add(new Cabecera());
        for (int i = 0; i < _listaServidores.size(); i++) {
            _panelPrincipal.add(_listaServidores.get(i));
        }
        repaint();
        _panelPrincipal.setBackground(_colorFondo);
        _panelPrincipal.repaint();
        _panelPrincipal.setVisible(true);
    }
    
    /**
     * Cabecera de la tabla donde van a representarse los servidores.
     */
    private class Cabecera extends JPanel implements Serializable {

        /**
         * Etiqueta de la direccion IP del infoServidor.
         */
        private JLabel _lblDireccionIP;
        /**
         * Etiqueta del puerto del infoServidor.
         */
        private JLabel _lblPuerto;
        /**
         * Etiqueta del nombre del infoServidor.
         */
        private JLabel _lblNombre;
        /**
         * Etiqueta de la descripcion del infoServidor.
         */
        private JLabel _lblDescripcion;
        /**
         * Panel principal que contiene a todos los anteriores elementos.
         */
        private JPanel _panelPrincipal;
        /**
         * Color de la fuente de la cabecera.
         */
        private Color _colorFuente = Color.WHITE;
        /**
         * Color de fondo de la cabecera.
         */
        private Color _colorFondo = Color.BLUE;

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

            setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,
                    new Color(102, 204, 255),
                    new Color(51, 153, 255),
                    new Color(0, 0, 102),
                    new Color(0, 0, 153)));

            _panelPrincipal = new JPanel();
            _lblDireccionIP = new JLabel("IP Servidor");
            _lblPuerto = new JLabel("Puerto Servidor");
            _lblNombre = new JLabel("Nombre Servidor");
            _lblDescripcion = new JLabel("Descripcion Servidor");
            _panelPrincipal.setLayout(new GridLayout(0, 4, 25, 25));
            _panelPrincipal.setBackground(_colorFondo);

            _lblDireccionIP.setForeground(_colorFuente);
            _lblPuerto.setForeground(_colorFuente);
            _lblNombre.setForeground(_colorFuente);
            _lblDescripcion.setForeground(_colorFuente);

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
     * Representa un archivo de la lista devuelta por el infoServidor.
     */
    private class ServidorIndividual extends JPanel {

        /**
         * Informacion a representar del infoServidor.
         */
        private InfoServidor _infoServidor;
        /**
         * Etiqueta que muestra el valor de la direccion IP del infoServidor.
         */
        private JLabel _lblDireccionIP;
        /**
         * Etiqueta que muestra el valor del puerto de conexion del infoServidor.
         */
        private JLabel _lblPuerto;
        /**
         * Etiqueta que muestra el valor del nombre del infoServidor.
         */
        private JLabel _lblNombre;
        /**
         * Etiqueta que muestra el valor de la descripcion del infoServidor.
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
         * @param infoServidor Informacion del infoServidor.
         */
        private ServidorIndividual(InfoServidor infoServidor) {

            _infoServidor = infoServidor;

            _eventosRaton = new EventosRaton();
            _oyenteBoton = new OyenteBoton();

            _panelPrincipal = new JPanel();
            _panelPrincipal.setBorder(BorderFactory.createLineBorder(_colorFondo));
            _panelPrincipal.setBackground(_colorFondo);
            setBackground(_colorFondo);

            _panelPrincipal.addMouseListener(_eventosRaton);
            _lblDireccionIP = new JLabel(_infoServidor.getDireccionIP());
            _lblDireccionIP.addMouseListener(_eventosRaton);
            _lblPuerto = new JLabel(Integer.toString(_infoServidor.getPuerto()));
            _lblPuerto.addMouseListener(_eventosRaton);
            _lblNombre = new JLabel(_infoServidor.getNombre());
            _lblNombre.addMouseListener(_eventosRaton);
            _lblDescripcion = new JLabel(_infoServidor.getDescripcion());
            _lblDescripcion.addMouseListener(_eventosRaton);

            iniciarComponentes();

            createPopupMenu();
        }

        /**
         * Inicia los componentes de una busqueda individual
         */
        private void iniciarComponentes() {

            _panelPrincipal.setLayout(new GridLayout(0, 4, 25, 15));
            _panelPrincipal.add(_lblNombre);
            _panelPrincipal.add(_lblDireccionIP);
            _panelPrincipal.add(_lblPuerto);
            _panelPrincipal.add(_lblDescripcion);
            setLayout(new BorderLayout());
            add(_panelPrincipal, BorderLayout.CENTER);
            _panelPrincipal.repaint();
            repaint();
        }

        /**
         * Devuelve la info del servidor.
         * 
         * @return Devuelve la info del servidor.
         */
        private InfoServidor getInfoServidor(){
            
            return _infoServidor;
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

                        _controlador.peticionConexionAServidor(_infoServidor.getDireccionIP(), _infoServidor.getPuerto());
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

                        _controlador.peticionConexionAServidor(_infoServidor.getDireccionIP(), _infoServidor.getPuerto());
                    } catch (Exception ex) {

                        JOptionPane.showMessageDialog(null, "Error de conexión",
                                "Error al conectarse al servidor",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } else if (evt.getClickCount() == 1) {

                    borrarSeleccionAnterior();

                    // Solo se queda marcado el que ha sido seleccionado
                    _panelPrincipal.setBackground(_colorSeleccion);
                    _panelPrincipal.setBorder(BorderFactory.createLineBorder(_colorSeleccion));
                    setBorder(BorderFactory.createLineBorder(_colorBorde));
                    _panelPrincipal.repaint();
                    repaint();

                    // Aviso a los observadores
                    avisarServidorSeleccionado(_infoServidor);
                }
            }

            /**
             * Establece el color blanco de fondo para todos los componentes
             * del panel.
             */
            private void borrarSeleccionAnterior() {

                for (ServidorIndividual b : _listaServidores) {

                    b._panelPrincipal.setBackground(_colorFondo);
                    b.setBorder(BorderFactory.createLineBorder(_colorFondo));
                    b._panelPrincipal.setBorder(BorderFactory.createLineBorder(_colorFondo));
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
    public void addObservador(ObservadorPanelServidores observador) {

        if (!_observadores.contains(observador)) {
            _observadores.add(observador);
        }
    }

    /**
     * Elimina un observador de la lista de observadores.
     * 
     * @param observador Observador a eliminar de la lista.
     */
    public void eliminaObservador(ObservadorPanelServidores observador) {

        _observadores.remove(observador);
    }

    /**
     * Avisa a todos los observadores registrados en la lista de la 
     * seleccion de un infoServidor en la lista de servidores.
     * 
     * @param infoServidor Informacion del infoServidor seleccionado.
     */
    private void avisarServidorSeleccionado(InfoServidor infoServidor) {

        for (ObservadorPanelServidores observador : _observadores) {
            observador.servidorSeleccionado(infoServidor);
        }
    }
}
