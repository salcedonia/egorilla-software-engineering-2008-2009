package gui.grafica.servidores;

import gestorDeConfiguracion.ControlConfiguracionCliente;
import gestorDeConfiguracion.ControlConfiguracionClienteException;
import gestorDeConfiguracion.InfoServidor;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Panel que gestiona los distintos servidores disponibles
 * en el cliente.
 * 
 * @author David Fernández, Javier Salcedo
 */
public class GUIPanelServidores extends JPanel implements ObservadorPanelServidores {

    /**
     * Indentificador de la clase.
     */
    private static final long serialVersionUID = 1L;
    /**
     * Boton que solicita la peticion de conexion al servidor seleccionado
     * en la lista de servidores disponibles al controlador del panel de servidores.
     */
    private JButton _btnConectar;
    /**
     * Boton que aniade el servidor a la lista de servidores disponibles
     * en la aplicacion.
     */
    private JButton _btnAnadir;
    /**
     * Boton que elimina el servidor de la lista de servidores disponibles en
     * la aplicacion.
     */
    private JButton _btnEliminar;
    /**
     * Limpia los datos del formulario en el que el usuario introduce los 
     * datos del servidor que quiere añadir a la lista.
     */
    private JButton _btnLimpiarDatos;
    /**
     * Etiqueta de direccion IP del servidor a aniadir.
     */
    private JLabel _lblDireccionIP;
    /**
     * Etiqueta de puerto del servidor a aniadir.
     */
    private JLabel _lblPuerto;
    /**
     * Etiqueta de nombre del servidor a aniadir.
     */
    private JLabel _lblNombre;
    /**
     * Etiqueta de descripcion del servidor a aniadir.
     */
    private JLabel _lblDescripcion;
    /**
     * Scroll panel que contiene a la lista de servidores disponibles.
     */
    private JScrollPane _scrollPaneListaServidores;
    /**
     * Campo de texto donde el usuario introduce la direccion IP del servidor
     * a aniadir.
     */
    private JTextField _txtDireccionIP;
    /**
     * Campo de texto donde el usuario introduce el puerto del servidor
     * a aniadir.
     */
    private JTextField _txtPuerto;
    /**
     * Campo de texto donde el usuario introduce el nombre del servidor
     * a aniadir.
     */
    private JTextField _txtNombre;
    /**
     * Campo de texto donde el usuario introduce la descripcion del servidor
     * a aniadir.
     */
    private JTextField _txtDescripcion;
    /**
     * Separador.
     */
    private JSeparator _separador;
    /**
     * Controlador del panel de servidores.
     */
    private ControladorPanelServidores _controlador;
    /**
     * Panel que contiene la lista de servidores disponibles en la aplicacion.
     */
    private PanelServidores _panelServidores;
    /**
     * Informacion del servidor seleccionado en el panel de servidores.
     */ 
    private InfoServidor _servidorSeleccionado;

    /** 
     * Constructor de la clase PanelServidores.
     * 
     * @param controladorPanelServidores Controlador del panel de servidores.
     */
    public GUIPanelServidores(ControladorPanelServidores controlador) {

        _controlador = controlador;
        iniciarComponentes();
    }

    /**
     * Inicia los componentes del panel de servidores.
     */
    private void iniciarComponentes() {

        GridBagConstraints gridBagConstraints;

        _btnConectar = new JButton();
        _btnAnadir = new JButton();
        _btnEliminar = new JButton();
        _btnLimpiarDatos = new JButton();
        _lblNombre = new JLabel();
        _lblDireccionIP = new JLabel();
        _lblPuerto = new JLabel();
        _lblDescripcion = new JLabel();
        _txtNombre = new JTextField();
        _txtDireccionIP = new JTextField();
        _txtPuerto = new JTextField();
        _txtDescripcion = new JTextField();
        _scrollPaneListaServidores = new JScrollPane();
        _separador = new JSeparator();

        setBorder(BorderFactory.createTitledBorder("Servidores"));
        setLayout(new GridBagLayout());

        // ETIQUETA DIRECCION IP
        _lblDireccionIP.setText("IP Servidor");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 20, 0, 0);
        add(_lblDireccionIP, gridBagConstraints);

