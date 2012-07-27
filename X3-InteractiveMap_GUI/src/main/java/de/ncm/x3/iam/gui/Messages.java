
package de.ncm.x3.iam.gui;


import java.beans.Beans;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	
	// //////////////////////////////////////////////////////////////////////////
	//
	// Constructor
	//
	// //////////////////////////////////////////////////////////////////////////
	private Messages() {
		// do not instantiate
	}
	
	// //////////////////////////////////////////////////////////////////////////
	//
	// Bundle access
	//
	// //////////////////////////////////////////////////////////////////////////
	private static final String BUNDLE_NAME = "language.messages"; //$NON-NLS-1$
	private static ResourceBundle RESOURCE_BUNDLE = loadBundle(Locale.getDefault());
	
	private static ResourceBundle loadBundle(Locale l) {
		return ResourceBundle.getBundle(BUNDLE_NAME, l);
	}
	
	// //////////////////////////////////////////////////////////////////////////
	//
	// Strings access
	//
	// //////////////////////////////////////////////////////////////////////////
	public static String getString(String key) {
		try {
			ResourceBundle bundle = Beans.isDesignTime() ? loadBundle(Locale.ENGLISH) : RESOURCE_BUNDLE;
			return bundle.getString(key);
		} catch (MissingResourceException e) {
			return "!" + key + "!";
		}
	}
	
	public static ResourceBundle setLocale(Locale newLocale) {
		RESOURCE_BUNDLE = loadBundle(newLocale);
		return RESOURCE_BUNDLE;
	}
	
	public static Locale getActualLocale() {
		return RESOURCE_BUNDLE.getLocale();
	}
}
