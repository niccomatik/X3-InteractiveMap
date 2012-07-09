
package de.ncm.x3.iam.data.universe;


import org.apache.log4j.Logger;

public class Sector implements WarpGateConstants {
	
	private static final Logger logger = Logger.getLogger(Sector.class);
	private String name = "";
	private Race race = null;
	private WarpGate[] warpGates = new WarpGate[4];
	
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
	
}
