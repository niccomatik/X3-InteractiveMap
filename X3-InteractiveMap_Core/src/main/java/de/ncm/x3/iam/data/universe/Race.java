
package de.ncm.x3.iam.data.universe;


public class Race {
	
	private Integer id;
	private String name;
	
	public Race(Integer id, String name) {
		// TODO: Implemet Specific behavior
		this.id = id;
		this.name = name;
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public static Integer getIDFromRawString(String rawID) {
		String id = rawID.replaceAll("A", "").replaceAll("-", "");
		return new Integer(id);
	}
	
}
