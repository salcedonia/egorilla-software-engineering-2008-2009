/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestorDeConfiguracion;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Ventanita (de prueba) que se comporta como observadora de la clase ControlConfiguracionCliente
 * Implementa la interfaz Observador
 * @author F. Javier Sanchez Pardo
 */
public class VentanaObservador extends JFrame implements Observador,ActionListener{
    //Variable estática para numerar las instancias.
    protected static int _contador = 0;

    private ControlConfiguracionCliente oCtrlConfigCli;
    
    private JPanel panel;
    private JLabel etiqueta;
    private JTextField txtField;
    private JButton boton;
     
    public VentanaObservador(ControlConfiguracionCliente oCtrlConfigCli)
    {
        //Inicializo la variable con mi Sujeto Observado
        this.oCtrlConfigCli = oCtrlConfigCli;        
        //Me suscribo al Sujeto observado.
        this.oCtrlConfigCli.anadirObservador(this);
        
        _contador++;
        
        //Etiqueto cada ventana que se cree con un Identificador distinto.
        this.setTitle("ID Observador: " + _contador);
        
        panel=new JPanel();

        etiqueta = new JLabel("Nº descargas simultaneas: ");
        panel.add(etiqueta);
        //Inicializo el campo de texto con el valor de la propiedad
        txtField = new JTextField(this.oCtrlConfigCli.obtenerPropiedad("Num_descargas_sim"));
        panel.add(txtField);
        boton = new JButton("Configurar");
        panel.add(boton);
        this.add(panel,BorderLayout.CENTER);
        
        boton.addActionListener(this);
        
        //Para cerrar la ventana
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        setLocation(0,0);
        setSize(500,100);
        setVisible(true);
        setResizable(false);
    }

    public void actualizar(Sujeto suj) {
        if (suj == this.oCtrlConfigCli){
            //Actualizo el campo con el valor cambiado
            txtField.setText(oCtrlConfigCli.obtenerPropiedad("Num_descargas_sim"));
        }
    }

//    public void mouseClicked(MouseEvent e) {
//        if (e.getComponent() == boton){
//            try {
//                //Establezco el nuevo valor de la propiedad
//                oCtrlConfigCli.establecerPropiedad("Num_descargas_sim", txtField.getText());
//            } catch (ControlConfiguracionClienteException ex) {
//                Logger.getLogger(VentanaObservador.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }

    public void actionPerformed(ActionEvent e) {
        System.out.println ((String) e.getActionCommand());
        if (((String) e.getActionCommand()).equals("Configurar")){
            try {
                //Establezco el nuevo valor de la propiedad
                oCtrlConfigCli.establecerPropiedad("Num_descargas_sim", txtField.getText());
            } catch (ControlConfiguracionClienteException ex) {
                Logger.getLogger(VentanaObservador.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
