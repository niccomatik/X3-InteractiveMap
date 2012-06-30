
package de.ncm.x3.iam.parser;


public interface Parser {
	
	public void parse();
	
	public boolean needUpdate(long timeGone);
	
}
