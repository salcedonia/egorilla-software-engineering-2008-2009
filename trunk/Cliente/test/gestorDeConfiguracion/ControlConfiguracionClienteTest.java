/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestorDeConfiguracion;

import java.util.Properties;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author F. Javier Sánchez Pardo
 */
public class ControlConfiguracionClienteTest {
    ControlConfiguracionCliente oControlConfiguracion;
    String sNomFichero = "cliente.properties";
    String sNomFicheroDef = "cliente_default.properties";
    
    @Before
    public void setUp() throws ControlConfiguracionClienteException {
        oControlConfiguracion = ControlConfiguracionCliente.obtenerInstancia("cliente.properties", "cliente_default.properties");
    }

    @Test
    public void testObtenerPropiedad() {
        //Leo valores de propiedades
        System.out.println("Valores de Propiedades");
        System.out.println ("Puerto: "+ oControlConfiguracion.obtenerPropiedad("Puerto"));
        System.out.println ("Lim_subida: " + oControlConfiguracion.obtenerPropiedad("Lim_subida"));
        System.out.println ("Lim_bajada: " + oControlConfiguracion.obtenerPropiedad("Lim_bajada"));
        System.out.println ("Num_descargas_sim: " + oControlConfiguracion.obtenerPropiedad("Num_descargas_sim"));        
    }

    @Test
    public void testObtenerPropiedadxDefecto() {
        //Leo valores de propiedades por defecto
        System.out.println("\nValores de Propiedades por defecto");
        System.out.println ("Puerto: "+ oControlConfiguracion.obtenerPropiedadPorDefecto("Puerto"));
        System.out.println ("Lim_subida: " + oControlConfiguracion.obtenerPropiedadPorDefecto("Lim_subida"));
        System.out.println ("Lim_bajada: " + oControlConfiguracion.obtenerPropiedadPorDefecto("Lim_bajada"));
        System.out.println ("Num_descargas_sim: " + oControlConfiguracion.obtenerPropiedadPorDefecto("Num_descargas_sim"));
    }

    @Test
    public void testEstablecerConfiguracion() throws ControlConfiguracionClienteException {
        Properties propiedades = oControlConfiguracion.obtenerConfiguracionPorDefecto();
        oControlConfiguracion.establecerConfiguracion(propiedades);
        //Leo valores de propiedades
        System.out.println("Valores de Propiedades");
        System.out.println ("Puerto: "+ oControlConfiguracion.obtenerPropiedad("Puerto"));
        System.out.println ("Lim_subida: " + oControlConfiguracion.obtenerPropiedad("Lim_subida"));
        System.out.println ("Lim_bajada: " + oControlConfiguracion.obtenerPropiedad("Lim_bajada"));
        System.out.println ("Num_descargas_sim: " + oControlConfiguracion.obtenerPropiedad("Num_descargas_sim"));        
    }    
}