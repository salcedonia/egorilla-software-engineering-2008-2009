package gui.grafica.trafico;

import datos.Archivo;
import gestorDeFicheros.GestorCompartidos;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.border.BevelBorder;
import peerToPeer.descargas.ObservadorAlmacenDescargas;

/**
 * Panel que gestiona las distintas descargas del cliente.
 * 
 * @author José Miguel Guerrero, Javier Salcedo
 */
public class PanelDescargas extends JPanel implements ObservadorAlmacenDescargas {

    /**
     * Lista de descargas del cliente.
     */
    private ArrayList<DescargaIndividual> _listaDescargas;
    /**
     * Controlador del panel de trafico.
     */
    private ControladorPanelTrafico _controlador;
    /**
     * Panel principal que contiene todos los elementos del panel.
     */
    private JPanel _panelPrincipal;
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
     * Constructor de la clase PanelDescargas.
     * 
     * @param controlador Controlador del panel de trafico.
     */
    public PanelDescargas(ControladorPanelTrafico controlador) {

        _controlador = controlador;

        // Registramos la vista como observadora del almacen de descargas
        _controlador.getGestorEGorilla().getAlmacenDescargas().agregarObservador(this);

        _listaDescargas = new ArrayList<DescargaIndividual>();
        _panelPrincipal = new JPanel();
        _panelPrincipal.setBackground(Color.WHITE);
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        add(_panelPrincipal, BorderLayout.NORTH);

        iniciarComponentes();
    }

    /**
     * Inicia los componentes del panel de descargas.
     */
    public void iniciarComponentes() {
        _panelPrincipal.setLayout(new GridLayout(0, 1, 0, 0));
        _panelPrincipal.add(new Cabecera());
    }

    /**
     * Repinta el panel de trafico.
     */
    public void repintar() {
        _panelPrincipal.removeAll();
        _panelPrincipal.add(new Cabecera());
        for (int i = 0; i < _listaDescargas.size(); i++) {
            _panelPrincipal.add(_listaDescargas.get(i));
        }
        repaint();
        _panelPrincipal.setBackground(_colorFondo);
        _panelPrincipal.repaint();
        _panelPrincipal.setVisible(true);
    }

    /**
     * Borra los archivos competados.
     */
    public void borrarCompletos() {
        for (int i = _listaDescargas.size() - 1; i >= 0; i--) {
            if (_listaDescargas.get(i).getEstado().equalsIgnoreCase("COMPLETADO")) {
                _listaDescargas.remove(i);
                _panelPrincipal.setVisible(false);
            }
        }
        repintar();
    }

    /**
     * Cabecera de la tabla donde van a representarse las descargas.
     */
    private class Cabecera extends JPanel {

        /**
         * Etiqueta del nombre del archivo asociado a la descarga.
         */
        private JLabel _labelnombre;
        /**
         * Etiqueta del estado de la descarga.
         */
        private JLabel _labelestado;  
        /**
         * Etiqueta del progreso de la descarga.
         */
        private JLabel _labelprogreso;  
        /**
         * Etiqueta del hash del archivo de la descarga.
         */
        private JLabel _labelhash;
        /**
         * Panel principal que contiene al resto.
         */
        private JPanel _panelPrincipal;
        /**
         * Color de fuente de la cabecera.
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
            _labelestado = new JLabel("Estado");
            _labelnombre = new JLabel("Fichero");
            _labelhash = new JLabel("Hash");
            _labelprogreso = new JLabel("Progreso");
            _panelPrincipal.setLayout(new GridLayout(0, 4, 25, 25));
            _panelPrincipal.setBackground(_colorFondo);

            _labelnombre.setForeground(_colorFuente);
            _labelhash.setForeground(_colorFuente);
            _labelprogreso.setForeground(_colorFuente);
            _labelestado.setForeground(_colorFuente);

            _panelPrincipal.add(_labelnombre);
            _panelPrincipal.add(_labelhash);
            _panelPrincipal.add(_labelprogreso);
            _panelPrincipal.add(_labelestado);
            setLayout(new BorderLayout());
            add(_panelPrincipal, BorderLayout.NORTH);
        }
    }

    /**
     * Clase que representa una descarga individual del cliente.
     */
    private class DescargaIndividual extends JPanel {

        /**
         * Etiqueta que muestra el nombre de la descarga.
         */
        private JLabel _lblNombre;
        /**
         * Etiqueta que muestra el estado de la descarga.
         */
        private JLabel _lblEstado;
        /**
         * Etiqueta que muestra el hash de la descarga.
         */
        private JLabel _lblHash;
        /**
         * Muestra el progreso de la descarga.
         */
        private JProgressBar _barraProgreso;
        /**
         * Hash del archivo asociado a la descarga.
         */
        private String _hash;
        /**
         * Progreso de la descarga.
         */
        private int _progreso;
        /**
         * Opcion de pausar la descarga del menu contextual.
         */
        private JMenuItem _opcionPausar;
        /**
         * Opcion de eliminar la descarga del menu contextual.
         */
        private JMenuItem _opcionEliminar;
        /**
         * Opcion limpiar todo del menu contextual.
         */
        private JMenuItem _opcionLimpiarTodo;
        /**
         * Oyente para los eventos de pulsacion de las opciones del menu contextual.
         */
        private OyenteBoton _oyenteBoton;
        /**
         * Oyente para los eventos del raton sobre las descargas.
         */
        private OyenteRaton _oyenteRaton;
        /**
         * Panel principal que contiene a todos los elementos anteriores.
         */
        private JPanel _panelPrincipal;

