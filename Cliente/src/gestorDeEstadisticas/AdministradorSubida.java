/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestorDeEstadisticas;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.LinkedList;
import org.apache.log4j.Logger;

/**
 * Modulo que gestiona las datos que se envian.
 * @see ModuloTrafico.
 * @author Qiang
 */

public class AdministradorSubida extends ModuloTrafico{
    //private static AdministradorSubida _instancia;
     // Variables de configuracion de las estadisticas.
    private String ruta = "../stadUpload.bin";
  
    private static final Logger log = Logger.getLogger(AdministradorSubida.class.getName());
    AdministradorSubida(DataInputStream fichero){
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
        velocidadGlobal = fichero.readInt();
        datosGlobal = fichero.readDouble();
        pesoGlobal = fichero.readInt();

    }

    private void incioGlobal() {
        velocidadGlobal = 0;
        datosGlobal = 0.0;
        pesoGlobal = 0;
    }
    
     public void inicioSesion() {
        velocidadSesion = 0;
        datosSesion = 0.0;
        fechaInicioSesion = new Date();
        ultimaActualizacion = new Date();
        listaVelocidadSesion = new LinkedList();
        listaDatosSesion = new LinkedList();
    }
     
     public void cerrar(DataOutputStream file) throws IOException {
          //TODO: Volcar datos de sesion en globales.
        datosGlobal += datosSesion;
        velocidadGlobal = ((velocidadGlobal * pesoGlobal) + (velocidadSesion * pesoSesion)) / (pesoSesion + pesoGlobal);
        pesoGlobal += pesoSesion;
        file.writeDouble(velocidadGlobal);
        file.writeDouble(datosGlobal);
        file.write(pesoGlobal);
    }

    private void volcarDatosGlobal(ObjectOutputStream writer) {
        Long cantidad;
      //TODO Hay que realizar el volcado
      /*  try
		{
			//Se abre el fichero donde se har� la copia
            writer.writeInt(_totalGlobal.size());
            for (int valor : _totalGlobal.keySet()) {
                cantidad = _totalGlobal.get(valor);
                writer.writeInt(valor);
                writer.writeLong(cantidad);
            }
             writer.flush();
		}
		catch (Exception e)
		{
            e.printStackTrace();
			System.err.println("Error al volcar los datos de estadistica");
		}*/
    }


    public   void reiniciarTodo() {

    }



    public void reniciarSesion() {
       // inicioSesion();
    }

    public void llegadaFichero(int cantidad) {
        //Nothing
    }

    public int getFicherosDescargadosGlobal() {
        return 0;
    }

    public int getFicherosDescargadosSesion() {
        return 0;
    }


}

   


 

