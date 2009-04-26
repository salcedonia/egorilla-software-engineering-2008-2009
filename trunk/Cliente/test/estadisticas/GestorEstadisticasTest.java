/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package estadisticas;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Qiang
 */
public class GestorEstadisticasTest {

    public GestorEstadisticasTest() {
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

    /**
     * Test of getInstacia method, of class GestorEstadisticas.
     */
    @Test
    public void testGetInstacia() {
        System.out.println("getInstacia");
        GestorEstadisticas instancia1 = GestorEstadisticas.getInstacia();
        GestorEstadisticas instancia2 = GestorEstadisticas.getInstacia();
        if (instancia1 == null)  {
            fail("Error al crear instacia, instancia nula");
        }
        assertEquals(instancia1, instancia2);
    }

    /**
     * Test of inicioSesion method, of class GestorEstadisticas.
     */
    @Test
    public void testInicioSesion() {
        System.out.println("inicioSesion");
        GestorEstadisticas instance = GestorEstadisticas.getInstacia();
        instance.inicioSesion();
        assertEquals(instance.getTotalDatosDescargaSesion(),0.0);
        assertEquals(instance.getTotalDatosSubidaSesion(),0.0);
    }
    /**
     * Test of cerrar method, of class GestorEstadisticas.
     */
    @Test
    public void testCerrar() {
        //TODO.
    }



    /**
     * Test of getFicherosDescargadosSesion method, of class GestorEstadisticas.
     */
    @Test
    public void testGetFicherosDescargadosSesion() {
        System.out.println("getFicherosDescargadosSesion");
        GestorEstadisticas instance = GestorEstadisticas.getInstacia();
        instance.llegadaFichero(25);
        instance.llegadaFichero(-5);
        int num = instance.getFicherosDescargadosSesion();
        assertEquals(25, num);
    }


    private class DummyDescarga extends AdministradorDescarga{
//        public DummyDescarga(){
//            //super();
//        }
        public void setTiempoMaximo(int valor){
            tiempoMaximo = valor;
        }
        public void setVarianza(int valor) {
            varianza = valor;
        }
        public void setIntervalo(int valor) {
            intervaloActualizacion = valor;
        }
    }
    private class DummyGestorEstadisticas extends GestorEstadisticas{
            public DummyGestorEstadisticas() {
                intancia = super.getInstacia();
            }
            public void setAdministradorSubida(AdministradorSubida value) {
                this.subida = value;
            }
            public void setAdminnistradorBajada(AdministradorDescarga value) {
                this.descarga = value;
            }
    }
    
    /**
     * Test of llegadaDatosDescarga method, of class GestorEstadisticas.
     */
    @Test
    public void testDatosDescarga() {
        System.out.println("llegadaDatosDescarga");
        
        double[] longitud = {250.0, 300.0, 350.0, 400};
        DummyGestorEstadisticas instance = new DummyGestorEstadisticas();
        DummyDescarga descarga = new DummyDescarga();
        descarga.setIntervalo(10);
        descarga.setTiempoMaximo(10);
        descarga.setVarianza(5);
        instance.setAdminnistradorBajada(descarga);
        for (Double log : longitud) {
            try {
                instance.llegadaDatosDescarga(log);
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
            }
        }
        double total = instance.getTotalDatosDescargaSesion();
        assertEquals(total, 1300.0);
        List<Double> valocidades = instance.getListaVelocidadMediaBajadaSesion();
        System.out.println("Total de velocidad descargados: " +valocidades.toString() );
        System.out.println("Total de datos descargados: " +String.valueOf(total) );
        double actual = instance.getVelocidadActualDescarga();
        System.out.println("velocidad actual: " +String.valueOf(actual) );
        double velocidad = total/(longitud.length*10);
        assertEquals(actual,velocidad);
        System.out.println(velocidad);
        
    }

}