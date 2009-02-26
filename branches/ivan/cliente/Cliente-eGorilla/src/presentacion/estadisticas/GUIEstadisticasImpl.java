/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package presentacion.estadisticas;

/**
 *
 * @author Victor
 */
public class GUIEstadisticasImpl extends GUIEstadisticas{

    private EstadisticasPanel CompartidosPanel;

	public GUIEstadisticasImpl() {
		CompartidosPanel = new EstadisticasPanel();
	}

	@Override
	public void update(int event, Object param) {
		CompartidosPanel.update(event, param);
	}

    public EstadisticasPanel getPanel() {
		return CompartidosPanel;
	}
}