package gui.grafica.buscador;

import datos.Archivo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 * Panel que contiene el resultado de una búsqueda.
 * 
 * @author Javier Salcedo
 */
public class PanelBusqueda extends JPanel {

    /**
     * Lista de archivos que ha respondido el servidor en respuesta a la búsqueda
     */
    private Archivo[] _busqueda;
    private ArrayList<BusquedaIndividual> _listaArchivos;
    /**
     * Panel principal del contenedor
     */
    private JPanel _panelPrincipal;

    /**
     * Constructor de la clase PanelBusqueda.
     */
    public PanelBusqueda(Archivo[] lista) {

        _busqueda = lista;
        _listaArchivos = new ArrayList<BusquedaIndividual>();
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

        // Representa los resultados de la búsqueda
        for (int i = 0; i < _busqueda.length; i++) {

            BusquedaIndividual b = new BusquedaIndividual(_busqueda[i].getNombre(),
                    _busqueda[i].getSize(),                                        
                    _busqueda[i].getTipo().toString(),
                    _busqueda[i].getHash());
            
            // Guardamos la fila en el ArrayList
            _listaArchivos.add(b);
            // Creamos una nueva fila
            _panelPrincipal.add(b);
        }
    }

    /**
     * Repinta el panel.
     */
    public void repintar() {
        _panelPrincipal.removeAll();
        _panelPrincipal.add(new Cabecera());
        for (int i = 0; i < _listaArchivos.size(); i++) {
            _panelPrincipal.add(_listaArchivos.get(i));
        }
        repaint();
        _panelPrincipal.repaint();
        _panelPrincipal.setVisible(true);
    }

    /**
     * Cabecera de la tabla donde van a representarse las búsquedas.
     */
    private class Cabecera extends JPanel {

        private  JLabel _lblNombre,  _lblTamanio ,  _lblTipoArchivo ,  _lblHash ;
        private JPanel _panelPrincipal;

        /**
         * Constructor de la clase Cabecera.
         */
        private Cabecera() {
            initComponent();
        }

        /**
         * Inicia los componentes de la cabecera.
         */
        private void initComponent() {

            _panelPrincipal = new JPanel();
            _lblNombre = new JLabel("Nombre");
            _lblTamanio = new JLabel("Tamanio");
            _lblTipoArchivo = new JLabel("Tipo");
            _lblHash = new JLabel("Hash");
            _panelPrincipal.setLayout(new GridLayout(0, 4, 25, 25));
            _panelPrincipal.setBackground(Color.BLUE);

            _lblNombre.setForeground(Color.WHITE);
            _lblTamanio.setForeground(Color.WHITE);
            _lblTipoArchivo.setForeground(Color.WHITE);
            _lblHash.setForeground(Color.WHITE);

            _panelPrincipal.add(_lblNombre);
            _panelPrincipal.add(_lblTamanio);
            _panelPrincipal.add(_lblTipoArchivo);
            _panelPrincipal.add(_lblHash);
            setLayout(new BorderLayout());
            add(_panelPrincipal, BorderLayout.NORTH);
        }
    }

    /**
     * Representa un archivo de la lista devuelta por el servidor.
     */
    private class BusquedaIndividual extends JPanel {

        /**
         * Etiquetas de representación de los datos
         */
        private  JLabel _lblNombre,   _lblTamanio ,  _lblTipoArchivo ,  _lblHash ;
        /**
         * Opción descargar del popup.
         */
        private JMenuItem _menuDescargar;
        /**
         * Para procesar pulsaciones sobre las opciones del popup.
         */
        private OyenteBoton _oyenteBoton;
        /**
         * Panel principal contenedor.
         */
        private JPanel _panelPrincipal;

        /**
         * Constructor de la clase BusquedaIndividual.
         * 
         * @param nombre Nombre del archivo.
         * @param tamanio Tamanio del archivo en bytes.
         * @param tipo Tipo del archivo.
         * @param hash Hash del archivo.
         */
        private BusquedaIndividual(String nombre, long tamanio, String tipo, String hash) {

            _panelPrincipal = new JPanel();
            _oyenteBoton = new OyenteBoton();
            _lblNombre = new JLabel(nombre);
            _lblTamanio = new JLabel(Long.toString(tamanio) + " bytes");
            _lblTipoArchivo = new JLabel(tipo);
            _lblHash = new JLabel(hash);
            initComponent();
            createPopupMenu();
        }

        /**
         * Inicia los componentes de una busqueda individual
         */
        private void initComponent() {
            _panelPrincipal.setLayout(new GridLayout(0, 4, 25, 25));
            _panelPrincipal.add(_lblNombre);
            _panelPrincipal.add(_lblTamanio);
            _panelPrincipal.add(_lblTipoArchivo);
            _panelPrincipal.add(_lblHash);
            setLayout(new BorderLayout());
            add(_panelPrincipal, BorderLayout.NORTH);
        }

        /**
         * Crea el menu que aparecera al hacer click con el boton derecho del raton
         * asignando los componentes que apareceran.
         */
        private void createPopupMenu() {

            JPopupMenu popup = new JPopupMenu();

            _menuDescargar = new JMenuItem("Descargar");
            _menuDescargar.addActionListener(_oyenteBoton);
            popup.add(_menuDescargar);

            MouseListener popupListener = new PopupListener(popup);
            _lblNombre.addMouseListener(popupListener);
            this.addMouseListener(popupListener);
        }

        /**
         * Clase que implementa los métodos necesarios para procesar 
         * eventos producidos sobre los diferentes botones del panel.
         */
        class OyenteBoton implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent event) {
                if (event.getActionCommand().equals("Descargar")) {
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
        private JPopupMenu popup;

        public PopupListener(JPopupMenu popupMenu) {
            popup = popupMenu;
        }

        @Override
        public void mousePressed(MouseEvent e) {
            mostrarMenuRaton(e);
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            mostrarMenuRaton(e);
        }

        private void mostrarMenuRaton(MouseEvent e) {
            if (e.isPopupTrigger()) {
                popup.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }
}
