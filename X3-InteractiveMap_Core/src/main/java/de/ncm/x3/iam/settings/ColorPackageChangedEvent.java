
package de.ncm.x3.iam.settings;


import java.util.EventObject;
import java.util.Properties;

public class ColorPackageChangedEvent extends EventObject {
	
	private final long when;
	private final String oldColorPackage;
	private final String newColorPackage;
	private final Properties newProperties;
	
	public ColorPackageChangedEvent(String oldColorPackage, String newColorPackage, Properties newProperties) {
		super("No source");
		this.oldColorPackage = oldColorPackage;
		this.newColorPackage = newColorPackage;
		this.newProperties = newProperties;
		
		this.when = System.currentTimeMillis();
	}
	
	public long getWhen() {
		return when;
	}
	
	public String getOldColorPackage() {
		return oldColorPackage;
	}
	
	public String getNewColorPackage() {
		return newColorPackage;
	}
	
	public Properties getNewProperties() {
		return newProperties;
	}
	
}
