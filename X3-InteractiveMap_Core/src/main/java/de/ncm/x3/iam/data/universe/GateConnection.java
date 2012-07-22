
package de.ncm.x3.iam.data.universe;


public class GateConnection {
	
	private final WarpGate gate1;
	private final WarpGate gate2;
	private final boolean directConnection;
	
	public GateConnection(WarpGate gate1, WarpGate gate2, boolean directConnection) {
		super();
		if (gate1 == null || gate2 == null) {
			throw new IllegalArgumentException("WarpGates must not be null!");
		}
		this.gate1 = gate1;
		this.gate2 = gate2;
		this.directConnection = directConnection;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		
		int result = prime * ((gate1 == null) ? 0 : gate1.hashCode());
		result += prime * ((gate2 == null) ? 0 : gate2.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		GateConnection other = (GateConnection) obj;
		if ((gate1.equals(other.gate1) || gate1.equals(other.gate2)) && (gate2.equals(other.gate2) || gate2.equals(other.gate1))) {
			return true;
		}
		return false;
	}
	
	public WarpGate getGate1() {
		return gate1;
	}
	
	public WarpGate getGate2() {
		return gate2;
	}
	
	public boolean isDirectConnection() {
		return directConnection;
	}
	
}
