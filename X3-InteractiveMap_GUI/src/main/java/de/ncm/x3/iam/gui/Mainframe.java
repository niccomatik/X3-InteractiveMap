
package de.ncm.x3.iam.gui;


import java.awt.BorderLayout;

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
import de.ncm.x3.iam.parser.ParserManager;

public class Mainframe extends JFrame {
	
	private static Mainframe instance;
	private Logger logger = Logger.getLogger(Mainframe.class);
	private JPanel contentPane;
	private JRadioButtonMenuItem actualrdbtnmntmLanguage;
	private ParserManager parseManager;
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
	private JCheckBoxMenuItem menuItemCenterViewAutomaticOnActualSector;
	private JCheckBoxMenuItem menuItemCenterMapAutomaticOnActualSector;
	private JMenuItem menuItemCenterMapOnActualSector;
	private JMenu menuHelp;
	private JMenu menuLanguage;
	
	/**
	 * Create the frame
	 */
	
	public Mainframe() {
		super();
		this.parseManager = new ParserManager();
		instance = this;
		logger.info("Creating the GUI");
		UIManager.getDefaults().addResourceBundle(GuiBundleManager.get().getBundle());
		
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
		
		logger.info("GUI created");
		
	}
	
	private void setupMenu() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		menuData = MenuFactory.createMenuData();
		menuItemParsing = MenuFactory.createMenuItemParsing(parseManager);
		menuData.add(menuItemParsing);
		menuItemClose = MenuFactory.createMenuItemClose();
		menuEdit = MenuFactory.createMenuEdit();
		menuItemChooseLogpath = MenuFactory.createMenuItemChooseLogpath();
		menuColorPack = MenuFactory.createMenuColorPack();
		menuView = MenuFactory.createMenuView();
		menuItemCenterMapAutomaticOnActualSector = MenuFactory.createMenuItemCenterMapAutomaticOnActualSector();
		menuItemCenterMapOnActualSector = MenuFactory.createMenuItemCenterMapOnActualSector(jUniverseMapScrollContainer);
		menuHelp = MenuFactory.createMenuHelp();
		menuLanguage = MenuFactory.createMenuLanguage();
		
		menuBar.add(menuData);
		menuData.addSeparator();
		menuData.add(menuItemClose);
		
		menuBar.add(menuEdit);
		menuEdit.add(menuItemChooseLogpath);
		menuEdit.add(menuColorPack);
		
		menuBar.add(menuView);
		menuView.add(menuItemCenterMapAutomaticOnActualSector);
		menuView.add(menuItemCenterMapOnActualSector);
		
		menuBar.add(menuHelp);
		menuHelp.add(menuLanguage);
		
	}
	
	public static void quit() {
		System.exit(0);
	}
	
	public static Mainframe get() {
		return instance;
	}
	
}
