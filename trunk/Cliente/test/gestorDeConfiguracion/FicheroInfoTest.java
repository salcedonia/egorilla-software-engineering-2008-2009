
package gestorDeConfiguracion;

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
public class FicheroInfoTest {
    private static FicheroInfo <InfoServidor> _oFicheroInfoServidores;
    
    public FicheroInfoTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() throws ControlConfiguracionClienteException {
        _oFicheroInfoServidores = new FicheroInfo <InfoServidor> ("servidores.info");
        _oFicheroInfoServidores.cargarFicheroInfo();
    }

    @Test
    public void testCarga() throws ControlConfiguracionClienteException {    
        _oFicheroInfoServidores.cargarFicheroInfo();
        ArrayList <InfoServidor> alInfoServidores = _oFicheroInfoServidores.getInfoFichero();
        System.out.println("Fichero de servidores");
        for(Iterator <InfoServidor> iterador = alInfoServidores.iterator(); iterador.hasNext();){
            System.out.println(iterador.next());
        }
    }
    
    @Test
    public void testModificacion() throws ControlConfiguracionClienteException {    
        InfoServidor infoServidor;
        
        _oFicheroInfoServidores.cargarFicheroInfo();
        ArrayList <InfoServidor> alInfoServidores = _oFicheroInfoServidores.getInfoFichero();
        //Añado 2 servidores mas
        infoServidor = new InfoServidor ("Servidor Otro", "128.23.21.1", 9091, "prueba 1");
        alInfoServidores.add(infoServidor);
        infoServidor = new InfoServidor ("Servidor Otro mas", "128.23.21.1", 9091, "prueba 1");
        alInfoServidores.add(infoServidor);
        //Modifico y grabo
        _oFicheroInfoServidores.setInfoFichero(alInfoServidores);
        //Imprimo para ver
        System.out.println("Fichero de servidores");
        for(Iterator <InfoServidor> iterador = alInfoServidores.iterator(); iterador.hasNext();){
            System.out.println(iterador.next());
        }
    }    
}