
package de.ncm.x3.iam.gui.component.universe;


import java.awt.Point;

import javax.swing.JScrollPane;
import javax.swing.JViewport;

import de.ncm.x3.iam.data.universe.GridPos;
import de.ncm.x3.iam.gui.layout.UniverseLayout;
import de.ncm.x3.iam.gui.listener.universe.HandScrollListener;

public class JUniverseMapScrollContainer extends JScrollPane {
	
	private JUniverseMap map;
	
	public JUniverseMapScrollContainer(JUniverseMap map) {
		super(map);
		this.map = map;
		if (!(map.getLayout() instanceof UniverseLayout)) {
			throw new IllegalStateException("No supported Layoutmanager in " + map.getClass().getName());
		}
		HandScrollListener scrollListener = new HandScrollListener(map);
		getViewport().addMouseMotionListener(scrollListener);
		getViewport().addMouseListener(scrollListener);
	}
	
	public void centerViewOnSector(GridPos gridpos) {
		UniverseLayout layout = (UniverseLayout) map.getLayout();
		
		int sectorPixelX = layout.getPixelX(gridpos.gridX);
		int sectorPixelY = layout.getPixelY(gridpos.gridY);
		int sectorWidth = layout.getSectorWidth();
		int sectorHeight = layout.getSectorHeight();
		
		JViewport vp = getViewport();
		
		Point vPos = new Point((sectorWidth / 2 + sectorPixelX - vp.getWidth() / 2), (sectorHeight / 2 + sectorPixelY - vp.getHeight() / 2));
		
		setViewPosition(vPos);
		setAutoscrolls(false);
	}
	
	public Point getViewPosition() {
		return getViewport().getViewPosition();
	}
	
	public void setViewPosition(Point p) {
		if (p.x < 0) {
			p.x = 0;
		}
		if (p.y < 0) {
			p.y = 0;
		}
		getViewport().setViewPosition(p);
	}
	
}
