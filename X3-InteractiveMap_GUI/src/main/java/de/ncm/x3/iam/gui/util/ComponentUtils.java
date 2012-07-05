
package de.ncm.x3.iam.gui.util;


import java.awt.Component;
import java.awt.Container;
import java.util.Locale;

import javax.swing.JMenu;
import javax.swing.JTabbedPane;

import org.apache.log4j.Logger;

public abstract class ComponentUtils {
	
	private static final Logger logger = Logger.getLogger(ComponentUtils.class);
	
	public static void setLocaleRecursively(final Component comp, final Locale l) {
		
		comp.setLocale(l);
		
		Component[] children = null;
		
		if (comp instanceof JMenu) {
			children = ((JMenu) comp).getMenuComponents();
		} else if (comp instanceof JTabbedPane) {
			JTabbedPane tabbedPane = (JTabbedPane) comp;
			children = new Component[tabbedPane.getTabCount()];
			for (int i = 0; i < children.length; i++) {
				children[i] = tabbedPane.getComponentAt(i);
			}
		} else if (comp instanceof Container) {
			children = ((Container) comp).getComponents();
		}
		
		for (Component child : children) {
			setLocaleRecursively(child, l);
		}
		
	}
	
}
