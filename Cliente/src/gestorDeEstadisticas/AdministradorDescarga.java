/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorDeEstadisticas;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import org.apache.log4j.Logger;

/**
 * @see ModuloTrafico.
 * @author Qiang
 */
class AdministradorDescarga extends ModuloTrafico {
    // Variables de configuracion de las estadisticas.
    private String ruta = "../stadDownload.bin";
    protected int ficherosGlobal;
    protected int ficherosSesion;
    
    private static final Logger log = Logger.getLogger(AdministradorDescarga.class.getName());

    
    protected AdministradorDescarga(DataInputStream fichero) {
        if (fichero != null) {
            try {
                cargaDatosGlobales(fichero);
            } catch (IOException ex) {
                incioGlobal();
                log.info("Las estadisticas no se han podido cargar, comenzaran desde 0");
            }
        } else {
            incioGlobal();
        }
        inicioSesion();
    }
    
    private void cargaDatosGlobales(DataInputStream fichero) throws IOException {
        //TODO cargar los datos de fichero.
        velocidadGlobal = fichero.readDouble();
        ficherosGlobal = fichero.readInt();
        datosGlobal = fichero.readDouble();
        pesoGlobal = fichero.readInt();

    }

    private void incioGlobal() {
        velocidadGlobal = 0;
        ficherosGlobal = 0;
        datosGlobal = 0.0;
        pesoGlobal = 0;
    }

    public void llegadaFichero(int cantidad) {
        if (cantidad>0)
            ficherosSesion += cantidad;
    }

        public void inicioSesion() {
            velocidadSesion = 0;
            ficherosSesion = 0;
            datosSesion = 0.0;
            fechaInicioSesion = new Date();
            ultimaActualizacion = new Date();
            listaVelocidadSesion = new LinkedList();
            listaDatosSesion = new LinkedList();
        }

    public void reiniciarTodo() {
        incioGlobal();
        inicioSesion();
    }

    public void reniciarSesion() {
        //TODO para que sirve esto?
    }

    public void cerrar(DataOutputStream file) throws IOException {
        //TODO: Volcar datos de sesion en globales.
        datosGlobal += datosSesion;
        ficherosGlobal += ficherosSesion;
        velocidadGlobal = ((velocidadGlobal * pesoGlobal) + (velocidadSesion * pesoSesion)) / (pesoSesion + pesoGlobal);
        pesoGlobal += pesoSesion;
        file.writeDouble(velocidadGlobal);
        file.write(ficherosGlobal);
        file.writeDouble(datosGlobal);
        file.write(pesoGlobal);
    }



    public int getFicherosDescargadosGlobal() {
        return ficherosGlobal + ficherosSesion;
    }

    public int getFicherosDescargadosSesion() {
        return ficherosSesion;
    }


}
