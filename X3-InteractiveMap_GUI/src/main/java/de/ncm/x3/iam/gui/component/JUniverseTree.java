
package de.ncm.x3.iam.gui.component;


import java.awt.EventQueue;
import java.util.Enumeration;
import java.util.List;
import java.util.TreeSet;

import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import de.ncm.x3.iam.data.ActualPlayerInfo;
import de.ncm.x3.iam.data.universe.GridPos;
import de.ncm.x3.iam.data.universe.Sector;
import de.ncm.x3.iam.data.universe.SpaceStation;
import de.ncm.x3.iam.data.universe.UniverseMap;
import de.ncm.x3.iam.data.universe.WarpGate;
import de.ncm.x3.iam.data.universe.WarpGateConstants;
import de.ncm.x3.iam.parser.ParseEvent;
import de.ncm.x3.iam.parser.ParseListener;
import de.ncm.x3.iam.parser.ParserFactory;

public class JUniverseTree extends JTree {
	
	private static final long serialVersionUID = 4593574589859462379L;
	
	private UniverseMap universeMap;
	private TSListener treeListener = new TSListener();
	
	public JUniverseTree() {
		super(new DefaultTreeModel(new DefaultMutableTreeNode("Universe")));
		ParserFactory.getUniverseMapParser().addParseListener(new PListener());
		getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		
		addTreeSelectionListener(treeListener);
		addTreeExpansionListener(treeListener);
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
				
			} else if (value instanceof List) {
				List list = ((List) value);
				if (list.size() > 0 && list.get(0) instanceof SpaceStation) {
					label = "Space Stations";
				}
			} else if (value instanceof SpaceStation) {
				label = ((SpaceStation) value).getName();
			}
			
		}
		
		return label;
	}
	
	protected void universeMapChanged(UniverseMap universeMap) {
		this.universeMap = universeMap;
		DefaultMutableTreeNode universeNode = new DefaultMutableTreeNode(universeMap);
		// Start Sector Sorting
		TreeSet<Sector> sectors = new TreeSet<Sector>();
		
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
			
			DefaultMutableTreeNode stationsNode = new DefaultMutableTreeNode(sector.getSpaceStations());
			sectorNode.add(stationsNode);
			
			TreeSet<SpaceStation> docks = new TreeSet<SpaceStation>();
			TreeSet<SpaceStation> factories = new TreeSet<SpaceStation>();
			
			for (SpaceStation station : sector.getSpaceStations()) { // Sort Factories
				if (station.isFactory()) {
					factories.add(station);
				} else if (station.isDock()) {
					docks.add(station);
				}
			}
			for (SpaceStation station : docks) {
				stationsNode.add(new DefaultMutableTreeNode(station, false));
			}
			for (SpaceStation station : factories) {
				stationsNode.add(new DefaultMutableTreeNode(station, false));
			}
			
		}
		getModelCasted().setRoot(universeNode);
		
	}
	
	public void expandAll(JTree tree, TreePath parent, boolean expand) {
		// Traverse children
		TreeNode node = (TreeNode) parent.getLastPathComponent();
		if (node.getChildCount() >= 0) {
			@SuppressWarnings("rawtypes")
			Enumeration e = node.children();
			while (e.hasMoreElements()) {
				TreeNode n = (TreeNode) e.nextElement();
				TreePath path = parent.pathByAddingChild(n);
				expandAll(tree, path, expand);
			}
		}
		// Expansion or collapse must be done bottom-up
		if (expand) {
			tree.expandPath(parent);
		} else {
			tree.collapsePath(parent);
		}
	}
	
	private DefaultTreeModel getModelCasted() {
		return (DefaultTreeModel) getModel();
	}
	
	public void selectSector(Sector sector) {
		if (sector == null) {
			return;
		}
		DefaultMutableTreeNode rootNode = (DefaultMutableTreeNode) getModelCasted().getRoot();
		
		for (int i = 0; i < rootNode.getChildCount(); i++) {
			DefaultMutableTreeNode sectorNode = (DefaultMutableTreeNode) rootNode.getChildAt(i);
			
			if (sector.equals(sectorNode.getUserObject())) {
				TreePath path = new TreePath(sectorNode.getPath());
				this.expandAll(this, path, true);
				this.setSelectionPath(path);
				this.scrollPathToVisible(path); // First ensure SectorNode is in view
				if (rootNode.getChildCount() > i + 1) { // then show last Node
					DefaultMutableTreeNode nextRow = (DefaultMutableTreeNode) rootNode.getChildAt(i + 1);
					this.scrollPathToVisible(new TreePath(nextRow.getPath()));
				} else {
					this.scrollPathToVisible(getVeryLastChildOf(sectorNode));
				}
				
				return;
			}
		}
	}
	
	public TreePath getVeryLastChildOf(DefaultMutableTreeNode node) {
		while (node.getChildCount() > 0) {
			node = (DefaultMutableTreeNode) node.getLastChild();
		}
		return new TreePath(node.getPath());
	}
	
	private class PListener implements ParseListener {
		
		@Override
		public void onParseStart(ParseEvent e) {}
		
		@Override
		public void onParseEnd(final ParseEvent e) {
			if (e.getParsedValue() instanceof UniverseMap) {
				EventQueue.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						JUniverseTree.this.universeMapChanged((UniverseMap) e.getParsedValue());
						
					}
				});
			} else if (e.getParsedValue() instanceof ActualPlayerInfo) {
				EventQueue.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						// setSelectionPath(new TreePath(lastPathComponent))
						
					}
				});
			}
			
		}
	}
	
	private class TSListener implements TreeSelectionListener, TreeExpansionListener {
		
		@Override
		public void valueChanged(TreeSelectionEvent e) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) getLastSelectedPathComponent();
			// if (e.getOldLeadSelectionPath() != null) {
			// expandAll(JUniverseTree.this, e.getOldLeadSelectionPath(), false);
			// }
			
			if (node == null) {
				// Nothing is selected.
				return;
			}
			
		}
		
		@Override
		public void treeExpanded(TreeExpansionEvent event) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) event.getPath().getLastPathComponent();
			if (node.getUserObject() instanceof Sector) {
				expandAll(JUniverseTree.this, event.getPath(), true);
			}
			
		}
		
		@Override
		public void treeCollapsed(TreeExpansionEvent event) {
			// TODO Auto-generated method stub
			
		}
	}
	
}
