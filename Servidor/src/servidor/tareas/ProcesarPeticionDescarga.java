package servidor.tareas;

import gestorDeRed.NetError;
import datos.*;
import gestorDeRed.GestorDeRed;
import java.util.ArrayList;
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
                                    ArchivoClientes archivosClientes, 
                                    DatosCliente cliente, 
                                    PeticionDescarga peticion) {
        _archivosClientes = archivosClientes;
        _cliente = cliente;
        _peticion = peticion;
        _red  = red;
    }
    
   

    @Override
    public void run(){
        // realiza la busqueda de los propietarios
        DatosCliente[] lista = _archivosClientes.getPropietarios(_peticion.getHash());
        
        
        //elimina a este cliente de la lista, de estar
        ArrayList<DatosCliente> arr = new ArrayList<DatosCliente>();
        for (int i = 0; i < lista.length; i++) 
            if (!_cliente.getIP().equals(lista[i].getIP()))
                arr.add(lista[i]);
        
        lista  = new DatosCliente[arr.size()];
        for (int i = 0; i < arr.size(); i++) 
           lista[i] = arr.get(i);
            
        
        Archivo arc = _archivosClientes.buscarPorHash(_peticion.getHash());
        
        // construye la respuesta
        RespuestaPeticionDescarga resp = new RespuestaPeticionDescarga(arc.getNombre(),
                                                            _peticion.getHash(), 
                                                            arc._tamano,
                                                            arc._tipo,
                                                            lista );
        
        resp.setDestino(_cliente.getIP(), _cliente.getPuertoEscucha());

           //TODO: logger
        System.out.println("\n llega peticion de descarga <"+
                            _peticion.getHash()+"> desde" +
                            _cliente.getIP());
        System.out.println("se responde con " + lista.length + " clientes propietarios");
        
        // envia la respuesta
        try {
            _red.envia(resp, _cliente.getIP(), _cliente.getPuertoEscucha());
        } catch (NetError ex) {
            Logger.getLogger(ProcesarPeticionDescarga.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
