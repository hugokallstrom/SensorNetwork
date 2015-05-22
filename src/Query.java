/**
 * Created by hugo on 5/11/15.
 */
import java.util.*;

/**
 * Class that implements Message. Query is searching for after a requested
 * event ID. When the event is found, the Query will go the same way back to
 * the origin of the request and then print the event ID, the time that the
 * event was detected and its origin. Query will live for 45 time steps.
 * If it did not find the event, it will try to send the request one more time
 * then stop searching.
 *
 * @author charlotteristiniemi
 *
 */
public class Query implements Message {

	private Stack<Node> pathTaken;
	private int steps;
	private Event event;
    private int requestedEventId;
    private int timeToLive = Constants.timeToLiveQuery;
    private boolean hasFoundPath = false;
    private boolean foundFinalNode = false;

    /**
     * Constructor for Query. Sets variables and creates a stack for path taken
     * and a hashtable for the visited nodes
     *
     * @param requestedEventId the requested event.
     */
	public Query(int requestedEventId) {
		steps = 0;
		pathTaken = new Stack<Node>();
		this.requestedEventId = requestedEventId;
	}

    /**
     * Checks if Query can move one step depending on Query's time to live.
     *
     * @return boolean - true if Query can move. False else.
     */
	public boolean canMove() {
        return steps < timeToLive;
	}

    /**
     * Returns Query's path taken as a stack.
     *
     * @return pathTaken
     */
    public Stack<Node> getPathTaken() {
		return pathTaken;
	}

    /**
     * Adds a node into hash table and a position into hash table. Also increases
     * Query's steps with one. If Query's request is replied, steps will be set
     * to 0.
     */
	public void addToPath(Node node) {
        if(foundFinalNode) {
            steps = 0;
        } else if(hasFoundPath) {
            steps = 0;
            pathTaken.push(node);
        } else {
			steps++;
            pathTaken.push(node);
        }
	}

    /**
     * Method handleEvents looks for a requested event ID in node's routingTable.
     * If the event value is null, the node doesn't know the way to the event so method returns null.
     * If event isn't null, the node knows the way to the event and checks if replied is done,
     * then returns the position to the neighbour who knows the way to the event.
     * If the node has found the node who has the event, the method returns the position
     * to the previous node in path taken.
     *
     * @param routingTable the nodes routing table.
     * @return Position
     */
	public Position handleEvents(RoutingTable routingTable) {
        if(!foundFinalNode) {
            event = routingTable.getEvent(requestedEventId);
            if(event != null) {
                hasFoundPath = true;
                if (event.getDistance() == 0) {
                    foundFinalNode = true;
                    return pathTaken.pop().getMyPosition();
                }
                return event.getPosition();
            }
        } else {
            if(replied()) {
                return null;
            } else {
                return pathTaken.pop().getMyPosition();
            }
        }
        return null;
    }

    /**
     * Checks if Query-stack has one node and if boolean "hasFoundPath" is true.
     * If both are true it means that Query has found the destination of the event
     * and travelled all the way back from where the Query was created.
     * It will print information about the event (event ID, time of the event and
     * the position of the event). If false, Query hasn't found its requested event.
     *
     * @return boolean - true if replied, false else
     */
    public boolean replied() {
        if(pathTaken.size() == 0 && event != null) {
            steps = timeToLive;
            printEventInfo();
            if(Constants.numberOfReplies.containsKey(event.getEventId())) {
                Constants.numberOfReplies.put(event.getEventId(), 2);
            } else {
                Constants.numberOfReplies.put(event.getEventId(), 1);
            }
            return true;
        }
        return false;
    }

    /**
     * Prints info about the event when the query is replied.
     */
    private void printEventInfo() {
        System.out.println("Query replied. Id: " + event.getEventId() + "\n"
                           + " Occurred at time step: " + event.getTimeOfEvent() + "\n"
                           + " At position: " + event.getPosition());
    }
}

