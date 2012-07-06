
package de.ncm.x3.iam.gui.component.universe;


import java.awt.Point;

import javax.swing.JScrollPane;
import javax.swing.JViewport;

import de.ncm.x3.iam.data.universe.GridPos;
import de.ncm.x3.iam.gui.layout.UniverseLayout;

public class JUniverseMapScrollContainer extends JScrollPane {
	
	private JUniverseMap map;
	
	public JUniverseMapScrollContainer(JUniverseMap map) {
		super(map);
		this.map = map;
		if (!(map.getLayout() instanceof UniverseLayout)) {
			throw new IllegalStateException("No supported Layoutmanager in " + map.getClass().getName());
		}
		// TODO: integrate mouse Movement translation
	}
	
	public void centerViewOnSector(GridPos gridpos) {
		UniverseLayout layout = (UniverseLayout) map.getLayout();
		
		int sectorPixelX = layout.getPixelX(gridpos.gridX);
		int sectorPixelY = layout.getPixelY(gridpos.gridY);
		int sectorWidth = layout.getSectorWidth();
		int sectorHeight = layout.getSectorHeight();
		
		JViewport vp = getViewport();
		
		Point vPos = new Point((sectorWidth / 2 + sectorPixelX - vp.getWidth() / 2), (sectorHeight / 2 + sectorPixelY - vp.getHeight() / 2));
		if (vPos.x < 0) {
			vPos.x = 0;
		}
		if (vPos.y < 0) {
			vPos.y = 0;
		}
		vp.setViewPosition(vPos);
	}
}
