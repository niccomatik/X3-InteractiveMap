
package de.ncm.x3.iam.gui.component;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;

import javax.swing.JPanel;

public abstract class JRenderPanel extends JPanel {
	
	private Graphics2D	g2d; // With field java don't have to reserve new
	                         // space for every Paint
	                         
	public JRenderPanel() {
		this(null);
		
	}
	
	public JRenderPanel(LayoutManager layout) {
		super(layout);
		setVisible(true);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		g2d = (Graphics2D) g;
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		paintView(g2d);
	}
	
	public abstract void paintView(Graphics2D g);
	
	@Override
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}
	
	@Override
	public Dimension getMaximumSize() {
		return getPreferredSize();
	}
	
}
