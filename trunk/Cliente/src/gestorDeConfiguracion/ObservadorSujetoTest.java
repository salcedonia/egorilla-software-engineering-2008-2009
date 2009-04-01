/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestorDeConfiguracion;

/**
 * Programa de prueba para probar la interaccion de un Sujeto observado (objeto ControlConfiguracionCliente)
 * con varios Observadores (ventanas).
 * @author F. Javier Sanchez Pardo
 */
public class ObservadorSujetoTest {

    public static void main(String[] args) throws ControlConfiguracionClienteException{
        //Creo el Sujeto
        ControlConfiguracionCliente oCtrlConfigCli = ControlConfiguracionCliente.obtenerInstancia("cliente.properties", "cliente_default.properties");
        //Creo los Observadores.
        VentanaObservador ventana1 = new VentanaObservador(oCtrlConfigCli);
        ventana1.setVisible(true);
        VentanaObservador ventana2 = new VentanaObservador(oCtrlConfigCli);
        ventana2.setVisible(true);
        VentanaObservador ventana3 = new VentanaObservador(oCtrlConfigCli);
        ventana3.setVisible(true);
    }
}