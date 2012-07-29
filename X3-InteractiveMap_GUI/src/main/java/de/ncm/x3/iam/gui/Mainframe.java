
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
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;

import de.ncm.x3.iam.gui.component.ComponentFactory;
import de.ncm.x3.iam.gui.component.JMenuSeperator;
import de.ncm.x3.iam.gui.component.universe.JUniverseMap;
import de.ncm.x3.iam.gui.component.universe.JUniverseMapScrollContainer;
import de.ncm.x3.iam.settings.PropertyManager;

public class Mainframe extends JFrame {
	
	private static Mainframe instance;
	private Logger logger = Logger.getLogger(Mainframe.class);
	private JPanel contentPane;
	private JUniverseMapScrollContainer jUniverseMapScrollContainer;
	
	private JMenuBar menuBar;
	private JUniverseMap jUniverseMap;
	private JMenu mnData;
	private JMenu mnEdit;
	private JMenu mnView;
	private JMenu mnHelp;
	private JCheckBoxMenuItem chckbxmntmParsingFiles;
	private JMenuItem mntmQuit;
	private JMenuSeperator menuSeperator;
	private JMenuItem mntmInstallScripts;
	private JMenuItem mntmSettings;
	private JCheckBoxMenuItem mntmCenterMapAutomatically;
	private JMenuItem mntmCenterMapOn;
	private JMenuItem mntmAbout;
	private JScrollPane scrollPane;
	private JTree tree;
	private JSplitPane splitPane;
	
	/**
	 * Create the frame
	 */
	
	public Mainframe() {
		super();
		instance = this;
		logger.info("Creating the GUI");
		
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
		
		tree = new JTree();
		tree.setModel(ComponentFactory.createDummyTree());
		scrollPane = new JScrollPane(tree);
		
		splitPane = ComponentFactory.createHorizontalJSplitPane(scrollPane, jUniverseMapScrollContainer, (int) (getWidth() * 0.2));
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		logger.info("GUI created");
		
		Messages.listBundle();
	}
	
	private void setupMenu() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		mnData = MenuFactory.createJMenu("Mainframe.mnData.text"); //$NON-NLS-1$
		MenuFactory.setupMenuData(mnData);
		menuBar.add(mnData);
		
		chckbxmntmParsingFiles = MenuFactory.createJCheckBoxMenuItem("Mainframe.chckbxmntmParsingFiles.text"); //$NON-NLS-1$
		MenuFactory.setupMenuItemParsingFiles(chckbxmntmParsingFiles);
		mnData.add(chckbxmntmParsingFiles);
		
		menuSeperator = MenuFactory.createJMenuSeperator();
		mnData.add(menuSeperator);
		
		mntmQuit = MenuFactory.createJMenuItem("Mainframe.mntmQuit.text");
		MenuFactory.setupMenuItemExit(mntmQuit);
		mnData.add(mntmQuit);
		
		mnEdit = MenuFactory.createJMenu("Mainframe.mnEdit.text"); //$NON-NLS-1$
		MenuFactory.setupMenuEdit(mnEdit);
		menuBar.add(mnEdit);
		
		mntmInstallScripts = MenuFactory.createJMenuItem("Mainframe.mntmInstallScripts.text"); //$NON-NLS-1$
		MenuFactory.setupMenuItemInstallScripts(mntmInstallScripts, this);
		mnEdit.add(mntmInstallScripts);
		
		mntmSettings = MenuFactory.createJMenuItem("Mainframe.mntmSettings.text"); //$NON-NLS-1$
		MenuFactory.setupMenuItemSettings(mntmSettings, this);
		mnEdit.add(mntmSettings);
		
		mnView = MenuFactory.createJMenu("Mainframe.mnView.text"); //$NON-NLS-1$
		MenuFactory.setupMenuView(mnView);
		menuBar.add(mnView);
		
		mntmCenterMapAutomatically = MenuFactory.createJCheckBoxMenuItem("Mainframe.mntmCenterMapAutomatically.text"); //$NON-NLS-1$
		MenuFactory.setupMenuItemCenterMapAutomatically(mntmCenterMapAutomatically);
		mnView.add(mntmCenterMapAutomatically);
		
		mntmCenterMapOn = MenuFactory.createJMenuItem("Mainframe.mntmCenterMapOn.text"); //$NON-NLS-1$
		MenuFactory.setupMenuItemCenterMap(mntmCenterMapOn, this);
		mnView.add(mntmCenterMapOn);
		
		mnHelp = MenuFactory.createJMenu("Mainframe.mnHelp.text"); //$NON-NLS-1
		MenuFactory.setupMenuHelp(mnHelp);
		menuBar.add(mnHelp);
		
		mntmAbout = MenuFactory.createJMenuItem("Mainframe.mntmAbout.text"); //$NON-NLS-1$
		MenuFactory.setupMenuItemAbout(mntmAbout);
		mnHelp.add(mntmAbout);
		
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
