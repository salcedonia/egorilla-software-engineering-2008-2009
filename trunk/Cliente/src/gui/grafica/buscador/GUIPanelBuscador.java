package gui.grafica.buscador;

import control.ControladorGrafica;
import datos.Archivo;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import peerToPeer.egorilla.GestorEgorilla;
import peerToPeer.egorilla.ObservadorGestorEgorilla;

/**
 * Panel de búsquedas de la aplicación.
 * 
 * @author  Javier Salcedo
 */
public class GUIPanelBuscador extends JPanel implements ObservadorGestorEgorilla{

    /**
     * Constante de identificador de clase
     */
    private static final long serialVersionUID = 1L;
    /**
     * Boton de comienzo de busqueda
     */
    private JButton _btnComenzar;
    /**
     * Boton que cierra todas las pestañas asociadas a las busquedas
     */
    private JButton _btnEliminar;
    /**
     * Boton que vacia el contenido del cuadro de texto donde el usuario
     * introduce el nombre del archivo que quiere buscar
     */
    private JButton _btnLimpiar;
    /**
     * Etiqueta del nombre del archivo a buscar
     */
    private JLabel _lblNombre;
    /**
     * ScrollPane que contiene el contenido de una busqueda 
     */
    private JScrollPane _panelScroll;
    /**
     * Separador
     */
    private JSeparator _separador;
    /**
     * Contiene el resultado de una busqueda compuesta por una lista de archivos
     */
    private PanelBusqueda _panelBusqueda;
    /**
     * Cuadro de texto donde el usuario introduce el nombre del archivo que 
     * quiere buscar
     */
    private JTextField _txtBusqueda;
    /**
     * Panel que contiene las pestañas de cada una de las busquedas realizadas
     */
    private JTabbedPane _panelPestanas;
    /**
     * Número de pestañas abiertas
     */
    private int _numeroPestañas = 0;
    /**
     * Vector de búsquedas realizadas
     * Cada componente representa una búsqueda de una pestaña determinada
     */
    private Vector<Archivo[]> _ultimasBusquedas;    
    /**
     * Controlador de la aplicacion en modo grafico
     */
    private ControladorGrafica _controlador;

    /** 
     * Constructor de la clase PanelBusquedas.
     * 
     * @param controlador Controlador de la interfaz gráfica. 
     */
    public GUIPanelBuscador(ControladorGrafica controlador) {

        _controlador = controlador;
        _controlador.getGestorEGorilla().agregarObservador(this);
        _ultimasBusquedas = new Vector<Archivo[]>();
        iniciarComponentes();
    }

