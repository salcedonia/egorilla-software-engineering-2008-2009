/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package presentacion.configuracion;

/**
 *
 * @author Victor
 */
public abstract class ConfiguracionControl {

    public static final int ACCION1 = 1;
	public static final int ACCION2 = 2;

	private static ConfiguracionControl instancia;

	// Devuelve la instancia única de la clase
	public static ConfiguracionControl instancia() {
		if (instancia == null)
			instancia = new ConfiguracionControlImpl();
		return instancia;
	}

	// Recoge el evento de actualización
	public abstract void action(int event, Object params);

	// Inicia la interfaz
	public abstract void iniciar();

}