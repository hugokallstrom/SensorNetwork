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

    public Node(Position position) {
        myPosition = position;
        availability = true;
        isSender = false;
        neighbours = new ArrayList<Node>();
        routingTable = new RoutingTable();
        messageQueue = new LinkedBlockingQueue<Message>();
    }

    public void receiveEvent(Event event) {
        if(calculateChance(Constants.eventChance)) {
            routingTable.addEvent(event);
            createAgent(event);
            System.out.println("Event detected at: " + myPosition);
            nodeStatus = "E";
        } else {
            if(availability) {
                nodeStatus = "*";
            } else {
                nodeStatus = "U";
            }
        }
    }

    private void createAgent(Event event) {
        if(calculateChance(Constants.agentChance)) {
            Message agent = new Agent(event);
            agent.addToPath(this);
            messageQueue.add(agent);
        }
    }

    private boolean calculateChance(int chance) {
        return new Random().nextInt(chance) == 0;
    }

    public void handleMessage() {
        if(!messageQueue.isEmpty()) {
            Message message = messageQueue.poll();
            Position nodePosition = message.handleEvents(routingTable);
            if(nodePosition == null) {
                Node neighbour = selectNextNeighbour(message.getPathTaken());
                sendMessageToNode(message, neighbour);
            } else if(nodePosition.equals(myPosition)) {
                Node previousNode = message.getPathTaken().pop();
                sendMessageToNode(message, previousNode);
            } else {
                Node neighbour = getNeighbourFromPos(nodePosition);
                sendMessageToNode(message, neighbour);
            }
        }
    }

    private Node selectNextNeighbour(Stack<Node> pathTaken) {
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
            System.out.println(myPosition + " Sending message to neighbour: " + neighbour.getMyPosition());
            neighbour.receiveMessage(message);
        } else {
            System.out.println(myPosition + "Putting message in queue, neighbour at " + neighbour.getMyPosition() + " not available.");
            nodeStatus = "+";
            messageQueue.add(message);
        }
    }

    private void receiveMessage(Message message) {
        availability = false;
        if(message.canMove()) {
            System.out.println(myPosition + " Received message");
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

    @Override
    public String toString() {
        if((myPosition.getX() + 1) == Math.sqrt(Constants.nrOfNodes)) {
            return nodeStatus + "\n";
        } else {
            return nodeStatus;
        }
    }
}

