
package de.ncm.x3.iam.gui.layout;


import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager2;
import java.util.HashMap;

import org.apache.log4j.Logger;

import de.ncm.x3.iam.data.universe.GridPos;

public class UniverseLayout implements LayoutManager2 {
	
	private static final Logger	          logger	 = Logger.getLogger(UniverseLayout.class);
	protected HashMap<GridPos, Component>	sectors	 = new HashMap<GridPos, Component>();
	private double	                      scale	     = 1.0;
	private Dimension	                  sectorSize	= new Dimension(100, 100);
	private int	                          xSpace	 = 30;
	private int	                          ySpace	 = 30;
	
	public UniverseLayout() {
		super();
	}
	
	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new IllegalArgumentException("Name mapping is not allowed");
		
	}
	
	@Override
	public void removeLayoutComponent(Component comp) {
		for (GridPos key : sectors.keySet()) {
			if (sectors.get(key).equals(comp)) {
				sectors.remove(key);
			}
		}
		
	}
	
	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return parent.getSize();
	}
	
	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return parent.getSize();
	}
	
	@Override
	public Dimension maximumLayoutSize(Container target) {
		return target.getSize();
	}
	
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		GridPos key = (GridPos) constraints;
		sectors.put(key, comp);
	}
	
	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}
	
	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}
	
	@Override
	public void invalidateLayout(Container target) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void layoutContainer(Container parent) {
		for (GridPos gridPos : sectors.keySet()) {
			Component comp = sectors.get(gridPos);
			comp.setLocation((int) (getPixelX(gridPos.gridX) * scale), (int) (getPixelY(gridPos.gridY) * scale));
			comp.setSize((int) (sectorSize.width * scale), (int) (sectorSize.height * scale));
		}
	}
	
	public int getPixelX(int gridX) {
		return (int) (((gridX + 1) * xSpace + gridX * sectorSize.width) * scale * scale);
	}
	
	public int getPixelY(int gridY) {
		return (int) (((gridY + 1) * ySpace + gridY * sectorSize.height) * scale * scale);
	}
	
}
