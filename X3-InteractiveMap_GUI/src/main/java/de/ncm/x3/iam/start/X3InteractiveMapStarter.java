
package de.ncm.x3.iam.start;


import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.log4j.PropertyConfigurator;

import de.ncm.x3.iam.gui.Mainframe;

public class X3InteractiveMapStarter {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
		
		ClassLoader classLoader = X3InteractiveMapStarter.class.getClassLoader();
		PropertyConfigurator.configure(classLoader.getResource("log4j.properties"));
		
		try {
			EventQueue.invokeAndWait(new Runnable() {
				
				@Override
				public void run() {
					try {
						Mainframe frame = new Mainframe();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			System.exit(-1);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(-1);
		}
		
	}
}
