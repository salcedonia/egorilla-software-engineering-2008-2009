package gui.grafica.compartidos;

import javax.swing.*;

import java.awt.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * Clase que gestiona el panel de compartidos en la ventana principal de la
 * aplicación.
 * 
 * @author Javier Salcedo
 */
public class GUIPanelCompartidos extends JPanel {

    /**
     * Constante de identificador de la clase.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Arbol de exploración de carpetas.
     */
    private JTree _explorador;
    
    private JPanel _panelArbol;
    private JPanel _panelContenido;
    private JScrollPane _scrollPaneArbol;
    private JScrollPane _scrollPaneContenido;
    private JSplitPane _splitPanel;
    private JTable _tablaContenido;
    
    /** 
     * Constructor de la clase PanelCompartidos. 
     */
    public GUIPanelCompartidos() {

        iniciarComponentes();
    }

    /**
     * Inicia y configura todos los componentes del panel de compartidos.
     */
    private void iniciarComponentes() {

        _splitPanel = new JSplitPane();
        _scrollPaneArbol = new JScrollPane();
        _scrollPaneContenido = new JScrollPane();
        _panelArbol = new JPanel();
        _panelContenido = new JPanel();
        _tablaContenido = new JTable();
        _explorador = new JTree();

        setBorder(BorderFactory.createTitledBorder("Archivos Compartidos"));
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        _splitPanel.setDividerLocation(200);
        
        _panelArbol.setLayout(new BoxLayout(_panelArbol, BoxLayout.LINE_AXIS));
        
        DefaultMutableTreeNode treeNode1 = new DefaultMutableTreeNode("Compartidos");      
        DefaultMutableTreeNode treeNode2 = new DefaultMutableTreeNode("Todos los archivos compartidos");
        treeNode1.add(treeNode2);
        treeNode2 = new DefaultMutableTreeNode("Archivos completados");
        treeNode1.add(treeNode2);
        treeNode2 = new DefaultMutableTreeNode("Archivos incompletos");
        treeNode1.add(treeNode2);
        treeNode2 = new DefaultMutableTreeNode("Directorios compartidos");
        treeNode1.add(treeNode2);
        _explorador.setModel(new DefaultTreeModel(treeNode1));
        _scrollPaneArbol.setViewportView(_explorador);

        _panelArbol.add(_scrollPaneArbol);
        _splitPanel.setLeftComponent(_panelArbol);

        _panelContenido.setLayout(new BoxLayout(_panelContenido, BoxLayout.LINE_AXIS));
        _scrollPaneContenido.setAutoscrolls(true);
        
        _tablaContenido.setModel(new DefaultTableModel(
                new Object[][]{}, new String[]{"Nombre", "Tamaño", "Tipo", "Identificador", "Carpeta"}));
        _tablaContenido.setPreferredSize(new Dimension(300, 64));
        _scrollPaneContenido.setViewportView(_tablaContenido);
        _panelContenido.add(_scrollPaneContenido);
        _splitPanel.setRightComponent(_panelContenido);
        add(_splitPanel);
    }
}
