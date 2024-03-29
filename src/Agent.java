/**
 * Created by hugo on 5/11/15.
 */
import java.util.*;
/**
 * Class that implements messages. Agent moves around spreading
 * information to nodes in the network, while agent hasn't moved
 * more than 50 steps.
 * @author Viktor Lindblad
 */
public class Agent implements Message {

	private Stack<Node> pathTaken;
	private RoutingTable routingTable;
	private int timeToLive;
	private int steps;
	private Node currentNode;
	
	/**
	 * Creates a new agent and put the given event in 
	 * his routing table.
	 * @param event the event which spawned the agent.
	 */
    public Agent(Event event) {
		steps = 0;
		timeToLive = Constants.timeToLiveAgent;
		pathTaken = new Stack<Node>();
		routingTable = new RoutingTable();
        Event eventCopy = new Event(event);
		routingTable.addEvent(eventCopy);
	}

    /**
     * Checks if agent can move.
     */
	public boolean canMove() {
		return steps < timeToLive;
	}

    /**
     * Returns a stack with the path agent has taken.
     */
	public Stack<Node> getPathTaken() {
		return pathTaken;
	}

    /**
     * Adds the given node to the stack.
     */
    public void addToPath(Node node) {
        if(pathTaken.size() > 0) {
            routingTable.incrementEventDistances();
        }
        
        currentNode = node;
        pathTaken.push(node);
        steps++;
    }
    
    /**
     * Checks agents routing table and nodes routing table and swaps
     * events. If node or agent knows a shorter path to an event
     * they they save the shorter path.
     */
	public Position handleEvents(RoutingTable nodeRoutingTable) {
		
        routingTable.syncEvents(nodeRoutingTable, currentNode);
        routingTable.findShortestPath(nodeRoutingTable, currentNode);
        
        return null;
	}

}
