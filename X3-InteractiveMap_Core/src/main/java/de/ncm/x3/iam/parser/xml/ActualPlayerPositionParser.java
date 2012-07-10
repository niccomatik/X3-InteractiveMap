
package de.ncm.x3.iam.parser.xml;


import java.io.File;

import org.apache.log4j.Logger;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import de.ncm.x3.iam.data.ActualPlayerInfo;
import de.ncm.x3.iam.data.universe.GridPos;

public class ActualPlayerPositionParser extends XMLParser<ActualPlayerInfo> {
	
	private static final Logger logger = Logger.getLogger(ActualPlayerPositionParser.class);
	
	public ActualPlayerPositionParser(File logFile) {
		super(logFile);
		logger.info("LogFile: " + logFile);
	}
	
	@Override
	protected ActualPlayerInfo parseXML(Element rootElement) {
		ActualPlayerInfo ret = new ActualPlayerInfo();
		
		NodeList children = rootElement.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			if (children.item(i).getNodeName().equalsIgnoreCase("Sector")) {
				ret.setSectorPosition(parseSectorPosition(children.item(i)));
			} else if (children.item(i).getNodeName().equalsIgnoreCase("Ship")) {
				parseShip(ret, children.item(i));
			}
		}
		return ret;
	}
	
	private void parseShip(ActualPlayerInfo ret, Node item) {
		
		NodeList children = item.getChildNodes();
		for (int i = 0; i < children.getLength(); i++) {
			if (children.item(i).getNodeName().equalsIgnoreCase("Name")) {
				ret.setShipName(getStringValueOf(children.item(i)));
			} else if (children.item(i).getNodeName().equalsIgnoreCase("X")) {
				ret.setX(getIntValueOf(children.item(i)));
			} else if (children.item(i).getNodeName().equalsIgnoreCase("Y")) {
				ret.setY(getIntValueOf(children.item(i)));
			} else if (children.item(i).getNodeName().equalsIgnoreCase("Z")) {
				ret.setZ(getIntValueOf(children.item(i)));
			}
		}
		
	}
	
	private GridPos parseSectorPosition(Node item) {
		
		NodeList children = item.getChildNodes();
		GridPos gc = new GridPos();
		
		for (int i = 0; i < children.getLength(); i++) {
			if (children.item(i).getNodeName().equalsIgnoreCase("X")) {
				gc.setGridX(getIntValueOf(children.item(i)));
			} else if (children.item(i).getNodeName().equalsIgnoreCase("Y")) {
				gc.setGridY(getIntValueOf(children.item(i)));
			}
		}
		
		return gc;
	}
	
}
