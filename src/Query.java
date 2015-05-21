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
    private Node currentNode;
    private int requestedEventId;
    private int timeToLive = Constants.timeToLiveQuery;
    private boolean hasFoundPath = false;
    private boolean foundFinalNode = false;

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
	
		//System.out.println("------------------------");
		//System.out.println("can query move? "+ steps);
		//System.out.println("------------------------");
        return steps < timeToLive;
	}

    /**
     * Returns Query's path taken as a stack.
     *
     * @return pathTaken
     */
    public Stack<Node> getPathTaken() {
    	printPathtaken();
		return pathTaken;
	}

    /**
     * Adds a node into hashtable and a position into hashtable. Also increses
     * Query's steps with one. If Query's request is replied, steps will be set
     * to 0.
     */
	public void addToPath(Node node) {
        currentNode = node;
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
     * If the event value is null, node doesn't know the way to the event so method returns null.
     * If event isn't null, node knows the way to the event and checks if replied is done,
     * then returns the current position in the routingTable.
     *
     * @param routingTable
     * @return Position
     */
	public Position handleEvents(RoutingTable routingTable) {
        if(!foundFinalNode) {
            event = routingTable.getEvent(requestedEventId);
            if(event != null) {
                if (event.getDistance() == 0) {
                    foundFinalNode = true;
                    System.out.println("Sending back event " + requestedEventId + " at " + currentNode.getMyPosition()
                            + ", next node: " + event.getPosition()
                            + ", Path taken: " + printPathtaken());
                    return pathTaken.pop().getMyPosition();
                }
               System.out.println("Found event " + requestedEventId + " at " + currentNode.getMyPosition()
                        + ", next node: " + event.getPosition()
                        + ", Path taken: " + printPathtaken());
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
            System.out.println("Query replied, event at " + event.getPosition() +
                    " Occurred at time step: " + event.getTimeOfEvent() + "." +
                    " With ID: " + event.getEventId() + "\n");
            steps = timeToLive;
            return true;
        }
        return false;
    }

    private String printPathtaken() {
        String out = "";
        for(int i = 0; i < pathTaken.size(); i++) {
            out += pathTaken.get(i).getMyPosition();
        }
        return out;
    }
}

