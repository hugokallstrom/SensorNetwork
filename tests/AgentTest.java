import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Stack;

import org.junit.Test;
import org.junit.Before;

import sun.management.resources.agent;

public class AgentTest {

	/**
	 * Unit testing agent
	 * @author ViktorLindblad
	 * 
	 */
	private Agent agent;
	private Position position;
	private Event event;
	private Node node;
	private RoutingTable rout;
	
	@Before
	public void startingTest(){
		/**
		 * Runs before every test.
		 */
		position = new Position(3,3);
		event = new Event(1,2,position);
		agent = new Agent(event);
		node = new Node(position);
		rout = new RoutingTable();
	}
	@Test
	public void AgentNotNull(){
		/**
		 * Confirms that agent is not null.
		 */
		assertNotNull(agent);
	}
	
	@Test
	public void TestAgentAddToPath(){
		/**
		 * Testing agents method add to path with a given node and 
		 * a different stack.  
		 */
		
		Stack<Node> stack;
		Stack<Node>	newstack = new Stack<Node>();
		
		agent.addToPath(node);
		stack=agent.getPathTaken();
		
		newstack.push(node);
		
		assertEquals(stack.peek(),newstack.peek());
	}
	
	@Test
	public void agentTimeToLive(){
		/**
		 * Testing if agent can move on a condition that should be false.
		 */
		assertTrue(agent.canMove());
		for(int i=0; i<100; i++ ){
			agent.addToPath(node);
		}
		assertFalse(agent.canMove());
	}

	@Test
	public void testMoveAgent(){
		/**
		 * Testing if agent can move on a condition that should be true.
		 */
		Constants.timeToLiveAgent=10;
		agent = new Agent(event);
		assertTrue(agent.canMove());
	}
	
	@Test
	public void TesthandleEvents(){
		
		/**
		 * Testing if agents handle events contains the same event,
		 * as a given event and routingtable. 
		 */
		
		RoutingTable r = new RoutingTable();
		r.addEvent(event);
		
		agent.handleEvents(rout);
		
		Event e = r.getEvent(1);
		
		Event ev = rout.getEvent(1);

		assertEquals(e,ev);
	}
	
	@Test
	public void TesthandleEventDistance(){
		fail("not yet implemented");
		event.setDistance(5);
		
		Position p = new Position(5,5);
		Event even = new Event(5,5,p);
		
		even.setDistance(3);
		
		agent.handleEvents(rout);		
		Event ev = rout.getEvent(1);

		assertEquals(ev);
	}
}
