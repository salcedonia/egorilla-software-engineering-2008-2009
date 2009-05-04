package gui.grafica.estadisticas;

import gestorDeEstadisticas.GestorEstadisticas;
import gestorDeEstadisticas.UtilFechas;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import org.apache.log4j.Logger;

/**
 * Panel de estadisticas de la aplicación.
 * 
 * @author Victor
 * @author S@L-c
 * @author Qiang
 */
public class GUIPanelEstadisticas extends JSplitPane {

    // CONSTANTES
    private static final long serialVersionUID = 1L;
    public static final String RUTA_RECURSOS = "src/interfaz/recursos/imagenes/estadisticas/";    // ATRIBUTOS
    private PanelGrafica _panelSubidas;
    private PanelGrafica _panelDescargas;
    private JPanel _panelDatos;
    JSplitPane panelGrafica;
    private GestorEstadisticas estadisticas = GestorEstadisticas.getInstacia();
    private static final Logger log = Logger.getLogger(GUIPanelEstadisticas.class);
    private boolean primerPintado = true;
//	************************************************************************************//
    /**
     * Constructor de la clase PanelEstadisticas.
     */
    public GUIPanelEstadisticas() {
        super();
        inciarComponentes();
//        log.info(this.getWidth());
//        log.info(this.getHeight());

    }

    private void customizePanelDatos() {
        _panelDatos.setBorder(javax.swing.BorderFactory.createTitledBorder("Conexión"));
        GridBagConstraints gridBagConstraints;


        String[][] listatitulos = {
            {"Cantidad de datos descargados: ", String.valueOf(estadisticas.getTotalDatosDescargaGlobal())},
            {"Datos de sesion: ", String.valueOf(estadisticas.getTotalDatosDescargaSesion())},
            {"Numero de ficheros descagados Global: ", String.valueOf(estadisticas.getFicherosDescargadosGlobal())},
            {"Numero de ficheros Sesion: ", String.valueOf(estadisticas.getFicherosDescargadosSesion())}
        };
        Label[] info;
        Label[] titulos;
//        _panelDatos.setBackground(new java.awt.Color(153, 255, 153));
//        setName("BackGround"); // NOI18N
        _panelDatos.setLayout(new GridBagLayout());
        int size = listatitulos.length;
        info = new Label[size];
        titulos = new Label[size];
        for (int cont = 0; cont < size; cont++) {
            titulos[cont] = new Label(listatitulos[cont][0]);
            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 0;
            gridBagConstraints.gridy = cont;
            _panelDatos.add(titulos[cont], gridBagConstraints);

            info[cont] = new Label(listatitulos[cont][1]);
            gridBagConstraints = new GridBagConstraints();
            gridBagConstraints.gridx = 1;
            gridBagConstraints.gridy = cont;
            _panelDatos.add(info[cont], gridBagConstraints);
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
        Dimension size = this.getSize();



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


        _panelSubidas = new PanelGrafica("Velocidad de Subida");
//        _panelSubidas.setTitulo("Velocidad Media de Subida");
//        _panelSubidas.setLeyendaEjeX("Tiempo");
//        _panelSubidas.setLeyendaEjeY("Velocidad");
        panelGrafica.setRightComponent(_panelSubidas);

//        //layout.addLayoutComponent(_panelSubidas, gbc);
//         add(_panelSubidas);
        _panelDescargas = new  PanelGrafica("Velocidad de Descarga");
//        _panelDescargas.setTitulo("Velocidad Media de Bajada");
//        _panelDescargas.setLeyendaEjeX("Tiempo");
//        _panelDescargas.setLeyendaEjeY("Velocidad");
        panelGrafica.setLeftComponent(_panelDescargas);
        int medio = (_panelDescargas.getHeight() + _panelSubidas.getHeight()) / 2;
        addComponentListener(new java.awt.event.ComponentAdapter() {

            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                primerPintado = true;
            }
        });
    }

    @Override
    public void paint(java.awt.Graphics g) {
        if (primerPintado) {
            _panelDescargas.validate();
            _panelSubidas.validate();
            int barraPaneles = this.getWidth() / 4;
            int barraGrafica = this.getHeight() / 2;
            panelGrafica.setDividerLocation(barraGrafica);
            this.setDividerLocation(barraPaneles);
//            _panelDescargas.changeImage();
//            _panelSubidas.changeImage();
            //customizePanelDatos();
            primerPintado = false;

        }
        ArrayList<Double> datosDesc = estadisticas.getListaVelocidadMediaBajadaSesion();
        Date fecha = new Date();
        ArrayList<Date> lista = new ArrayList();
        for (int cont = 0; cont < datosDesc.size(); cont++) {
            lista.add(fecha);
            fecha = UtilFechas.restarSegundos(fecha, 30);
        }
//        _panelDescargas.setEjeX(lista);
//        _panelDescargas.setEjeY(datosDesc);
        _panelDescargas.setinicio(estadisticas.getFechaInicio());
        _panelDescargas.setListaEjeX(estadisticas.getListaVelocidadMediaBajadaSesion());
        _panelDescargas.actualiza();
        _panelDescargas.repaint();
        _panelSubidas.repaint();

        super.paint(g);
    }
}
