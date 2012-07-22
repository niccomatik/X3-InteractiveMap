
package de.ncm.x3.iam.gui.component.universe;


import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import de.ncm.x3.iam.data.universe.GridPos;
import de.ncm.x3.iam.data.universe.Sector;
import de.ncm.x3.iam.data.universe.UniverseMap;

public class TestJUniverseMap {
	
	private JUniverseMap jUniverseMap;
	private GridPos s1;
	private GridPos s2;
	private GridPos s3;
	private GridPos s4;
	private GridPos s5;
	
	@Before
	public void setupBefore() {
		jUniverseMap = new JUniverseMap();
		
		s1 = new GridPos(1, 1);
		s2 = new GridPos(2, 1);
		s3 = new GridPos(3, 1);
		s4 = new GridPos(1, 2);
		s5 = new GridPos(3, 2);
		
		HashMap<GridPos, Sector> sectors = new HashMap<GridPos, Sector>();
		sectors.put(s1, new Sector());
		sectors.put(s2, new Sector());
		sectors.put(s3, new Sector());
		sectors.put(s4, new Sector());
		sectors.put(s5, new Sector());
		UniverseMap umap = new UniverseMap();
		
		jUniverseMap.setUniverseMap(umap);
		
	}
	
	@Test
	public void testIsDirectConnection() {
		
		assertTrue(jUniverseMap.isDirectConnection(s1, s2));
		assertTrue(jUniverseMap.isDirectConnection(s2, s3));
		assertTrue(!jUniverseMap.isDirectConnection(s1, s3));
		assertTrue(jUniverseMap.isDirectConnection(s1, s4));
		assertTrue(!jUniverseMap.isDirectConnection(s2, s5));
		assertTrue(!jUniverseMap.isDirectConnection(s1, s5));
		assertTrue(jUniverseMap.isDirectConnection(s3, s5));
		
		// Reverse Direction
		assertTrue(jUniverseMap.isDirectConnection(s2, s1));
		assertTrue(jUniverseMap.isDirectConnection(s3, s2));
		assertTrue(!jUniverseMap.isDirectConnection(s3, s1));
		assertTrue(jUniverseMap.isDirectConnection(s4, s1));
		assertTrue(!jUniverseMap.isDirectConnection(s5, s2));
		assertTrue(!jUniverseMap.isDirectConnection(s5, s1));
		assertTrue(jUniverseMap.isDirectConnection(s5, s3));
		
	}
}
