package datos;

import gestorDeProtocolos.DatosCliente;
import java.util.*;
import tareas.*;

/**
 * Clase de Lista de archivos que proporciona todos los métodos necesarios para
 * el tratamiento de éste tipo de objetos.
 * La estructura empleada es una tabla hash ya que proporciona una eficiencia
 * mayor que otras estructuras para éste propósito.
 * 
 * @author pitidecaner
 * @author Salcedonia
 */
public class ArchivoClientes {

    // ATRIBUTOS
    // <MD5, conexion-PeerCon-IP>
    private HashMap<Archivo, ArrayList<ConexionCliente>> _relacion;
    private int _numeroClientes;

    /**
     * Constructor de la clase ListaArchivos.
     */
    public ArchivoClientes() {
        _relacion = new HashMap<Archivo, ArrayList<ConexionCliente>>();
    }

    /**
     * Añade un archivo a la lista de archivos.
     * 
     * @param a Archivo a añadir a la lista de archivos.
     */
    public synchronized void anadirArchivo(Archivo a, ConexionCliente conexion) {
        //_relacion.add(a);
        ArrayList<ConexionCliente> archivoClientes = new ArrayList<ConexionCliente>();
        archivoClientes.add(conexion);
        _relacion.put(a, archivoClientes);
    }

    /**
     * 
     * @param a
     * @param conexion
     */
    public synchronized void anadirCliente(Archivo a, ConexionCliente conexion) {
        //_relacion.add(a);
        //Recupero los clientes que tienen ese fichero y aniado el nuevo cliente q lo tiene
        ArrayList<ConexionCliente> archivoClientes = _relacion.get(a);
        //System.out.println( "Hash "+a.getHash() );
        //System.out.println( "archivoClientes "+archivoClientes );
        archivoClientes.add(conexion);
    //creo q al manejar referencias, este add ya aniade la conexion al archivo existente.
    //_relacion.put( a, archivoClientes );
    }

    /**
     * Elimina un archivo de la lista de archivos.
     * 
     * @param a Archivo a eliminar de la lista de archivos.
     */
    public synchronized void eliminarArchivo(Archivo a) {        // TODO: esto no puede funcionar asi
        //_relacion.remove(a);
    }

    /**
     * Actualiza la BBDD del servidor con los ficheros que tiene un cliente recién
     * conectado, se comprueba si cada uno de los archivos pertenecían antes al sistema.
     * en tal caso sólo se añade la referencia a este cliente y el posible nombre
     * distinto del archivo.
     *
     * @param conexion la conexión con el cliente.
     * @param listaArchivos la lista de archivos de este cliente.
     */
    public void actualizarDesdeListaCliente(ConexionCliente conexion,
            ListaArchivos listaArchivos) {

        //Cada vez que se actualiza es por que se ha conectado un cliente, aunque mal hecho
        //se debe mirar antes si el cliente tiene o no archivos compartidos.
        //Los archivos en descarga se pueden/deben considerar compartidos
        incrementoNumeroClientes();

        //No me gusta esto aqui, pero no se ha tenido en cuenta el caso

        //Si es el primer cliente, es decir, servidor sin ficheros...
        if (getNumeroArchivos() == 0) {
            System.out.println("Lista de ficheros global vacia. Metemos compartidos del cliente");
            for (int i = 0; i < listaArchivos.size(); i++) {
                //Meto todos los ficheros compartidos del cliente
                anadirArchivo(listaArchivos.get(i), conexion);
            }
        } else { //Sino...
            boolean encontrado = false;

            Archivo archivoServ = null;

            //Iterator it = _relacion.entrySet().iterator();

            for (int i = 0; i < listaArchivos.size(); i++) {

                Iterator it = _relacion.entrySet().iterator();
                //flag encontrado ya que si le encuentro el primero en la lista
                //no pierdo tiempo buscando en el resto xq no va a estar repe
                for (int j = 0; j < getNumeroArchivos() && encontrado == false;
                        j++) {
                    //while ( it.hasNext() ){
                    Map.Entry e = (Map.Entry) it.next();
                    archivoServ = (Archivo) e.getKey();
                    //}
                    //encontrado = listaArchivos.get(i).comparaArchivo( get(j) );
                    //encontrado = _relacion.containsKey( listaArchivos.get(i) );
                    encontrado = comparaHashArchivo(listaArchivos.get(i), archivoServ);
                }
                if (encontrado == false) {
                    System.out.println("No encontrado en el servidor");
                    anadirArchivo(listaArchivos.get(i), conexion);
                //_relacion.add( new Cliente_Archivo(listaArchivos.get(i).getHash() ,listaArchivos.get(i).getNombre(), conexion) );
                } else {
                    System.out.println("Encontrado en el servidor");
                    anadirCliente(archivoServ, conexion);
                    //Hago las relaciones para el encontrado
                    //Lo pongo a false para seguir buscando
                    encontrado = false;
                }
            }
        // buscalo en la BBDD
        //for (Archivo archivoAux : this) 
        //  encontrado = archivoAux.comparaArchivo(archivo);

        // si no existe darlo de alta
        //
        // else {

        // tomar nota de la relación, de existir.
        //for (Cliente_Archivo rel : _relacion) {
        //  if (rel._hash.contentEquals(archivo.getHash())) {

        //  rel._propietarios.add(conexion);
        //    rel._nombresArchivo.add(archivo.getNombre());
        // }
        // }
        //}
        // }
        }
    }

