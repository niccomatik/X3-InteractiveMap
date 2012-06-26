
package de.ncm.x3.iam.data.universe;


public class Sector {
	
	public static final byte	WARPGATE_NORTH	= 0;
	public static final byte	WARPGATE_SOUTH	= 1;
	public static final byte	WARPGATE_EAST	= 2;
	public static final byte	WARPGATE_WEST	= 3;
	
	private String	         name	           = "";
	private Integer	         raceID	           = -1;
	private WarpGate[]	     warpGates	       = new WarpGate[4];
	
	public Sector(String name, Integer raceID, WarpGate[] warpGates) {
		super();
		this.name = name;
		this.raceID = raceID;
		this.warpGates = warpGates;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getRaceID() {
		return raceID;
	}
	
	public void setRaceID(Integer raceID) {
		this.raceID = raceID;
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
