package gui.grafica.trafico;

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
import peerToPeer.descargas.ObservadorAlmacenDescargas;

/**
 *
 * @author José Miguel Guerrero
 */
public class GUIPanelTrafico extends JPanel implements ObservadorAlmacenDescargas {

    private ArrayList<DescargaIndividual> _listaDescargas;
    private ControladorPanelTrafico _controlador;
    private JPanel _panelPrincipal;

    public GUIPanelTrafico(ControladorPanelTrafico controlador) {
        
        _controlador = controlador;
        
        // Registramos la vista como observadora del almacen de descargas
        _controlador.getGestorEGorilla().getAlmacenDescargas().agregarObservador(this);
        
        _listaDescargas = new ArrayList<DescargaIndividual>();
        _panelPrincipal = new JPanel();
        setLayout(new BorderLayout());
        add(_panelPrincipal, BorderLayout.NORTH);
        iniciarComponentes();
    }

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
        _panelPrincipal.repaint();
        _panelPrincipal.setVisible(true);
    }

    @Override
    public void nuevaDescarga(String nombre, String hash, int tamanio) {
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

    private class Cabecera extends JPanel {

        private JLabel _labelnombre,  _labelestado,  _labelprogreso,  _labelhash;
        private JPanel _panelPrincipal;

        private Cabecera() {
            iniciarComponentes();
        }

        private void iniciarComponentes() {
            _panelPrincipal = new JPanel();
            _labelestado = new JLabel("ESTADO");
            _labelnombre = new JLabel("FICHERO");
            _labelhash = new JLabel("HASH");
            _labelprogreso = new JLabel("PROGRESO");
            _panelPrincipal.setLayout(new GridLayout(0, 4, 150, 150));
            _panelPrincipal.setBackground(Color.BLUE);


            _labelnombre.setForeground(Color.white);
            _labelhash.setForeground(Color.white);
            _labelprogreso.setForeground(Color.white);
            _labelestado.setForeground(Color.white);

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
        private JMenuItem _menuItem,  _menuItem2;
        private OyenteBoton _oyenteBoton;
        private OyenteRaton _oyenteRaton;
        private JPanel _panelPrincipal;

        private DescargaIndividual(String nombre, String hash, int maximo) {
            _barra = new JProgressBar(0, maximo);
            _barra.setValue(0);
            _barra.setStringPainted(true);
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
            _panelPrincipal.setLayout(new GridLayout(0, 4, 50, 50));
            _panelPrincipal.add(_labelnombre);
            _panelPrincipal.add(_labelhash);
            _panelPrincipal.add(_barra);
            _panelPrincipal.add(_labelestado);
            _panelPrincipal.setBackground(Color.WHITE);
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

            
            _oyenteRaton=new OyenteRaton(popup);
            
            _labelnombre.addMouseListener(_oyenteRaton);
            _labelestado.addMouseListener(_oyenteRaton);
            _labelhash.addMouseListener(_oyenteRaton);
            _barra.addMouseListener(_oyenteRaton);
            this.addMouseListener(_oyenteRaton);
        }

        public void cambiarColor(Color c){
            /*_labelnombre.setBackground(c);
            _labelestado.setBackground(c);
            _labelhash.setBackground(c);*/
            _panelPrincipal.setBackground(c);
        }

        public void crearBorde(Color c){
            this.setBorder(BorderFactory.createLineBorder(c));
        }

        class OyenteBoton implements ActionListener {

            @Override
            public void actionPerformed(ActionEvent event) {
                if (event.getActionCommand().equals("Pausar")) {
                    _labelestado.setText("En pausa");
                    //TODO enviar orden al GestorEGorilla/AlmacenDescarga de parar la descarga
                    _menuItem.setText("Continuar");
                }
                if (event.getActionCommand().equals("Continuar")) {
                    _labelestado.setText("Descargando");
                    //TODO enviar orden al GestorEGorilla/AlmacenDescarga de parar la descarga
                    _menuItem.setText("Pausar");
                }
                if (event.getActionCommand().equals("Eliminar")) {
                    //TODO enviar orden al GestorEGorilla/AlmacenDescarga de eliminar la descarga
                    eliminarDescarga(_hash);
                }
            }
        }

            class OyenteRaton implements MouseListener {
                private JPopupMenu popup;

                public OyenteRaton(JPopupMenu pop){
                    popup=pop;
                }
                
                @Override
                public void mouseClicked(MouseEvent e) {
                    asignar(e,Color.CYAN,Color.BLACK);
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    mostrarMenuRaton(e);
                    asignar(e,Color.CYAN,Color.BLACK);
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    mostrarMenuRaton(e);
                    asignar(e,Color.CYAN,Color.BLACK);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    asignar(e,Color.CYAN,Color.BLACK);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                   asignar(e,Color.WHITE,Color.WHITE);
                }


                public void asignar(MouseEvent e, Color back, Color borde){
                    cambiarColor(back);
                    crearBorde(borde);
                }

                private void mostrarMenuRaton(MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        popup.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }
    }


    
}
