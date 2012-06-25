package de.ncm.x3.iam.gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import de.ncm.x3.iam.bundle.GuiBundleManager;

public class Mainframe extends JFrame {
	private static GuiBundleManager bm = GuiBundleManager.get();
	private JPanel contentPane;
	
	/**
	 * Create the frame.
	 */
	
	public Mainframe() {
		super();
		setTitle("X - InteractiveMap"); //$NON-NLS-1$
		setSize(1024, 768);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setupMenu();
		
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
	}
	
	private void setupMenu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuData = new JMenu(bm.getString("mainframe.menu.data")); //$NON-NLS-1$
		menuBar.add(menuData);
		
		JCheckBoxMenuItem chckbxmntmParsing = new JCheckBoxMenuItem(
				bm.getString("mainframe.menu.data.parsing")); //$NON-NLS-1$
		chckbxmntmParsing.setSelected(true);
		menuData.add(chckbxmntmParsing);
		
		menuData.addSeparator();
		
		JMenuItem mntmSchlieen = new JMenuItem(
				bm.getString("mainframe.menu.data.close")); //$NON-NLS-1$
		mntmSchlieen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				quit();
			}
		});
		menuData.add(mntmSchlieen);
		
		JMenu menuEdit = new JMenu(bm.getString("mainframe.menu.edit")); //$NON-NLS-1$
		menuBar.add(menuEdit);
		
		JMenuItem mntmLogpfadWhlen = new JMenuItem(
				bm.getString("mainframe.menu.edit.choose_logpath")); //$NON-NLS-1$
		menuEdit.add(mntmLogpfadWhlen);
		
		JMenu menuView = new JMenu(bm.getString("mainframe.menu.view")); //$NON-NLS-1$
		menuBar.add(menuView);
		
		JCheckBoxMenuItem checkBoxMenuItemCenterViewOnActualSector = new JCheckBoxMenuItem(
				bm.getString("mainframe.menu.view.automatic_center_on_sector")); //$NON-NLS-1$
		checkBoxMenuItemCenterViewOnActualSector.setSelected(true);
		menuView.add(checkBoxMenuItemCenterViewOnActualSector);
		
		JMenuItem menuItemChenterMap = new JMenuItem(
				bm.getString("mainframe.menu.view.ceter_map_on_sector")); //$NON-NLS-1$
		
		menuView.add(menuItemChenterMap);
		
		JMenu menuHelp = new JMenu(bm.getString("mainframe.menu.help")); //$NON-NLS-1$
		menuBar.add(menuHelp);
		
	}
	
	protected void quit() {
		System.exit(0);
	}
	
}
