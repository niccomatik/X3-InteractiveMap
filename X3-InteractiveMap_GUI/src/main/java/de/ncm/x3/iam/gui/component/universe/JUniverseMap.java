
package de.ncm.x3.iam.gui.component.universe;


import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.util.HashMap;

import org.apache.log4j.Logger;

import de.ncm.x3.iam.data.ActualPlayerInfo;
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
	private ActualPlayerInfo actualPlayerInfo = new ActualPlayerInfo();
	private UniverseMap universeMap;
	private HashMap<GridPos, JSector> jUniverseMap = new HashMap<GridPos, JSector>();
	
	public JUniverseMap() {
		super(new UniverseLayout());
		PListener pListener = new PListener();
		ParserFactory.getUniverseMapParser().addParseListener(pListener);
		ParserFactory.getActualPlayerPositionParser().addParseListener(pListener);
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
	
	public void setActualPlayerInfo(ActualPlayerInfo actualPlayerInfo) {
		if (!this.actualPlayerInfo.getSectorPosition().equals(actualPlayerInfo.getSectorPosition())) {
			GridPos oldSector = this.actualPlayerInfo.getSectorPosition();
			GridPos newSector = actualPlayerInfo.getSectorPosition();
			JSector oldJSector = jUniverseMap.get(oldSector);
			if (oldJSector != null) {
				oldJSector.setHighlighted(false);
			}
			
			JSector newJSector = jUniverseMap.get(newSector);
			if (newJSector != null) {
				newJSector.setHighlighted(true);
			}
			this.actualPlayerInfo.setSectorPosition(actualPlayerInfo.getSectorPosition());
		}
		
	}
	
	public void setUniverseMap(UniverseMap map) {
		removeAllSectors();
		logger.info("Adding new Sectors");
		this.universeMap = map;
		for (GridPos gridPos : map.getSectors().keySet()) {
			Sector sector = map.getSectors().get(gridPos);
			JSector jSec = new JSector(sector);
			jSec.setBackground(ColorPackageManager.get().getRaceColor(sector.getRace().getId()));
			add(jSec, gridPos);
			jUniverseMap.put(gridPos, jSec);
		}
		validate();
		repaint();
		if (getParent() != null) {
			getParent().validate();
		}
		logger.info("New Sectors added");
		
	}
	
	private void removeAllSectors() {
		for (GridPos gp : jUniverseMap.keySet()) {
			remove(jUniverseMap.get(gp));
		}
		
	}
	
	private class PListener implements ParseListener {
		
		@Override
		public void onParseStart(ParseEvent e) {}
		
		@Override
		public void onParseEnd(ParseEvent e) {
			if (e.getParsedValue() instanceof UniverseMap) {
				final UniverseMap map = (UniverseMap) e.getParsedValue();
				
				EventQueue.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						setUniverseMap(map);
					}
				});
			} else if (e.getParsedValue() instanceof ActualPlayerInfo) {
				final ActualPlayerInfo actualPlayerInfo = (ActualPlayerInfo) e.getParsedValue();
				EventQueue.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						setActualPlayerInfo(actualPlayerInfo);
					}
				});
				
			} else {
				logger.debug("ParseEvent not covered by JUniverseMap");
			}
			
		}
		
	}
}
