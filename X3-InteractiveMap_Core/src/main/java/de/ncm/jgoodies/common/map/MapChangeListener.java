
package de.ncm.jgoodies.common.map;


import java.util.EventListener;

public interface MapChangeListener extends EventListener {
	
	public void contentsChanged(MapChangeEvent e);
	
}
