package de.ncm.x3.iam.bundle;

import java.util.Locale;
import java.util.ResourceBundle;

public class BundleManager {
	
	private String filePrefix = "/ressources/language/lang";
	private ResourceBundle rb = null;
	private LocaleChangedListener listener = null;
	private static BundleManager instance = null;
	
	private BundleManager() { // no one except own class is allowed to
								// instantiate
		System.out
				.println(filePrefix
						+ "_de.properties : "
						+ getClass().getResourceAsStream(
								filePrefix + "_de.properties"));
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
	
	public static BundleManager get() {
		if (instance == null) {
			instance = new BundleManager();
		}
		return instance;
	}
	
}
