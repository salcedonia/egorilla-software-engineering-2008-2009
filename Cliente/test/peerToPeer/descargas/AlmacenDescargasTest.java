/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package peerToPeer.descargas;

import datos.Archivo;
import mensajes.p2p.Tengo;
import mensajes.serverclient.DatosCliente;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Clase de prueba de AlmacénDescargas
 * De momento, hay que comentar el método actualiza del constructor de Descarga
 * pues da un error y obviamente, no se crea la descarga (o descargas) necesarias
 * para estas pruebas.
 * Al comentarlo, la descarga se crea, pero sin hablar con el GestorDeCompartidos
 * para saber qué fragmentos tiene ya
 * @author Iñaki Goffard
 */
public class AlmacenDescargasTest {
    
    AlmacenDescargas _almacen;
    
    Archivo _archivo1;
    
    Archivo _archivo2;
    
    Archivo _archivo3;
    
    Archivo _archivo4;
    
    
    

    public AlmacenDescargasTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        
        _almacen = new AlmacenDescargas();
        _archivo1 = new Archivo("peli","12345");
        _archivo2 = new Archivo("documento","67890");
        _archivo3 = new Archivo("imagen","54321");
        _archivo4 = new Archivo("plantilla","02468");
        _almacen.nuevaDescarga(_archivo1);
        _almacen.nuevaDescarga(_archivo2);
        _almacen.nuevaDescarga(_archivo3);
        _almacen.nuevaDescarga(_archivo4);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of nuevaDescarga method, of class AlmacenDescargas.
     */
    @Test
    public void testNuevaDescarga() {
        System.out.println("nuevaDescarga");
        int tam = _almacen.getListaDescargas().size();
        assertEquals( 4, tam);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of respuestaPeticionDescarga method, of class AlmacenDescargas.
     */
    @Ignore
    @Test
    public void testRespuestaPeticionDescarga() {
        System.out.println("respuestaPeticionDescarga");
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of actualizaDescarga method, of class AlmacenDescargas.
     */
    @Ignore
    @Test
    public void testActualizaDescarga() {
        System.out.println("actualizaDescarga");
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of dameSiguiente method, of class AlmacenDescargas.
     * Comprobamos la circularidad de la lista
     */
    @Test
    public void testDameSiguiente() {
        System.out.println("dameSiguiente");
        Descarga des =_almacen.dameSiguiente();
        String hash = des.getArchivo().getHash();
        assertEquals("12345",hash);
        des = _almacen.dameSiguiente();
        hash = des.getArchivo().getHash();
        assertEquals("67890",hash);
        des = _almacen.dameSiguiente();
        hash = des.getArchivo().getHash();
        assertEquals("54321",hash);
         //Compruebo que da bien la vuelta
        des = _almacen.dameSiguiente();
        hash = des.getArchivo().getHash();
        assertEquals("02468",hash);
        des = _almacen.dameSiguiente();
        hash = des.getArchivo().getHash();
        assertEquals("12345",hash);
        des = _almacen.dameSiguiente();
        hash = des.getArchivo().getHash();
        assertEquals("67890",hash);
        des = _almacen.dameSiguiente();
        hash = des.getArchivo().getHash();
        assertEquals("54321",hash);
        des = _almacen.dameSiguiente();
        hash = des.getArchivo().getHash();
        assertEquals("02468",hash);
       
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

}