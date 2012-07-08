
package de.ncm.x3.iam.gui.component.universe;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;

import de.ncm.x3.iam.data.universe.Sector;
import de.ncm.x3.iam.data.universe.WarpGate;
import de.ncm.x3.iam.data.universe.WarpGateConstants;
import de.ncm.x3.iam.gui.component.JRenderPanel;

public class JSector extends JRenderPanel implements WarpGateConstants {
	
	private Sector sector;
	
	private JLabel jLabelSectorName;
	private JLabel[] jLabelWarpGate;
	
	public JSector(Sector s) {
		super(new GridBagLayout());
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
		drawSectorInfo(s);
	}
	
	public Sector getSector() {
		return sector;
	}
	
	private void drawSectorInfo(Sector s) {
		removeAll();
		if (jLabelSectorName == null || jLabelWarpGate == null) {
			this.jLabelWarpGate = new JLabel[WARPGATE_MAX_VALUE + 1];
			this.jLabelWarpGate[WARPGATE_NORTH] = new JLabel("N");
			this.jLabelWarpGate[WARPGATE_EAST] = new JLabel("E");
			this.jLabelWarpGate[WARPGATE_SOUTH] = new JLabel("S");
			this.jLabelWarpGate[WARPGATE_WEST] = new JLabel("W");
			
			for (int i = 0; i < jLabelWarpGate.length; i++) {
				Font font = jLabelWarpGate[i].getFont();
				font = new Font(font.getName(), Font.BOLD, font.getSize() + 2);
				jLabelWarpGate[i].setFont(font);
				jLabelWarpGate[i].setForeground(new Color(255, 150, 0));
			}
			
			this.jLabelSectorName = new JLabel("SectorName");
			
			add(this.jLabelWarpGate[WARPGATE_NORTH], createGridBagConstraints(1, 0));
			add(jLabelWarpGate[WARPGATE_EAST], createGridBagConstraints(2, 1));
			add(jLabelWarpGate[WARPGATE_SOUTH], createGridBagConstraints(1, 2));
			add(jLabelWarpGate[WARPGATE_WEST], createGridBagConstraints(0, 1));
			add(jLabelSectorName, createGridBagConstraints(1, 1));
			
		}
		for (byte i = 0; i < jLabelWarpGate.length; i++) {
			WarpGate gate = s.getWarpGate(i);
			if (gate != null && gate.exists()) {
				jLabelWarpGate[i].setToolTipText("TargetSector: " + gate.getTargetGridPos());
				jLabelWarpGate[i].setVisible(true);
			} else {
				jLabelWarpGate[i].setVisible(false);
			}
		}
		jLabelSectorName.setText(s.getName());
		
	}
	
	private static GridBagConstraints createGridBagConstraints(int x, int y) {
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = x;
		constraints.gridy = y;
		return constraints;
	}
	
}
