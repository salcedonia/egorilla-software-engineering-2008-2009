/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gui.grafica.estadisticas;

import gestorDeEstadisticas.UtilFechas;
import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JPanel;
import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;

/**
 * Panel donde se dibujan las graficas que muestran las velocidades medias.
 * @author Qiang
 */
public class PanelGrafica {

    private final static String LEYENDA_X = "TIEMPO";
    private final static String LEYENDA_Y = "VELOCIDAD";
    private String title;
    private Date fechaInicio;
    private ArrayList<Double> listaVelocidad;
    private  JFreeChart chartSet;
    private  TimeSeriesCollection dataset = new TimeSeriesCollection();
    private static final Logger log = Logger.getLogger(PanelGrafica.class);
    ChartPanel panel;
    /**
     * Constructor.
     * @param titulo. Título de la grafica
     */
    public PanelGrafica(String titulo) {
        //JFreeChart chart = ;
        //super(createChart(titulo));
        title = titulo;
        chartSet = createChart();
        panel = new ChartPanel(chartSet);
        title = titulo;
        listaVelocidad = new ArrayList<Double>();
        panel.setFillZoomRectangle(true);
        panel.setMouseWheelEnabled(true);
    }

    /**
     * Crea un objeto JFreechart con el titulo pasado por parametro con 
     * leyenda en el eje X  siendo el tiempo y la leyenda de eje Y  la velocidad.
     * La grafica creada es vacía.
     *
     * @param title. Titulo de la grafica.
     *
     * @return A chart.
     */
    private  JFreeChart createChart() {

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                title, // title
                LEYENDA_X, // x-axis label
                LEYENDA_Y, // y-axis label
                dataset, // data
                true, // create legend?
                true, // generate tooltips?
                false // generate URLs?
                );

        chart.setBackgroundPaint(Color.white);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);

        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setBaseShapesVisible(true);
            renderer.setBaseShapesFilled(true);
            renderer.setDrawSeriesLineAsPath(true);
        }

        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("h:mm"));
        chartSet = chart;
        return chart;

    }

    /**
     * Creates a dataset, Crea el conjunto de datos a mostrar.
     *
     * @return The dataset.
     */
    private XYDataset createDataset() {
        List series = dataset.getSeries();
        TimeSeries s1 = null;
        if (series == null || series.isEmpty()) {
            s1 = new TimeSeries("");
        } else {
            s1 = (TimeSeries) series.get(0);
        }
        Date fechaInicial;
        Minute hora;
        if (fechaInicio != null) {
            fechaInicial = fechaInicio;
            for (Double valor : listaVelocidad) {
                 hora = new Minute(fechaInicial);
                s1.addOrUpdate(hora, valor);
                fechaInicial = UtilFechas.sumarSegundos(fechaInicial, 30);
            }
        }


        dataset.removeAllSeries();
        dataset.addSeries(s1);
//        dataset.addSeries(s2);

        return dataset;

    }
    
    /**
     * Se introduce la lista de valores del eje Y.
     * @param lista
     */
    public void setListaEjeX(ArrayList<Double> lista) {
        listaVelocidad.clear();
        listaVelocidad.addAll(lista);
    }

    /**
     * Fija el inicio del eje X
     * @param inicio
     */
     
    public void setinicio(Date inicio) {
        fechaInicio = inicio;
    }

    /**
     * Actuliza los datos para el refresco.
     */
    public void actualiza() {
        createDataset();
    }
    
    public JPanel getPanel() {
        return panel;
    }
}
