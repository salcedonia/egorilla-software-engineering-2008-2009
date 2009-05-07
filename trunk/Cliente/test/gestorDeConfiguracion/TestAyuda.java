/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gestorDeConfiguracion;

import java.net.URL;

/**
 *
 * @author usuario_local
 */
public class TestAyuda {
    public static void main(String[] args) throws ControlConfiguracionClienteException{
    String NAVEGADOR_WIN= "rundll32 url.dll,FileProtocolHandler "; // el predeterminado del S.O.
    String NAVEGADOR_LINUX= "konqueror "; // mozilla, etc

    String cmd,navegador;


    String os = System.getProperty("os.name");
    
//new ApplicationLauncher("http://www.mipaginaweb.com"); 
//
//new ApplicationLauncher("home/midirectorio/archivo.mp3"); 
//
//Aquí os dejo el código modificado:
//
//import java.io.*;
//
//public class ApplicationLauncher {
//private static String linuxDesktop = null;
//
//public ApplicationLauncher(String args) {
//if(args.indexOf("http") != -1) {
//launchURL(args);
//} else {
//launchDefaultViewer(args);
//}
//}
//private static String getEnv(String envvar) {
//try{
//Process p = Runtime.getRuntime().exec("/bin/sh echo $" + envvar);
//BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
//String value = br.readLine();
//if(value == null) {
//return "";
//} else {
//return value.trim();
//}
//} catch(Exception error) {
//return "";
//}
//}
//private static String getLinuxDesktop() {
//// Solo se averigua el entorno de escritorio una vez, después se almacena en la variable estática
//if(linuxDesktop != null) {
//return linuxDesktop;
//}
//if(!getEnv("KDE_FULL_SESSION").equals("") || !getEnv("KDE_MULTIHEAD").equals("")) {
//linuxDesktop="kde";
//} else if(!getEnv("GNOME_DESKTOP_SESSION_ID").equals("") || !getEnv("GNOME_KEYRING_SOCKET").equals("")) {
//linuxDesktop="gnome";
//} else {
//linuxDesktop="";
//}
//return linuxDesktop;
//}
//public static Process launchURL(String url) {
//try {
//if(System.getProperty("os.name").toUpperCase().indexOf("95") != -1) {
//return Runtime.getRuntime().exec(new String[]{"command.com", "/C", "start", url});
//}
//if(System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") != -1) {
//return Runtime.getRuntime().exec(new String[]{"cmd.exe", "/C", "start", url});
//}
//if(System.getProperty("os.name").toUpperCase().indexOf("MAC") != -1) {
//return Runtime.getRuntime().exec(new String[]{"open", url});
//}
//if (System.getProperty("os.name").toUpperCase().indexOf("LINUX") != -1 ) {
//if(getLinuxDesktop().equals("kde")) {
//return Runtime.getRuntime().exec(new String[]{"kfmclient", "exec", url});
//} else {
//return Runtime.getRuntime().exec(new String[]{"gnome-open", url});
//}
//}
//} catch(IOException ioex) {
//System.out.println(ioex);
//}
//return null;
//}
//public static Process launchDefaultViewer(String filepath) {
//return launchURL(new File(filepath).getAbsolutePath());
//}
//}    
//    if (os!= null && os.startsWith("Win"))
//        navegador= NAVEGADOR_WIN;
//    else
//        navegador= NAVEGADOR_LINUX;
//    try{

//        URL web = this.getClass().getResource("C:\\Documents and Settings\\usuario_local\\.eclipse\\org.eclipse.platform_3.3.0_274290615\\configuration\\org.eclipse.osgi\\bundles\\1063\\1\\.cp\\docs\\api\\index.html");
//        if(web.startsWith("file"))
//        web = web.substring(5);
//        cmd= navegador +" file://"+ web+"/../../../../manual/index.htm";
//        //cmd= navegador +" file://"+ web;
//        System.out.println(cmd);
//        Process p = Runtime.getRuntime().exec(cmd);
//
//    }catch (Exception e){ 
//        JOptionPane.showMessageDialog(null,e.get...
//    }
    }
}
