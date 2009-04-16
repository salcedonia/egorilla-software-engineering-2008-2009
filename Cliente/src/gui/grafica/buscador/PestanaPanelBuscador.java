package gui.grafica.buscador;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.*;

//************************************************************************************//
/**
 * Clase que representa una pestaña de un JTabbedPane. Esta pestaña contendrá un 
 * botón para cerrarla como funcionalidad añadida.
 *  
 * @author S@L-c
 */
public class PestanaPanelBuscador extends JPanel implements ActionListener {

    // CONSTANTES
	private static final long serialVersionUID = 1L;
	private static final String RUTA_RECURSOS = "/recursos/interfaz/buscador/";
	
	// ATRIBUTOS
	private final JTabbedPane _panelContenedor; // Referencia al coponente contenedor, una vez asigado no cambia (final)
    private JLabel _contenido; // Texto de la pestaña
    private JButton _btnCerrar; 
    private Cursor _cursor = new Cursor(Cursor.HAND_CURSOR); // Cursor de la mano
    private ImageIcon _imgCerrar = new ImageIcon(getClass().getResource(RUTA_RECURSOS + "btnCerrar.png"));
    
//  ************************************************************************************//
    /**
     * Constructor de la clase PestanaPanelBuscador.
     * 
     * @param panelContenedor Panel contenedor al que pertenece la pestaña.
     */
    public PestanaPanelBuscador(final JTabbedPane panelContenedor) {
        
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));
        
        // Obtengo la referencia del tabbedPane contenedor
        _panelContenedor = panelContenedor;
        setOpaque(false);
                
        // Obtengo el titulo que le fue asignado al JPanel
        _contenido = new JLabel() {
            
        	// CONSTANTES
			private static final long serialVersionUID = 1L;

			/**
			 * Devuelve el titulo de la pestaña correspondiente.
		 	 *
			 * @return El titulo de la pestaña correspondiente.
			 */
			public String getText() {
                
            	/*
            	 * Necesito el indice de nuevo porque este es el espacio de la nueva clase
                 * para ello hago referencia a la clase contenedora de la que estoy ahora 
                 */
                int i = panelContenedor.indexOfTabComponent(PestanaPanelBuscador.this);
                
                if (i != -1)
                    return panelContenedor.getTitleAt(i);
                
                return null;
            }
        };
                
        /*
         * Agregamos el texto de la pestaña y establecemos una
         * distancia por default entre este componente y los demás
         */
        add(_contenido);
        _contenido.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));

        // BOTON CERRAR
        _btnCerrar = new JButton();
        _btnCerrar.setIcon(_imgCerrar);
        int size = 20;
        _btnCerrar.setPreferredSize(new Dimension(size, size));
        _btnCerrar.setToolTipText("Cerrar esta pestaña");
        _btnCerrar.setUI(new BasicButtonUI());
        //hacemos transparente su relleno
        _btnCerrar.setContentAreaFilled(false);
        //No necesita ser enfocable
        _btnCerrar.setFocusable(false);
        _btnCerrar.setBorder(BorderFactory.createEtchedBorder());
        _btnCerrar.setBorderPainted(false);
        //Efecto de enmarcado cuando el mouse pasa sobre el
        _btnCerrar.addMouseListener(eventosRaton);
        _btnCerrar.setRolloverEnabled(true);
        //Listener para obtener controlar los eventos del boton
        _btnCerrar.addActionListener(this);
        // Ponemos el cursor de la mano
        _btnCerrar.setCursor(_cursor);
        add(_btnCerrar);
        //de nuevo un borde predeterminado
        setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
    }
    
//  ************************************************************************************//
    /**
     * Pulsacion sobre el botón cerrar.
     * 
     * @param ev Evento de acción.
     */
    public void actionPerformed(ActionEvent ev){
       
    	Object o = ev.getSource();
       
    	if (o == _btnCerrar){
            
    		// de nuevo el indice de ficha a la que pertenece "esta" pestaña
            int i = _panelContenedor.indexOfTabComponent(this);
            if (i != -1) {
                //quitando el JPanel al que pertenece esta pestaña
                _panelContenedor.remove(i);
            }
       }
   }
    
//  ************************************************************************************//
    /**
     * MouseListener que implementa las acciones de ratón sobre el botón
     * y cuando se sale del botón. 
     */
    private final static MouseListener eventosRaton = new MouseAdapter() {
        
    	public void mouseEntered(MouseEvent e) {
            
    		Component component = e.getComponent();
            
    		// Es un boton?
            if (component instanceof JButton) {
                
            	//casting
                JButton button = (JButton) component;
                //el mouse lo señala, pintemos el marco del boton
                button.setBorderPainted(true);
            }
        }

        public void mouseExited(MouseEvent e) {
            
        	Component component = e.getComponent();
            
        	// Es un boton?
            if (component instanceof JButton) {
                
            	//casting
                JButton button = (JButton) component;
                //el mouse ya no lo señala, quite el marco del boton
                button.setBorderPainted(false);
            }
        }
    };
    
}