        // TEXTO DIRECCION IP
        _txtDireccionIP.setText("");
        _txtDireccionIP.setColumns(15);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 150, 0, 0);
        add(_txtDireccionIP, gridBagConstraints);

        // ETIQUETA PUERTO
        _lblPuerto.setText("Puerto Servidor");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 20, 0, 0);
        add(_lblPuerto, gridBagConstraints);

        // TEXTO PUERTO
        _txtPuerto.setText("");
        _txtPuerto.setColumns(4);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 150, 0, 0);
        add(_txtPuerto, gridBagConstraints);

        // ETIQUETA NOMBRE
        _lblNombre.setText("Nombre Servidor");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 20, 0, 0);
        add(_lblNombre, gridBagConstraints);

        // TEXTO NOMBRE
        _txtNombre.setText("");
        _txtNombre.setColumns(20);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 150, 0, 0);
        add(_txtNombre, gridBagConstraints);

        // ETIQUETA DESCRIPCION
        _lblDescripcion.setText("Descripcion Servidor");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 20, 0, 0);
        add(_lblDescripcion, gridBagConstraints);

        // TEXTO DESCRIPCION
        _txtDescripcion.setText("");
        _txtDescripcion.setColumns(20);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 150, 0, 0);
        add(_txtDescripcion, gridBagConstraints);

        // BOTON AÑADIR
        _btnAnadir.setText("Añadir a la Lista de Servidores");
        _btnAnadir.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    pulsacionBotonAnadirServidor(evt);
                } catch (ControlConfiguracionClienteException ex) {
                }
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        add(_btnAnadir, gridBagConstraints);

        // BOTON LIMPIAR DATOS
        _btnLimpiarDatos.setText("Limpiar Datos del Servidor");
        _btnLimpiarDatos.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                pulsacionBotonLimpiarDatosServidor(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(0, 0, 0, 0);
        add(_btnLimpiarDatos, gridBagConstraints);

        // SEPARADOR
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.ipadx = 700;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(20, 10, 0, 0);
        add(_separador, gridBagConstraints);

        // PANEL DE SERVIDORES
        _panelServidores = new PanelServidores(_controlador);
        _panelServidores.addObservador(this);
        _scrollPaneListaServidores.add(_panelServidores);
        _scrollPaneListaServidores.setViewportView(_panelServidores);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(20, 10, 0, 0);
        add(_scrollPaneListaServidores, gridBagConstraints);

        // BOTON CONECTAR
        _btnConectar.setText("Conectar al Servidor");
        _btnConectar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                pulsacionBotonConectarServidor(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(20, 10, 10, 10);
        add(_btnConectar, gridBagConstraints);

        // BOTON ELIMINAR
        _btnEliminar.setText("Eliminar de la Lista de Servidores");
        _btnEliminar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                pulsacionBotonEliminarServidor(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.insets = new Insets(20, 250, 10, 10);
        add(_btnEliminar, gridBagConstraints);
    }

    /**
     * Pulsacion del boton de añadir un nuevo servidor. Le indica al panel 
     * de servidores que añada un nuevo servidor a su lista.
     * 
     * @param evt Evento de pulsacion.
     */
    private void pulsacionBotonAnadirServidor(ActionEvent evt) throws ControlConfiguracionClienteException {

        // Si los datos introducidos son validos
        if (datosIntroducidosOk()) {

            try {

                InfoServidor infoServidor = new InfoServidor(_txtNombre.getText().trim(), _txtDireccionIP.getText().trim(), Integer.parseInt(_txtPuerto.getText().trim()), _txtDescripcion.getText().trim());
                // Añadimos el servidor a la lista de servidores
                ControlConfiguracionCliente.obtenerInstancia().anadirServidor(infoServidor);

                // Aniadimos el servidor al panel
                _panelServidores.anadirServidor(infoServidor);
                _scrollPaneListaServidores.add(_panelServidores);
                _scrollPaneListaServidores.setViewportView(_panelServidores);
                repaint();
                
            } catch (NumberFormatException ex) {

                mostrarMensajeError("El Puerto Servidor no es un valor numérico");
            } catch (ControlConfiguracionClienteException ex) {

                mostrarMensajeError("Se ha producido un fallo al añadir el Servidor a la Lista de Servidores: \n\t" + ex.getMessage());
            }
        }
    }

    /**
     * Valida los datos del nuevo servidor introducidos en el formulario por el usuario.
     * 
     * @return Verdadero si todos los datos introducidos son validos y falso
     * en caso contrario.
     */
    private boolean datosIntroducidosOk() {

        // Añadimos el nuevo servidor al panel
        if (_txtDireccionIP.getText().matches("")) {
            mostrarMensajeError("Introduce la dirección IP del servidor");
            return false;
        } else if (_txtPuerto.getText().matches("")) {
            mostrarMensajeError("Introduce el puerto del servidor");
            return false;
        } else if (_txtNombre.getText().matches("")) {
            mostrarMensajeError("Introduce el nombre del servidor");
            return false;
        } else if (_txtDescripcion.getText().matches("")) {
            mostrarMensajeError("Introduce la descripcion del servidor");
            return false;
        }
        return true;
    }

    /**
     * Limpia el contenido de los JField donde el usuario introduce los datos
     * del nuevo servidor que quiere añadir.
     * 
     * @param evt Evento del raton.
     */
    private void pulsacionBotonLimpiarDatosServidor(ActionEvent evt) {

        _txtDescripcion.setText("");
        _txtDireccionIP.setText("");
        _txtNombre.setText("");
        _txtPuerto.setText("");
    }

    /**
     * Metodo que se ejecuta cuando se pulsa el boton de conectar.
     * 
     * @param evt Evento de pulsacion del boton.
     */
    private void pulsacionBotonConectarServidor(ActionEvent evt) {

        // Si se ha seleccionado un servidor primero
        if (_servidorSeleccionado != null) {

            try {

                _controlador.peticionConexionAServidor(_servidorSeleccionado.getDireccionIP(), _servidorSeleccionado.getPuerto());
            } catch (Exception ex) {

                JOptionPane.showMessageDialog(null, "Error de conexión",
                        "Error al conectarse al servidor",
                        JOptionPane.ERROR_MESSAGE);
            }
        } else {
            mostrarMensajeError("Selecciona un servidor primero");
        }
    }

    /**
     * Metodo que se ejecuta cuando se pulsa el boton de eliminar servidor.
     * 
     * @param evt Evento de pulsacion del boton.
     */
    private void pulsacionBotonEliminarServidor(ActionEvent evt) {

        try {

            // Eliminamos el servidor de la lista de servidores
            ControlConfiguracionCliente.obtenerInstancia().eliminarServidor(_servidorSeleccionado);

            // Eliminamos el servidor del panel
            _panelServidores.eliminarServidor(_servidorSeleccionado);
            _scrollPaneListaServidores.add(_panelServidores);
            _scrollPaneListaServidores.setViewportView(_panelServidores);
            repaint();
        } catch (ControlConfiguracionClienteException ex) {

            mostrarMensajeError("Se ha producido un fallo al añadir el Servidor a la Lista de Servidores");
        }
    }

    /**
     * Muestra un Mensaje alertando de un error en la introducción de los datos.
     * 
     * @param mensaje Mensaje de error a mostrar.
     */
    private void mostrarMensajeError(String mensaje) {

        JOptionPane.showMessageDialog(null,
                mensaje,
                "Error al introducir los datos del servidor",
                JOptionPane.ERROR_MESSAGE);
    }

    //------------------------------------------------\\
    //      INTERFACE OBSERVADORPANELSERVIDORES       \\
    //------------------------------------------------\\
    @Override
    public void servidorSeleccionado(InfoServidor servidorSeleccionado) {

        _servidorSeleccionado = servidorSeleccionado;
    }
}
