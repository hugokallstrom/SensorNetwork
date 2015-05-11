import java.util.ArrayList;

/**
 * Created by hugo on 5/11/15.
 */
public class SensorSimulator {

    private double eventChance = Constants.eventChance;
    private ArrayList<Node> nodes = new ArrayList<Node>();
    private ArrayList<Node> queryNodes = new ArrayList<Node>();

    public void initNodes(int nrOfNodes) {
        for(int i = 0; i < nrOfNodes; i++) {
            // Create nodes.
        }
    }

    public void startSimulation(int steps) {
        for(int i = 0; i < steps; i++) {
            // Run time step
        }
    }
}
