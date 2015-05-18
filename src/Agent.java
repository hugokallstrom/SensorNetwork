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
        Node previousNode;
		steps++;
        if(pathTaken.size() > 1) {
            previousNode = pathTaken.peek();
        } else {
           previousNode = node;
        }
		pathTaken.push(node);
        routingTable.incrementEventDistances();
        routingTable.changeEventPosition(previousNode.getMyPosition(), node.getRoutingTable().getEventList());
        System.out.println(routingTable.toString());
	}

	public Position handleEvents(RoutingTable routingTable){
		/**
		 * Iterate agents routingtable and nodes routingtable and swaps
		 * events. If node or agent knows a shorter path to an event
		 * they swap. 
		 */
		
        ArrayList<Event> nodesEventList = routingTable.getEventList();
		this.routingTable.syncEvents(nodesEventList);
		return null;
	}
}
