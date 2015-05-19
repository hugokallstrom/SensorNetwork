import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * @author Hugo Källström
 * Simulates a sensor network by
 * creating an array of nodes. The array is iterated over
 * and for every node there is a chance to create an event
 * and send it to the node. If the node is a sender, a query
 * can be sent periodically.
 */
public class SensorSimulator {

    private ArrayList<Node> nodes = new ArrayList<Node>();
    private ArrayList<Event> eventList = new ArrayList<Event>();

    /**
     * Initialize the node matrix and
     * set neighbours to all nodes.
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
            node.setNeighbours(findNeighbours(node.getMyPosition()));
        }
    }

    /**
     * Finds the neighbours based on a position.
     * @param myPosition the position
     * @return an array list of nodes which is neighbours to the
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
     * Starts a new simulation and runs it for fixed number of time steps.
     * @param steps number of time steps to run.
     * @throws IOException
     */
    public void startSimulation(int steps) throws IOException {
        setQueryNodes();
        for(int timeStep = 0; timeStep < steps; timeStep++) {
        	System.out.println("Step " + timeStep);
            executeTimeStep(timeStep);
        }
    }

    /**
     * Sets a number of nodes senders, which means the nodes
     * can create queries for events.
     */
    private void setQueryNodes() {
        for(int i = 0; i < Constants.nrOfQueryNodes; i++) {
            int random = generateRandom(Constants.nrOfNodes);
            nodes.get(random).setSender(true);
        }
    }

    /**
     * Executes a time step by first creating an event. Thereafter
     * the current node handles its messages. Finally if the node is a
     * sender, the node sends a query for a random event.
     * @param timeStep how many time steps to run the simulation.
     * @throws IOException
     */
    private void executeTimeStep(int timeStep) throws IOException {
        for(Node node : nodes) {
            if(calculateChance(Constants.eventChance)) {
                Event event = new Event(generateRandom(Constants.eventIdMax), timeStep, node.getMyPosition());
                eventList.add(event);
                node.receiveEvent(event);
            }

            node.handleMessage();

            if(node.isSender() && timeStep % Constants.queryInterval == 0 && !eventList.isEmpty()) {
                sendQueryToNode(node);
            }
        }

        setNodesAvailable();


      System.out.println("Press any button to continue");
      System.in.read();
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
     * Sends a query for a random event id.
     * @param node the node to create the query.
     */
    private void sendQueryToNode(Node node) {
        Collections.shuffle(eventList);
        Event event = eventList.get(0);
        node.createQuery(event.getEventId());
    }

    /**
     * Sets all nodes to available.
     */
    private void setNodesAvailable() {
        for(Node node : nodes) {
            node.setAvailable();
           System.out.print(node.toString());
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
}
