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

    public Node(NodeBuilder builder) {
        myPosition = builder.myPosition;
        isAvailable = builder.isAvailable;
        isSender = builder.isSender;
        neighbours = builder.neighbours;
        this.routingTable = new RoutingTable();
        this.messageQueue = new PriorityQueue<Message>();

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

    public static class NodeBuilder {
        private int timeStep;
        private Position myPosition;
        private ArrayList<Node> neighbours;
        private RoutingTable routingTable;
        private double agentChance = Constants.agentChance;
        private Queue<Message> messageQueue;
        private boolean isAvailable;
        private boolean isSender;

        public NodeBuilder(Position position) {
            myPosition = position;
        }

        public NodeBuilder isAvailable(boolean available) {
            isAvailable = available;
            return this;
        }

        public NodeBuilder isSender(boolean sender) {
            isSender = sender;
            return this;
        }

        public NodeBuilder neighbours(ArrayList<Node> neighbours) {
            this.neighbours = neighbours;
            return this;
        }

        public Node build() {
            return new Node(this);
        }
    }
}

