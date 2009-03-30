/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package estadisticas;

import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Logger;

/**
 *
 * @author Qiang
 */

public class AdministradorSubida extends ModuloTrafico{
    private static AdministradorSubida _instancia;
     // Variables de configuracion de las estadisticas.
    private String ruta = "../stadUpload.bin";
  
    private static final Logger log = Logger.getLogger(AdministradorSubida.class.getName());
    AdministradorSubida(){
        cargaDatosGlobales();
        inicioSesion();
    }

    private void cargaDatosGlobales() {
        //TODO cargar los datos de fichero.
        velocidadGlobal = 0;
        datosGlobal = 0.0;
        pesoGlobal = 0;

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
     
     public void cerrar() {
          //TODO: Volcar datos de sesion en globales.
        datosGlobal += datosSesion;
        velocidadGlobal = ((velocidadGlobal * pesoGlobal) + (velocidadSesion * pesoSesion)) / (pesoSesion + pesoGlobal);
        pesoGlobal += pesoSesion;
    }

    private void volcarDatosGlobal(ObjectOutputStream writer) {
        Long cantidad;
      //TODO Hay que realizar el volcado
      /*  try
		{
			//Se abre el fichero donde se hará la copia
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


    public synchronized  void reiniciarTodo() {
        /*inicioSesion();
        velocidadMediaGlobal = 0;
        _totalGlobal = 0;
        // volcarNumeroFicheros(_numFicherosGlobal);
        // TODO volcarDatosGlobal();
*/
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

   


 

