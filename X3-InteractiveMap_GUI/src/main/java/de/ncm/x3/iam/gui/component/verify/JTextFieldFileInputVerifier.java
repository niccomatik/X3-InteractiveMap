
package de.ncm.x3.iam.gui.component.verify;


import java.io.File;
import java.io.IOException;

import javax.swing.InputVerifier;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.Popup;

import de.ncm.x3.iam.gui.component.ComponentFactory;

public class JTextFieldFileInputVerifier extends InputVerifier {
	
	private Popup errorPopup = null;
	
	public JTextFieldFileInputVerifier() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public boolean verify(JComponent input) {
		JTextField fileTextField = (JTextField) input;
		
		File f = new File(fileTextField.getText());
		try {
			f.getCanonicalPath();
		} catch (IOException e) {
			printError(fileTextField, "No valid Path: \"" + fileTextField.getText() + "\"");
			return false;
		}
		if (!f.canRead()) {
			printError(fileTextField, "Cannot read Path: \"" + fileTextField.getText() + "\"");
			return false;
		}
		removeError();
		return true;
		
	}
	
	private void removeError() {
		if (errorPopup != null) {
			errorPopup.hide();
		}
		errorPopup = null;
	}
	
	private void printError(JTextField fileTextField, String message) {
		removeError();
		
		errorPopup = ComponentFactory.createPopupDialog(fileTextField, message);
		errorPopup.show();
	}
	
	public boolean hasError() {
		return errorPopup != null;
	}
}
