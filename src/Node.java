import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;

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
            Node node = message.syncEvents(routingTable);
            if(node.equals(this)) {
                Node neighbour = message.getPathTaken().pop();
                if(neighbour.isAvailable) {
                    neighbour.receiveMessage(message);
                }
            }
        }
    }

    private void receiveMessage(Message message) {
        messageQueue.
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

