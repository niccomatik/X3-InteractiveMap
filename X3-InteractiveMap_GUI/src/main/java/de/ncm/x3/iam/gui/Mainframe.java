
package de.ncm.x3.iam.gui;


import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Locale;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.UIManager;

import org.apache.log4j.Logger;

import de.ncm.x3.iam.bundle.GuiBundleManager;
import de.ncm.x3.iam.gui.component.ComponentFactory;
import de.ncm.x3.iam.gui.component.universe.JUniverseMap;
import de.ncm.x3.iam.gui.component.universe.JUniverseMapScrollContainer;
import de.ncm.x3.iam.gui.util.ComponentUtils;
import de.ncm.x3.iam.parser.ParserManager;
import de.ncm.x3.iam.settings.PropertyManager;

public class Mainframe extends JFrame {
	
	private Logger logger = Logger.getLogger(Mainframe.class);
	private JPanel contentPane;
	private JRadioButtonMenuItem actualrdbtnmntmLanguage;
	private ParserManager parseManager;
	protected ComponentFactory componentFactory = ComponentFactory.get();
	private JUniverseMapScrollContainer jUniverseMapScrollContainer;
	
	/**
	 * Create the frame
	 */
	
	public Mainframe() {
		super();
		UIManager.getDefaults().addResourceBundle("language.lang");
		logger.info("Creating the GUI");
		
		setLocationByPlatform(true);
		setTitle("X - InteractiveMap");
		setSize(1024, 768);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setupMenu();
		
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JUniverseMap jUniverseMap = new JUniverseMap();
		jUniverseMapScrollContainer = new JUniverseMapScrollContainer(jUniverseMap);
		contentPane.add(jUniverseMapScrollContainer, BorderLayout.CENTER);
		
		this.parseManager = new ParserManager();
		logger.info("GUI created");
		
	}
	
	private void setupMenu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuData = componentFactory.createLocalisedComponent("mainframe.menu.data", JMenu.class);
		menuBar.add(menuData);
		
		final JCheckBoxMenuItem chckbxmntmParsing = componentFactory.createLocalisedComponent("mainframe.menu.data.parsing", JCheckBoxMenuItem.class);
		
		chckbxmntmParsing.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (chckbxmntmParsing.isSelected()) {
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
				chckbxmntmParsing.setSelected(enabled);
			}
		});
		
		menuData.add(chckbxmntmParsing);
		
		menuData.addSeparator();
		
		JMenuItem mntmClose = componentFactory.createLocalisedComponent("mainframe.menu.data.close", JMenuItem.class);
		mntmClose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				quit();
			}
		});
		menuData.add(mntmClose);
		
		JMenu menuEdit = componentFactory.createLocalisedComponent("mainframe.menu.edit", JMenu.class);
		menuBar.add(menuEdit);
		
		JMenuItem mntmChooseLogpath = componentFactory.createLocalisedComponent("mainframe.menu.edit.choose_logpath", JMenuItem.class);
		mntmChooseLogpath.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Choose and save Logpath
				
			}
		});
		menuEdit.add(mntmChooseLogpath);
		
		JMenu menuView = componentFactory.createLocalisedComponent("mainframe.menu.view", JMenu.class);
		menuBar.add(menuView);
		
		JCheckBoxMenuItem checkBoxMenuItemCenterViewOnActualSector = componentFactory.createLocalisedComponent(
				"mainframe.menu.view.ceter_map_on_sector.automatic", JCheckBoxMenuItem.class);
		
		checkBoxMenuItemCenterViewOnActualSector.setSelected(true);
		chckbxmntmParsing.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Implement automatic center on sector
				
			}
		});
		menuView.add(checkBoxMenuItemCenterViewOnActualSector);
		
		JMenuItem menuItemCenterMap = componentFactory.createLocalisedComponent("mainframe.menu.view.ceter_map_on_sector", JMenuItem.class);
		menuItemCenterMap.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Implement center on sector
				Rectangle rect = getBounds();
				rect.x = 500;
				rect.y = 500;
				
				jUniverseMapScrollContainer.getViewport().scrollRectToVisible(rect);
				
			}
		});
		menuView.add(menuItemCenterMap);
		
		JMenu menuHelp = componentFactory.createLocalisedComponent("mainframe.menu.help", JMenu.class);
		menuBar.add(menuHelp);
		
		JMenu mnLanguage = componentFactory.createLocalisedComponent("mainframe.menu.help.language", JMenu.class);
		menuHelp.add(mnLanguage);
		
		for (final Locale l : GuiBundleManager.get().getAvailableLocales()) {
			
			final JRadioButtonMenuItem rdbtnmntmLanguage = new JRadioButtonMenuItem(l.getDisplayLanguage(l));
			
			if (l.getDisplayLanguage(l).equalsIgnoreCase(rdbtnmntmLanguage.getLocale().getDisplayLanguage(l))) {
				rdbtnmntmLanguage.setSelected(true);
				actualrdbtnmntmLanguage = rdbtnmntmLanguage;
			} else {
				rdbtnmntmLanguage.setSelected(false);
			}
			rdbtnmntmLanguage.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (actualrdbtnmntmLanguage != null) {
						actualrdbtnmntmLanguage.setSelected(false);
					}
					actualrdbtnmntmLanguage = rdbtnmntmLanguage;
					actualrdbtnmntmLanguage.setSelected(true);
					ComponentUtils.setLocaleRecursively(Mainframe.this, l);
				}
			});
			
			mnLanguage.add(rdbtnmntmLanguage);
			
		}
		
	}
	
	protected void quit() {
		System.exit(0);
	}
	
}
