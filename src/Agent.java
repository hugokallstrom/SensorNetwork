/**
 * Created by hugo on 5/11/15.
 */
public class Agent {
	private Stack<Node> pathTaken;
	private HashTable<Integer,Event> events;
	private int timeToLive;
	private int steps;
	
	public Agent(Event e){
		steps = 0;
		timeToLive = Constants.timeToLiveAgent;
		pathTaken = new Stack<Node>();
		events = new HashTable<Integer,Event>();
		events.put(e.eventsId,e);
	}
}
