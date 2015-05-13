import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by hugo on 5/11/15.
 */
public class Node {
    private int timeStep;
    private Position myPosition;
    private ArrayList<Node> neighbours;
    private RoutingTable routingTable;
    private double agentChance = Constants.agentChance;
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

    public void setNeighbours(ArrayList<Node> neighbours) {
        this.neighbours = neighbours;
    }

    public ArrayList<Node> getNeighbours() {
        return neighbours;
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

