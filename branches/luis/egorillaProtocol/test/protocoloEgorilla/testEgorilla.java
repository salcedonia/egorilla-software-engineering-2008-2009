package protocoloEgorilla;

/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

import gestorDeRed.GestorDeRed;
import gestorDeRed.test.GestorDeRedDummyImpl;
import java.util.logging.Level;
import java.util.logging.Logger;
import mensajes.Mensaje;
import mensajes.p2p.Dame;
import mensajes.p2p.HolaQuiero;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import peerToPeer.Fragmento;
import peerToPeer.GestorDescargas;
import protocoloEgorilla.Archivo;
import protocoloEgorilla.GestorEgorilla;
import static org.junit.Assert.*;

/**
 *
 * @author pitidecaner
 */
public class testEgorilla {

    public testEgorilla() {
    }

    static private GestorDescargas des;
    static GestorEgorilla ego;
    static GestorDeRedDummyImpl<Mensaje> red;
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        des = new GestorDescargas();
        red = new GestorDeRedDummyImpl<Mensaje>();
        ego = new GestorEgorilla(des, red);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        
        // antes de cada prueba lo limpia todo
        
        des = new GestorDescargas();
        red = new GestorDeRedDummyImpl<Mensaje>();
        ego = new GestorEgorilla(des, red);
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
    public void testGestorDescargas(){
        
        System.out.println("test de archivos en gestor de descargas");
        Archivo a = new Archivo("prueba", "aljfgasnfan");
        des.altaFichero(a);
        assertTrue(des.puedoBajar("aljfgasnfan"));
        assertFalse(des.puedoBajar("hashinventado"));
           
        // si añado un fragmento...
        Fragmento f = new Fragmento();
        f.hash = "aljfgasnfan";
        f.nombre = "prueba";
        f.offset = 0;
        f.tama = 10;
        
        Byte[] chunk = new Byte[10];
        
        System.out.println("test de fragmentos");
        des.llegaFragmento(f, chunk);
        
        // y pregunto por los fragmentos de este bloque, debe decirme que hay uno
        assertNotNull(des.getFragmentos("aljfgasnfan"));
        assertEquals(des.getFragmentos("aljfgasnfan").get(0), f);
    }
    
    @Test
    public void testRed(){
        
            // flujo normal de funcionamiento:
            // HolaQuiero
            // Tengo
            // Dame
            // Toma
            // alto

            System.out.println("test de llegada de mensajes");

            // preparamos un fichero con fragmentos
            Archivo a = new Archivo("prueba", "aljfgasnfan");
            des.altaFichero(a);

            ego.comienzaP2P();
            HolaQuiero hola = new HolaQuiero(a);

            red.testFingeLLegada(hola, "10.0.0.1", 4545);
            
            Dame d = new Dame();
            d.hash = "aljfgasnfan";
            d.nombre = "prueba";
            
            assertEquals(1,red.testGetEnviados().size());
            
            red.testFingeLLegada(d, "10.0.0.1", 4545);
   
            
    }

}