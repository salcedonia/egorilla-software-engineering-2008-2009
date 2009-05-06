/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package util;

import util.Util;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author F. Javier Sanchez Pardo
 */
public class UtilTest {
    
    public UtilTest() {
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

   
    @Test
    public void serializar() throws FileNotFoundException, IOException{
        //TEST 3: Serializar ArrayList de ArrayList de Strings
        String [][] aServidores  = { {"Servidor 1x", "128.23.21.1", "9091", "prueba 1"},
                                    {"Servidor 2x", "128.23.21.2", "9092", "prueba 2"},
                                    {"Servidor 3x", "128.23.21.3", "9093", "prueba 3"}};

        System.out.println("Serializando tabla bidimensional...");
        Util.serializar(aServidores, "servidores6.cfg");
    }
    
    @Test
    public void deserializar() throws FileNotFoundException, IOException, ClassNotFoundException{
        String [][] aServidores = (String[][]) Util.deserializar ("servidores6.cfg");
        System.out.println("Contenido de la tabla bidimensional serializada:");
        for(int iFila = 0; iFila < aServidores.length; iFila++){
            for(int iFila2 = 0; iFila2 < aServidores[iFila].length; iFila2++)
                System.out.print(aServidores[iFila][iFila2] + " ");
            System.out.println();
        }
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}

}