
package de.ncm.x3.iam.gui.component;


import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

public class UniverseOverviewTreeCellRenderer extends DefaultTreeCellRenderer {
	
	public UniverseOverviewTreeCellRenderer() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
		super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
		setText(convertValueToText(tree, value));
		
		return this;
	}
	
	private String convertValueToText(JTree tree, Object value) {
		String label = tree.convertValueToText(value, false, false, false, 0, false);
		return label;
	}
	
}
