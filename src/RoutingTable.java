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
		/*Denna metod �r inte f�rdig*/
		for(Event event : e ){
			if(!events.contains(event)){
				events.add(event);
			}
		}
		for(Event event : events){
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
		throw new IllegalArgumentException("Event's id was not found! in "
				+ " routingTable");
	}
}
