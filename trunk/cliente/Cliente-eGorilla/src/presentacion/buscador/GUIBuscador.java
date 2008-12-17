/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package presentacion.buscador;

/**
 *
 * @author Victor
 */
public abstract class GUIBuscador {

    private static GUIBuscador instancia = null;
	//Todos los eventos de actualización
	public static final int EVENTO1 = 1;
	public static final int EVENTO2 = 2;
        public static final int EVENTO_RECIBIR_BUSQUEDA = 3;
       
	// Devuelve la instancia única de la clase
	public static GUIBuscador instancia() {
		if (instancia == null)
			instancia = new GUIBuscadorImpl();
		return instancia;
	}

	//Recoge los eventos de actualización
	public abstract void update(int evento, Object obj);

    //Devuelve el panel de Buscador
    public abstract BuscadorPanel getPanel();
}