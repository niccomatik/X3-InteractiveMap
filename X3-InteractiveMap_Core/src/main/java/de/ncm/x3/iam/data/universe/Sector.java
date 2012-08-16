
package de.ncm.x3.iam.data.universe;


import java.util.ArrayList;
import java.util.Arrays;

import org.apache.log4j.Logger;

import com.jgoodies.binding.beans.Model;

public class Sector extends Model implements WarpGateConstants, Comparable<Sector> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6684121054569984002L;
	private static final Logger logger = Logger.getLogger(Sector.class);
	private String name = "";
	private Race race = null;
	private WarpGate[] warpGates = new WarpGate[4];
	private ArrayList<SpaceStation> stations = new ArrayList<SpaceStation>();
	
	public Sector(String name, Race race, WarpGate[] warpGates) {
		super();
		this.name = name;
		this.race = race;
		this.warpGates = warpGates;
	}
	
	public Sector() {}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Race getRace() {
		return race;
	}
	
	public void setRace(Race race) {
		this.race = race;
	}
	
	public WarpGate[] getWarpGates() {
		return warpGates;
	}
	
	public void setWarpGates(WarpGate[] warpGates) {
		this.warpGates = warpGates;
	}
	
	public WarpGate getWarpGate(byte id) {
		return getWarpGates()[id];
	}
	
	public void setWarpGate(byte id, WarpGate warpGate) {
		getWarpGates()[id] = warpGate;
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
	
	public ArrayList<SpaceStation> getSpaceStations() {
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
