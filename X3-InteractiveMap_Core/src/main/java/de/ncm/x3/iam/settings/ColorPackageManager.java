
package de.ncm.x3.iam.settings;


import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;

public class ColorPackageManager {
	
	private static final Logger logger = Logger.getLogger(ColorPackageManager.class);
	private static final String folder = "color packs/";
	private static final String developmentPath = "src/main/externalRessources/";
	private static final String propertiesFileName = "pack.properties";
	private static ColorPackageManager instance;
	
	private String actualColorPackage = "";
	private ColorPackageChangedListener colorPackackageChangedListener = null;
	private Properties actualProperties = new Properties();
	
	private ColorPackageManager() {
		setActualColorPackage(PropertyManager.get().getProperty("colorpackage.actual"));
	}
	
	public String[] listColorPackages() {
		String path = folder;
		
		if (new Boolean(System.getProperty("isDevRunMode"))) {
			path = developmentPath + path;
		}
		ArrayList<String> packList = new ArrayList<String>();
		for (File f : new File(path).listFiles()) {
			if (f.isDirectory()) {
				packList.add(f.getName());
			}
		}
		
		return packList.toArray(new String[packList.size()]);
	}
	
	public void setActualColorPackage(String colorPackage) {
		actualProperties.clear();
		String propPath = folder;
		if (new Boolean(System.getProperty("isDevRunMode"))) {
			propPath = developmentPath + propPath;
		}
		propPath += colorPackage + "/";
		try {
			actualProperties.load(new FileInputStream(propPath + propertiesFileName));
			if (colorPackackageChangedListener != null) {
				colorPackackageChangedListener.colorPackageChanged(new ColorPackageChangedEvent(actualColorPackage, colorPackage, actualProperties));
			}
			actualColorPackage = colorPackage;
			PropertyManager.get().setProperty("colorpackage.actual", colorPackage);
		} catch (FileNotFoundException e) {
			logger.error("ColorPackage '" + colorPackage + "' does not exist", e);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public ColorPackageChangedListener getColorPackackageChangedListener() {
		return colorPackackageChangedListener;
	}
	
	public void setColorPackackageChangedListener(ColorPackageChangedListener colorPackackageChangedListener) {
		this.colorPackackageChangedListener = colorPackackageChangedListener;
	}
	
	public String getActualColorPackage() {
		return actualColorPackage;
	}
	
	public Properties getActualColorPackageProperties() {
		return actualProperties;
	}
	
	public Color getRaceColor(Integer id) {
		Color ret = new Color(240, 240, 240);
		String key = "race." + id.intValue() + ".color";
		Object value = getActualColorPackageProperties().get(key);
		if (value != null) {
			if (value instanceof String) {
				String[] elements = ((String) value).split(",");
				for (int i = 0; i < elements.length; i++) {
					elements[i] = elements[i].trim();
				}
				if (elements.length == 4) {
					ret = new Color(Integer.parseInt(elements[0]), Integer.parseInt(elements[1]), Integer.parseInt(elements[2]), Integer.parseInt(elements[3]));
				} else if (elements.length == 3) {
					ret = new Color(Integer.parseInt(elements[0]), Integer.parseInt(elements[1]), Integer.parseInt(elements[2]));
				}
			}
		}
		return ret;
	}
	
	public static ColorPackageManager get() {
		if (instance == null) {
			instance = new ColorPackageManager();
		}
		return instance;
		
	}
}
