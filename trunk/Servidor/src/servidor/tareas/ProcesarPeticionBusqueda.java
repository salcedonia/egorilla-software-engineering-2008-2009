package servidor.tareas;

import com.sun.jmx.remote.internal.ClientCommunicatorAdmin;
import gestorDeRed.NetError;
import java.io.IOException;
import datos.*;
import gestorDeRed.GestorDeRed;
import java.util.logging.Level;
import java.util.logging.Logger;
import mensajes.Mensaje;
import mensajes.serverclient.DatosCliente;
import mensajes.serverclient.PeticionConsulta;
import mensajes.serverclient.RespuestaPeticionConsulta;


/**
 * Esta clase implementa el hilo que se ejecuta para resolver una consulta.
 * Se encarga de localizar los datos requeridos por el cliente y reenviárselos.
 * 
 * @author pitidecaner
 */
public class ProcesarPeticionBusqueda extends Thread{

    private GestorDeRed<Mensaje> _red;
    private ArchivoClientes _archivos;
    private DatosCliente _cliente;
    private PeticionConsulta _peticion;
    
    public ProcesarPeticionBusqueda(GestorDeRed<Mensaje> red,  
                                    ArchivoClientes ar,
                                    DatosCliente   cliente,
                                    PeticionConsulta peticion) {
        _red = red;
        _archivos = ar;
        _cliente = cliente;
        _peticion = peticion;
    }

    @Override
    public void run(){
        // realiza la busqueda de los archivos
        Archivo[] lista = _archivos.buscar(  _peticion.getCadenaBusqueda());
        
        // construlle una respuesta
        RespuestaPeticionConsulta resp = new RespuestaPeticionConsulta(lista);
        
        // la envia
        try {
            _red.envia(resp, _cliente.getIP(), _cliente.puertoDestino());
        } catch (NetError ex) {
            Logger.getLogger(ProcesarPeticionBusqueda.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
