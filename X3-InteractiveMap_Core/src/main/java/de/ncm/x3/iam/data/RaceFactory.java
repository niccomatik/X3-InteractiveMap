
package de.ncm.x3.iam.data;


import de.ncm.x3.iam.data.universe.Race;

public abstract class RaceFactory {
	
	public static Race get(String string) {
		// TODO Auto-generated method stub
		return new Race(string);
	}
	
}
