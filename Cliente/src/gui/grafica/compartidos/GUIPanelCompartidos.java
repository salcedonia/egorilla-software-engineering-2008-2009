package gui.grafica.compartidos;

import javax.swing.*;

import java.awt.*;

//************************************************************************************//
/**
 * Clase que gestiona el panel de compartidos en la ventana principal de la
 * aplicación.
 * 
 * @author Victor
 * @author S@L-c
 */
public class GUIPanelCompartidos extends JPanel {

    // CONSTANTES
	private static final long serialVersionUID = 1L;
	
	// ATRIBUTOS
    private JTree _explorador;
    private JPanel _panel1;
    private JPanel _panel2;
    private JScrollPane _panelScroll1;
    private JScrollPane _panelScroll2;
    private JSplitPane _panelSplit1;
    private JTable _tablaContenido;
    
    // CONTROL
    @SuppressWarnings("unused")
	
	
//	************************************************************************************//
	/** 
	 * Constructor de la clase PanelCompartidos. 
	 */
    public GUIPanelCompartidos() {
    	
  
    	
        iniciarComponentes();
    }
	
//	************************************************************************************//
	/**
	 * Inicia y configura todos los componentes del panel de compartidos.
	 */
	private void iniciarComponentes() {

        _panelSplit1 = new JSplitPane();
        _panelScroll1 = new JScrollPane();
        _panelScroll2 = new JScrollPane();
        _panel1 = new JPanel();
        _panel2 = new JPanel();
        _tablaContenido = new JTable();
        _explorador = new JTree();
        
        setBorder(BorderFactory.createTitledBorder("Archivos Compartidos"));
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        _panelSplit1.setDividerLocation(200);
        _panelSplit1.setName("panelSplit1");

        _panel1.setName("panel1"); 
        _panel1.setLayout(new BoxLayout(_panel1, BoxLayout.LINE_AXIS));

        _panelScroll1.setName("panelScroll1");

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Compartidos");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Todos los archivos compartidos");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Archivos completados");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Archivos incompletos");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Directorios compartidos");
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Todos los directorios");
        javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Disco local (C:)");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Unidad DVD (D:)");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        _explorador.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        _explorador.setName("explorador");
        _panelScroll1.setViewportView(_explorador);

        _panel1.add(_panelScroll1);

        _panelSplit1.setLeftComponent(_panel1);

        _panel2.setName("jPanel2");
        _panel2.setLayout(new BoxLayout(_panel2, BoxLayout.LINE_AXIS));

        _panelScroll2.setAutoscrolls(true);
        _panelScroll2.setName("panelScroll2");

        _tablaContenido.setBackground(new Color(235, 233, 237));
        _tablaContenido.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {}, new String [] { "Nombre", "Tamaño", "Tipo", "Identificador", "Carpeta"}));
        _tablaContenido.setName("tablaContenido");
        _tablaContenido.setPreferredSize(new Dimension(300, 64));
        _panelScroll2.setViewportView(_tablaContenido);

        _panel2.add(_panelScroll2);

        _panelSplit1.setRightComponent(_panel2);

        add(_panelSplit1);
    }
	
//	************************************************************************************//
	/**
	 * Procesa los distintos eventos que se producen sobre la vista de compartidos.
	 * 
	 * @param evento Evento producido sobre el panel de compartidos.
	 * @param params Parametros asociados a ese evento.
	 */
	
}
