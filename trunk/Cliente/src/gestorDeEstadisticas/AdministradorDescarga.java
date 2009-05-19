/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gestorDeEstadisticas;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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

    
    protected AdministradorDescarga(BufferedReader fichero) {
        if (fichero != null) {
            try {
                cargaDatosGlobales(fichero);
            } catch (Exception ex) {
                incioGlobal();
                log.info("Las estadisticas no se han podido cargar, comenzaran desde 0");
            }
        } else {
            incioGlobal();
        }
        inicioSesion();
    }
    
    private void cargaDatosGlobales(BufferedReader fichero) throws IOException {
        velocidadGlobal = Double.valueOf(fichero.readLine());
        ficherosGlobal = Integer.parseInt(fichero.readLine());
        datosGlobal = Double.valueOf(fichero.readLine());
        pesoGlobal = Integer.parseInt(fichero.readLine());

    }

    private void incioGlobal() {
        velocidadGlobal = 0;
        ficherosGlobal = 0;
        datosGlobal = 0.0;
        pesoGlobal = 1;
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

    public void cerrar(BufferedWriter file) throws IOException {
        //TODO: Volcar datos de sesion en globales.
        datosGlobal += datosSesion;
        ficherosGlobal += ficherosSesion;
        int dividendo = pesoSesion + pesoGlobal;
        if (dividendo == 0) dividendo = 1;
        velocidadGlobal = ((velocidadGlobal * pesoGlobal) + (velocidadSesion * pesoSesion)) / dividendo;
        pesoGlobal += pesoSesion;
        file.append(String.valueOf(velocidadGlobal));file.newLine();
        file.append(String.valueOf(ficherosGlobal));file.newLine();
        file.append(String.valueOf(datosGlobal));file.newLine();
        file.append(String.valueOf(pesoGlobal));file.newLine();
    }



    public int getFicherosDescargadosGlobal() {
        return ficherosGlobal + ficherosSesion;
    }

    public int getFicherosDescargadosSesion() {
        return ficherosSesion;
    }


}
