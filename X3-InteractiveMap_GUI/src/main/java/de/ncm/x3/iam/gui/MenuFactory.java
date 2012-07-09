
package de.ncm.x3.iam.gui;


import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Locale;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import org.apache.log4j.Logger;

import de.ncm.x3.iam.bundle.GuiBundleManager;
import de.ncm.x3.iam.data.universe.GridPos;
import de.ncm.x3.iam.gui.component.ComponentFactory;
import de.ncm.x3.iam.gui.component.universe.JUniverseMapScrollContainer;
import de.ncm.x3.iam.gui.util.ComponentUtils;
import de.ncm.x3.iam.parser.ParserManager;
import de.ncm.x3.iam.settings.ColorPackageManager;
import de.ncm.x3.iam.settings.PropertyManager;

public abstract class MenuFactory {
	
	private static final Logger logger = Logger.getLogger(MenuFactory.class);
	
	public static JMenu createMenuData() {
		
		return ComponentFactory.get().createLocalisedComponent("mainframe.menu.data", JMenu.class);
	}
	
	public static JCheckBoxMenuItem createMenuItemParsing(final ParserManager parseManager) {
		final JCheckBoxMenuItem menuItem = ComponentFactory.get().createLocalisedComponent("mainframe.menu.data.parsing", JCheckBoxMenuItem.class);
		
		menuItem.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (menuItem.isSelected()) {
					parseManager.startParsing();
				} else {
					parseManager.stopParsing();
				}
			}
		});
		
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				logger.info("JCheckBoxMenuItemParsing: Set Value from properties");
				boolean enabled = false;
				String prop = PropertyManager.get().getProperty("parser.continuousparsing.enabled");
				if (prop != null) {
					if (prop.trim().equals("true") || prop.trim().equals("1")) {
						enabled = true;
					}
				}
				menuItem.setSelected(enabled);
			}
		});
		return menuItem;
	}
	
	public static JMenuItem createMenuItemClose() {
		JMenuItem menuItem = ComponentFactory.get().createLocalisedComponent("mainframe.menu.data.close", JMenuItem.class);
		menuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Mainframe.quit();
			}
		});
		return menuItem;
	}
	
	public static JMenu createMenuEdit() {
		return ComponentFactory.get().createLocalisedComponent("mainframe.menu.edit", JMenu.class);
	}
	
	public static JMenuItem createMenuItemChooseLogpath() {
		JMenuItem menuItem = ComponentFactory.get().createLocalisedComponent("mainframe.menu.edit.choose_logpath", JMenuItem.class);
		menuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Choose and save Logpath
				
			}
		});
		return menuItem;
	}
	
	public static JMenu createMenuColorPack() {
		JMenu menu = ComponentFactory.get().createLocalisedComponent("mainframe.menu.edit.colorpack", JMenu.class);
		fillMenuColorPack(menu);
		return menu;
	}
	
	private static void fillMenuColorPack(final JMenu menu) {
		// TODO: fill menu with ColorPacks
		for (final String cp : ColorPackageManager.get().listColorPackages()) {
			final JRadioButtonMenuItem item = new JRadioButtonMenuItem(cp);
			if (cp.equalsIgnoreCase(PropertyManager.get().getProperty("colorpackage.actual"))) {
				item.setSelected(true);
			} else {
				item.setSelected(false);
			}
			item.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					for (Component comp : menu.getMenuComponents()) {
						if (comp instanceof JRadioButtonMenuItem) {
							((JRadioButtonMenuItem) comp).setSelected(false);
						}
					}
					item.setSelected(true);
					ColorPackageManager.get().setActualColorPackage(cp);
				}
			});
			
			menu.add(item);
		}
		
	}
	
	public static JMenu createMenuView() {
		return ComponentFactory.get().createLocalisedComponent("mainframe.menu.view", JMenu.class);
	}
	
	public static JCheckBoxMenuItem createMenuItemCenterMapAutomaticOnActualSector() {
		JCheckBoxMenuItem menuItem = ComponentFactory.get().createLocalisedComponent("mainframe.menu.view.ceter_map_on_sector.automatic",
				JCheckBoxMenuItem.class);
		
		menuItem.setSelected(true);
		menuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Implement automatic center on sector
				
			}
		});
		return menuItem;
	}
	
	public static JMenuItem createMenuItemCenterMapOnActualSector(final JUniverseMapScrollContainer scrollContainer) {
		JMenuItem menuItem = ComponentFactory.get().createLocalisedComponent("mainframe.menu.view.ceter_map_on_sector", JMenuItem.class);
		menuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				scrollContainer.centerViewOnSector(new GridPos(5, 5)); // TODO: Replace DummyData with real actual sector
			}
		});
		return menuItem;
	}
	
	public static JMenu createMenuHelp() {
		
		return ComponentFactory.get().createLocalisedComponent("mainframe.menu.help", JMenu.class);
	}
	
	public static JMenu createMenuLanguage() {
		JMenu menu = ComponentFactory.get().createLocalisedComponent("mainframe.menu.help.language", JMenu.class);
		fillMenuLanguage(menu);
		return menu;
	}
	
	private static void fillMenuLanguage(final JMenu menu) {
		
		for (final Locale l : GuiBundleManager.get().getAvailableLocales()) {
			
			final JRadioButtonMenuItem menuItem = new JRadioButtonMenuItem(l.getDisplayLanguage(l));
			
			if (l.getDisplayLanguage(l).equalsIgnoreCase(menuItem.getLocale().getDisplayLanguage(l))) {
				menuItem.setSelected(true);
			} else {
				menuItem.setSelected(false);
			}
			menuItem.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					for (Component item : menu.getMenuComponents()) {
						if (item instanceof JRadioButtonMenuItem) {
							((JRadioButtonMenuItem) item).setSelected(false);
						}
					}
					menuItem.setSelected(true);
					ComponentUtils.setLocaleRecursively(Mainframe.get(), l);
				}
			});
			
			menu.add(menuItem);
			
		}
	}
}
