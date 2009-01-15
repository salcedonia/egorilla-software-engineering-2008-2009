package interfaz.estadisticas;

import javax.swing.*;
import java.awt.*;

//************************************************************************************//
/**
 * Panel de estadisticas de la aplicación.
 * 
 * @author Victor
 * @author S@L-c
 */
public class GUIPanelEstadisticas extends JPanel {

    // CONSTANTES
	private static final long serialVersionUID = 1L;
	public static final String RUTA_RECURSOS = "src/interfaz/recursos/imagenes/estadisticas/";
	
	// ATRIBUTOS
    private JLabel _lblFechaEstadisticas;
    private JLabel _lblUltimaPuestaACero;
    private JButton _btnReiniciar;
    private JButton jButton1;
    private JButton jButton2;
    private JButton jButton3;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel _panelIzquierda;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JPanel jPanel6;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JScrollPane jScrollPane4;
    private JSplitPane jSplitPane1;
    private JSplitPane jSplitPane2;
    private JSplitPane jSplitPane3;
    private JTree _arbolEstadisticas;
    
    // CONTROL
    @SuppressWarnings("unused")
	private ControlPanelEstadisticas _controlPanelEstadisticas;
    
//	************************************************************************************//
	/**
	 * Constructor de la clase PanelEstadisticas.
	 */
	public GUIPanelEstadisticas() {
		
		_controlPanelEstadisticas = new ControlPanelEstadisticas(this);
		inciarComponentes();
    }
	
//	************************************************************************************//
	 /**
     * Inicia los componentes del panel de estadisticas.
     */
    private void inciarComponentes() {

    	_btnReiniciar = new JButton();
    	jButton1 = new JButton();
    	jButton2 = new JButton();
    	jButton3 = new JButton();
    	jPanel1 = new JPanel();
    	jPanel2 = new JPanel();
    	_panelIzquierda = new JPanel();
    	jPanel4 = new JPanel();
    	jPanel5 = new JPanel();
    	jPanel6 = new JPanel();
    	jSplitPane1 = new JSplitPane();
    	jSplitPane2 = new JSplitPane();
        jSplitPane3 = new JSplitPane();
        jScrollPane1 = new JScrollPane();
        jScrollPane2 = new JScrollPane();
        jScrollPane3 = new JScrollPane();
        jScrollPane4 = new JScrollPane();
        _lblUltimaPuestaACero = new JLabel();
        _lblFechaEstadisticas = new JLabel();
        _arbolEstadisticas = new JTree();
        
        setBorder(BorderFactory.createTitledBorder("Estadisticas"));
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        jSplitPane1.setDividerLocation(200);
        jSplitPane1.setName("jSplitPane1");

        jPanel1.setName("jPanel1"); 
        jPanel1.setLayout(new BorderLayout());

        _panelIzquierda.setMinimumSize(new Dimension(100, 30));
        _panelIzquierda.setName("panelIzquierda"); 
        _panelIzquierda.setPreferredSize(new Dimension(100, 30));

        _btnReiniciar.setText("Reiniciar");
        _btnReiniciar.setMaximumSize(new Dimension(85, 25));
        _btnReiniciar.setMinimumSize(new Dimension(85, 25));
        _btnReiniciar.setName("btnReiniciar"); 
        _btnReiniciar.setPreferredSize(new Dimension(85, 25));
        _panelIzquierda.add(_btnReiniciar);

        _lblUltimaPuestaACero.setText("Última puesta a cero: ");
        _lblUltimaPuestaACero.setName("lblUltimaPuestaACero"); 
        _panelIzquierda.add(_lblUltimaPuestaACero);

        _lblFechaEstadisticas.setText("15/12/2008 22:12:48");
        _lblFechaEstadisticas.setName("fechaEstadisticas");
        _panelIzquierda.add(_lblFechaEstadisticas);

        jPanel1.add(_panelIzquierda, BorderLayout.NORTH);

        jScrollPane1.setName("jScrollPane1");

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Estadisticas");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Transferencia");
        javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Índice SU/DE Sesión: En espera...");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Sesión Índice SU/DE (Excluidas Subidas a amigos): En espera...");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Índice SU/DE Acumulado: En espera...");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Subidas");
        javax.swing.tree.DefaultMutableTreeNode treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Sesión");
        javax.swing.tree.DefaultMutableTreeNode treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("Datos subidos: 0 Bytes");
        treeNode4.add(treeNode5);
        treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("Datos subidos a amigos: 0 Bytes");
        treeNode4.add(treeNode5);
        treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("Numero total de subidas: 0");
        treeNode4.add(treeNode5);
        treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("Subidas en espera: 0");
        treeNode4.add(treeNode5);
        treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("Sesiones de subida: 0");
        javax.swing.tree.DefaultMutableTreeNode treeNode6 = new javax.swing.tree.DefaultMutableTreeNode("Total de subidas satisfactorias: 0 (0.00 %)");
        treeNode5.add(treeNode6);
        treeNode6 = new javax.swing.tree.DefaultMutableTreeNode("Total de subidas erróneas: 0 (0.00 %)");
        treeNode5.add(treeNode6);
        treeNode6 = new javax.swing.tree.DefaultMutableTreeNode("Promedio subido por sesión: En espera...");
        treeNode5.add(treeNode6);
        treeNode6 = new javax.swing.tree.DefaultMutableTreeNode("Tiempo medio de subida: 0 segundos");
        treeNode5.add(treeNode6);
        treeNode4.add(treeNode5);
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Acumulado");
        treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("Datos subidos: 0 GB");
        treeNode4.add(treeNode5);
        treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("Sesiones de subida: 0");
        treeNode6 = new javax.swing.tree.DefaultMutableTreeNode("Total de subidas satisfactorias: 0 (0.00 %)");
        treeNode5.add(treeNode6);
        treeNode6 = new javax.swing.tree.DefaultMutableTreeNode("Total de subidas erróneas: 0 (0.00 %)");
        treeNode5.add(treeNode6);
        treeNode6 = new javax.swing.tree.DefaultMutableTreeNode("Promedio subido por sesión: En espera...");
        treeNode5.add(treeNode6);
        treeNode6 = new javax.swing.tree.DefaultMutableTreeNode("Tiempo medio de subida: 0 segundos");
        treeNode5.add(treeNode6);
        treeNode4.add(treeNode5);
        treeNode3.add(treeNode4);
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Descargas");
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Sesión");
        treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("Datos descargados: 0 Bytes");
        treeNode4.add(treeNode5);
        treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("Descargas completas: 0");
        treeNode4.add(treeNode5);
        treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("Descargas activas (partes): 0");
        treeNode4.add(treeNode5);
        treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("Fuentes encontradas: 0");
        treeNode4.add(treeNode5);
        treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("Sesiones de descarga: 0");
        treeNode6 = new javax.swing.tree.DefaultMutableTreeNode("Total de descargas satisfactorias: 0 (0.00 %)");
        treeNode5.add(treeNode6);
        treeNode6 = new javax.swing.tree.DefaultMutableTreeNode("Total de descargas erróneas: 0 (0.00 %)");
        treeNode5.add(treeNode6);
        treeNode6 = new javax.swing.tree.DefaultMutableTreeNode("Promedio descargado por sesión: En espera...");
        treeNode5.add(treeNode6);
        treeNode6 = new javax.swing.tree.DefaultMutableTreeNode("Tiempo medio de descarga: 0 segundos");
        treeNode5.add(treeNode6);
        treeNode4.add(treeNode5);
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Acumulado");
        treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("Datos descargados: 0 GB");
        treeNode4.add(treeNode5);
        treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("Descargas completas: 0");
        treeNode4.add(treeNode5);
        treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("Descargas activas (partes): 0");
        treeNode4.add(treeNode5);
        treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("Fuentes encontradas: 0");
        treeNode4.add(treeNode5);
        treeNode5 = new javax.swing.tree.DefaultMutableTreeNode("Sesiones de descarga: 0");
        treeNode6 = new javax.swing.tree.DefaultMutableTreeNode("Total de descargas satisfactorias: 0 (0.00 %)");
        treeNode5.add(treeNode6);
        treeNode6 = new javax.swing.tree.DefaultMutableTreeNode("Total de descargas erróneas: 0 (0.00 %)");
        treeNode5.add(treeNode6);
        treeNode6 = new javax.swing.tree.DefaultMutableTreeNode("Promedio descargado por sesión: En espera...");
        treeNode5.add(treeNode6);
        treeNode6 = new javax.swing.tree.DefaultMutableTreeNode("Tiempo medio de descarga: 0 segundos");
        treeNode5.add(treeNode6);
        treeNode4.add(treeNode5);
        treeNode3.add(treeNode4);
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Tiempo de estadísticas");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Última puesta a cero: 15/12/2008 22:12:48");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Tiempo desde la última puesta a cero: 4 días 1:34 Horas");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Sesión");
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Tiempo de ejecución: 4:20 Horas");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Tiempo de transferencia: 1:02 Minutos");
        treeNode3.add(treeNode4);
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Acumulado");
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Tiempo de ejecución: 4:20 Horas");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Tiempo de transferencia: 1:02 Minutos");
        treeNode3.add(treeNode4);
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Clientes");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Cliente conocidos: 1");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Servidores");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Servidores activos: 1");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Servidores caídos: 0");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Servidores borrados: 0");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Total: 1");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Usuarios totales: 2");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Archivos totales: 25");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Records");
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Máx. Servidores activos: 1");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Máx. Usuarios conectados: 2");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Máx. Archivos disponibles: 30");
        treeNode3.add(treeNode4);
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Archivos compartidos");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Nº de archivos compartidos: 25");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Tamaño medio de archivo: 2 MB");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Archivo de mayor tamaño: 56 MB");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Total de archivos compartidos: 12 GB");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Records");
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Máx. archivos compartidos: 30");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Promedio de archivo mayor: 40 MB");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Archivo de mayor tamaño: 9 GB");
        treeNode3.add(treeNode4);
        treeNode4 = new javax.swing.tree.DefaultMutableTreeNode("Mayor cantidad compartidad: 12 GB");
        treeNode3.add(treeNode4);
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Espacio en disco");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Número de descargas: 0");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Tamaño total de Descargas: 0 MB");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Tamaño total Completados: 0 MB (0%)");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Tamaño total pendiente de descargar: 0 MB");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Espacio libre en unidad temporal: 54 GB");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Espacio adicional necesario para las descargas: 0 GB");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        _arbolEstadisticas.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        _arbolEstadisticas.setName("arbolEstadisticas"); 
        jScrollPane1.setViewportView(_arbolEstadisticas);

        jPanel1.add(jScrollPane1, BorderLayout.CENTER);

        jSplitPane1.setLeftComponent(jPanel1);

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setLayout(new BoxLayout(jPanel2, BoxLayout.LINE_AXIS));

        jSplitPane2.setDividerLocation(250);
        jSplitPane2.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPane2.setName("jSplitPane2"); // NOI18N

        jSplitPane3.setDividerLocation(250);
        jSplitPane3.setOrientation(JSplitPane.VERTICAL_SPLIT);
        jSplitPane3.setName("jSplitPane3"); // NOI18N

        jPanel4.setName("jPanel4"); // NOI18N
        jPanel4.setLayout(new CardLayout());

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        jButton3.setIcon(new ImageIcon(RUTA_RECURSOS + "conexion.jpg"));
        jButton3.setBorder(null);
        jButton3.setBorderPainted(false);
        jButton3.setContentAreaFilled(false);
        jButton3.setHorizontalAlignment(SwingConstants.LEFT);
        jButton3.setName("jButton3"); 
        jScrollPane4.setViewportView(jButton3);

        jPanel4.add(jScrollPane4, "card3");

        jSplitPane3.setBottomComponent(jPanel4);

        jPanel5.setName("jPanel5");
        jPanel5.setLayout(new CardLayout());

        jScrollPane3.setName("jScrollPane3");
        
        jButton2.setIcon(new ImageIcon(RUTA_RECURSOS + "subidas.jpg"));
        jButton2.setBorder(null);
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setHorizontalAlignment(SwingConstants.LEFT);
        jButton2.setName("jButton2"); 
        jScrollPane3.setViewportView(jButton2);

        jPanel5.add(jScrollPane3, "card3");

        jSplitPane3.setLeftComponent(jPanel5);

        jSplitPane2.setBottomComponent(jSplitPane3);

        jPanel6.setName("jPanel6"); 
        jPanel6.setLayout(new CardLayout());

        jScrollPane2.setName("jScrollPane2"); 

        jButton1.setIcon(new ImageIcon(RUTA_RECURSOS + "descargas.jpg"));
        jButton1.setAutoscrolls(true);
        jButton1.setBorder(null);
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setHorizontalAlignment(SwingConstants.LEFT);
        jButton1.setName("jButton1"); 
        jScrollPane2.setViewportView(jButton1);

        jPanel6.add(jScrollPane2, "card3");

        jSplitPane2.setLeftComponent(jPanel6);

        jPanel2.add(jSplitPane2);

        jSplitPane1.setRightComponent(jPanel2);

        add(jSplitPane1);
    }

//	************************************************************************************//
	/**
	 * Procesa los distintos eventos que se producen sobre la vista de estadísticas.
	 * 
	 * @param evento Evento producido sobre el panel de estadísticas.
	 * @param params Parametros asociados a ese evento.
	 */
	public void tratarEvento(EventoPanelEstadisticas evento, Object params) {
		
	}
}
