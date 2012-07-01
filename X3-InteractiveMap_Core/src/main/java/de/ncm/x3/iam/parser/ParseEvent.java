
package de.ncm.x3.iam.parser;


import java.util.EventObject;

public class ParseEvent extends EventObject {
	
	private Object	parsedValue;
	private long	when;
	
	public ParseEvent(Object source, Object parsedValue) {
		super(source);
		this.parsedValue = parsedValue;
		this.when = System.currentTimeMillis();
	}
	
	public Object getParsedValue() {
		return parsedValue;
	}
	
	public long getWhen() {
		return when;
	}
}
