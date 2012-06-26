
package de.ncm.x3.iam.data.universe;


public class GridPos {
	
	public final int	gridX;
	public final int	gridY;
	
	public GridPos(int gridX, int gridY) {
		this.gridX = gridX;
		this.gridY = gridY;
	}
	
	public int getGridX() {
		return gridX;
	}
	
	public int getGridY() {
		return gridY;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + gridX;
		result = prime * result + gridY;
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
		GridPos other = (GridPos) obj;
		if (gridX != other.gridX) {
			return false;
		}
		if (gridY != other.gridY) {
			return false;
		}
		return true;
	}
	
	@Override
	public String toString() {
		return "GridPos [gridX=" + gridX + ", gridY=" + gridY + "]";
	}
	
}
