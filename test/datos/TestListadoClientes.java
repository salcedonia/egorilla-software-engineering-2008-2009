/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datos;

import mensajes.serverclient.DatosCliente;
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
public class TestListadoClientes {

    public TestListadoClientes() {
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
    public void testClase(){
        ListadoClientes l = new ListadoClientes();

        // clientes de palo para realizar tests
        DatosCliente c1 = new DatosCliente();
        c1.setNombreUsuario("c1");
        c1.setIP("ip1");
        c1.setPuertoEscucha(2454);
        DatosCliente c2 = new DatosCliente();
        c2.setNombreUsuario("c2");
        c2.setIP("ip2");
        c2.setPuertoEscucha(2454);
        DatosCliente c3 = new DatosCliente();
        c3.setNombreUsuario("c3");
        c3.setIP("ip3");
        c3.setPuertoEscucha(2454);

        // prueba con lista vacia, busqueda erronea
        assertFalse(l.estaDeAlta("no existe"));

        l.altaCliente(c1.getIP(), c1.getPuertoEscucha(), c1);
        l.altaCliente(c2.getIP(), c3.getPuertoEscucha(), c2);
        l.altaCliente(c3.getIP(), c2.getPuertoEscucha(), c3);

        // prueba con lista con contenido, busqueda erronea
        assertFalse(l.estaDeAlta("no existe"));

        // prueba con lista con contenido, busqueda exitosa
        assertTrue(l.estaDeAlta(c1.getIP()));
        assertTrue(l.estaDeAlta(c2.getIP()));
        assertTrue(l.estaDeAlta(c3.getIP()));

        // prueba el tamaño
        assertEquals(3,l.getClientes().size());

        // comprueba las busquedas
        assertEquals(c1, l.getDatosCliente(c1.getIP()));
        assertEquals(c2, l.getDatosCliente(c2.getIP()));
        assertEquals(c3, l.getDatosCliente(c3.getIP()));

        assertEquals(c1.getPuertoEscucha(), l.getPuerto(c1.getIP()));
        assertEquals(c2.getPuertoEscucha(), l.getPuerto(c2.getIP()));
        assertEquals(c3.getPuertoEscucha(), l.getPuerto(c3.getIP()));
   
        // elimina
        l.eliminaCliente(c2.getIP());

        assertTrue(l.estaDeAlta(c1.getIP()));
        assertFalse(l.estaDeAlta(c2.getIP()));
        assertTrue(l.estaDeAlta(c3.getIP()));

        // eso es todo
    }

}