
package de.ncm.x3.iam.data;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;

public class ScriptManager {
	
	private static final Logger logger = Logger.getLogger(ScriptManager.class);
	
	private static ScriptManager instance;
	
	private ScriptManager() {}
	
	public void installScriptsTo(File folder) {
		if (!folder.isDirectory()) {
			throw new IllegalArgumentException("The argument has to be a Folder");
		}
		try {
			System.out.println(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
			JarFile jar = new JarFile(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
			
			Enumeration<JarEntry> jarRootContent = jar.entries();
			while (jarRootContent.hasMoreElements()) {
				
				JarEntry entry = jarRootContent.nextElement();
				String[] fileEntry = entry.getName().split("/");
				
				if (!entry.isDirectory() && fileEntry.length == 2 && fileEntry[0].equals("xscripts")) {
					logger.debug("Scripts found: " + fileEntry[1]);
					File outputFile = new File(folder, fileEntry[1]);
					// outputFile.mkdirs();
					InputStream is = jar.getInputStream(entry);
					FileOutputStream fos = new FileOutputStream(outputFile);
					while (is.available() > 0) {
						fos.write(is.read());
					}
					fos.close();
					is.close();
				}
			}
			
		} catch (IOException e) {
			logger.error("Error while opening JarFile (maybe you run the Application in development mode): ", e);
		}
		
	}
	
	public static ScriptManager get() {
		if (instance == null) {
			instance = new ScriptManager();
		}
		return instance;
	}
	
}
