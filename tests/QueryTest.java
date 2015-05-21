import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Test;
import org.junit.Before;


public class QueryTest {

	Event e;
	Query q;
	Position p;
	Node n;
	Stack<Node> pathTaken;
	RoutingTable rT;

	/**
	 * Initializes what to be used in tests.
	 */
	@Before
	public void init() {
		p = new Position(10,20);
		e = new Event(50, 60, p);
		q = new Query(50);
		n = new Node(p);
		pathTaken = new Stack<Node>();
		rT = new RoutingTable();
	}

	/**
	 * Test to see that the constructor created something.
	 */
	@Test
	public void testQuery() {
		assertNotNull(q);
	}

	/**
	 * In the beginning canMove method should always be true. Checks if return value is true.
	 */
	@Test
	public void testCanMove() {
		assertTrue(q.canMove());
	}

	/**
	 * Checks the "time to live" limit for Query that is 405. After for loop,
	 * canMove method should therefore return false.
	 */
	@Test
	public void testTimeToLive() {
		assertTrue(q.canMove());
		for(int i = 0 ; i < 45 ; i++){
			q.addToPath(n);
		}
		assertFalse(q.canMove());
	}

	/**
	 * Adds one node to stack, then uses getPathTaken to return the stack and then compare
	 * the created node to what was in the top of the stack to see that they're equal.
	 * Tests both addToPath and getPathTaken.
	 */
	@Test
	public void testAddToPath() {
		q.addToPath(n);
		pathTaken = q.getPathTaken();
		Node newNode = pathTaken.pop();
		assertEquals(newNode, n);
	}

	/**
	 * When a Query is replied with distance 0, handleEvents should print information and
	 * return same position as the query is standing on.
	 */
	@Test
	public void testHandleEvents() {
		Position goalEventPos = new Position(1,1);
		Node goalEventNode = new Node(goalEventPos);
		q.addToPath(goalEventNode);

		Event goalEvent = new Event(50,60,goalEventPos);
		rT.addEvent(goalEvent);

		assertEquals(q.handleEvents(rT),goalEventPos);
	}

	/**
	 * When a Query found the event ID in the routingTable but distance is greater than
	 * 0, it should return the next position to go to.
	 */
	@Test
	public void testHandleEvents1() {
		Position neighborPos = new Position(1,1);
		Node neighborNode = new Node(neighborPos);
		q.addToPath(neighborNode);

		Event findEvent = new Event(50,60,neighborPos);
		findEvent.setDistance(3);
		rT.addEvent(findEvent);

		assertEquals(q.handleEvents(rT),neighborPos);
	}

	/**
	 * If there is no events in routingTable, handleEvents should return null.
	 */
	@Test
	public void testHandleEvents3() {
		assertNull(q.handleEvents(rT));
	}
}
