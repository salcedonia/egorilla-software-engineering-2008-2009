/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package presentacion.compartidos;

/**
 *
 * @author Victor
 */
public abstract class GUICompartidos {

    private static GUICompartidos instancia = null;
	//Todos los eventos de actualización
	public static final int EVENTO1 = 1;
	public static final int EVENTO2 = 2;

	// Devuelve la instancia única de la clase
	public static GUICompartidos instancia() {
		if (instancia == null)
			instancia = new GUICompartidosImpl();
		return instancia;
	}

	//Recoge los eventos de actualización
	public abstract void update(int evento, Object obj);

    //Devuelve el panel de Compartidos
    public abstract CompartidosPanel getPanel();
}