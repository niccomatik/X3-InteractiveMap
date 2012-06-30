
package de.ncm.x3.iam.parser;


import java.util.HashMap;

import org.apache.log4j.Logger;

public class ParserManager {
	
	private static Logger	        logger	    = Logger.getLogger(ParserManager.class);
	protected HashMap<Parser, Long>	parserMap	= new HashMap<Parser, Long>();	         // Parser, LastTimeInvoked
	protected Thread	            parseThread	= null;
	protected long	                waitTime	= 100;
	protected boolean	            active	    = false;
	
	public ParserManager() {
		addParser(ParserFactory.getUniverseMapParser());
	}
	
	private void addParser(Parser parser) {
		parserMap.put(parser, System.currentTimeMillis());
		
	}
	
	public void reparse() {
		long actualTime = System.currentTimeMillis();
		for (Parser p : parserMap.keySet()) {
			if (p.needUpdate(actualTime - parserMap.get(p))) {
				p.parse();
				parserMap.put(p, actualTime);
			}
		}
	}
	
	public void startParsing() {
		logger.info("Staring parse Thread");
		if (!active) {
			createThread();
			parseThread.start();
			logger.info("Parse Thread started");
		}
	}
	
	public void stopParsing() {
		logger.info("Stopping parse Thread");
		active = false;
		logger.debug("Waiting for thread to stop");
		if (parseThread != null) {
			while (parseThread.isAlive()) {
				
			}
		}
		logger.debug("Thread stoped");
	}
	
	private void createThread() {
		active = true;
		parseThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while (active) {
					reparse();
					try {
						Thread.sleep(waitTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
			}
		});
		
	}
	
}
