/**
 * Created by hugo on 5/11/15.
 */
import java.util.*;

public class Query implements Message {
	
	private Stack<Node> pathTaken;
	private Hashtable<Position, Node> visited;
	private int steps;
	private Event event;
	private int timeToLive;
	private boolean isReplied=false;

	public Query(Event e) {
		timeToLive = Constants.timeToLiveQuery;
		steps = 0;
		visited = new Hashtable<Position,Node>();
		pathTaken = new Stack<Node>();
		event = e;
	}

	public boolean canMove() {
		return(timeToLive>=steps);
	}

    public Stack<Node> getPathTaken() {
		return pathTaken;
	}

	public void addToPath(Node node) {
		if(isReplied) {
			steps = 0;
		} else {
			steps++;
            pathTaken.push(node);
            visited.put(node.getMyPosition(), node);
        }
	}
	
	public Position handleEvents(RoutingTable routingTable) {
		Event eventDirection = routingTable.getEvent(event);
		if(eventDirection != null) {
			isReplied = true;
			return eventDirection.getPosition();
		}
		return null;
	}
}

