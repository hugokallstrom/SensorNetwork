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
    private String nodeStatus;
    private int agentChance = Constants.agentChance;

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
    }

    /**
     * Receives an event and adds it to the
     * nodes routing table.
     * @param event the received event.
     */
    public void receiveEvent(Event event) {
        routingTable.addEvent(event);
        createAgent(event);
        System.out.println("Event detected at: " + myPosition + "With id: " + event.getEventId() + " At timestep: " + event.getTimeOfEvent());
        nodeStatus = "E";
    }

    /**
     * Creates a new agent based on a predefined chance,
     * and adds it to the message queue.
     * @param event the received event.
     */
    private void createAgent(Event event) {
        if(calculateChance(agentChance)) {
            Message agent = new Agent(event);
            agent.addToPath(this);
            messageQueue.add(agent);
        }
    }

    /**
     * Creates a new query and adds it to the message queue.
     * @param eventId the event id to search for.
     */
    public void createQuery(int eventId) {
        System.out.println(myPosition + " Creating query with event id " + eventId);
        Message query = new Query(eventId);
        query.addToPath(this);
        messageQueue.add(query);
    }

    /**
     * Checks if a random generated integer equals zero.
     * The bigger the supplied value, the smaller the chance
     * to return true.
     * @param chance the value to generate a random integer from.
     * @return if the generated value equals zero, return true.
     */
    private boolean calculateChance(int chance) {
        return new Random().nextInt(chance) == 0;
    }

    /**
     * If there are messages in the message queue, retrieve
     * the first message and depending on the type of
     * message, either sync events with an agent or try to answer
     * a query. The message is then sent to a suitable neighbour.
     */
    public void handleMessage() {
        // Test purposes
        if(availability) {
            nodeStatus = "*";
        } else {
            nodeStatus = "U";
        }

        if(!messageQueue.isEmpty()) {
            Message message = messageQueue.poll();
            Position nodePosition = message.handleEvents(routingTable);
            if(nodePosition == null) {
                Node neighbour = selectNextNeighbour(message.getPathTaken());
                sendMessageToNode(message, neighbour);
            } else if(nodePosition.equals(myPosition)) {
                Node previousNode = message.getPathTaken().pop();
                sendMessageToNode(message, previousNode);
                //    System.out.println("Found event at " + myPosition + " Sending back to: " + previousNode.getMyPosition());
            } else {
                Node neighbour = getNeighbourFromPos(nodePosition);
                sendMessageToNode(message, neighbour);
            }
        }
    }

    /**
     * Selects a neighbour based on the messages path taken.
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
     * Returns a neighbour based on a position.
     * @param nodePosition the position of the neighbour.
     * @return the neighbour which has the position.
     */
    private Node getNeighbourFromPos(Position nodePosition) {
      //  System.out.println(myPosition.toString() + "neighbor: " + nodePosition.toString());
        for(Node neighbour : neighbours) {
            if(neighbour.getMyPosition().equals(nodePosition)) {
                return neighbour;
            }
        }
        return null;
    }

    /**
     * Sends a message to a node (neighbour) if it is available.
     * @param message the message to be sent.
     * @param neighbour the node which receives the message.
     */
    private void sendMessageToNode(Message message, Node neighbour) {
        if(neighbour.isAvailable()) {
            availability = false;
        //    System.out.println(myPosition + " Sending message to neighbour: " + neighbour.getMyPosition());
            neighbour.receiveMessage(message);
        } else {
          //  System.out.println(myPosition + " Putting message in queue, neighbour at " + neighbour.getMyPosition() + " not available.");
            nodeStatus = "+";
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
            nodeStatus = "A";
        }
    }

    /**
     * Sets the nodes neighbours.
     * @param neighbours the nodes neighbours.
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
    public Queue getQueue() {
        return messageQueue;
    }

    /**
     * Set the chance to create an agent.
     * @param agentChance the chance to create an agent.
     */
    public void setAgentChance(int agentChance) {
        this.agentChance = agentChance;
    }

    /**
     * Prints the nodes status.
     * @return a string representation of the nodes status.
     */
    @Override
    public String toString() {
        if((myPosition.getX() + 1) == Math.sqrt(Constants.nrOfNodes)) {
            return nodeStatus + "\n";
        } else {
            return nodeStatus;
        }
    }
}

