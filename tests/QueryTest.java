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
	
	
	@Before
	public void init() {
		p = new Position(10,20);
		e = new Event(50, 60, p);
		q = new Query(50);
		n = new Node(p);
		pathTaken = new Stack<Node>();
		rT = new RoutingTable();
	}
	
	
	@Test
	public void testQuery() {
		assertNotNull(q);
	}
	
	
	@Test
	public void testCanMove() {
		assertTrue(q.canMove());
	}
	
	
	@Test
	public void testTimeToLive() {
		assertTrue(q.canMove());
		for(int i = 0 ; i < 405 ; i++){
			q.addToPath(n);
		}
		assertFalse(q.canMove());
	}
	
	@Test
	public void testAddToPath() {
		q.addToPath(n);
		pathTaken = q.getPathTaken();
		Node newNode = pathTaken.pop();
		assertEquals(newNode, n);
	}
	
	@Test
	public void testRepliedDone() {
		assertFalse(q.repliedDone());
	}
	
	
	@Test
	public void testHandleEvents() {
		q.addToPath(n);
		rT.addEvent(e);
		assertNull(q.handleEvents(rT));
	}
	
	@Test
	public void testHandleEvents2() {
		q.addToPath(n);
		q.addToPath(n);
		rT.addEvent(e);
		assertNull(q.handleEvents(rT));
		assertEqual(event.getPosition(),p)
	}
	
	

}