    /**
     * Inicia los componentes del panel de búsquedas.
     */
    private void iniciarComponentes() {

        GridBagConstraints gridBagConstraints;

        _lblNombre = new JLabel();
        _txtBusqueda = new JTextField();
        _separador = new JSeparator();
        _btnComenzar = new JButton();
        _btnEliminar = new JButton();
        _btnLimpiar = new JButton();

        // PANEL PRINCIPAL
        setBorder(BorderFactory.createTitledBorder("Buscador"));
        setName("PanelBuscador");
        setLayout(new GridBagLayout());

        // LABEL NOMBRE
        _lblNombre.setFont(new Font("Tahoma", Font.BOLD, 11));
        _lblNombre.setText("Nombre: ");
        _lblNombre.setName("lblNombre");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(10, 30, 0, 0);
        add(_lblNombre, gridBagConstraints);

        // TEXTBOX BUSQUEDAS
        _txtBusqueda.setText("");
        _txtBusqueda.setMinimumSize(new Dimension(100, 20));
        _txtBusqueda.setMaximumSize(new Dimension(100, 20));
        _txtBusqueda.setPreferredSize(new Dimension(100, 20));
        _txtBusqueda.setName("txtBusqueda");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(0, 30, 0, 0);
        add(_txtBusqueda, gridBagConstraints);

        // BOTON COMENZAR
        _btnComenzar.setText("Comenzar");
        _btnComenzar.setName("btnComenzar");
        _btnComenzar.setMaximumSize(new Dimension(100, 20));
        _btnComenzar.setMinimumSize(new Dimension(100, 20));
        _btnComenzar.setPreferredSize(new Dimension(100, 20));
        _btnComenzar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                pulsacionBotonComenzar(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(0, 200, 0, 0);
        add(_btnComenzar, gridBagConstraints);

        // BOTON LIMPIAR
        _btnLimpiar.setText("Limpiar");
        _btnLimpiar.setName("btnLimpiar");
        _btnLimpiar.setMaximumSize(new Dimension(100, 20));
        _btnLimpiar.setMinimumSize(new Dimension(100, 20));
        _btnLimpiar.setPreferredSize(new Dimension(100, 20));
        _btnLimpiar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                pulsacionBotonLimpiar(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(15, 200, 0, 0);
        add(_btnLimpiar, gridBagConstraints);

        // SEPARADOR
        _separador.setName("separador");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(50, 10, 0, 0);
        add(_separador, gridBagConstraints);

        // BOTON ELIMINAR
        _btnEliminar.setText("Eliminar Todo");
        _btnEliminar.setName("btnEliminar");
        _btnEliminar.setMaximumSize(new Dimension(150, 20));
        _btnEliminar.setMinimumSize(new Dimension(150, 20));
        _btnEliminar.setPreferredSize(new Dimension(150, 20));
        _btnEliminar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                pulsacionBotonEliminar(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(20, 10, 10, 10);
        add(_btnEliminar, gridBagConstraints);

        // PANEL CON PESTAÑAS
        _panelPestanas = new JTabbedPane();
        _panelPestanas.setBackground(Color.WHITE);
        _panelPestanas.setMaximumSize(new Dimension(450, 20));
        _panelPestanas.setMinimumSize(new Dimension(450, 20));
        _panelPestanas.setPreferredSize(new Dimension(450, 450));

        // Para que aparezcan las flechas de desplazamiento cuando no quepan las pestañas
        _panelPestanas.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 19;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 357;
        gridBagConstraints.ipady = 163;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(17, 10, 0, 21);
        add(_panelPestanas, gridBagConstraints);

        getAccessibleContext().setAccessibleName("Buscador");
    }

    /**
     * Lanza la consulta al servidor leyendo el contenido del textbox.
     * 
     * @param evt Evento de pulsación del ratón.
     */
    private void pulsacionBotonComenzar(ActionEvent evt) {

        if (_controlador.conectado()) {
            if (!_txtBusqueda.getText().matches("")) {
                _controlador.peticionBuscarFichero(_txtBusqueda.getText());
            } else {
                mostrarErrorNombreNoIntroducido();
            }
        } else {
            mostrarErrorNoConetadoAServidor();
        }
    }

    /**
     * Vacía la tabla de contenidos donde se cargan todos los resultados de las búsquedas
     * asociadas.
     * 
     * @param evt Evento de pulsación de ratón.
     */
    private void pulsacionBotonEliminar(ActionEvent evt) {

        // Cerramos todas las pestañas activas
        _panelPestanas.removeAll();
        setNumeroNuevos(0);
    }

    /**
     * Vacía el campo de texto donde se introducen los nombres de los archivos solicitados.
     * 
     * @param evt Evento de pulsación de ratón.
     */
    private void pulsacionBotonLimpiar(ActionEvent evt) {

        // Vaciamos el contenido del textbox
        _txtBusqueda.setText("");
    }

    /**
     * Muestra un mensaje de error informando que no se ha conectado a ningún servidor.
     */
    private void mostrarErrorNoConetadoAServidor() {

        JOptionPane.showMessageDialog(null,
                "¡No se ha conectado a ningún servidor!",
                "Error conexión",
                JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Muestra el mensaje de error informando que no se ha introducido el nombre
     * del lista a buscar.
     */
    private void mostrarErrorNombreNoIntroducido() {

        JOptionPane.showMessageDialog(null,
                "Debe especificar un nombre para el archivo",
                "Búsqueda no informada",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Muestra el mensaje informando que la busqueda ha concluido sin coincidencias.
     */
    private void mostrarMensajeBusquedaSinCoincidencias() {

        JOptionPane.showMessageDialog(null,
                "No se han encontrado coincidencias para el archivo especificado",
                "Busqueda sin resultados",
                JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Inserta las filas en la tabla como resultado de la búsqueda. 
     * La tabla va dentro del _panelScroll y éste a su vez es el contenido de la 
     * nueva pestaña que se abre en el _panelPestanas.
     * 
     * @param lista Lista de archivos a insertar.
     */
    private void mostrarResultadoBusqueda(Archivo[] lista) {

        // Creamos el contenido
        _panelBusqueda = new PanelBusqueda(_controlador, lista);
        // Registramos como observador del almacen de descargas 
        _controlador.getGestorEGorilla().getAlmacenDescargas().agregarObservador(_panelBusqueda);
        
        // PANEL DE SCROLL
        _panelScroll = new JScrollPane();
        _panelScroll.setName("panelScroll");
        _panelScroll.setBackground(Color.WHITE);
        _panelScroll.add(_panelBusqueda);
        _panelScroll.setViewportView(_panelBusqueda);

        // Hay una nueva pestaña
        setNumeroNuevos(getNumeroNuevos() + 1);

        // Agregamos el panel de contenido, una nueva ficha
        _panelPestanas.addTab(_txtBusqueda.getText(), _panelScroll);

        // Trasladamos el enfoque a esta nueva ficha
        _panelPestanas.setSelectedIndex(_panelPestanas.getTabCount() - 1);

        //Creamos nuestra pestaña personalizada
        PestanaPanelBuscador pestanaPanelBuscador = new PestanaPanelBuscador(_panelPestanas);

        //Agregamos la pestaña creada a nuestra ficha
        _panelPestanas.setTabComponentAt(_panelPestanas.getTabCount() - 1, pestanaPanelBuscador);
    }

    /**
     * Devuelve el número de pestañas activas.
     * 
     * @return El número de pestañas activas.
     */
    public int getNumeroNuevos() {

        return _numeroPestañas;
    }

    /**
     * Establece el número de pestañas activas a valor numeroNuevos.
     * 
     * @param numeroNuevos Nuevo valor del número de pestañas activas.
     */
    public void setNumeroNuevos(int numeroNuevos) {

        _numeroPestañas = numeroNuevos;
    }

    //------------------------------------------\\
    //      INTERFACE OBSERVADOREGORILLA        \\
    //------------------------------------------\\
    /**
     * La conexion con el servidor ha sido completada.
     *
     * @param gestorEGorilla GestorEGorilla de la aplicación.
     * @param ip IP del servidor.
     * @param puerto Puerto del servidor.
     */
    @Override
    public void conexionCompletada(GestorEgorilla gestorEGorilla, String ip, int port) {
    }

    /**
     * Se ha completado la desconexion con el servidor.
     * 
     * @param gestorEGorilla GestorEGorilla de la aplicación.
     */
    @Override
    public void desconexionCompletada(GestorEgorilla gestorEGorilla) {
    }

    /**
     * Se han recibido los resultados de la busqueda.
     *
     * @param gestorEGorilla GestorEGorilla de la aplicación.
     * @param nombre Nombre del lista a buscar.
     * @param lista Lista de archivos de la última búsqueda.
     */
    @Override
    public void resultadosBusqueda(GestorEgorilla gestorEGorilla, String cad, Archivo[] lista) {

        if (lista.length != 0) {

            // Guardo la búsqueda en el vector de busquedas
            _ultimasBusquedas.add(lista);
            mostrarResultadoBusqueda((Archivo[]) lista);
        } else {
            mostrarMensajeBusquedaSinCoincidencias();
        }
    }

    /**
     * La descarga de un lista ha sido completada.
     * 
     * @param gestorEGorilla GestorEGorilla de la aplicación.
     */
    @Override
    public void finDescarga(GestorEgorilla obj) {
    }

    /**
     * La conexion con el servidor se ha perdido.
     *
     * @param gestorEGorilla GestorEGorilla de la aplicación.
     */
    @Override
    public void perdidaConexion(GestorEgorilla obj) {
    }
}
