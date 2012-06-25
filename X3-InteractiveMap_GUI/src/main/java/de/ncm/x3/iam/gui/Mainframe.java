
package de.ncm.x3.iam.gui;


import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;

import de.ncm.x3.iam.bundle.GuiBundleManager;
import de.ncm.x3.iam.bundle.SwingLocaleChangedListener;

public class Mainframe extends JFrame {
	
	private JPanel	                   contentPane;
	private SwingLocaleChangedListener	localeChangedListener;
	private JRadioButtonMenuItem	   actualrdbtnmntmLanguage;
	
	/**
	 * Create the frame.
	 */
	
	public Mainframe() {
		super();
		this.localeChangedListener = new SwingLocaleChangedListener();
		setTitle("X - InteractiveMap");
		setSize(1024, 768);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setupMenu();
		
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		GuiBundleManager.get().setLocaleChangedListener(localeChangedListener);
	}
	
	private void setupMenu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuData = new JMenu("Data");
		menuData.setName("mainframe.menu.data");
		localeChangedListener.add(menuData);
		menuBar.add(menuData);
		
		JCheckBoxMenuItem chckbxmntmParsing = new JCheckBoxMenuItem("Parsing");
		chckbxmntmParsing.setName("mainframe.menu.data.parsing");
		localeChangedListener.add(chckbxmntmParsing);
		chckbxmntmParsing.setSelected(true);
		menuData.add(chckbxmntmParsing);
		
		menuData.addSeparator();
		
		JMenuItem mntmClose = new JMenuItem("Close");
		mntmClose.setName("mainframe.menu.data.close");
		localeChangedListener.add(mntmClose);
		mntmClose.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				quit();
			}
		});
		menuData.add(mntmClose);
		
		JMenu menuEdit = new JMenu("Edit");
		menuEdit.setName("mainframe.menu.edit");
		localeChangedListener.add(menuEdit);
		menuBar.add(menuEdit);
		
		JMenuItem mntmChooseLogpath = new JMenuItem("Choose Logpath");
		mntmChooseLogpath.setName("mainframe.menu.edit.choose_logpath");
		localeChangedListener.add(mntmChooseLogpath);
		menuEdit.add(mntmChooseLogpath);
		
		JMenu menuView = new JMenu("View");
		menuView.setName("mainframe.menu.view");
		localeChangedListener.add(menuView);
		menuBar.add(menuView);
		
		JCheckBoxMenuItem checkBoxMenuItemCenterViewOnActualSector = new JCheckBoxMenuItem("Center map automatically on current sector");
		checkBoxMenuItemCenterViewOnActualSector.setName("mainframe.menu.view.ceter_map_on_sector.automatic");
		localeChangedListener.add(checkBoxMenuItemCenterViewOnActualSector);
		checkBoxMenuItemCenterViewOnActualSector.setSelected(true);
		menuView.add(checkBoxMenuItemCenterViewOnActualSector);
		
		JMenuItem menuItemCenterMap = new JMenuItem("Center map automatically on current sector");
		menuItemCenterMap.setName("mainframe.menu.view.ceter_map_on_sector");
		localeChangedListener.add(menuItemCenterMap);
		menuView.add(menuItemCenterMap);
		
		JMenu menuHelp = new JMenu("Help");
		menuHelp.setName("mainframe.menu.help");
		localeChangedListener.add(menuHelp);
		menuBar.add(menuHelp);
		
		JMenu mnLanguage = new JMenu("Language");
		mnLanguage.setName("mainframe.menu.help.language");
		localeChangedListener.add(mnLanguage);
		menuHelp.add(mnLanguage);
		
		for (final Locale l : GuiBundleManager.get().getAvailableLocales()) {
			
			final JRadioButtonMenuItem rdbtnmntmLanguage = new JRadioButtonMenuItem(l.getDisplayLanguage());
			System.out.println(l.toLanguageTag() + "\t<======>\t" + Locale.getDefault().toLanguageTag());
			if (l.toLanguageTag().equalsIgnoreCase(Locale.getDefault().toLanguageTag())) {
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
					GuiBundleManager.get().setLocale(l);
					
				}
			});
			
			mnLanguage.add(rdbtnmntmLanguage);
			
		}
	}
	
	protected void quit() {
		System.exit(0);
	}
	
}
