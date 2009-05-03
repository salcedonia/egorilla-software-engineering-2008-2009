package gui.grafica.buscador;

import control.ControladorGrafica;
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
import peerToPeer.descargas.ObservadorAlmacenDescargas;

/**
 * Panel que contiene el resultado de una búsqueda.
 * 
 * @author Javier Salcedo
 */
public class PanelBusqueda extends JPanel implements ObservadorAlmacenDescargas{

    /**
     * Lista de archivos que ha respondido el servidor en respuesta a la búsqueda
     */
    private Archivo[] _busqueda;
    /**
     * Lista de paneles de archivos (BusquedaIndividual).
     */
    private ArrayList<BusquedaIndividual> _listaArchivos;
    /**
     * Panel principal del contenedor
     */
    private JPanel _panelPrincipal;
    /**
     * Controlador de la aplicacion en modo grafico.
     */
    private ControladorGrafica _controlador;

    /**
     * Constructor de la clase PanelBusqueda.
     */
    public PanelBusqueda(ControladorGrafica controlador, Archivo[] lista){

        _controlador = controlador;
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

            BusquedaIndividual b = new BusquedaIndividual(_busqueda[i]);
            
            // Guardamos la fila en el ArrayList
            _listaArchivos.add(b);
            // Creamos una nueva fila
            _panelPrincipal.add(b);
        }
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
    private class BusquedaIndividual extends JPanel{

        /**
         * Archivo que representa la búsqueda individual. Lo necesitamos
         * para pasarselo al controlador.
         */
        private Archivo _archivo;
        /**
         * Etiquetas de representación de los datos
         */
        private  JLabel _lblNombre,   _lblTamanio ,  _lblTipoArchivo ,  _lblHash ;
        /**
         * Opción descargar del _popup.
         */
        private JMenuItem _menuDescargar;
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
         * @param nombre Nombre del archivo.
         * @param tamanio Tamanio del archivo en bytes.
         * @param tipo Tipo del archivo.
         * @param hash Hash del archivo.
         */
        private BusquedaIndividual(Archivo archivo) {

            _archivo = archivo;
            
            _eventosRaton = new EventosRaton();
            _oyenteBoton = new OyenteBoton();
            
            _panelPrincipal = new JPanel();
            _panelPrincipal.addMouseListener(_eventosRaton);
            _lblNombre = new JLabel(_archivo.getNombre());
            _lblNombre.addMouseListener(_eventosRaton);
            _lblTamanio = new JLabel(Long.toString(_archivo.getSize()) + " bytes");
            _lblTamanio.addMouseListener(_eventosRaton);
            _lblTipoArchivo = new JLabel(_archivo.getTipo().name());
            _lblTipoArchivo.addMouseListener(_eventosRaton);
            _lblHash = new JLabel(_archivo.getHash());
            _lblHash.addMouseListener(_eventosRaton);
            
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
            _panelPrincipal.setBackground(Color.WHITE);
            _panelPrincipal.repaint();
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
                    
                    // Aviso al controlador con el archivo asociado
                    // a la BusquedaIndividual a la que se ha pulsado
                    _controlador.peticionDescargarFichero(_archivo);
                }
            }
        }
        
        /**
         * Clase que implementa los métodos necesarios para procesar 
         * eventos del raton producidos sobre el panel.
         */
        class EventosRaton extends MouseAdapter{
        
            @Override
            public void mouseClicked(MouseEvent evt){
            
                if(evt.getClickCount() == 2){
                    
                    // Aviso al controlador con el archivo asociado
                    // a la BusquedaIndividual a la que se ha pulsado
                    _controlador.peticionDescargarFichero(_archivo);
                }
                else 
                    if(evt.getClickCount() == 1){
                    
                        repintar();
                        // Solo se queda marcado el que ha sido seleccionado
                        _panelPrincipal.setBackground(Color.CYAN);
                        _panelPrincipal.repaint();
                    }     
            }

            /**
             * Establece el color blanco de fondo para todos los componentes
             * del panel.
             */
            private void repintar() {
            
                for(BusquedaIndividual b : _listaArchivos){
                
                    b._panelPrincipal.setBackground(Color.WHITE);
                    b._panelPrincipal.repaint();
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

        public PopupListener(JPopupMenu popupMenu) {
            _popup = popupMenu;
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
                _popup.show(e.getComponent(), e.getX(), e.getY());
            }
        }
    }
    
    //--------------------------------------\\
    //      INTERFACE ALMACEN DESCARGAS     \\
    //--------------------------------------\\
    
    /**
     * Pone en rojo todas la línea de la nueva descarga. 
     */
    @Override
    public void nuevaDescarga(String nombre, String hash, int tamanio) {

        for(BusquedaIndividual b : _listaArchivos){
                
            // Ponemos en rojo esa fila cuando ha llegado esa descarga
            if(b._lblHash.getText().matches(hash)){
            
                b._panelPrincipal.setBackground(Color.WHITE);
                b._lblNombre.setForeground(Color.RED);
                b._lblTamanio.setForeground(Color.RED);
                b._lblTipoArchivo.setForeground(Color.RED);
                b._lblHash.setForeground(Color.RED);
                
                b._panelPrincipal.repaint();
            }        
        }
    }

    @Override
    public void fragmentoDescargado(String hash) {

    }
}
