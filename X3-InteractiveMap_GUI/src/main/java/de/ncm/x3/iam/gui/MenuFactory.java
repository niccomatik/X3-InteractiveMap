
package de.ncm.x3.iam.gui;


import java.awt.Component;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.util.Locale;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import de.ncm.x3.iam.bundle.GuiBundleManager;
import de.ncm.x3.iam.data.ScriptManager;
import de.ncm.x3.iam.gui.component.ComponentFactory;
import de.ncm.x3.iam.gui.util.ComponentUtils;
import de.ncm.x3.iam.parser.ParserFactory;
import de.ncm.x3.iam.parser.ParserManager;
import de.ncm.x3.iam.settings.ColorPackageManager;
import de.ncm.x3.iam.settings.PropertyManager;

public abstract class MenuFactory {
	
	private static final Logger logger = Logger.getLogger(MenuFactory.class);
	
	/**
	 * @wbp.factory
	 */
	public static JMenu createMenuData() {
		JMenu menu = new JMenu();
		ComponentFactory.get().createLocalisedComponent("mainframe.menu.data", menu);
		return menu;
	}
	
	public static JCheckBoxMenuItem createMenuItemParsing() {
		final JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem();
		ComponentFactory.get().createLocalisedComponent("mainframe.menu.data.parsing", menuItem);
		
		menuItem.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (menuItem.isSelected()) {
					ParserManager.get().startParsing();
				} else {
					ParserManager.get().stopParsing();
				}
				PropertyManager.get().setProperty("parser.continuousparsing.enabled", menuItem.isSelected());
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
		JMenuItem menuItem = new JMenuItem();
		ComponentFactory.get().createLocalisedComponent("mainframe.menu.data.close", menuItem);
		menuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Mainframe.quit();
			}
		});
		return menuItem;
	}
	
	public static JMenu createMenuEdit() {
		JMenu menu = new JMenu();
		ComponentFactory.get().createLocalisedComponent("mainframe.menu.edit", menu);
		return menu;
	}
	
	public static JMenuItem createMenuItemChooseLogpath() {
		final JMenuItem menuItem = new JMenuItem();
		ComponentFactory.get().createLocalisedComponent("mainframe.menu.edit.choose_logpath", menuItem);
		menuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc;
				File propDefinedFile = new File(PropertyManager.get().getProperty("log.parser.xml.path"));
				if (propDefinedFile.exists()) {
					fc = new JFileChooser(propDefinedFile);
				} else {
					fc = new JFileChooser(System.getProperty("user.home"));
				}
				
				fc.setLocale(menuItem.getLocale());
				fc.setDialogTitle(UIManager.get("filechooser.logpath.title", fc.getLocale()).toString());
				
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fc.showOpenDialog(null);
				File f;
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					f = fc.getSelectedFile();
					f.mkdirs();
					
					PropertyManager.get().setProperty("log.parser.xml.path", f.getAbsolutePath());
					ParserFactory.updateXMLParserPath();
				}
				
			}
		});
		
		return menuItem;
	}
	
	public static JMenu createMenuColorPack() {
		JMenu menu = new JMenu();
		ComponentFactory.get().createLocalisedComponent("mainframe.menu.edit.colorpack", menu);
		fillMenuColorPack(menu);
		return menu;
	}
	
	private static void fillMenuColorPack(final JMenu menu) {
		
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
		JMenu menu = new JMenu();
		ComponentFactory.get().createLocalisedComponent("mainframe.menu.view", menu);
		return menu;
	}
	
	public static JCheckBoxMenuItem createMenuItemCenterMapAutomaticOnActualSector() {
		final JCheckBoxMenuItem menuItem = new JCheckBoxMenuItem();
		ComponentFactory.get().createLocalisedComponent("mainframe.menu.view.ceter_map_on_sector.automatic", menuItem);
		
		menuItem.setSelected(new Boolean(PropertyManager.get().getProperty("universemap.automaticcenter")));
		menuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				PropertyManager.get().setProperty("universemap.automaticcenter", "" + menuItem.isSelected());
			}
		});
		return menuItem;
	}
	
	public static JMenuItem createMenuItemCenterMapOnActualSector(final Mainframe mf) {
		JMenuItem menuItem = new JMenuItem();
		ComponentFactory.get().createLocalisedComponent("mainframe.menu.view.ceter_map_on_sector", menuItem);
		menuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				mf.getJUniverseMapScrollContainer().centerViewOnSector(mf.getJUniverseMap().getActualPlayerInfo().getSectorPosition());
				// sector
			}
		});
		return menuItem;
	}
	
	public static JMenu createMenuHelp() {
		JMenu menu = new JMenu();
		ComponentFactory.get().createLocalisedComponent("mainframe.menu.help", menu);
		return menu;
	}
	
	public static JMenu createMenuLanguage() {
		JMenu menu = new JMenu();
		ComponentFactory.get().createLocalisedComponent("mainframe.menu.help.language", menu);
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
					ComponentUtils.setLocaleRecursively(Mainframe.get(), l);
				}
			});
			
			menuItem.addPropertyChangeListener("locale", new PropertyChangeListener() {
				
				@Override
				public void propertyChange(PropertyChangeEvent evt) {
					Locale newValue = (Locale) evt.getNewValue();
					if (menuItem.getText().equalsIgnoreCase(newValue.getDisplayLanguage(l))) {
						menuItem.setSelected(true);
					} else {
						menuItem.setSelected(false);
					}
					
				}
			});
			
			menu.add(menuItem);
			
		}
	}
	
	public static JMenuItem createMenuItemInstallScripts() {
		final JMenuItem menuItem = new JMenuItem();
		
		ComponentFactory.get().createLocalisedComponent("mainframe.menu.edit.installScripts", menuItem);
		
		menuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser fc = new JFileChooser(PropertyManager.get().getProperty("game.scriptfolder"));
				fc.setLocale(menuItem.getLocale());
				fc.setDialogTitle(UIManager.get("filechooser.scriptpath.title", fc.getLocale()).toString());
				
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnVal = fc.showOpenDialog(null);
				File f;
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					f = fc.getSelectedFile();
					f.mkdirs();
					ScriptManager.get().installScriptsTo(f);
					JOptionPane.showMessageDialog(menuItem, UIManager.get("filechooser.scriptpath.success", fc.getLocale()).toString());
					PropertyManager.get().setProperty("game.scriptfolder", f.getAbsolutePath());
				}
				
			}
		});
		
		return menuItem;
	}
}
