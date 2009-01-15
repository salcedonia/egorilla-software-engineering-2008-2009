package logica.paquete.md5;

import java.io.*;
import java.util.*;

//*****************************************************************************//
/**
 * Clase de apoyo para el cálculo del md5 de una archivo.
 * 
 * @author S@L-c
 */
public class FileHelper {
	
	// CONSTANTES
	private static long CACHE_INTERVAL = (1000 * 30);
	private static HashMap sumCache = new HashMap();
	private static long lastUpdate = 0;

//	 *****************************************************************************//
	/**
	 * 
	 * @param md5
	 * @return
	 */
	public static boolean md5Exists(String md5) {
		
		Object ob = getSumCache().get(md5);
		return (ob != null);
	}

//	 *****************************************************************************//
	/**
	 * 
	 * @return
	 */
	private static HashMap getSumCache() {
		if (System.currentTimeMillis() > (lastUpdate + CACHE_INTERVAL)) {
			rebuildCache();
			lastUpdate = System.currentTimeMillis();
		}

		return sumCache;
	}

//	 *****************************************************************************//
	/**
	 * 
	 *
	 */
	private static synchronized void rebuildCache() {

		File dir = (new File(".")).getAbsoluteFile();
		sumCache = getMD5Sums(dir);
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
		
		System.out.println("Scanning for: " + md5);
		File f = (File) getMD5Sums(directory).get(md5);
		System.out.println("Got: " + f);
		
		return f;
	}

//	 *****************************************************************************//
	/**
	 * 
	 * @param directory
	 * @return
	 */
	private static HashMap getMD5Sums(File directory) {
		
		HashMap<String, File> nm = new HashMap<String, File>();

		System.out.println("Scanning " + directory);

		if (!directory.isDirectory())
			return null;

		File[] sub = directory.listFiles();
		for (int i = 0; i < sub.length; i++) {

			File cur = sub[i];

			if (cur == null)
				continue;

			if (!cur.isFile())
				continue;

			try {
				String sm = MD5Sum.getFileMD5Sum(cur);
				nm.put(sm, cur);
				System.out.println(" -- " + sub[i] + " (" + sm + ")");
			} catch (Exception e) {
			}
		}

		return nm;
	}
}
