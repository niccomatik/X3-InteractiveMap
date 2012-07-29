
package de.ncm.x3.iam.gui;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.apache.log4j.Logger;

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
	
	public static JLabel localise(final JLabel label, final String messageKey) {
		label.addPropertyChangeListener("locale", new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				label.setText(Messages.getString(messageKey));
			}
		});
		
		return label;
		
	}
	
	public static JSplitPane createHorizontalJSplitPane(JComponent left, JComponent right, int dividerLocation) {
		JSplitPane pane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, left, right);
		pane.setDividerLocation(dividerLocation);
		return pane;
	}
	
	public static JButton createJButton(String messageKey) {
		JButton button = new JButton(Messages.getString(messageKey));
		button.setLocale(Messages.getActualLocale());
		localise(button, messageKey);
		return button;
	}
	
	public static TreeModel createDummyTreeModel() {
		DefaultMutableTreeNode rootnode = new DefaultMutableTreeNode("Universe");
		DefaultMutableTreeNode sector1 = new DefaultMutableTreeNode("Sector1");
		DefaultMutableTreeNode station1 = new DefaultMutableTreeNode("Station1");
		DefaultMutableTreeNode ship1 = new DefaultMutableTreeNode("Ship1");
		
		station1.setAllowsChildren(false);
		ship1.setAllowsChildren(false);
		
		rootnode.add(sector1);
		sector1.add(station1);
		sector1.add(ship1);
		return new DefaultTreeModel(rootnode);
	}
	
	public static JButton createBrowseButtonTextFieldBinding(final JTextField textField, final String fileChooserTitle, final int fileSelectionMode,
			final PropertyChangeListener changeListener) {
		
		final JButton button = createJButton("BrowseButton.text");
		button.setName(button.getText() + ": " + fileChooserTitle);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				File actualPath = new File(textField.getText()); // Take actual Path
				
				JFileChooser fc = new JFileChooser(actualPath);
				fc.setDialogTitle(fileChooserTitle);
				fc.setFileSelectionMode(fileSelectionMode);
				int returnVal = fc.showOpenDialog(button);
				File f;
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					f = fc.getSelectedFile();
					f.mkdirs();
					changeListener.propertyChange(new PropertyChangeEvent(button, fileChooserTitle, actualPath.getAbsoluteFile(), f.getAbsoluteFile()));
				}
			}
		});
		
		return button;
	}
	
	public static Popup createPopupDialog(JComponent parent, String message) {
		JPanel contents = new JPanel();
		contents.setBackground(new Color(243, 255, 159));
		JLabel errorLabel = new JLabel(message);
		errorLabel.setForeground(Color.RED);
		contents.add(errorLabel);
		return PopupFactory.getSharedInstance().getPopup(parent, contents, parent.getLocationOnScreen().x, parent.getLocationOnScreen().y + parent.getHeight());
		
	}
	
	public static JLabel createJLabel(String messageKey) {
		JLabel label = new JLabel(Messages.getString(messageKey));
		label.setLocale(Messages.getActualLocale());
		localise(label, messageKey);
		return label;
	}
}
