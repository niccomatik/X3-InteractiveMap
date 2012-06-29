
package de.ncm.x3.iam.bundle;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.log4j.Logger;

public class GuiBundleManager {
	
	private static Logger	        logger	   = Logger.getLogger(GuiBundleManager.class);
	private String	                filePrefix	= "language.lang";
	private ResourceBundle	        rb	       = null;
	private LocaleChangedListener	listener	= null;
	
	private static GuiBundleManager	instance	= null;
	
	private GuiBundleManager() { // no one except own class is allowed to
		                         // instantiate
		setLocale(Locale.getDefault());
	}
	
	public String getString(String key) {
		return rb.getString(key);
	}
	
	public String[] getStringArray(String key) {
		return rb.getStringArray(key);
	}
	
	public Locale getLocale() {
		return rb.getLocale();
	}
	
	public void setLocale(Locale l) {
		rb = ResourceBundle.getBundle(filePrefix, l);
		if (listener != null) {
			listener.localeChanged(rb);
		}
		
	}
	
	public LocaleChangedListener getLocaleChangedListener() {
		return listener;
	}
	
	public void setLocaleChangedListener(LocaleChangedListener listener) {
		this.listener = listener;
		if (listener != null) {
			listener.localeChanged(rb);
		}
	}
	
	public static GuiBundleManager get() {
		if (instance == null) {
			instance = new GuiBundleManager();
		}
		return instance;
	}
	
	public Locale[] getAvailableLocales() {
		
		ArrayList<Locale> out = new ArrayList<Locale>();
		
		String[] split = filePrefix.split("\\.");
		
		String folderPath = getClass().getResource("/" + filePrefix.replace("." + split[split.length - 1], "")).getFile();
		File folder = new File(folderPath);
		
		if (folder.canRead()) { // True: Normal FileSystem (= eclipse run mode)
			logger.info("Using Dev-Mode Locale listing");
			for (File f : folder.listFiles()) {
				logger.debug("Locale found: " + f);
				if (f.isFile()) {
					out.add(parseLocaleString(f.getName()));
				}
			}
		} else { // False: Packed in jar file
		
			try {
				JarFile jar = new JarFile(getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
				Enumeration<JarEntry> jarRootContent = jar.entries();
				while (jarRootContent.hasMoreElements()) {
					JarEntry entry = jarRootContent.nextElement();
					String[] fileEntry = entry.getName().split("/");
					if (!entry.isDirectory() && fileEntry.length == 2 && fileEntry[0].equals(split[0])) {
						logger.debug("Locale found: " + fileEntry[1]);
						out.add(parseLocaleString(fileEntry[1]));
					}
				}
			} catch (IOException e) {
				logger.error("Opening JarFile '" + folderPath.split("!")[0] + "':", e);
			}
			
		}
		return out.toArray(new Locale[out.size()]);
	}
	
	public static Locale parseLocaleString(String fileName) {
		
		String localeFileName = fileName.split("\\.")[0]; // If no '.' inside fileName will be returned
		
		String[] parts = localeFileName.split("_");
		String language = parts.length > 1 ? parts[1] : "";
		String country = parts.length > 2 ? parts[2] : "";
		String variant = "";
		if (parts.length >= 3) {
			// There is definitely a variant, and it is everything after the country code sans the separator between the country code and the variant.
			int endIndexOfCountryCode = localeFileName.indexOf(country) + country.length();
			// Strip off any leading '_' and whitespace, what's left is the variant.
			variant = localeFileName.substring(endIndexOfCountryCode).trim();
			if (variant.startsWith("_")) {
				variant = trimLeadingCharacter(variant, '_');
			}
		}
		return language.length() > 0 ? new Locale(language, country, variant) : null;
	}
	
	private static String trimLeadingCharacter(String s, char c) {
		StringBuffer buff = new StringBuffer(s);
		if (buff.length() > 0 && buff.charAt(0) == c) {
			buff.deleteCharAt(0);
		}
		return buff.toString();
	}
}
