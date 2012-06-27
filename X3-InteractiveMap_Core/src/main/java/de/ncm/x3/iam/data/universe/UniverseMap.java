
package de.ncm.x3.iam.data.universe;


import java.util.HashMap;

import org.apache.log4j.Logger;

public class UniverseMap {
	
	private static Logger	         LOGGER	    = Logger.getLogger(UniverseMap.class);
	private HashMap<GridPos, Sector>	sectors	= new HashMap<GridPos, Sector>();
	
	public UniverseMap() {}
	
	public void putSector(GridPos gridPos, Sector sec) {
		Sector ret = sectors.put(gridPos, sec);
		if (ret != null) {
			LOGGER.warn("Map value at " + gridPos + " has been replaced");
		}
	}
	
	public HashMap<GridPos, Sector> getSectors() {
		return sectors;
	}
	
}
