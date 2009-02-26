/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package presentacion.descargas;

/**
 *
 * @author Victor
 */
public abstract class GUIDescargas {

    private static GUIDescargas instancia = null;
	//Todos los eventos de actualización
	public static final int EVENTO1 = 1;
	public static final int EVENTO2 = 2;

	// Devuelve la instancia única de la clase
	public static GUIDescargas instancia() {
		if (instancia == null)
			instancia = new GUIDescargasImpl();
		return instancia;
	}

	//Recoge los eventos de actualización
	public abstract void update(int evento, Object obj);

    //Devuelve el panel de Buscador
    public abstract DescargasPanel getPanel();
}