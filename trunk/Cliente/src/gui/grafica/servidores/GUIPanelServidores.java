package gui.grafica.servidores;

import control.ControladorGrafica;
import gestorDeConfiguracion.ControlConfiguracionCliente;
import gestorDeConfiguracion.ControlConfiguracionClienteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

/**
 * Panel que gestiona los distintos servidores disponibles
 * en el cliente.
 * 
 * @author David Fernández
 */
public class GUIPanelServidores extends JPanel {

    // CONSTANTES
    private static final long serialVersionUID = 1L;    
    
    // ATRIBUTOS
    private JButton _btnConectar;
    private JButton _btnAñadir;
    private JLabel _lblListaServidores;
    private JLabel _lblDireccionIP;
    private JLabel _lblPuerto;
    private JLabel _lblActualizarViaURL;
    private JScrollPane _panelScroll;
    private JSeparator _separador;
    private JTable _tablaContenido;
    private JTextField _txtDireccionIP;
    private JTextField _txtPuerto;
    private JTextField _txtActualizarViaURL;
    private DefaultTableModel _defaultTableModel;   
    private ControladorGrafica _controlador;
    private String _serverHost;
    private Integer _sPuerto;

    /** 
     * Constructor de la clase PanelServidores.
     */
    public GUIPanelServidores() {


        iniciarComponentes();
    }

    /**
     * Inicia los componentes del panel de servidores.
     */
    private void iniciarComponentes() {

        GridBagConstraints gridBagConstraints;

        _btnConectar = new JButton();
        _btnAñadir = new JButton();
        _lblListaServidores = new JLabel();
        _lblDireccionIP = new JLabel();
        _lblPuerto = new JLabel();
        _lblActualizarViaURL = new JLabel();
        _txtDireccionIP = new JTextField();
        _txtPuerto = new JTextField();
        _txtActualizarViaURL = new JTextField();
        _separador = new JSeparator();
        _panelScroll = new JScrollPane();
        _tablaContenido = new JTable();

        setBorder(BorderFactory.createTitledBorder("Servidores"));
        setName("PanelServidores");
        setLayout(new GridBagLayout());

        _lblListaServidores.setFont(new Font("Tahoma", Font.BOLD, -11));
        _lblListaServidores.setText("Lista Servidores");
        _lblListaServidores.setName("lblListaServidores");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(20, 20, 0, 0);
        add(_lblListaServidores, gridBagConstraints);

        _panelScroll.setName("panelScroll");

        _tablaContenido.setBackground(new Color(235, 233, 237));


        _defaultTableModel = new javax.swing.table.DefaultTableModel(new Object[][]{}, new String[]{"Nombre del Servidor", "IP", "Descripción", "Usuarios", "Número máximo de usuarios", "Archivos", "Preferencia"});
        _tablaContenido = new JTable(_defaultTableModel) {
            // CONSTANTES
            private static final long serialVersionUID = 1L;
            Class[] types = new Class[]{
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false, false, false
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        };
        _tablaContenido.setName("tablaContenido");
        _panelScroll.setViewportView(_tablaContenido);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 9;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 337;
        gridBagConstraints.ipady = 183;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(6, 10, 0, 0);
        add(_panelScroll, gridBagConstraints);

        _btnConectar.setText("Conectar");
        _btnConectar.setMaximumSize(new Dimension(81, 23));
        _btnConectar.setMinimumSize(new Dimension(81, 23));
        _btnConectar.setName("btnConectar");
        _btnConectar.setPreferredSize(new Dimension(81, 23));
        _btnConectar.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                pulsacionBotonConectarServidor(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(20, 10, 7, 0);
        add(_btnConectar, gridBagConstraints);

        _txtDireccionIP.setText("");
        _txtDireccionIP.setName("txtDireccionIP");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 74;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(6, 20, 0, 0);
        add(_txtDireccionIP, gridBagConstraints);

        _lblDireccionIP.setText("Direccion IP");
        _lblDireccionIP.setName("lblDireccionIP");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(6, 20, 0, 0);
        add(_lblDireccionIP, gridBagConstraints);

        _lblPuerto.setText("Puerto");
        _lblPuerto.setName("lblPuerto");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(0, 20, 0, 0);
        add(_lblPuerto, gridBagConstraints);

        _txtPuerto.setText("");
        _txtPuerto.setName("txtPuerto");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 74;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(6, 20, 0, 0);
        add(_txtPuerto, gridBagConstraints);

        _btnAñadir.setText("Añadir a la Lista");
        _btnAñadir.setName("btnAñadir");
        _btnAñadir.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                pulsacionBotonAñadirServidor(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 20, 0, 0);
        add(_btnAñadir, gridBagConstraints);

        _separador.setName("separador");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.ipadx = 120;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(7, 20, 0, 3);
        add(_separador, gridBagConstraints);

        _lblActualizarViaURL.setText("Actualizar via URL");
        _lblActualizarViaURL.setName("jLabel4");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.ipadx = 24;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(8, 20, 0, 3);
        add(_lblActualizarViaURL, gridBagConstraints);

        _txtActualizarViaURL.setText("");
        _txtActualizarViaURL.setName("txtActualizarViaURL");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.ipadx = 104;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(6, 20, 0, 3);
        add(_txtActualizarViaURL, gridBagConstraints);

        iniciarPanelServidor();
    }

    private void iniciarPanelServidor() {
        try {
            _sPuerto = Integer.parseInt(ControlConfiguracionCliente.obtenerInstancia().obtenerPropiedad("PuertoServidor"));
            _serverHost = ControlConfiguracionCliente.obtenerInstancia().obtenerPropiedad("IpServidor");
            String nombreServidor = ControlConfiguracionCliente.obtenerInstancia().obtenerPropiedad("NombreServidor");
            String descripcion = ControlConfiguracionCliente.obtenerInstancia().obtenerPropiedad("Descripcion");

            Object[] servidor = new Object[7];
            servidor[0] = servidor[0] = nombreServidor;
            servidor[1] = _serverHost;
            servidor[2] = descripcion;
            servidor[3] = "";
            servidor[4] = "";
            servidor[5] = "";
            servidor[6] = "";
            _defaultTableModel.addRow(servidor);
        } catch (ControlConfiguracionClienteException ex) {
            Logger.getLogger(GUIPanelServidores.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void pulsacionBotonConectarServidor(ActionEvent evt) {
        try {
            _controlador.peticionConexionAServidor(_serverHost, _sPuerto);
        } catch (Exception ex) {
            Logger.getLogger(GUIPanelServidores.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void pulsacionBotonAñadirServidor(ActionEvent evt) {

        // Llamamos al control del panel
        Object[] parametros = new Object[2];
        parametros[0] = _txtPuerto.getText().trim();
        parametros[1] = _txtDireccionIP.getText().trim();
    }
}