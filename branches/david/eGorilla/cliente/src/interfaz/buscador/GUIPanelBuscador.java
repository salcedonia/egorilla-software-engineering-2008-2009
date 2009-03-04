package interfaz.buscador;

import java.awt.Font;
import java.awt.GridBagConstraints;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import logica.paquete.Archivo;


//************************************************************************************//
/**
 * Panel de búsquedas de la aplicación.
 * 
 * @author  Mnemo
 * @author S@L-c
 */
public class GUIPanelBuscador extends JPanel {

    // CONSTANTES
	private static final long serialVersionUID = 1L;
	
	// ATRIBUTOS
	private JButton _btnComenzar;
    private JButton _btnDescargar;
    private JButton _btnEliminar;
    private JButton _btnLimpiar;
    private JLabel _lblNombre;
    private JScrollPane _panelScroll;
    private JSeparator _separador;
    private JTable _tablaContenido;
    private JTextField _txtBusqueda;
    private JTabbedPane _panelPestanas;
    private int _numeroPestañas = 0; // Número de pestañas abiertas

    // CONTROL
	private ControlPanelBuscador _controlPanelBuscador;
    
//	************************************************************************************//
	/** 
	 * Constructor de la clase PanelBusquedas. 
	 */
    public GUIPanelBuscador() {
    
    	_controlPanelBuscador = new ControlPanelBuscador(this);
    	
    	iniciarComponentes();
    }

//	************************************************************************************//
   /**
    * Inicia los componentes del panel de búsquedas.
    */
    private void iniciarComponentes() {
    	
        GridBagConstraints gridBagConstraints;

        _lblNombre = new JLabel();
        _txtBusqueda = new JTextField();
        _separador = new JSeparator();
        _btnComenzar = new JButton();
        _btnDescargar = new JButton();
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
        
        // BOTON DESCARGAR
        _btnDescargar.setText("Descargar"); 
        _btnDescargar.setName("btnDescargar"); 
        _btnDescargar.setMaximumSize(new Dimension(100, 20));
        _btnDescargar.setMinimumSize(new Dimension(100, 20));
        _btnDescargar.setPreferredSize(new Dimension(100, 20));     
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(20, 10, 10, 10);
        add(_btnDescargar, gridBagConstraints);

        // BOTON ELIMINAR
        _btnEliminar.setText("Eliminar Todo");
        _btnEliminar.setName("btnEliminar");
        _btnEliminar.setMaximumSize(new Dimension(150, 20));
        _btnEliminar.setMinimumSize(new Dimension(150, 20));
        _btnEliminar.setPreferredSize(new Dimension(150, 20));
        _btnEliminar.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent evt) {
                pulsacionBotonEliminar(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(20, 90, 10, 10);
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

//	************************************************************************************//
    /**
     * Lanza la consulta al servidor leyendo el contenido del textbox.
     * 
     * @param evt Evento de pulsación del ratón.
     */
	private void pulsacionBotonComenzar(ActionEvent evt) {
		
		// Llamamos al control del panel
		_controlPanelBuscador.accionPanelBuscador(AccionPanelBuscador.BUSCAR_ARCHIVO_SERVIDOR, _txtBusqueda.getText().trim());	
	}
	
//	************************************************************************************//
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
	
//	************************************************************************************//
	/**
	 * Vacía el campo de texto donde se introducen los nombres de los archivos solicitados.
	 * 
	 * @param evt Evento de pulsación de ratón.
	 */
	private void pulsacionBotonLimpiar(ActionEvent evt) {
	
		// Vaciamos el contenido del textbox
		_txtBusqueda.setText("");
	}

//	************************************************************************************//
    /**
     * Distingue entre los distintos eventos de actualización.
     *  
	 * @param evento Evento producido.
	 * @param parametros Parametros asociados al evento.
     */
	public void tratarEvento(EventoPanelBuscador evento, Object parametros) {
		
		switch(evento){
		
			case MOSTRAR_RESULTADO_BUSQUEDA: 
				
				mostrarResultadoBusqueda((Archivo[]) parametros); 
				break;
			
			case MOSTRAR_MENSAJE_BUSQUEDA_SIN_COINCIDENCIAS: 
				
            	mostrarMensajeBusquedaSinCoincidencias();
            	break;
            	
			case MOSTRAR_ERROR_NOMBRE_NO_INTRODUCIDO: 
				                
	        	mostrarErrorNombreNoIntroducido();
				break;
			
			case MOSTRAR_ERROR_NO_CONECTADO_SERVIDOR: 
				
				mostrarErrorNoConetadoAServidor();
				break;
		}
	}

//	************************************************************************************//
	/**
	 * Muestra un mensaje de error informando que no se ha conectado a ningún servidor.
	 */
	private void mostrarErrorNoConetadoAServidor() {
		
		JOptionPane.showMessageDialog(null,
		        "¡No se ha conectado a ningún servidor!",
		        "Error conexión",
		        JOptionPane.WARNING_MESSAGE);
	}

//	************************************************************************************//
	/**
	 * Muestra el mensaje de error informando que no se ha introducido el nombre
	 * del archivo a buscar.
	 */
	private void mostrarErrorNombreNoIntroducido() {
		
		JOptionPane.showMessageDialog(null,
		                    "Debe especificar un nombre para el archivo",
		                    "Búsqueda no informada",
		                    JOptionPane.INFORMATION_MESSAGE);
	}

//	************************************************************************************//
	/**
	 * Muestra el mensaje informando que la busqueda ha concluido sin coincidencias.
	 */
	private void mostrarMensajeBusquedaSinCoincidencias() {
		
		JOptionPane.showMessageDialog(null,
		        "No se han encontrado coincidencias para el archivo especificado",
		        "Busqueda sin resultados",
		        JOptionPane.WARNING_MESSAGE);
	}
	
//	************************************************************************************//
	/**
	 * Inserta las filas en la tabla como resultado de la búsqueda. 
	 * La tabla va dentro del _panelScroll y éste a su vez es el contenido de la 
	 * nueva pestaña que se abre en el _panelPestanas.
	 * 
	 * @param archivo Archivo a insertar.
	 */
	private void mostrarResultadoBusqueda(Archivo[] archivo){

    	// Nombre de las columnas
		String[] nombreColumnas = {	"Nombre", 
									"Tamaño", 
									"Disponibilidad", 
									"Fuentes", 
									"Tipo", 
									"Identificador de archivo"};
		
		// Recuperamos los datos
    	Object datos[][] = new Object[archivo.length][nombreColumnas.length];

    	for (int i=0; i< archivo.length; i++) {
            datos[i][0] = archivo[i]._nombre;
            datos[i][1] = String.valueOf(archivo[i]._tamano) + " bytes";
            datos[i][2] = "100%";
            datos[i][3] = "1(1)";
            datos[i][4] = archivo[i]._tipo.toString();
            datos[i][5] = archivo[i]._hash;
        }
  	    
    	_tablaContenido = new JTable(datos, nombreColumnas);
		_tablaContenido.setName("tablaContenido");
		_tablaContenido.setModel(new DefaultTableModel(datos, nombreColumnas));
		_tablaContenido.setBackground(Color.WHITE);
		_tablaContenido.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Para que no se ajuste al tamaño del scroll panel
		_tablaContenido.setShowGrid(false); // Para que no se vean las líneas de división
		_tablaContenido.setAutoCreateRowSorter(true); // Para ordenar las columnas
		_tablaContenido.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		_tablaContenido.setRowMargin(5);
		_tablaContenido.setRowHeight(20);
		
		// Damos un ancho a las columnas del JTable y centramos su contenido
		for(int i=0; i < _tablaContenido.getModel().getColumnCount(); i++ ){
			
			TableColumn columna = _tablaContenido.getColumnModel().getColumn(i);
			DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();
			
			switch(i){
			
				// Nombre
				case 0: tableCellRenderer.setHorizontalAlignment(SwingConstants.LEFT);
						columna.setCellRenderer(tableCellRenderer);
						columna.setPreferredWidth(375); break;
				
				// Tamaño
				case 1: tableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
						columna.setCellRenderer(tableCellRenderer);
						columna.setPreferredWidth(100); break;
				
				// Disponibilidad
				case 2: tableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
						columna.setCellRenderer(tableCellRenderer);
						columna.setPreferredWidth(100); break;
				
				// Fuentes
				case 3: tableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
						columna.setCellRenderer(tableCellRenderer);
						columna.setPreferredWidth(75); break;
				
				// Tipo
				case 4: tableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
						columna.setCellRenderer(tableCellRenderer);
						columna.setPreferredWidth(100); break;
				
				// Hash
				case 5: tableCellRenderer.setHorizontalAlignment(SwingConstants.LEFT);
						columna.setCellRenderer(tableCellRenderer);
						columna.setPreferredWidth(225); break;
			}
		}
		
    	// PANEL DE SCROLL
		_panelScroll = new JScrollPane();
        _panelScroll.setName("panelScroll");
        _panelScroll.setBackground(Color.WHITE);
        _panelScroll.add(_tablaContenido);
        _panelScroll.setViewportView(_tablaContenido);
        
        // Hay una nueva pestaña
        setNumeroNuevos(getNumeroNuevos()+1);
        
        // Agregamos el panel de contenido, una nueva ficha
        _panelPestanas.addTab(_txtBusqueda.getText(), _panelScroll);
        
        // Trasladamos el enfoque a esta nueva ficha
        _panelPestanas.setSelectedIndex(_panelPestanas.getTabCount()-1);
        
        //Creamos nuestra pestaña personalizada
        PestanaPanelBuscador pestanaPanelBuscador = new PestanaPanelBuscador(_panelPestanas);
        
        //Agregamos la pestaña creada a nuestra ficha
        _panelPestanas.setTabComponentAt(_panelPestanas.getTabCount()-1, pestanaPanelBuscador);
    }
	    
//	************************************************************************************//
    /**
     * Devuelve el número de pestañas activas.
     * 
     * @return El número de pestañas activas.
     */
    public int getNumeroNuevos(){
    	
        return _numeroPestañas;
    }

//	************************************************************************************//
    /**
     * Establece el número de pestañas activas a valor numeroNuevos.
     * 
     * @param numeroNuevos Nuevo valor del número de pestañas activas.
     */
    public void setNumeroNuevos(int numeroNuevos){
    	
        _numeroPestañas = numeroNuevos;
    }
}
