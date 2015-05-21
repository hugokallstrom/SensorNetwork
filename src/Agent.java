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
    private Node previousNode;
	
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
            previousNode = pathTaken.peek();
            routingTable.incrementEventDistances();
        } else {
            previousNode = node;
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
       // System.out.println("Position: " + pathTaken.peek().getMyPosition());
        routingTable.syncEvents(nodeRoutingTable, pathTaken.peek(), previousNode);
        routingTable.findShortestPath(nodeRoutingTable, pathTaken.peek(), previousNode);
        //routingTable.changeEventPosition(pathTaken.peek(), nodeRoutingTable);
        routingTable.deepCopyHashtable();
        nodeRoutingTable.deepCopyHashtable();
        //System.out.println("Agent routing table : " + routingTable.toString());
        //System.out.println("Node routing table : " + nodeRoutingTable.toString());
        return null;
	}
}
