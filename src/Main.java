import java.util.ArrayList;

/**
 * Created by hugo on 5/11/15.
 */
public class Main {

    public static void main(String args[]) {
        SensorSimulator sensorSimulator = new SensorSimulator();
        sensorSimulator.initNodes(Constants.nrOfNodes);
        sensorSimulator.setQueryNodes();
        sensorSimulator.startSimulation(Constants.steps);
        System.out.println("Simulation done, queries sent: " + Constants.queriesSent);
        System.out.println("Queries replied: " + Constants.numberOfReplies.size());
        //checkReplies();
    }

    public static void checkReplies() {
        for(Integer eventId : Constants.numberOfReplies.keySet()) {
            if(Constants.numberOfReplies.get(eventId) == 2) {
                System.out.println("Dual reply on: " + eventId);
            }
        }
    }

}
