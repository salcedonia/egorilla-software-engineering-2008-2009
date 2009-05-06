package gui.grafica.compartidos;

import datos.Archivo;
import gestorDeFicheros.GestorCompartidos;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import mensajes.serverclient.ListaArchivos;
import org.jfree.data.xy.Vector;

/**
 * Clase que gestiona el panel de compartidos en la ventana principal de la
 * aplicación.
 * 
 * @author Mercedes Bernal
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
    
    private GestorCompartidos _gestorDeCompartidos;
    /**
     * Contiene el resultado de una lista de archivos compartidos.
     */
    //private PanelCompartidos _panelCompartidos;
    
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
        /*DefaultMutableTreeNode treeNode2 = new DefaultMutableTreeNode("Todos los archivos compartidos");
        treeNode1.add(treeNode2);
        treeNode2 = new DefaultMutableTreeNode("Archivos completados");
        treeNode1.add(treeNode2);
        treeNode2 = new DefaultMutableTreeNode("Archivos incompletos");
        treeNode1.add(treeNode2);
        treeNode2 = new DefaultMutableTreeNode("Directorios compartidos");
        treeNode1.add(treeNode2);*/
        
        _explorador.setModel(new DefaultTreeModel(treeNode1));
        _scrollPaneArbol.setViewportView(_explorador);
        
        
        _explorador.addMouseListener(new MouseListener() {

            @Override
            public void mousePressed(MouseEvent arg0) {
                System.out.println("mousePressed");
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("mouseClicked");
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("mouseReleased");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                System.out.println("mouseEntered");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                System.out.println("mouseExited");
            }

        });

        _panelArbol.add(_scrollPaneArbol);
        _splitPanel.setLeftComponent(_panelArbol);

        _panelContenido.setLayout(new BoxLayout(_panelContenido, BoxLayout.LINE_AXIS));
        _scrollPaneContenido.setAutoscrolls(true);
        
        DefaultTableModel modelo = new DefaultTableModel(new Object[][]{}, new String[]{"Nombre", "Tamaño", "Tipo", "Identificador"});
        listarArchivosCompartidos(modelo);
        
        _tablaContenido.setModel(modelo);
        //_tablaContenido.setPreferredSize(new Dimension(300, 140));
        _scrollPaneContenido.setViewportView(_tablaContenido);
        _panelContenido.add(_scrollPaneContenido);
        _splitPanel.setRightComponent(_panelContenido);
        add(_splitPanel);
    }
    
    private void listarArchivosCompartidos(DefaultTableModel modelo){
        ListaArchivos listacompartidos = _gestorDeCompartidos.getInstancia().getArchivosCompartidos();
        
        for(int i=0; i<listacompartidos.size();i++){
            //_tablaContenido.add
            Archivo archivo = listacompartidos.get(i);
            Object[] objeto = {archivo.getNombre(), new Long(archivo.getSize()), archivo.getTipo().toString(), archivo.getHash().toString()};
            modelo.addRow(objeto);
        }
    }
    
}
