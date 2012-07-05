
package de.ncm.x3.iam.gui.component;


import java.awt.Component;
import java.awt.ComponentOrientation;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;

import javax.swing.AbstractButton;
import javax.swing.JLabel;

import org.apache.log4j.Logger;

public class ComponentFactory {
	
	private static final Logger	                          logger	                = Logger.getLogger(ComponentFactory.class);
	
	private static ComponentFactory	                      instance;
	
	protected HashMap<Class<? extends Component>, String>	localisationTextMethods	= new HashMap<Class<? extends Component>, String>();
	
	private ComponentFactory() {
		putLocalisationTextMethod(AbstractButton.class, "setText");
		putLocalisationTextMethod(JLabel.class, "setText");
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public <E extends Component> E createLocalisedComponent(String key, Class<E> componetClass) {
		
		String methodString = localisationTextMethods.get(componetClass);
		if (methodString == null) {
			for (Class clazz : localisationTextMethods.keySet()) {
				if (clazz.isAssignableFrom(componetClass)) {
					methodString = localisationTextMethods.get(clazz);
					break;
				}
			}
			if (methodString == null) {
				throw new IllegalStateException("No localisation method found for " + componetClass);
			}
		}
		
		Method localTextMethod = null;
		for (Method m : componetClass.getMethods()) {
			if (m.getName().equals(methodString)) {
				localTextMethod = m;
			}
		}
		if (localTextMethod == null) {
			logger.error("The method '" + methodString + "' does not exist for " + componetClass);
			return null;
		}
		
		try {
			final E comp = componetClass.newInstance();
			comp.addPropertyChangeListener("locale", new LocaleChangedListener(comp, localTextMethod, key));
			
			return comp;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public HashMap<Class<? extends Component>, String> getLocalisationTextMethods() {
		return localisationTextMethods;
	}
	
	public void putLocalisationTextMethod(Class<? extends Component> clazz, String methodName) {
		this.localisationTextMethods.put(clazz, methodName);
	}
	
	public static ComponentFactory get() {
		if (instance == null) {
			instance = new ComponentFactory();
		}
		return instance;
	}
	
	private class LocaleChangedListener implements PropertyChangeListener {
		
		private Component	comp;
		private Method		method;
		private String		key;
		
		public LocaleChangedListener(Component comp, Method method, String key) {
			this.comp = comp;
			this.method = method;
			this.key = key;
		}
		
		@Override
		public void propertyChange(PropertyChangeEvent evt) {
			Locale locale = (Locale) evt.getNewValue();
			comp.setComponentOrientation(ComponentOrientation.getOrientation(locale));
			try {
				method.invoke(comp, key);
			} catch (IllegalAccessException e) {
				logger.fatal("Cannot change LocaleText due to ", e);
			} catch (IllegalArgumentException e) {
				logger.fatal("Cannot change LocaleText due to ", e);
			} catch (InvocationTargetException e) {
				logger.fatal("Cannot change LocaleText due to ", e);
			}
		}
	}
}
