import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Test;
import org.junit.Before;

public class AgentTest {

	/**
	 * Unit testing agents methods.
	 * @author Viktor Lindblad
	 * 
	 */
	private Agent agent;
	private Position position;
	private Event event;
	private Node node;
	private RoutingTable rout;
	
	/**
	 * Runs before every test.
	 */
	@Before
	public void startingTest() {
	
		position = new Position(3,3);
		event = new Event(1,2,position);
		event.setDistance(5);
		agent = new Agent(event);
		
		node = new Node(position);
		
	}
	
	/**
	 * Confirms that agent is not null.
	 */
	@Test
	public void AgentNotNull() {
		assertNotNull(agent);
	}
	
	/**
	 * Testing agents method add to path and get path taken
	 * with a given node and a different stack.  
	 */
	@Test
	public void TestAgentAddToPath() {
		
		Stack<Node> stack;
		Stack<Node>	newstack = new Stack<Node>();
		
		agent.addToPath(node);
		stack=agent.getPathTaken();
		
		newstack.push(node);
		
		assertEquals(stack.peek(),newstack.peek());
	}
	
	/**
	 * Testing if agent can move on a condition that should be false.
	 */
	@Test
	public void agentTimeToLive() {
		
		assertTrue(agent.canMove());
		for(int i=0; i<100; i++ ){
			agent.addToPath(node);
		}
		
		assertFalse(agent.canMove());
	}
	
	/**
	 * Testing if agent can move on a condition that should be true.
	 */
	@Test
	public void testMoveAgent() {
		agent = new Agent(event);
		assertTrue(agent.canMove());
	}

	/**
	 * Testing if agents handle events give the node the event agent has.
	 */
	@Test
	public void TesthandleEvents() {
		
		Position p = new Position(2,4);
		Node node = new Node(p);
		rout=node.getRoutingTable();
		
		agent.addToPath(node);
		
		agent.handleEvents(rout);
		
		
		Event e = node.getRoutingTable().getEvent(1);
		
		assertEquals(e.getEventId(),1);
	}
	
	/**
	 * Test if distance is changed when running handle events, with two 
	 * given events with same id and different distance.
	 */
	@Test
	public void TesthandleEventDistance() {
		
		Position p = new Position(2,4);
		Event ev = new Event(1,4,p);
		Node node = new Node(p);
		
		ev.setDistance(3);
		node.getRoutingTable().addEvent(ev);
		
		rout=node.getRoutingTable();
		
		agent.addToPath(node);
		
		agent.handleEvents(rout);
		
		Event e = node.getRoutingTable().getEvent(1);
		
		assertEquals(e.getDistance(),3);
	}
}
