
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

public abstract class XMLParser<E> {
	
	private DocumentBuilder	       dBuilder;
	private DocumentBuilderFactory	dbFactory;
	private long	               lastModified	= -1;
	private File	               f;
	
	public XMLParser(String file) {
		this.f = new File(file);
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
	
	public final E parse() {
		long lastModified = f.lastModified();
		E ret = null;
		try {
			Document doc = dBuilder.parse(f);
			doc.getDocumentElement().normalize();
			Element rootNode = doc.getDocumentElement();
			ret = parseXML(rootNode);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		this.lastModified = lastModified;
		return ret;
	}
	
	public boolean isModified() {
		long lastModified = f.lastModified();
		if (lastModified != this.lastModified) {
			return true;
		}
		return false;
	}
	
	protected abstract E parseXML(Element rootElement);
	
	/**
	 * @return the f
	 */
	public File getFile() {
		return f;
	}
	
	/**
	 * @param f
	 *            the f to set
	 */
	protected void setFile(File f) {
		this.f = f;
	}
	
}
