/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package presentacion.buscador;

/**
 *
 * @author Victor
 */
public abstract class BuscadorControl {

    public static final int ACCION1 = 1;
	public static final int ACCION2 = 2;

	private static BuscadorControl instancia;

	// Devuelve la instancia única de la clase
	public static BuscadorControl instancia() {
		if (instancia == null)
			instancia = new BuscadorControlImpl();
		return instancia;
	}

	// Recoge el evento de actualización
	public abstract void action(int event, Object params);

	// Inicia la interfaz
	public abstract void iniciar();

}