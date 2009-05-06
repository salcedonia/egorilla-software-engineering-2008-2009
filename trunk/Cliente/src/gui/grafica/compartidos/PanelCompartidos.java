package gui.grafica.compartidos;

import datos.Archivo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import mensajes.serverclient.ListaArchivos;

/**
 * Panel que contiene los archivos de un directorio.
 * 
 * @author Javier Salcedo, Mercedes Bernal
 */
public class PanelCompartidos extends JPanel {

    /**
     * Lista de archivos que ha respondido el servidor en respuesta a la búsqueda
     */
    private ListaArchivos _archivos;
    /**
     * Lista de paneles de archivos de un directorio.
     */
    private ArrayList<ArchivoIndividual> _listaArchivos;
    /**
     * Panel principal del contenedor
     */
    private JPanel _panelPrincipal;
    /**
     * Controlador del panel buscador.
     */
    private ControladorPanelCompartidos _controlador;
    /**
     * Color de selección.
     */
    private Color _colorSeleccion = new Color(102, 204, 255);
    /**
     * Color de fondo del panel.
     */
    private Color _colorFondo = Color.WHITE;
    /**
     * Color archivo descargado.
     */
    private Color _colorDescargado = Color.RED;
    /**
     * Color del borde del panel.
     */
    private Color _colorBorde = Color.BLACK;

    /**
     * Constructor de la clase PanelBusqueda.
     * 
     * @param controlador Controlador del panel buscador.
     * @param lista Lista de archivos devuelta por el servidor.
     */
    public PanelCompartidos(ControladorPanelCompartidos controlador, ListaArchivos lista) {

        _controlador = controlador;
        _archivos = lista;
        _listaArchivos = new ArrayList<ArchivoIndividual>();
        _panelPrincipal = new JPanel();
        _panelPrincipal.setBackground(Color.WHITE);
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        add(_panelPrincipal, BorderLayout.NORTH);
        iniciarComponentes();
    }

    /**
     * Inicia los componentes del panel.
     */
    public void iniciarComponentes() {

        _panelPrincipal.setLayout(new GridLayout(0, 1, 0, 0));
        _panelPrincipal.add(new Cabecera());

        // Representa los resultados de la búsqueda
        for (int i = 0; i < _archivos.size();i++) {

            ArchivoIndividual a = new ArchivoIndividual(_archivos.get(i));

            // Guardamos la fila en el ArrayList
            _listaArchivos.add(a);
            // Creamos una nueva fila
            _panelPrincipal.add(a);
        }
    }

    /**
     * Cabecera de la tabla donde van a representarse los archivos.
     */
    private class Cabecera extends JPanel {

        /**
         * Etiqueta del nombre del archivo.
         */
        private JLabel _lblNombre;
        /**
         * Etiqueta del tamanio del archivo.
         */
        private JLabel _lblTamanio;
        /**
         * Etiqueta del tipo de archivo del archivo.
         */
        private JLabel _lblTipoArchivo;
        /**
         * Etiqueta del hash del archivo.
         */
        private JLabel _lblHash;
        /**
         * Panel principal que contiene a todos los elementos anteriores.
         */
        private JPanel _panelPrincipal;
        /**
         * Color de fuente de la cabecera.
         */
        private Color _colorFuente = Color.WHITE;
        /**
         * Color de fondo de la cabecera.
         */
        private Color _colorFondo = Color.BLUE;

        /**
         * Constructor de la clase Cabecera.
         */
        private Cabecera() {

            iniciarComponentes();
        }

        /**
         * Inicia los componentes de la cabecera.
         */
        private void iniciarComponentes() {

            setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED,
                    new Color(102, 204, 255),
                    new Color(51, 153, 255),
                    new Color(0, 0, 102),
                    new Color(0, 0, 153)));

            _panelPrincipal = new JPanel();
            _lblNombre = new JLabel("Nombre");
            _lblTamanio = new JLabel("Tamaño");
            _lblTipoArchivo = new JLabel("Tipo");
            _lblHash = new JLabel("Hash");
            _panelPrincipal.setLayout(new GridLayout(0, 4, 1, 0));
            _panelPrincipal.setBackground(_colorFondo);

