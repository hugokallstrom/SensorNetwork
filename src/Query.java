/**
 * Created by hugo on 5/11/15.
 */
import java.util.*;

/**
 * 
 * @author charlotteristiniemi
 *
 */
public class Query implements Message {
	
	private Stack<Node> pathTaken;
	private Hashtable<Position, Node> visited;
	private int steps;
	private Event event;
    private int requestedEventId;
	private int timeToLive = Constants.timeToLiveQuery;
	private boolean isReplied=false;

	public Query(int requestedEventId) {
		steps = 0;
		visited = new Hashtable<Position,Node>();
		pathTaken = new Stack<Node>();
		this.requestedEventId = requestedEventId;
	}

	public boolean canMove() {
        return steps < timeToLive;
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
        try {
            event = routingTable.getEvent(requestedEventId);
            isReplied = true;
            if(repliedDone()) {
                return null;
            }
            return event.getPosition();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public boolean repliedDone() {
        if(pathTaken.size() == 1 && isReplied) {
            System.out.println("Query replied, event at " + event.getPosition() +
                               " Occurred at time step: " + event.getTimeOfEvent() + "." +
                               " With ID: " + event.getEventId() + "\n");
            steps = timeToLive;
            return true;
        }
        return false;
    }
}

