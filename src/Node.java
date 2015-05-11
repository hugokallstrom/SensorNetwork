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
}

