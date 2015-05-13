/**
 * Created by hugo on 5/11/15.
 */
import java.util.*;

public class RoutingTable {

	private ArrayList<Event> events;
	
	public RoutingTable(){
		events = new ArrayList<Event>();
	}

	public void syncEvents(ArrayList<Event> e){

		
		
		for(Event event : events ){
			if(!events.contains(event)){
				events.add(event);
			}
		}
		for(Event event : e){
			if(event.getDistance())
			if(!e.contains(event)){
				e.add(event);
			}
		}
	}

	public void addEvent(Event e){
		
		events.add(e);
	}

	public Event getEvent(Event e){
		
		if(events.contains(e)){
				return e;
		}
		return null;
	}
	public ArrayList<Event> getEventList(){
		
		return events;
	}
}
