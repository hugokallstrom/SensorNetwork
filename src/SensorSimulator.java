import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by hugo on 5/11/15.
 */
public class SensorSimulator {

    private ArrayList<Node> nodes = new ArrayList<Node>();

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
        findQueryNodes();
        do {
            for(int i = 0; i < steps; i++) {
                System.out.println("Step " + i);
                for(Node node : nodes) {
                    Event event = createEvent(i, node.getMyPosition());
                    node.receiveEvent(event);
                    node.handleMessage();
                    if(node.isSender()) {

                    }
                }
                for(Node node : nodes) {
                    System.out.print(node.toString());
                    node.setAvailable();
                }
                System.out.println("Press any button to continue");
                System.in.read();
            }
        } while(steps != Constants.steps);
    }

    private void findQueryNodes() {
        for(int i = 0; i < Constants.nrOfQueryNodes; i++) {
            int random = generateRandom(Constants.nrOfNodes);
            nodes.get(random).setSender(true);
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
