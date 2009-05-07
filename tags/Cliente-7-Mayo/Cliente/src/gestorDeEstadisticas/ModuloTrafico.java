/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestorDeEstadisticas;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * @see GestorEstadisticas.
 * @author Qiang
 */
public abstract class ModuloTrafico {
   // cantidad de datos descargados.
    protected Double datosGlobal;
    protected Double datosSesion;
    protected Date fechaInicioSesion;
    protected Date ultimaActualizacion;
    protected LinkedList<Double> listaVelocidadSesion;
    protected LinkedList<Double> listaDatosSesion;
   
    // velocidad media de descarga.
    protected double velocidadGlobal;
    protected int pesoGlobal;
    protected double velocidadSesion;
    protected int pesoSesion;
    
    protected static int tiempoMaximo = 120;
    protected static int varianza = 10;    // Cantidad de datos para calcular la velocidad media
    protected static int intervaloActualizacion = 30;
    
    protected void actualizarVelocidades(int count) {
        /* El pivote representa la posicion del array desde donde hay que actualizar 
         * el array de velocidades,abarca desde 0 a listaVelocidadSesion.size()-1.
         */
        //int pivote = Math.max(listaVelocidadSesion.size()-(count+1), 0);
        int pivote =0;
        if (count == 0) {
            pivote = Math.max(listaVelocidadSesion.size()-1,0);
        } else  {
            pivote = Math.min(tiempoMaximo - (count + 1), listaVelocidadSesion.size());
        }
        if (listaVelocidadSesion.isEmpty()) {
        }
        
        if (listaVelocidadSesion.size() > tiempoMaximo - count) {
            int overflow = listaVelocidadSesion.size() + count - tiempoMaximo;
            for (int i = 0; i < overflow; i++) {        
               listaVelocidadSesion.remove(0);
            }
        }
        //Apartir de "intervalo", donde viene los bytes llegados calculamos la velocidad media para esa posicion
        List<Double> intervalo;
        if (count ==0 ) {
             intervalo = getIntervalo(pivote);
                double cantidad = 0;
                for (Double valor : intervalo) {
                    cantidad += valor;
                }
                int numerador = intervalo.isEmpty() ? 1 : intervalo.size();
                cantidad = cantidad / (numerador * intervaloActualizacion);
                if (listaVelocidadSesion.size() <= pivote) {
                    listaVelocidadSesion.add(pivote, cantidad);
                } else {
                    listaVelocidadSesion.set(pivote, cantidad);
                }
                //Calculamos la velocidad media de la sesion.
                velocidadSesion = (((velocidadSesion * pesoSesion) + listaVelocidadSesion.getLast())) / (pesoSesion + 1);
                pesoSesion++;
        } else {
            for (int i = 0; i < count; i++) {
                intervalo = getIntervalo(pivote + i);
                double cantidad = 0;
                for (Double valor : intervalo) {
                    cantidad += valor;
                }
                int numerador = intervalo.isEmpty() ? 1 : intervalo.size();
                cantidad = cantidad / (numerador * intervaloActualizacion);
                if (listaVelocidadSesion.size() <= pivote + i) {
                    listaVelocidadSesion.add(pivote + i, cantidad);
                } else {
                    listaVelocidadSesion.set(pivote + i, cantidad);
                }
                //Calculamos la velocidad media de la sesion.
                velocidadSesion = (((velocidadSesion * pesoSesion) + listaVelocidadSesion.getLast())) / (pesoSesion + 1);
                pesoSesion++;
            }
        }
    }
     protected List<Double> getIntervalo(int pivote) {
        int valor = pivote;
        int inferior = 0;
        int superior = 0;
        if (listaDatosSesion.size() < varianza) {
            return listaDatosSesion;
        } else if (pivote > listaDatosSesion.size() - varianza / 2) {
            superior = listaDatosSesion.size() - 1;
            inferior = listaDatosSesion.size() - varianza;
        } else if (pivote < varianza / 2) {
            superior = varianza - 1;
            inferior = 0;
        } else {
            superior = valor + varianza / 2;
            inferior = valor - varianza / 2;
        }
        return listaDatosSesion.subList(inferior, superior);
    }
     
    public void llegadaDatos(double longitud) {
        Date nuevaFecha = new Date();
        datosSesion += longitud;
        // Numero de intervalos a actualizar (0, 120)
        int numIntervalos = UtilFechas.diferenciaIntervalos(nuevaFecha, ultimaActualizacion, intervaloActualizacion);
        if (numIntervalos > 0) {
            ultimaActualizacion = nuevaFecha;
        }
        int capacidadTope = tiempoMaximo - numIntervalos;
        Double value = 0.0;
        if (numIntervalos == 0) {
            if (!listaDatosSesion.isEmpty()) {
                value = listaDatosSesion.removeLast();
            }
            value += longitud;
            listaDatosSesion.addLast(value);
        } else {
            if (listaDatosSesion.size() >= capacidadTope) {
                for (int i = 0; i < listaDatosSesion.size() - capacidadTope; i++) {
                    listaDatosSesion.poll();
                }
            }
            for (int i = 0; i < numIntervalos - 1; i++) {
                listaDatosSesion.addLast(0.0);
            }
            listaDatosSesion.add(longitud);
        }
        actualizarVelocidades(numIntervalos);
    }
    
    public Date getFechaInicio() {
        int cantidad = listaVelocidadSesion.size() / 2;
        return UtilFechas.restarMinutos(ultimaActualizacion, cantidad);
    }
    
    public ArrayList<Double> getListaVelocidadMediaSesion() {
        ArrayList retorno = new ArrayList();
         retorno.addAll(listaVelocidadSesion);
         return retorno;
    }

    public List<Double> getListaDatosMediaSesion() {
        return listaDatosSesion;
    }
    public double getTotalDatosSesion() {
        return datosSesion;
    }

    public double getTotalDatosGlobal() {
        return datosGlobal + datosSesion;
    }
    public double getVelocidadMediaSesion() {
        return velocidadSesion;
    }

    public double getVelocidadMediaGlobal() {
        double velocidad = ((velocidadGlobal * pesoGlobal) + (velocidadSesion * pesoSesion)) / (pesoSesion + pesoGlobal);
        return velocidad;
    }
    public double getVelocidadActual() {
        this.llegadaDatos(0);
        return listaVelocidadSesion.getLast();
    }


}
