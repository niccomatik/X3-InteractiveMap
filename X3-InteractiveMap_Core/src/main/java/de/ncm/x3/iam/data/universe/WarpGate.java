
package de.ncm.x3.iam.data.universe;


public class WarpGate {
	
	private final GridPos	targetGridPos;
	
	public WarpGate(GridPos targetGridPos) {
		super();
		this.targetGridPos = targetGridPos;
	}
	
	public GridPos getTargetGridPos() {
		return targetGridPos;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((targetGridPos == null) ? 0 : targetGridPos.hashCode());
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
		WarpGate other = (WarpGate) obj;
		if (targetGridPos == null) {
			if (other.targetGridPos != null) {
				return false;
			}
		} else if (!targetGridPos.equals(other.targetGridPos)) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "WarpGate [targetGridPos=" + targetGridPos + "]";
	}
	
}