    /**
     * 
     * @param aCliente
     * @param aServidor
     * @return
     */
    public boolean comparaHashArchivo(Archivo aCliente, Archivo aServidor) {
        if (aCliente.getHash().compareTo(aServidor.getHash()) == 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 
     * @return
     */
    public int getNumeroArchivos() {
        return _relacion.size();
    }

    /**
     * 
     */
    public void incrementoNumeroClientes() {
        _numeroClientes++;
    }

    /**
     * 
     * @return
     */
    public int getNumeroClientes() {
        return _numeroClientes;
    }

    /**
     * 
     * @return
     */
    public Iterator getIterador() {
        Iterator it = _relacion.entrySet().iterator();
        return it;
    }

    /**
     * 
     * @return
     */
    public Iterator<Map.Entry<Archivo, ArrayList<ConexionCliente>>> getIteradorGenerico() {
        Iterator<Map.Entry<Archivo, ArrayList<ConexionCliente>>> it = _relacion.entrySet().iterator();
        return it;
    }

    /*Para usar Vector, ArrayList, etc*/
    /*public List getListaArchivos(){
    
    return null;
    }*/

    /*System.out.println("The elements of HashMap are");
    Set set= hashmap.keySet(); 
    Iterator iter = set.iterator(); 
    while(iter.hasNext()){
    Object clave = iter.next();
    Object valor = hashmap.get(clave);
    System.out.println("Key: " + clave + " Value: " + valor);
     */
    public String getListaArchivos() {
        Archivo archivo;
        String lista = "";

        Iterator it = getIterador();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            archivo = (Archivo) e.getKey();
            //System.out.println( "Nombre: "+archivo.getNombre() );
            lista += archivo.getNombre() + "\n";
        }

        return lista;
    }

    /**
     * Buscar por nombre, la versión actual busca aquellos archivos que se llaman
     * exactamente como el nombre indicado.
     * 
     * @param nombre Nombre del archivo buscado.
     * 
     * @return Un array con los datos de los clientes que tienen ese archivo.
     */
    public Archivo[] buscar(String nombre) {

        Archivo archivo;
        Vector<Archivo> lista = new Vector<Archivo>();

        /*for (Archivo a : this) {
        if (a._nombre.contains(nombre))
        lista.add(a);
        }*/
        /*for( int i = 0;  i < getNumeroArchivos();  i++){
        if( _relacion ){
        }
        }*/
        Iterator it = getIterador();

        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            //System.out.println( e.getKey() + " " + e.getValue() );
            //PERMITIR QUE EL ARCHIVO UNICO TENGA VARIOS NOMBRES? Fakes si, pero tb similares
            //System.out.println( e.getKey().getHash() );
            archivo = (Archivo) e.getKey();
            System.out.println(archivo.getNombre());

            if (archivo.getNombre().contains(nombre)) {
                lista.add(archivo);
            }
        }

        System.out.println("Archivos encontrados: <" + lista.size() + ">");
        Archivo[] ars = new Archivo[lista.size()];
        return lista.toArray(ars);
    }

    /**
     * Buscar por tipo de archivo.
     * 
     * @param tipo Tipo del archivo buscado.
     * 
     * @return Un array con los datos de los clientes que tienen ese archivo.
     */
    /*public Archivo[] buscar(TipoArchivo tipo){
    Vector<Archivo> lista = new Vector<Archivo>();
    
    for (Archivo a : this) {
    if (a._tipo == tipo)
    lista.add(a);
    }
    
    Archivo[] ars= new Archivo[lista.size()];
    return lista.toArray(ars);
    }*/

    /**
     * Búsqueda por hash.
     *  
     * @param hash Hash del archivo a buscar.
     * 
     * @return Un array con los datos de los clientes que tienen ese archivo.
     */
    public DatosCliente[] getPropietarios(String hash) {
        Archivo archivo = null;
        ArrayList<ConexionCliente> listaConexionCliente = null;
        DatosCliente[] listaClientes = null;
        boolean encontrado = false;

        /*for (Cliente_Archivo r : _relacion) {
        if (r._hash.contentEquals(hash)){
        for (ConexionCliente cliente : r._propietarios) {
        lista.add(cliente.getDatosCliente());
        }
        break;
        }
        }*/
        //DatosCliente[] clientes= new DatosCliente[lista.size()];

        Iterator<Map.Entry<Archivo, ArrayList<ConexionCliente>>> it = getIteradorGenerico();

        while (it.hasNext() && encontrado == false) {
            Map.Entry<Archivo, ArrayList<ConexionCliente>> e = it.next();
            //System.out.println( e.getKey() + " " + e.getValue() );
            //System.out.println( e.getKey().getHash() );
            archivo = e.getKey();
            System.out.println(archivo.getHash());

            if (archivo.getHash().equals(hash)) {
                //lista.add( archivo );
                System.out.println("Fichero <" + archivo.getNombre() + "> entontrado");
                listaConexionCliente = e.getValue();
                encontrado = true;
            }
        }
        if (encontrado == true) {
            listaClientes = new DatosCliente[listaConexionCliente.size()];
            for (int i = 0; i < listaConexionCliente.size(); i++) {
                listaClientes[i] = listaConexionCliente.get(i).getDatosCliente();
            }
        }

        return listaClientes;
    }
}
