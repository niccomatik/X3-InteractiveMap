
package de.ncm.x3.iam.util;


public final class StringUtil {
	
	public static String removeFirstCharacter(String s, char c) {
		StringBuffer buff = new StringBuffer(s);
		if (buff.length() > 0 && buff.charAt(0) == c) {
			buff.deleteCharAt(0);
		}
		return buff.toString();
	}
	
	public static String removeLastCharacter(String s, char c) {
		StringBuffer buff = new StringBuffer(s);
		if (buff.length() > 0 && buff.charAt(s.length() - 1) == c) {
			buff.deleteCharAt(s.length() - 1);
		}
		return buff.toString();
	}
	
}
