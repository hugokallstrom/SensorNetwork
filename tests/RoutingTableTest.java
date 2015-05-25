import static org.junit.Assert.*;

import java.util.HashMap;
import org.junit.Test;
import org.junit.Before;

/**
 * Unit tests for RoutingTables methods.
 * @author Viktor Lindblad
 */

public class RoutingTableTest {
	private RoutingTable rout;
	private Event event;
	private Position p;
	
	
	/**
	 * Runs before every tests.
	 */
	
	@Before
	public void startingTest(){
		p = new Position(1,1);
		event = new Event(1,2,p);
		event.setDistance(2);
		rout = new RoutingTable();
		rout.addEvent(event);
		
	}
	
	/**
	 * Tests the RoutingTables construction. 
	 */
	@Test
	public void testConstruction(){
		assertNotNull(rout);
	}
	
	/**
	 * Test the method, getting an event.
	 * The event is added in @Before. getEvent(1), there 1
	 * stands for events id. 
	 */
	@Test
	public void testGetEvent(){
		
		assertEquals(rout.getEvent(1),event);
	}
	
	/**
	 * Testing adding a new event with id 2.
	 * 
	 */
	@Test
	public void testAddEvent(){
		Position pos = new Position(2,2);
		Event e = new Event(2,3,pos);
		rout.addEvent(e);
		
		assertEquals(rout.getEvent(2),e);
	}
	
	/**
	 * Testing getting a hashtable from routingtable.
	 */
	@Test
	public void testGetEventTable(){
        HashMap<Integer, Event> rt = new HashMap<Integer, Event>();
		
		rt = rout.getEventTable();
		
		assertEquals(rt,rout.getEventTable());
	}
	
	/**
	 * Test set a new hash map to routing tables hash map.
	 * Uses getEventTable to confirm.
	 */
	@Test
	public void testSetEventTable(){
        HashMap<Integer, Event> rt = new HashMap<Integer, Event>();
		Position pos = new Position(2,2);
		Event e = new Event(2,3,pos);
		rt.put(e.getEventId(), e);
		
		rout.setEventTable(rt);
		
		assertEquals(rt , rout.getEventTable());
	}
	
	/**
	 * Test to see if a event in a routing table 
	 * with a known shorter way gives the information
	 * to another routing table.
	 */
	@Test
	public void testShortestPath(){
		
		RoutingTable rt = new RoutingTable();
		Position pos = new Position(3,3);
		Event e = new Event(1,100,pos);
		Node node = new Node(pos);
		Position po = new Position(3,5);
		Event testEvent = new Event(5,4,po);
		
		rout.addEvent(testEvent);
		e.setDistance(5);
		rt.addEvent(e);
		
		rout.findShortestPath(rt, node);
				
		assertEquals(rt.getEvent(1).getDistance(),2);
	}

	/**
	 * Testing to increment distance of events in routing table.
	 */
	@Test
	public void testIncrementDistance(){
		rout.incrementEventDistances();
		
		assertFalse(rout.getEvent(1).getDistance()==2);
		assertTrue(rout.getEvent(1).getDistance()==3);
	}
	
	/**
	 * Testing, sync two different hash map. To change information 
	 * with each other with one empty hash map.
	 */
	@Test
	public void testsyncEvents(){
		
		RoutingTable testrout = new RoutingTable();
		Node node = new Node(p);
		
		assertTrue(testrout.getEventTable().isEmpty());
		
		rout.syncEvents(testrout, node);
		
		assertFalse(testrout.getEventTable().isEmpty());
	}
	
	/**
	 * Testing, sync two different hash map. To change information
	 * with each other with more than one event in both hash map.
	 */
	@Test
	public void testSyncEventsMooreEvents() {
		RoutingTable testrout = new RoutingTable();
		
		Position pos = new Position(3,3);
		Node node = new Node(pos);
		Event e = new Event(15,100,pos);
		Position p = new Position(5,5);
		Event ev = new Event(22,100,p);
		
		testrout.addEvent(ev);
		testrout.addEvent(e);
		
		rout.syncEvents(testrout, node);
		
		assertEquals(rout.getEventTable().size(),3);
	}

}
