
package de.ncm.x3.iam.gui.component.universe;


import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics2D;

import org.apache.log4j.Logger;

import de.ncm.x3.iam.data.universe.GridPos;
import de.ncm.x3.iam.data.universe.Sector;
import de.ncm.x3.iam.data.universe.UniverseMap;
import de.ncm.x3.iam.gui.component.JRenderPanel;
import de.ncm.x3.iam.gui.layout.UniverseLayout;
import de.ncm.x3.iam.parser.ParseEvent;
import de.ncm.x3.iam.parser.ParseListener;
import de.ncm.x3.iam.parser.ParserFactory;
import de.ncm.x3.iam.settings.ColorPackageChangedEvent;
import de.ncm.x3.iam.settings.ColorPackageChangedListener;
import de.ncm.x3.iam.settings.ColorPackageManager;

public class JUniverseMap extends JRenderPanel {
	
	private static final Logger logger = Logger.getLogger(JUniverseMap.class);
	
	public JUniverseMap() {
		super(new UniverseLayout());
		
		ParserFactory.getUniverseMapParser().addParseListener(new PListener());
		setBackground(Color.BLACK);
		
		ColorPackageManager.get().setColorPackackageChangedListener(new ColorPackageChangedListener() {
			
			@Override
			public void colorPackageChanged(ColorPackageChangedEvent e) {
				for (Component comp : getComponents()) {
					if (comp instanceof JSector) {
						JSector sec = (JSector) comp;
						sec.setBackground(ColorPackageManager.get().getRaceColor(sec.getSector().getRace().getId()));
					}
				}
				
			}
		});
		
	}
	
	@Override
	public void paintView(Graphics2D g) {
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
	}
	
	private class PListener implements ParseListener {
		
		@Override
		public void onParseStart(ParseEvent e) {}
		
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
						JSector jSec = new JSector(sector);
						jSec.setBackground(ColorPackageManager.get().getRaceColor(sector.getRace().getId()));
						add(jSec, gridPos);
					}
					validate();
					repaint();
					if (getParent() != null) {
						getParent().validate();
					}
					logger.info("New Sectors added");
				}
			});
			
		}
	}
	
}
