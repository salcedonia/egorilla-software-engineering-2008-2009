package gestorDeFicheros;

import static org.junit.Assert.*;
import junit.framework.JUnit4TestAdapter;
import org.junit.*;

/**
 */
public class GestorDiscoTest {
    


  /*  @Before
  public void inicializar(){

  }*/

  public static junit.framework.Test suite(){
    return new JUnit4TestAdapter( GestorDiscoTest.class );
  }

    /*@BeforeClass
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
    }*/

    /**
     */
    @Test
    public void testConstructor() {

      //Establecer las rutas relativas donde coloco los archivos de prueba
      //como son private necesito hacer el reflect

      GestorDisco gestorDisco = new GestorDisco();
      assertEquals( gestorDisco.getListaArchivosTemporales() != null, true );
      assertEquals( gestorDisco.getListaArchivosCompletos() != null, true );
      assertEquals( gestorDisco.getListaArchivosTodos() != null, true );
      //Comprobar el numero de archivo en estas.

      assertEquals( gestorDisco.getManejarIndices() != null, true );
      assertEquals( gestorDisco.getManejarListaArchivos() != null, true );

      assertEquals( gestorDisco.getFragmentador() != null, true );
      assertEquals( gestorDisco.getEnsamblador() != null, true );

      assertEquals( gestorDisco.getDirectorioTemporales() != null, true );
      assertEquals( gestorDisco.getDirectorioCompletos() != null, true );
      //comprobar q son los de por defecto, añadiendo la ruta para las pruebas

      assertEquals( gestorDisco.getExtesionIndices() != null, true );
      assertEquals( gestorDisco.getExtesionFicheroTemporal() != null, true );
      //comprobar q son los de por defecto
      assertEquals( gestorDisco.getTamanioBytesFragmento(), 512 ); //puede variar!
    }

    /**
     */
    @Ignore
    @Test
    public void test2() {
    }

}
