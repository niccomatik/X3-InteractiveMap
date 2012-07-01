
package de.ncm.x3.iam.gui.component.universe;


import java.awt.Color;
import java.awt.Graphics2D;

import de.ncm.x3.iam.gui.component.JRenderPanel;
import de.ncm.x3.iam.gui.layout.UniverseLayout;

public class JUniverseMap extends JRenderPanel {
	
	public JUniverseMap() {
		super(new UniverseLayout());
		setBackground(Color.BLACK);
	}
	
	@Override
	public void paintView(Graphics2D g) {
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
	}
	
}
