/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package estadisticas;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Qiang
 */

public class ModuloEstadisticas implements ObservadorDatos, GestorEstadisticas{
    private static ModuloEstadisticas _instancia;
    /*
     * Estadisticas globales
     * Estadisticas de sesion
     *
8    * */
     TreeMap<Integer,Long> _datosGlobal;
     TreeMap<Integer,Long> _datosSesion;
     TreeMap<Integer,Long> _datosLocal;
     int _numFicherosGlobal;
     int _numFicherosSesion;
     String archivo = "..\\datos.bin";



    private ModuloEstadisticas(){

        _numFicherosGlobal = cargaNumeroFicheros();
        _datosGlobal = cargarDatosGlobal();
        _datosSesion = new TreeMap<Integer,Long>();
        _datosLocal = new TreeMap<Integer,Long>();
    }


    private void actualizaGlobal(Date date, long longitud) {
        Integer fecha = UtilFechas.dameFecha(date);
        Long valor = _datosGlobal.get(fecha);
        valor = valor != null ? valor+longitud : longitud;
        _datosGlobal.put(fecha, valor);
    }

    private void actualizaSesion(SortedMap<Integer, Long> actualizacion) {
        Integer key;
        long valor = 0;
        //TODO esto hay que depurarlo
        if (actualizacion.isEmpty()) {
            return;
        }
        if (!_datosSesion.isEmpty()) {
            Integer ultimo = _datosSesion.lastKey();
            int tope = ultimo + 1000;
            actualizacion.tailMap(ultimo);
            actualizacion.headMap(ultimo);
        } else {
            key = actualizacion.firstKey();
            for (Long datos : actualizacion.values()) {
                valor += datos;
            }
            _datosSesion.put(key, valor);
        }
    }

    private int cargaNumeroFicheros() {
          //TODO Hay que realizar el volcado
        int valor = 0;
        try
		{
			//Se abre el fichero donde se hará la copia
			FileInputStream fileOutput = new FileInputStream (archivo);
            ObjectInputStream reader = new ObjectInputStream(fileOutput);
            valor = reader.readInt();
            reader.close();
			// Cierre de los ficheros
		}
		catch (IOException e)
		{
			System.err.println("Error al cargar los datos de estadistica");
		}
        return valor;
    }

    private TreeMap<Integer, Long> cargarDatosGlobal() {
        //TODO:  Hay que ralizar la carga.
       return new TreeMap<Integer,Long>();

    }

    private void volcarDatosGlobal(ObjectOutputStream writer) {
        Long cantidad;
      //TODO Hay que realizar el volcado
        try
		{
			//Se abre el fichero donde se hará la copia
            writer.writeInt(_datosGlobal.size());
            for (int valor : _datosGlobal.keySet()) {
                cantidad = _datosGlobal.get(valor);
                writer.writeInt(valor);
                writer.writeLong(cantidad);
            }
             writer.flush();
		}
		catch (Exception e)
		{
            e.printStackTrace();
			System.err.println("Error al volcar los datos de estadistica");
		}
    }

    private void volcarNumeroFicheros(ObjectOutputStream writer, int i) {
        try
		{
            writer.writeInt(i);
            writer.flush();
		}
		catch (Exception e)
		{
            e.printStackTrace();
			System.err.println("Error al volcar  los datos de estadistica");
		}
    }

     public synchronized static ModuloEstadisticas dameInstancia(){
        if (_instancia == null) {
            _instancia = new ModuloEstadisticas();
        }
        return _instancia;
    }

    public void llegadaDatos(long longitud) {
        Date date = new Date();
        Integer fecha = UtilFechas.dameFecha(date);
        Integer hora = UtilFechas.dameHora(date);
        synchronized (this) {
            _datosLocal.put(hora, longitud);
            int tope = hora - 1000;
            actualizaSesion(_datosLocal.headMap(tope));
            actualizaGlobal(date, longitud);
        }
    }

    public synchronized void llegadaFichero(int cantidad) {
         _numFicherosGlobal++;
         _numFicherosSesion++;
        //TODO volcarNumeroFicheros(_numFicherosGlobal);
    }

    public synchronized void inicioSesion() {
        _datosSesion = new TreeMap<Integer,Long>();
        _datosLocal = new TreeMap<Integer,Long>();
        _numFicherosSesion = 0;
    }

    public synchronized  void reiniciarTodo() {
        inicioSesion();
        _datosGlobal = new TreeMap<Integer,Long>();
        _numFicherosGlobal = 0;
        // volcarNumeroFicheros(_numFicherosGlobal);
        // TODO volcarDatosGlobal();

    }

    public synchronized  void cerrar() {
        FileOutputStream fileOutput = null;
        try {
            
            fileOutput = new FileOutputStream(archivo);
            ObjectOutputStream writer = new ObjectOutputStream(fileOutput);
            volcarNumeroFicheros(writer, _numFicherosGlobal);
            volcarDatosGlobal(writer);
        } catch (IOException ex) {
            Logger.getLogger(ModuloEstadisticas.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fileOutput.close();
            } catch (IOException ex) {
                Logger.getLogger(ModuloEstadisticas.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void reniciarSesion() {
        inicioSesion();
    }

    public List<Long> getDatosEntreDosFechas(Date inicio, Date fin) {
        Integer begin = UtilFechas.dameFecha(inicio);
        Integer end = UtilFechas.dameFecha(fin);
        SortedMap<Integer, Long> subList = _datosGlobal.subMap(begin, end);
        return (List<Long>) subList.values();
    }

    public long getDatosUnaFecha(Date dia) {
        Integer fecha = UtilFechas.dameFecha(dia);
        return _datosGlobal.get(fecha);
        
    }

    public List<Long> getDatosSesion() {
      List<Long> datos = new ArrayList<Long>();
        datos.addAll(_datosSesion.values());
        return datos;
    }

    public int getFicherosDescargadosGlobal() {
        return _numFicherosGlobal;
    }

    public int getFicherosDescargadosSesion() {
        return _numFicherosSesion;
    }

    public double getVelocidadActual() {
        int cont = 0;
        for (Long valor :_datosLocal.values()) {
            cont += valor;
        }
        _datosLocal.lastKey();
        int periodo = _datosLocal.lastKey() - _datosLocal.firstKey();
        return(cont/periodo);
    }
    public long getTotalDatosSesion() {
        long cont = 0;
        for (long cant :_datosSesion.values()) {
            cont += cant;
        }
        for (long cant :_datosLocal.values()) {
            cont += cant;
        }
        return cont;
    }

    public long totalDescarga() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


 

}
