
package de.ncm.x3.iam.gui;


import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import de.ncm.x3.iam.gui.component.ComponentFactory;
import de.ncm.x3.iam.gui.component.JMenuSeperator;
import de.ncm.x3.iam.parser.ParserManager;
import de.ncm.x3.iam.settings.PropertyManager;

public final class MenuFactory {
	
	private static final Logger logger = Logger.getLogger(MenuFactory.class);
	
	/**
	 * @wbp.factory
	 * @wbp.factory.parameter.source messageKey The KeyString mentioned in the language file
	 */
	public static JMenu createJMenu(String messageKey) {
		JMenu menu = new JMenu(Messages.getString(messageKey));
		menu.setLocale(Messages.getActualLocale());
		ComponentFactory.localise(menu, messageKey);
		return menu;
	}
	
	/**
	 * @wbp.factory
	 * @wbp.factory.parameter.source messageKey The KeyString mentioned in the language file
	 */
	public static JMenuItem createJMenuItem(String messageKey) {
		JMenuItem menuItem = new JMenuItem(Messages.getString(messageKey));
		menuItem.setLocale(Messages.getActualLocale());
		ComponentFactory.localise(menuItem, messageKey);
		return menuItem;
	}
	
	/**
	 * @wbp.factory
	 * @wbp.factory.parameter.source messageKey The KeyString mentioned in the language file
	 */
	public static JCheckBoxMenuItem createJCheckBoxMenuItem(String messageKey) {
		JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem(Messages.getString(messageKey));
		menuItem.setLocale(Messages.getActualLocale());
		ComponentFactory.localise(menuItem, messageKey);
		return menuItem;
	}
	
	/**
	 * @wbp.factory
	 * @wbp.factory.parameter.source messageKey The KeyString mentioned in the language file
	 */
	public static JRadioButtonMenuItem createJRadioButtonMenuItem(String messageKey) {
		JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem(Messages.getString(messageKey));
		menuItem.setLocale(Messages.getActualLocale());
		ComponentFactory.localise(menuItem, messageKey);
		return menuItem;
	}
	
	/**
	 * @wbp.factory
	 * @wbp.factory.parameter.source messageKey The KeyString mentioned in the language file
	 */
	public static JPopupMenu createJPopupMenu(String messageKey) {
		JPopupMenu menu = new JPopupMenu(Messages.getString(messageKey));
		menu.setLocale(Messages.getActualLocale());
		ComponentFactory.localise(menu, messageKey);
		return menu;
	}
	
	/**
	 * @wbp.factory
	 */
	public static JMenuSeperator createJMenuSeperator() {
		JMenuSeperator seperator = new JMenuSeperator();
		return seperator;
	}
	
	public static JMenuItem setupMenuItemExit(JMenuItem menuItem) {
		// menuItem.setMnemonic(KeyEvent.VK_Q);
		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		
		menuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Mainframe.quit();
			}
		});
		return menuItem;
	}
	
	public static JCheckBoxMenuItem setupMenuItemParsingFiles(final JCheckBoxMenuItem menuItem) {
		menuItem.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (menuItem.isSelected()) {
					ParserManager.get().startParsing();
				} else {
					ParserManager.get().stopParsing();
				}
				PropertyManager.get().setContinousParsingEnabled(menuItem.isSelected());
			}
		});
		
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				logger.info("JCheckBoxMenuItemParsing: Set Value from properties");
				
				menuItem.setSelected(PropertyManager.get().getContinousParsingEnabled());
			}
		});
		return menuItem;
		
	}
	
	public static JMenu setupMenuEdit(JMenu menu) {
		// TODO Auto-generated method stub
		return menu;
	}
	
	public static JMenuItem setupMenuItemInstallScripts(JMenuItem menuItem) {
		// TODO Auto-generated method stub
		
		return menuItem;
		
	}
	
	public static JMenuItem setupMenuItemSettings(JMenuItem menuItem, final Frame comp) {
		menuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JSettingsDialog dialog = new JSettingsDialog(comp);
				dialog.setVisible(true);
			}
		});
		return menuItem;
	}
	
	public static JMenu setupMenuView(JMenu menuItem) {
		// TODO Auto-generated method stub
		return menuItem;
	}
	
	public static JCheckBoxMenuItem setupMenuItemCenterMapAutomatically(final JCheckBoxMenuItem menuItem) {
		menuItem.setSelected(new Boolean(PropertyManager.get().getAutomaticCenterEnabled()));
		menuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PropertyManager.get().setAutomaticCenterEnabled(menuItem.isSelected());
			}
		});
		
		return menuItem;
	}
	
	public static JMenuItem setupMenuItemCenterMap(JMenuItem menuItem, final Mainframe mf) {
		menuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mf.getJUniverseMapScrollContainer().centerViewOnSector(mf.getJUniverseMap().getActualPlayerInfo().getSectorPosition());
			}
		});
		return menuItem;
	}
	
	public static JMenu setupMenuHelp(JMenu menu) {
		// TODO Auto-generated method stub
		return menu;
	}
	
	public static JMenuItem setupMenuItemAbout(JMenuItem menuItem) {
		// TODO Auto-generated method stub
		return menuItem;
	}
	
	public static JMenu setupMenuData(JMenu menu) {
		// TODO Auto-generated method stub
		return menu;
	}
	
}
