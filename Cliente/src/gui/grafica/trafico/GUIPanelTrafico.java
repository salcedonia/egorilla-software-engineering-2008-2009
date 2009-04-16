package gui.grafica.trafico;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

//************************************************************************************//
/**
 * Panel de tráfico entre archivos.
 * 
 * @author  Mnemo
 * @author S@L-c
 */
public class GUIPanelTrafico extends JPanel {

    // CONSTANTES
	private static final long serialVersionUID = 1L;
	
	// ATRIBUTOS
    private JLabel _lblEstado;
    private JScrollPane _panelScroll1;
    private JScrollPane _panelScroll2;
    private JTable _tablaContenido;
    private JTextArea _txtEstado;
    
    // CONTROL
    @SuppressWarnings("unused")
	private ControlPanelTrafico _controlPanelTrafico;

//	************************************************************************************//
	/**
	 * Constructor de la clase PanelTrafico.
	 */
	public GUIPanelTrafico() {
    
		_controlPanelTrafico = new ControlPanelTrafico(this);
		iniciarComponentes();
    }
    
//	************************************************************************************//
    /**
     * Inicia los componentes del panel de tráfico.
     */
    private void iniciarComponentes() {

    	GridBagConstraints gridBagConstraints;

        _lblEstado = new JLabel();
        _panelScroll1 = new JScrollPane();
        _txtEstado = new JTextArea();
        _panelScroll2 = new JScrollPane();
        _tablaContenido = new JTable();

        setBorder(BorderFactory.createTitledBorder("Trafico"));
        setName("PanelTrafico"); 
        setLayout(new GridBagLayout());

        _lblEstado.setName("lblEstado");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        add(_lblEstado, gridBagConstraints);

        _panelScroll1.setName("panelScroll1"); 

        _txtEstado.setColumns(20);
        _txtEstado.setRows(5);
        _txtEstado.setName("textEstado");
        _txtEstado.setEditable(false);
        _panelScroll1.setViewportView(_txtEstado);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 357;
        gridBagConstraints.ipady = 27;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(10, 10, 10, 10);
        add(_panelScroll1, gridBagConstraints);

        // TABLA DE CONTENIDO
        _panelScroll2.setName("panelScroll2");
        _tablaContenido.setBackground(new Color(235, 233, 237));
        _tablaContenido.setModel(new DefaultTableModel( new Object [][] {},
        												new String [] { "Nombre", "Tamaño", "Completado", "Velocidad", "Progreso", "Fuentes", "Prioridad", "Estado", "Restante"}) {
            
			private static final long serialVersionUID = 1L;
			
			Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
			
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            @SuppressWarnings("unchecked")
			public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        
        _tablaContenido.setEnabled(false);
        _tablaContenido.setGridColor(new Color(235, 233, 237));
        _tablaContenido.setName("tablaContenido");
        _tablaContenido.setSelectionForeground(new Color(235, 233, 237)); 
        _panelScroll2.setViewportView(_tablaContenido);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 357;
        gridBagConstraints.ipady = 183;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(20, 10, 0, 10);
        add(_panelScroll2, gridBagConstraints);

        getAccessibleContext().setAccessibleName("Trafico"); 
    }
    
//	************************************************************************************//
    /**
     * Distingue entre los distintos eventos de actualización.
     *  
	 * @param evento Evento producido.
	 * @param parametros Parametros asociados al evento.
     */
    public void tratarEventos(EventoPanelTrafico accion, Object parametros) {
		
	}
}
