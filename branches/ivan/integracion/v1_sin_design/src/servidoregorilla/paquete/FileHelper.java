/*  JSCOUR - Java Scour search and download interface
 *  Copyright (C) 2000  jscour@priest.com
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA.
 */
package servidoregorilla.paquete;

import java.io.*;
import java.net.*;
import java.util.*;
import java.text.*;
import java.security.*;

public class FileHelper
	{
	private static long CACHE_INTERVAL = (1000 * 30);

	private static HashMap sumCache = new HashMap ();
	private static long lastUpdate = 0;

	public static boolean md5Exists (String md5)
		{
		Object ob = getSumCache ().get (md5);
		return (ob != null); 
		}

	private static HashMap getSumCache ()
		{
		if (System.currentTimeMillis () > (lastUpdate + CACHE_INTERVAL))
			{
			rebuildCache ();
			lastUpdate = System.currentTimeMillis ();
			}

		return sumCache;
		}

	private static synchronized void rebuildCache ()
		{
		File dir = (new File(".") ).getAbsoluteFile() ;

		sumCache = getMD5Sums ( dir );
		}

	public static File scanForMD5 (String md5, File directory)
		throws IOException
		{
		System.out.println ("Scanning for: " + md5);
		File f = (File)getMD5Sums (directory).get (md5);
		System.out.println ("Got: " + f);
		return f;
		}

	private static HashMap getMD5Sums (File directory)
		{
		HashMap<String,File> nm = new HashMap<String,File> ();

		System.out.println ("Scanning " + directory);

		if (!directory.isDirectory ())
			return null;

		File [] sub = directory.listFiles ();
		for (int i = 0; i < sub.length; i++)
			{

			File cur = sub [i];

			if (cur == null)
				continue;

			if (!cur.isFile ())
				continue;

			try
				{
				String sm = MD5Sum.getFileMD5Sum (cur);
				nm.put (sm, cur);
				System.out.println (" -- " + sub [i] + " (" + sm + ")");
				}
			catch (Exception e)
				{
				}
			}

		return nm;
		}
	
	}
