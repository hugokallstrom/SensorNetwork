import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by hugo on 5/11/15.
 */
public class SensorSimulator {

    private ArrayList<Node> nodes = new ArrayList<Node>();
    private ArrayList<Event> eventList = new ArrayList<Event>();

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

    public void startSimulation(int steps) throws IOException {
        setQueryNodes();
        for(int timeStep = 0; timeStep < steps; timeStep++) {
        //    System.out.println("Step " + timeStep);
            manageNodes(timeStep);
        }
    }

    private void setQueryNodes() {
        for(int i = 0; i < Constants.nrOfQueryNodes; i++) {
            int random = generateRandom(Constants.nrOfNodes);
            nodes.get(random).setSender(true);
        }
    }

    private void manageNodes(int timeStep) throws IOException {

        for(Node node : nodes) {
            if(calculateChance(Constants.eventChance)) {
                Event event = createEvent(timeStep, node.getMyPosition());
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

    private boolean calculateChance(int chance) {
        return new Random().nextInt(chance) == 0;
    }

    private void sendQueryToNode(Node node) {
        Collections.shuffle(eventList);
        Event event = eventList.get(0);
        node.createQuery(event.getEventId());
    }

    private void setNodesAvailable() {
        for(Node node : nodes) {
            node.setAvailable();
            System.out.print(node.toString());
        }
    }

    private Event createEvent(int timeOfEvent, Position position) {
        int eventId = generateRandom(Constants.eventIdMax);
        return new Event(eventId, timeOfEvent, position);
    }

    private int generateRandom(int maxVal) {
        Random rand = new Random();
        return rand.nextInt(maxVal);
    }
}
