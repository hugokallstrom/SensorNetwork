import java.util.*;

/**
 * Created by hugo on 5/11/15.
 */
public class Node {

    private Position myPosition;
    private ArrayList<Node> neighbours;
    private RoutingTable routingTable;
    private Queue<Message> messageQueue;
    private boolean isAvailable;
    private boolean isSender;

    public Node(Position position) {
        myPosition = position;
        isAvailable = true;
        isSender = false;
        neighbours = new ArrayList<Node>();
        routingTable = new RoutingTable();
        messageQueue = new PriorityQueue<Message>();
    }

    public void receiveEvent(Event event) {
        if(calculateChance(Constants.eventChance)) {
            routingTable.addEvent(event);
            createAgent(event);
        }
    }

    private void createAgent(Event event) {
        if(calculateChance(Constants.agentChance)) {
            Message agent = new Agent(event);
            messageQueue.add(agent);
        }
    }

    private boolean calculateChance(int chance) {
        return new Random().nextInt(chance - 1) == 0;
    }

    public void handleMessage() {
        if(!messageQueue.isEmpty()) {
            Message message = messageQueue.poll();
            Position nodePosition = message.handleEvents(routingTable);
            if(nodePosition.equals(myPosition) && message.canMove()) {
                Node previousNode = message.getPathTaken().pop();
                if(previousNode.isAvailable) {
                    previousNode.receiveMessage(message);
                }
            } else if(nodePosition != null) {
                Node neighbour = getNeighbour(nodePosition);
                sendMessageToNode(message, neighbour);
            } else {
                Node neighbour = selectNextNeighbour(message.getPathTaken());
                sendMessageToNode(message, neighbour);
            }
        }
    }

    private Node selectNextNeighbour(Stack<Node> pathTaken) {
        for(Node visitedNode : pathTaken) {
            for (Node neighbour : neighbours) {
                if(!visitedNode.equals(neighbour)) {
                    return neighbour;
                }
            }
        }
        return neighbours.get(new Random().nextInt(neighbours.size()) + 1);
    }

    private Node getNeighbour(Position nodePosition) {
        for(Node neigbour : neighbours) {
            if(neigbour.getMyPosition().equals(nodePosition)) {
                return neigbour;
            }
        }
        return null;
    }

    private void sendMessageToNode(Message message, Node neighbour) {
        if(neighbour.isAvailable && message.canMove()) {
            neighbour.receiveMessage(message);
        }
    }

    private void receiveMessage(Message message) {
        isAvailable = false;
        message.addToPath(this);
        messageQueue.add(message);
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
        isAvailable = true;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public Position getMyPosition() {
        return myPosition;
    }

    @Override
    public String toString() {
        return "Node info: " + myPosition.toString() +
               "Neighbours: \n" + printNeighbours();
    }

    private String printNeighbours() {
        String neighboursString = " ";
        for(Node node : neighbours) {
           neighboursString += String.valueOf(node.getMyPosition()) + " ";
        }
        return neighboursString;
    }
}

