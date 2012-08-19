
package de.ncm.x3.iam.data.universe;


import org.apache.log4j.Logger;

import com.jgoodies.binding.beans.Model;

import de.ncm.jgoodies.model.HashMapModel;

public class UniverseMap extends Model {
	
	private static Logger logger = Logger.getLogger(UniverseMap.class);
	public static final String UNIVERSEMAP_SECTOR_CHANGE = "UniverseMap_Sector_Change";
	public static final String UNIVERSEMAP_ALL_SECTORS_CHANGE = "UniverseMap_All_Sectors_Change";
	private HashMapModel<GridPos, Sector> sectors = new HashMapModel<GridPos, Sector>();
	private HashMapModel<Integer, Race> races;
	
	public UniverseMap() {}
	
	public void putSector(GridPos gridPos, Sector sec) {
		Sector ret = sectors.put(gridPos, sec);
		if (ret != null) {
			logger.warn("Map value at " + gridPos + " has been replaced");
		}
		firePropertyChange(UNIVERSEMAP_SECTOR_CHANGE, ret, sec);
	}
	
	public HashMapModel<GridPos, Sector> getSectors() {
		return sectors;
	}
	
	public void setRaces(HashMapModel<Integer, Race> raceMap) {
		this.races = raceMap;
		
	}
	
	public HashMapModel<Integer, Race> getRaces() {
		return races;
	}
	
	public String getSectorName(GridPos gp) {
		Sector sector = sectors.get(gp);
		if (sector == null) {
			return "";
		} else {
			return sector.getName();
		}
		
	}
	
}
