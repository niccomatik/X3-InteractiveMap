
package de.ncm.x3.iam.bundle;


import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.AbstractButton;

public class SwingLocaleChangedListener implements LocaleChangedListener {
	
	private ArrayList<AbstractButton>	abstractButtons;
	
	@Override
	public void localeChanged(ResourceBundle rb) {
		for (AbstractButton b : abstractButtons) {
			b.setText(rb.getString(b.getName()));
		}
		
	}
	
	public boolean add(AbstractButton b) {
		initAbstractButtons();
		return abstractButtons.add(b);
	}
	
	private void initAbstractButtons() {
		if (abstractButtons == null) {
			this.abstractButtons = new ArrayList<AbstractButton>();
		}
	}
	
}
