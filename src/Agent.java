/**
 * Created by hugo on 5/11/15.
 */
import java.util.*;

public class Agent {
	
	private Stack<Node> pathTaken;
	private Hashtable<Integer,Event> events;
	private int timeToLive;
	private int steps;
	
	public Agent(Event e){
		steps = 0;
		timeToLive = Constants.timeToLiveAgent;
		pathTaken = new Stack<Node>();
		events = new Hashtable<Integer,Event>();
		events.put(e.getEventId(),e);
	}
}
