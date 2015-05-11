/**
 * Created by hugo on 5/11/15.
 */
public class Main {

    public static void main(String args[]) {
        SensorSimulator sensorSimulator = new SensorSimulator();
        sensorSimulator.initNodes(Constants.nrOfNodes);
        sensorSimulator.startSimulation(Constants.steps);
    }

}
