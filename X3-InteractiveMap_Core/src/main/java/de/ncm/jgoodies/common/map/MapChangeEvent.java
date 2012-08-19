
package de.ncm.jgoodies.common.map;


import java.util.EventObject;
import java.util.Map;

public class MapChangeEvent extends EventObject {
	
	private Object key;
	private Object oldValue;
	private Object newValue;
	
	public MapChangeEvent(Map<?, ?> source, Object key, Object oldValue, Object newValue) {
		super(source);
		this.key = key;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}
	
	public Object getKey() {
		return key;
	}
	
	public Object getOldValue() {
		return oldValue;
	}
	
	public Object getNewValue() {
		return newValue;
	}
	
}
