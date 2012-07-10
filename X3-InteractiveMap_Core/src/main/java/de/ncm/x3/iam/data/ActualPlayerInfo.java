
package de.ncm.x3.iam.data;


import de.ncm.x3.iam.data.universe.GridPos;

public class ActualPlayerInfo {
	
	private GridPos sectorPosition = new GridPos();
	private String shipName = "";
	private int x = 0;
	private int y = 0;
	private int z = 0;
	
	public ActualPlayerInfo(GridPos sectorPosition, String shipName, int x, int y, int z) {
		super();
		this.sectorPosition = sectorPosition;
		this.shipName = shipName;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public ActualPlayerInfo() {
		super();
	}
	
	public GridPos getSectorPosition() {
		return sectorPosition;
	}
	
	public void setSectorPosition(GridPos sectorPosition) {
		this.sectorPosition = sectorPosition;
	}
	
	public String getShipName() {
		return shipName;
	}
	
	public void setShipName(String shipName) {
		this.shipName = shipName;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getZ() {
		return z;
	}
	
	public void setZ(int z) {
		this.z = z;
	}
	
}
