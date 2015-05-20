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



}
