
package de.ncm.x3.iam.data.universe;


import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class TestGateConnection {
	
	@Before
	public void setUp() throws Exception {}
	
	@Test
	public void testEqualsObject() {
		WarpGate gate1 = new WarpGate(new GridPos(0, 0));
		WarpGate gate2 = new WarpGate(new GridPos(1, 1));
		WarpGate gate3 = new WarpGate(new GridPos(2, 1));
		WarpGate gate4 = new WarpGate(new GridPos(1, 2));
		WarpGate gate5 = new WarpGate(new GridPos(5, 8));
		assertTrue(new GateConnection(gate1, gate2, false).equals(new GateConnection(gate2, gate1, false)));
		assertTrue(new GateConnection(gate1, gate5, false).equals(new GateConnection(gate5, gate1, false)));
		
	}
	
}
