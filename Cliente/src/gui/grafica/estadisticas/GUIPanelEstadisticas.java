package gui.grafica.estadisticas;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import javax.swing.*;
/**
 * Panel de estadisticas de la aplicación.
 * 
 * @author Victor
 * @author S@L-c
 * @author Qiang
 */
public class GUIPanelEstadisticas extends JPanel {

    // CONSTANTES
    private static final long serialVersionUID = 1L;
    public static final String RUTA_RECURSOS = "src/interfaz/recursos/imagenes/estadisticas/";    // ATRIBUTOS
    private JLabel _lblEstado;
    private GUIPanelGrafica _panelSubidas;
    private GUIPanelGrafica _panelDescargas;
    private JPanel panelInfo;
    private JTable _tablaContenido;
    private JTextArea _txtEstado;
    
    private BufferedImage grafica = null;



//	************************************************************************************//
    /**
     * Constructor de la clase PanelEstadisticas.
     */
    public GUIPanelEstadisticas() {
        super();
        inciarComponentes();

    }
//	************************************************************************************//
    /**
     * Inicia los componentes del panel de estadisticas.
     */
    private void inciarComponentes() {
        GridLayout layout = new GridLayout(2,1);
        //layout.setColumns(1);
        this.setLayout(layout);
        _panelSubidas = new GUIPanelGrafica();
        _panelSubidas.setTitulo("Velocidad Media de Subida");
        _panelSubidas.setLeyendaEjeX("Tiempo");
        _panelSubidas.setLeyendaEjeY("Velocidad");
        
        _panelDescargas = new  GUIPanelGrafica();
        _panelDescargas.setTitulo("Velocidad Media de Bajada");
        _panelDescargas.setLeyendaEjeX("Tiempo");
        _panelDescargas.setLeyendaEjeY("Velocidad");
        
        panelInfo = new JPanel();
        //this.add(panelInfo);
        this.add(_panelSubidas);
        this.add(_panelDescargas);
            //_panelSubidas = new JPanel();
    }
    
     
    
    private class GraficPanel extends JPanel {
      
      public GraficPanel() {
          setBackground(Color.gray);
      }
    }
      
}
