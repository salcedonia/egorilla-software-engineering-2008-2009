/*
 * Este proyecto esta sujeto a licencia GPL
 * This project and code is uncer GPL license
 */

package servidor.tareas;

import datos.Archivo;
import datos.ArchivoClientes;
import datos.ListadoClientes;
import gestorDeRed.test.GestorDeRedDummyImpl;
import java.util.logging.Level;
import java.util.logging.Logger;
import mensajes.Mensaje;
import mensajes.TipoMensaje;
import mensajes.serverclient.DatosCliente;
import mensajes.serverclient.ListaArchivos;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import servidor.ServidorEgorilla;
import static org.junit.Assert.*;

/**
 *
 * @author pitidecaner
 */
public class TestConexion {

    public TestConexion() {
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
    public void testListaClientes(){
        System.out.println("Probando el sistema de almacenamiento de clientes");
        ListadoClientes lc = new ListadoClientes();
        assertEquals( 0 , lc.getClientes().size());
        
        // crea datos para un cliente de pruebas
        DatosCliente dummyClient = new DatosCliente();
        dummyClient.setNombreUsuario("Dummy");
        dummyClient.setPuertoEscucha(2435);
        dummyClient.setIP("45.67.87.98");
        
        lc.altaCliente("45", 54, dummyClient);
        assertEquals( 1 , lc.getClientes().size());
        assertEquals("45", lc.getClientes().elementAt(0));
        
        DatosCliente sacadoDeLaLista = lc.getDatosCliente("45");
        assertEquals(dummyClient, sacadoDeLaLista);
        assertEquals("45.67.87.98", sacadoDeLaLista.getIP());
        assertEquals("Dummy", sacadoDeLaLista.getNombreUsuario());
        assertEquals(2435, sacadoDeLaLista.getPuertoEscucha());
        
        lc.eliminaCliente("45");
        assertEquals( 0 , lc.getClientes().size());
    }
    
    @Test
    public void testEnvios(){
        System.out.println("Probando envio de paquetes ficticios");
        GestorDeRedDummyImpl<Mensaje> red = new GestorDeRedDummyImpl<Mensaje>();
        ListadoClientes lc = new ListadoClientes();
        ArchivoClientes ac = new ArchivoClientes();
        ServidorEgorilla egorilla = new ServidorEgorilla(red, lc, ac);
        
        red.registraReceptor(egorilla);
        red.comienzaEscucha();
        System.out.println("Sistema Listo");
        
        
        // crea datos para un cliente de pruebas
         DatosCliente dummyClient = new DatosCliente();
        dummyClient.setNombreUsuario("Dummy");
        dummyClient.setPuertoEscucha(2435);
        dummyClient.setIP("45.67.87.98");
                 
        // llegada de un datos cliente:
        red.testFingeLLegada(dummyClient, "45.67.87.98", 2435);
        try {
            System.out.println("esperando 2 segundos para dar tiempo al otro hilo");
            // espera para dar tiempo al hilo que escucha
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(TestConexion.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        assertEquals( 1 , lc.getClientes().size());
        assertEquals("45.67.87.98", lc.getClientes().elementAt(0));
        
        DatosCliente sacadoDeLaLista = lc.getDatosCliente("45.67.87.98");
        assertEquals(dummyClient, sacadoDeLaLista);
        assertEquals("45.67.87.98", sacadoDeLaLista.getIP());
        assertEquals("Dummy", sacadoDeLaLista.getNombreUsuario());
        assertEquals(2435, sacadoDeLaLista.getPuertoEscucha());
        
        assertEquals(1, red.testGetEnviados().size());
        assertEquals(TipoMensaje.Bienvenido, red.testGetEnviados().firstElement().getTipoMensaje());
        
        System.out.println("Test Conexion OK");
    }
    
    @Test
    public void testListaArchivos(){
        System.out.println("Probando envio de lista de ficheros");
        GestorDeRedDummyImpl<Mensaje> red = new GestorDeRedDummyImpl<Mensaje>();
        ListadoClientes lc = new ListadoClientes();
        ArchivoClientes ac = new ArchivoClientes();
        ServidorEgorilla egorilla = new ServidorEgorilla(red, lc, ac);
        
        red.registraReceptor(egorilla);
        red.comienzaEscucha();
        System.out.println("Sistema Listo");
        
        
        // llegada de un datos cliente:
        DatosCliente dummyClient = new DatosCliente();
        dummyClient.setNombreUsuario("Dummy");
        dummyClient.setPuertoEscucha(2435);
        dummyClient.setIP("45.67.87.98");
        // lo da de alta (como si estubiera conectado)
        lc.altaCliente(dummyClient.getIP(), dummyClient.getPuertoEscucha(), dummyClient);
        
        ListaArchivos lista = new ListaArchivos();
        //crea una lista de archivos falsa
        for (int i=0; i < 10; i++){
            Archivo a = new Archivo(Integer.toString(i), Integer.toString(i));
            lista.add(a);
        }
        // finge la llegada por la red
        red.testFingeLLegada(lista, dummyClient.getIP(),dummyClient.getPuertoEscucha());
        
        // espera para dar tiempo al hilo que escucha
        try {
            System.out.println("esperando 2 segundos para dar tiempo al otro hilo");
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            Logger.getLogger(TestConexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // comprueba busqueda correcta
        assertEquals(lista.firstElement(), ac.buscar("0")[0]);
        // comrueba busqueda erronea
        assertEquals(0, ac.buscar("no esta").length);
        // compara numero de archivos subidos
        assertEquals(10, ac.getNumeroArchivos());
        
        System.out.println("Test lista ficheros OK");
    }
    
    
}