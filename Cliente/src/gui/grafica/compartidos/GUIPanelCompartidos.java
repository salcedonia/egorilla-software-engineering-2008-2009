package gui.grafica.compartidos;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * Clase que gestiona el panel de compartidos en la ventana principal de la
 * aplicación.
 * 
 * @author Mercedes Bernal, Javier Salcedo
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
    /**
     * Nodo principal del arbol de exploracion.
     */
    private DefaultMutableTreeNode _nodoCompartidos;
    /**
     * Nodo que muestra todos los archivos compartidos.
     */
    private DefaultMutableTreeNode _nodoTodosLosCompartidos;
    /**
     * Nodo que muestra todos los archivos compartidos completos.
     */
    private DefaultMutableTreeNode _nodoCompletos;
    /**
     * Nodo que muestra todos los archivos compartidos incompletos.
     */
    private DefaultMutableTreeNode _nodoIncompletos;
    /**
     * Panel que contiene el arbol de exploracion.
     */
    private JPanel _panelArbol;
    /**
     * Panel que contiene a la tabla del contenido.
     */
    private JPanel _panelContenido;
    /**
     * Scroll panel que contiene al panel del arbol de exploracion.
     */
    private JScrollPane _scrollPaneArbol;
    /**
     * Scroll panel que contiene al panel del contenido
     */
    private JScrollPane _scrollPaneContenido;
    /**
     * Split panel que separa al panel de contenido y al del arbol.
     */
    private JSplitPane _splitPanel;
    /**
     * Controlador del panel de compartidos.
     */
    private ControladorPanelCompartidos _controlador;
    /**
     * Contiene el resultado de una lista de archivos compartidos.
     */
    private PanelCompartidos _panelCompartidos;

    /** 
     * Constructor de la clase PanelCompartidos. 
     */
    public GUIPanelCompartidos(ControladorPanelCompartidos controlador) {

        _controlador = controlador;

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
        _explorador = new JTree();

        setBorder(BorderFactory.createTitledBorder("Archivos Compartidos"));
        setLayout(new BorderLayout());

        // SPLIT IZQUIERDO
        _panelArbol.setLayout(new BorderLayout());

        // JTREE
        _nodoCompartidos = new DefaultMutableTreeNode("Compartidos");
        _nodoTodosLosCompartidos = new DefaultMutableTreeNode("Todos los Compartidos");
        _nodoCompartidos.add(_nodoTodosLosCompartidos);

        _nodoCompletos = new DefaultMutableTreeNode("Completos");
        _nodoCompartidos.add(_nodoCompletos);

        _nodoIncompletos = new DefaultMutableTreeNode("Incompletos");
        _nodoCompartidos.add(_nodoIncompletos);

        _explorador.setModel(new DefaultTreeModel(_nodoCompartidos));
        _scrollPaneArbol.setViewportView(_explorador);
        _explorador.addTreeSelectionListener(new TreeSelectionListener() {

            @Override
            public void valueChanged(TreeSelectionEvent e) {

                // Devuelve la ruta del ultimo elemento seleccionado del arbol.
                DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) _explorador.getLastSelectedPathComponent();

                // Si no se ha seleccionado nada
                if (nodo == null) {
                    return;
                }

                Object infoDelNodo = nodo.getUserObject();
                if (nodo.isLeaf()) {
                    String carpeta = (String) infoDelNodo;

                    if (carpeta.matches("Todos los Compartidos")) {
                        _panelCompartidos = new PanelCompartidos(_controlador, _controlador.peticionListarTodosCompartidos());
                    }

                    if (carpeta.matches("Completos")) {
                        _panelCompartidos = new PanelCompartidos(_controlador, _controlador.peticionListarCompartidosCompletos());
                    }

                    if (carpeta.matches("Incompletos")) {
                        _panelCompartidos = new PanelCompartidos(_controlador, _controlador.peticionListarCompartidosIncompletos());
                    }
                    actualizarPanelDerecha();

                // Si hay mas opciones se pondrian aqui
                }
            }

            /**
             * Configura todos los elementos 
             */
            private void actualizarPanelDerecha() {

                _panelCompartidos.setPreferredSize(new Dimension(450, 500));
                _scrollPaneContenido.setAutoscrolls(true);
                _scrollPaneContenido.setViewportView(_panelCompartidos);
                _panelContenido.add(_scrollPaneContenido);
                _splitPanel.setDividerLocation(_splitPanel.getDividerLocation());
                _splitPanel.setRightComponent(_panelContenido);
                repaint();
            }
        });

        _panelArbol.add(_scrollPaneArbol);

        _splitPanel.setLeftComponent(_panelArbol);

        // SPLIT DERECHO
        _panelContenido.setLayout(
                new BorderLayout());

        // Cargamos los compartidos por defecto
        _panelCompartidos = new PanelCompartidos(_controlador, _controlador.peticionListarTodosCompartidos());
        _panelCompartidos.setPreferredSize(new Dimension(450, 500));
        _scrollPaneContenido.setAutoscrolls(true);
        _scrollPaneContenido.setViewportView(_panelCompartidos);

        _panelContenido.add(_scrollPaneContenido);
        _splitPanel.setRightComponent(_panelContenido);

        _splitPanel.setDividerLocation(225);

        add(_splitPanel);
    }
}
