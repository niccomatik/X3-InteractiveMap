
package de.ncm.x3.iam.gui.component;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.AbstractButton;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;

import org.apache.log4j.Logger;

import de.ncm.x3.iam.gui.Messages;

public final class ComponentFactory {
	
	private static final Logger logger = Logger.getLogger(ComponentFactory.class);
	
	public static <E extends AbstractButton> E localise(final E menu, final String messageKey) {
		menu.addPropertyChangeListener("locale", new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				menu.setText(Messages.getString(messageKey));
				
			}
		});
		
		return menu;
	}
	
	public static JPopupMenu localise(final JPopupMenu menu, final String messageKey) {
		menu.addPropertyChangeListener("locale", new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				menu.setLabel(Messages.getString(messageKey));
				
			}
		});
		
		return menu;
		
	}
	
	public static JSplitPane createHorizontalJSplitPane(JComponent left, JComponent right, int dividerLocation) {
		JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
		pane.setDividerLocation(dividerLocation);
		return pane;
	}
}
