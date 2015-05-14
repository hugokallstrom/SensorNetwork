/**
 * Created by hugo on 5/11/15.
 */
import java.util.*;

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
		routingTable.addEvent(event);
	}

	public boolean canMove() {
		return steps < timeToLive;
	}

	public Stack<Node> getPathTaken() {
		return pathTaken;
	}

    public void addToPath(Node n) {
		steps++;
		pathTaken.push(n);
	}

	public Position handleEvents(RoutingTable routingTable) {
        ArrayList<Event> nodesEventList = routingTable.getEventList();
		this.routingTable.syncEvents(nodesEventList);
		return null;
	}
}
