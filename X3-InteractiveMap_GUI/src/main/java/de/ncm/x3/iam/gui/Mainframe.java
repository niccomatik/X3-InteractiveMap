
package de.ncm.x3.iam.gui;


import java.awt.BorderLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.UIManager;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;

import de.ncm.x3.iam.bundle.GuiBundleManager;
import de.ncm.x3.iam.gui.component.ComponentFactory;
import de.ncm.x3.iam.gui.component.universe.JUniverseMap;
import de.ncm.x3.iam.gui.component.universe.JUniverseMapScrollContainer;
import de.ncm.x3.iam.settings.PropertyManager;

public class Mainframe extends JFrame {
	
	private static Mainframe instance;
	private Logger logger = Logger.getLogger(Mainframe.class);
	private JPanel contentPane;
	private JRadioButtonMenuItem actualrdbtnmntmLanguage;
	protected ComponentFactory componentFactory = ComponentFactory.get();
	private JUniverseMapScrollContainer jUniverseMapScrollContainer;
	private JMenu menuData;
	private JCheckBoxMenuItem menuItemParsing;
	private JMenuBar menuBar;
	private JMenuItem menuItemClose;
	private JMenu menuEdit;
	private JMenuItem menuItemChooseLogpath;
	private JMenu menuColorPack;
	private JMenu menuView;
	private JCheckBoxMenuItem menuItemCenterMapAutomaticOnActualSector;
	private JMenuItem menuItemCenterMapOnActualSector;
	private JMenu menuHelp;
	private JMenu menuLanguage;
	private JMenuItem menuItemInstallScripts;
	private JUniverseMap jUniverseMap;
	
	/**
	 * Create the frame
	 */
	
	public Mainframe() {
		super();
		instance = this;
		logger.info("Creating the GUI");
		UIManager.getDefaults().addResourceBundle(GuiBundleManager.get().getBundle());
		
		setLocationByPlatform(true);
		setTitle("X - InteractiveMap");
		setSize(1024, 768);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WListener());
		setupMenu();
		
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		jUniverseMap = new JUniverseMap();
		jUniverseMapScrollContainer = new JUniverseMapScrollContainer(jUniverseMap);
		contentPane.add(jUniverseMapScrollContainer, BorderLayout.CENTER);
		
		logger.info("GUI created");
		
	}
	
	private void setupMenu() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		menuData = MenuFactory.createMenuData();
		menuItemParsing = MenuFactory.createMenuItemParsing();
		menuData.add(menuItemParsing);
		menuItemClose = MenuFactory.createMenuItemClose();
		menuEdit = MenuFactory.createMenuEdit();
		menuItemChooseLogpath = MenuFactory.createMenuItemChooseLogpath();
		menuColorPack = MenuFactory.createMenuColorPack();
		menuView = MenuFactory.createMenuView();
		menuItemCenterMapAutomaticOnActualSector = MenuFactory.createMenuItemCenterMapAutomaticOnActualSector();
		menuItemCenterMapOnActualSector = MenuFactory.createMenuItemCenterMapOnActualSector(this);
		menuHelp = MenuFactory.createMenuHelp();
		menuLanguage = MenuFactory.createMenuLanguage();
		menuItemInstallScripts = MenuFactory.createMenuItemInstallScripts();
		
		menuBar.add(menuData);
		menuData.addSeparator();
		menuData.add(menuItemClose);
		
		menuBar.add(menuEdit);
		menuEdit.add(menuItemChooseLogpath);
		menuEdit.add(menuItemInstallScripts);
		
		menuBar.add(menuView);
		menuView.add(menuItemCenterMapAutomaticOnActualSector);
		menuView.add(menuItemCenterMapOnActualSector);
		menuView.addSeparator();
		menuView.add(menuColorPack);
		
		menuBar.add(menuHelp);
		menuHelp.add(menuLanguage);
		
	}
	
	public static void quit() {
		PropertyManager.get().save();
		System.exit(0);
	}
	
	public static Mainframe get() {
		return instance;
	}
	
	private class WListener implements WindowListener {
		
		@Override
		public void windowOpened(WindowEvent e) {}
		
		@Override
		public void windowClosing(WindowEvent e) {
			quit();
			
		}
		
		@Override
		public void windowClosed(WindowEvent e) {}
		
		@Override
		public void windowIconified(WindowEvent e) {}
		
		@Override
		public void windowDeiconified(WindowEvent e) {}
		
		@Override
		public void windowActivated(WindowEvent e) {}
		
		@Override
		public void windowDeactivated(WindowEvent e) {}
		
	}
	
	public JUniverseMap getJUniverseMap() {
		return jUniverseMap;
	}
	
	public JUniverseMapScrollContainer getJUniverseMapScrollContainer() {
		return jUniverseMapScrollContainer;
	}
	
}
