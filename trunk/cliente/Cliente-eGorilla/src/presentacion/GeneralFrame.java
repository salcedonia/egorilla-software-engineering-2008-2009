/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GeneralFrame.java
 *
 * Created on 26-nov-2008, 0:25:11
 */

package presentacion;

import java.awt.CardLayout;
import java.awt.Image;
import javax.swing.JPanel;
import presentacion.buscador.GUIBuscador;
import presentacion.compartidos.GUICompartidos;
import presentacion.configuracion.GUIConfiguracion;
import presentacion.descargas.GUIDescargas;
import presentacion.servidores.GUIServidores;

/**
 *
 * @author Victor
 */
public class GeneralFrame extends javax.swing.JFrame {
    private JPanel panelActual;

    /** Creates new form GeneralFrame */
    public GeneralFrame() {
        
        initComponents();
        setExtendedState(MAXIMIZED_BOTH);
        setVisible(true);
        
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        estado = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        conexion = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        bConectar = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JToolBar.Separator();
        bServidores = new javax.swing.JButton();
        bBuscar = new javax.swing.JButton();
        bTrafico = new javax.swing.JButton();
        bCompartidos = new javax.swing.JButton();
        bEstadisticas = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JToolBar.Separator();
        bConfiguracion = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance().getContext().getResourceMap(GeneralFrame.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setExtendedState(MAXIMIZED_BOTH);
        setForeground(resourceMap.getColor("Form.foreground")); // NOI18N
        setIconImage(getIconImage());
        setIconImages(getIconImages());
        setLocationByPlatform(true);
        setMinimumSize(new java.awt.Dimension(436, 436));
        setName("Form"); // NOI18N

        mainPanel.setName("mainPanel"); // NOI18N
        mainPanel.setPreferredSize(new java.awt.Dimension(800, 600));
        mainPanel.setLayout(new java.awt.CardLayout());
        mainPanel.add( "Servidores", GUIServidores.instancia().getPanel());
        mainPanel.add( "Buscador", GUIBuscador.instancia().getPanel());
        mainPanel.add( "Configuracion", GUIConfiguracion.instancia().getPanel());
        mainPanel.add( "Descargas", GUIDescargas.instancia().getPanel());
        mainPanel.add( "Compartidos", GUICompartidos.instancia().getPanel());
        getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);

        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setLayout(new java.awt.BorderLayout());

        jSeparator1.setName("jSeparator1"); // NOI18N
        jPanel1.add(jSeparator1, java.awt.BorderLayout.NORTH);

        estado.setText(resourceMap.getString("estado.text")); // NOI18N
        estado.setName("estado"); // NOI18N
        jPanel1.add(estado, java.awt.BorderLayout.CENTER);

        jPanel2.setMinimumSize(new java.awt.Dimension(40, 20));
        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setPreferredSize(new java.awt.Dimension(100, 20));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator2.setName("jSeparator2"); // NOI18N
        jPanel2.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 10, 20));

        conexion.setText(resourceMap.getString("conexion.text")); // NOI18N
        conexion.setName("conexion"); // NOI18N
        jPanel2.add(conexion, new org.netbeans.lib.awtextra.AbsoluteConstraints(8, 0, 90, -1));

        jPanel1.add(jPanel2, java.awt.BorderLayout.EAST);

        getContentPane().add(jPanel1, java.awt.BorderLayout.PAGE_END);

        jToolBar1.setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("botonera.border.title"))); // NOI18N
        jToolBar1.setFloatable(false);
        jToolBar1.setForeground(resourceMap.getColor("botonera.foreground")); // NOI18N
        jToolBar1.setRollover(true);
        jToolBar1.setName("botonera"); // NOI18N
        jToolBar1.setOpaque(false);

        bConectar.setFont(resourceMap.getFont("Form.font")); // NOI18N
        bConectar.setText(resourceMap.getString("bConectar.text")); // NOI18N
        bConectar.setMargin(new java.awt.Insets(0, 0, 0, 0));
        bConectar.setMaximumSize(new java.awt.Dimension(70, 70));
        bConectar.setMinimumSize(new java.awt.Dimension(70, 70));
        bConectar.setName("bConectar"); // NOI18N
        bConectar.setPreferredSize(new java.awt.Dimension(70, 70));
        bConectar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                conexion(evt);
            }
        });
        jToolBar1.add(bConectar);

        jSeparator3.setName("jSeparator3"); // NOI18N
        jToolBar1.add(jSeparator3);

        bServidores.setFont(resourceMap.getFont("Form.font")); // NOI18N
        bServidores.setText(resourceMap.getString("bServidores.text")); // NOI18N
        bServidores.setMaximumSize(new java.awt.Dimension(70, 70));
        bServidores.setMinimumSize(new java.awt.Dimension(70, 70));
        bServidores.setName("bServidores"); // NOI18N
        bServidores.setPreferredSize(new java.awt.Dimension(70, 70));
        bServidores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cargarServidores(evt);
            }
        });
        jToolBar1.add(bServidores);

        bBuscar.setFont(resourceMap.getFont("Form.font")); // NOI18N
        bBuscar.setLabel(resourceMap.getString("bBuscar.label")); // NOI18N
        bBuscar.setMaximumSize(new java.awt.Dimension(70, 70));
        bBuscar.setMinimumSize(new java.awt.Dimension(70, 70));
        bBuscar.setName("bBuscar"); // NOI18N
        bBuscar.setPreferredSize(new java.awt.Dimension(70, 70));
        bBuscar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cargarBuscador(evt);
            }
        });
        jToolBar1.add(bBuscar);

        bTrafico.setFont(resourceMap.getFont("Form.font")); // NOI18N
        bTrafico.setText(resourceMap.getString("bTrafico.text")); // NOI18N
        bTrafico.setMaximumSize(new java.awt.Dimension(70, 70));
        bTrafico.setMinimumSize(new java.awt.Dimension(70, 70));
        bTrafico.setName("bTrafico"); // NOI18N
        bTrafico.setPreferredSize(new java.awt.Dimension(70, 70));
        bTrafico.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cargarTrafico(evt);
            }
        });
        jToolBar1.add(bTrafico);

        bCompartidos.setFont(resourceMap.getFont("Form.font")); // NOI18N
        bCompartidos.setText(resourceMap.getString("bCompartidos.text")); // NOI18N
        bCompartidos.setFocusable(false);
        bCompartidos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        bCompartidos.setMaximumSize(new java.awt.Dimension(70, 70));
        bCompartidos.setMinimumSize(new java.awt.Dimension(70, 70));
        bCompartidos.setName("bCompartidos"); // NOI18N
        bCompartidos.setPreferredSize(new java.awt.Dimension(70, 70));
        bCompartidos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        bCompartidos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cargarCompartidos(evt);
            }
        });
        jToolBar1.add(bCompartidos);

        bEstadisticas.setFont(resourceMap.getFont("Form.font")); // NOI18N
        bEstadisticas.setText(resourceMap.getString("bEstadisticas.text")); // NOI18N
        bEstadisticas.setMaximumSize(new java.awt.Dimension(70, 70));
        bEstadisticas.setMinimumSize(new java.awt.Dimension(70, 70));
        bEstadisticas.setName("bEstadisticas"); // NOI18N
        bEstadisticas.setPreferredSize(new java.awt.Dimension(70, 70));
        bEstadisticas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cargarEstadisticas(evt);
            }
        });
        jToolBar1.add(bEstadisticas);

        jSeparator4.setName("jSeparator4"); // NOI18N
        jToolBar1.add(jSeparator4);

        bConfiguracion.setFont(resourceMap.getFont("Form.font")); // NOI18N
        bConfiguracion.setText(resourceMap.getString("bConfiguracion.text")); // NOI18N
        bConfiguracion.setMaximumSize(new java.awt.Dimension(70, 70));
        bConfiguracion.setMinimumSize(new java.awt.Dimension(70, 70));
        bConfiguracion.setName("bConfiguracion"); // NOI18N
        bConfiguracion.setPreferredSize(new java.awt.Dimension(70, 70));
        bConfiguracion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                cargarConfiguracion(evt);
            }
        });
        jToolBar1.add(bConfiguracion);

        getContentPane().add(jToolBar1, java.awt.BorderLayout.PAGE_START);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cargarServidores(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cargarServidores

        ((CardLayout)mainPanel.getLayout()).show( mainPanel,"Servidores" );
}//GEN-LAST:event_cargarServidores

    private void cargarBuscador(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cargarBuscador
       
        ((CardLayout)mainPanel.getLayout()).show( mainPanel,"Buscador" );
}//GEN-LAST:event_cargarBuscador

    private void cargarTrafico(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cargarTrafico

        ((CardLayout)mainPanel.getLayout()).show( mainPanel,"Descargas" );
}//GEN-LAST:event_cargarTrafico

    private void cargarConfiguracion(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cargarConfiguracion

        ((CardLayout)mainPanel.getLayout()).show( mainPanel,"Configuracion" );
}//GEN-LAST:event_cargarConfiguracion

    private void cargarEstadisticas(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cargarEstadisticas
        // TODO add your handling code here:
}//GEN-LAST:event_cargarEstadisticas

