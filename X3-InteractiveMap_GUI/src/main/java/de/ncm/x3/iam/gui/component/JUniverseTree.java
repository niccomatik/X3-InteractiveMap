
package de.ncm.x3.iam.gui.component;


import java.awt.EventQueue;
import java.util.Comparator;
import java.util.TreeSet;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import de.ncm.x3.iam.data.universe.GridPos;
import de.ncm.x3.iam.data.universe.Sector;
import de.ncm.x3.iam.data.universe.UniverseMap;
import de.ncm.x3.iam.data.universe.WarpGate;
import de.ncm.x3.iam.data.universe.WarpGateConstants;
import de.ncm.x3.iam.parser.ParseEvent;
import de.ncm.x3.iam.parser.ParseListener;
import de.ncm.x3.iam.parser.ParserFactory;

public class JUniverseTree extends JTree {
	
	private UniverseMap universeMap;
	
	public JUniverseTree() {
		super(new DefaultTreeModel(new DefaultMutableTreeNode("Universe")));
		ParserFactory.getUniverseMapParser().addParseListener(new PListener());
	}
	
	@Override
	public String convertValueToText(Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		String label = super.convertValueToText(value, selected, expanded, leaf, row, hasFocus);
		
		if (value instanceof DefaultMutableTreeNode) {
			value = ((DefaultMutableTreeNode) value).getUserObject();
			// TODO: Localise
			if (value instanceof UniverseMap) {
				label = "Universe";
			} else if (value instanceof Sector) {
				label = ((Sector) value).getName();
			} else if (value instanceof WarpGate[]) {
				label = "WarpGates";
			} else if (value instanceof Object[] && ((Object[]) value).length == 2) {
				WarpGate gate = (WarpGate) ((Object[]) value)[0];
				byte id = (Byte) ((Object[]) value)[1];
				
				if (id == WarpGateConstants.WARPGATE_NORTH) {
					label = "North: ";
				} else if (id == WarpGateConstants.WARPGATE_EAST) {
					label = "East: ";
				} else if (id == WarpGateConstants.WARPGATE_SOUTH) {
					label = "South: ";
				} else if (id == WarpGateConstants.WARPGATE_WEST) {
					label = "West: ";
				}
				if (gate != null) {
					label += universeMap.getSectorName(gate.getTargetGridPos());
				}
				
			}
		}
		
		return label;
	}
	
	protected void universeMapChanged(UniverseMap universeMap) {
		this.universeMap = universeMap;
		DefaultMutableTreeNode universeNode = new DefaultMutableTreeNode(universeMap);
		// Start Sector Sorting
		TreeSet<Sector> sectors = new TreeSet<Sector>(new Comparator<Sector>() {
			
			@Override
			public int compare(Sector s1, Sector s2) {
				return s1.getName().compareTo(s2.getName());
			}
		});
		
		for (GridPos gp : universeMap.getSectors().keySet()) {
			sectors.add(universeMap.getSectors().get(gp));
		}
		// End Sector Sorting
		
		for (Sector sector : sectors) {
			DefaultMutableTreeNode sectorNode = new DefaultMutableTreeNode(sector);
			universeNode.add(sectorNode);
			DefaultMutableTreeNode warpGateNode = new DefaultMutableTreeNode(sector.getWarpGates());
			sectorNode.add(warpGateNode);
			
			if (sector.getWarpGates()[WarpGateConstants.WARPGATE_NORTH] != null) {
				warpGateNode.add(new DefaultMutableTreeNode(new Object[] { sector.getWarpGates()[WarpGateConstants.WARPGATE_NORTH],
						WarpGateConstants.WARPGATE_NORTH }, false));
			}
			if (sector.getWarpGates()[WarpGateConstants.WARPGATE_EAST] != null) {
				warpGateNode.add(new DefaultMutableTreeNode(new Object[] { sector.getWarpGates()[WarpGateConstants.WARPGATE_EAST],
						WarpGateConstants.WARPGATE_EAST }, false));
			}
			if (sector.getWarpGates()[WarpGateConstants.WARPGATE_SOUTH] != null) {
				warpGateNode.add(new DefaultMutableTreeNode(new Object[] { sector.getWarpGates()[WarpGateConstants.WARPGATE_SOUTH],
						WarpGateConstants.WARPGATE_SOUTH }, false));
			}
			if (sector.getWarpGates()[WarpGateConstants.WARPGATE_WEST] != null) {
				warpGateNode.add(new DefaultMutableTreeNode(new Object[] { sector.getWarpGates()[WarpGateConstants.WARPGATE_WEST],
						WarpGateConstants.WARPGATE_WEST }, false));
			}
			
		}
		setModel(new DefaultTreeModel(universeNode));
	}
	
	private class PListener implements ParseListener {
		
		@Override
		public void onParseStart(ParseEvent e) {}
		
		@Override
		public void onParseEnd(final ParseEvent e) {
			if (!(e.getParsedValue() instanceof UniverseMap)) {
				return;
			}
			EventQueue.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					JUniverseTree.this.universeMapChanged((UniverseMap) e.getParsedValue());
					
				}
			});
			
		}
	}
	
}
