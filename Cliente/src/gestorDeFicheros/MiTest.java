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
package gestorDeFicheros;

import datos.*;
import java.util.*;
import java.io.*;

public class MiTest {

    public static void main(String[] args) {

        GestorDisco gestorDisco = new GestorDisco();

        Fragmentador fragmentador = gestorDisco.getFragmentador();

        Ensamblador ensamblador = gestorDisco.getEnsamblador();

        String hashBueno = "1fde78d1baadbfb3c2554f3651b40330";
        Vector<Fragmento> fragmentosTengoBueno;
        //framentos de un fichero completo
        fragmentosTengoBueno = fragmentador.queFragmentosTienes(hashBueno);
        System.out.println("cantidadFragmentosTengoBueno " + fragmentosTengoBueno.size());
        for (int i = 0; i < fragmentosTengoBueno.size(); i++) {
            System.out.println(fragmentosTengoBueno.get(i).toString());
        }
        Vector<Fragmento> fragmentosFaltanBueno = fragmentador.queFragmentosFaltan(hashBueno);
        System.out.println("cantidadFragmentosFaltanBueno " + fragmentosFaltanBueno.size());
        System.out.println();

        //framentos de un fichero que no existe
        String hashMalo = "1fde78d1baadbfb3c2554f3651b40331";
        Vector<Fragmento> fragmentosMalo = fragmentador.queFragmentosTienes(hashMalo);
        System.out.println("cantidadFragmentosMalo " + fragmentosMalo);

        String nombreFileComJar = "comunicaciones.jar";
        File fileComJar = new File(nombreFileComJar);
        String hashAux = MD5Sum.getFileMD5Sum(fileComJar);
        Archivo archivoComJar = new Archivo(fileComJar.getName(), hashAux, fileComJar.length(),
                TipoArchivo.ARCHIVO);
        System.out.println(archivoComJar.toString());
        ensamblador.nuevoArchivoTemporal(archivoComJar);
        //Debo comprobar que el archivo generado tiene el tamano indicado   
        File fileTemp = new File(gestorDisco.getDirectorioTemporales() + "//" + nombreFileComJar + ".tmp");
        //No hace falta comprobarlo, pero casi en un 99% el hash debe ser diferente
        String hashTemp = MD5Sum.getFileMD5Sum(fileTemp);
        //hashTemp es el hash del archivo temporal en cada momento, es decir, cambiara en funcion de 
        //los fragmentos, hasta tener todos, qentonces coincidira con el iriginal
        Archivo archivoTemp = new Archivo(fileTemp.getName(), hashTemp, fileTemp.length(),
                TipoArchivo.ARCHIVO);
        System.out.println(archivoTemp.toString());

        //framentos de un fichero incompleto - para esto se necesita previa creacion    
        Vector<Fragmento> fragmentosSin = fragmentador.queFragmentosTienes(hashAux);
        //Como acabo de crear el fichero temporal no tengo ningun fragmento en los indices
        System.out.println("cantidadFragmentosSin " + fragmentosSin.size());
        for (int i = 0; i < fragmentosSin.size(); i++) {
            //System.out.println( fragmentos.get( i ).toString() );
        }

        System.out.println("cantidadFragmentosBueno " + fragmentosTengoBueno.size());
        Byte[] bytesFragmento3 = fragmentador.dameBytesDelFragmento(fragmentosTengoBueno.get(3));
        Byte[] bytesFragmento0 = fragmentador.dameBytesDelFragmento(fragmentosTengoBueno.get(0));
        Byte[] bytesFragmento11 = fragmentador.dameBytesDelFragmento(fragmentosTengoBueno.get(11));
        Byte[] bytesFragmento16 = fragmentador.dameBytesDelFragmento(fragmentosTengoBueno.get(16));
        Byte[] bytesFragmento6 = fragmentador.dameBytesDelFragmento(fragmentosTengoBueno.get(6));
        System.out.println("bF2:" + bytesFragmento3.length + " bF0:" + bytesFragmento0.length + " bF11:" +
                bytesFragmento11.length + " bF16:" + bytesFragmento16.length + " bF6:" + bytesFragmento6.length);
        //Comprobar al menos que no salen null cuando deberian funcionar
        //Y que son lecturas de 512 menos, posiblemente, el ultimo fragmento del fichero

        //Pido los bytes de un fragmento con hash malo de NO existir
        Fragmento fragmentoErroneoHash = new Fragmento(fragmentosTengoBueno.get(1).getNombre(),
                "1fde78d1baadbfb3c2554f3651b40331", fragmentosTengoBueno.get(1).getTama(),
                fragmentosTengoBueno.get(1).getOffset());
        Byte[] bytesFragmentoErroneoHash = fragmentador.dameBytesDelFragmento(fragmentoErroneoHash);
        System.out.println("bFErroneoHash:" + bytesFragmentoErroneoHash);

        //Pido los bytes de un fragmento con hash malo de existir en otro file
        Fragmento fragmentoErroneoHash2 = new Fragmento(fragmentosTengoBueno.get(1).getNombre(),
                fragmentosTengoBueno.get(4).getHash(), fragmentosTengoBueno.get(1).getTama(),
                fragmentosTengoBueno.get(1).getOffset());
        Byte[] bytesFragmentoErroneoHash2 = fragmentador.dameBytesDelFragmento(fragmentoErroneoHash2);
        System.out.println("bFErroneoHash2:" + bytesFragmentoErroneoHash2);

        //Pido los bytes de un fragmento con offset malo
        Fragmento fragmentoErroneoOffset = new Fragmento(fragmentosTengoBueno.get(1).getNombre(),
                fragmentosTengoBueno.get(1).getHash(), fragmentosTengoBueno.get(1).getTama(),
                fragmentosTengoBueno.get(16).getOffset());
        Byte[] bytesFragmentoErroneoOffset = fragmentador.dameBytesDelFragmento(fragmentoErroneoOffset);
        System.out.println("bFErroneoOffset:" + bytesFragmentoErroneoOffset);
        //Hacer mas pruebas de este


        //Prueba de guardar un Fragmento-Byte sobre un fichero del que tengo 0 partes 
        //(esta dado de alta en temporales)
        Vector<Fragmento> fragmentosActualesComJar = fragmentador.queFragmentosTienes(
                "538b5b9d439778e2527a3e163a1e623b");
        System.out.println("cantidadFragmentosActualesConJar " + fragmentosActualesComJar.size());

        Byte[] bytesFragmentoComJar = bytesFragmento3; //cojo cualquier array de bytes para esta prueba

        Fragmento fragmentoNuevoComJar = new Fragmento(nombreFileComJar,
                "538b5b9d439778e2527a3e163a1e623b", 14724, 1024);
        ensamblador.guardarFragmentoEnArchivo(fragmentoNuevoComJar, bytesFragmentoComJar);
        fragmentosActualesComJar = fragmentador.queFragmentosTienes(
                "538b5b9d439778e2527a3e163a1e623b");
        System.out.println("cantidadFragmentosActualesConJar " + fragmentosActualesComJar.size());
        for (int i = 0; i < fragmentosActualesComJar.size(); i++) {
            System.out.println(fragmentosActualesComJar.get(i).toString());
        }

        //Simulo la ensamblacion de un archivo
        String nombreFilePeli = "ComoPencarJunio.avi";
        File filePeli = new File(nombreFilePeli);
        String hashPeli = MD5Sum.getFileMD5Sum(filePeli);
        Archivo archivoPeli = new Archivo(filePeli.getName(), hashPeli, filePeli.length(),
                TipoArchivo.VIDEO);
        System.out.println(archivoPeli.toString());
        ensamblador.nuevoArchivoTemporal(archivoPeli);
        try {
            RandomAccessFile punteroFicheroRead = new RandomAccessFile(filePeli, "r");
            byte[] bytesFragmentoA = new byte[512], bytesFragmentoB = new byte[512],
                    bytesFragmentoC = new byte[469];
            punteroFicheroRead.seek(1024);
            punteroFicheroRead.read(bytesFragmentoC); //leo los 469

            punteroFicheroRead.seek(0);
            punteroFicheroRead.read(bytesFragmentoA); //leo los 512

            punteroFicheroRead.seek(512);
            punteroFicheroRead.read(bytesFragmentoB); //leo los 512

            Fragmento fragmentoA = new Fragmento(filePeli.getName(), hashPeli, filePeli.length(), 0),
                    fragmentoB = new Fragmento(filePeli.getName(), hashPeli, filePeli.length(), 512),
                    fragmentoC = new Fragmento(filePeli.getName(), hashPeli, filePeli.length(), 1024);

            Vector<Fragmento> fragmentosPeli = fragmentador.queFragmentosTienes(hashPeli);
            System.out.println("cantidadFragmentosTengoActualesPeli " + fragmentosPeli.size());
            for (int i = 0; i < fragmentosPeli.size(); i++) {
                System.out.println(fragmentosPeli.get(i).toString());
            }
            fragmentosPeli = fragmentador.queFragmentosFaltan(hashPeli);
            System.out.println("cantidadFragmentosFaltanActualesPeli " + fragmentosPeli.size());
            for (int i = 0; i < fragmentosPeli.size(); i++) {
                System.out.println(fragmentosPeli.get(i).toString());
            }


            ensamblador.guardarFragmentoEnArchivo(fragmentoC, primitivoAObjeto(bytesFragmentoC));

            fragmentosPeli = fragmentador.queFragmentosTienes(hashPeli);
            System.out.println("cantidadFragmentosTengoActualesPeli " + fragmentosPeli.size());
            for (int i = 0; i < fragmentosPeli.size(); i++) {
                System.out.println(fragmentosPeli.get(i).toString());
            }
            fragmentosPeli = fragmentador.queFragmentosFaltan(hashPeli);
            System.out.println("cantidadFragmentosFaltanActualesPeli " + fragmentosPeli.size());
            for (int i = 0; i < fragmentosPeli.size(); i++) {
                System.out.println(fragmentosPeli.get(i).toString());
            }

            ensamblador.guardarFragmentoEnArchivo(fragmentoA, primitivoAObjeto(bytesFragmentoA));

            fragmentosPeli = fragmentador.queFragmentosTienes(hashPeli);
            System.out.println("cantidadFragmentosTengoActualesPeli " + fragmentosPeli.size());
            for (int i = 0; i < fragmentosPeli.size(); i++) {
                System.out.println(fragmentosPeli.get(i).toString());
            }
            fragmentosPeli = fragmentador.queFragmentosFaltan(hashPeli);
            System.out.println("cantidadFragmentosFaltanActualesPeli " + fragmentosPeli.size());
            for (int i = 0; i < fragmentosPeli.size(); i++) {
                System.out.println(fragmentosPeli.get(i).toString());
            }

            ensamblador.guardarFragmentoEnArchivo(fragmentoB, primitivoAObjeto(bytesFragmentoB));

            fragmentosPeli = fragmentador.queFragmentosTienes(hashPeli);
            System.out.println("cantidadFragmentosTengoActualesPeli " + fragmentosPeli.size());
            for (int i = 0; i < fragmentosPeli.size(); i++) {
                System.out.println(fragmentosPeli.get(i).toString());
            }
            fragmentosPeli = fragmentador.queFragmentosFaltan(hashPeli);
            System.out.println("cantidadFragmentosFaltanActualesPeli " + fragmentosPeli.size());
            for (int i = 0; i < fragmentosPeli.size(); i++) {
                System.out.println(fragmentosPeli.get(i).toString());
            }

            punteroFicheroRead.close();
        } catch (Exception e) {
        }

    //Obtener fragmentos de un archivo temporal

    //probar a guardar uno al que solo le falta un fragmento y qse complete bien

    //probar otro que solo le falta un fragmento y q el utlimo fragmento este mal
    //(no coincidira el hash final)

    }

    private static Byte[] primitivoAObjeto(byte[] bytes) {
        Byte[] oBytesFragmento = new Byte[bytes.length];

        for (int i = 0; i < bytes.length; i++) {
            //oBytesFragmento[ i ] = new Byte( bytes[ i ] );
            oBytesFragmento[i] = Byte.valueOf(bytes[i]);
        }

        return oBytesFragmento;
    }
}
