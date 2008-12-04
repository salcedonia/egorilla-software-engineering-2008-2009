/*
 * BuscadorPanel.java
 *
 * Created on 27 de noviembre de 2008, 8:42
 */

package presentacion.buscador;

/**
 *
 * @author  Mnemo
 */
public class BuscadorPanel extends javax.swing.JPanel {

    /** Creates new form BuscadorPanel */
    public BuscadorPanel() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance().getContext().getResourceMap(BuscadorPanel.class);
        setBorder(javax.swing.BorderFactory.createTitledBorder(resourceMap.getString("Form.border.title"))); // NOI18N
        setName("Form"); // NOI18N
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 130, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    //Inicia la interfaz
	private void iniciar() {
		setVisible(true);

		BuscadorControl.instancia().action(BuscadorControl.ACCION1, "");
	}

    //Distingue entre los distintos eventos de actualización
	@SuppressWarnings("unchecked")
	public void update(int action, Object object) {
		switch (action) {
			case GUIBuscador.EVENTO1:
				//cargarBuscador();
				break;
            case GUIBuscador.EVENTO2:
				break;
		}
	}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables

}
