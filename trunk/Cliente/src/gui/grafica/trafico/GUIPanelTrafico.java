/* 
	This file is part of eGorilla.

    eGorilla is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    eGorilla is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with eGorilla.  If not, see <http://www.gnu.org/licenses/>.
*/

package gui.grafica.trafico;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Panel que gestiona todas las descargas de la aplicacion.
 * 
 * @author José Miguel Guerrero
 */
public class GUIPanelTrafico extends JPanel{

    /**
     * Controlador del panel de trafico.
     */
    private ControladorPanelTrafico _controlador;
    /**
     * Scroll panel para el panel de descargas.
     */ 
    private JScrollPane _scrollPaneDescargas;
    /**
     * Panel que muestra las descargas de la aplicacion.
     */
    private PanelDescargas _panelDescargas;

    /**
     * Constructor de la clase GUIPanelTrafico.
     * 
     * @param controlador Controlador de la pestaña de trafico.
     */
    public GUIPanelTrafico(ControladorPanelTrafico controlador) {

        // Guardamos el controlador
        _controlador = controlador;

        // Iniciamos los componentes del panel
        iniciarComponentes();
    }

    /**
     * Inicia los componentes necesarios para el panel.
     */
    public void iniciarComponentes() {

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        
        // PANEL PRINCIPAL
        setBorder(BorderFactory.createTitledBorder("Trafico"));
        setLayout(new GridBagLayout());
            
        _scrollPaneDescargas = new JScrollPane();
        
        // PANEL DESCARGAS
        _panelDescargas = new PanelDescargas(_controlador);
        _panelDescargas.setMaximumSize(new Dimension(450, 20));
        _panelDescargas.setMinimumSize(new Dimension(450, 20));
        _panelDescargas.setPreferredSize(new Dimension(450, 450));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 357;
        gridBagConstraints.ipady = 163;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(17, 10, 0, 21);
        
        _scrollPaneDescargas.add(_panelDescargas);
        _scrollPaneDescargas.setViewportView(_panelDescargas);
        add(_scrollPaneDescargas, gridBagConstraints);
    }
}
