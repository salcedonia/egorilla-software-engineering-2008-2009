package gui.grafica.trafico;

import datos.Archivo;
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
 *
 * @author José Miguel Guerrero
 */
public class PanelDescargas extends JPanel implements ObservadorAlmacenDescargas {

    private ArrayList<DescargaIndividual> _listaDescargas;
    private ControladorPanelTrafico _controlador;
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

    public void borrarCompletos() {
        for (int i = _listaDescargas.size() - 1; i >= 0; i--) {
            if (_listaDescargas.get(i).getEstado().equalsIgnoreCase("COMPLETADO")) {
                _listaDescargas.remove(i);
                _panelPrincipal.setVisible(false);
            }
        }
        repintar();
    }

    private class Cabecera extends JPanel {

        private JLabel _labelnombre,  _labelestado,  _labelprogreso,  _labelhash;
        private JPanel _panelPrincipal;
        /**
         * Color de fuente de la cabecera.
         */
        private Color _colorFuente = Color.WHITE;
        /**
         * Color de fondo de la cabecera.
         */
        private Color _colorFondo = Color.BLUE;

        private Cabecera() {
            iniciarComponentes();
        }

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

    @SuppressWarnings("unused")
    private class DescargaIndividual extends JPanel {

        private JLabel _labelnombre,  _labelestado,  _labelhash;
        private JProgressBar _barra;
        private String _hash;
        private int _progreso;
        private JMenuItem _menuItem,  _menuItem2,  _menuItem3;
        private OyenteBoton _oyenteBoton;
        private OyenteRaton _oyenteRaton;
        private JPanel _panelPrincipal;

        private DescargaIndividual(String nombre, String hash, int maximo) {
            _barra = new JProgressBar(0, maximo);
            _barra.setValue(0);
            _barra.setStringPainted(true);
            cambiarColorBarra(new Color(19, 6, 255));
            _hash = hash;
            _panelPrincipal = new JPanel();
            _oyenteBoton = new OyenteBoton();
            _labelnombre = new JLabel(nombre);
            _labelhash = new JLabel(hash);
            _labelestado = new JLabel("Descargando");
            initComponent();
            createPopupMenu();
        }

        private void initComponent() {
            _panelPrincipal.setLayout(new GridLayout(0, 4, 25, 5));
            _panelPrincipal.add(_labelnombre);
            _panelPrincipal.add(_labelhash);
            _panelPrincipal.add(_barra);
            _panelPrincipal.add(_labelestado);
            _panelPrincipal.setBackground(_colorFondo);
            setLayout(new BorderLayout());
            add(_panelPrincipal, BorderLayout.NORTH);
        }

        private String getHash() {
            return _hash;
        }

        private void incrementaProgressBar() {
            _progreso++;
            _barra.setValue(_progreso);
        }

        /**
         * Crea el menu que aparecera al hacer click con el boton derecho del raton
         * asignando los componentes que apareceran.
         */
        private void createPopupMenu() {
            JPopupMenu popup = new JPopupMenu();

            _menuItem = new JMenuItem("Pausar"/*,new ImageIcon("img/pause.gif")*/);
            _menuItem.addActionListener(_oyenteBoton);
            popup.add(_menuItem);

            _menuItem2 = new JMenuItem("Eliminar"/*,new ImageIcon("img/cross.gif")*/);
            _menuItem2.addActionListener(_oyenteBoton);
            popup.add(_menuItem2);

            _menuItem3 = new JMenuItem("Limpiar completos"/*,new ImageIcon("img/cross.gif")*/);
            _menuItem3.addActionListener(_oyenteBoton);
            popup.add(_menuItem3);

            _oyenteRaton = new OyenteRaton(popup);

            _labelnombre.addMouseListener(_oyenteRaton);
            _labelestado.addMouseListener(_oyenteRaton);
            _labelhash.addMouseListener(_oyenteRaton);
            _barra.addMouseListener(_oyenteRaton);
            this.addMouseListener(_oyenteRaton);
        }

        public void cambiarColor(Color c) {
            _panelPrincipal.setBackground(c);
        }

        public void cambiarColorBarra(Color c) {
            _barra.setForeground(c);
        }

        public void crearBorde(Color c) {
            this.setBorder(BorderFactory.createLineBorder(c));
        }

        public void setEstado(String texto) {
            _labelestado.setText(texto);
        }

        public String getEstado() {
            return _labelestado.getText();
        }

        class OyenteBoton implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent event) {
                if (event.getActionCommand().equals("Pausar")) {
                    //TODO enviar orden al GestorEGorilla/AlmacenDescarga de parar la descarga
                    _menuItem.setText("Continuar");                    
                    Archivo arch=new Archivo(_labelnombre.getText(),_hash);
                    _controlador.getGestorEGorilla().descargaPausada(arch);
                }
                if (event.getActionCommand().equals("Continuar")) {
                    //TODO enviar orden al GestorEGorilla/AlmacenDescarga de parar la descarga
                    _menuItem.setText("Pausar");
                    Archivo arch=new Archivo(_labelnombre.getText(),_hash);
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

        class OyenteRaton implements MouseListener {

            private JPopupMenu popup;

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

