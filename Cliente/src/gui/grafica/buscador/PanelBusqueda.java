package gui.grafica.buscador;

import datos.Archivo;
import gui.grafica.servidores.ObservadorPanelServidores;
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
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import peerToPeer.descargas.ObservadorAlmacenDescargas;

/**
 * Panel que contiene el resultado de una busqueda.
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
     * Controlador del panel buscador.
     */
    private ControladorPanelBuscador _controlador;
    /**
     * Vector de observadores de esta clase.
     */
    private Vector<ObservadorPanelBusqueda> _observadores;

    /**
     * Constructor de la clase PanelBusqueda.
     * 
     * @param controlador Controlador del panel buscador.
     * @param lista Lista de archivos devuelta por el servidor.
     */
    public PanelBusqueda(ControladorPanelBuscador controlador, Archivo[] lista){

        _controlador = controlador;
        _busqueda = lista;
        _listaArchivos = new ArrayList<BusquedaIndividual>();
        _observadores = new Vector<ObservadorPanelBusqueda>();
        _panelPrincipal = new JPanel();
        setLayout(new BorderLayout());
        add(_panelPrincipal, BorderLayout.NORTH);
        iniciarComponentes();
    }

    /**
     * Inicia los componentes del panel.
     */
    public void iniciarComponentes() {

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
            iniciarComponentes();
        }

        /**
         * Inicia los componentes de la cabecera.
         */
        private void iniciarComponentes() {

            _panelPrincipal = new JPanel();
            _lblNombre = new JLabel("Nombre");
            _lblTamanio = new JLabel("Tamaño");
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
         * Etiqueta que muestra el nombre del archivo.
         */
        private JLabel _lblNombre;   
        /**
         * Etiqueta que muestra el valor del tamanio del archivo.
         */
        private JLabel _lblTamanio;  
        /**
         * Etiqueta que muestra el tipo de archivo del archivo.
         */
        private JLabel _lblTipoArchivo;  
        /**
         * Etiqueta que muestra el hash del archivo.
         */
        private JLabel _lblHash;
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
            
            iniciarComponentes();
            
            createPopupMenu();
        }

        /**
         * Inicia los componentes de una busqueda individual
         */
        private void iniciarComponentes() {
            
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
                        
                        // Avisamos de la seleccion de una fila
                        avisarArchivoSeleccionado(_archivo);
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

        /**
         * Constructor de la clase PopupListener.
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
    public void addObservador(ObservadorPanelBusqueda observador){
    
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
     * @param archivo Archivo seleccionado.
     */
    private void avisarArchivoSeleccionado(Archivo archivo){
    
        for(ObservadorPanelBusqueda observador : _observadores)
            observador.archivoSeleccionado(archivo);
    } 
    
    //--------------------------------------\\
    //      INTERFACE ALMACEN DESCARGAS     \\
    //--------------------------------------\\
    
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
