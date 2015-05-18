import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Stack;

import org.junit.Test;
import org.junit.Before;

import sun.management.resources.agent;

public class AgentTest {

	/**
	 * Junit test for agent
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
		
		position = new Position(3,3);
		event = new Event(1,2,position);
		agent = new Agent(event);
		node = new Node(position);
		rout = new RoutingTable();
	}
	@Test
	public void AgentNotNull(){
		assertNotNull(agent);
	}
	
	@Test
	public void TestAgentAddToPath(){
		
		Stack<Node> stack;
		Stack<Node>	newstack = new Stack<Node>();
		
		agent.addToPath(node);
		stack=agent.getPathTaken();
		
		newstack.push(node);
		
		assertEquals(stack.peek(),newstack.peek());
	}
	
	@Test
	public void agentTimeToLive(){
		assertTrue(agent.canMove());
		for(int i=0; i<100; i++ ){
			agent.addToPath(node);
		}
		assertFalse(agent.canMove());
	}

	@Test
	public void testCantMoveAgent() {
		Constants.timeToLiveAgent=0;
		agent = new Agent(event);
		assertFalse(agent.canMove());
	}
	@Test
	public void testMoveAgent(){
		Constants.timeToLiveAgent=10;
		agent = new Agent(event);
		assertTrue(agent.canMove());
	}
	
	@Test
	public void TesthandleEvents(){
		
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
