import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by hugo on 5/11/15.
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

    public Node(Position position) {
        myPosition = position;
        availability = true;
        isSender = false;
        neighbours = new ArrayList<Node>();
        routingTable = new RoutingTable();
        messageQueue = new LinkedBlockingQueue<Message>();
    }

    public void receiveEvent(Event event) {
        routingTable.addEvent(event);
        createAgent(event);
    //    System.out.println("Event detected at: " + myPosition + "With id: " + event.getEventId());
        nodeStatus = "E";
    }

    private void createAgent(Event event) {
        if(calculateChance(agentChance)) {
            Message agent = new Agent(event);
            agent.addToPath(this);
            messageQueue.add(agent);
        }
    }

    public void createQuery(int eventId) {
    //    System.out.println(myPosition + " Creating query with event id " + eventId);
        Message query = new Query(eventId);
        query.addToPath(this);
        messageQueue.add(query);
    }

    private boolean calculateChance(int chance) {
        return new Random().nextInt(chance) == 0;
    }

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

    private Node getNeighbourFromPos(Position nodePosition) {
        for(Node neighbour : neighbours) {
            if(neighbour.getMyPosition().equals(nodePosition)) {
                return neighbour;
            }
        }
        return null;
    }

    private void sendMessageToNode(Message message, Node neighbour) {
        if(neighbour.isAvailable()) {
            availability = false;
          //  System.out.println(myPosition + " Sending message to neighbour: " + neighbour.getMyPosition());
            neighbour.receiveMessage(message);
        } else {
          //  System.out.println(myPosition + " Putting message in queue, neighbour at " + neighbour.getMyPosition() + " not available.");
            nodeStatus = "+";
            messageQueue.add(message);
        }
    }

    private void receiveMessage(Message message) {
        availability = false;
        if(message.canMove()) {
            message.addToPath(this);
            messageQueue.add(message);
            nodeStatus = "A";
        }
    }

    public void setNeighbours(ArrayList<Node> neighbours) {
        this.neighbours = neighbours;
    }

    public void setSender(boolean sender) {
        isSender = sender;
    }

    public boolean isSender() {
        return isSender;
    }

    public void setAvailable() {
        availability = true;
    }

    public boolean isAvailable() {
        return availability;
    }

    public Position getMyPosition() {
        return myPosition;
    }

    public RoutingTable getRoutingTable() {
        return routingTable;
    }

    public Queue getQueue() {
        return messageQueue;
    }

    public void setAgentChance(int agentChance) {
        this.agentChance = agentChance;
    }

    @Override
    public String toString() {
        if((myPosition.getX() + 1) == Math.sqrt(Constants.nrOfNodes)) {
            return nodeStatus + "\n";
        } else {
            return nodeStatus;
        }
    }
}

