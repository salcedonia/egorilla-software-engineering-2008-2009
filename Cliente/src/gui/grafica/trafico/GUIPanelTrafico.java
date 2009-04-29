package gui.grafica.trafico;

import java.awt.BorderLayout;
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
import javax.swing.JProgressBar;



public class GUIPanelTrafico extends JPanel{
	private ArrayList<DescargaIndividual> _listaDescargas;
    private JPanel _panelPrincipal;
    private int prueba=0;
    
	public GUIPanelTrafico(){
		_listaDescargas= new ArrayList<DescargaIndividual>();
        _panelPrincipal=new JPanel();
        setLayout(new BorderLayout());
        add(_panelPrincipal,BorderLayout.NORTH);
		initComponent();
	}

	public void initComponent(){
		_panelPrincipal.setLayout(new GridLayout(0,1,0,0));
		_panelPrincipal.add(new Cabecera());
		nuevaDescarga("peli.avi","asdadsadafdagfadgadfg",100);
	}

	public void repintar(){
		_panelPrincipal.removeAll();
		_panelPrincipal.add(new Cabecera());
		for(int i=0;i<_listaDescargas.size();i++){
			_panelPrincipal.add(_listaDescargas.get(i));
		}
		repaint();
        _panelPrincipal.repaint();
        _panelPrincipal.setVisible(true);
	}

	public void nuevaDescarga(String nombre, String hash, int tamanio){
		DescargaIndividual descarga=new DescargaIndividual(nombre, hash, tamanio);
		_panelPrincipal.add(descarga);
		_listaDescargas.add(descarga);
        _panelPrincipal.repaint();
		repaint();
	}

	public void eliminarDescarga(String hash){
		for(int i=0;i<_listaDescargas.size();i++){
			if(_listaDescargas.get(i).getHash().equals(hash)){
				_listaDescargas.remove(i);
                _panelPrincipal.setVisible(false);
				repintar();
				break;
			}
		}
	}

	private class Cabecera extends JPanel{
		private JLabel _labelnombre,_labelestado,_labelprogreso,_labelhash;
        private JPanel _panelPrincipal;

		private Cabecera(){
			initComponent();
		}
		private void initComponent(){
            _panelPrincipal=new JPanel();
			_labelestado=new JLabel("Estado");
			_labelnombre=new JLabel("Fichero");
			_labelhash=new JLabel("Hash");
			_labelprogreso=new JLabel("Progreso");
			_panelPrincipal.setLayout(new GridLayout(0,4,150,150));
			_panelPrincipal.add(_labelnombre);
			_panelPrincipal.add(_labelhash);
			_panelPrincipal.add(_labelprogreso);
			_panelPrincipal.add(_labelestado);
            setLayout(new BorderLayout());
            add(_panelPrincipal,BorderLayout.NORTH);
		}
	}

	@SuppressWarnings("unused")
	private class DescargaIndividual extends JPanel{
		private JLabel _labelnombre,_labelestado,_labelhash;
		private JProgressBar _barra;
		private String _hash;
		private JMenuItem _menuItem, _menuItem2;
	    private OyenteBoton _oyenteBoton;
        private JPanel _panelPrincipal;

	    private DescargaIndividual(String nombre, String hash, int maximo){
			_barra = new JProgressBar(0, maximo);
			_barra.setValue(0);
			_barra.setStringPainted(true);
			_hash=hash;
            _panelPrincipal=new JPanel();
			_oyenteBoton=new OyenteBoton();
			_labelnombre=new JLabel(nombre);
			_labelhash=new JLabel(hash);
			_labelestado=new JLabel("Descargando");
			initComponent();
			createPopupMenu();
		}

	    private void initComponent(){
			_panelPrincipal.setLayout(new GridLayout(0,4,50,50));
			_panelPrincipal.add(_labelnombre);
			_panelPrincipal.add(_labelhash);
			_panelPrincipal.add(_barra);
			_panelPrincipal.add(_labelestado);
            setLayout(new BorderLayout());
            add(_panelPrincipal,BorderLayout.NORTH);
		}

	    private String getHash(){
			return _hash;
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

	        MouseListener popupListener = new PopupListener(popup);
	        _labelnombre.addMouseListener(popupListener);
	        _labelestado.addMouseListener(popupListener);
			_barra.addMouseListener(popupListener);
	        this.addMouseListener(popupListener);
	    }

		class OyenteBoton implements ActionListener{
			public void actionPerformed(ActionEvent event){
				if(event.getActionCommand().equals("Pausar")){
					_labelestado.setText("En pausa");
                    prueba++;
					nuevaDescarga("peli"+prueba+".avi","asdadsadafdagfadgadfg"+prueba,100);
					//TODO enviar orden al GestorEGorilla/AlmacenDescarga de parar la descarga
					_menuItem.setText("Continuar");
				}
				if(event.getActionCommand().equals("Continuar")){
					_labelestado.setText("Descargando");
                    //TODO enviar orden al GestorEGorilla/AlmacenDescarga de parar la descarga
					_menuItem.setText("Pausar");
				}
				if(event.getActionCommand().equals("Eliminar")){
                    //TODO enviar orden al GestorEGorilla/AlmacenDescarga de eliminar la descarga
					eliminarDescarga(_hash);
				}
			}
		}
	}

	class PopupListener extends MouseAdapter {
        private JPopupMenu popup;

        public PopupListener(JPopupMenu popupMenu) {
            popup = popupMenu;
        }

        public void mousePressed(MouseEvent e) {
            mostrarMenuRaton(e);
        }

        public void mouseReleased(MouseEvent e) {
            mostrarMenuRaton(e);
        }

        private void mostrarMenuRaton(MouseEvent e) {
            if (e.isPopupTrigger()) {
                popup.show(e.getComponent(),e.getX(), e.getY());
            }
        }
	}


}
