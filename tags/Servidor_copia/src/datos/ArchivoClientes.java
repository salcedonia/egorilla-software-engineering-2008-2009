package datos;

import mensajes.serverclient.ListaArchivos;
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
    private Hashtable<String, ArrayList<DatosCliente>> _relacion;
    private int _numeroClientes;
    private Vector<Archivo> _archivos;
    
    
    /**
     * Constructor de la clase ListaArchivos.
     */
    public ArchivoClientes() {
        _relacion = new Hashtable<String, ArrayList<DatosCliente>>();
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

        //Si es el primer cliente, es decir, servidor sin ficheros...
        if (getNumeroArchivos() == 0) {
            System.out.println("Lista de ficheros global vacia. Metemos compartidos del cliente");
            for (int i = 0; i < listaArchivos.size(); i++) {
                //Meto todos los ficheros compartidos del cliente
                anadirArchivo(listaArchivos.get(i), cliente);
            }
        } else { //Sino...

            for (int i = 0; i < listaArchivos.size(); i++) {
                if (_relacion.containsKey(listaArchivos.get(i)._hash)){
                    // ya lo tenemos, actualizamos su lista
                    _relacion.get (listaArchivos.get(i)._hash).add(cliente);
                }
                else{
                    // es nuevo, nueva entrada
                    ArrayList<DatosCliente> lista = new ArrayList<DatosCliente>();
                    lista.add(cliente);
                    _archivos.add(listaArchivos.get(i));
                    _relacion.put(listaArchivos.get(i)._hash, lista);
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
        return _archivos.size();
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
        
        if (_relacion.containsKey(hash)){
            ArrayList<DatosCliente> a = _relacion.get(hash);
            DatosCliente[] l = new DatosCliente[a.size()];
            for (int i = 0; i < a.size(); i++) 
                l[i] = a.get(i);
                
            return l;
        }
        else
            return new DatosCliente[0];
    }

    /**
     * eliminar propietario
     *
     * @param ip la ip que ya no esta online
     */
    public void eliminaPropietario(DatosCliente datos){
        _numeroClientes --;

        ArrayList<Archivo> tmp = new ArrayList<Archivo>();
        ArrayList<DatosCliente> eso;

        for (Archivo archivo : _archivos) {
            eso = _relacion.get(archivo._hash);
            if (eso != null) {
                eso.remove(datos);
                if (eso.size() == 0) {
                    //nos hemos quedado sin propietarios para el archivo, lo borramos
                    tmp.add(archivo);
                }
            }
        }
        for (Archivo archivo : tmp) {
            _archivos.remove(archivo);
            _relacion.remove(archivo.getHash());
        }
    }
    
    public Archivo buscarPorHash (String hash){
        
        // Vector<Archivo> lista =  new Vector<Archivo>();
         
         //boolean respuesta = false;
        int pos = 0;
        
        Archivo arch = null;
  
        for (Archivo archivo : _archivos) {
           
           if (archivo.getHash().contains(hash)){
              arch = _archivos.elementAt(pos);
              break;
           }
           pos ++;
              
        }
        
        return arch;
        
    }
}
