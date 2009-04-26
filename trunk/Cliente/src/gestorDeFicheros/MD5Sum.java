package gestorDeFicheros;

import java.io.*;
import java.security.*;

/**  
 * Clase encarga del calculo del algoritmo MD5 a aplicar sobre los archivos 
 * de la aplicacion.
 * 
 * @author Ivan Munsuri basado en el codigo de jscour@priest.com
 */
public class MD5Sum {
    
    // Alrededor 356000 bytes... < 308,032 bytes
    
    // CONSTANTES
    //public static long SCOUR_MD5_BYTE_LIMIT = 10000;
    public final static int SCOUR_MD5_BYTE_LIMIT = (300 * 1024);
    
    // ATRIBUTOS
    private static MessageDigest _md = null;

    /**
     * Constructor de la clase md5Sum. Calcula el MD5 de una forma compatible 
     * sobre la que el protocolo scour.net codifica sus contraseñas.
     * 
     *	@param str El nombre del archivo para el cual se calcula el MD5.
     * 
     *	@return	El codigo MD5.
     */
    public static String md5Sum(String str) {
        try {
            return md5Sum(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    public static String md5Sum(byte[] input) {
        return md5Sum(input, -1);
    }

    public static String md5Sum(byte[] input, int limit) {
        try {
            if (_md == null) {
                _md = MessageDigest.getInstance("MD5");
            }
            _md.reset();
            byte[] digest;

            if (limit == -1) {
                digest = _md.digest(input);
            } else {
                _md.update(input, 0,
                        limit > input.length ? input.length : limit);
                digest = _md.digest();
            }

            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < digest.length; i++) {
                hexString.append(hexDigit(digest[i]));
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }

    /**
     * Convierte un digito hexadecimal a un String, usado por el metodo md5Sum.
     * 
     * @param x	El digito a traducir.
     * 
     * @return El codigo hexadecimal correspondiente para el digito.
     */
    static private String hexDigit(byte x) {
        StringBuffer sb = new StringBuffer();
        char c;

        // First nibble
        c = (char) ((x >> 4) & 0xf);
        if (c > 9) {
            c = (char) ((c - 10) + 'a');
        } else {
            c = (char) (c + '0');
        }

        sb.append(c);

        // Second nibble
        c = (char) (x & 0xf);
        if (c > 9) {
            c = (char) ((c - 10) + 'a');
        } else {
            c = (char) (c + '0');
        }

        sb.append(c);
        return sb.toString();
    }

    /**
     * Obtener el codigo MD5 de un archivo. Scour solo cuenta los primeros
     * SCOUR_MD5_BYTE_LIMIT bytes de un archivo para calcular los codigos 
     * (probablemente para una mejor eficiencia o una mejor comparacion entre 
     * las cuentas y las descargas sin finalizar).
     * 
     *	@param f El fichero a analizar.
     * 
     *	@return El codigo MD5 asociado al fichero.
     * 
     *	@throws IOException al ocurrir un error de IO.
     */
    public static String getFileMD5Sum(File f){
      
      String sum = null;
      try{
        FileInputStream in = new FileInputStream( f.getAbsolutePath() );
        
        byte[] b = new byte[1024];
        int num = 0;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        
        while ((num = in.read(b)) != -1) {
          out.write(b, 0, num);
          
          if (out.size() > SCOUR_MD5_BYTE_LIMIT) {
            sum = md5Sum(out.toByteArray(), SCOUR_MD5_BYTE_LIMIT);
            break;
          }
        }
        
        if (sum == null) {
          sum = md5Sum(out.toByteArray(), SCOUR_MD5_BYTE_LIMIT);
        }
        
        in.close();
        out.close();
      }catch( Exception e ){
        e.printStackTrace();
      }
      
      return sum;
    }
}
