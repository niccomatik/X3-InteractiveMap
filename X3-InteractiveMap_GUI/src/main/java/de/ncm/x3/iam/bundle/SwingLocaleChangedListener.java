
package de.ncm.x3.iam.bundle;


import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.AbstractButton;

import org.apache.log4j.Logger;

public class SwingLocaleChangedListener implements LocaleChangedListener {
	
	private static Logger	          LOGGER	= Logger.getLogger(SwingLocaleChangedListener.class);
	private ArrayList<AbstractButton>	abstractButtons;
	
	@Override
	public void localeChanged(ResourceBundle rb) {
		LOGGER.info("Locale changed to '" + rb.getLocale() + "'");
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
