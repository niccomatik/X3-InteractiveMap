
package de.ncm.x3.iam.gui;


import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import org.apache.log4j.Logger;

import de.ncm.x3.iam.data.ScriptManager;
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
	
	// ************************
	// ************************************************
	// ************************************************************************
	// ************************************************************************************************
	/**
	 * @wbp.factory
	 */
	public static JMenuItem createMenuItemQuit() {
		JMenuItem menuItem = createJMenuItem("Mainframe.mntmQuit.text");
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
	
	/**
	 * @wbp.factory
	 */
	public static JCheckBoxMenuItem createMenuItemParsingFiles() {
		final JCheckBoxMenuItem menuItem = createJCheckBoxMenuItem("Mainframe.chckbxmntmParsingFiles.text");
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
	
	/**
	 * @wbp.factory
	 */
	public static JMenu createMenuEdit() {
		JMenu menu = MenuFactory.createJMenu("Mainframe.mnEdit.text");
		return menu;
	}
	
	/**
	 * @wbp.factory
	 * @wbp.factory.parameter.source mf The Mainframe
	 */
	public static JMenuItem createMenuItemInstallScripts(final Mainframe mf) {
		JMenuItem menuItem = createJMenuItem("Mainframe.mntmInstallScripts.text");
		menuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				File gameFolder = new File(PropertyManager.get().getGameFolder());
				if (gameFolder.exists()) {
					ScriptManager.get().installScriptsTo(gameFolder);
				} else {
					JOptionPane.showMessageDialog(mf, Messages.getString("Error.path.notExisting"), "Error", JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
		
		return menuItem;
		
	}
	
	/**
	 * @wbp.factory
	 * @wbp.factory.parameter.source mf The Mainframe
	 */
	public static JMenuItem createMenuItemSettings(final Mainframe comp) {
		JMenuItem menuItem = MenuFactory.createJMenuItem("Mainframe.mntmSettings.text");
		menuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JSettingsDialog dialog = new JSettingsDialog(comp);
				dialog.setVisible(true);
			}
		});
		return menuItem;
	}
	
	/**
	 * @wbp.factory
	 */
	public static JMenu createMenuView() {
		JMenu menuItem = MenuFactory.createJMenu("Mainframe.mnView.text");
		return menuItem;
	}
	
	/**
	 * @wbp.factory
	 */
	public static JCheckBoxMenuItem createMenuItemCenterMapAutomatically() {
		final JCheckBoxMenuItem menuItem = MenuFactory.createJCheckBoxMenuItem("Mainframe.mntmCenterMapAutomatically.text");
		menuItem.setSelected(new Boolean(PropertyManager.get().getAutomaticCenterEnabled()));
		menuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PropertyManager.get().setAutomaticCenterEnabled(menuItem.isSelected());
			}
		});
		
		return menuItem;
	}
	
	/**
	 * @wbp.factory
	 * @wbp.factory.parameter.source mf The Mainframe
	 */
	public static JMenuItem createMenuItemCenterMap(final Mainframe mf) {
		JMenuItem menuItem = MenuFactory.createJMenuItem("Mainframe.mntmCenterMapOn.text");
		menuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mf.getJUniverseMapScrollContainer().centerViewOnSector(mf.getJUniverseMap().getActualPlayerInfo().getSectorPosition());
			}
		});
		return menuItem;
	}
	
	/**
	 * @wbp.factory
	 */
	public static JMenu createMenuHelp() {
		JMenu menu = MenuFactory.createJMenu("Mainframe.mnHelp.text");
		return menu;
	}
	
	/**
	 * @wbp.factory
	 */
	public static JMenuItem createMenuItemAbout() {
		JMenuItem menuItem = MenuFactory.createJMenuItem("Mainframe.mntmAbout.text");
		return menuItem;
	}
	
	/**
	 * @wbp.factory
	 */
	public static JMenu createMenuData() {
		JMenu menu = MenuFactory.createJMenu("Mainframe.mnData.text");
		return menu;
	}
	
}