            _lblNombre.setForeground(_colorFuente);
            _lblTamanio.setForeground(_colorFuente);
            _lblTipoArchivo.setForeground(_colorFuente);
            _lblHash.setForeground(_colorFuente);

            _panelPrincipal.add(_lblNombre);
            _panelPrincipal.add(_lblTamanio);
            _panelPrincipal.add(_lblTipoArchivo);
            _panelPrincipal.add(_lblHash);
            setLayout(new BorderLayout());
            add(_panelPrincipal, BorderLayout.NORTH);
        }
    }

    /**
     * Representa un archivo de una carpeta.
     */
    private class ArchivoIndividual extends JPanel {

        /**
         * Archivo que representa el archivos individual.
         */
        private Archivo _archivo;
        /**
         * Etiqueta que muestra el nombre del archivo.
         */
        private JLabel _lblNombre;
        /**
         * Etiqueta que muestra el valor del tamanio del archivo.
         */
        private JLabel _lblTamanio;
        /**
         * Etiqueta que muestra el tipo de archivo del archivo.
         */
        private JLabel _lblTipoArchivo;
        /**
         * Etiqueta que muestra el hash del archivo.
         */
        private JLabel _lblHash;
        /*
         * Para procesar los eventos del raton sobre el panel. 
         */
        private EventosRaton _eventosRaton;
        /**
         * Panel principal contenedor.
         */
        private JPanel _panelPrincipal;

        /**
         * Constructor de la clase ArchivoIndividual.
         * 
         * @param nombre Nombre del archivo.
         * @param tamanio Tamanio del archivo en bytes.
         * @param tipo Tipo del archivo.
         * @param hash Hash del archivo.
         */
        private ArchivoIndividual(Archivo archivo) {

            _archivo = archivo;

            _eventosRaton = new EventosRaton();

            _panelPrincipal = new JPanel();
            _panelPrincipal.setBorder(BorderFactory.createLineBorder(_colorFondo));
            _panelPrincipal.setBackground(_colorFondo);
            setBackground(_colorFondo);

            _panelPrincipal.addMouseListener(_eventosRaton);
            _lblNombre = new JLabel(_archivo.getNombre());
            _lblNombre.addMouseListener(_eventosRaton);
            _lblTamanio = new JLabel(Long.toString(_archivo.getSize()) + " bytes");
            _lblTamanio.addMouseListener(_eventosRaton);
            _lblTipoArchivo = new JLabel(_archivo.getTipo().name());
            _lblTipoArchivo.addMouseListener(_eventosRaton);
            _lblHash = new JLabel(_archivo.getHash());
            _lblHash.addMouseListener(_eventosRaton);

            iniciarComponentes();
        }

        /**
         * Inicia los componentes de un archivo individual
         */
        private void iniciarComponentes() {

            _panelPrincipal.setLayout(new GridLayout(0, 4, 1, 0));
            _panelPrincipal.add(_lblNombre);
            _panelPrincipal.add(_lblTamanio);
            _panelPrincipal.add(_lblTipoArchivo);
            _panelPrincipal.add(_lblHash);
            setLayout(new BorderLayout());
            add(_panelPrincipal, BorderLayout.CENTER);
            _panelPrincipal.repaint();
        }

        /**
         * Clase que implementa los métodos necesarios para procesar 
         * eventos del raton producidos sobre el panel.
         */
        private class EventosRaton extends MouseAdapter {

            @Override
            public void mouseClicked(MouseEvent evt) {

                repintar();
                // Solo se queda marcado el que ha sido seleccionado
                _panelPrincipal.setBackground(_colorSeleccion);
                _panelPrincipal.setBorder(BorderFactory.createLineBorder(_colorSeleccion));
                setBorder(BorderFactory.createLineBorder(_colorBorde));
                _panelPrincipal.repaint();
            }
        }

        /**
         * Establece el color blanco de fondo para todos los componentes
         * del panel.
         */
        private void repintar() {

            for (ArchivoIndividual a : _listaArchivos) {

                a._panelPrincipal.setBackground(_colorFondo);
                a.setBorder(BorderFactory.createLineBorder(_colorFondo));
                a._panelPrincipal.setBorder(BorderFactory.createLineBorder(_colorFondo));
                a._panelPrincipal.repaint();
            }
        }
    }
}
