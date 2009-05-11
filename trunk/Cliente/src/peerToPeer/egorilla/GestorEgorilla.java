package peerToPeer.egorilla;

import datos.Archivo;
import datos.Fragmento;
import gestorDeConfiguracion.ControlConfiguracionCliente;
import gestorDeConfiguracion.ObservadorControlConfiguracionCliente;
import gestorDeFicheros.GestorDisco;
import gestorDeRed.GestorDeRed;
import java.util.Properties;
import mensajes.Mensaje;
import mensajes.p2p.Tengo;
import mensajes.serverclient.DatosCliente;
import mensajes.serverclient.RespuestaPeticionConsulta;
import peerToPeer.GestorP2P;
import peerToPeer.ObservadorP2P;
import peerToPeer.descargas.AlmacenDescargas;
import peerToPeer.descargas.Descargador;

/**
 *
 * @author Luis Ayuso, José Miguel Guerrero
 * @author Modificado por Javier Sánchez
 * 
 * Esta clase utiliza parametros de configuracion por tanto va a ser observadora de ControlConfiguracionCliente 
 * y sera notificada cuando cambie la configuracion dando un tratamiento adecuado al cambio (o no hacer nada).
 * //TODO: Dar tratamiento a los cambios en la configuracion del cliente (si asi se desea).
 * 
 */
public class GestorEgorilla extends Thread implements ObservadorControlConfiguracionCliente,
                                                      GestorP2P{
    /** gestor de red para realizar las comunicaciones. */
    private GestorDeRed<Mensaje> _red;
    /** se encarga de enviar los mensajes */
    private GestorMensajes _colaMensajes;
    /** variable para ver si estamos o no conectados */
    private boolean _conectado;
    /** almacen de descargas donde se almacenan estas */
    private AlmacenDescargas _descargas;
    /** el descargador es un hilo que se encarga de gestionar las descargas*/
    private Descargador     _descargador;
    /** el servidor egorilla se encarga de recibir los mensajes y tratarlos*/
    private ServidorP2PEgorilla _server;

    /**
     * constructor de la clase
     * @param puerto puerto por el que se escucha
     * @param disco, el gestor de disco
     */
    public GestorEgorilla(int puerto, GestorDisco disco) {
        //inicialmente no estamos conectados
        _conectado =false;

        _descargas = new AlmacenDescargas();
        _server = new ServidorP2PEgorilla(this);
    }

//------------------------------------------------------------------------------
//--------------------- interfaz ObservadorControlConfiguracionCliente ---------
//------------------------------------------------------------------------------
    @Override
    public void cambioEnPropiedades(ControlConfiguracionCliente obj, Properties propiedades) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void descargaCompletada(Archivo archivo) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
    
//------------------------------------------------------------------------------
//--------------------- interfaz GestorP2P -------------------------------------
//------------------------------------------------------------------------------

    @Override
    public void desconectar(){
        
    }

    @Override
    public AlmacenDescargas getAlmacenDescargas() {
       return _descargas;
    }

    @Override
    public void nuevaConsulta(String cadena) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void nuevaDescarga(Archivo a) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean estaConectadoAServidor() {
        return _conectado;
    }

    @Override
    public void pausarDescarga(Archivo a) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void eliminarDescarga(Archivo a) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void pedirPropietariosaServidor(Archivo archivo) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void reanudarDescarga(Archivo a) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void conectarAServidor(String ip, int puerto) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void agregarObservador(ObservadorP2P obs) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void eliminaObservador(ObservadorP2P obs) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    void DescargaFichero(Archivo a, DatosCliente[] lista) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    void actualizaDescarga(Tengo reciv) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

//------------------------------------------------------------------------------
//------------------- Metodos del paquete --------------------------------------
//------------------------------------------------------------------------------
    /**
     * encola un mensaje para ser enviado por la red
     * @param msj
     */
    public void addMensajeParaEnviar(Mensaje msj) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    void conectado() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    void enviaListaArchivos() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    void fragmentoDescargado(Fragmento f, Byte[] parte) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    void perdidaDeConexion(String ip) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    void reanudarDescargas() {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    void resultadoBusqueda(RespuestaPeticionConsulta respuestaPeticionConsulta) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

//------------------------------------------------------------------------------
//------------------- Metodos del privados -------------------------------------
//------------------------------------------------------------------------------
    
}
