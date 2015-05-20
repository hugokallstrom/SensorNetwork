import java.io.IOException;

/**
 * Created by hugo on 5/11/15.
 */
public class Main {

    public static void main(String args[]) {
        SensorSimulator sensorSimulator = new SensorSimulator();
        sensorSimulator.initNodes(Constants.nrOfNodes);
        sensorSimulator.setQueryNodes();
        try {
            sensorSimulator.startSimulation(Constants.steps);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
