
package de.ncm.jgoodies.common.map;


import java.util.Map;

public interface ObservableMap<K, V> extends Map<K, V> {
	
	public void addMapChangeListener(MapChangeListener l);
	
	public void removeMapChangeListener(MapChangeListener l);
	
}
