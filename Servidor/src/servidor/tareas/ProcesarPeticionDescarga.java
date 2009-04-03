package servidor.tareas;

import gestorDeRed.NetError;
import java.io.IOException;
import datos.*;
import gestorDeRed.GestorDeRed;
import java.util.logging.Level;
import java.util.logging.Logger;
import mensajes.Mensaje;
import mensajes.serverclient.DatosCliente;
import mensajes.serverclient.PeticionDescarga;
import mensajes.serverclient.RespuestaPeticionDescarga;


/**
 * Realiza la busqueda de todos los clientes que tienen el fichero indicado
 * 
 * @author pitidecaner
 * @author Salcedonia
 */
public class ProcesarPeticionDescarga extends Thread {

    private GestorDeRed<Mensaje> _red;
    private ArchivoClientes      _archivosClientes;
    private PeticionDescarga     _peticion;
    private DatosCliente         _cliente;
    
    public ProcesarPeticionDescarga(GestorDeRed<Mensaje> red,
                                    ArchivoClientes      archivosClientes,
                                    PeticionDescarga     peticion,
                                    DatosCliente         cliente) {
        _archivosClientes = archivosClientes;
        _cliente = cliente;
        _peticion = peticion;
        _red  = red;
    }

    @Override
    public void run(){
        // realiza la busqueda de los propietarios
        DatosCliente[] lista = _archivosClientes.getPropietarios(_peticion.getHash());
        
        // construye la respuesta
        RespuestaPeticionDescarga resp = new RespuestaPeticionDescarga(_peticion.getNombre(),
                                                            _peticion.getHash(), 
                                                            lista );
        // envia la respuesta
        try {
            _red.envia(resp, _cliente.getIP(), _cliente.getPuertoEscucha());
        } catch (NetError ex) {
            Logger.getLogger(ProcesarPeticionDescarga.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
