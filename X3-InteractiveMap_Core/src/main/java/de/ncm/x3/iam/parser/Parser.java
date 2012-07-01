
package de.ncm.x3.iam.parser;


import java.util.ArrayList;

public abstract class Parser {
	
	protected ArrayList<ParseListener>	listener	= new ArrayList<ParseListener>();
	
	public abstract void parse();
	
	public abstract boolean needUpdate(long timeGone);
	
	public boolean addParseListener(ParseListener listener) {
		return this.listener.add(listener);
	}
	
	public boolean removeParseListener(ParseListener listener) {
		return this.listener.remove(listener);
	}
	
	public void fireParseEndEvent(ParseEvent e) {
		for (ParseListener l : listener) {
			l.onParseEnd(e);
		}
	}
	
	public void fireParseStartEvent(ParseEvent e) {
		for (ParseListener l : listener) {
			l.onParseStart(e);
		}
	}
	
}
