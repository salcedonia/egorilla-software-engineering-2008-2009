/* 
	This file is part of eGorilla.

    eGorilla is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    eGorilla is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with eGorilla.  If not, see <http://www.gnu.org/licenses/>.
*/
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestorDeEstadisticas;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Qiang
 */
public class UtilFechas {
    private static final int  conversorMinutos = 60000;
    private static final int  conversorSegundos = 1000;
     public static Integer dameFecha(Date date) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(date);
        StringBuilder fecha = new StringBuilder();
        fecha.append(calendario.get(Calendar.YEAR));
        fecha.append(calendario.get(Calendar.MONTH));
        fecha.append(calendario.get(Calendar.DAY_OF_MONTH));
        return Integer.parseInt(fecha.toString());
     }

     public static Integer dameHora(Date date) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(date);
        StringBuilder fecha = new StringBuilder();
        fecha.append(calendario.get(Calendar.HOUR));
        fecha.append(calendario.get(Calendar.MINUTE));
        fecha.append(calendario.get(Calendar.SECOND));
        return Integer.parseInt(fecha.toString());
     }
     
     
      public static Integer dameHoras(Date date) {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(date);
        StringBuilder fecha = new StringBuilder();
        fecha.append(calendario.get(Calendar.HOUR));
        return Integer.parseInt(fecha.toString());
     }

     public static Date stringToDate(String fecha) {
        String[] tokens = fecha.split(":");
        if (tokens.length != 6 ) {
            System.out.println("Formato de fecha erroneo");
        //    return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, 10, 25);
        
        String year = tokens[0];
        String month = tokens[0];
        String day = tokens[0];
        String hour = tokens[0];
        String mins = tokens[0];
        String sec = tokens[0];
        return  calendar.getTime();

     }

    public static long diferenciaMinutos(Date nuevaFecha, Date ultimaActualizacion) {
        long newDate = nuevaFecha.getTime();
        long oldDate = ultimaActualizacion.getTime();
        long minutos = (newDate - oldDate) / conversorMinutos;
        return minutos;
    }
    
     public static long diferenciaSegundos(Date nuevaFecha, Date ultimaActualizacion) {
        long newDate = nuevaFecha.getTime();
        long oldDate = ultimaActualizacion.getTime();
        long segundos = (newDate - oldDate) / conversorSegundos;
        return segundos;
    }
     
    public static int diferenciaIntervalos(Date nuevaFecha, Date ultimaActualizacion, int intervalo) {
        if (nuevaFecha.before(ultimaActualizacion)) {
            Date aux = nuevaFecha;
            nuevaFecha =  ultimaActualizacion;
            ultimaActualizacion = aux;
        }
        long newDate = nuevaFecha.getTime();
        long oldDate = ultimaActualizacion.getTime();
        long segundos = (newDate - oldDate) / conversorSegundos;
        return Math.round(segundos/intervalo);
    }
    public static Date restarMinutos(Date fecha, int intervalo) {
        intervalo = intervalo * conversorMinutos;
        long newDate = fecha.getTime()- intervalo;
        Date nuevaFecha = new Date(newDate);
        return nuevaFecha;
    }
    
    public static Date restarSegundos(Date fecha, int intervalo) {
        intervalo = intervalo * conversorSegundos;
        long newDate = fecha.getTime()- intervalo;
        Date nuevaFecha = new Date(newDate);
        return nuevaFecha;
    }
    
     public static Date sumarSegundos(Date fecha, int intervalo) {
        intervalo = intervalo * conversorSegundos;
        long newDate = fecha.getTime()+ intervalo;
        Date nuevaFecha = new Date(newDate);
        return nuevaFecha;
    }
}
