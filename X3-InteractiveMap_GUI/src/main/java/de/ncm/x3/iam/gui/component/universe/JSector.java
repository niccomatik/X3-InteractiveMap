
package de.ncm.x3.iam.gui.component.universe;


import java.awt.Graphics2D;
import java.awt.LayoutManager;

import de.ncm.x3.iam.gui.component.JRenderPanel;

public class JSector extends JRenderPanel {
	
	public JSector() {
		// TODO Auto-generated constructor stub
	}
	
	public JSector(LayoutManager layout) {
		super(layout);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void paintView(Graphics2D g) {
		g.setColor(getBackground());
		g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, getWidth() / 3, getHeight() / 3);
		g.setColor(getForeground());
		g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, getWidth() / 3, getHeight() / 3);
	}
	
}
