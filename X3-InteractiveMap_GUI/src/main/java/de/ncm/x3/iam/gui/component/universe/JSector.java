
package de.ncm.x3.iam.gui.component.universe;


import java.awt.Graphics2D;

import de.ncm.x3.iam.data.universe.Sector;
import de.ncm.x3.iam.gui.component.JRenderPanel;

public class JSector extends JRenderPanel {
	
	private Sector sector;
	
	public JSector(Sector s) {
		super();
		this.setSector(s);
		
	}
	
	@Override
	public void paintView(Graphics2D g) {
		g.setColor(getBackground());
		g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, getWidth() / 3, getHeight() / 3);
		g.setColor(getForeground());
		g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, getWidth() / 3, getHeight() / 3);
	}
	
	private void setSector(Sector s) {
		this.sector = s;
	}
	
	public Sector getSector() {
		return sector;
	}
	
}
