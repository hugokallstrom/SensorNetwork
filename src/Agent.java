/**
 * Created by hugo on 5/11/15.
 */
import java.util.*;
/**
 * Class that implements messages. Agent moves around spreading
 * information to nodes in the network, while agent hasn't moved
 * more than 50 steps.
 * @author ViktorLindblad
 */
public class Agent implements Message {

	private Stack<Node> pathTaken;
	private RoutingTable routingTable;
	private int timeToLive;
	private int steps;
	
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
        
        pathTaken.push(node);
        steps++;
    }
    
    /**
     * Iterate agents routing table and nodes routing table and swaps
     * events. If node or agent knows a shorter path to an event
     * they swap.
     */
	public Position handleEvents(RoutingTable nodeRoutingTable) {
        routingTable.syncEvents(nodeRoutingTable, pathTaken.peek());
        routingTable.findShortestPath(nodeRoutingTable, pathTaken.peek());
        routingTable.deepCopyHashtable();
        
        return null;
	}
}
