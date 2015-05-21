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
		//System.out.println("-----------------------");
		//System.out.println("Can agent move?" + steps);
		//System.out.println("-----------------------");
		//testPrintPath();
		return steps < timeToLive;
	}
	
	public Stack<Node> getPathTaken() {
		/**
		 * Returns a stack with the path agent has taken. 
		 */
		
		return pathTaken;
	}
	/**
     * Adds the given node to the stack.
     */
    public void addToPath(Node node) {
    	
        if(pathTaken.size() > 0) {
            previousNode = pathTaken.peek();
           this.routingTable.incrementEventDistances();
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
	public Position handleEvents(RoutingTable nodeRoutingTable){		

		routingTable.syncEvents(nodeRoutingTable);
        routingTable.findShortestPath(nodeRoutingTable);
        
        if(pathTaken.size() > 1) {
            this.routingTable.changeEventPosition(previousNode, nodeRoutingTable);
        }

        routingTable.deepCopyHashtable();
       // System.out.println("Agent has this in routing table: at position" + pathTaken.peek());
        //routingTable.printInfornmationRouting();
        
        //testPrint();
        return null;
	}

    private void testPrint() {
        Node currentNode = pathTaken.peek();
        System.out.println("Agent at position: " + currentNode.getMyPosition().toString() + routingTable.toString());
    }
    /*
    private void testPrintPath(){
    	Stack<Node> testPrint = pathTaken;
    	
    	System.out.println("--------------");
    	System.out.println("Agen have taken this path");
    	System.out.println("--------------");
    	while(!testPrint.isEmpty()){
    		System.out.println("Position "+testPrint.peek().getMyPosition().toString());
    		testPrint.pop();
    	}
    }*/
}
