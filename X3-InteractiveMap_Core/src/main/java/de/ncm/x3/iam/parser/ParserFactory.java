
package de.ncm.x3.iam.parser;


import java.io.File;

import de.ncm.x3.iam.parser.xml.UniverseMapParser;
import de.ncm.x3.iam.settings.PropertyManager;

public abstract class ParserFactory {
	
	private static Parser	universeMapParser	= null;
	
	public static Parser getUniverseMapParser() {
		if (universeMapParser == null) {
			String file = PropertyManager.get().getProperty("log.parser.xml.path") + PropertyManager.get().getProperty("log.parser.xml.universemap.file");
			universeMapParser = new UniverseMapParser(new File(file));
		}
		return universeMapParser;
		
	}
	
}