/**
 * Created by hugo on 5/11/15.
 */
import java.util.*;

public class Agent implements Message {
	/**
	 * Class that implements messages. Agent moves around spreading
	 * information to nodes in the network, while agent hasn't moved
	 * more than 50 steps.  
	 * @author ViktorLindblad
	 */
	
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
		routingTable.addEvent(event);
	}

	public boolean canMove() {
		/**
		 * Checks if agent can move.
		 */
		return steps < timeToLive;
	}
	
	public Stack<Node> getPathTaken() {
		/**
		 * Returns a stack with the path agent has taken. 
		 */
		
		return pathTaken;
	}

    public void addToPath(Node node) {
    	/**
    	 * Adds the given node to the stack.
    	 */
		steps++;
        if(pathTaken.size() > 0) {
            previousNode = pathTaken.peek();
            this.routingTable.incrementEventDistances();
        } else {
            previousNode = node;
        }
		pathTaken.push(node);
	}

	public Position handleEvents(RoutingTable routingTable){
		/**
		 * Iterate agents routing table and nodes routing table and swaps
		 * events. If node or agent knows a shorter path to an event
		 * they swap. 
		 */
		System.out.println("Im an agent");
        Node currentNode = pathTaken.peek();
        ArrayList<Event> nodesEventList = routingTable.getEventList();
		this.routingTable.syncEvents(nodesEventList);
        if(pathTaken.size() != 1) {
            this.routingTable.changeEventPosition(previousNode.getMyPosition(), currentNode.getMyPosition(), nodesEventList);
        }
       // System.out.println("Agent at position: " + currentNode.getMyPosition() + routingTable.toString());
        return null;
	}

}