private void conexion(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_conexion

    if(bConectar.getText().equals("Conectar"))
        eGorillaControlGeneral.instancia().action(eGorillaControlGeneral.CONECTAR, null);
    else
        eGorillaControlGeneral.instancia().action(eGorillaControlGeneral.DESCONECTAR, null);
}//GEN-LAST:event_conexion

private void cargarCompartidos(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_cargarCompartidos

    ((CardLayout)mainPanel.getLayout()).show( mainPanel,"Compartidos" );
}//GEN-LAST:event_cargarCompartidos

    // Recoge el evento de actualización
    public Image getIconImage() {
		
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance().getContext().getResourceMap(GeneralFrame.class);

        return resourceMap.getImageIcon("imageLabel.icon").getImage();
	}
    
    // Recoge el evento de actualización
	public void update(int evento, Object params) {
		switch (evento) {
			case GUIGeneral.MOSTRAR_MENU:
				setVisible(true);
				break;
            case GUIGeneral.MOSTRAR_CONECTADO:
				bConectar.setText("Desconectar");
                conexion.setText("Conectado");
                estado.setText("eGorilla conectado");
				break;
            case GUIGeneral.MOSTRAR_DESCONECTADO:
				bConectar.setText("Conectar");
                conexion.setText("Desconectado");
                estado.setText("eGorilla desconectado");
				break;
		}
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bBuscar;
    private javax.swing.JButton bCompartidos;
    private javax.swing.JButton bConectar;
    private javax.swing.JButton bConfiguracion;
    private javax.swing.JButton bEstadisticas;
    private javax.swing.JButton bServidores;
    private javax.swing.JButton bTrafico;
    private javax.swing.JLabel conexion;
    private javax.swing.JLabel estado;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JToolBar.Separator jSeparator3;
    private javax.swing.JToolBar.Separator jSeparator4;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JPanel mainPanel;
    // End of variables declaration//GEN-END:variables

}
