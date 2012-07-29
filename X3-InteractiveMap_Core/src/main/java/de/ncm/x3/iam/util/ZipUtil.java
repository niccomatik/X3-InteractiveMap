
package de.ncm.x3.iam.util;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.log4j.Logger;

public final class ZipUtil {
	
	private static final Logger logger = Logger.getLogger(ZipUtil.class);
	
	public static void coppyRecursiveFromJarFile(JarFile jar, String innerPath, File outputPath) {
		coppyRecursiveFromZipFile(jar, innerPath, outputPath);
	}
	
	public static void coppyRecursiveFromZipFile(ZipFile zip, String innerPath, File outputPath) {
		
		innerPath = StringUtil.removeFirstCharacter(innerPath, '/');
		if (!innerPath.endsWith("/")) {
			innerPath = innerPath + "/";
		}
		
		Enumeration<? extends ZipEntry> zipRootContent = zip.entries();
		while (zipRootContent.hasMoreElements()) {
			
			ZipEntry entry = zipRootContent.nextElement();
			
			if (entry.getName().startsWith(innerPath) && !entry.getName().endsWith("/")) {
				String outputInnerPathName = entry.getName().substring(innerPath.length()); // remove the prefix "innerPath"
				
				File outputFile = new File(outputPath, outputInnerPathName);
				File outputFileFolder = new File(outputPath, outputInnerPathName.replace(PathBuilder.getFileNameOfPath(outputInnerPathName), "")); // removing
																																					// FileName
																																					// from
																																					// folder
																																					// path
				outputFileFolder.mkdirs(); // Creating Folder
				InputStream is = null;
				FileOutputStream fos = null;
				try {
					
					is = zip.getInputStream(entry);
					fos = new FileOutputStream(outputFile);
					
					while (is.available() > 0) {
						fos.write(is.read());
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (fos != null) {
						try {
							fos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					if (is != null) {
						try {
							is.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				
			}
		}
	}
	
}
