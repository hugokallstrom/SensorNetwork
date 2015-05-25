import java.util.HashMap;

/**
 * Class handling constants.
 * @author Hugo Källström
 */
public class Constants {
    public static int nrOfNodes = 2500;
    public static int eventChance = 10000;
    public static int steps = 10000;
    public static int queryInterval = 400;
    public static int agentChance = 2;
    public static int timeToLiveAgent = 50;
    public static int timeToLiveQuery = 45;
    public static int nrOfQueryNodes = 4;
    public static int eventIdMax = 20000000;
    public static HashMap<Integer, Integer> numberOfReplies =
    		new HashMap<Integer, Integer>();
    public static int queriesSent = 0;

}
