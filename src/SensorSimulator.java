import java.util.ArrayList;

/**
 * Created by hugo on 5/11/15.
 */
public class SensorSimulator {

    private double eventChance = Constants.eventChance;
    private ArrayList<Node> nodes = new ArrayList<Node>();
    private ArrayList<Node> queryNodes = new ArrayList<Node>();

    public void initNodes(int nrOfNodes) {
        double matrixWidth = Math.sqrt(nrOfNodes);

        for(int x = 0; x < nrOfNodes; x++) {
            for(int y = 0; y < matrixWidth; y++) {
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

    public void startSimulation(int steps) {
        for(int i = 0; i < steps; i++) {
            // Run time step

        }
    }
}
