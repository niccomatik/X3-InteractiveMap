
package de.ncm.x3.iam;


import de.ncm.x3.iam.presentationmodel.MainWindowPModel;

public final class Application {
	
	private static Application instance;
	private MainWindowPModel mainWindow;
	
	private Application() {
		// TODO Auto-generated constructor stub
	}
	
	public void setMainWindow(MainWindowPModel mainWindow) {
		this.mainWindow = mainWindow;
	}
	
	public MainWindowPModel getMainWindow() {
		return this.mainWindow;
	}
	
	public static Application get() {
		if (instance == null) {
			instance = new Application();
		}
		return instance;
	}
	
}
