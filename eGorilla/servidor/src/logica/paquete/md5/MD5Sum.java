package logica.paquete.md5;

import java.io.*;
import java.security.*;

//*****************************************************************************//
/**
 * Clase que aplica el algoritmo md5.
 * 
 * @author S@L-c
 */
public class MD5Sum {

	// CONSTANTES
	public static int SCOUR_MD5_BYTE_LIMIT = (300 * 1024);

	// ATRIBUTOS
	private static MessageDigest _md = null;

//	 *****************************************************************************//
	/**
	 * Method: md5Sum Purpose: calculate the MD5 in a way compatible with how
	 * the scour.net protocol encodes its passwords (incidentally, it also
	 * outputs a string identical to the md5sum unix command).
	 * 
	 * @param str
	 *            the String from which to calculate the sum
	 * @return the MD5 checksum
	 */
	public static String md5Sum(String str) {
		
		try {
		
			return md5Sum(str.getBytes("UTF-8"));
		} 
		catch (UnsupportedEncodingException e) {
			
			throw new IllegalStateException(e.getMessage());
		}
	}

//	 *****************************************************************************//
	/**
	 * Method: md5Sum Purpose: calculate the MD5 in a way compatible with how
	 * the scour.net protocol encodes its passwords (incidentally, it also
	 * outputs a string identical to the md5sum unix command).
	 * 
	 * @param str
	 *            the String from which to calculate the sum
	 * @return the MD5 checksum
	 */	
	public static String md5Sum(byte[] input) {

		return md5Sum(input, -1);
	}

// *****************************************************************************//
	/**
	 * Method: md5Sum Purpose: calculate the MD5 in a way compatible with how
	 * the scour.net protocol encodes its passwords (incidentally, it also
	 * outputs a string identical to the md5sum unix command).
	 * 
	 * @param str
	 *            the String from which to calculate the sum
	 * @return the MD5 checksum
	 */
	public static String md5Sum(byte[] input, int limit) {
		
		try {
			
			if (_md == null)
				_md = MessageDigest.getInstance("MD5");

			_md.reset();
			byte[] digest;

			if (limit == -1) {
				digest = _md.digest(input);
			} 
			else {
			
				_md.update(input, 0, limit > input.length ? input.length: limit);
				digest = _md.digest();
			}

			StringBuffer hexString = new StringBuffer();

			for (int i = 0; i < digest.length; i++) {
				hexString.append(hexDigit(digest[i]));
			}

			return hexString.toString();
		} 
		catch (NoSuchAlgorithmException e) {
			
			throw new IllegalStateException(e.getMessage());
		}
	}

//	 *****************************************************************************//
	/**
	 * Method: hexDigit Purpose: convert a hex digit to a String, used by
	 * md5Sum.
	 * 
	 * @param x
	 *            the digit to translate
	 * @return the hex code for the digit
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

//	 *****************************************************************************//
	/**
	 * Method: getFileMD5Sum Purpose: get the MD5 sum of a file. Scour exchange
	 * only counts the first SCOUR_MD5_BYTE_LIMIT bytes of a file for
	 * caclulating checksums (probably for efficiency or better comaprison
	 * counts against unfinished downloads).
	 * 
	 * @param f
	 *            the file to read
	 * @return the MD5 sum string
	 * @throws IOException
	 *             on IO error
	 */
	public static String getFileMD5Sum(File f) throws IOException {

		String sum = null;
		FileInputStream in = new FileInputStream(f.getAbsolutePath());

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

		if (sum == null)
			sum = md5Sum(out.toByteArray(), SCOUR_MD5_BYTE_LIMIT);

		in.close();
		out.close();

		return sum;
	}

//	 *****************************************************************************//
	/**
	 * 
	 * @param md5
	 * @param directory
	 * @return
	 * @throws IOException
	 */
	public static File scanForMD5(String md5, File directory) throws IOException {
		
		return FileHelper.scanForMD5(md5, directory);
	}

//	 *****************************************************************************//
	/**
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		System.out.println(getFileMD5Sum(new File(args[0])) + "  " + args[0]);
	}
}
