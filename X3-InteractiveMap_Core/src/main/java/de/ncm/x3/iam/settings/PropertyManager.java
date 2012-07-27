
package de.ncm.x3.iam.settings;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertyManager {
	
	private static Logger logger = Logger.getLogger(PropertyManager.class);
	private Properties userProperties = null;
	private static PropertyManager instance = null;
	private static final String defaultProbFile = "default.properties"; // In jar file
	private static final String userProbFile = "user.properties"; // At program directory
	
	public static final String KEY_GAMEFOLDER = "game.folder";
	public static final String KEY_UNIVERSEMAP_LOGFILENAME = "log.parser.xml.universemap.file";
	public static final String KEY_UNIVERSEMAP_PLAYERPOSITION_LOGFILENAME = "log.parser.xml.actualplayerposition.file";
	public static final String KEY_LOGPATH = "log.parser.xml.path";
	public static final String KEY_PARSER_CONTINOUSPARSING_ENABLED = "parser.continuousparsing.enabled";
	public static final String KEY_ACTUAL_COLORPACK = "colorpackage.actual";
	public static final String KEY_UNIVERSEMAP_AUTOMATICCENTER = "universemap.automaticcenter";
	
	private PropertyManager() {
		logger.info("Loading Properties");
		
		userProperties = getUserProperties(getDefaultProperties());
		
		logger.info("Properties loaded");
		
	}
	
	private Properties getUserProperties(Properties defaultProperties) {
		
		Properties userProperties = new Properties(defaultProperties);
		File userProp = new File(userProbFile);
		if (!userProp.exists()) {
			String message = "File '" + userProbFile + "' does not exist - using Default";
			logger.error(message);
		} else {
			
			try {
				userProperties.load(new FileInputStream(userProp));
			} catch (IOException e) {
				logger.error("", e);
			}
		}
		return userProperties;
	}
	
	private Properties getDefaultProperties() {
		Properties defaultProperties = new Properties();
		
		InputStream stream = getClass().getClassLoader().getResourceAsStream(defaultProbFile);
		if (stream == null) {
			String message = "File '" + defaultProbFile + "' does not exist!";
			logger.fatal(message);
			throw new IllegalStateException(message);
		}
		
		try {
			defaultProperties.load(stream);
		} catch (IOException e) {
			logger.error("", e);
		}
		return defaultProperties;
	}
	
	public String getProperty(String key) {
		return userProperties.getProperty(key);
	}
	
	public void setProperty(String key, String value) {
		userProperties.setProperty(key, value);
	}
	
	public static PropertyManager get() {
		if (instance == null) {
			instance = new PropertyManager();
		}
		return instance;
	}
	
	public void setProperty(String key, boolean value) {
		setProperty(key, "" + value);
		
	}
	
	public void save() {
		logger.info("Saving properties");
		try {
			userProperties.store(new FileOutputStream(userProbFile), "User properties");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public String getGameFolder() {
		return getProperty(KEY_GAMEFOLDER);
	}
	
	public String getLogpath() {
		return getProperty(KEY_LOGPATH);
	}
	
	public String getUniverseMapLogFile() {
		return getProperty(KEY_UNIVERSEMAP_LOGFILENAME);
	}
	
	public String getUniverseMapPlayerPositionFile() {
		return getProperty(KEY_UNIVERSEMAP_PLAYERPOSITION_LOGFILENAME);
	}
	
	public boolean getAutomaticCenterEnabled() {
		return new Boolean(getProperty(KEY_UNIVERSEMAP_AUTOMATICCENTER));
	}
	
	public boolean getContinousParsingEnabled() {
		return new Boolean(getProperty(KEY_PARSER_CONTINOUSPARSING_ENABLED));
	}
	
	public String getActualColorPackage() {
		return getProperty(KEY_ACTUAL_COLORPACK);
	}
	
	public void setUniverseMapLogFile(String value) {
		setProperty(KEY_UNIVERSEMAP_LOGFILENAME, value);
	}
	
	public void setUniverseMapPlayerPositionFile(String value) {
		setProperty(KEY_UNIVERSEMAP_PLAYERPOSITION_LOGFILENAME, value);
	}
	
	public void setAutomaticCenterEnabled(boolean value) {
		setProperty(KEY_UNIVERSEMAP_AUTOMATICCENTER, value);
	}
	
	public void setContinousParsingEnabled(boolean value) {
		setProperty(KEY_PARSER_CONTINOUSPARSING_ENABLED, value);
	}
	
	public void setActualColorPackage(String value) {
		setProperty(KEY_ACTUAL_COLORPACK, value);
	}
	
	public void setGameFolder(String value) {
		setProperty(KEY_GAMEFOLDER, value);
		
	}
	
	public void setLogPath(String value) {
		setProperty(KEY_LOGPATH, value);
		
	}
	
}
