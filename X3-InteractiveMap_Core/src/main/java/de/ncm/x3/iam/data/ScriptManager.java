
package de.ncm.x3.iam.data;


import java.io.File;
import java.io.IOException;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;

import de.ncm.x3.iam.util.ZipUtil;

public class ScriptManager {
	
	private static final Logger logger = Logger.getLogger(ScriptManager.class);
	
	private static ScriptManager instance;
	
	private ScriptManager() {}
	
	public void installScriptsTo(File gameFolder) {
		if (!gameFolder.isDirectory()) {
			throw new IllegalArgumentException("The argument has to be a Folder");
		}
		try {
			JarFile jar = new JarFile(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
			ZipUtil.coppyRecursiveFromJarFile(jar, "/xscripts", gameFolder);
			
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
