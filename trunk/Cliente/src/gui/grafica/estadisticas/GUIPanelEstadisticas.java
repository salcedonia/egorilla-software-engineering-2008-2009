package gui.grafica.estadisticas;

import gestorDeEstadisticas.GestorEstadisticas;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import org.apache.log4j.Logger;

/**
 * Panel de estadisticas de la aplicación.
 * 
 * @author Qiang
 * @author Victor
 * @author S@L-c
 */
public class GUIPanelEstadisticas extends JSplitPane {

    // CONSTANTES
    private static final long serialVersionUID = 1L;
    public static final String RUTA_RECURSOS = "src/interfaz/recursos/imagenes/estadisticas/";    // ATRIBUTOS
    private static final Logger log = Logger.getLogger(GUIPanelEstadisticas.class);
    private PanelGrafica _panelSubidas;
    private PanelGrafica _panelDescargas;
    private JPanel _panelDatos;
    private JSplitPane panelGrafica;
    private GestorEstadisticas estadisticas = GestorEstadisticas.getInstacia();
    private boolean primerPintado = true;
    private JLabel[] info;
    private JLabel[] titulos;
    private JButton refresco;
//	************************************************************************************//
    /**
     * Constructor de la clase PanelEstadisticas.
     */
    public GUIPanelEstadisticas() {
        super();
        inciarComponentes();

    }

    private void customizePanelDatos() {
        _panelDatos.setBorder(BorderFactory.createTitledBorder("Datos de descarga"));
        GridBagConstraints gridBagConstraints;
        String[][] listatitulos = {
            {"Total de datos descargados: ", String.valueOf(estadisticas.getTotalDatosDescargaGlobal())},
            {"Datos descargados en la sesion: ", String.valueOf(estadisticas.getTotalDatosDescargaSesion())},
            {"Numero de ficheros descagados Global: ", String.valueOf(estadisticas.getFicherosDescargadosGlobal())},
            {"Numero de ficheros Sesion: ", String.valueOf(estadisticas.getFicherosDescargadosSesion())},
            {"Total de datos enviados : ", String.valueOf(estadisticas.getTotalDatosSubidaGlobal())},
            {"Datos enviados en la Sesion: ", String.valueOf(estadisticas.getTotalDatosSubidaSesion())}
        };
        _panelDatos.setLayout(new GridBagLayout());
        int size = listatitulos.length;
        info = new JLabel[size];
        titulos = new JLabel[size];
        int cont = 0;
        for (cont = 0; cont < size; cont++) {
            titulos[cont] = new JLabel(listatitulos[cont][0]);
            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.anchor = GridBagConstraints.WEST;
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = cont;
            gridBagConstraints.insets = new Insets(20, 0, 20, 0);
            _panelDatos.add(titulos[cont], gridBagConstraints);

            info[cont] = new JLabel(listatitulos[cont][1]);
            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = cont;
            gridBagConstraints.insets = new Insets(20, 0, 20, 0);
            _panelDatos.add(info[cont], gridBagConstraints);

        }
        refresco = new JButton("Refrescar");
        refresco.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                actualizaPanelDatos();
                _panelDescargas.setinicio(estadisticas.getFechaInicio());
                _panelDescargas.setListaEjeX(estadisticas.getListaVelocidadMediaBajadaSesion());
                _panelSubidas.setinicio(estadisticas.getFechaInicio());
                _panelSubidas.setListaEjeX(estadisticas.getListaVelocidadMediaSubidaSesion());
                _panelSubidas.actualiza();
                _panelDescargas.actualiza();
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = cont;
        gridBagConstraints.insets = new Insets(20, 0, 20, 0);
        _panelDatos.add(refresco, gridBagConstraints);
    }

    private void actualizaPanelDatos() {
        String[] listatitulos = {
            String.valueOf(estadisticas.getTotalDatosDescargaGlobal()),
            String.valueOf(estadisticas.getTotalDatosDescargaSesion()),
            String.valueOf(estadisticas.getFicherosDescargadosGlobal()),
            String.valueOf(estadisticas.getFicherosDescargadosSesion()),
            String.valueOf(estadisticas.getTotalDatosSubidaGlobal()),
            String.valueOf(estadisticas.getTotalDatosSubidaSesion())
        };
        for (int cont = 0; cont < listatitulos.length; cont++) {
            info[cont].setText(listatitulos[cont]);
        }
    }
//	************************************************************************************//
    /**
     * Inicia los componentes del panel de estadisticas.
     */
    private void inciarComponentes() {
        this.setName("Estadisticas");
        this.setEnabled(true);
        this.setDoubleBuffered(true);
        _panelDatos = new JPanel();
        GroupLayout panelIzq = new GroupLayout(_panelDatos);
        _panelDatos.setLayout(panelIzq);
        panelIzq.setHorizontalGroup(
                panelIzq.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 100, Short.MAX_VALUE));
        panelIzq.setVerticalGroup(
                panelIzq.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGap(0, 377, Short.MAX_VALUE));
        customizePanelDatos();
        this.setLeftComponent(_panelDatos);
        panelGrafica = new JSplitPane();
        panelGrafica.setLayout(new GridLayout(1, 0));
        double valur = this.getPreferredSize().getWidth();
        int loc = Math.round(Math.round(valur));
        log.info("valor de loc : " + loc);
        panelGrafica.setDividerLocation(loc);
        panelGrafica.setName("Estadisticas");
        panelGrafica.setEnabled(false);
        panelGrafica.setOrientation(JSplitPane.VERTICAL_SPLIT);
        this.setRightComponent(panelGrafica);
        this.setDividerLocation(319);
        this.setEnabled(false);
        _panelSubidas = new PanelGrafica("Velocidad de Subida");
        panelGrafica.setRightComponent(_panelSubidas.getPanel());
        _panelDescargas = new PanelGrafica("Velocidad de Descarga");
        panelGrafica.setLeftComponent(_panelDescargas.getPanel());
        addComponentListener(new java.awt.event.ComponentAdapter() {

            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                primerPintado = true;
            }
        });
    }

    @Override
    public void paint(Graphics grafico) {
        if (primerPintado) {
            int barraGrafica = this.getHeight() / 2;
            panelGrafica.setDividerLocation(barraGrafica);
            this.setDividerLocation(320);
            primerPintado = false;

        }
        _panelDescargas.setinicio(estadisticas.getFechaInicio());
        _panelDescargas.setListaEjeX(estadisticas.getListaVelocidadMediaBajadaSesion());
        _panelSubidas.setinicio(estadisticas.getFechaInicio());
        _panelSubidas.setListaEjeX(estadisticas.getListaVelocidadMediaSubidaSesion());
        _panelSubidas.actualiza();
        _panelDescargas.actualiza();
        actualizaPanelDatos();
//        _panelDescargas.getPanel().repaint();
//        _panelSubidas.repaint();

        super.paint(grafico);
    }

    @Override
    public void printAll(Graphics g) {
        super.print(g);
    }

    @Override
    public void paintAll(Graphics grafico) {
        super.printAll(grafico);
    }

    public String formatearBytes(int tam) {
        String[] unidades = {
            "Bytes",
            "Kbs",
            "Mbs",
            "Gbs"
        };
        double base = Math.log(tam) / Math.log(2);
        double entero = tam / Math.pow(10, base);

        return "";
    }
}
