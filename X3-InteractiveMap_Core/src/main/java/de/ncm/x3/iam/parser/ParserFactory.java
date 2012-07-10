
package de.ncm.x3.iam.parser;


import java.io.File;

import de.ncm.x3.iam.parser.xml.ActualPlayerPositionParser;
import de.ncm.x3.iam.parser.xml.UniverseMapParser;
import de.ncm.x3.iam.settings.PropertyManager;

public abstract class ParserFactory {
	
	private static Parser universeMapParser = null;
	private static ActualPlayerPositionParser actualPlayerPositionParser;
	
	public static Parser getUniverseMapParser() {
		if (universeMapParser == null) {
			String file = PropertyManager.get().getProperty("log.parser.xml.path") + PropertyManager.get().getProperty("log.parser.xml.universemap.file");
			universeMapParser = new UniverseMapParser(new File(file));
		}
		return universeMapParser;
		
	}
	
	public static Parser getActualPlayerPositionParser() {
		if (actualPlayerPositionParser == null) {
			String file = PropertyManager.get().getProperty("log.parser.xml.path")
					+ PropertyManager.get().getProperty("log.parser.xml.actualplayerposition.file");
			actualPlayerPositionParser = new ActualPlayerPositionParser(new File(file));
		}
		return actualPlayerPositionParser;
	}
	
}
