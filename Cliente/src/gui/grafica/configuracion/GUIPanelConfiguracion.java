package gui.grafica.configuracion;

import javax.swing.*;
import java.awt.*;
//import org.netbeans.lib.awtextra.*;

//************************************************************************************//
/**
 * Panel que se encarga de la configuración de la aplicación.
 *
 * @author  Mnemo
 * @author S@L-c
 */
public class GUIPanelConfiguracion extends JPanel{

    // CONSTANTES
	private static final long serialVersionUID = 1L;
	
    // ATRIBUTOS
	private JButton jButton1;
    private JButton jButton2;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JTextField jTextField1;
    private JTextField jTextField2;
    private JTextField jTextField3;
    
    // CONTROL
    @SuppressWarnings("unused")
	

//	************************************************************************************//
	/**
     * Constructor de la clase PanelConfiguración.
     */
	public GUIPanelConfiguracion() {
    	
        
		iniciarComponentes();
    }

//	************************************************************************************//
    /**
     * Inicia los componentes del panel de servidores.
     */
    private void iniciarComponentes() {
        
    	GridBagConstraints gridBagConstraints;

        jPanel1 = new JPanel();
        jLabel2 = new JLabel();
        jTextField1 = new JTextField();
        jLabel3 = new JLabel();
        jTextField2 = new JTextField();
        jLabel4 = new JLabel();
        jTextField3 = new JTextField();
        jPanel2 = new JPanel();
        jButton1 = new JButton();
        jButton2 = new JButton();

        setBorder(BorderFactory.createTitledBorder("Configuración")); 
        setName("PanelConfiguracion"); 
        setLayout(new GridBagLayout());

        jPanel1.setName("jPanel1"); 
        //jPanel1.setLayout(new AbsoluteLayout());

        jLabel2.setText("Limite de Velocidad"); 
        jLabel2.setName("jLabel2");
        //jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        jTextField1.setText(""); 
        jTextField1.setName("jTextField1"); 
        //jPanel1.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, 110, -1));

        jLabel3.setText("Numero de Descargas Simultaneas"); 
        jLabel3.setName("jLabel3"); 
        //jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, -1, -1));

        jTextField2.setText(""); 
        jTextField2.setName("jTextField2"); 
       // jPanel1.add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, 110, -1));

        jLabel4.setText("Puerto"); 
        jLabel4.setName("jLabel4"); 
       // jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, -1, -1));

        jTextField3.setText(""); 
        jTextField3.setName("jTextField3"); 
       // jPanel1.add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 80, 110, -1));

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.ipady = 40;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(30, 10, 0, 0);
        add(jPanel1, gridBagConstraints);

        jPanel2.setName("jPanel2"); 
        jPanel2.setLayout(new GridBagLayout());

        jButton1.setText("Aceptar"); 
        jButton1.setMaximumSize(new Dimension(81, 23));
        jButton1.setMinimumSize(new Dimension(81, 23));
        jButton1.setName("jButton1"); 
        jButton1.setPreferredSize(new Dimension(81, 23));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(20, 200, 0, 0);
        jPanel2.add(jButton1, gridBagConstraints);

        jButton2.setText("Restaurar");
        jButton2.setName("jButton2"); 
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(20, 9, 0, 0);
        jPanel2.add(jButton2, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 23;
        gridBagConstraints.ipady = 107;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(0, 10, 11, 6);
        add(jPanel2, gridBagConstraints);

        getAccessibleContext().setAccessibleName("Configuracion");    
    }
    
//	************************************************************************************//
    /**
     * Distingue entre los distintos eventos de actualización.
     *  
	 * @param evento Evento producido.
	 * @param parametros Parametros asociados al evento.
     */
   
}
