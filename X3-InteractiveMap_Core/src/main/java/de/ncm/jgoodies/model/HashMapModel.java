
package de.ncm.jgoodies.model;


import java.util.HashMap;
import java.util.Map;

import javax.swing.event.EventListenerList;
import javax.swing.event.ListDataListener;

import de.ncm.jgoodies.common.map.MapChangeEvent;
import de.ncm.jgoodies.common.map.MapChangeListener;
import de.ncm.jgoodies.common.map.ObservableMap;

public class HashMapModel<K, V> extends HashMap<K, V> implements ObservableMap<K, V> {
	
	/**
	 * Holds the registered ListDataListeners. The list that holds these listeners is initialized lazily in {@code #getEventListenerList}.
	 * 
	 * @see #addListDataListener(ListDataListener)
	 * @see #removeListDataListener(ListDataListener)
	 */
	private EventListenerList listenerList;
	
	// Constructor*************************************************************************************************
	public HashMapModel() {
		super();
	}
	
	public HashMapModel(int initialCapacity) {
		super(initialCapacity);
	}
	
	public HashMapModel(Map<? extends K, ? extends V> m) {
		super();
		for (K key : m.keySet()) {
			put(key, m.get(key));
		}
	}
	
	public HashMapModel(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}
	
	// Normal Methods***********************************************************************************************
	
	@Override
	public V put(K key, V value) {
		V oldValue = super.put(key, value);
		fireMapPutEvent(key, oldValue, value);
		return oldValue;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public V remove(Object key) {
		V oldValue = super.remove(key);
		fireMapRemoveEvent((K) key, oldValue);
		return oldValue;
	}
	
	@Override
	public void addMapChangeListener(MapChangeListener l) {
		getEventListenerList().add(MapChangeListener.class, l);
	}
	
	@Override
	public void removeMapChangeListener(MapChangeListener l) {
		getEventListenerList().remove(MapChangeListener.class, l);
	};
	
	/**
	 * Lazily initializes and returns the event listener list used to notify registered listeners.
	 * 
	 * @return the event listener list used to notify listeners
	 */
	private EventListenerList getEventListenerList() {
		if (listenerList == null) {
			listenerList = new EventListenerList();
		}
		return listenerList;
	}
	
	private void fireMapPutEvent(K key, V oldValue, V newValue) {
		fireMapChangeEvent(new MapChangeEvent(this, key, oldValue, newValue));
	}
	
	private void fireMapRemoveEvent(K key, V oldValue) {
		fireMapChangeEvent(new MapChangeEvent(this, key, oldValue, null));
	}
	
	public void fireMapChangeEvent(MapChangeEvent e) {
		Object[] listeners = getEventListenerList().getListenerList();
		
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == MapChangeListener.class) {
				((MapChangeListener) listeners[i + 1]).contentsChanged(e);
			}
		}
		
	}
	
}
