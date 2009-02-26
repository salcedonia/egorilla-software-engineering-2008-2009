/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package presentacion;

/**
 *
 * @author Victor
 */
public abstract class eGorillaControlGeneral {

    private static eGorillaControlGeneral instancia;

    public static final int CONECTAR = 0;
    public static final int DESCONECTAR = 1;


	// Devuelve la única instanciad de eGorillaControlGeneral
	public static eGorillaControlGeneral instancia() {
		if (instancia == null)
			instancia = new eGorillaControlGeneralImpl();
		return instancia;
	}

    // Recoge el evento de actualización
	public abstract void action(int evento, Object params);
	
	// Inicia la interfaz
	public abstract void iniciar();
}
