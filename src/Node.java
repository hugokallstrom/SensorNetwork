import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author Hugo Källström
 * Represents a node in the sensor network.
 * The node can detect events and send out queries for specific events,
 * it can also receive messages in the form of agents and queries.
 */
public class Node {

    private Position myPosition;
    private ArrayList<Node> neighbours;
    private RoutingTable routingTable;
    private Queue<Message> messageQueue;
    private boolean availability;
    private boolean isSender;
    private QueryTimer timer;
    private Message message;

    /**
     * Creates a new Node based on a position.
     * @param position the nodes position.
     */
    public Node(Position position) {
        myPosition = position;
        availability = true;
        isSender = false;
        neighbours = new ArrayList<Node>();
        routingTable = new RoutingTable();
        messageQueue = new LinkedBlockingQueue<Message>();
        timer = null;
    }

    /**
     * Receives an event and adds it to the
     * nodes routing table.
     * @param event the received event.
     */
    public void receiveEvent(Event event) {
        routingTable.addEvent(event);
    }

    /**
     * Creates a new agent based on a predefined chance,
     * and adds it to the message queue.
     * @param event the received event.
     */
    public void createAgent(Event event) {
        Message agent = new Agent(event);
        agent.addToPath(this);
        messageQueue.add(agent);
    }

    /**
     * Creates a new query and adds it to the message queue.
     * @param eventId the event id to search for.
     */
    public void createQuery(int eventId) {
        Message query = new Query(eventId);
        query.addToPath(this);
        messageQueue.add(query);
        timer = new QueryTimer(eventId);
    }

    
    
    public Message getMessageInQueue() {
        if(!messageQueue.isEmpty()) {
            return messageQueue.poll();
        } else {
            return null;
        }
    }
    
    /**
     * If there are messages in the message queue, retrieve
     * the first message and depending on the type of
     * message, either sync events with an agent or try to answer
     * a query. The message is then sent to a suitable neighbor.
     */
    public Node handleMessage(Message message) {
        this.message = message;
        
        Position nodePosition = message.handleEvents(routingTable);
        Node neighbour = getNeighbourFromPos(nodePosition);
        
        if(nodePosition == null || nodePosition.equals(myPosition)) {
            neighbour = selectNextNeighbour(message.getPathTaken());
        } else if(neighbour.getMyPosition().equals(myPosition)) {
                neighbour = selectNextNeighbour(message.getPathTaken());
            }   
        return neighbour;
    }

    public void checkTimer() {
        if(timer != null) {
            timer.countQuerySteps();
            if(timer.checkQuerySteps()) {
                createQuery(timer.getEventId());
                timer=null;
            }
        }
    }

    /**
     * Selects a neighbor based on the messages path taken.
     * @param pathTaken the path the message has taken through the network.
     * @return a suitable node, preferably one which has not been visited before.
     */
    private Node selectNextNeighbour(Stack<Node> pathTaken) {
        Collections.shuffle(neighbours);
        for(Node neighbour : neighbours) {
            if(neighbour.isAvailable() && !pathTaken.contains(neighbour)) {
                return neighbour;
            }
        }
        for(Node neighbour : neighbours) {
            if(neighbour.isAvailable()) {
                return neighbour;
            }
        }
        return neighbours.get(new Random().nextInt(neighbours.size()));
    }

    /**
     * Returns a neighbor based on a position.
     * @param nodePosition the position of the neighbor.
     * @return the neighbor which has the position.
     */
    private Node getNeighbourFromPos(Position nodePosition) {
        for(Node neighbour : neighbours) {
            if(neighbour.getMyPosition().equals(nodePosition)) {
                return neighbour;
            }
        }
        return this;
    }

    /**
     * Sends a message to a node (neighbor) if it is available.
     * @param neighbour the node which receives the message.
     */
    public void sendMessageToNode(Node neighbour) {
        if(neighbour.isAvailable() && message.canMove() && isAvailable()) {
            availability = false;
            neighbour.receiveMessage(message);
        } else if(message.canMove()){
            messageQueue.add(message);
        }
    }

    /**
     * Receives a message and sets availability to false.
     * @param message the message received.
     */
    private void receiveMessage(Message message) {
        availability = false;
        if(message.canMove()) {
            message.addToPath(this);
            messageQueue.add(message);
        }
    }

    /**
     * Sets the nodes neighbors.
     * @param neighbours the nodes neighbors.
     */
    public void setNeighbours(ArrayList<Node> neighbours) {
        this.neighbours = neighbours;
    }

    /**
     * Set a node as sender.
     * @param sender true if the node is a sender.
     */
    public void setSender(boolean sender) {
        isSender = sender;
    }

    /**
     * Checks if the node is a sender.
     * @return true if the node is a sender.
     */
    public boolean isSender() {
        return isSender;
    }

    /**
     * Set the node as available.
     */
    public void setAvailable() {
        availability = true;
    }

    /**
     * Check if the node is available.
     * @return true if the node is available.
     */
    public boolean isAvailable() {
        return availability;
    }

    /**
     * Gets the nodes position.
     * @return the nodes position.
     */
    public Position getMyPosition() {
        return myPosition;
    }

    /**
     * Gets the nodes routing table.
     * @return the nodes routing table.
     */
    public RoutingTable getRoutingTable() {
        return routingTable;
    }

    /**
     * Gets the nodes queue.
     * @return the nodes queue.
     */
    public Queue<Message> getQueue() {
        return messageQueue;
    }

}

