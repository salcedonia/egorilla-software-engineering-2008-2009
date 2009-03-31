/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package estadisticas;

import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Logger;

/**
 *
 * @author Qiang
 */
class AdministradorDescarga extends ModuloTrafico {
    // Variables de configuracion de las estadisticas.
    private String ruta = "../stadDownload.bin";
    protected int ficherosGlobal;
    protected int ficherosSesion;
    
    private static final Logger log = Logger.getLogger(AdministradorDescarga.class.getName());

    
    AdministradorDescarga() {
        cargaDatosGlobales();
        inicioSesion();
    }
    
    private void cargaDatosGlobales() {
        //TODO cargar los datos de fichero.
        velocidadGlobal = 0;
        ficherosGlobal = 0;
        datosGlobal = 0.0;
        pesoGlobal = 0;

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

    public void cerrar() {
        //TODO: Volcar datos de sesion en globales.
        datosGlobal += datosSesion;
        ficherosGlobal += ficherosSesion;
        velocidadGlobal = ((velocidadGlobal * pesoGlobal) + (velocidadSesion * pesoSesion)) / (pesoSesion + pesoGlobal);
        pesoGlobal += pesoSesion;
    }



    public int getFicherosDescargadosGlobal() {
        return ficherosGlobal + ficherosSesion;
    }

    public int getFicherosDescargadosSesion() {
        return ficherosSesion;
    }


}
