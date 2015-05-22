import static org.junit.Assert.*;

import java.util.Stack;

import org.junit.Test;
import org.junit.Before;

public class AgentTest {

	/**
	 * Unit testing agents methods.
	 * @author ViktorLindblad
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
	public void startingTest(){
	
		position = new Position(3,3);
		event = new Event(1,2,position);
		agent = new Agent(event);
		node = new Node(position);
		rout = new RoutingTable();
	}
	
	/**
	 * Confirms that agent is not null.
	 */
	@Test
	public void AgentNotNull(){
		assertNotNull(agent);
	}
	
	/**
	 * Testing agents method add to path and get path taken
	 * with a given node and a different stack.  
	 */
	@Test
	public void TestAgentAddToPath(){
		
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
	public void agentTimeToLive(){
		
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
	public void testMoveAgent(){

		Constants.timeToLiveAgent=10;
		agent = new Agent(event);
		assertTrue(agent.canMove());
	}

	/**
	 * Testing if agents handle events contains the same event,
	 * as a given event and routingtable.
	 *  
	 */
	@Test
	public void TesthandleEvents(){
		
		RoutingTable r = new RoutingTable();
		
		agent.handleEvents(r);
		
		Event e = r.getEvent(1);
		
		assertEquals(e.getEventId(),event.getEventId());
	}
	
	/**
	 * Test if distance is changed when running handle events, with a 
	 * given event with given distance.
	 */
	@Test
	public void TesthandleEventDistance(){
		Position p = new Position(2,4);
		Event ev = new Event(1,4,p);
		event.setDistance(3);
		
		ev.setDistance(5);
		
		rout.addEvent(ev);
		
		agent.handleEvents(rout);	
		
		Event e = rout.getEvent(1);
		
		assertEquals(ev.getDistance(),3);
	}
}
