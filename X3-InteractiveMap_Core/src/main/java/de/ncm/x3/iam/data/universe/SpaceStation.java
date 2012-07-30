
package de.ncm.x3.iam.data.universe;


public class SpaceStation implements Comparable<SpaceStation> {
	
	public static final String TYPE_FACTORY = "Factory";
	public static final String TYPE_Dock = "Dock";
	private String type = "";
	private int posZ = 0;
	private int posY = 0;
	private int posX = 0;
	private String name = "";
	
	public SpaceStation() {}
	
	public SpaceStation(String type, int posZ, int posY, int posX, String name) {
		super();
		this.type = type;
		this.posZ = posZ;
		this.posY = posY;
		this.posX = posX;
		this.name = name;
	}
	
	public void setName(String name) {
		this.name = name;
		
	}
	
	public void setPosX(int posX) {
		this.posX = posX;
		
	}
	
	public void setPosY(int posY) {
		this.posY = posY;
		
	}
	
	public void setPosZ(int posZ) {
		this.posZ = posZ;
		
	}
	
	public void setType(String type) {
		this.type = type;
		
	}
	
	public String getType() {
		return type;
	}
	
	public int getPosZ() {
		return posZ;
	}
	
	public int getPosY() {
		return posY;
	}
	
	public int getPosX() {
		return posX;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public int compareTo(SpaceStation station) {
		return getName().compareTo(station.getName());
	}
	
	public boolean isFactory() {
		return getType().equalsIgnoreCase(TYPE_FACTORY);
	}
	
	public boolean isDock() {
		return getType().equalsIgnoreCase(TYPE_Dock);
	}
	
	@Override
	public String toString() {
		return "SpaceStation [type=" + type + ", posZ=" + posZ + ", posY=" + posY + ", posX=" + posX + ", name=" + name + "]";
	}
	
}