        /**
         * Constructor de la clase DescargaIndividual.
         * 
         * @param nombre Nombre del archivo de la descarga.
         * @param hash Hash del archivo de la descarga.
         * @param maximo Tamanio del archivo de la descarga.
         */
        private DescargaIndividual(String nombre, String hash, int maximo) {

            _barraProgreso = new JProgressBar(0, maximo);
            _barraProgreso.setValue(0);
            _barraProgreso.setStringPainted(true);
            cambiarColorBarra(new Color(19, 6, 255));
            _hash = hash;
            _panelPrincipal = new JPanel();
            _oyenteBoton = new OyenteBoton();
            _lblNombre = new JLabel(nombre);
            _lblHash = new JLabel(hash);
            _lblEstado = new JLabel("Descargando");
            iniciarComponentes();
            createPopupMenu();
        }

        /**
         * Inicia los componentes del panel de descargas.
         */
        private void iniciarComponentes() {

            _panelPrincipal.setLayout(new GridLayout(0, 4, 25, 5));
            _panelPrincipal.add(_lblNombre);
            _panelPrincipal.add(_lblHash);
            _panelPrincipal.add(_barraProgreso);
            _panelPrincipal.add(_lblEstado);
            _panelPrincipal.setBackground(_colorFondo);
            setLayout(new BorderLayout());
            add(_panelPrincipal, BorderLayout.NORTH);
        }

        /**
         * Devuelve el hash del archivo asociado a una descarga.
         * 
         * @return El hash del archivo asociado a una descarga.
         */
        private String getHash() {

            return _hash;
        }

        /**
         * Incrementa el valor y el dibujo de la barra de progreso.
         */
        private void incrementaProgressBar() {

            _progreso++;
            _barraProgreso.setValue(_progreso);
        }
        
        private void setValorProgressBar(int numero) {
            _progreso=numero;
            _barraProgreso.setValue(numero);
        }
        

        /**
         * Crea el menu que aparecera al hacer click con el boton derecho del raton
         * asignando los componentes que apareceran.
         */
        private void createPopupMenu() {
            JPopupMenu popup = new JPopupMenu();

            _opcionPausar = new JMenuItem("Pausar"/*,new ImageIcon("img/pause.gif")*/);
            _opcionPausar.addActionListener(_oyenteBoton);
            popup.add(_opcionPausar);

            _opcionEliminar = new JMenuItem("Eliminar"/*,new ImageIcon("img/cross.gif")*/);
            _opcionEliminar.addActionListener(_oyenteBoton);
            popup.add(_opcionEliminar);

            _opcionLimpiarTodo = new JMenuItem("Limpiar completos"/*,new ImageIcon("img/cross.gif")*/);
            _opcionLimpiarTodo.addActionListener(_oyenteBoton);
            popup.add(_opcionLimpiarTodo);

            _oyenteRaton = new OyenteRaton(popup);

            _lblNombre.addMouseListener(_oyenteRaton);
            _lblEstado.addMouseListener(_oyenteRaton);
            _lblHash.addMouseListener(_oyenteRaton);
            _barraProgreso.addMouseListener(_oyenteRaton);
            addMouseListener(_oyenteRaton);
        }

        /**
         * Cambia el color de fondo de la descarga.
         * 
         * @param c Nuevo color a establecer.
         */
        public void cambiarColor(Color c) {

            _panelPrincipal.setBackground(c);
        }

        /**
         * Cambia el color de la barra.
         * 
         * @param c Nuevo color a establecer.
         */
        public void cambiarColorBarra(Color c) {

            _barraProgreso.setBackground(c);
            _barraProgreso.setForeground(c);
        }

        /**
         * Dibuja el borde para la descarga.
         * 
         * @param c Color del borde.
         */
        public void crearBorde(Color c) {

            setBorder(BorderFactory.createLineBorder(c));
        }

        /**
         * Establece el estado de la descarga a valor <b>texto</b>.
         * 
         * @param texto Nuevo valor a establecer.
         */
        public void setEstado(String texto) {

            _lblEstado.setText(texto);
        }

        /**
         * Devuelve el estado de una descarga.
         * 
         * @return El estado de una descarga.
         */
        public String getEstado() {

            return _lblEstado.getText();
        }

