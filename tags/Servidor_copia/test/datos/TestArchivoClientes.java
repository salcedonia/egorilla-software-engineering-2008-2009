/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datos;

import mensajes.serverclient.DatosCliente;
import mensajes.serverclient.ListaArchivos;
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
public class TestArchivoClientes {

    public TestArchivoClientes() {
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
    public void pruebaClase(){
        ArchivoClientes ac = new ArchivoClientes();

        // clientes de prueba
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

        // archivos de prueba
           // uno cada uno
        Archivo a1 = new Archivo("a1", "hash1", 4345, TipoArchivo.ARCHIVO);
        Archivo a2 = new Archivo("a2", "hash2", 4345, TipoArchivo.AUDIO);
        Archivo a3 = new Archivo("a3", "hash3", 4345, TipoArchivo.IMAGEN);

           // compartidos
        Archivo b1 = new Archivo("b1", "hashb1", 4345, TipoArchivo.OTRO); // 1y2
        Archivo b2 = new Archivo("b1", "hashb2", 4345, TipoArchivo.DOCUMENTO); // 2y3
        Archivo b3 = new Archivo("b1", "hashb3", 4345, TipoArchivo.VIDEO); // 1,2y3

        // arvhivos de 1
        ListaArchivos la1 = new ListaArchivos();
        la1.add(a1);
        la1.add(b1);
        la1.add(b3);

        // arvhivos de 2
        ListaArchivos la2 = new ListaArchivos();
        la2.add(a2);
        la2.add(b1);
        la2.add(b2);
        la2.add(b3);

        // archivos de 3
        ListaArchivos la3 = new ListaArchivos();
        la3.add(a3);
        la3.add(b2);
        la3.add(b3);

        // prueba busquedas con lista vacia
        assertEquals(0,ac.buscar("no existe").length);

        // añade cliente 1
        ac.actualizarDesdeListaCliente(c1, la1);

        // prueba busquedas con lista con contenido
        assertEquals(0,ac.buscar("no existe").length);
        assertEquals(a1,ac.buscar(a1._nombre)[0]);
        assertEquals(c1, ac.getPropietarios(a1._hash)[0]);

        // añade todos
        ac.actualizarDesdeListaCliente(c2, la2);
        ac.actualizarDesdeListaCliente(c3, la3);

        // comprueba las relacciones
        assertEquals(3,ac.getPropietarios(b3._hash).length);
        assertEquals(2,ac.getPropietarios(b2._hash).length);
        assertEquals(2,ac.getPropietarios(b1._hash).length);

        assertEquals(1,ac.getPropietarios(a3._hash).length);
        assertEquals(1,ac.getPropietarios(a2._hash).length);
        assertEquals(1,ac.getPropietarios(a1._hash).length);

        // busquedas de propieatarios erroneas
        assertEquals(0,ac.getPropietarios("no exite").length);

        // eliminamos un pavo
        ac.eliminaPropietario(c1);
        assertEquals(0,ac.buscar(a1._nombre).length);
        assertEquals(1,ac.getPropietarios(b1._hash).length);
        assertEquals(2,ac.getPropietarios(b3._hash).length);

        // quedan 2 clientes dentro
        assertEquals(2, ac.getNumeroClientes());
        // quedan 5 ficheros dentro
        assertEquals(5, ac.getNumeroArchivos());

        //eliminamos todos
        ac.eliminaPropietario(c2);
        ac.eliminaPropietario(c3);

        assertEquals(0,ac.buscar(a1._nombre).length);
        assertEquals(0,ac.buscar(a2._nombre).length);
        assertEquals(0,ac.buscar(a3._nombre).length);

        assertEquals(0,ac.buscar(b1._nombre).length);
        assertEquals(0,ac.buscar(b2._nombre).length);
        assertEquals(0,ac.buscar(b3._nombre).length);

        // propietarios con hash no existente
        assertEquals(0, ac.getPropietarios("no exite").length);
    }
}