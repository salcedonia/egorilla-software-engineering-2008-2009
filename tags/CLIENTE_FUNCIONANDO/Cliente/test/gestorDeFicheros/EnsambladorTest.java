package gestorDeFicheros;

import datos.Archivo;
import datos.TipoArchivo;
import java.io.File;
import junit.framework.JUnit4TestAdapter;
import org.junit.*;

/**
 */
public class EnsambladorTest {
    


  /*  @Before
  public void inicializar(){

  }*/

  public static junit.framework.Test suite(){
    return new JUnit4TestAdapter( EnsambladorTest.class );
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

      Ensamblador ensamblador = gestorDisco.getEnsamblador();

      String directorioTest = "test//gestorDeFicheros//";
      String nombreFileComJar = "comunicaciones.jir";
      File fileComJar = new File( directorioTest+nombreFileComJar );
      String hashAux = MD5Sum.getFileMD5Sum( fileComJar );
      Archivo archivoComJar = new Archivo( fileComJar.getName(), hashAux, fileComJar.length(), 
        TipoArchivo.ARCHIVO);
      System.out.println( archivoComJar.toString() );
      ensamblador.nuevoArchivoTemporal( archivoComJar );
    }

    /**
     */
    @Ignore
    @Test
    public void test2() {
    }

}
