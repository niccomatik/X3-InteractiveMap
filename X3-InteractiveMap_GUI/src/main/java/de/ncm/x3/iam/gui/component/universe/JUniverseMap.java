
package de.ncm.x3.iam.gui.component.universe;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;

import de.ncm.x3.iam.data.ActualPlayerInfo;
import de.ncm.x3.iam.data.universe.GateConnection;
import de.ncm.x3.iam.data.universe.GridPos;
import de.ncm.x3.iam.data.universe.Sector;
import de.ncm.x3.iam.data.universe.UniverseMap;
import de.ncm.x3.iam.data.universe.WarpGate;
import de.ncm.x3.iam.data.universe.WarpGateConstants;
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
	private ArrayList<GateConnection> gateConnections = new ArrayList<GateConnection>();
	private boolean updatingGateCalculations = false;
	
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
						sec.reloadImages();
					}
				}
				
			}
		});
		
	}
	
	@Override
	public void paintView(Graphics2D g) {
		g.setColor(getBackground());
		g.fillRect(0, 0, getWidth(), getHeight());
		
		drawGateConnections(g);
	}
	
	public void setActualPlayerInfo(final ActualPlayerInfo actualPlayerInfo) {
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
			} else if (universeMap.getSectors().get(newSector) != null) {
				EventQueue.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						setActualPlayerInfo(actualPlayerInfo); // Sector is there but not added to GUI => Try again
						
					}
				});
			}
			this.actualPlayerInfo.setSectorPosition(actualPlayerInfo.getSectorPosition());
		}
		
	}
	
	public void setUniverseMap(UniverseMap map) {
		removeAllUnnecessarySectors(map.getSectors());
		logger.info("Adding new Sectors");
		this.universeMap = map;
		for (GridPos gridPos : map.getSectors().keySet()) {
			Sector sector = map.getSectors().get(gridPos);
			
			JSector jSec;
			if (jUniverseMap.containsKey(gridPos)) {
				jSec = jUniverseMap.get(gridPos);
				jSec.setSector(sector);
			} else {
				
				jSec = new JSector(sector);
				
				add(jSec, gridPos);
				jUniverseMap.put(gridPos, jSec);
			}
			
			if (gridPos.equals(actualPlayerInfo.getSectorPosition())) {
				jSec.setHighlighted(true);
			}
		}
		calculateGateConnections();
		validate();
		repaint();
		if (getParent() != null) {
			getParent().validate();
		}
		logger.info("New Sectors added");
		
	}
	
	private void removeAllUnnecessarySectors(HashMap<GridPos, Sector> sectors) {
		ArrayList<GridPos> removeList = new ArrayList<GridPos>();
		for (GridPos gp : jUniverseMap.keySet()) {
			if (!sectors.containsKey(gp)) {
				remove(jUniverseMap.get(gp));
				removeList.add(gp);
			}
		}
		for (GridPos gp : removeList) {
			jUniverseMap.remove(gp);
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
	
	public ActualPlayerInfo getActualPlayerInfo() {
		return actualPlayerInfo;
	}
	
	public void calculateGateConnections() {
		updatingGateCalculations = true;
		gateConnections.clear();
		HashMap<GridPos, Sector> sectors = universeMap.getSectors();
		for (GridPos pos : sectors.keySet()) {
			Sector sec = sectors.get(pos);
			for (WarpGate wg : sec.getWarpGates()) {
				WarpGate newWg = new WarpGate(pos);
				if (newWg != null && wg != null) {
					if (!newWg.equals(wg)) { // sector connected with itself -> nothing to paint
						GateConnection gateCon = new GateConnection(newWg, wg, isDirectConnection(newWg.getTargetGridPos(), wg.getTargetGridPos()));
						if (!gateConnections.contains(gateCon)) {
							gateConnections.add(gateCon);
						}
					}
				}
			}
		}
		updatingGateCalculations = false;
	}
	
	public void drawGateConnections(Graphics2D g) {
		while (updatingGateCalculations) {
		} // wait until calculations are ready
		
		// Connection which is not a Line parallel to X- or Y-Axe
		g.setStroke(new BasicStroke(5, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER));
		g.setColor(Color.CYAN);
		for (GateConnection gateCon : gateConnections) {
			if (!gateCon.isDirectConnection()) {
				GridPos gridPos1 = gateCon.getGate2().getTargetGridPos();// WarpGate of sector 2 has sector 1 as target
				GridPos gridPos2 = gateCon.getGate1().getTargetGridPos();
				
				JSector jSec1 = jUniverseMap.get(gridPos1);
				JSector jSec2 = jUniverseMap.get(gridPos2);
				
				int x1 = jSec1.getX();
				int y1 = jSec1.getY();
				int x2 = jSec2.getX();
				int y2 = jSec2.getY();
				
				Point pixelPos1 = calculatePixelPosOfLine(x1, y1, jSec1, gateCon.getGate1());
				Point pixelPos2 = calculatePixelPosOfLine(x2, y2, jSec2, gateCon.getGate2());
				
				g.drawLine(pixelPos1.x, pixelPos1.y, pixelPos2.x, pixelPos2.y);
				// TODO: check if gate is north, south... to adjust the line points
			}
		}
		
		// Normal Connection (on one Axe)
		g.setStroke(new BasicStroke(10));
		g.setColor(Color.WHITE);
		for (GateConnection gateCon : gateConnections) {
			if (gateCon.isDirectConnection()) {
				JSector jSec1 = jUniverseMap.get(gateCon.getGate1().getTargetGridPos());
				JSector jSec2 = jUniverseMap.get(gateCon.getGate2().getTargetGridPos());
				g.drawLine(jSec1.getX() + jSec1.getWidth() / 2, jSec1.getY() + jSec1.getHeight() / 2, jSec2.getX() + jSec2.getWidth() / 2,
						jSec2.getY() + jSec2.getHeight() / 2);
				
			}
		}
		
	}
	
	private Point calculatePixelPosOfLine(int x, int y, JSector jSec, WarpGate gateToCheck) {
		Sector sec = jSec.getSector();
		if (sec.isWarpgate(WarpGateConstants.WARPGATE_NORTH, gateToCheck)) {
			x += jSec.getWidth() / 2;
			
		} else if (sec.isWarpgate(WarpGateConstants.WARPGATE_EAST, gateToCheck)) {
			x += jSec.getWidth();
			y += jSec.getHeight() / 2;
			
		} else if (sec.isWarpgate(WarpGateConstants.WARPGATE_SOUTH, gateToCheck)) {
			x += jSec.getWidth() / 2;
			y += jSec.getHeight();
			
		} else if (sec.isWarpgate(WarpGateConstants.WARPGATE_WEST, gateToCheck)) {
			y += jSec.getHeight() / 2;
		}
		
		return new Point(x, y);
	}
	
	public boolean isDirectConnection(GridPos sector1, GridPos sector2) {
		int dx = sector1.gridX - sector2.gridX;
		int dy = sector1.gridY - sector2.gridY;
		
		if (dx == 0 && Math.abs(dy) == 1 || dy == 0 && Math.abs(dx) == 1) {
			return true;
		}
		
		if (dy == 0) {
			int minX = Math.min(sector1.gridX, sector2.gridX);
			int maxX = Math.max(sector1.gridX, sector2.gridX);
			for (int i = minX; i < maxX; i++) {
				if (universeMap.getSectors().get(new GridPos(i, sector1.gridY)) != null) {
					return true;
				}
			}
		}
		
		if (dx == 0) {
			int minY = Math.min(sector1.gridY, sector2.gridY);
			int maxY = Math.max(sector1.gridY, sector2.gridY);
			for (int i = minY; i < maxY; i++) {
				if (universeMap.getSectors().get(new GridPos(sector1.gridX, i)) != null) {
					return true;
				}
			}
		}
		
		return false;
	}
}
