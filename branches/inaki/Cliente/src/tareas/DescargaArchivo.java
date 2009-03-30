package tareas;

import gestorDeFicheros.ParteDeArchivo;
//import gestorDeProtocolos.RespuestaPeticionDescarga;

import gestorDeRed.GestorDeRed;

import gestorDeRed.ConexionPeer;
import java.io.IOException;
import java.net.Socket;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import mensajes.serverclient.RespuestaPeticionDescarga;

/**
 * Hilo de ejecucion particular para la descarga de un archivo entre varios
 * Clientes eGorilla.
 * 
 * @author Luis Ayuso, Ivan Munsuri, Javier Salcedo
 */
@Deprecated
public class DescargaArchivo extends Thread {

    // ATRIBUTOS
    private RespuestaPeticionDescarga _respuestaDescarga;
    private String _deseado;
    private String _directorio;

    /**
     * 
     * @param respuestaDescarga
     * @param deseado
     */
    public DescargaArchivo(RespuestaPeticionDescarga respuestaDescarga, String deseado) {
        
        _respuestaDescarga = respuestaDescarga;
        _deseado = deseado;
        _directorio = "incoming";
    }

    /**
     * 
     */
    @Override
    public void run() {
        
        String ip;
        int puerto;

        //Si el fichero no tiene usuarios conectados (aunque no deberia estar ni el fichero en la lista)
        //Luego esto no deberia cumplirse nunca
        if (_respuestaDescarga.getLista().length == 0) {
            System.err.println("No hay fuentes, ningun cliente tiene el archivo.");
        } else {
            //System.out.println( "Conecta con un usuario." );
            try {
                //En principio solo se pide partes a uno de los usuarios q tiene el archivo.
                //Problema de solo a uno esq al q se lo pide puede estar ocupado, subiendo ya a
                //varios clientes y entonces, al no controlar esto, fallo.
                //En el futuro permitir q se pida a varios clientes.
                ip = _respuestaDescarga.getLista()[0].getIP();
                puerto = _respuestaDescarga.getLista()[0].getPuertoEscucha();

                ConexionPeer conexionPeer = new ConexionPeer(new Socket(ip, puerto));

                conexionPeer.enviarObjeto(_deseado);

                ArrayList<Byte[]> partes = new ArrayList<Byte[]>();
                //'Bucle' donde va recibiendo todos las partes.

                //Si tiene mas de una parte, necesito saber primero cuantas partes voy a recibir
                //Para saber cuando parar el bucle de recibir
                int cantidadDePartes = conexionPeer.recibirEntero(); //cantidad de partes

                for (int i = 0; i < cantidadDePartes; i++) {
                    Byte[] parte = (Byte[]) conexionPeer.recibirObjeto();
                    System.out.println("Parte recibida");
                    partes.add(parte);
                    System.out.println("<"+ partes.size() +"> partes recibidas");
                }

                //Escribir los partes que forman el fichero.
                ParteDeArchivo escritorFicheroDePartes = new ParteDeArchivo(_directorio, "ficheroDescargado" + _deseado, _deseado);

                escritorFicheroDePartes.setPartes(partes);

                conexionPeer.cerrarComunicacion();

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(DescargaArchivo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(DescargaArchivo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
