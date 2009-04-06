package datos;

import mensajes.serverclient.ListaArchivos;
import servidor.tareas.ConexionCliente;
import java.util.*;
import mensajes.serverclient.DatosCliente;

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
    // <MD5, lista de propietarios>
    private HashMap<String, ArrayList<DatosCliente>> _relacion;
    private int _numeroClientes;
    private Vector<Archivo> _archivos;
    
    
    /**
     * Constructor de la clase ListaArchivos.
     */
    public ArchivoClientes() {
        _relacion = new HashMap<String, ArrayList<DatosCliente>>();
        _archivos = new Vector<Archivo>();
    }

    /**
     * Añade un archivo a la lista de archivos.
     * 
     * @param a Archivo a añadir a la lista de archivos.
     */
    private synchronized void anadirArchivo(Archivo a, DatosCliente cliente) {
        //_relacion.add(a);
        ArrayList<DatosCliente> archivoClientes = new ArrayList<DatosCliente>();
        archivoClientes.add(cliente);
        _relacion.put(a._hash, archivoClientes);
        _archivos.add(a);
    }

    /**
     * añade la relacción entre un archivo y un cliente.
     * se da por hecho que el archivo ya existe
     * 
     * @param a el archivo que tiene este cliente
     * @param cliente los datos del cliente
     */
    private synchronized void anadirCliente(Archivo a, DatosCliente cliente) {
 
        //Recupero los clientes que tienen ese fichero y aniado el nuevo cliente q lo tiene
        ArrayList<DatosCliente> archivoClientes = _relacion.get(a._hash);
        if (archivoClientes != null)
            archivoClientes.add(cliente);
    }

    /**
     * Elimina un archivo de la lista de archivos.
     * 
     * @param a Archivo a eliminar de la lista de archivos.
     */
    private synchronized void eliminarArchivo(Archivo a) {      
        _relacion.remove(a._hash);
        _archivos.remove(a);
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
    public void actualizarDesdeListaCliente(DatosCliente cliente,
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
                anadirArchivo(listaArchivos.get(i), cliente);
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
                    anadirArchivo(listaArchivos.get(i), cliente);
                //_relacion.add( new Cliente_Archivo(listaArchivos.get(i).getHash() ,listaArchivos.get(i).getNombre(), conexion) );
                } else {
                    System.out.println("Encontrado en el servidor");
                    anadirCliente(archivoServ, cliente);
                    //Hago las relaciones para el encontrado
                    //Lo pongo a false para seguir buscando
                    encontrado = false;
                }
            }
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
    
        Vector<Archivo> lista =  new Vector<Archivo>();
  
        for (Archivo archivo : _archivos) {
           if (archivo.getNombre().contains(nombre))
               lista.add(archivo);
        }
        
        Archivo[ ] arcs = new Archivo[lista.size()];
        for (int i=0; i< lista.size(); i++) {
            arcs[i] = lista.elementAt(i);
        }
        return arcs;
    }


    /**
     * Búsqueda por hash.
     *  
     * @param hash Hash del archivo a buscar.
     * 
     * @return Un array con los datos de los clientes que tienen ese archivo.
     */
    public DatosCliente[] getPropietarios(String hash) {
        
        if (_relacion.containsKey(hash))
        
           return (DatosCliente[]) _relacion.get(hash).toArray();
        else
            return null;
    }
}