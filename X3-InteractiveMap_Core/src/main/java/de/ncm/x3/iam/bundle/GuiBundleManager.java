package de.ncm.x3.iam.bundle;

import java.util.Locale;
import java.util.ResourceBundle;

public class GuiBundleManager {
	
	private String filePrefix = "language.lang";
	private ResourceBundle rb = null;
	private LocaleChangedListener listener = null;
	private static GuiBundleManager instance = null;
	
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
	
	public static GuiBundleManager get() {
		if (instance == null) {
			instance = new GuiBundleManager();
		}
		return instance;
	}
	
}
