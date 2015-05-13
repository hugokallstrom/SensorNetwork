import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class EventTest {

	Event e;
	Position p;
	
	@Before
	public void init() {
		p = new Position(10,20);
		e = new Event(50, 60, p);
	}
	
	@Test
	public void testPosition() {
		assertNotNull(e);
	}

	@Test
	public void testGetEventId(){
		assertEquals(e.getEventId(),50);
	}
	

	@Test
	public void testGetDistance(){
		assertEquals(e.getDistance(),0);
	}
	

	@Test
	public void testGetPosition(){
		assertEquals(e.getPosition(),p);
	}
	
	@Test
	public void testgetTimeOfEvent(){
		assertEquals(e.getTimeOfEvent(),60);
	}
	
	@Test
	public void testSetPosition(){
		Position first = new Position(10,20);
		Position second = new Position(40,50);
		Event e = new Event(50, 60, first);
		assertEquals(e.getPosition(),first);
		e.setPosition(second);
		assertEquals(e.getPosition(),second);
	}
	
	
	@Test
	public void testSetDistance(){
		Position p = new Position(10,20);
		Event e = new Event(50, 60, p);
		e.setDistance(5);
		assertEquals(e.getDistance(),5);
	}	
}
