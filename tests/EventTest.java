import static org.junit.Assert.*;

import org.junit.Test;


public class EventTest {

	@Test
	public void testPosition() {
		Position p = new Position(10,20);
		Event e = new Event(50, 60, p);
		assertNotNull(e);
	}

	@Test
	public void testGetEventId(){
		Position p = new Position(10,20);
		Event e = new Event(50, 60, p);
		assertEquals(e.getEventId(),50);
	}
	

	@Test
	public void testGetDistance(){
		Position p = new Position(10,20);
		Event e = new Event(50, 60, p);
		assertEquals(e.getDistance(),0);
	}
	

	@Test
	public void testGetPosition(){
		Position p = new Position(10,20);
		Event e = new Event(50, 60, p);
		assertEquals(e.getPosition(),p);
	}
	
	@Test
	public void testgetTimeOfEvent(){
		Position p = new Position(10,20);
		Event e = new Event(50, 60, p);
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
