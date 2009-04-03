/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

package gestorDeRed.TCP;

import gestorDeRed.GestorDeRed;
import gestorDeRed.NetError;
import gestorDeRed.Receptor;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author pitidecaner
 */
public class testTCP {

    public testTCP() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void testTCPComs(){
       System.out.println("probando implementacion TCP de la red "); 
       
       GestorDeRed<String> red = new GestorDeRedTCPimpl<String>(6969);
       red.registraReceptor(new Receptor<String>() {
            public void recibeMensaje(String msj, String ip, int port) {
                assertEquals("Probando send", msj);
            }
        }); 
        
         System.out.println("Prueba envio fallido"); 
         String error  =null;
        try {

            red.envia("Probando send", "localhost", 6969);
        } catch (NetError ex) {
            error = "hay error";
        }
        assertNotNull(error);
        
   
       
        System.out.println("prueba envio con el puerto escuchando");
        red.comienzaEscucha();
        error = null;
        try {
            // espera activa para dar tiempo al hilo que escucha a abrir el
            // socket
            for (long i = 0; i < 1000000; i++) {               
            }

            red.envia("Probando send", "localhost", 6969);
        } catch (NetError ex) {
            error = "hay error";
        } 
        assertNull(error);  
    }   
}