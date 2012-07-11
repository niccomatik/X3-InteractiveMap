
package de.ncm.x3.iam.parser.xml;


import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import de.ncm.x3.iam.parser.ParseEvent;
import de.ncm.x3.iam.parser.Parser;

public abstract class XMLParser<E> extends Parser {
	
	private DocumentBuilder dBuilder;
	private DocumentBuilderFactory dbFactory;
	private long lastModified = -1;
	private File file;
	
	public XMLParser(File logFile) {
		this.file = logFile;
		dbFactory = DocumentBuilderFactory.newInstance();
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public static String getStringValueOf(Node n) {
		return n.getFirstChild().getNodeValue();
	}
	
	public static int getIntValueOf(Node n) {
		return Integer.parseInt(getStringValueOf(n));
	}
	
	public static double getDoubleValueOf(Node n) {
		return Double.parseDouble(getStringValueOf(n));
	}
	
	public static long getLongValueOf(Node n) {
		return Long.parseLong(getStringValueOf(n));
	}
	
	public static byte getByteValueOf(Node n) {
		return Byte.parseByte(getStringValueOf(n));
	}
	
	public static boolean getBooleanValueOf(Node n) {
		return Boolean.parseBoolean(getStringValueOf(n));
	}
	
	@Override
	public final void parse() {
		long lastModified = file.lastModified();
		fireParseStartEvent(new ParseEvent(this, null));
		E ret = null;
		try {
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			Element rootNode = doc.getDocumentElement();
			ret = parseXML(rootNode);
			fireParseEndEvent(new ParseEvent(this, ret));
			this.lastModified = lastModified;
		} catch (SAXException e) {
			if (Boolean.parseBoolean(System.getProperty("isDevOutputMode"))) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean isModified() {
		long lastModified = file.lastModified();
		if (lastModified != this.lastModified) {
			return true;
		}
		return false;
	}
	
	protected abstract E parseXML(Element rootElement);
	
	public File getFile() {
		return file;
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	
	@Override
	public boolean needUpdate(long timeGone) {
		if (!file.exists()) {
			return false;
		}
		return isModified();
	}
}
