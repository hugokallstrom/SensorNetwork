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
    private Event finalEvent;
    private int requestedEventId;
    private int timeToLive = Constants.timeToLiveQuery;
    private boolean isReplied = false;

    /**
     * Constructor for Query. Sets variables and creates a stack for path taken
     * and a hashtable for the visited nodes
     *
     * @param requestedEventId
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
     * Adds a node into hashtable and a position into hashtable. Also increses
     * Query's steps with one. If Query's request is replied, steps will be set
     * to 0.
     */
	public void addToPath(Node node) {
		if(isReplied) {
			steps = 0;
		} else {
			steps++;
            pathTaken.push(node);
        }
	}
	
	public Position handleEvents(RoutingTable routingTable) {
        event = routingTable.getEvent(requestedEventId);
        if(event != null) {
            if (event.getDistance() == 0) {
                finalEvent = event;
            }
            isReplied = true;
            repliedDone();
            return event.getPosition();
        }
        return null;
    }

    /**
     * Checks if Query-stack has one node and if boolean "isReplied" is true.
     * If both are true it means that Query has found the destination of the event
     * and travelled all the way back from where the creation of the Query.
     * It will print information about the event (event ID, time of the event and
     * the position of the event). If false, Query hasn't found its requested event.
     *
     * @return boolean - true if replied, false else
     */
    public void repliedDone() {
        if(pathTaken.size() == 1 && event.getDistance() == 0) {
            System.out.println("Query replied, event at " + finalEvent.getPosition() +
                    " Occurred at time step: " + finalEvent.getTimeOfEvent() + "." +
                    " With ID: " + finalEvent.getEventId() + "\n");
            steps = timeToLive;
        }
    }
}

