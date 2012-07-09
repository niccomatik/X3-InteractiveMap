
package de.ncm.x3.iam.gui.component.universe;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import de.ncm.x3.iam.data.universe.Sector;
import de.ncm.x3.iam.data.universe.WarpGate;
import de.ncm.x3.iam.data.universe.WarpGateConstants;
import de.ncm.x3.iam.gui.component.JRenderPanel;

public class JSector extends JRenderPanel implements WarpGateConstants {
	
	private Sector sector;
	
	private JLabel jLabelSectorName;
	private JLabel[] jLabelWarpGate;
	
	private Color warpGateTextColor = new Color(255, 150, 0);
	
	public JSector(Sector s) {
		super(new BorderLayout());
		this.setSector(s);
		setBorder(new EmptyBorder(5, 5, 5, 5));
		
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
		initSectorInfo();
		for (byte i = 0; i < jLabelWarpGate.length; i++) {
			WarpGate gate = s.getWarpGate(i);
			if (gate != null && gate.exists()) {
				jLabelWarpGate[i].setToolTipText("TargetSector: " + gate.getTargetGridPos());
				jLabelWarpGate[i].setForeground(warpGateTextColor);
			} else {
				jLabelWarpGate[i].setForeground(new Color(255, 255, 255, 0));
			}
		}
		jLabelSectorName.setText("<html><body style=\"text-align: center;\">" + s.getName() + "</body></html>");
		jLabelSectorName.setToolTipText(s.getName());
		
	}
	
	private void initSectorInfo() {
		
		if (jLabelSectorName == null || jLabelWarpGate == null) {
			this.jLabelWarpGate = new JLabel[WARPGATE_MAX_VALUE + 1];
			this.jLabelWarpGate[WARPGATE_NORTH] = new JLabel("N");
			this.jLabelWarpGate[WARPGATE_EAST] = new JLabel("E");
			this.jLabelWarpGate[WARPGATE_SOUTH] = new JLabel("S");
			this.jLabelWarpGate[WARPGATE_WEST] = new JLabel("W");
			
			for (byte i = 0; i < jLabelWarpGate.length; i++) {
				setupLabel(jLabelWarpGate[i], i);
			}
			
			this.jLabelSectorName = new JLabel("SectorName");
			// jLabelSectorName.set
			Font font = jLabelSectorName.getFont();
			font = new Font(font.getName(), Font.BOLD, font.getSize());
			jLabelSectorName.setFont(font);
			jLabelSectorName.setHorizontalAlignment(SwingConstants.CENTER);
			jLabelSectorName.setBorder(new EmptyBorder(2, 2, 2, 2));
			
			add(this.jLabelWarpGate[WARPGATE_NORTH], BorderLayout.NORTH);
			add(jLabelWarpGate[WARPGATE_EAST], BorderLayout.EAST);
			add(jLabelWarpGate[WARPGATE_SOUTH], BorderLayout.SOUTH);
			add(jLabelWarpGate[WARPGATE_WEST], BorderLayout.WEST);
			add(jLabelSectorName, BorderLayout.CENTER);
			
		}
		
	}
	
	private static JLabel setupLabel(JLabel label, byte warpGateID) {
		Font font = label.getFont();
		font = new Font(font.getName(), Font.BOLD, font.getSize() + 2);
		label.setFont(font);
		// label.setBackground(new Color(0, 100, 255, 150));
		// label.setOpaque(true);
		
		switch (warpGateID) {
			case WARPGATE_NORTH:
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setVerticalAlignment(SwingConstants.TOP);
				break;
			case WARPGATE_EAST:
				label.setHorizontalAlignment(SwingConstants.RIGHT);
				label.setVerticalAlignment(SwingConstants.CENTER);
				break;
			case WARPGATE_SOUTH:
				label.setHorizontalAlignment(SwingConstants.CENTER);
				label.setVerticalAlignment(SwingConstants.BOTTOM);
				break;
			case WARPGATE_WEST:
				label.setHorizontalAlignment(SwingConstants.LEFT);
				label.setVerticalAlignment(SwingConstants.CENTER);
				break;
		
		}
		return label;
	}
	
}
