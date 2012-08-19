
package de.ncm.x3.iam.data.universe;


import java.util.Arrays;

import org.apache.log4j.Logger;

import com.jgoodies.binding.beans.Model;
import com.jgoodies.common.collect.ArrayListModel;

public class Sector extends Model implements WarpGateConstants, Comparable<Sector> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6684121054569984002L;
	private static final Logger logger = Logger.getLogger(Sector.class);
	private static final String SECTOR_NAME = "Sector_Name";
	private static final String SECTOR_RACE = "Sector_Race";
	private static final String SECTOR_WARPGATES = "Sector_Warpgates";
	private static final String SECTOR_WARPGATE = "Sector_Warpgate";
	
	private String name = "";
	private Race race = null;
	private WarpGate[] warpGates = new WarpGate[4];
	private ArrayListModel<SpaceStation> stations = new ArrayListModel<SpaceStation>();
	
	public Sector(String name, Race race, WarpGate[] warpGates) {
		super();
		setName(name);
		setRace(race);
		setWarpGates(warpGates);
	}
	
	public Sector() {}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		Object oldValue = this.name;
		this.name = name;
		firePropertyChange(SECTOR_NAME, oldValue, name);
	}
	
	public Race getRace() {
		return race;
	}
	
	public void setRace(Race race) {
		Object oldValue = this.race;
		this.race = race;
		firePropertyChange(SECTOR_RACE, oldValue, race);
	}
	
	public WarpGate[] getWarpGates() {
		return warpGates;
	}
	
	public void setWarpGates(WarpGate[] warpGates) {
		Object oldValue = this.warpGates;
		this.warpGates = warpGates;
		firePropertyChange(SECTOR_WARPGATES, oldValue, warpGates);
	}
	
	public WarpGate getWarpGate(byte id) {
		return getWarpGates()[id];
	}
	
	public void setWarpGate(byte id, WarpGate warpGate) {
		Object oldValue = getWarpGates()[id];
		getWarpGates()[id] = warpGate;
		firePropertyChange(SECTOR_WARPGATE, oldValue, warpGate);
	}
	
	public boolean isWarpgate(byte id, WarpGate warpGate) {
		if (warpGate == null) {
			return false;
		}
		return warpGate.equals(getWarpGate(id));
	}
	
	public void addSpaceStation(SpaceStation station) {
		this.stations.add(station);
		
	}
	
	public ArrayListModel<SpaceStation> getSpaceStations() {
		return stations;
	}
	
	@Override
	public String toString() {
		return "Sector [name=" + name + ", race=" + race + ", warpGates=" + Arrays.toString(warpGates) + ", stations=" + stations + "]";
	}
	
	@Override
	public int compareTo(Sector sector) {
		return getName().compareTo(sector.getName());
	}
	
}
