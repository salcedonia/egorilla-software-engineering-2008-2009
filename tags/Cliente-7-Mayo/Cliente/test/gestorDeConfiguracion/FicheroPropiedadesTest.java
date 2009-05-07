/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestorDeConfiguracion;

import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author F. Javier Sánchez Pardo
 */
public class FicheroPropiedadesTest {

    FicheroPropiedades oFichPropiedades;
    FicheroPropiedades oFichPropiedadesDef;
    String sNomFichero = "cliente.properties";
    String sNomFicheroDef = "cliente_default.properties";
    
    @Before
    public void setUp() {
        //Cargo el archivo de propiedades por defecto
        try{
            oFichPropiedadesDef = new FicheroPropiedades(sNomFicheroDef);
        }catch(ControlConfiguracionClienteException e){
            System.out.println("\nError al cargar el fichero " + sNomFicheroDef);
        }
        //Cargo el archivo de propiedades normal
        try{
            oFichPropiedades = new FicheroPropiedades(sNomFichero);
        }catch(ControlConfiguracionClienteException e){
            System.out.println("\nError al cargar el fichero " + sNomFichero);
        }

    }

    @Test
     public void testToString() {
         System.out.println("\nPropiedades del fichero " + sNomFicheroDef);
         System.out.println("\n" + oFichPropiedadesDef.toString());
         System.out.println("\nPropiedades del fichero " + sNomFichero);
         System.out.println("\n" + oFichPropiedades.toString());
     }
    
    @Test 
    public void testEstablecerPropiedad() {
        //Establezco propiedades en el fichero de propiedades por defecto.
        try{
            oFichPropiedadesDef.establecerPropiedad("Puerto", "4000");
            oFichPropiedadesDef.establecerPropiedad("Lim_subida", "8");
            oFichPropiedadesDef.establecerPropiedad("Lim_bajada", "8");
            oFichPropiedadesDef.establecerPropiedad("Num_descargas_sim", "10");
        }catch(ControlConfiguracionClienteException e){         
            System.out.println("\nError al establecer propiedades en el fichero " + sNomFicheroDef);
        }
        //Establezco los valores de las propiedades en el fichero de propiedades normal.
        try{
            oFichPropiedades.establecerPropiedad("Puerto", "4000");
            oFichPropiedades.establecerPropiedad("Lim_subida", "20");
            oFichPropiedades.establecerPropiedad("Lim_bajada", "20");
            oFichPropiedades.establecerPropiedad("Num_descargas_sim", "15");
        }catch(ControlConfiguracionClienteException e){         
            System.out.println("\nError al establecer propiedades en el fichero " + sNomFichero);
        }

        System.out.println("\nPropiedades del fichero " + sNomFicheroDef);
        System.out.println("\n" + oFichPropiedadesDef.toString());
        
        System.out.println("\nPropiedades del fichero " + sNomFichero);
        System.out.println("\n" + oFichPropiedades.toString());
     }
    
    @Test 
    public void testObtenerPropiedad() {
        //Leo valores del fichero de propiedades por defecto
        System.out.println("\nValores de Propiedades del fichero " + sNomFicheroDef);
        System.out.println ("Puerto: "+ oFichPropiedadesDef.obtenerPropiedad("Puerto"));
        System.out.println ("Lim_subida: " + oFichPropiedadesDef.obtenerPropiedad("Lim_subida"));
        System.out.println ("Lim_bajada: " + oFichPropiedadesDef.obtenerPropiedad("Lim_bajada"));
        System.out.println ("Num_descargas_sim: " + oFichPropiedadesDef.obtenerPropiedad("Num_descargas_sim"));

        //Leo valores del fichero de propiedades normal
        System.out.println("Valores de Propiedades del fichero " + sNomFichero);
        System.out.println ("Puerto: "+ oFichPropiedades.obtenerPropiedad("Puerto"));
        System.out.println ("Lim_subida: " + oFichPropiedades.obtenerPropiedad("Lim_subida"));
        System.out.println ("Lim_bajada: " + oFichPropiedades.obtenerPropiedad("Lim_bajada"));
        System.out.println ("Num_descargas_sim: " + oFichPropiedades.obtenerPropiedad("Num_descargas_sim"));
     }
}