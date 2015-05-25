import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Simulates a sensor network by
 * creating an array of nodes. The array is iterated over
 * and for every node there is a chance to create an event
 * and send it to the node. If the node is a sender, a query
 * can be sent periodically.
 * @author Hugo Källström
 */
public class SensorSimulator {

    private ArrayList<Node> nodes = new ArrayList<Node>();
    private ArrayList<Event> eventList = new ArrayList<Event>();

    /**
     * Initialize the node matrix and
     * set neighbors to all nodes.
     * @param nrOfNodes total number of nodes in the matrix.
     */
    public void initNodes(int nrOfNodes) {
        double matrixDim = Math.sqrt(nrOfNodes);
        for(int y = 0; y < matrixDim; y++) {
            for(int x = 0; x < matrixDim; x++) {
                Position position = new Position(x, y);
                Node node = new Node(position);
                nodes.add(node);
            }
        }
        for(Node node : nodes) {
            node.setNeighbors(findNeighbours(node.getMyPosition()));
        }
    }

    /**
     * Finds the neighbors based on a position.
     * @param myPosition the position
     * @return an array list of nodes which is neighbors to the
     * position.
     */
    private ArrayList<Node> findNeighbours(Position myPosition) {
        ArrayList<Node> neighbours = new ArrayList<Node>();
        for(Node node : nodes) {
            Position position = node.getMyPosition();
            if(position.isNeighbour(myPosition)) {
                neighbours.add(node);
            }
        }
        return neighbours;
    }

    /**
     * Sets a number of nodes senders, which means the nodes
     * can create queries for events.
     */
    public void setQueryNodes() {
        for(int i = 0; i < Constants.nrOfQueryNodes; i++) {
            int random = generateRandom(Constants.nrOfNodes);
            nodes.get(random).setSender(true);
        }
    }

    /**
     * Starts a new simulation and runs it for fixed number of time steps.
     * @param steps number of time steps to run.
     * @throws IOException
     */
    public void startSimulation(int steps)  {
        for(int timeStep = 0; timeStep < steps; timeStep++) {
            executeTimeStep(timeStep);
            setAllNodesAvailable();
        }
    }

    /**
     * Executes a time step by first creating an event. Thereafter
     * the current node handles its messages. Finally if the node is a
     * sender, the node sends a query for a random event.
     * @param timeStep how many time steps to run the simulation.
     * @throws IOException
     */
    private void executeTimeStep(int timeStep) {
        for(Node node : nodes) {
            createEvent(node, timeStep);
            node.checkTimer();
            Message message = node.getMessageInQueue();
            if(message != null) {
                Node nextNode = node.handleMessage(message);
                node.sendMessageToNode(nextNode);
            }
            sendQuery(node, timeStep);
        }
    }

    /**
     * Creates a new event and agent based on a predefined chance.
     * Sends the event and agent to the node.
     * @param node node to receive event.
     * @param timeStep the current time step.
     */
    private void createEvent(Node node, int timeStep) {
        if(calculateChance(Constants.eventChance)) {
            Event event = new Event(generateRandom(Constants.eventIdMax), timeStep, node.getMyPosition());
            eventList.add(new Event(event));
            node.receiveEvent(event);
            if(calculateChance(Constants.agentChance)) {
                node.createAgent(event);
            }
        }
    }

    /**
     * Sends a query for a random event id.
     * @param node the node to create the query.
     */
    private void sendQuery(Node node, int timeStep) {
        if(node.isSender() && timeStep % Constants.queryInterval == 0 && !eventList.isEmpty()) {
            Constants.queriesSent++;
            Collections.shuffle(eventList);
            Event event = eventList.get(0);
            eventList.remove(0);
            node.createQuery(event.getEventId());
        }
    }

    /**
     * Sets all nodes to available.
     */
    private void setAllNodesAvailable() {
        for(Node node : nodes) {
            node.setAvailable();
        }
    }

    /**
     * Generates a random value in the range 0 - maxVal.
     * @param maxVal the max value for the range.
     * @return the randomly generated value.
     */
    private int generateRandom(int maxVal) {
        Random rand = new Random();
        return rand.nextInt(maxVal);
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
}
