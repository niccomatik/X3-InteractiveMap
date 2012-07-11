
package de.ncm.x3.iam.parser;


import java.io.File;

import de.ncm.x3.iam.parser.xml.ActualPlayerPositionParser;
import de.ncm.x3.iam.parser.xml.UniverseMapParser;
import de.ncm.x3.iam.settings.PropertyManager;

public abstract class ParserFactory {
	
	private static UniverseMapParser universeMapParser = null;
	private static ActualPlayerPositionParser actualPlayerPositionParser;
	
	public static Parser getUniverseMapParser() {
		if (universeMapParser == null) {
			File file = new File(PropertyManager.get().getProperty("log.parser.xml.path"), PropertyManager.get().getProperty("log.parser.xml.universemap.file"));
			universeMapParser = new UniverseMapParser(file);
		}
		return universeMapParser;
		
	}
	
	public static Parser getActualPlayerPositionParser() {
		if (actualPlayerPositionParser == null) {
			File file = new File(PropertyManager.get().getProperty("log.parser.xml.path"), PropertyManager.get().getProperty(
					"log.parser.xml.actualplayerposition.file"));
			actualPlayerPositionParser = new ActualPlayerPositionParser(file);
		}
		return actualPlayerPositionParser;
	}
	
	public static void updateXMLParserPath() {
		File file = new File(PropertyManager.get().getProperty("log.parser.xml.path"), PropertyManager.get().getProperty("log.parser.xml.universemap.file"));
		universeMapParser.setFile(file);
		
		file = new File(PropertyManager.get().getProperty("log.parser.xml.path"), PropertyManager.get().getProperty("log.parser.xml.actualplayerposition.file"));
		actualPlayerPositionParser.setFile(file);
	}
}