        /**
         * Clase que gestiona los eventos de pulsación sobre los elementos
         * del menu contextual de cada descarga.
         */
        class OyenteBoton implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent event) {
                if (event.getActionCommand().equals("Pausar")) {
                    //TODO enviar orden al GestorEGorilla/AlmacenDescarga de parar la descarga
                    _opcionPausar.setText("Continuar");                    
                    Archivo arch=new Archivo(_lblNombre.getText(),_hash);
                    _controlador.getGestorEGorilla().descargaPausada(arch);
                }
                if (event.getActionCommand().equals("Continuar")) {
                    _lblEstado.setText("Descargando");
                    //TODO enviar orden al GestorEGorilla/AlmacenDescarga de parar la descarga
                    _opcionPausar.setText("Pausar");
                    Archivo arch=new Archivo(_lblNombre.getText(),_hash);
                    _controlador.getGestorEGorilla().nuevaDescarga(arch);
                }
                if (event.getActionCommand().equals("Eliminar")) {
                    //TODO enviar orden al GestorEGorilla/AlmacenDescarga de eliminar la descarga
                    eliminarDescarga(_hash);
                }
                if (event.getActionCommand().equals("Limpiar completos")) {
                    //TODO enviar orden al GestorEGorilla/AlmacenDescarga de eliminar la descarga
                    borrarCompletos();
                }
            }
        }

        /**
         * Clase que gestiona los eventos del raton producidos sobre 
         * cada descarga.
         */
        class OyenteRaton implements MouseListener {

            /**
             * Popup menu asociado a cada descarga.
             */
            private JPopupMenu popup;

            /**
             * Constructor de la clase OyenteRaton.
             * 
             * @param pop Popup asociado.
             */
            public OyenteRaton(JPopupMenu pop) {

                popup = pop;
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                asignar(e, _colorSeleccion, _colorBorde);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                mostrarMenuRaton(e);
                asignar(e, _colorSeleccion, _colorBorde);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                mostrarMenuRaton(e);
                asignar(e, _colorSeleccion, _colorBorde);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                asignar(e, _colorSeleccion, _colorBorde);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                asignar(e, _colorFondo, _colorFondo);
            }

            public void asignar(MouseEvent e, Color back, Color borde) {
                cambiarColor(back);
                crearBorde(borde);
            }

            /**
             * Muesta el menu contextual.
             * 
             * @param e Evento del raton.
             */
            private void mostrarMenuRaton(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popup.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        }
    }

    //--------------------------------------\\
    //      INTERFACE ALMACEN DESCARGAS     \\
    //--------------------------------------\\
    
    @Override
    public void nuevaDescarga(String nombre, String hash, int tamanio) {
        for (int i = 0; i < _listaDescargas.size(); i++) {
            if (_listaDescargas.get(i).getHash().equals(hash)) {
                _listaDescargas.get(i).setEstado("Descargando");
                _listaDescargas.get(i).cambiarColorBarra(new Color(19, 6, 255));
                return;
            }
        }
        DescargaIndividual descarga = new DescargaIndividual(nombre, hash, tamanio);
        int tamanioAux = GestorCompartidos.getInstancia().getGestorDisco().getFragmentador().cantidadFragmentosArchivo(hash);
        System.out.println("TAMAÑOOOOOOOOOO "+ tamanioAux + " - " + (tamanioAux-tamanio));
        descarga.setValorProgressBar((tamanioAux-tamanio));
        _panelPrincipal.add(descarga);
        _listaDescargas.add(descarga);
        _panelPrincipal.repaint();
        repaint();
    }

    @Override
    public void fragmentoDescargado(String hash) {
        for (int i = 0; i < _listaDescargas.size(); i++) {
            if (_listaDescargas.get(i).getHash().equals(hash)) {
                _listaDescargas.get(i).incrementaProgressBar();
                //repintar();
                break;
            }
        }
    }

    @Override
    public void eliminarDescarga(String hash) {
        for (int i = 0; i < _listaDescargas.size(); i++) {
            if (_listaDescargas.get(i).getHash().equals(hash)) {
                _listaDescargas.remove(i);
                _panelPrincipal.setVisible(false);
                repintar();
                break;
            }
        }
    }

    @Override
    public void descargaCompleta(String hash) {
        for (int i = 0; i < _listaDescargas.size(); i++) {
            if (_listaDescargas.get(i).getHash().equals(hash)) {
                _listaDescargas.get(i).cambiarColorBarra(new Color(61, 194, 106));
                _listaDescargas.get(i).setEstado("COMPLETADO");
                break;
            }
        }
    }

    @Override
    public void descargaPausada(String hash) {
        for (int i = 0; i < _listaDescargas.size(); i++) {
            if (_listaDescargas.get(i).getHash().equals(hash)) {
                _listaDescargas.get(i).cambiarColorBarra(new Color(210, 205, 13));
                _listaDescargas.get(i).setEstado("En pausa");
                break;
            }
        }
    }
}

