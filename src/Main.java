/**
 * Main class running SensorSimulator.
 * @author Hugo Kallstrom
 */
public class Main {

    public static void main(String args[]) {
    	
        SensorSimulator sensorSimulator = new SensorSimulator();
        sensorSimulator.initNodes(Constants.nrOfNodes);
        sensorSimulator.setQueryNodes();
        sensorSimulator.startSimulation(Constants.steps);

        System.out.println("Simulation done, queries sent: " 
        								+ Constants.queriesSent);
        
        System.out.println("Queries replied: " 
        								+ Constants.numberOfReplies.size());
    }
}
