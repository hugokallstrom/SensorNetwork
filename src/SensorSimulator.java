import java.util.ArrayList;

/**
 * Created by hugo on 5/11/15.
 */
public class SensorSimulator {

    private double eventChance = Constants.eventChance;
    private ArrayList<Node> nodes = new ArrayList<Node>();
    private ArrayList<Node> queryNodes = new ArrayList<Node>();

    public void initNodes(int nrOfNodes) {
        for(int x = 0; x < nrOfNodes; x++) {
            double matrixWidth = Math.sqrt(nrOfNodes);
            for(int y = 0; y < matrixWidth; y++) {
                Position position = new Position(x, y);
                Node node = new Node(position);
                nodes.add(node);
            }
        }

        for(int x = 0; x < nrOfNodes; x++) {

        }
    }

    private ArrayList<Node> findNeighbours(Position position) {
        ArrayList<Node> neighbours = new ArrayList<Node>();
        for(Node node : nodes) {

        }
        return neighbours;
    }

    public void startSimulation(int steps) {
        for(int i = 0; i < steps; i++) {
            // Run time step
        }
    }
}
