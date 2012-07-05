
package de.ncm.x3.iam.gui.component.universe;


import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics2D;

import javax.swing.JScrollPane;

import org.apache.log4j.Logger;

import de.ncm.x3.iam.data.universe.GridPos;
import de.ncm.x3.iam.data.universe.Sector;
import de.ncm.x3.iam.data.universe.UniverseMap;
import de.ncm.x3.iam.gui.component.JRenderPanel;
import de.ncm.x3.iam.gui.layout.UniverseLayout;
import de.ncm.x3.iam.parser.ParseEvent;
import de.ncm.x3.iam.parser.ParseListener;
import de.ncm.x3.iam.parser.ParserFactory;

public class JUniverseMap extends JScrollPane {
	
	private static final Logger logger = Logger.getLogger(JUniverseMap.class);
	private ViewPanel viewPanel;
	
	public JUniverseMap() {
		super();
		ParserFactory.getUniverseMapParser().addParseListener(new PListener());
		viewPanel = new ViewPanel();
		setViewportView(viewPanel);
		
	}
	
	private class ViewPanel extends JRenderPanel {
		
		public ViewPanel() {
			super(new UniverseLayout());
			setBackground(Color.BLACK);
			
		}
		
		@Override
		public void paintView(Graphics2D g) {
			g.setColor(getBackground());
			g.fillRect(0, 0, getWidth(), getHeight());
		}
	}
	
	private class PListener implements ParseListener {
		
		@Override
		public void onParseStart(ParseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onParseEnd(ParseEvent e) {
			final UniverseMap map;
			if (e.getParsedValue() instanceof UniverseMap) {
				map = (UniverseMap) e.getParsedValue();
			} else {
				throw new IllegalStateException("Parsed value has to be a UniverseMap");
			}
			
			EventQueue.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					removeAll();
					logger.info("Adding new Sectors");
					for (GridPos gridPos : map.getSectors().keySet()) {
						Sector sector = map.getSectors().get(gridPos);
						// logger.debug("Pos: " + gridPos + "\tSector: " + sector.getName());
						viewPanel.add(new JSector(sector), gridPos);
					}
					viewPanel.validate();
					viewPanel.repaint();
					JUniverseMap.this.validate();
					logger.info("New Sectors added");
				}
			});
			
		}
	}
	
}
