import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test for class Event.
 *
 * @author      Charlotte Ristiniemi
 */

public class EventTest {

	Event e;
	Position p;

	/**
	 * Initializing a new event with a given position to work with in every test.
	 */
	@Before
	public void init() {
		p = new Position(10,20);
		e = new Event(50, 60, p);
	}

	/**
	 * Test to see that something was created in Event constructor
	 */
	@Test
	public void testEvent() {
		assertNotNull(e);
	}

	/**
	 * Test to get event ID. ID was set to 50 in constructor.
	 * Check if method getEventId equals 50.
	 */
	@Test
	public void testGetEventId(){
		assertEquals(e.getEventId(),50);
	}

	/**
	 * Test to get event ID. ID was set to 50 in constructor.
	 * Check if method getEventId equals 50.
	 */
	@Test
	public void testGetDistance(){
		assertEquals(e.getDistance(),0);
	}

	/**
	 * Test to get position. Position was set to x=10 y=20 in constructor.
	 * Check if method getPosition equals that position.
	 */
	@Test
	public void testGetPosition(){
		assertEquals(e.getPosition(),p);
	}

	/**
	 * Test to get the time that the event was created.
	 * Time of event was set to 60 in constructor.
	 * Check if method getTimeOfEvent equals 60.
	 */
	@Test
	public void testGetTimeOfEvent(){
		assertEquals(e.getTimeOfEvent(),60);
	}

	/**
	 * Test for second constructor. A new event is made with new Id, time and position.
	 * Then checked if right.
	 */
	@Test
	public void testNewEvent(){
		Position p1 = new Position(2,3);
		Event e1 = new Event(123,456,p1);
		Event event = new Event(e1);
		assertNotNull(event);
		assertEquals(event.getEventId(),123);
		assertEquals(event.getTimeOfEvent(),456);
		assertEquals(event.getPosition(),p1);
	}

	/**
	 * Test to set a new position in Event. First position is x=10 y=20 and second position is x=40 y=50.
	 * First position is set in constructor and checked if right, then second gets
	 * set with method and checked if right.
	 */
	@Test
	public void testSetPosition(){
		Position first = new Position(10,20);
		Position second = new Position(40,50);
		Event e = new Event(50, 60, first);
		assertEquals(e.getPosition(),first);
		e.setPosition(second);
		assertEquals(e.getPosition(),second);
	}

	/**
	 * Test to set a new distance in Event. Distance is set to 0 in constructor.
	 * Distance 5 is then set into Event and then checked if right.
	 */
	@Test
	public void testSetDistance(){
		e.setDistance(5);
		assertEquals(e.getDistance(),5);
	}

	/**
	 * Test to see that method incrementDistance increases distance with 1
	 * every time it is called. Distance is set to 0 in constructor,
	 * increased with one in method and then checked.
	 */
	@Test
	public void testIncrementDistance(){
		e.incrementDistance();
		assertEquals(e.getDistance(),1);
	}


	/**
	 * Test to set a new time of event in Event. Time of event is set to 60 in constructor.
	 * Time 60 is then set into Event and checked if right.
	 */
	@Test
	public void testSetTimeOfEvent(){
		e.setTimeOfEvent(25);
		assertEquals(e.getTimeOfEvent(),25);
	}
}