/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package estadisticas;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Qiang
 */
public class UtilFechas {
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
}
