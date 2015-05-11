/**
 * Created by hugo on 5/11/15.
 */
import java.util.*;

public class Query {
	private Stack<Node> pathTaken;
	private Hashtable<Position, Node> visited;
	private int steps;
	private Event event;
	private int timeToLive;

	public Query(Event e){
		
		timeToLive = Constants.timeToLiveQuery;
		steps = 0;
		visited = new Hashtable<Position,Node>();
		pathTaken = new Stack<Node>();
		event = e;
	}
}
