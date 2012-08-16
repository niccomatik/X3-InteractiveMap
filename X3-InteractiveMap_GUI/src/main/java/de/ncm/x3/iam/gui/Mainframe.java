
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
import javax.swing.WindowConstants;

import org.apache.log4j.Logger;

import de.ncm.x3.iam.gui.component.JMenuSeperator;
import de.ncm.x3.iam.gui.component.JUniverseTree;
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
	private JUniverseTree tree;
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
		
		tree = new JUniverseTree();
		scrollPane = new JScrollPane(tree);
		
		jUniverseMap = new JUniverseMap();
		jUniverseMapScrollContainer = new JUniverseMapScrollContainer(jUniverseMap, tree);
		
		splitPane = ComponentFactory.createHorizontalJSplitPane(scrollPane, jUniverseMapScrollContainer, (int) (getWidth() * 0.2));
		contentPane.add(splitPane, BorderLayout.CENTER);
		
		logger.info("GUI created");
		
		// Messages.listBundle();
	}
	
	private void setupMenu() {
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		mnData = MenuFactory.createMenuData(); //$NON-NLS-1$
		chckbxmntmParsingFiles = MenuFactory.createMenuItemParsingFiles(); //$NON-NLS-1$
		menuSeperator = MenuFactory.createJMenuSeperator();//$NON-NLS-1$
		mntmQuit = MenuFactory.createMenuItemQuit();//$NON-NLS-1$
		mnEdit = MenuFactory.createMenuEdit(); //$NON-NLS-1$
		mntmInstallScripts = MenuFactory.createMenuItemInstallScripts(this); //$NON-NLS-1$
		mntmSettings = MenuFactory.createMenuItemSettings(this); //$NON-NLS-1$
		mnView = MenuFactory.createMenuView(); //$NON-NLS-1$
		mntmCenterMapAutomatically = MenuFactory.createMenuItemCenterMapAutomatically(); //$NON-NLS-1$
		mntmCenterMapOn = MenuFactory.createMenuItemCenterMap(this); //$NON-NLS-1$
		mnHelp = MenuFactory.createMenuHelp(); //$NON-NLS-1
		mntmAbout = MenuFactory.createMenuItemAbout(); //$NON-NLS-1$
		
		menuBar.add(mnData);
		{
			mnData.add(chckbxmntmParsingFiles);
			
			mnData.add(menuSeperator);
			
			mnData.add(mntmQuit);
		}
		menuBar.add(mnEdit);
		{
			mnEdit.add(mntmInstallScripts);
			
			mnEdit.add(mntmSettings);
		}
		menuBar.add(mnView);
		{
			mnView.add(mntmCenterMapAutomatically);
			
			mnView.add(mntmCenterMapOn);
		}
		menuBar.add(mnHelp);
		{
			mnHelp.add(mntmAbout);
		}
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
