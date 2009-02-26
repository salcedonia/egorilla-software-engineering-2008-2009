/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package presentacion.configuracion;

/**
 *
 * @author Victor
 */
public abstract class GUIConfiguracion {

    private static GUIConfiguracion instancia = null;
	//Todos los eventos de actualización
	public static final int EVENTO1 = 1;
	public static final int EVENTO2 = 2;

	// Devuelve la instancia única de la clase
	public static GUIConfiguracion instancia() {
		if (instancia == null)
			instancia = new GUIConfiguracionImpl();
		return instancia;
	}

	//Recoge los eventos de actualización
	public abstract void update(int evento, Object obj);

    //Devuelve el panel de Buscador
    public abstract ConfiguracionPanel getPanel();
}