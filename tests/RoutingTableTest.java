import static org.junit.Assert.*;

import java.util.Hashtable;

import org.junit.Test;
import org.junit.Before;

/**
 * @author Viktor Lindblad
 * Unit tests for RoutingTables methods.
 *
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
		Hashtable<Integer, Event> rt = new Hashtable<Integer, Event>();
		
		rt = rout.getEventTable();
		
		assertEquals(rt,rout.getEventTable());
	}
	
	/**
	 * Test set a new hashtable to routingtables hashtable.
	 * Uses getEventTable to confirm.
	 */
	@Test
	public void testSetEventTable(){
		Hashtable<Integer, Event> rt = new Hashtable<Integer, Event>();
		Position pos = new Position(2,2);
		Event e = new Event(2,3,pos);
		rt.put(e.getEventId(), e);
		
		rout.setEventTable(rt);
		
		assertEquals(rt , rout.getEventTable());
	}
	
	/**
	 * Test to see if a event in a routingtable 
	 * with a known shorter way gives the information
	 * to another routingtable.
	 */
	@Test
	public void testShortestPath(){
		
		RoutingTable rt = new RoutingTable();
		Position pos = new Position(3,3);
		Event e = new Event(1,100,pos);
		
		Position po = new Position(3,5);
		Event testEvent = new Event(5,4,po);
		
		rout.addEvent(testEvent);
		e.setDistance(5);
		rt.addEvent(e);
		
		rout.findShortestPath(rt);
		
		System.out.println(rt.getEvent(1).getDistance());
		
		assertEquals(rt.getEvent(1).getDistance(),2);
	}

	/**
	 * Test to see if an event changes it previous position in routingtable.
	 */
	@Test
	public void testChangeEventPosition(){
		Position p = new Position(1,2);
		Node previus = new Node(p);
		
		rout.changeEventPosition(previus, rout);
		
		assertEquals(rout.getEvent(1).getPosition(),p);
	}
	
	/**
	 * Testing to increment distance of events in routingtable.
	 */
	@Test
	public void testIncrementDistance(){
		rout.incrementEventDistances();
		
		assertFalse(rout.getEvent(1).getDistance()==2);
		assertTrue(rout.getEvent(1).getDistance()==3);
	}
	
	/**
	 * Testing, sync two different hashtables. To change information 
	 * with each other with one empty hashtable.
	 */
	@Test
	public void testsyncEvents(){
		
		RoutingTable testrout = new RoutingTable();
		
		assertTrue(testrout.getEventTable().isEmpty());
		
		rout.syncEvents(testrout);
		
		assertFalse(testrout.getEventTable().isEmpty());
	}
	
	/**
	 * Testing, sync two different hashtables. To change information
	 * with each other with more than one event in both hashtable
	 */
	@Test
	public void testSyncEventsMooreEvents() {
		RoutingTable testrout = new RoutingTable();
		
		Position pos = new Position(3,3);
		Event e = new Event(15,100,pos);
		Position p = new Position(5,5);
		Event ev = new Event(22,100,p);
		
		testrout.addEvent(ev);
		testrout.addEvent(e);
		
		rout.syncEvents(testrout);
		
		assertEquals(rout.getEventTable().size(),3);
	}
	
	/**
	 * Testing copying one hashtable with one event to see that it dont 
	 * changes. 
	 */
	@Test
	public void testDeepCopyHashtable(){
		rout.deepCopyHashtable();
		Event ev = rout.getEvent(1);
		
		assertEquals(ev.getEventId(),event.getEventId());
		assertEquals(ev.getDistance(),event.getDistance());
		
		
	}
	@Test
	public void testHandleMessage(){
		Position pos = new Position(3,3);
		Event e = new Event(1,100,pos);
		e.setDistance(10);
		Position p = new Position(5,5);
		
		Node node = new Node(p);
		node.getRoutingTable().addEvent(e);
		
		rout.syncEvents(node.getRoutingTable());
		
		rout.findShortestPath(node.getRoutingTable());
		
		rout.deepCopyHashtable();
		Event even = node.getRoutingTable().getEvent(1);
		
		assertEquals(event.getEventId(),even.getEventId());
		assertEquals(event.getDistance(),even.getDistance());
		
	}
}
