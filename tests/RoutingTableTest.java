import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Stack;

import org.junit.Test;
import org.junit.Before;

public class RoutingTableTest {
	private RoutingTable rout;
	private Event event;
	private Position p;
	
	
	@Before
	public void startingTest(){
		p = new Position(1,1);
		event = new Event(1,2,p);
		event.setDistance(2);
		rout = new RoutingTable();
		rout.addEvent(event);
		
	}
	
	@Test
	public void testGetEvent(){
		
		assertEquals(rout.getEvent(1),event);
	}
	
	@Test
	public void testAddEvent(){
		Position pos = new Position(2,2);
		Event e = new Event(2,3,pos);
		rout.addEvent(e);
		
		assertEquals(rout.getEvent(2),e);
	}
	
	@Test
	public void testGetEventList(){
		
		assertEquals(rout.getEventList().size(),1);
		
		Position pos = new Position(2,2);
		Event e = new Event(2,3,pos);
		rout.addEvent(e);
		
		assertEquals(rout.getEventList().size(),2);
	}
	
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
		
		System.out.println(rt.getEvent(1).getEventId());
		assertEquals(2,rt.getEventList().size());
	}
	
	@Test
	public void testsyncEvents(){
		
		RoutingTable testrout = new RoutingTable();
		
		assertTrue(testrout.getEventList().isEmpty());
		assertFalse(rout.getEventList().isEmpty());
		
		rout.syncEvents(testrout);
		System.out.println(testrout.getEventList().size());
		
		//assertFalse(testrout.getEventList().isEmpty());
	}
}
