/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package presentacion;

/**
 *
 * @author Victor
 */
public abstract class GUIGeneral {

    //Todos los eventos de actualización
	public static final int MOSTRAR_MENU = 0;
	private static GUIGeneral instancia;


	// Devuelve la instancia única de la clase
	public static GUIGeneral instancia() {
		if (instancia == null)
			instancia = new GUIGeneralImpl();
		return instancia;
	}

	// Recoge el evento de actualización
	public abstract void update(int evento, Object params);
}
