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

    private static GUIGeneral instancia;

    //Todos los eventos de actualización
	public static final int MOSTRAR_MENU = 0;
	public static final int MOSTRAR_CONECTADO = 1;
	public static final int MOSTRAR_DESCONECTADO = 2;


	// Devuelve la instancia única de la clase
	public static GUIGeneral instancia() {
		if (instancia == null)
			instancia = new GUIGeneralImpl();
		return instancia;
	}

	// Recoge el evento de actualización
	public abstract void update(int evento, Object params);
}
